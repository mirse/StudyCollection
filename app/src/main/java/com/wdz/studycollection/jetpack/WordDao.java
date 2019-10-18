package com.wdz.studycollection.jetpack;

import com.wdz.studycollection.jetpack.bean.Word;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
//DAO是代码的基础，它用于提供word的增、删、改、查。
@Dao
public interface WordDao {
    @Insert
    void insert(Word word);

    @Delete
    void delete(Word word);

    @Query("DELETE FROM word_tab")
    void deleteAll();

    @Query("SELECT * from word_tab ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();

}
