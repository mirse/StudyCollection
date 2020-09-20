package com.wdz.module_customview.main.viewbase.floating;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FloatingService extends Service implements View.OnTouchListener {

    private int x;
    private int y;
    private WindowManager.LayoutParams layoutParams;
    private WindowManager systemService;
    private static final String TAG = "FloatingService";
    public FloatingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatView();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatView() {
        if (Settings.canDrawOverlays(this)){
            systemService = (WindowManager) getSystemService(WINDOW_SERVICE);
            final Button button = new Button(getApplicationContext());
            button.setText("floating window");
            button.setBackgroundColor(Color.BLUE);
            button.setOnTouchListener(this);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    systemService.removeView(button);
                }
            });
            layoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.width = 500;
            layoutParams.height = 100;
            layoutParams.x = 0;
            layoutParams.y = 0;
            systemService.addView(button, layoutParams);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                Log.i(TAG,"downx:"+x+" downy:"+y);
                break;
            case MotionEvent.ACTION_MOVE:
                int nowX = (int) event.getRawX();
                int nowY = (int) event.getRawY();
                int movedX = nowX - x;
                int movedY = nowY - y;
                x = nowX;
                y = nowY;
                layoutParams.x = layoutParams.x + movedX;
                layoutParams.y = layoutParams.y + movedY;
//                layoutParams.x = (int) (event.getRawX() - event.getX());
//                layoutParams.y = (int) (event.getRawY() - event.getY());
                Log.i(TAG,"x:"+nowX+" y:"+nowX);
                systemService.updateViewLayout(v,layoutParams);
                break;
            case MotionEvent.ACTION_UP:
                break;
                default:
                    break;
        }
        return false;
    }
}
