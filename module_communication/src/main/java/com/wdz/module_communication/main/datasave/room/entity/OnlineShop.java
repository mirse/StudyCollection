package com.wdz.module_communication.main.datasave.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.wdz.module_communication.main.datasave.room.StringTypeConverter;


import java.util.ArrayList;
import java.util.List;

@Entity
public class OnlineShop {

    @PrimaryKey
    @NonNull
    public int shopId;
    public int shopAddress;
    public int id;
    @TypeConverters(StringTypeConverter.class)
    public List<String> house = new ArrayList<>();

}
