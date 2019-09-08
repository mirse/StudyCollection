package com.example.dezhiwang.studycollection.mvp.test1.Presenter;

import com.example.dezhiwang.studycollection.mvp.test1.Token;
import com.example.dezhiwang.studycollection.mvp.test1.model.BaseModel;
import com.example.dezhiwang.studycollection.mvp.test1.model.DataModel;
import com.example.dezhiwang.studycollection.mvp.test1.view.DataView;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public class MainPresenter implements BasePresenter {
    private DataView dataView;
    public MainPresenter(DataView dataView) {
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
    public void loadData(String msg) {
        dataView.showLoading();

        DataModel
                .request(Token.API_USER_DATA)
                .params(msg)
                .execute(new BaseModel.onLoadCompleteListener<String>() {
            @Override
            public void onComplete(String body) {
                dataView.showData(body);
            }

            @Override
            public void onFailed(String msg) {
                dataView.showData(msg);
            }


        });
    }


    public void login(String[] msg){
        dataView.showLoading();
        DataModel.request(Token.API_LOGIN_DATA)
                .params(msg)
                .execute(new BaseModel.onLoadCompleteListener<String>() {
                    @Override
                    public void onComplete(String body) {
                        dataView.showData(body);
                    }

                    @Override
                    public void onFailed(String msg) {
                        dataView.showData(msg);
                    }
                });
    }
}
