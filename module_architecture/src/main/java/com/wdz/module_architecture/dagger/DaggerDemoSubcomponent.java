package com.wdz.module_architecture.dagger;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Subcomponent(modules = {AndroidSupportInjectionModule.class})
public interface DaggerDemoSubcomponent extends AndroidInjector<DaggerDemoActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DaggerDemoActivity>{

    }


    @Module
    class SubModule{

        @Provides
        String provideName(){
            return DaggerDemoActivity.class.getName();
        }
    }
}
