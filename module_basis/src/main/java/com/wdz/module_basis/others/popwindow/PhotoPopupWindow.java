package com.wdz.module_basis.others.popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;


import com.wdz.module_basis.R;

public class PhotoPopupWindow extends BasePopupWindow implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();

    public PhotoPopupWindow(Context context) {
        super(context);
        getItemView(R.id.icon_btn_select).setOnClickListener(this);
        getItemView(R.id.icon_btn_camera).setOnClickListener(this);
        getItemView(R.id.icon_btn_cancel).setOnClickListener(this);
    }


    @Override
    public int getLayoutId() {
        return  R.layout.pop_item_update;
    }

    @Override
    public int getPopupAnimationStyle() {
        return R.style.PopupWindowAnimStyle;
    }

    @Override
    public int getPopupWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int getPopupHeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public boolean isPopupFocus() {
        return true;
    }

    @Override
    public ColorDrawable getBackgroundDrawable() {
        return new ColorDrawable(0x0000000);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.icon_btn_select){
            Log.i(TAG, "onClick: select");
        }
        else if (v.getId() == R.id.icon_btn_camera){
            Log.i(TAG, "onClick: camera");
        }
        else if (v.getId() == R.id.icon_btn_cancel){
            Log.i(TAG, "onClick: cancel");
        }
    }
}
