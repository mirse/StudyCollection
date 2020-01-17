package com.wdz.studycollection.datasave.room.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"uidu"})})
public class Market {
    @PrimaryKey
    public int uidu;
    public String address;



    public Market() {

    }


    public Market(int uid, String address) {
        this.uidu = uid;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Market{" +
                "uidu=" + uidu +
                ", address='" + address + '\'' +
                '}';
    }
}
