package com.example.dezhiwang.studycollection.mvp.logintest.view;

/**
 * Created by dezhi.wang on 2018/10/30.
 */

public interface BaseView {
    void showToast(String msg);
    void loginSuccess(String msg);
    void loginFail(String msg);
    void showProgress();
    void hideProgress();

}
