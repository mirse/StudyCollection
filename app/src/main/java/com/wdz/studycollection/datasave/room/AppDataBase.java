package com.wdz.studycollection.datasave.room;

import com.wdz.studycollection.datasave.room.entity.Clothes;
import com.wdz.studycollection.datasave.room.entity.Person;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Person.class, Clothes.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract PersonDao personDao();
    public abstract ClothesDao clothesDao();
}