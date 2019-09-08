package com.example.dezhiwang.studycollection.mvp.writeandread.model;

import android.util.SparseArray;

import com.example.dezhiwang.studycollection.mvp.writeandread.bean.UserName;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public class IUserModel implements UserModel {
    private String mFirstName;
    private String mLastName;
    private int mInt;
    SparseArray<UserName> mlist=new SparseArray<UserName>();
    @Override
    public void setId(int id) {
        mInt=id;
    }

    @Override
    public void setFirst(String first) {
        mFirstName=first;

    }

    @Override
    public void setLast(String last) {
        mLastName=last;
        UserName userName = new UserName(mFirstName, mLastName);
        mlist.append(mInt,userName);
    }

    @Override
    public UserName load(int id) {
        mInt=id;
        UserName userName = mlist.get(mInt,new UserName("null","null"));
        return userName;
    }
}
