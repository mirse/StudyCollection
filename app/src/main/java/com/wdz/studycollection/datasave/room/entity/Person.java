package com.wdz.studycollection.datasave.room.entity;

import java.util.Arrays;

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
    public byte[] uuid;
    @Ignore
    public int money;
    @Embedded
    public MyAddress address;

    public Person() {

    }

    @Ignore
    public Person(String name, int age,byte[] uuid) {
        this.name = name;
        this.age = age;
        this.uuid = uuid;
    }

    public byte[] getUuid() {
        return uuid;
    }

    public void setUuid(byte[] uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Person{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", uuid=" + Arrays.toString(uuid) +
                ", money=" + money +
                ", address=" + address +
                '}';
    }
}
