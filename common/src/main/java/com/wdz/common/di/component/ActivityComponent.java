package com.wdz.common.di.component;


import com.wdz.common.di.module.ActivityModule;


import dagger.Component;



@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
@ActivityScope
public interface ActivityComponent {

}
