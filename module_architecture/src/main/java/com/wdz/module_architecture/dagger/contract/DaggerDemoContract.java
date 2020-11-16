package com.wdz.module_architecture.dagger.contract;

import com.wdz.module_architecture.dagger.User;

public interface DaggerDemoContract {
    interface Model {
        void loadData();
    }

    interface View {
        void loadSuccess(User user);
    }

    interface Presenter {
        void loadData();
    }
}
