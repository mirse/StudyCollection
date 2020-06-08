package com.wdz.mychoosedialog.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wdz.mychoosedialog.R;
import com.wdz.mychoosedialog.datepicker.base.RoomPickerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



/**
 * @author dezhi.wang
 */
public class RoomPicker implements View.OnClickListener, RoomPickerView.OnSelectListener {

    private Context mContext;
    private TimeSelectCallback mCallback;
    private boolean mCanDialogShow;
    private Dialog mPickerDialog;
    private RoomPickerView mDpvRoom;
    private List<String> mList = new ArrayList<>();
    private String selectRoomName = "";

    public interface TimeSelectCallback {

        /**
         * @param roomName 房间名字
         */
        void onRoomSelected(String roomName);

        /**
         * 点击取消的接口回调
         */
        void onCancel();
        /**
         * 滑动时的时间回调
         * @param roomName 房间名字
         */
        void onRoomSelecting(String roomName);
    }


    /**
     * 初始化时间选择器
     * @param context Activity Context
     * @param callback 选择结果回调
     */
    public RoomPicker(String cancelText,String saveText,List<String> mList,Context context, TimeSelectCallback callback) {
        if (context == null || callback == null ) {
            mCanDialogShow = false;
            return;
        }
        this.mList = mList;
        mContext = context;
        mCallback = callback;
        initView(cancelText,saveText);
        mCanDialogShow = true;
    }

    private void initView(String cancelText,String saveText) {
        mPickerDialog = new Dialog(mContext, R.style.date_picker_dialog);
        mPickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mPickerDialog.setContentView(R.layout.picker_room);
        setGravity(Gravity.BOTTOM);
        TextView tvCancel = mPickerDialog.findViewById(R.id.tv_cancel);
        TextView tvConfirm = mPickerDialog.findViewById(R.id.tv_confirm);
        tvConfirm.setText(saveText);
        tvCancel.setText(cancelText);
        tvConfirm.setText(saveText);
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        mDpvRoom = mPickerDialog.findViewById(R.id.dpv_room);
        mDpvRoom.setOnSelectListener(this);

        mDpvRoom.setDataList(mList);
        //默认选择的房间
        selectRoomName = mList.get(0);
        mDpvRoom.setSelected(0);
    }

    /**
     * 设置弹窗位置
     * @param gravity 位置属性 默认为Gravity.BOTTOM
     */
    public void setGravity(int gravity) {
        Window window = mPickerDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = gravity;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
    }


    /**
     * 设置是否可以滚动
     */
    private void setCanScroll(boolean canScroll) {
        mDpvRoom.setCanScroll(canScroll);
    }


    /**
     * @return 弹窗是否显示
     */
    private boolean canShow() {
        return mCanDialogShow && mPickerDialog != null;
    }


    /**
     * 展示时间选择器
     */
    public void show() {
        if (!canShow()) {
            return;
        }
            mPickerDialog.show();
    }


    /**
     * 设置是否允许点击屏幕或物理返回键关闭
     */
    public void setCancelable(boolean cancelable) {
        if (!canShow()) {
            return;
        }

        mPickerDialog.setCancelable(cancelable);
    }
    /**
     * 设置日期控件是否可以循环滚动
     */
    public void setScrollLoop(boolean canLoop) {
        if (!canShow()) {
            return;
        }
        mDpvRoom.setCanScrollLoop(canLoop);
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_cancel) {
            if (mCallback != null) {
                mCallback.onCancel();
            }
        }
        else if (i == R.id.tv_confirm) {
            if (mCallback != null) {
                mCallback.onRoomSelected(selectRoomName);
            }
        }

        if (mPickerDialog != null && mPickerDialog.isShowing()) {
            mPickerDialog.dismiss();
        }
    }

    @Override
    public void onSelect(View view, String selected) {
        if (view == null || TextUtils.isEmpty(selected)) {
            return;
        }
        selectRoomName = selected;
        if (mCallback!=null){
            mCallback.onRoomSelecting(selected);
        }
    }

}
