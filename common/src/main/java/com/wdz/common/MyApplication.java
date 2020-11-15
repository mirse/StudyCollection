package com.wdz.common;

import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wdz.common.di.component.AppComponent;


import com.wdz.common.di.component.DaggerAppComponent;
import com.wdz.common.di.module.AppModule;


public class MyApplication extends MultiDexApplication{
    public static MyApplication context;
    private AppComponent appcomponent;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        context = this;
        appcomponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openDebug();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openLog();
        }
        ARouter.init(this);
       
    }
    public AppComponent getAppcomponent(){
        return appcomponent;
    }


    public static MyApplication getInstance(){
        if (context == null){
         context = new MyApplication();
        }
        return context;
    }


}
