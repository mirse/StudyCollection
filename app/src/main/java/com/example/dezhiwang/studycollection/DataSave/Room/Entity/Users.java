package com.example.dezhiwang.studycollection.DataSave.Room.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Users {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "release_year")
    private int releaseYear;
    // getters and setters are ignored for brevity but they are required for Room to work.


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Users(int id, String name, int releaseYear) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
    }
}