package com.wdz.module_architecture.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    User provideStudent() {
        return new User();
    }
}
