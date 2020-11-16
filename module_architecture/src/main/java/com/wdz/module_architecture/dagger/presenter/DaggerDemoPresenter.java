package com.wdz.module_architecture.dagger.presenter;

import android.util.Log;

import com.wdz.module_architecture.dagger.User;
import com.wdz.module_architecture.dagger.contract.DaggerDemoContract;
import com.wdz.module_architecture.dagger.model.DaggerDemoModel;

import javax.inject.Inject;

public class DaggerDemoPresenter implements DaggerDemoContract.Presenter {
    private final String TAG = this.getClass().getSimpleName();
    private DaggerDemoContract.View view;
    private User user;
    @Inject
    DaggerDemoModel daggerDemoModel;

    @Inject
    public DaggerDemoPresenter(DaggerDemoContract.View view, User user) {
        this.view = view;
        this.user = user;
    }

    @Override
    public void loadData() {
        Log.i(TAG, "loadData: "+daggerDemoModel);
        view.loadSuccess(user);
    }
}
