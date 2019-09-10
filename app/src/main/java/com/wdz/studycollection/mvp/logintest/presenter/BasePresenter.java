package com.wdz.studycollection.mvp.logintest.presenter;

import com.wdz.studycollection.mvp.logintest.model.User;
import com.wdz.studycollection.mvp.logintest.view.BaseView;

/**
 * Created by dezhi.wang on 2018/10/30.
 */

public interface BasePresenter {
    void attachView(BaseView view);
    void detachView();
    void login(User user);
}
