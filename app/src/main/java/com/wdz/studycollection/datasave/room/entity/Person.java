package com.wdz.studycollection.datasave.room.entity;

import java.util.Arrays;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"uid"})})
public class Person {
    @PrimaryKey
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
    public Person(String name, int age,int uid) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        //this.uuid = uuid;
    }


    @Override
    public String toString() {
        return "Person{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", age=" + age +
                //", uuid=" + Arrays.toString(uuid) +
                ", money=" + money +
                ", address=" + address +
                '}';
    }
}
