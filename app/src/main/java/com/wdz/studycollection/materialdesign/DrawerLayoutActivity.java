package com.wdz.studycollection.materialdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.wdz.studycollection.MainActivity;
import com.wdz.studycollection.R;

public class DrawerLayoutActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_open_left,R.id.btn_open_right,R.id.btn_close_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_open_left:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.btn_open_right:
                mDrawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.btn_close_right:
                mDrawerLayout.closeDrawers();
                break;
            default:
                    break;
        }
    }
}
