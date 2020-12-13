package com.wdz.studycollection.main;

import android.app.Activity;
import android.content.Context;

import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.bugly.crashreport.CrashReport;
import com.wdz.studycollection.main.di.component.DaggerAppComponent;


import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class MyApplication extends MultiDexApplication implements HasActivityInjector {
    public static MyApplication context;

    @Inject
    public DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        context = this;
        DaggerAppComponent.builder().application(this).build().inject(this);
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openDebug();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openLog();
        }
        ARouter.init(this);

        //bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), "d057558e8f", true);

    }

    public static MyApplication getInstance(){
        if (context == null){
         context = new MyApplication();
        }
        return context;
    }


    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
