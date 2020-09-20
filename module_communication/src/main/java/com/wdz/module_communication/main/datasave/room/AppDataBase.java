package com.wdz.module_communication.main.datasave.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.wdz.module_communication.main.datasave.room.entity.Clothes;
import com.wdz.module_communication.main.datasave.room.entity.Market;
import com.wdz.module_communication.main.datasave.room.entity.OnlineShop;
import com.wdz.module_communication.main.datasave.room.entity.Person;
import com.wdz.module_communication.main.datasave.room.entity.Vendor;



@Database(entities = {Person.class, Clothes.class, Market.class, Vendor.class, OnlineShop.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract PersonDao personDao();
    public abstract ClothesDao clothesDao();
    public abstract MarketDao marketDao();
    public abstract VendorDao vendorDao();
    public abstract OnlineShopDao onlineShopDao();


}