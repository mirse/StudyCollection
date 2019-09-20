package com.wdz.studycollection.media;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wdz.studycollection.R;

public class CameraXActivity extends AppCompatActivity {

    @BindView(R.id.bt_open_camera)
    Button mBtnOpenCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x);
        Unbinder bind = ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_open_camera)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_open_camera:
                break;
        }
    }
}
