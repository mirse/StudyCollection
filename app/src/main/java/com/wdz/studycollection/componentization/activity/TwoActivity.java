package com.wdz.studycollection.componentization.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.studycollection.R;

import static com.wdz.studycollection.componentization.activity.FirstActivity.NEED_LOGIN;

@Route(path = "/test/twoActivity")
public class TwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
    }
}
