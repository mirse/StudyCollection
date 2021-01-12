package com.wdz.module_architecture.paging.room;

import android.content.Context;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


public class DBInstance {
    //public static final String DB_NAME = "room_test";
//    private static final String DB_NAME = "/sdcard/Wdz/room.db";
    private static final String DB_NAME = "roomtest_databese1";
    public static AppDataBase appDataBase;
    public static AppDataBase getInstance(Context context){
        if (appDataBase==null){
            synchronized (DBInstance.class){
                appDataBase = Room.databaseBuilder(context, AppDataBase.class,DB_NAME)
                        //.addMigrations(MIGRATION_1_2)
                        //.fallbackToDestructiveMigration() //清空数据库
                        .build();
            }
        }
        return appDataBase;
    }

}
