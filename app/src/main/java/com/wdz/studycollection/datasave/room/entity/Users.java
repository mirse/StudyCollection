package com.wdz.studycollection.datasave.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("name")})
public class Users {
    @PrimaryKey
    private int id;
    private String name;
    private String identify;

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

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public Users(int id, String name, String identify) {
        this.id = id;
        this.name = name;
        this.identify = identify;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", identify='" + identify + '\'' +
                '}';
    }
}