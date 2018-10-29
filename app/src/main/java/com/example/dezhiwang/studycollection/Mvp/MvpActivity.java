package com.example.dezhiwang.studycollection.Mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dezhiwang.studycollection.Mvp.view.MvpView;
import com.example.dezhiwang.studycollection.R;

public class MvpActivity extends AppCompatActivity implements MvpView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
    }

    @Override
    public void showData() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
