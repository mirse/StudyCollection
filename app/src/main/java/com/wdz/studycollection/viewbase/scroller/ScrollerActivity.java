package com.wdz.studycollection.viewbase.scroller;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.wdz.studycollection.R;
import com.wdz.studycollection.viewbase.scroller.adapter.RecyclerViewAdapter;
import com.wdz.studycollection.viewbase.scroller.bean.Device;
import com.wdz.studycollection.viewbase.scroller.bean.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ScrollerActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List mItem;
    private Page page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        ButterKnife.bind(this);
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage() + "-" + locale.getCountry();

        initUi();
    }

    private void initUi() {
        mItem = new ArrayList<String>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(getData());
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    private List<Page> getData() {
        List<Page> mPages= new ArrayList<>();
        String temp = "item";
        page = new Page();
        List<Device> mDevice = new ArrayList<>();
        for (int j=0;j<7;j++){
            Device device = new Device();
            device.setName("device"+j);
            device.setSelected(true);
            mDevice.add(device);
        }
        page.setDevice(mDevice);
        mPages.add(page);

        page = new Page();

        mDevice = new ArrayList<>();
        for (int j=0;j<6;j++){
            Device device = new Device();
            device.setName("device1"+j);
            device.setSelected(false);
            mDevice.add(device);
        }
        page.setDevice(mDevice);
        mPages.add(page);



        return mPages;
    }


    @OnClick(R.id.bt_add)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_add:
                //recyclerViewAdapter.insertDevice();


                break;
            default:
                break;
        }
    }

}
