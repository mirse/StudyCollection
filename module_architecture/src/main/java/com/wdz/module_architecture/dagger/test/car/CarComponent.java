package com.wdz.module_architecture.dagger.test.car;

import com.wdz.module_architecture.dagger.DaggerDemoActivity;
import com.wdz.module_architecture.dagger.test.bike.BikeComponent;
import com.wdz.module_architecture.dagger.test.qualifier.CarScope;

import dagger.Component;

@Component(modules = CarModule.class)
@CarScope
public interface CarComponent {
//    void inject(DaggerDemoActivity daggerDemoActivity);
//    Car offerCar();
    BikeComponent.Builder buildBikeComponent();
}
