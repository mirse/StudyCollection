package com.wdz.studycollection.datasave.room.entity;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class UsersAll {
    //内嵌对象
    @Embedded
    public Users users;
    @Relation(parentColumn = "id",entityColumn = "foreId")
    public List<UsersChild> usersChildren;
}
