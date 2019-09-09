package com.example.dezhiwang.studycollection.handler.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.dezhiwang.studycollection.BR;

public class Login extends BaseObservable {
    //如果是 public 修饰符，则可以直接在成员变量上方加上 @Bindable 注解
    @Bindable
    public String name;
    private String pwd;
    private String check;
    //如果是 private 修饰符，则在成员变量的 get 方法上添加 @Bindable 注解
    @Bindable
    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Login(String name, String pwd, String check) {
        this.name = name;
        this.pwd = pwd;
        this.check = check;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.example.dezhiwang.studycollection.BR.name);
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        //更新所有字段
        notifyChange();
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }
}
