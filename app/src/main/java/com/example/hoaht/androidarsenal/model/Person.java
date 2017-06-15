package com.example.hoaht.androidarsenal.model;

import io.realm.RealmObject;

/**
 * Person.
 *
 * @author HoaHT
 */

public class Person extends RealmObject {
    private String name;
    private String age;

    public Person() {
    }

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
