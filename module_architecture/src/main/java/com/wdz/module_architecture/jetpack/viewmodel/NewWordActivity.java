package com.wdz.module_architecture.jetpack.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.R2;
import com.wdz.module_architecture.jetpack.WordViewModel;
import com.wdz.module_architecture.jetpack.bean.Word;

import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = ARouterConstant.ACTIVITY_NEW_WORD)
public class NewWordActivity extends AppCompatActivity {

    private EditText mEditWordView;
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        ButterKnife.bind(this);
        mEditWordView = findViewById(R.id.edit_word);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
    }

    @OnClick({R2.id.button_save,R2.id.button_delete})
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.button_save) {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditWordView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                setResult(RESULT_OK, replyIntent);
                wordViewModel.insert(new Word(mEditWordView.getText().toString()));
            }
            finish();
        } else if (id == R.id.button_delete) {
            Intent replyIntent1 = new Intent();
            if (TextUtils.isEmpty(mEditWordView.getText())) {
                setResult(RESULT_CANCELED, replyIntent1);
            } else {
                setResult(RESULT_OK, replyIntent1);
                wordViewModel.delete(new Word(mEditWordView.getText().toString()));
            }
            finish();
        }
    }
}
