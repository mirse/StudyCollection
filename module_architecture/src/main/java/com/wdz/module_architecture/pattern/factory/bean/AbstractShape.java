package com.wdz.module_architecture.pattern.factory.bean;

public abstract class AbstractShape {
    public abstract String showShape();
}

class CircleShape extends AbstractShape{

    @Override
    public String showShape() {
        return "circle";
    }
}

class RectangleShape extends AbstractShape{

    @Override
    public String showShape() {
        return "rectangle";
    }
}
