package com.wdz.studycollection.datasave.room.entity;

import com.wdz.studycollection.mvp.logintest.model.User;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Users.class,parentColumns = "id",childColumns = "childId"))
public class UsersChild {
    @PrimaryKey
    private int childId;
    public String childName;
    public String childIdentify;

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildIdentify() {
        return childIdentify;
    }

    public void setChildIdentify(String childIdentify) {
        this.childIdentify = childIdentify;
    }

    public UsersChild(int childId, String childName, String childIdentify) {
        this.childId = childId;
        this.childName = childName;
        this.childIdentify = childIdentify;
    }
}
