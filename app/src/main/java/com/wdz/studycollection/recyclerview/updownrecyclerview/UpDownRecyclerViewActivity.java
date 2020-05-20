package com.wdz.studycollection.recyclerview.updownrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    private Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_down_recycler_view);
        ButterKnife.bind(this);
        device = new Device();
        upDownAdapter = new UpDownAdapter(device, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,  RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(upDownAdapter);

    }

    @OnClick({R.id.bt_add_bulb,R.id.bt_add_gateway})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_add_bulb:
                Bulb bulb = new Bulb();
                device.getBulbList().add(bulb);
                upDownAdapter.updateDevice(device);
                break;
            case R.id.bt_add_gateway:
                Gateway gateway = new Gateway();
                device.getGatewayList().add(gateway);
                upDownAdapter.updateDevice(device);
                break;
            default:
                break;
        }
    }


    class Device{
        public int deviceType;
        public List<Bulb> bulbList = new ArrayList<>();
        public List<Gateway> gatewayList = new ArrayList<>();

        public List<Bulb> getBulbList() {
            return bulbList;
        }

        public void setBulbList(List<Bulb> bulbList) {
            this.bulbList = bulbList;
        }

        public List<Gateway> getGatewayList() {
            return gatewayList;
        }

        public void setGatewayList(List<Gateway> gatewayList) {
            this.gatewayList = gatewayList;
        }

    }

    class Bulb{

    }
    class Gateway{

    }
}
