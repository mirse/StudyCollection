package com.wdz.studycollection.internet.glide;

import android.content.Context;

public class MyButton {
    public MyButton(Context context) {
    }
    private msgListener mOnkeyValueListener; //定义监听接口，接受Main类传过来的监听对象

    //模拟用户触摸屏幕时系统底层进行的操作
    public void doClick() {
        mOnkeyValueListener.onMsgGet("点击了A"); //该方法可以在A类跟Main类中调用，都会将内容传给Main类。这句话就实现了监听的效果


    }

    public msgListener getmOnkeyValueListener() {
        return mOnkeyValueListener;
    }

    public void setmOnkeyValueListener(msgListener mOnkeyValueListener) {
        this.mOnkeyValueListener = mOnkeyValueListener; //Main类中调用该方法，传入监听对象
    }
}
