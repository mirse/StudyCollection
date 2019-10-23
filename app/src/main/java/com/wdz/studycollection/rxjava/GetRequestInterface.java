package com.wdz.studycollection.rxjava;

import com.wdz.studycollection.rxjava.bean.Translation;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface GetRequestInterface {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Observable<Translation> getCall();


    // 网络请求2
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<Translation> getCall_2();
}
