package com.wdz.module_basis.others.popwindow;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_POPUP_WINDOW)
public class PopupWindowActivity extends AppCompatActivity {

    @BindView(R2.id.bt_show_popup)
    Button btShowPopup;
    private BasePopupWindow basePopupWindow;
    private WifiPopupWindow wifiPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
        ButterKnife.bind(this);
        basePopupWindow = new PhotoPopupWindow(this);
        wifiPopupWindow = new WifiPopupWindow(this);

    }
    @OnClick(R2.id.bt_show_popup)
    public void onClick(View view) {
        if (view.getId() == R.id.bt_show_popup) {
            //basePopupWindow.showAtLocation(findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            wifiPopupWindow.showAsDropDown(findViewById(R.id.bt_show_popup), 0, 0, Gravity.CENTER);
        }
    }

}
