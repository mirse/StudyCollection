package com.wdz.module_architecture.dagger;

import android.app.Activity;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = DaggerDemoSubComponent.class)
public abstract class DaggerAndroidModule {
    @Binds
    @IntoMap
    @ActivityKey(DaggerDemoActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindDaggerAndroidActivityInjectorFactory(DaggerDemoSubComponent.Builder builder);

    @Provides
    @Singleton
    static User provideStudent() {
        return new User();
    }
}
