package com.wdz.module_architecture.dagger.presenter;

import com.wdz.module_architecture.dagger.contract.MyFragmentContract;
import com.wdz.module_architecture.dagger.model.MyFragmentModel;

import javax.inject.Inject;

public class MyFragmentPresenter implements MyFragmentContract.Presenter {
    private MyFragmentContract.View view;

    @Inject
    MyFragmentModel myFragmentModel;

    public MyFragmentPresenter(MyFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void loadUser() {
        view.loadUserSuccess();
    }
}
