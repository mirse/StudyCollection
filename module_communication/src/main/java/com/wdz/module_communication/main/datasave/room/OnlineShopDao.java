package com.wdz.module_communication.main.datasave.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wdz.module_communication.main.datasave.room.entity.OnlineShop;


import java.util.List;

//Person
@Dao
interface OnlineShopDao {

      @Insert(onConflict = OnConflictStrategy.REPLACE)
      void insertOnlineShop(OnlineShop... onlineShops);

      @Delete
      void deleteOnlineShop(OnlineShop... onlineShops);

      @Update
      void updateOnlineShop(OnlineShop... onlineShops);





      @Query("select * from onlineshop")
      LiveData<List<OnlineShop>> findAllOnlineShop();
}