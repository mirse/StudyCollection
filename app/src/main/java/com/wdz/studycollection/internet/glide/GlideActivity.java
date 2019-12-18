package com.wdz.studycollection.internet.glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wdz.studycollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GlideActivity extends AppCompatActivity implements msgListener {
    private static final String TAG = "GlideActivity";
    @BindView(R.id.iv_glide)
    ImageView ivGlide;
    private Outter outter;
    private msgListener msgListener;
    private MyButton myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);

        outter = new Outter(this);

        myButton = new MyButton(this);
        myButton.setmOnkeyValueListener(this); //传递监听对象到A类


        //initImg();
    }

    @OnClick({R.id.bt_sendmsg,R.id.bt_refresh})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_sendmsg:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);//休眠3秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        myButton.doClick(); //这里是调用A类中的方法，接受到A类传入的内容
                    }
                }).start();

                break;
            case R.id.bt_refresh:
                outter = null;
                outter = new Outter(this);
                Log.i(TAG,"outter:"+outter);
                break;
        }
    }

    private void initImg() {
        Glide.with(this)
                .load("http://dmimg.5054399.com/allimg/pkm/pk/22.jpg")
                .into(ivGlide);
    }

    @Override
    public void onMsgGet(String msg) {
        synchronized (this) {
            Log.i(TAG, "this:" + this + " thread:" + Thread.currentThread().getName()+" outter:"+outter);
            outter.onMsgGet(msg);
        }
    }
}
