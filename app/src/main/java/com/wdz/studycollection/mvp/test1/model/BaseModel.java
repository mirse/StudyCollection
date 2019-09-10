package com.wdz.studycollection.mvp.test1.model;

/**
 * Created by dezhi.wang on 2018/10/31.
 */
//
//public interface BaseModel {
//
//
//
//
//    void loadData(String msg,onLoadCompleteListener onLoadCompleteListener);
//
//
//
//
//    interface onLoadCompleteListener{
//        void onComplete(String body);
//        void onFailed(String msg);
//    }
//}

public abstract class BaseModel<T>{
    //数据请求参数
    protected String[] mParams;

    /**
     * 设置数据请求参数
     * @param args 参数数组
     */
    public  BaseModel params(String... args){
        mParams = args;
        return this;
    }

    public abstract void execute(onLoadCompleteListener<T> onLoadCompleteListener);

    public interface onLoadCompleteListener<T>{
        void onComplete(T body);
        void onFailed(T msg);
    }
}
