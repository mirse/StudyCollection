package com.wdz.module_architecture.dagger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.base.BaseActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.R2;
import com.wdz.module_architecture.dagger.contract.DaggerDemoContract;
import com.wdz.module_architecture.dagger.fragment.MyFragment;
import com.wdz.module_architecture.dagger.presenter.DaggerDemoPresenter;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;


/*
* inject 注入
* component 注入器
* module 模块
*
*/

@Route(path = ARouterConstant.ACTIVITY_DAGGER)
public class DaggerDemoActivity extends DaggerAppCompatActivity implements DaggerDemoContract.View {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.cl_root)
    ConstraintLayout clRoot;


    @Inject
    DaggerDemoPresenter daggerDemoPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        ButterKnife.bind(this);

        Log.i(TAG, "onCreate: "+daggerDemoPresenter);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.cl_root, MyFragment.getInstance(""),"f1")
                .commit();

        daggerDemoPresenter.loadData();
    }


    @Override
    public void loadSuccess(User user) {
        Toast.makeText(this,"success"+user
                ,Toast.LENGTH_SHORT).show();
    }
}
