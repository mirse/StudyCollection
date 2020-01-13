package com.wdz.studycollection.datasave.room.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"marketId"})})
public class Market {
    @PrimaryKey
    public int marketId;
    public String address;



    public Market() {

    }


    public Market(int marketId, String address) {
        this.marketId = marketId;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Market{" +
                "marketId=" + marketId +
                ", address='" + address + '\'' +
                '}';
    }
}
