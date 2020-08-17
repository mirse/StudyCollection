package com.wdz.studycollection.viewbase.scroller.edittext;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

public class SoftKeyBoardListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "SoftKeyBoardListener";
    //activity的根视图
    private View rootView;
    //纪录根视图的显示高度
    int rootViewVisibleHeight = 0;
    private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;

    public SoftKeyBoardListener(Activity activity) {
        if (activity == null){
            return;
        }
        //获取activity的根视图
        rootView = activity.getWindow().getDecorView();

        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

        @Override
        public void onGlobalLayout() {

        }
    };


    /**
     * 移除软键盘变化监听
     */
    public void removeListener(){
        if (rootView!=null){
            rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    @Override
    public void onGlobalLayout() {
        //获取当前根视图在屏幕上显示的大小
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);

        //当前可视区域高度
        int visibleHeight = r.height();
        Log.i(TAG, "onGlobalLayout: visibleHeight："+visibleHeight);
        Log.i(TAG, "onGlobalLayout: rootViewVisibleHeight："+rootViewVisibleHeight);
        if (rootViewVisibleHeight == 0) {
            rootViewVisibleHeight = visibleHeight;
            return;
        }

        //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
        if (rootViewVisibleHeight == visibleHeight) {
            return;
        }

        //根视图显示高度变小超过200，可以看作软键盘显示了
        if (rootViewVisibleHeight - visibleHeight > 200) {
            if (onSoftKeyBoardChangeListener != null) {
                onSoftKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
            }
            rootViewVisibleHeight = visibleHeight;
            return;
        }

        //根视图显示高度变大超过200，可以看作软键盘隐藏了
        if (visibleHeight - rootViewVisibleHeight > 200) {
            if (onSoftKeyBoardChangeListener != null) {
                onSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
            }
            rootViewVisibleHeight = visibleHeight;
            return;
        }
    }


    public interface OnSoftKeyBoardChangeListener {
        /**
         * 软键盘显示
         * @param height
         */
        void keyBoardShow(int height);

        /**
         * 软键盘隐藏
         * @param height
         */
        void keyBoardHide(int height);
    }

    public void setListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
    }
}
