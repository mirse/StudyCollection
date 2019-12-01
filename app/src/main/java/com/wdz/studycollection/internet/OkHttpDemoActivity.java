package com.wdz.studycollection.internet;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wdz.studycollection.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class OkHttpDemoActivity extends AppCompatActivity {
    private static final String TAG = "OkHttpDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_demo);
        ButterKnife.bind(this);

    }
    @OnClick({R.id.btn_get,R.id.btn_post})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_get:
                //异步get请求
                getData();
                break;
            case R.id.btn_post:
                //post
//                postData();
                postFile();
                break;
        }
    }

    private void getData() {
        String url = "http://www.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        //异步get请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    private void postData() {
        String url = "https://api.github.com/markdown/raw";
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "hello";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType,requestBody))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, "name:"+headers.name(i) + " value:" + headers.value(i));
                }
                Log.d(TAG,"body:"+response.body().string());
            }
        });
    }

    private void postFile(){
        MediaType parse = MediaType.parse("text/x-markdown; charset=utf-8");
        File file = new File("test.md");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(parse, file))
                .build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, "name:"+headers.name(i) + " value:" + headers.value(i));
                }
                Log.d(TAG,"body:"+response.body().string());
            }
        });

    }
}
