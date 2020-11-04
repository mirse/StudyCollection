package com.wdz.module_architecture.dagger.test.car;


import com.wdz.module_architecture.dagger.test.bike.BikeComponent;
import com.wdz.module_architecture.dagger.test.qualifier.CarScope;
import com.wdz.module_architecture.dagger.test.qualifier.ProvideCarName;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = BikeComponent.class)
public class CarModule {
    @Provides
    public Car getCar(){
        return new Car();
    }

    @Provides
    @ProvideCarName(name = "111")
    public Car getMyCar(){
        return new Car("豪华车");
    }

    @Provides
    @ProvideCarName(name = "222")
    @CarScope
    public Car provideCar(){
        return new Car("普通车");
    }
}



