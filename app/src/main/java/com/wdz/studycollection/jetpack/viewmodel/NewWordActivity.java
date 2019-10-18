package com.wdz.studycollection.jetpack.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wdz.studycollection.R;
import com.wdz.studycollection.jetpack.WordViewModel;
import com.wdz.studycollection.jetpack.bean.Word;

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

    @OnClick({R.id.button_save,R.id.button_delete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_save:
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    setResult(RESULT_OK, replyIntent);
                    wordViewModel.insert(new Word(mEditWordView.getText().toString()));
                }
                finish();
                break;
            case R.id.button_delete:
                Intent replyIntent1 = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent1);
                } else {
                    setResult(RESULT_OK, replyIntent1);
                    wordViewModel.delete(new Word(mEditWordView.getText().toString()));
                }
                finish();
                break;
            default:
                break;
        }
    }
}
