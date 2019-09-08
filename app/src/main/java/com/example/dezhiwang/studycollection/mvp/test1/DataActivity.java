package com.example.dezhiwang.studycollection.mvp.test1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dezhiwang.studycollection.mvp.test1.Presenter.MainPresenter;
import com.example.dezhiwang.studycollection.mvp.test1.view.DataView;
import com.example.dezhiwang.studycollection.R;

public class DataActivity extends AppCompatActivity implements DataView{

    private TextView mTvData;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        mainPresenter = new MainPresenter(this);
        initView();


    }

    private void initView() {
        Button mBtnGetSuccess = findViewById(R.id.bt_get_success);
        Button mBtnFail = findViewById(R.id.bt_get_fail);
        Button mBtnLogin = findViewById(R.id.bt_login);
        mTvData = findViewById(R.id.tv_data);

        mBtnGetSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.loadData("success");
            }
        });

        mBtnFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.loadData("failed");
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.login(new String[]{"wdz","1234"});
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    public void showData(String data) {
        mTvData.setText(data);
    }

    @Override
    public void showLoading() {
        Toast.makeText(this,"加载中......",Toast.LENGTH_SHORT).show();
    }
}
