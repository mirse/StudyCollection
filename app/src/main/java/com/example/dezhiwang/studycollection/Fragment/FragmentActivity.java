package com.example.dezhiwang.studycollection.Fragment;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dezhiwang.studycollection.R;


public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,Fragment1.getInstance("hello world"),"f1")
                    .addToBackStack(Fragment1.class.getSimpleName())
                    .commit();
        }
    }


}
