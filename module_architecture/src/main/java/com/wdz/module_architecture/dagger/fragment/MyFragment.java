package com.wdz.module_architecture.dagger.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wdz.module_architecture.R;
import com.wdz.module_architecture.dagger.User;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class MyFragment extends Fragment {
    private static final String TAG = "MainFragment";

    @Inject
    User mFragmentUser;


    public static Fragment getInstance(String value){
        MyFragment fragment1 = new MyFragment();
        Bundle bundle = new Bundle();
        return fragment1;
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "context = " + context);
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "mFragmentUser = " + mFragmentUser);
    }
}
