package com.wdz.module_architecture.dagger;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = UserModule.class)
public interface DaggerDemoSubComponent extends AndroidInjector<DaggerDemoActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DaggerDemoActivity>{

    }
}
