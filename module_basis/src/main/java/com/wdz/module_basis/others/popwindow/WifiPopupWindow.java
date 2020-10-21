package com.wdz.module_basis.others.popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wdz.module_basis.R;
import com.wdz.module_basis.widget.recyclerview.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class WifiPopupWindow extends BasePopupWindow implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<String> datas = new ArrayList<>();

    public WifiPopupWindow(Context context) {
        super(context);
        RecyclerView rv = (RecyclerView) getItemView(R.id.rv_wifi);
        datas.add("选择新的Wi-Fi");
        datas.add("YANKON-bakgee");
        datas.add("CMCC-YANKON");
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        MyAdapter myAdapter = new MyAdapter(datas);
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(myAdapter);
    }


    @Override
    public int getLayoutId() {
        return  R.layout.popup_wifi;
    }

    @Override
    public int getPopupAnimationStyle() {
        return R.style.PopupWindowAnimStyle;
    }

    @Override
    public int getPopupWidth() {
        return dp2px(getContext(),315);
    }

    @Override
    public int getPopupHeight() {
        return dp2px(getContext(),135);
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

    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
