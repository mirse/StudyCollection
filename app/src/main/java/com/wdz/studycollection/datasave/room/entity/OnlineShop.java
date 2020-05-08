package com.wdz.studycollection.datasave.room.entity;

import com.wdz.studycollection.datasave.room.StringTypeConverter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import static androidx.room.ForeignKey.CASCADE;

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
