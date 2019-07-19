package com.example.dezhiwang.studycollection.Mvp.Test1.Presenter;

import com.example.dezhiwang.studycollection.Mvp.Test1.model.IMainModel;
import com.example.dezhiwang.studycollection.Mvp.Test1.model.ModelImpl;
import com.example.dezhiwang.studycollection.Mvp.Test1.view.DataView;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public class MainPresenter implements BasePresenter {
    private DataView dataView;
    private ModelImpl model;
    public MainPresenter(DataView dataView) {
        model=new ModelImpl();
        attahView(dataView);
    }

    @Override
    public void attahView(DataView dataView) {
        this.dataView=dataView;
    }

    @Override
    public void detachView() {
        if (this.dataView!=null) {
            this.dataView = null;
        }
    }

    @Override
    public void loadData() {
        dataView.showLoading();
        model.loadData(new IMainModel.onLoadCompleteListener() {
            @Override
            public void onComplete(String body) {
                dataView.showData(body);
            }
        });

    }
}
