package com.wdz.module_architecture.pattern.factory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.pattern.factory.abstractF.BlueCircleFactory;

/*
 *  简单工厂模式：一个抽象产品类派生多个具体类。一个具体工厂类，生产出不同具体产品类的实例
 *
 *  工厂模式
 *
 *  抽象工厂模式：生产（【多个产品】组合）的对象时
 *
 * AbstractColor - BlueColor
 *               - RedColor
 *
 * AbstractShape - CircleShape
 *               - RectangleShape
 *
 * AbstractFactory - BlueCircleFactory 抽象工厂 组合BlueColor和CircleShape
 *
 *
 */
@Route(path = ARouterConstant.ACTIVITY_FACTORY_DEMO)
public class FactoryActivity extends AppCompatActivity {
    private static final String TAG = "FactoryActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);
        BlueCircleFactory blueCircleFactory = new BlueCircleFactory();
        Log.i(TAG, "onCreate:Color:"+ blueCircleFactory.getColor().showColor()+" shape:"+blueCircleFactory.getShape().showShape());
    }
}