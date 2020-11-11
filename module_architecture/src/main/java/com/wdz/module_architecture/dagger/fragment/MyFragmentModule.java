package com.wdz.module_architecture.dagger.fragment;

import com.wdz.module_architecture.dagger.DaggerDemoActivity;
import com.wdz.module_architecture.dagger.UserModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyFragmentModule {

    @ContributesAndroidInjector(modules = {UserModule.class})
    abstract MyFragment contributeDaggerDemoActivityInjector();


}
