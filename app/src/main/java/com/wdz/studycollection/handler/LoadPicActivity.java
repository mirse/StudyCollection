package com.wdz.studycollection.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.LogPrinter;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wdz.studycollection.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoadPicActivity extends AppCompatActivity {
    private static final String TAG = "LoadPicActivity";


    @BindView(R.id.bt_pic_load)
    Button mBtnLoadPic;
    @BindView(R.id.iv_pic_load)
    ImageView mIvPic;
    @BindView(R.id.bt_reset)
    Button mBtnReset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pic);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_pic_load,R.id.bt_reset})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_pic_load:
                //子线程执行耗时操作
                new Thread(){
                    @Override
                    public void run() {
                        Bitmap bitmap = loadPicfrominternet();
                        Handler handler = new Handler(Looper.getMainLooper());
                        //ui线程显示图片
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mIvPic.setImageBitmap(bitmap);
                            }
                        });
                    }
                }.start();
                break;
            case R.id.bt_reset:
                //mIvPic.setImageResource(R.mipmap.ic_launcher);
                mIvPic.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cartoon));
                break;
        }
    }

    /**
     * 从云端加载图片 -- 耗时操作
     */
    public Bitmap loadPicfrominternet(){
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            URL url = new URL("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1421494343,3838991329&fm=23&gp=0.jpg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10*1000);
            connection.setReadTimeout(5*1000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200 ){
                inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
        } catch (Exception e) {
            Log.i(TAG,e.toString());
            //Toast.makeText(getApplicationContext(), "访问失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }


}
