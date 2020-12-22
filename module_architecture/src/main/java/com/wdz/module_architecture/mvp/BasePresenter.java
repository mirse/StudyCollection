package com.wdz.module_architecture.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @author wdz
 * @param <T> View角色要实现的接口类型
 */
public abstract class BasePresenter<T> {
    /**
     * view接口的弱引用
     */
    protected Reference<T> mViewRef;

    /**
     * 与view建立关联
     * @param view
     */
    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);
    }

    /**
     * @return 获取view
     */
    protected T getView(){
        return mViewRef.get();
    }

    /**
     * @return 是否与view建立了关联
     */
    public boolean isViewAttach(){
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 与view解除关联
     */
    public void detachView(){
        if (mViewRef!=null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
