package com.wdz.studycollection.materialdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wdz.studycollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextInputActivity extends AppCompatActivity {
    @BindView(R.id.textInput)
    TextInputEditText textInputEditText;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);
        ButterKnife.bind(this);
    }
}
