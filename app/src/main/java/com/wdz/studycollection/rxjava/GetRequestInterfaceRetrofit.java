package com.wdz.studycollection.rxjava;

import com.wdz.studycollection.rxjava.bean.Translation;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;


public interface GetRequestInterfaceRetrofit {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Call<Translation> getCall();


    // 网络请求2
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<Translation> getCall_2();
}
