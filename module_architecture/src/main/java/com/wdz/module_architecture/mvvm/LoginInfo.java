package com.wdz.module_architecture.mvvm;

import androidx.databinding.BaseObservable;

import com.wdz.module_architecture.rxjava.bean.BaseResponse;
import com.wdz.module_architecture.rxjava.http.BaseObserver;

import io.reactivex.annotations.NonNull;

public class LoginInfo extends BaseObservable {
    private String user;
    private String pwd;
    private boolean isLogin;

    public LoginInfo(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
