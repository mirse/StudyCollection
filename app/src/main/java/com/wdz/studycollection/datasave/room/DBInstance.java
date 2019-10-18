package com.wdz.studycollection.datasave.room;

import android.app.Application;

import com.wdz.studycollection.MyApplication;

import androidx.room.Room;

public class DBInstance {
    //public static final String DB_NAME = "room_test";
    private static final String DB_NAME = "/sdcard/Wdz/room.db";
    public static AppDataBase appDataBase;
    public static AppDataBase getInstance(){
        if (appDataBase==null){
            synchronized (DBInstance.class){
                appDataBase = Room.databaseBuilder(MyApplication.getInstance(),AppDataBase.class,DB_NAME)
                        .build();
            }
        }
        return appDataBase;
    }
}
