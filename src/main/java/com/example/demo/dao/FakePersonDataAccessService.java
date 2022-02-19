package com.example.demo.dao;
import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao") // Same as @Component but mainly used for Persistence layer
public class FakePersonDataAccessService implements PersonDAO {

    private static List<Person> db = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        db.add(new Person(id,person.getName(),person.getAge()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return db;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return db.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    } // Method to see if there is actually a person with the given id

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> mayBe = selectPersonById(id);
        if(mayBe.isPresent()){
            db.remove(mayBe.get());
            return 1;
        }
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return selectPersonById(id).map(
                p -> { int indexOfPersonToUpdate = db.indexOf(p);
                    if (indexOfPersonToUpdate >= 0){
                        db.set(indexOfPersonToUpdate, new Person(id, person.getName(), person.getAge()));
                        return 1;
                    }
                    else return 0;
                }).orElse(0);
    }
}
