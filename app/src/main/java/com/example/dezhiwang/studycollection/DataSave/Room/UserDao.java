package com.example.dezhiwang.studycollection.DataSave.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dezhiwang.studycollection.DataSave.Room.Entity.Users;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<Users> loadAll();
    @Query("SELECT * FROM users WHERE id IN (:songIds)")
    List<Users> loadAllBySongId(int... songIds);
    @Query("SELECT * FROM users WHERE name LIKE :name AND release_year = :year LIMIT 1")
    Users loadOneByNameAndReleaseYear(String name, int year);
    @Insert
    void insertAll(Users... songs);
    @Delete
    void delete(Users song);
}