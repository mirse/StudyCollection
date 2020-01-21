package com.wdz.studycollection.datasave.room.entity;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PersonInfo {
    @Embedded
    public Person person;

    @Relation(parentColumn = "uid",entityColumn = "uidu")
    public List<Market> markets;

    @Relation(parentColumn = "uid",entityColumn = "vendorId")
    public List<Vendor> vendors;

    @Override
    public String toString() {
        return "PersonInfo{" +
                "person=" + person +
                ", markets=" + markets +
                ", vendors=" + vendors +
                '}';
    }
}
