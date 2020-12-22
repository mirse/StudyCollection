package com.wdz.module_architecture.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author wdz
 * @param <V> View接口类型
 * @param <T> Presenter具体类型
 */
public abstract class MvpBaseActivity<V extends MvpBaseView,T extends BasePresenter<V>> extends AppCompatActivity implements MvpBaseView {

    private T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建presenter
        mPresenter = createPresenter();
        //view与presenter创建关联
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /**
     * @return
     */
    protected abstract T createPresenter();
}
