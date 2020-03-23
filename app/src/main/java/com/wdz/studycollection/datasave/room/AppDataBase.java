package com.wdz.studycollection.datasave.room;

import com.wdz.studycollection.datasave.room.entity.Clothes;
import com.wdz.studycollection.datasave.room.entity.Market;
import com.wdz.studycollection.datasave.room.entity.OnlineShop;
import com.wdz.studycollection.datasave.room.entity.Person;
import com.wdz.studycollection.datasave.room.entity.Vendor;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Person.class, Clothes.class, Market.class, Vendor.class, OnlineShop.class},version = 2,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract PersonDao personDao();
    public abstract ClothesDao clothesDao();
    public abstract MarketDao marketDao();
    public abstract VendorDao vendorDao();
    public abstract OnlineShopDao onlineShopDao();


}