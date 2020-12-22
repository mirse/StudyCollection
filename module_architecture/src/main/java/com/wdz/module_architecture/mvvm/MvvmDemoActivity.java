package com.wdz.module_architecture.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.databinding.ActivityMvvmDemoBinding;

@Route(path = ARouterConstant.ACTIVITY_MVVMDEMO)
public class MvvmDemoActivity extends AppCompatActivity implements LoginModel.OnLoginListener {
    private static final String TAG = "MvvmDemoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMvvmDemoBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_demo);
        LoginModel loginModel = new LoginModel(this);
        viewDataBinding.setModel(loginModel);
    }

    @Override
    public void onLoginSuccess() {
        Log.i(TAG, "onLoginSuccess: ");
    }

    @Override
    public void onLoginFail() {
        Log.i(TAG, "onLoginFail: ");
    }
}