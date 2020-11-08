package com.wdz.module_architecture.dagger.test.bike;

import com.wdz.module_architecture.dagger.DaggerDemoActivity;

import com.wdz.module_architecture.dagger.test.qualifier.BikeScope;

import javax.inject.Scope;

import dagger.Component;
import dagger.Subcomponent;

@Subcomponent(modules = BikeModule.class)
@BikeScope
public interface BikeComponent {
    void inject(DaggerDemoActivity daggerDemoActivity);

    @Subcomponent.Builder
    interface Builder{
        BikeComponent builder();
    }
}
