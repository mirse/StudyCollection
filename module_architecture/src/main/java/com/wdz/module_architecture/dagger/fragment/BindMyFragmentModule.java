package com.wdz.module_architecture.dagger.fragment;

import androidx.fragment.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module
public abstract class BindMyFragmentModule {
    @ContributesAndroidInjector(modules = MyFragmentModule.class)
    abstract MyFragment contributeMyFragmentInjector();
}
