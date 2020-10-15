package com.wdz.module_basis.others.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_DIALOG_FRAGMENT)
public class DialogFragmentActivity extends AppCompatActivity {

    private CommonDialogFragment commonDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_fragment);
        ButterKnife.bind(this);

        commonDialogFragment = new CommonDialogFragment.CommonDialogFragmentBuilder()
                .setType(DialogType.DIALOG_TYPE_MESSAGE)
                .setTitle("提示")
                .setMessage("xxxxxxxxxxxxxxxxxxxx")
                .setNegativeButtonText("no")
                .setPositiveButtonText("yes")
                .bulid();




    }
    @OnClick(R2.id.bt_show_dialog)
    public void onClick(View view){
        if (view.getId() == R.id.bt_show_dialog){
            commonDialogFragment.show(getSupportFragmentManager(),"dialog");
        }
    }
}
