package com.wdz.studycollection.main.di.component;

import android.app.Application;

import com.wdz.module_architecture.di.ActivityModule;
import com.wdz.studycollection.main.MyApplication;
import com.wdz.studycollection.main.di.module.AppModule;


import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,AppModule.class, ActivityModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }


    void inject(MyApplication myApplication);

}
