package com.wdz.studycollection.componentization.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wdz.studycollection.R;

import static com.wdz.studycollection.componentization.activity.FirstActivity.NEED_LOGIN;

@Route(path = "/test/firstActivity",extras = NEED_LOGIN)
public class FirstActivity extends AppCompatActivity {

    public final static int NEED_LOGIN = 1;

    private static final String TAG = "FirstActivity";
    //1、通过Autowired注解表明key  &  需要在onCreate中调用ARouter.getInstance().inject(this);
    @Autowired(name = "key")
    public String data;
    //2、通过Autowired注解 & 将key作为属性名称  需要在onCreate中调用ARouter.getInstance().inject(this);
    @Autowired()
    public String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ARouter.getInstance().inject(this);
        Log.i(TAG, "onCreate: "+key);
        String key1 = getIntent().getExtras().getString("key");
        Log.i(TAG, "onCreate: "+key1);
    }
}
