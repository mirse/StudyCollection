package com.wdz.studycollection.datasave.room.entity;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class UsersAll {
    //内嵌对象
    @Embedded
    public Users users;
    @Relation(parentColumn = "id",entityColumn = "childId")
    public List<UsersChild> usersChildren;

    @Override
    public String toString() {
        return "UsersAll{" +
                "users=" + users +
                ", usersChildren=" + usersChildren +
                '}';
    }
}
