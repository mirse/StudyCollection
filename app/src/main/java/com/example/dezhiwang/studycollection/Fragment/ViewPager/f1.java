package com.example.dezhiwang.studycollection.Fragment.ViewPager;



import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dezhiwang.studycollection.R;


public class f1 extends LazyFragment {
    public static String PARAM = "param_key";


    @Override
    protected void initData() {
      Toast.makeText(getContext(),"loading... f1",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int onLayoutRes() {
        return R.layout.fragment_1;
    }

    @Override
    protected void initView(View view) {
        TextView textView = view.findViewById(R.id.textView6);
    }


}
