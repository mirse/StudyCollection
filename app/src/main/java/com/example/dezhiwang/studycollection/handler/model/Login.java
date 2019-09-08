package com.example.dezhiwang.studycollection.handler.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.dezhiwang.studycollection.BR;

public class Login extends BaseObservable {
    @Bindable
    public String name;
    private String pwd;
    private String check;

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
        notifyChange();
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }
}
