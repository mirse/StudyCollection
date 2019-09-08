package com.example.dezhiwang.studycollection.mvp.writeandread.view;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public interface BaseView {
    void setUser(String msg);
    String getFirst();
    String getLast();
    int getInt();
    void setSelect(boolean is);
}
