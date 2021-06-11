package com.wdz.module_architecture.pattern.factory.abstractF;


import com.wdz.module_architecture.pattern.factory.bean.color.AbstractColor;
import com.wdz.module_architecture.pattern.factory.bean.shape.AbstractShape;


/*
 * 抽象工厂模式
 *
 * 优点：
 * 1、使用时只需关注所需产品对应的工厂，不需要关心创建细节
 * 2、加入新产品时无需修改抽象工厂和抽象产品提供的接口、客户端，符合开闭原则
 *
 * 缺点：
 * 1、增加新的产品等级结构，会对新的系统产生较大的修改，不符合开闭原则
 */
public abstract class AbstractFactory {
    public abstract AbstractShape getShape();
    public abstract AbstractColor getColor();
}


