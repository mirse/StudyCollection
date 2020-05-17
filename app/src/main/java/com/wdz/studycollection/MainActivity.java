package com.wdz.studycollection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.ServiceConnection;
import android.os.Bundle;

import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wdz.studycollection.activity.ColorPicker1Activity;
import com.wdz.studycollection.activity.ColorPicker2Activity;
import com.wdz.studycollection.activity.ColorPickerActivity;
import com.wdz.studycollection.activity.DatePickerActivity;
import com.wdz.studycollection.activity.DrawableIconActivity;
import com.wdz.studycollection.activity.DrawableIconIVActivity;
import com.wdz.studycollection.activity.DrawableWrapperActivity;
import com.wdz.studycollection.activity.LetterIndexActivity;
import com.wdz.studycollection.activity.PageActivity;
import com.wdz.studycollection.immersive.MainSplashActivity;
import com.wdz.studycollection.immersive.SplashImageActivity;
import com.wdz.studycollection.activity.SurfaceViewActivity;
import com.wdz.studycollection.activity.TabViewActivity;
import com.wdz.studycollection.anim.AnimDemoActivity;
import com.wdz.studycollection.asynctask.AsyncTaskActivity;
import com.wdz.studycollection.datasave.parcelable.ParcelableDemoActivity;
import com.wdz.studycollection.datasave.room.RoomTestActivity;
import com.wdz.studycollection.datasave.SharedPreferenceActivity;
import com.wdz.studycollection.datasave.service.ServiceDemoActivity;
import com.wdz.studycollection.eventbus.MessageEvent;
import com.wdz.studycollection.eventbus.RxBus;
import com.wdz.studycollection.fragment.FragmentActivity;
import com.wdz.studycollection.fragment.revolve.FixProblemsActivity;
import com.wdz.studycollection.fragment.revolve.SavedInstanceStateUsingActivity;
import com.wdz.studycollection.fragment.viewpager.VpAndFragActivity;
import com.wdz.studycollection.handler.HandlerDemoActivity;
import com.wdz.studycollection.handler.LoadPicActivity;
import com.wdz.studycollection.indicatorview.IndicatorActivity;
import com.wdz.studycollection.internet.OkHttpDemoActivity;
import com.wdz.studycollection.internet.glide.GlideActivity;
import com.wdz.studycollection.jetpack.viewmodel.ViewModelActivity;
import com.wdz.studycollection.materialdesign.CoordinatorLayoutActivity;
import com.wdz.studycollection.materialdesign.DrawerLayoutActivity;
import com.wdz.studycollection.materialdesign.testBehavior.TestActivity;
import com.wdz.studycollection.media.CameraXActivity;
import com.wdz.studycollection.media.NotifyActivity;
import com.wdz.studycollection.myview.LayoutInflaterActivity;
import com.wdz.studycollection.mvp.logintest.MvpActivity;
import com.wdz.studycollection.mvp.test1.DataActivity;
import com.wdz.studycollection.mvp.writeandread.SavaActivity;
import com.wdz.studycollection.network.SocketClientActivity;
import com.wdz.studycollection.recyclepager.ImageRecyclerActivity;
import com.wdz.studycollection.recyclepager.FragmentPagerActivity;
import com.wdz.studycollection.recyclerview.grid.GridViewActivity;
import com.wdz.studycollection.recyclerview.RecyclerViewActivity;
import com.wdz.studycollection.rxjava.RxDemoActivity;
import com.wdz.studycollection.viewbase.CoordinateSystemActivity;
import com.wdz.studycollection.viewbase.floating.FloatingViewActivity;
import com.wdz.studycollection.viewbase.scroller.ScrollerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener {

    private RecyclerView mRecycleView;
    private MainAdapter mainAdapter;
    private static final String TAG = "MainActivity";
    private RxBus rxBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.hide();
        }
        initMain();

        initRxBus();
        //EventBus.getDefault().register(this);
        Toast.makeText(this,"瀑布流拖拽problem",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainService.class);
        MyServiceConnection myServiceConnection = new MyServiceConnection();
        bindService(intent, myServiceConnection, Context.BIND_NOT_FOREGROUND);
    }

    class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    private void initRxBus() {
        rxBus = RxBus.getInstance();
        Disposable disposable = rxBus.doSubscribe(String.class, new Consumer<String>() {
            @Override
            public void accept(String integer) throws Exception {
                Log.d(TAG, integer + "");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, throwable.toString());
            }
        });
        rxBus.addSubscription(this,disposable);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        rxBus.unSubscription(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribe(MessageEvent messageEvent){
        Log.i(TAG,messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void onStickySubscribe(MessageEvent messageEvent){
        Log.i(TAG,"sticky:"+messageEvent.getMessage());
    }



    private void initMain() {
        mRecycleView = findViewById(R.id.rv);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(this);
        mainAdapter.setOnItemClickListener(this);
        initData();
        mRecycleView.setAdapter(mainAdapter);
    }

    private void initData() {
        List<MainBean> list = new ArrayList<>();
        String[][] array={
                {"自定义View","argb圆","rgbRing圆","hsv圆","SurfaceView","翻页效果","TabView"},
                {"角标效果","Group实现","View实现","ImageV实现","指示器","fragment循环","viewpage循环"},
                {"字母索引","列表索引","Login-mvp","mvp_test","save_test","选择器","recyclerView"},
                {"视图","LayoutInflater","瀑布流","界面旋转修复","Fragment","与viewpager","界面旋转"},
                {"网络通信","Socket通信","AsyncTask","Handler","handler切换线程","OkHttp","Glide"},
                {"数据存储","Sp","Room","序列化","Service","",""},
                {"View合集","坐标系","悬浮窗","Scroller","","",""},
                {"动画合集","属性动画","splash","","","",""},
                {"多媒体","通知","相机","","","",""},
                {"Material","抽屉","折叠视图","","","",""},
                {"Jetpack","ViewModel","RxJava","","","",""}
        };

        for (String[] mArray:array) {
            list.add(new MainBean(mArray[0],mArray[1],mArray[2],mArray[3],mArray[4],mArray[5],mArray[6]));
        }
        mainAdapter.setList(list);

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i(TAG,"position:"+position);
        switch (position){
            case 0:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this,ColorPickerActivity.class));
                        //eventBus 订阅粘性事件
                        EventBus.getDefault().register(this);
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this,ColorPicker1Activity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this,ColorPicker2Activity.class));
                        break;
                    case R.id.button4:
                        startActivity(new Intent(this, SurfaceViewActivity.class));
                        break;
                    case R.id.button5:
                        startActivity(new Intent(this, PageActivity.class));
                        break;
                    case R.id.button6:
                        startActivity(new Intent(this, TabViewActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this,DrawableWrapperActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this,DrawableIconActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this,DrawableIconIVActivity.class));
                        break;
                    case R.id.button4:
                        startActivity(new Intent(this, IndicatorActivity.class));
                        break;
                    case R.id.button5:
                        startActivity(new Intent(this, FragmentPagerActivity.class));
                        break;
                    case R.id.button6:
                        startActivity(new Intent(this, ImageRecyclerActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this,LetterIndexActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this,MvpActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this,DataActivity.class));
                        break;
                    case R.id.button4:
                        startActivity(new Intent(this, SavaActivity.class));
                        break;
                    case R.id.button5:
                        startActivity(new Intent(this, DatePickerActivity.class));
                        break;
                    case R.id.button6:
                        startActivity(new Intent(this, RecyclerViewActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, LayoutInflaterActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this, GridViewActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this, FixProblemsActivity.class));
                        break;
                    case R.id.button4:
                        startActivity(new Intent(this, FragmentActivity.class));
                        break;
                    case R.id.button5:
                        startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 4:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, SocketClientActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this, AsyncTaskActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this, HandlerDemoActivity.class));
                        break;
                    case R.id.button4:
                        startActivity(new Intent(this, LoadPicActivity.class));
                        break;
                    case R.id.button5:
                        startActivity(new Intent(this, OkHttpDemoActivity.class));
                        break;
                    case R.id.button6:
                        startActivity(new Intent(this, GlideActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 5:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, SharedPreferenceActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this, RoomTestActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this, ParcelableDemoActivity.class));
                        break;
                    case R.id.button4:
                        startActivity(new Intent(this, ServiceDemoActivity.class));
                        break;
                    case R.id.button5:
                        //startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        //startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 6:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, CoordinateSystemActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this, FloatingViewActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this, ScrollerActivity.class));
                        break;
                    case R.id.button4:
                        //startActivity(new Intent(this, FragmentActivity.class));
                        break;
                    case R.id.button5:
                        //startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        //startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 7:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, AnimDemoActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this, MainSplashActivity.class));
                        break;
                    case R.id.button3:
                        //startActivity(new Intent(this, ScrollerActivity.class));
                        break;
                    case R.id.button4:
                        //startActivity(new Intent(this, FragmentActivity.class));
                        break;
                    case R.id.button5:
                        //startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        //startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 8:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, NotifyActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this, CameraXActivity.class));
                        break;
                    case R.id.button3:
                        //startActivity(new Intent(this, ScrollerActivity.class));
                        break;
                    case R.id.button4:
                        //startActivity(new Intent(this, FragmentActivity.class));
                        break;
                    case R.id.button5:
                        //startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        //startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
                        break;
                    default:
                        break;
                }
                break;

            case 9:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, DrawerLayoutActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this, CoordinatorLayoutActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this, TestActivity.class));
                        break;
                    case R.id.button4:
                        //startActivity(new Intent(this, FragmentActivity.class));
                        break;
                    case R.id.button5:
                        //startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        //startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 10:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, ViewModelActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this, RxDemoActivity.class));
                        break;
                    case R.id.button3:
                        //startActivity(new Intent(this, ScrollerActivity.class));
                        break;
                    case R.id.button4:
                        //startActivity(new Intent(this, FragmentActivity.class));
                        break;
                    case R.id.button5:
                        //startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        //startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;

        }
    }
}
