package com.wdz.module_architecture.di;

import com.wdz.module_architecture.dagger.DaggerDemoActivity;
import com.wdz.module_architecture.dagger.fragment.MyFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = DaggerDemoModule.class)
    abstract DaggerDemoActivity bindDaggerDemoActivity();

    @ContributesAndroidInjector(modules = MyFragmentModule.class)
    abstract MyFragment bindMyFragment();
}
