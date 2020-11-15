package com.wdz.module_architecture.dagger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.R2;
import com.wdz.module_architecture.dagger.fragment.MyFragment;






import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
* inject 注入
* component 注入器
* module 模块
*
*/

@Route(path = ARouterConstant.ACTIVITY_DAGGER)
public class DaggerDemoActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.cl_root)
    ConstraintLayout clRoot;


    @Inject
    User user;
    @Inject
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        ButterKnife.bind(this);

        Log.i(TAG, "onCreate: "+user);
//        Toast.makeText(this,user.toString(),Toast.LENGTH_SHORT).show();
//        tv.setText(user.toString());
//
//        clRoot.addView(tv);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.cl_root, MyFragment.getInstance(""),"f1")
                .commit();
    }


}
