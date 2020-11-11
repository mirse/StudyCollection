package com.wdz.module_architecture.dagger;

import com.wdz.module_architecture.dagger.fragment.MyFragmentModule;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {UserModule.class, MyFragmentModule.class})
public interface DaggerDemoSubComponent extends AndroidInjector<DaggerDemoActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DaggerDemoActivity>{

    }
}
