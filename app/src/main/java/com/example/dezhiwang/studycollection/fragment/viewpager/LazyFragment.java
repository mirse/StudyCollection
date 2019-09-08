package com.example.dezhiwang.studycollection.fragment.viewpager;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class LazyFragment extends Fragment {
    private boolean isFirst = true;
    private boolean isPrepared;


    /**
     * 在fragment的生命周期之前回调
     * @param isVisibleToUser 当前fragment是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }

    private void lazyLoad() {
        if (!isFirst || !isPrepared) {
            return;
        }
        isFirst = false;
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(onLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        lazyLoad();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
    }

    @Override
    public void onStart() {
        super.onStart();

        /**  返回当前fragment是否可见
         * @return The current value of the user-visible hint on this fragment.
         * @see #setUserVisibleHint(boolean)
         */
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        resetState();
    }

    /**
     * 重置标志位
     */
    private void resetState() {
        isFirst = true;
        isPrepared = false;
    }





    protected abstract void initData();

    protected abstract int onLayoutRes();

    protected abstract void initView(View view);

}

