package com.wdz.studycollection.myview;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.wdz.studycollection.R;

public class LayoutInflaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout container = findViewById(R.id.container);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout, null, false);
        container.addView(view);
        Log.i("inflate", "View:"+view);
    }
}
