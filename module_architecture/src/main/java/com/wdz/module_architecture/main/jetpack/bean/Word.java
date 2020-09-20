package com.wdz.module_architecture.main.jetpack.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity(tableName = "word_table") 每一个@Entity类代表数据库中的一张表。tableName为生成表的表名。
@Entity(tableName = "word_tab")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(String mWord) {
        this.mWord = mWord;
    }

    public String getWord() {
        return mWord;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mWord='" + mWord + '\'' +
                '}';
    }
}
