package com.wdz.studycollection.datasave.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.wdz.studycollection.datasave.room.entity.Users;
import com.wdz.studycollection.datasave.room.entity.UsersChild;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<Users> loadAll();
    @Insert
    void insertAll(Users... users);
    @Query("SELECT * FROM users WHERE id LIKE :id LIMIT 1")
    Users findById(int id);

    @Query("SELECT * FROM USERS")
    List<Users> findAll();



    @Query("SELECT * FROM userschild")
    List<UsersChild> loadAllChild();
    @Insert
    void insertAll(UsersChild... users);
    @Query("SELECT * FROM userschild WHERE childId LIKE :id LIMIT 1")
    UsersChild findByIdChild(int id);

}