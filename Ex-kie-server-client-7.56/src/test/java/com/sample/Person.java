package com.sample;

import java.util.Calendar;

import org.kie.api.remote.Remotable;

@Remotable
public class Person {

    private String name;

    private int age;
    
    private Calendar birthDay;

    
    public Calendar getBirthDay() {
        return birthDay;
    }

    
    public void setBirthDay(Calendar birthDay) {
        this.birthDay = birthDay;
    }

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
