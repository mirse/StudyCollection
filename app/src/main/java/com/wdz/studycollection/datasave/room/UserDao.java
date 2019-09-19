package com.wdz.studycollection.datasave.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.wdz.studycollection.datasave.room.entity.Users;
import com.wdz.studycollection.datasave.room.entity.UsersAll;
import com.wdz.studycollection.datasave.room.entity.UsersChild;
import com.wdz.studycollection.mvp.logintest.model.User;

import java.util.List;

@Dao
public interface UserDao {
    //users表
    @Query("SELECT * FROM users")
    List<Users> loadAll();
    @Insert
    void insertAll(Users... users);
    @Query("SELECT * FROM users WHERE id LIKE :id LIMIT 1")
    List<UsersAll> findById(int id);
    @Query("SELECT * FROM users")
    List<UsersAll> findAll();
    @Query("DELETE FROM users")
    void deleteAll();


    //userschild 表
    @Query("SELECT * FROM userschild")
    List<UsersChild> loadAllChild();
    @Insert
    void insertAll(UsersChild... users);
    @Query("SELECT * FROM userschild WHERE childId LIKE :id LIMIT 1")
    UsersChild findByIdChild(int id);

    @Query("SELECT * FROM userschild")
    List<UsersChild> findAllChild();

    @Query("DELETE FROM userschild")
    void deleteAllChild();

}