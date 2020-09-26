package com.wdz.module_basis.widget.recyclerview.updownrecyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpDownRecyclerViewActivity extends AppCompatActivity {
    @BindView(R2.id.updownrecyclerview)
    RecyclerView recyclerView;
    private List<Device> mList = new ArrayList<>();
    private UpDownAdapter upDownAdapter;
    private final int DEVICE_TITLE = 0;
    private final int DEVICE_BULB = 1;
    private final int DEVICE_GATEWAY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_basis_activity_up_down_recycler_view);
        ButterKnife.bind(this);
        upDownAdapter = new UpDownAdapter(mList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,  3,RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(upDownAdapter);

        View view = LayoutInflater.from(this).inflate(R.layout.layout_header, recyclerView, false);
        upDownAdapter.setHeaderView(view);

    }

    @OnClick({R2.id.bt_add_bulb,R2.id.bt_add_gateway})
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.bt_add_bulb) {
            Device device1 = new Device();
            device1.deviceType = DEVICE_BULB;
            mList.add(device1);
            upDownAdapter.updateDevice(mList);
        } else if (id == R.id.bt_add_gateway) {
            Device device = new Device();
            device.deviceType = DEVICE_GATEWAY;
            mList.add(device);
            upDownAdapter.updateDevice(mList);
        }
    }


    static class Device{

        public Device() {
        }

        public Device(int deviceType) {
            this.deviceType = deviceType;
        }

        public int deviceType;


        @Override
        public String toString() {
            return "Device{" +
                    "deviceType=" + deviceType +
                    '}';
        }
    }


}
