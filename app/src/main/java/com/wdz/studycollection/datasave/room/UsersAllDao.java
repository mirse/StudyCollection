package com.wdz.studycollection.datasave.room;

import com.wdz.studycollection.datasave.room.entity.UsersAll;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface UsersAllDao {
    @Query("SELECT * from users")
    public List<UsersAll> loadUserAndPets();
}
