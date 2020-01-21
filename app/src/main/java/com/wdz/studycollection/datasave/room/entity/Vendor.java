package com.wdz.studycollection.datasave.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Vendor {
    @PrimaryKey
    public int vendorId;
    public String vendorName;

    public Vendor(int vendorId, String vendorName) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId=" + vendorId +
                ", vendorName='" + vendorName + '\'' +
                '}';
    }
}
