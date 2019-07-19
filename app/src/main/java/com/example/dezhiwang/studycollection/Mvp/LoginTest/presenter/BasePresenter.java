package com.example.dezhiwang.studycollection.Mvp.LoginTest.presenter;

import com.example.dezhiwang.studycollection.Mvp.LoginTest.model.User;
import com.example.dezhiwang.studycollection.Mvp.LoginTest.view.BaseView;

/**
 * Created by dezhi.wang on 2018/10/30.
 */

public interface BasePresenter {
    void attachView(BaseView view);
    void detachView();
    void login(User user);
}
