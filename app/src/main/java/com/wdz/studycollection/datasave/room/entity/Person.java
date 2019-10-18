package com.wdz.studycollection.datasave.room.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Person {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String name;
    public int age;
    @Ignore
    public int money;
    @Embedded
    public MyAddress address;

    public Person() {

    }

    @Ignore
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", money=" + money +
                ", address=" + address +
                '}';
    }
}
