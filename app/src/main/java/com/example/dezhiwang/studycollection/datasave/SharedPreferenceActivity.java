package com.example.dezhiwang.studycollection.datasave;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dezhiwang.studycollection.R;

public class  SharedPreferenceActivity extends AppCompatActivity {

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
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)){
                    spUtils.saveValue(key,value);
                }
            }
        });

        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtKey.setText("");
                mEtValue.setText("");
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
