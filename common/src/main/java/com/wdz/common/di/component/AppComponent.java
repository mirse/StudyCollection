package com.wdz.common.di.component;

import com.wdz.common.MyApplication;
import com.wdz.common.di.module.AppModule;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MyApplication myApplication);

}
