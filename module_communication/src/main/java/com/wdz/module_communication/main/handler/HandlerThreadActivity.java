package com.wdz.module_communication.main.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HandlerThreadActivity extends AppCompatActivity {

    @BindView(R2.id.imageView)
    ImageView mImageView;
    /**
     * 图片地址集合
     */
    private String url[]={
            "https://img-blog.csdn.net/20160903083245762",
            "https://img-blog.csdn.net/20160903083252184",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083311972",
            "https://img-blog.csdn.net/20160903083319668",
            "https://img-blog.csdn.net/20160903083326871"
    };
    private Handler mUIHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Drawable model = (Drawable) msg.obj;
            mImageView.setImageDrawable(model);
        }
    };
    private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        ButterKnife.bind(this);
        HandlerThread handlerThread = new HandlerThread("download-image");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                //Bitmap bitmap = downloadUrlBitmap(url[msg.what]);
                Drawable drawable = downBitmap(url[msg.what]);
                Message msg1 = new Message();
                msg1.what = msg.what;
                msg1.obj =drawable;
                mUIHandler.sendMessage(msg1);
                return false;
            }
        });

        for (int i=0;i<url.length;i++){
            handler.sendEmptyMessageDelayed(i,i*1000);
        }
    }

    private Drawable downBitmap(String url){
        try {
            FutureTarget<Drawable> submit = Glide.with(this)
                    .load(url)
                    .submit();
            drawable = submit.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;


    }

    private Bitmap downloadUrlBitmap(String urlString) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        Bitmap bitmap=null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            bitmap= BitmapFactory.decodeStream(in);
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}
