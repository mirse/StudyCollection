package com.wdz.module_architecture.paging.room;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * @Author dezhi.wang
 * @Date 2021/1/9 8:58
 */
@Dao
public interface UserDao {

    //可观察的user对象
    @Query("SELECT * FROM User")
    DataSource.Factory<Integer,User> queryUsers();

    //可观察的user对象
    @Query("SELECT * FROM User")
    LiveData<List<User>> queryUsersByLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPerson(User user);
}
