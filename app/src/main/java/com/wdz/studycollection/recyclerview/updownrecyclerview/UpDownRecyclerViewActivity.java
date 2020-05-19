package com.wdz.studycollection.recyclerview.updownrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpDownRecyclerViewActivity extends AppCompatActivity {
    @BindView(R.id.updownrecyclerview)
    RecyclerView recyclerView;
    private List<Device> mList = new ArrayList<>();
    private UpDownAdapter upDownAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_down_recycler_view);
        ButterKnife.bind(this);

        upDownAdapter = new UpDownAdapter(mList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(upDownAdapter);
    }

    @OnClick({R.id.bt_add_bulb,R.id.bt_add_gateway})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_add_bulb:
                upDownAdapter.addDevice(new Device(0));
                break;
            case R.id.bt_add_gateway:
                upDownAdapter.addDevice(new Device(1));
                break;
            default:
                break;
        }
    }


    class Device{
        public int deviceType;

        public Device(int deviceType) {
            this.deviceType = deviceType;
        }
    }
}
