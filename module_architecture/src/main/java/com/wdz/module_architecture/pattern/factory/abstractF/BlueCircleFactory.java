package com.wdz.module_architecture.pattern.factory.abstractF;

import com.wdz.module_architecture.pattern.factory.bean.color.AbstractColor;
import com.wdz.module_architecture.pattern.factory.bean.color.BlueColor;
import com.wdz.module_architecture.pattern.factory.bean.shape.AbstractShape;
import com.wdz.module_architecture.pattern.factory.bean.shape.CircleShape;

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