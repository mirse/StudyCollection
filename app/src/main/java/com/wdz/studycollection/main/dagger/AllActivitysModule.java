package com.wdz.studycollection.main.dagger;

import com.wdz.module_architecture.dagger.DaggerAndroidModule;
import com.wdz.module_architecture.dagger.DaggerDemoActivity;
import com.wdz.module_architecture.dagger.DaggerDemoSubComponent;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = DaggerDemoSubComponent.class)
public abstract class AllActivitysModule {
    @ContributesAndroidInjector(modules = DaggerAndroidModule.class)
    abstract DaggerDemoActivity contributeDaggerDemoActivityInject();
}
