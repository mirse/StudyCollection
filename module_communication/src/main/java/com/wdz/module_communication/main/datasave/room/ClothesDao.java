package com.wdz.module_communication.main.datasave.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.wdz.module_communication.main.datasave.room.entity.Clothes;
import com.wdz.module_communication.main.datasave.room.entity.embedded.PersonInfo;


import java.util.List;

//clothes
@Dao
interface ClothesDao {
      @Insert
      void insertClothes(Clothes... clothes);

      @Query("select * from clothes")
      LiveData<List<Clothes>> findAllClothes();


      @Transaction
      @Query("select * from person where uid=:fatherId")
      PersonInfo getPersonInfo(int fatherId);

}