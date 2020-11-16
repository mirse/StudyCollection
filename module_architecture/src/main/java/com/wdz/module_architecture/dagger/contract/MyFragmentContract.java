package com.wdz.module_architecture.dagger.contract;

public interface MyFragmentContract {
    interface Model {
        void loadUser();
    }

    interface View {
        void loadUserSuccess();
    }

    interface Presenter {
        void loadUser();
    }
}
