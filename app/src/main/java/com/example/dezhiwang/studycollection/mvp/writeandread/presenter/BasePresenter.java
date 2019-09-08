package com.example.dezhiwang.studycollection.mvp.writeandread.presenter;

import com.example.dezhiwang.studycollection.mvp.writeandread.view.BaseView;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public interface BasePresenter {
    void attachView(BaseView baseView);
    void detachView();
    void savaData(int id,String firstName,String lastName);
    void loadData(int id);
    void setSelected(boolean is);
}
