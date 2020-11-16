package com.wdz.module_architecture.dagger.model;

import com.wdz.module_architecture.dagger.contract.MyFragmentContract;

import javax.inject.Inject;

public class MyFragmentModel implements MyFragmentContract.Model {

    @Inject
    public MyFragmentModel() {
    }

    @Override
    public void loadUser() {

    }
}
