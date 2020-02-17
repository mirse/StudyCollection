package com.wdz.studycollection.datasave.parcelable;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.List;

public class ParcelableDemoActivity extends AppCompatActivity {
    @BindView(R.id.bt_next)
    Button mBtnNext;

    private final String BOOK = "book";
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable_demo);
        ButterKnife.bind(this);
        ArrayList<String> mYears = new ArrayList<>();
        for(int i=0;i<=201900;i++){
            mYears.add(i+"年");
        }
//        mYears.add("1990");
//        mYears.add("2019");
        book = new Book("书名", 100);
        book.years=mYears;

    }

    @OnClick(R.id.bt_next)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_next:
                Intent intent = new Intent(this, ParcelableNextActivity.class);
                intent.putExtra(BOOK,book);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
