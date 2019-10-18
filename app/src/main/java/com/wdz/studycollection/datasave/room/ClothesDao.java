package com.wdz.studycollection.datasave.room;

import com.wdz.studycollection.datasave.room.entity.Clothes;
import com.wdz.studycollection.datasave.room.entity.Person;
import com.wdz.studycollection.datasave.room.entity.PersonInfo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

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