package com.wdz.module_communication.main.datasave.room;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wdz.module_communication.main.datasave.room.entity.Vendor;

import java.util.List;

//Vendor
@Dao
interface VendorDao {

      @Insert(onConflict = OnConflictStrategy.REPLACE)
      void insertVendor(Vendor... vendors);

      @Delete
      void deleteVendor(Vendor vendors);

      @Update
      void updateVendor(Vendor vendors);



      @Query("delete from vendor where vendorId=:id")
      int deleteVendorById(int id);


      @Query("select * from vendor")
      DataSource.Factory<Integer,Vendor> findAllVendor();
}