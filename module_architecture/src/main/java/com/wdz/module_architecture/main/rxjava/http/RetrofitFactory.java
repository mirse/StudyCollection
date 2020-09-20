package com.wdz.module_architecture.main.rxjava.http;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wdz.module_architecture.main.rxjava.bean.BaseResponse;
import com.wdz.module_architecture.main.rxjava.bean.TranslationEnToCh;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitFactory {
    private static final String TAG = "RetrofitFactory";
    private static RetrofitFactory retrofitFactory;
    private final ApiService apiService;

    public static RetrofitFactory getInstance(){
        if (retrofitFactory==null){
            synchronized (RetrofitFactory.class){
                if (retrofitFactory==null){
                    retrofitFactory = new RetrofitFactory();
                }
            }
        }
        return retrofitFactory;
    }

    private RetrofitFactory() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(ApiService.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(ApiService.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(ApiService.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }
    private ApiService getApi(){
        return apiService;
    }

    private ObservableTransformer observableTransformer(){
        return new ObservableTransformer(){
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public void eng2Chinese(String word,BaseObserver<BaseResponse<TranslationEnToCh>> baseResponseBaseObserver){
        getApi().en2ch(word)
                .compose(observableTransformer())
                .subscribe(baseResponseBaseObserver);
    }

    public void eng2ChineseByInterval(final String word, final BaseObserver<BaseResponse<TranslationEnToCh>> baseResponseBaseObserver){
        Observable.interval(2,1,TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "accept: "+aLong);
                        getApi().en2ch(word)
                                .compose(observableTransformer())
                                .subscribe(baseResponseBaseObserver);

                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void eng2ChineseByConditional(String word,BaseObserver<BaseResponse<TranslationEnToCh>> baseResponseBaseObserver){

    }


}
