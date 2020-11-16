package com.wdz.module_architecture.di;

import com.wdz.module_architecture.dagger.contract.MyFragmentContract;
import com.wdz.module_architecture.dagger.fragment.MyFragment;
import com.wdz.module_architecture.dagger.presenter.MyFragmentPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MyFragmentModule {
    @Provides
    MyFragmentContract.View provideDaggerDemoView(MyFragment myFragment){
        return myFragment;
    }

    @Provides
    MyFragmentPresenter provideDaggerDemoPresenter(MyFragmentContract.View view){
        return new MyFragmentPresenter(view);
    }
}
