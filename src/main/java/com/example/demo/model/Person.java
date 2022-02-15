package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Person {

    private final UUID id;
    private final String name;
    private final int age;

    public Person(@JsonProperty("id") UUID id, @JsonProperty("name") String name,@JsonProperty("age") int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public UUID getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }


}
