package com.wdz.studycollection;

import android.app.Application;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {
    public static MyApplication context;
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        context = this;
    }
    public static MyApplication getInstance(){
        if (context == null){
         context = new MyApplication();
        }
        return context;
    }
}
