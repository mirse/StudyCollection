package com.wdz.module_architecture.di;

import android.content.Context;

import com.wdz.module_architecture.dagger.DaggerDemoActivity;
import com.wdz.module_architecture.dagger.User;
import com.wdz.module_architecture.dagger.contract.DaggerDemoContract;
import com.wdz.module_architecture.dagger.model.DaggerDemoModel;
import com.wdz.module_architecture.dagger.presenter.DaggerDemoPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerDemoModule{
    @Provides
    DaggerDemoContract.View provideDaggerDemoView(DaggerDemoActivity daggerDemoActivity){
        return daggerDemoActivity;
    }

    @Provides
    DaggerDemoPresenter provideDaggerDemoPresenter(DaggerDemoContract.View view, User user){
        return new DaggerDemoPresenter(view,user);
    }
    @Provides
    DaggerDemoModel provideDaggerDemoModel(){
        return new DaggerDemoModel();
    }

}
