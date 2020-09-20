package com.wdz.module_communication.main.datasave.room;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.wdz.common.MyApplication;


public class DBInstance {
    //public static final String DB_NAME = "room_test";
    private static final String DB_NAME = "/sdcard/Wdz/room.db";
    public static AppDataBase appDataBase;
    public static AppDataBase getInstance(){
        if (appDataBase==null){
            synchronized (DBInstance.class){
                appDataBase = Room.databaseBuilder(MyApplication.getInstance(),AppDataBase.class,DB_NAME)
                        .addMigrations(MIGRATION_1_2)
                        //.fallbackToDestructiveMigration() //清空数据库
                        .build();
            }
        }
        return appDataBase;
    }
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //1、普通方式更新数据库 exec增加新列
//            database.execSQL("ALTER TABLE person "
//                    + " ADD COLUMN uuid BLOB ");

            //2、新建表迁移数据库以更新数据库
            database.execSQL("CREATE TABLE shop_new(shopId INTEGER NOT NULL PRIMARY KEY,shopAddress INTEGER NOT NULL,id INTEGER NOT NULL)");
            database.execSQL("INSERT INTO shop_new(shopId,shopAddress) SELECT shopId,shopAddress FROM OnlineShop");
            database.execSQL("DROP TABLE OnlineShop");
            database.execSQL("ALTER TABLE shop_new RENAME TO OnlineShop");
        }
    };
}
