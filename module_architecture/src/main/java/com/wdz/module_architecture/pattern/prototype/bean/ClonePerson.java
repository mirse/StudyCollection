package com.wdz.module_architecture.pattern.prototype.bean;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ClonePerson implements Cloneable {
    private String name;
    private ArrayList<String> listStr = new ArrayList<>();



    /*
    * 浅拷贝：拷贝基本数据类型（byte、char、short、int、long、float、double、boolean），只是复制指向某个对象的指针
    * 深拷贝：除基本数据类型外，会创造一个一模一样的对象
    *
    */

    @NonNull
    @Override
    public Object clone() {
        ClonePerson clonePerson = null;
        try {
            clonePerson= (ClonePerson) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        clonePerson.name = this.name;
        //clonePerson.listStr = this.listStr; //数组（引用）
        clonePerson.listStr = (ArrayList<String>) this.listStr.clone();//调用数组clone,深拷贝
        return clonePerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getListStr() {
        return listStr;
    }

    public void setListStr(ArrayList<String> listStr) {
        this.listStr = listStr;
    }

    @Override
    public String toString() {
        return "ClonePerson{" +
                "name='" + name + '\'' +
                ", listStr=" + listStr +
                '}';
    }
}
