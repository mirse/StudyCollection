package com.example.dezhiwang.studycollection.DataSave.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dezhiwang.studycollection.DataSave.Room.Entity.Users;


@Database(entities = {Users.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();
}