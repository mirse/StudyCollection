package com.wdz.studycollection.main;

import android.app.Activity;

import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wdz.studycollection.main.dagger.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class MyApplication extends MultiDexApplication implements HasActivityInjector {
    public static MyApplication context;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        context = this;
        DaggerAppComponent.create().inject(this);
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openDebug();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openLog();
        }
        ARouter.init(this);
       
    }


    public static MyApplication getInstance(){
        if (context == null){
         context = new MyApplication();
        }
        return context;
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
