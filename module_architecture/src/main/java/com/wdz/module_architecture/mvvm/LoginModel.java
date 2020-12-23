package com.wdz.module_architecture.mvvm;

import android.text.Editable;
import android.text.TextWatcher;
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

    public TextWatcher userTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            loginInfo.setUser(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public TextWatcher pwdTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            loginInfo.setPwd(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
