package com.sample;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = -5411807328989112195L;

    private String name;
    private int age;

    public Person() {}

    public Person(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

}
