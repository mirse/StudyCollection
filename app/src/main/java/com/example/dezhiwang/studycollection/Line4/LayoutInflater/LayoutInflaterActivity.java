package com.example.dezhiwang.studycollection.Line4.LayoutInflater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.dezhiwang.studycollection.R;

public class LayoutInflaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflater);
        LinearLayout container = findViewById(R.id.container);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout, null, false);
        container.addView(view);
        Log.i("inflate", "View:"+view);
    }
}
