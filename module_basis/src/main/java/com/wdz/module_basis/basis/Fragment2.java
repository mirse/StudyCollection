package com.wdz.module_basis.basis;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wdz.module_basis.R;


public class Fragment2 extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mParam = getArguments().getString(PARAM);
        Log.i(TAG, "onAttach: ");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        TextView textView = view.findViewById(R.id.textView6);
        textView.setText(mParam);
        Button mBtnNext = view.findViewById(R.id.bt_next);

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,Fragment3.getInstance("Fragment3"),"Fragment3")
                        .addToBackStack(Fragment2.class.getSimpleName())
                        .commit();
            }
        });


        return view;
    }


}
