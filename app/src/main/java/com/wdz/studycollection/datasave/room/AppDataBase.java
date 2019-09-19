package com.wdz.studycollection.datasave.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.wdz.studycollection.datasave.room.entity.Users;
import com.wdz.studycollection.datasave.room.entity.UsersChild;


@Database(entities = {Users.class,UsersChild.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();
}