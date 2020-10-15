package com.wdz.module_basis.others.dialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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


    }
    @OnClick(R2.id.bt_show_dialog)
    public void onClick(View view){
        if (view.getId() == R.id.bt_show_dialog){
           new CommonDialogFragment.MessageDialogFragmentBuilder(DialogFragmentActivity.this)
                    .setMessage("xxxxxxxxxxxxxxxxxxxx")
                    .setTitle("提示")
                    .setNegativeButtonText("no", new CommonDialogBuilder.OnClickListener() {
                        @Override
                        public void onClick() {
                            Toast.makeText(getBaseContext(),"cancel",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButtonText("yes", new CommonDialogBuilder.OnClickListener() {
                        @Override
                        public void onClick() {
                            Toast.makeText(getBaseContext(),"yes",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();

        }
    }
}
