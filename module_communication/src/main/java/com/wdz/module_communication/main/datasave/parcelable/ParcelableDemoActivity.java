package com.wdz.module_communication.main.datasave.parcelable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_PARACELABLE)
public class ParcelableDemoActivity extends AppCompatActivity {
    @BindView(R2.id.bt_next)
    Button mBtnNext;

    private final String BOOK = "book";
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable_demo);
        ButterKnife.bind(this);
        ArrayList<String> mYears = new ArrayList<>();
        for(int i=0;i<=20190;i++){
            mYears.add(i+"年");
        }
//        mYears.add("1990");
//        mYears.add("2019");
        book = new Book("书名", 100);
        book.years=mYears;

    }

    @OnClick(R2.id.bt_next)
    public void onClick(View view){
        if (view.getId() == R.id.bt_next) {
            Intent intent = new Intent(ParcelableDemoActivity.this, ParcelableNextActivity.class);
            intent.putExtra(BOOK, book);
            startActivity(intent);
        }
    }
}
