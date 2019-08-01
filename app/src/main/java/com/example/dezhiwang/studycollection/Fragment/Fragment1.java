package com.example.dezhiwang.studycollection.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dezhiwang.studycollection.R;


public class Fragment1 extends Fragment {
    public static String PARAM = "param_key";
    private String mParam;
    private Activity mActivity;
    private OnFragmentInteractionListener mListener;
    private CallBackValue mCallBack;
//在Fragment中定义接口，并让Activity实现该接口
    public interface OnFragmentInteractionListener {
        //将str从Fragment传递给Activity
        void onItemClick(String str);
    }

    public interface CallBackValue{
        public void sendValue2Fragment(String  text);
    }
    public static Fragment getInstance(String value){
        Fragment1 fragment1 = new Fragment1();
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
        Toast.makeText(getContext(),"activity -> fragment:"+mParam,Toast.LENGTH_SHORT).show();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;//将参数Context强转为OnFragmentInteractionListener对象
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        if (context instanceof CallBackValue) {
            mCallBack = (CallBackValue) context;//将参数Context强转为OnFragmentInteractionListener对象
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    public void setText(String s){
        Toast.makeText(getContext(),"Fragment -> Fragment:"+s,Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        TextView textView = view.findViewById(R.id.textView6);
        textView.setText(mParam);
        Button mBtn2 = view.findViewById(R.id.bt2);
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick("回参");
                mCallBack.sendValue2Fragment("fragment1告诉fragment2");
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.container,Fragment2.getInstance("f2"),"f2")
//                        .addToBackStack(Fragment2.class.getSimpleName())
//                        .commit();


            }
        });
        return view;
    }


}
