package com.wdz.studycollection.datasave.room.entity;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PersonInfo {
    @Embedded
    public Person person;

    @Relation(parentColumn = "uid",entityColumn = "persionId")
    public List<Clothes> clothes;


    @Override
    public String toString() {
        return "PersonInfo{" +
                "person=" + person +
                ", clothes=" + clothes +
                '}';
    }
}
