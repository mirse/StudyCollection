package com.wdz.module_basis.basis;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = ARouterConstant.ACTIVITY_FRAGMENT)
public class FragmentActivity extends AppCompatActivity implements Fragment1.DataCallBack {
    private final String TAG = this.getClass().getSimpleName();

    private Fragment fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_basis_activity_fragment);
        ButterKnife.bind(this);
        fragment1 = Fragment1.getInstance("fragment1");

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int num = getSupportFragmentManager().getBackStackEntryCount();
                Log.i(TAG, "getBackStackEntryCount: "+num);
                for (int i = 0; i < num; i++) {
                    FragmentManager.BackStackEntry backStackEntryAt = getSupportFragmentManager().getBackStackEntryAt(i);
                    Log.i(TAG, "onBackStackChanged: "+backStackEntryAt.getName());
                }
            }
        });

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment1,"fragment1")
//                    .addToBackStack(Fragment1.class.getSimpleName())
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }

    }
    @OnClick(R2.id.bt_activity)
    public void onClick(View view){
        if (view.getId()==R.id.bt_activity){

        }
    }

    @Override
    public void dataCallBack(String s) {
        Log.i(TAG, "dataCallBack: 收到fragment2的回调了:"+s);
    }
}
