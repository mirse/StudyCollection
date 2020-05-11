package com.wdz.studycollection.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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

import java.util.concurrent.TimeUnit;

public class RxDemoActivity extends AppCompatActivity {
    private static final String TAG = "RxDemoActivity";
    private Disposable mDisposable;
    @SuppressLint("CheckResult")

    @BindView(R.id.ed)
    EditText mEd;
    @BindView(R.id.bt_start)
    Button mBtnStart;
    @BindView(R.id.bt_interval)
    Button mBtnInterval;
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
    @OnClick({R.id.bt_start,R.id.bt_interval})
    public void onClick(View view){
        String word = mEd.getText().toString();
        if (word.equals("")){
            word = "love";
        }
        switch (view.getId()){
            case R.id.bt_start:
                en2Ch(word);
                RxBus.getInstance().post("I am Rxbus");
                break;
            case R.id.bt_interval:
                en2ChByInterval(word);
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

    private void en2ChByInterval(String word){




        retrofitFactory.eng2ChineseByInterval(word,new BaseObserver<BaseResponse<TranslationEnToCh>>() {
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

}
