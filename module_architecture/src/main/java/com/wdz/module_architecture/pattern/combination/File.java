package com.wdz.module_architecture.pattern.combination;

/**
 * 抽象文件夹类
 */
public abstract class File{
    private String name;

    public File(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void display();
}
