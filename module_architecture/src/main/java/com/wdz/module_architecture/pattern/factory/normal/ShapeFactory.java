package com.wdz.module_architecture.pattern.factory.normal;

import com.wdz.module_architecture.pattern.factory.bean.shape.AbstractShape;

public abstract class ShapeFactory {
    public abstract AbstractShape createShape();
}
