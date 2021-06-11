package com.wdz.module_architecture.pattern.factory.bean;

public class BlueCircleFactory extends AbstractFactory{

    @Override
    public AbstractShape getShape() {
        return new CircleShape();
    }

    @Override
    public AbstractColor getColor() {
        return new BlueColor();
    }
}