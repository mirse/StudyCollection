package com.wdz.module_architecture.dagger.test.bike;

import com.wdz.module_architecture.dagger.test.car.Car;

import dagger.Module;
import dagger.Provides;

@Module
public class BikeModule {
    @Provides
    public Bike getBike(Car car){
        return new Bike(car);
    }
}
