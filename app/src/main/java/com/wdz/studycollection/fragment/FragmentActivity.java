package com.wdz.studycollection.fragment;


import android.os.Bundle;
import android.widget.Toast;

import com.wdz.studycollection.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;



public class FragmentActivity extends AppCompatActivity implements Fragment1.OnFragmentInteractionListener, Fragment1.CallBackValue {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,Fragment1.getInstance("f1"),"f1")
                    //.addToBackStack(Fragment1.class.getSimpleName())
                    .commit();
        }

    }


    @Override
    public void onItemClick(String str) {

        Toast.makeText(getApplicationContext(),"fragment -> activity:"+str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendValue2Fragment(String text) {
        Fragment f2 = Fragment2.getInstance("f2");
        Bundle bundle = new Bundle();
        bundle.putString("word",text);
        f2.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,f2)
                .addToBackStack(Fragment2.class.getSimpleName())
                .commit();
    }
}
