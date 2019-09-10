package com.wdz.studycollection.indicatorview;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdz.studycollection.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Created by dezhi.wang on 2018/9/29.
 */

public class Fragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment2, container, false);
        return view;
    }
}
