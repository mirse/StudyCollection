package com.wdz.module_architecture.pattern.builder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.pattern.builder.bean.MyComputer;

@Route(path = ARouterConstant.ACTIVITY_BUILDER_DEMO)
public class BuilderDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder_demo);
        Computer computer = new Computer.Bulider("", "")
                .setKeyboard("")
                .setMouse("")
                .setUsb("")
                .bulid();

        MyComputer myComputer = new MyComputer.MyComputerBuilder()
                .setUsb("")
                .setKeyBoard("")
                .setRam("")
                .bulid();

    }
}
