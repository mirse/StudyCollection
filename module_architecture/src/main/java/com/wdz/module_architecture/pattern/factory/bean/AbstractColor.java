package com.wdz.module_architecture.pattern.factory.bean;

public abstract class AbstractColor {
    public abstract String showColor();
}

class RedColor extends AbstractColor{

    @Override
    public String showColor() {
        return "red";
    }
}
class BlueColor extends AbstractColor{

    @Override
    public String showColor() {
        return "blue";
    }
}
