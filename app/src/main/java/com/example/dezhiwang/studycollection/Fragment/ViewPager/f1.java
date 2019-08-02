package com.example.dezhiwang.studycollection.Fragment.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
