package com.wdz.studycollection;

import android.app.Application;

public class MyApplication extends Application{
    public static MyApplication context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public static MyApplication getInstance(){
        if (context == null){
         context = new MyApplication();
        }
        return context;
    }
}
