package com.wdz.module_architecture.dagger;

import android.app.Activity;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents =  DaggerDemoSubcomponent.class)
public abstract class DaggerDemoModule {
    @Binds
    @IntoMap
    @ActivityKey(DaggerDemoActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindDaggerDemoInjectorFactory(DaggerDemoSubcomponent.Builder builder);

}
