package com.example.dezhiwang.studycollection.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dezhiwang.studycollection.R;


public class Fragment3 extends Fragment {
    public static String PARAM = "param_key";
    private String mParam;
    private Activity mActivity;

    public static Fragment getInstance(String value){
        Fragment3 fragment1 = new Fragment3();
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        TextView textView = view.findViewById(R.id.textView6);
        textView.setText(mParam);
        Button mBtnNext = view.findViewById(R.id.bt_next);

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack(Fragment2.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });


        return view;
    }


}
