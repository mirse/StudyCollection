package com.wdz.studycollection.fragment.viewpager;



import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wdz.studycollection.R;


public class f2 extends LazyFragment {
    public static String PARAM = "param_key";






    @Override
    protected void initData() {
        Toast.makeText(getContext(),"loading... f2",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int onLayoutRes() {
        return R.layout.fragment_2;
    }

    @Override
    protected void initView(View view) {
        TextView textView = view.findViewById(R.id.textView6);
    }

}