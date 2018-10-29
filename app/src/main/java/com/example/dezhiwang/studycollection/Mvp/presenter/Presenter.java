package com.example.dezhiwang.studycollection.Mvp.presenter;

/**
 * Created by dezhi.wang on 2018/10/29.
 */

public interface Presenter<V> {
    void attachView(V view);
    void detachView();
}
