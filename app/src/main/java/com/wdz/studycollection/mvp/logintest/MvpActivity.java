package com.wdz.studycollection.mvp.logintest;

import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wdz.studycollection.R;
import com.wdz.studycollection.mvp.logintest.model.User;
import com.wdz.studycollection.mvp.logintest.presenter.MvpPresenter;
import com.wdz.studycollection.mvp.logintest.view.BaseView;

public class MvpActivity extends AppCompatActivity implements BaseView{


    private MvpPresenter mvpPresenter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        final EditText mUser = findViewById(R.id.et_user);
        final EditText mPw = findViewById(R.id.et_pw);
        Button mLogin = findViewById(R.id.bt_login);
        mProgressBar = findViewById(R.id.progressBar);
        mvpPresenter = new MvpPresenter(this);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        User user = new User(mUser.getText().toString().trim(), mPw.getText().toString().trim());
                        mvpPresenter.login(user);
                    }
                },1000);

            }
        });
    }

    @Override
    protected void onDestroy() {
        mvpPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(String msg) {
        showToast(msg);
    }

    @Override
    public void loginFail(String msg) {
        showToast(msg);

    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}