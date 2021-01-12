package com.wdz.module_architecture.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author wdz
 * @param <V> View接口类型
 * @param <T> Presenter具体类型
 */
public abstract class MvpBaseActivity<V extends MvpBaseView,T extends BasePresenter<V>> extends AppCompatActivity implements MvpBaseView {

    private T mPresenter;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        //创建presenter
        mPresenter = createPresenter();
        //view与presenter创建关联
        mPresenter.attachView((V) this);
    }

    /**
     * 添加订阅
     * 配合RxView、RxTextView、RxCompoundButton相关
     * @param disposable
     */
    public void addDisposable(Disposable disposable){
        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable(){
        if (compositeDisposable!=null){
            compositeDisposable.clear();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearDisposable();
        mPresenter.detachView();
    }

    /**
     * @return
     */
    protected abstract T createPresenter();
}
