package com.wdz.studycollection.datasave.room;

import com.wdz.studycollection.datasave.room.entity.Clothes;
import com.wdz.studycollection.datasave.room.entity.Person;
import com.wdz.studycollection.datasave.room.entity.embedded.PersonInfo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
//Person
@Dao
interface PersonDao {

      @Insert(onConflict = OnConflictStrategy.REPLACE)
      void insertPerson(Person person,Clothes clothes);

      @Delete
      void deletePerson(Person person);

      @Update
      void updatePerson(Person person);

      @Query("select * from person where uid=:uid")
      List<Person> findPersonByUid(int uid);

      @Query("delete from person where uid=:uid")
      int deletePersonById(int uid);

     // @Transaction
      @Query("select * from person")
      LiveData<List<Person>> findAllPerson();

      @Transaction
      @Query("select * from person WHERE person.uid = :id")
      List<PersonInfo> loadAllInfoById(int id);

}