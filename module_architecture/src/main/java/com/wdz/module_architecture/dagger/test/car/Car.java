package com.wdz.module_architecture.dagger.test.car;

import com.wdz.module_architecture.dagger.test.qualifier.ProvideCarName;

import javax.inject.Inject;

public class Car {

    public String name;

    public Car() {

    }

    public Car(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                '}';
    }
}

