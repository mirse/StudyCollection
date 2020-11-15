package com.wdz.common.di.module;

import com.wdz.common.base.BaseActivity;



import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }
    @Provides

    public BaseActivity provideBindActivity(){
        return baseActivity;
    }
}
