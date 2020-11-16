package com.wdz.module_architecture.dagger.model;

import android.content.Context;

import com.wdz.module_architecture.dagger.contract.DaggerDemoContract;

public class DaggerDemoModel implements DaggerDemoContract.Model {

    private Context context;

    public DaggerDemoModel() {

    }

    @Override
    public void loadData() {

    }
}
