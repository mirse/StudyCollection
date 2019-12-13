package com.wdz.studycollection.datasave.room;

import android.app.Application;

import com.wdz.studycollection.MyApplication;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DBInstance {
    //public static final String DB_NAME = "room_test";
    private static final String DB_NAME = "/sdcard/Wdz/room.db";
    public static AppDataBase appDataBase;
    public static AppDataBase getInstance(){
        if (appDataBase==null){
            synchronized (DBInstance.class){
                appDataBase = Room.databaseBuilder(MyApplication.getInstance(),AppDataBase.class,DB_NAME)
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
        }
        return appDataBase;
    }
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //此处对于数据库中的所有更新都需要写下面的代码
            database.execSQL("ALTER TABLE person "
                    + " ADD COLUMN uuid BLOB ");


        }
    };
}
