package com.wdz.studycollection.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wdz.studycollection.R;
import com.wdz.studycollection.eventbus.RxBus;
import com.wdz.studycollection.rxjava.bean.BaseResponse;
import com.wdz.studycollection.rxjava.bean.Translation;
import com.wdz.studycollection.rxjava.bean.TranslationEnToCh;
import com.wdz.studycollection.rxjava.http.ApiService;
import com.wdz.studycollection.rxjava.http.BaseObserver;
import com.wdz.studycollection.rxjava.http.RetrofitFactory;

public class RxDemoActivity extends AppCompatActivity {
    private static final String TAG = "RxDemoActivity";
    private Disposable mDisposable;
    @SuppressLint("CheckResult")

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.list)
    Button mBtnList;
    @BindView(R.id.ed)
    EditText mEd;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.bt_start)
    Button mBtnStart;
    @BindView(R.id.result)
    TextView mTvResult;

    private int maxCount = 10;
    private int currnetCount = 0;
    private int waitRetryTime = 0;
    private int i=0;
    private RetrofitFactory retrofitFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_demo);
        ButterKnife.bind(this);
        retrofitFactory = RetrofitFactory.getInstance();
    }
    @OnClick(R.id.bt_start)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_start:

                String word = mEd.getText().toString();

                if (word.equals("")){
                    word = "love";
                }

                en2Ch(word);
                //EventBus.getDefault().post(new MessageEvent("eventbus 回调了"));
                //EventBus.getDefault().postSticky(new MessageEvent("sticky eventbus 回调"));
                RxBus.getInstance().post("I am Rxbus");
                break;
            default:
                break;
        }
    }

    private void en2Ch(String word){
        retrofitFactory.eng2Chinese(word,new BaseObserver<BaseResponse<TranslationEnToCh>>() {


            @Override
            protected void onSuccess(BaseResponse tBaseResponse) {
                TranslationEnToCh translationEnToCh = (TranslationEnToCh) tBaseResponse.getContent();
                mTvResult.setText(translationEnToCh.getWord_mean().get(0));
                Log.i(TAG, "onSuccess: "+translationEnToCh.getWord_mean().get(0));
            }

            @Override
            protected void onFailure(BaseResponse tBaseResponse) {
                TranslationEnToCh translationEnToCh = (TranslationEnToCh) tBaseResponse.getContent();
                mTvResult.setText(translationEnToCh.getOut());
                Log.i(TAG, "onFailure: ");
            }


        });
    }
//    /**
//     * eng -> chinese
//     */
//    private void request() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://fy.iciba.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiService getRequestInterfaceRetrofit = retrofit.create(ApiService.class);
//        Call<Translation> call = getRequestInterfaceRetrofit.getCall();
//        call.enqueue(new Callback<Translation>() {
//            @Override
//            public void onResponse(Call<Translation> call, Response<Translation> response) {
//                Log.i(TAG,"response:"+response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<Translation> call, Throwable t) {
//                Log.i(TAG,"failed");
//            }
//        });
//    }
}
