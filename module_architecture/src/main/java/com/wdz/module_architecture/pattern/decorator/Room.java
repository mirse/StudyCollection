package com.wdz.module_architecture.pattern.decorator;

public abstract class Room {
    /**
     * 装修方法
     */
    public abstract void fitment();
}

class NewRoom extends Room
{

    @Override
    public void fitment() {
        System.out.println("待装修");
    }
}
