package com.wdz.module_architecture.mvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.databinding.ActivityMvvmDemoBinding;
import com.wdz.module_architecture.paging.room.User;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterConstant.ACTIVITY_MVVMDEMO)
public class MvvmDemoActivity extends AppCompatActivity {
    private static final String TAG = "MvvmDemoActivity";

    private ActivityMvvmDemoBinding viewDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MvvmViewModel mvvmViewModel = new ViewModelProvider(this).get(MvvmViewModel.class);

        /*
        * dataBinding实现数据与视图的双向绑定
        */
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_demo);

        viewDataBinding.setModel(mvvmViewModel);
        mvvmViewModel.getUsers(this);


        mvvmViewModel.persons.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.i(TAG, "onChanged: "+users.toString());
            }
        });

        mvvmViewModel.loginStatus.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginStatus) {
                if (loginStatus){
                    Log.i(TAG, "onChanged: 登录成功");
                }
                else{
                    Log.i(TAG, "onChanged: 登录失败");
                }
            }
        });

    }



//    @Override
//    public void onLoginSuccess() {
//        Log.i(TAG, "onLoginSuccess: ");
//    }
//
//    @Override
//    public void onLoginFail() {
//        Log.i(TAG, "onLoginFail: ");
//    }
}