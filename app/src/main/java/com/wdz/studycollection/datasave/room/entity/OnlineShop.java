package com.wdz.studycollection.datasave.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity
public class OnlineShop {

    @PrimaryKey
    @NonNull
    public int shopId;
    public String shopAddress;

}
