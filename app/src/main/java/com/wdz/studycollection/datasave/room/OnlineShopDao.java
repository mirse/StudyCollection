package com.wdz.studycollection.datasave.room;

import com.wdz.studycollection.datasave.room.entity.Market;
import com.wdz.studycollection.datasave.room.entity.OnlineShop;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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