package com.wdz.module_architecture.mvvm;

import android.view.View;

public class LoginModel {
    public LoginInfo loginInfo = new LoginInfo("","");
    private OnLoginListener onLoginListener;
    public interface OnLoginListener{
        void onLoginSuccess();
        void onLoginFail();
    }
    public LoginModel(OnLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    public void login(View view){
        if (loginInfo.getUser().equals("wdz") && loginInfo.getPwd().equals("111")){
            onLoginListener.onLoginSuccess();
        }
        else{
            onLoginListener.onLoginFail();
        }
    }
}
