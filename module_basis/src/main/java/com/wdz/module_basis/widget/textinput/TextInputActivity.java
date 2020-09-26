package com.wdz.module_basis.widget.textinput;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;


import butterknife.BindView;
import butterknife.ButterKnife;
@Route(path = ARouterConstant.ACTIVITY_TEXT_INPUT)
public class TextInputActivity extends AppCompatActivity {
    @BindView(R2.id.textInput)
    TextInputEditText textInputEditText;
    @BindView(R2.id.textInputLayout)
    TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);
        ButterKnife.bind(this);
    }
}
