package com.wdz.module_architecture.dagger.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wdz.module_architecture.R;
import com.wdz.module_architecture.dagger.User;
import com.wdz.module_architecture.dagger.contract.MyFragmentContract;
import com.wdz.module_architecture.dagger.presenter.MyFragmentPresenter;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class MyFragment extends DaggerFragment implements MyFragmentContract.View {
    private static final String TAG = "MainFragment";

    @Inject
    MyFragmentPresenter myFragmentPresenter;


    public static Fragment getInstance(String value){
        MyFragment fragment1 = new MyFragment();
        Bundle bundle = new Bundle();
        return fragment1;
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "context = " + context);
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        myFragmentPresenter.loadUser();
    }

    @Override
    public void loadUserSuccess() {
        Toast.makeText(getActivity(),"loadUserSuccess",Toast.LENGTH_SHORT).show();
    }
}
