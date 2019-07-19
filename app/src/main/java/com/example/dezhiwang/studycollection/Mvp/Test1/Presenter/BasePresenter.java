package com.example.dezhiwang.studycollection.Mvp.Test1.Presenter;

import com.example.dezhiwang.studycollection.Mvp.Test1.view.DataView;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public interface BasePresenter {
    void attahView(DataView dataView);
    void detachView();
    void loadData();

}
