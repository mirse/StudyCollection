package com.wdz.common.di.module;

import android.app.Application;

import com.wdz.common.MyApplication;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private MyApplication application;

    public AppModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    MyApplication provideMyApplication(){
        return application;
    }
}
