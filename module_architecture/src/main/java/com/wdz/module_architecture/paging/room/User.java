package com.wdz.module_architecture.paging.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author dezhi.wang
 * @Date 2021/1/9 8:57
 */
@Entity
public class User {
    @PrimaryKey
    public int userId;
    public String userName;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
