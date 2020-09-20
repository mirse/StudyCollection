package com.wdz.module_architecture.main.rxjava.http;

import android.util.Log;

import com.wdz.module_architecture.main.rxjava.bean.BaseResponse;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseResponse<T> tBaseResponse) {
        Log.i(TAG, "onNext: "+tBaseResponse.toString());
        if (tBaseResponse.getStatus()==0){
            onSuccess(tBaseResponse);
        }
        else{
            onFailure(tBaseResponse);
        }
    }

    protected abstract void onSuccess(BaseResponse tBaseResponse);

    protected abstract void onFailure(BaseResponse tBaseResponse);

    @Override
    public void onError(Throwable e) {
        Log.i(TAG, "onError: "+e.toString());
        onFailure(null);
    }

    @Override
    public void onComplete() {

    }
}
