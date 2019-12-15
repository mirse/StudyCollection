package com.wdz.studycollection.rxjava;

import com.wdz.studycollection.rxjava.bean.Translation;
import com.wdz.studycollection.rxjava.bean.TranslationEnToCh;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface GetRequestInterfaceRetrofit {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Call<Translation> getCall();


    // 网络请求2
    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<TranslationEnToCh> getCall_2(@Field("i") String targetSentence);
}
