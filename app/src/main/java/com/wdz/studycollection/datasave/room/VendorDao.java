package com.wdz.studycollection.datasave.room;

import com.wdz.studycollection.datasave.room.entity.Market;
import com.wdz.studycollection.datasave.room.entity.Vendor;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
      LiveData<List<Vendor>> findAllVendor();
}