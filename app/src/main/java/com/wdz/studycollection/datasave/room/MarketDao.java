package com.wdz.studycollection.datasave.room;

import com.wdz.studycollection.datasave.room.entity.Market;
import com.wdz.studycollection.datasave.room.entity.Person;

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
interface MarketDao {

      @Insert(onConflict = OnConflictStrategy.REPLACE)
      void insertMarket(Market... market);

      @Delete
      void deleteMarket(Market market);

      @Update
      void updateMarket(Market market);



      @Query("delete from market where uidu=:id")
      int deleteMarketById(int id);


      @Query("select * from market")
      LiveData<List<Market>> findAllMarket();
}