package com.example.dezhiwang.studycollection.Fragment;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dezhiwang.studycollection.R;


public class FragmentActivity extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,Fragment1.getInstance("f1"),"f1")
                    .addToBackStack(Fragment1.class.getSimpleName())
                    .commit();
        }
    }


    @Override
    public void onItemClick(String str) {
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }
}
