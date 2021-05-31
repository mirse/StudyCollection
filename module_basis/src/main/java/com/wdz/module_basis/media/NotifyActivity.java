package com.wdz.module_basis.media;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.MainActivity;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = ARouterConstant.ACTIVITY_NOTIFY)
public class NotifyActivity extends AppCompatActivity {
    private static final String TAG = "NotifyActivity";
    @BindView(R2.id.bt_start_notify)
    Button mBtnStartNotify;
    private Notification notification;
    private NotificationManager manager;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        ButterKnife.bind(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String channelId = "chat";
//            String channelName = "聊天消息";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            createNotificationChannel(channelId, channelName, importance);
//
//            channelId = "subscribe";
//            channelName = "订阅消息";
//            importance = NotificationManager.IMPORTANCE_DEFAULT;
//            createNotificationChannel(channelId, channelName, importance);
//        }

    }

//    @TargetApi(Build.VERSION_CODES.O)
//    private void createNotificationChannel(String channelId, String channelName, int importance) {
//        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
//        channel.setShowBadge(true);
//        notificationManager = (NotificationManager) getSystemService(
//                NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(channel);
//    }



    @OnClick(R2.id.bt_start_notify)
    public void onClick(View view){
        if (view.getId() == R.id.bt_start_notify) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent = new Intent(this, NotifyActivity.class);
            PendingIntent contentIndent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String id = "11";
                String description = "description";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(id, description, importance);
                notificationManager.createNotificationChannel(channel);
                Notification notification = new NotificationCompat.Builder(this,id)
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setContentIntent(contentIndent)
                        .setChannelId("11")
                        .setSmallIcon(R.mipmap.ic_launcher)// 设置状态栏里面的图标（小图标）
                        .setWhen(System.currentTimeMillis())// 设置时间发生时间
                        .setAutoCancel(false)// 设置可以清除
                        .setContentTitle("收到一条聊天消息")// 设置下拉列表里的标题
                        .setContentText("今天中午吃什么？")// 设置上下文内容
                        .build();
                // 用图标id做notify的ID
                notificationManager.notify(R.mipmap.ic_launcher, notification);
            }
            else {
                Notification notification = new Notification.Builder(this)
                        .setContentIntent(contentIndent)
                        .setSmallIcon(R.mipmap.ic_launcher)// 设置状态栏里面的图标（小图标）
                        .setWhen(System.currentTimeMillis())// 设置时间发生时间
                        .setAutoCancel(false)// 设置可以清除
                        .setContentTitle("收到一条聊天消息")// 设置下拉列表里的标题
                        .setContentText("今天中午吃什么？")// 设置上下文内容
                        .build();
                // 用图标id做notify的ID
                notificationManager.notify(R.mipmap.ic_launcher, notification);

            }







//
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel notificationChannel = manager.getNotificationChannel("subscribe");
//
//            }
//
//            //点击通知的跳转界面
//            Intent resultIntent = new Intent(this, MainActivity.class);
//            PendingIntent resultPendingIntent = PendingIntent.getActivity(
//                    this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//            Notification notification = new NotificationCompat.Builder(this, "subscribe")
//                    .setContentTitle("收到一条聊天消息")
//                    .setContentText("今天中午吃什么？")
//                    .setWhen(System.currentTimeMillis())
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
//                    .setAutoCancel(true)
//                    .setContentIntent(resultPendingIntent)
//                    .setNumber(2)
//                    .build();
//
//
//            manager.notify(1, notification);
        }
    }
}
