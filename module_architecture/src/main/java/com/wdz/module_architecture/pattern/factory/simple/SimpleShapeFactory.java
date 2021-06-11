package com.wdz.module_architecture.pattern.factory.simple;

import com.wdz.module_architecture.pattern.factory.bean.shape.CircleShape;
import com.wdz.module_architecture.pattern.factory.bean.shape.RectangleShape;

/*
 * 简单工厂模式:省去生成对象的new操作，调用工厂类的静态方法直接获取实例
 * 优点：
 * 1、将对象的创建和使用进行分离
 * 缺点：
 * 2、若添加新产品需要修改工厂类的逻辑，不符合开闭原则
 */
public class SimpleShapeFactory {
    //获取Circle
    public static CircleShape getCircle(){
        return new CircleShape();
    }
    //获取Rectangle
    public static RectangleShape getRectangle(){
        return new RectangleShape();
    }
}
