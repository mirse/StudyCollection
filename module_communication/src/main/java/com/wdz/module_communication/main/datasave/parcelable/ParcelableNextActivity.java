package com.wdz.module_communication.main.datasave.parcelable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  ParcelableNextActivity extends AppCompatActivity {
    private static final String TAG = "ParcelableNextActivity";
    @BindView(R2.id.tv_show)
    TextView mTvShow;

    private final String BOOK = "book";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable_next);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Log.i(TAG,intent.toString());
        Book book = getIntent().getParcelableExtra(BOOK);
        Log.i(TAG,book.toString());
        //mTvShow.setText(book.bookName+" 价格："+book.price+" years:"+book.years.toString());
    }
}
