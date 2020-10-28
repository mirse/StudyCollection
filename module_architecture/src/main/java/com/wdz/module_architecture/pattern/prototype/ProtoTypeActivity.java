package com.wdz.module_architecture.pattern.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.pattern.prototype.bean.ClonePerson;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterConstant.ACTIVITY_PROTOTYPE_DEMO)
public class ProtoTypeActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proto_type);
        ClonePerson clonePerson = new ClonePerson();
        clonePerson.setName("0");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1s");
        clonePerson.setListStr(strings);
        Log.i(TAG, "onCreate: 原对象"+clonePerson.toString());
        ClonePerson clonePerson1 = (ClonePerson) clonePerson.clone();
        ArrayList<String> strings1 = new ArrayList<>();
        strings1.add("2s");
        clonePerson1.setName(String.valueOf(1));
        clonePerson1.setListStr(strings1);
        Log.i(TAG, "onCreate: 拷贝后对象"+clonePerson1.toString());
        Log.i(TAG, "onCreate: 拷贝完，原对象"+clonePerson.toString());
    }
}
