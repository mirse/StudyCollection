package com.example.dezhiwang.studycollection.Mvp.Test1.model;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public class ModelImpl implements IMainModel {
    @Override
    public void loadData(onLoadCompleteListener onLoadCompleteListener) {
        String data="从网络中加载数据";
        onLoadCompleteListener.onComplete(data);
    }
}
