package com.wdz.module_architecture.pattern.factory.normal;

import com.wdz.module_architecture.pattern.factory.bean.shape.AbstractShape;
import com.wdz.module_architecture.pattern.factory.bean.shape.CircleShape;

/*
 * 工厂模式：根据具体产品类型，构建对应产品线，生产对应产品
 *
 * 优点：
 * 1、使用时只需关注所需产品对应的工厂，不需要关心创建细节
 * 2、加入新产品时无需修改抽象工厂和抽象产品提供的接口、客户端，符合开闭原则
 *
 * 缺点：
 * 1、添加新产品时，需要编写新的具体类、具体工厂类，增加了代码
 *
 */
public class CircleShapeFactory extends ShapeFactory {
    @Override
    public AbstractShape createShape() {
        return new CircleShape();
    }
}
