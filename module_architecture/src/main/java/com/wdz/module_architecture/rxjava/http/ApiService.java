package com.wdz.module_architecture.rxjava.http;



import com.wdz.module_architecture.rxjava.bean.BaseResponse;
import com.wdz.module_architecture.rxjava.bean.TranslationEnToCh;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiService {

    String baseUrl="";
    int HTTP_TIME_OUT = 0;


    @FormUrlEncoded
    @POST("ajax.php?a=fy&f=auto&t=auto")
    Observable<BaseResponse<TranslationEnToCh>> en2ch(@Field("w") String context);


    // 网络请求2
    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Observable<TranslationEnToCh> getCall_2(@Field("i") String targetSentence);
}
