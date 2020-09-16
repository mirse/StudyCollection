package com.wdz.module_customview.main;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;

@Route(path = ARouterConstant.ACTIVITY_LAYOUT_INFLATER)
public class LayoutInflaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_customview_activity_layout_inflater);
        LinearLayout container = findViewById(R.id.container);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout, null, false);
        container.addView(view);
        Log.i("inflate", "View:"+view);
    }
}
