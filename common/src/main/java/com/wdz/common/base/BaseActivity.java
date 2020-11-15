package com.wdz.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;




import javax.inject.Inject;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    @Inject
    protected  P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        injectDagger();
        super.onCreate(savedInstanceState);


    }



}
