package com.wdz.module_basis.fragment.viewpager;



import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wdz.module_basis.R;


public class f3 extends LazyFragment {
    public static String PARAM = "param_key";





    @Override
    protected void initData() {
        Toast.makeText(getContext(),"loading... f3",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int onLayoutRes() {
        return R.layout.fragment_3;
    }

    @Override
    protected void initView(View view) {
        TextView textView = view.findViewById(R.id.textView6);
    }


}
