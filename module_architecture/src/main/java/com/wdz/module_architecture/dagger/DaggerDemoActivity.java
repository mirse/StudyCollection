package com.wdz.module_architecture.dagger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.dagger.test.bike.Bike;





import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;

/*
* inject 注入
* component 注入器
* module 模块
*
*/

@Route(path = ARouterConstant.ACTIVITY_DAGGER)
public class DaggerDemoActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

//    @Inject
//    @ProvideCarName(name = "222")
//    Car car;

//    @Inject
//    Bike bike;
    @Inject
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);

//        CarComponent carComponent = DaggerCarComponent.create();
//        DaggerBikeComponent.builder().carComponent(carComponent).build().inject(this);

//
//        CarComponent carComponent = DaggerCarComponent.create();
//        carComponent.buildBikeComponent().builder().inject(this);
//
//
        Log.i(TAG, "onCreate: "+user);
//        Toast.makeText(this,bike.toString(),Toast.LENGTH_SHORT).show();

    }
}
