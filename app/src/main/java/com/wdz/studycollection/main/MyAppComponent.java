package com.wdz.studycollection.main;

import com.wdz.module_architecture.dagger.DaggerDemoModule;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {AndroidInjectionModule.class, AndroidSupportInjectionModule.class,
                        DaggerDemoModule.class})
public interface MyAppComponent {
    void inject(MyApplication myApplication);
}
