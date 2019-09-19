package com.wdz.studycollection.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wdz.studycollection.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class Fragment2 extends Fragment {
    public static String PARAM = "param_key";
    private String mParam;
    private Activity mActivity;

    public static Fragment getInstance(String value){
        Fragment2 fragment1 = new Fragment2();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM,value);
        fragment1.setArguments(bundle);
        return fragment1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mParam = getArguments().getString(PARAM);
        String key = getArguments().getString("word");
        Toast.makeText(mActivity,"fragment向fragment传值:"+key,Toast.LENGTH_SHORT).show();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        TextView textView = view.findViewById(R.id.textView6);
        textView.setText(mParam);
        Button mBtnNext = view.findViewById(R.id.bt_next);

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,Fragment3.getInstance("f3"),"f3")
                        .addToBackStack(Fragment3.class.getSimpleName())
                        .commit();

                Fragment1 rightFragment = (Fragment1) getFragmentManager().findFragmentByTag("f1");
                rightFragment.setText("123456");
            }
        });


        return view;
    }


}
