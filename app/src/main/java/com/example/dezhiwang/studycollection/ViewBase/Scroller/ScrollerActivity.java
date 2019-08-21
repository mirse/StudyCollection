package com.example.dezhiwang.studycollection.ViewBase.Scroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dezhiwang.studycollection.R;

import java.util.Locale;

public class ScrollerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        Locale locale = Locale.getDefault();
//Locale.getDefault() 和 LocaleList.getAdjustedDefault().get(0) 同等效果，还不需要考虑版本问题，推荐直接使用
        String language = locale.getLanguage() + "-" + locale.getCountry();
        Toast.makeText(getApplicationContext(),"language:"+language,Toast.LENGTH_SHORT).show();
    }
}
