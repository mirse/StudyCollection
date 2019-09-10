package com.wdz.studycollection.mvp.logintest.presenter;

import android.text.TextUtils;

import com.wdz.studycollection.mvp.logintest.model.User;
import com.wdz.studycollection.mvp.logintest.view.BaseView;

/**
 * Created by dezhi.wang on 2018/10/30.
 */

public class MvpPresenter implements BasePresenter {
    private BaseView baseView;

    public MvpPresenter(BaseView baseView) {
        attachView(baseView);
    }

    @Override
    public void attachView(BaseView view) {
        this.baseView=view;
    }

    @Override
    public void detachView() {
        baseView=null;
    }

    @Override
    public void login(User user) {
      //  baseView.showProgress();
        if (!TextUtils.isEmpty(user.getName())&&!TextUtils.isEmpty(user.getPassword())){
            if (user.getName().equals("123")&&user.getPassword().equals("123")){
                baseView.loginSuccess("登陆成功");
               // baseView.hideProgress();
            }else{
                baseView.loginFail("登陆失败");
              //  baseView.hideProgress();
            }
        }else{
            baseView.showToast("账号或密码为空");
          //  baseView.hideProgress();
        }
    }
}
