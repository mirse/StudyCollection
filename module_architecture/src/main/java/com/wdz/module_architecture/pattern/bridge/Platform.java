package com.wdz.module_architecture.pattern.bridge;

/**
 * 桥接模式：把对象之间的继承关系改成耦合关系
 *
 * 取代多层继承方案
 */
public abstract class Platform {
    protected Engineer engineer;
    public abstract void program();
}

abstract class Engineer{
    public abstract void getType();
}


class WindowPlatform extends Platform{
    private Engineer engineer;
    public WindowPlatform(Engineer engineer) {
        this.engineer = engineer;
    }

    @Override
    public void program() {
        engineer.getType();
        System.out.println("使用windows");
    }
}

class LinuxPlatform extends Platform{
    private Engineer engineer;
    public LinuxPlatform(Engineer engineer) {
        this.engineer = engineer;
    }

    @Override
    public void program() {
        engineer.getType();
        System.out.println("使用Linux");
    }
}

class AndroidEngineer extends Engineer{

    @Override
    public void getType() {
        System.out.println("android");
    }
}

class IosEngineer extends Engineer{

    @Override
    public void getType() {
        System.out.println("ios");
    }
}
