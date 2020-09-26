package com.wdz.module_architecture.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wdz.module_architecture.R;
import com.wdz.module_architecture.R2;
import com.wdz.module_architecture.eventbus.RxBus;
import com.wdz.module_architecture.rxjava.bean.BaseResponse;
import com.wdz.module_architecture.rxjava.bean.TranslationEnToCh;
import com.wdz.module_architecture.rxjava.http.BaseObserver;
import com.wdz.module_architecture.rxjava.http.RetrofitFactory;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class RxDemoActivity extends AppCompatActivity {
    private static final String TAG = "RxDemoActivity";
    private Disposable mDisposable;
    @SuppressLint("CheckResult")

    @BindView(R2.id.ed)
    EditText mEd;
    @BindView(R2.id.bt_start)
    Button mBtnStart;
    @BindView(R2.id.bt_interval)
    Button mBtnInterval;
    @BindView(R2.id.result)
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
    @OnClick({R2.id.bt_start,R2.id.bt_interval})
    public void onClick(View view){
        String word = mEd.getText().toString();
        if (word.equals("")){
            word = "love";
        }
        int id = view.getId();
        if (id == R.id.bt_start) {
            en2Ch(word);
            RxBus.getInstance().post("I am Rxbus");
        } else if (id == R.id.bt_interval) {
            en2ChByInterval(word);
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
