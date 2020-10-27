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


public class Fragment1 extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    public static String PARAM = "param_key";
    private String mParam;
    private Activity mActivity;
    private DataCallBack dataCallBack;


    public static Fragment getInstance(String value){
        Fragment1 fragment1 = new Fragment1();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM,value);
        fragment1.setArguments(bundle);
        return fragment1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: ");
        super.onAttach(context);
        dataCallBack = (DataCallBack) context;
        mActivity = (Activity) context;
        mParam = getArguments().getString(PARAM);
        Toast.makeText(getContext(),"activity向fragment1传值:"+mParam,Toast.LENGTH_SHORT).show();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        TextView textView = view.findViewById(R.id.textView6);
        textView.setText(mParam);
        Button mBtn2 = view.findViewById(R.id.bt2);
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCallBack.dataCallBack("fragment回传数据给activity,我要跳转到fragment2了");

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,Fragment2.getInstance("Fragment2"),"Fragment2")
                        .addToBackStack(Fragment1.class.getSimpleName())
                        .commit();
            }
        });
        return view;
    }

    public interface DataCallBack{
        void dataCallBack(String s);
    }


}
