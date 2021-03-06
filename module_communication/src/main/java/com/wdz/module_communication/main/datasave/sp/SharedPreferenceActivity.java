package com.wdz.module_communication.main.datasave.sp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_communication.R;


import java.util.Iterator;
import java.util.Set;

@Route(path = ARouterConstant.ACTIVITY_SP)
public class  SharedPreferenceActivity extends AppCompatActivity {

    private static final String TAG = "SharedPreferenceActivit";
    private SpUtils spUtils;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);
        spUtils = new SpUtils(this);
        initUI();
    }

    private void initUI() {
        final EditText mEtKey = findViewById(R.id.et_key);
        final EditText mEtValue = findViewById(R.id.et_value);
        Button mBtnSave = findViewById(R.id.bt_save);
        Button mBtnRead = findViewById(R.id.bt_read);
        Button mBtnClear = findViewById(R.id.bt_clear);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = mEtKey.getText().toString();
                String value = mEtValue.getText().toString();
              //  if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)){
                    spUtils.savePrimaryAddress(String.valueOf(spUtils.getPrimaryAddress().size()+2));

                    Set<String> primaryAddress = spUtils.getPrimaryAddress();
                    Log.i(TAG,"length:"+primaryAddress.size());
                    Iterator<String> iterator = primaryAddress.iterator();
                    while (iterator.hasNext()){
                        Log.i(TAG,iterator.next());
                    }

              //  }
            }
        });

        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = mEtValue.getText().toString();
                mEtKey.setText("");
                mEtValue.setText("");

                spUtils.deletePrimaryAddress(value);
                Set<String> primaryAddress = spUtils.getPrimaryAddress();
                Log.i(TAG,"length:"+primaryAddress.size());
                Iterator<String> iterator = primaryAddress.iterator();
                while (iterator.hasNext()){
                    Log.i(TAG,iterator.next());
                }
            }
        });

        mBtnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mEtKey.getText().toString();
                if (!TextUtils.isEmpty(key)){
                    String value = (String) spUtils.getValue(key, "null");
                    mEtValue.setText(value);
                }

            }
        });
    }
}
