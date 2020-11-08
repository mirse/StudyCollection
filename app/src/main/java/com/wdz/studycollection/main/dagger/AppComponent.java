package com.wdz.studycollection.main.dagger;

import com.wdz.module_architecture.dagger.DaggerAndroidModule;
import com.wdz.studycollection.main.MyApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, DaggerAndroidModule.class})
public interface AppComponent {
    void inject(MyApplication myApplication);
}
