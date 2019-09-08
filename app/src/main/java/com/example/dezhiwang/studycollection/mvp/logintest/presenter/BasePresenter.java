package com.example.dezhiwang.studycollection.mvp.logintest.presenter;

import com.example.dezhiwang.studycollection.mvp.logintest.model.User;
import com.example.dezhiwang.studycollection.mvp.logintest.view.BaseView;

/**
 * Created by dezhi.wang on 2018/10/30.
 */

public interface BasePresenter {
    void attachView(BaseView view);
    void detachView();
    void login(User user);
}
