package com.example.dezhiwang.studycollection.Mvp.model;

import com.example.dezhiwang.studycollection.Mvp.presenter.ModelPresenter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by dezhi.wang on 2018/10/29.
 */

public class MvpModel {
    ModelPresenter modelPresenter;
    public MvpModel(ModelPresenter modelPresenter) {
        this.modelPresenter=modelPresenter;
    }
    public void loadData(){
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get("http://www.weather.com.cn/adat/sk/101010100.html",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                MvpBean mvpBean = new MvpBean();
                try {
                    JSONObject weatherinfo = response.getJSONObject("weatherinfo");
                    mvpBean.setCity(weatherinfo.getString("city"));
                    mvpBean.setWd(weatherinfo.getString("WD"));
                    mvpBean.setWs(weatherinfo.getString("WS"));
                    mvpBean.setTime(weatherinfo.getString("time"));
                    modelPresenter.loadSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
