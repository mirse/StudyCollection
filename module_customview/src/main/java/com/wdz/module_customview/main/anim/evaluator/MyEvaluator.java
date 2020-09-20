package com.wdz.module_customview.main.anim.evaluator;

import android.animation.TypeEvaluator;


/*
 * 自定义估值器
 * */
public class MyEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction*(endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction*(endPoint.getY() - startPoint.getY());
        Point point = new Point(x, y);
        return point;
    }
}