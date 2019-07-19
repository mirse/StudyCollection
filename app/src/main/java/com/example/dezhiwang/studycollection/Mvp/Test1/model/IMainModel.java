package com.example.dezhiwang.studycollection.Mvp.Test1.model;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public interface IMainModel {
    void loadData(onLoadCompleteListener onLoadCompleteListener);
    interface onLoadCompleteListener{
        void onComplete(String body);
    }
}
