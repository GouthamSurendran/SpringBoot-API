package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("dao")
public class PersonDataAccessService implements PersonDAO{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        final String query = "INSERT INTO person (id,name,age) VALUES (uuid_generate_v4(), ?, ?)";
        String name = person.getName();
        int age = person.getAge();
        jdbcTemplate.update(query,
                new Object[]{name, age});
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        final String query = "SELECT id, name, age FROM person";
        return jdbcTemplate.query(query, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            return new Person(id, name, age);
        });
    }

    @SuppressWarnings("deprecation")
    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String query = "SELECT id, name, age FROM person WHERE id = ?";

        Person person = jdbcTemplate.queryForObject(query,
                new Object[]{id},
                (resultSet, i) -> {
                UUID personId = UUID.fromString(resultSet.getString("id"));
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                return new Person(personId, name,age);
                }
                );
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        final String query = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(query, new Object[]{id});
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        String name = person.getName();
        int age = person.getAge();
        final String query = "UPDATE person SET name = ?, age = ? WHERE id = ?";
        jdbcTemplate.update(query, new Object[]{name, age, id});
        return 1;
    }
}
