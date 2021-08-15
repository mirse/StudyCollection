package com.wdz.studycollection.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
//import com.example.addservice.IMyAidlInterface;
import com.tencent.bugly.crashreport.CrashReport;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.common.util.StringArrayUtils;
import com.wdz.module_basis.basis.service.MyService;
import com.wdz.studycollection.R;
import com.wdz.studycollection.main.xmlparse.SaxHelper;


import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.os.Environment.DIRECTORY_MUSIC;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.viewPager2)
    ViewPager2 viewPager2;
    @BindView(R.id.ll_tab_1)
    LinearLayout llTab1;
    @BindView(R.id.ll_tab_2)
    LinearLayout llTab2;
    @BindView(R.id.ll_tab_3)
    LinearLayout llTab3;
    @BindView(R.id.ll_tab_4)
    LinearLayout llTab4;
    static float sNoncompatDensity = 0;
    static float sNoncompatScaledDensity;
    private List<Fragment> fragmentArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();

        //getPath - 取得相对路径 getAbsolutePath - 取得绝对路径

        Log.i(TAG, "onCreate:getFilesDir： "+getFilesDir().getAbsolutePath());
        Log.i(TAG, "onCreate:getCacheDir： "+getCacheDir ().getAbsolutePath());
        //
        Log.i(TAG, "onCreate:getExternalCacheDir： "+getExternalCacheDir ().getAbsolutePath());
        Log.i(TAG, "onCreate:getExternalFilesDir： "+getExternalFilesDir (DIRECTORY_MUSIC).getAbsolutePath());
        //
        Log.i(TAG, "onCreate:getExternalStorageState： "+ Environment.getExternalStorageState());
        Log.i(TAG, "onCreate:getExternalStoragePublicDirectory： "+ Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC));
        Log.i(TAG, "onCreate:getExternalStorageDirectory： "+ Environment.getExternalStorageDirectory());

        //
        Log.i(TAG, "onCreate:getRootDirectory： "+ Environment.getRootDirectory().getAbsolutePath());
        Log.i(TAG, "onCreate:getDataDirectory： "+ Environment.getDataDirectory().getAbsolutePath());
        Log.i(TAG, "onCreate:getDownloadCacheDirectory： "+ Environment.getDownloadCacheDirectory().getAbsolutePath());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int dpi = (int) (dm.density*160);
        setCustomDensity(this,getApplication());
        Log.i(TAG, "onCreate: "+dpi);
        testAidl();

        textXmlParse();
    }

    private void textXmlParse() {
        try {
            InputStream inputStream = getAssets().open("parse.xml");
            SaxHelper saxHelper = new SaxHelper();
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(inputStream,saxHelper);
            inputStream.close();
            Log.i(TAG, "textXmlParse: "+saxHelper.getPersons());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void testAidl() {

        //buildTools如果是29.0.0 编译会报错 com.android.ide.common.workers.WorkerExecutorException:
//        Intent intent = new Intent("com.example.addservice.IMyAidlInterface");
//        intent.setPackage("com.example.addservice");
//        bindService(intent, new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
//                try {
//                    int count = iMyAidlInterface.add(1, 2);
//                    Log.i(TAG, "onServiceConnected: "+count);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//                Log.i(TAG, "onServiceDisconnected: ");
//            }
//        }, Context.BIND_AUTO_CREATE);
    }


    /**
     * 字节跳动屏幕适配方式
     * @param activity
     * @param application
     */
    private static void setCustomDensity(Activity activity, Application application){
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();


        if (sNoncompatDensity == 0){
            //设备独立像素
            sNoncompatDensity = appDisplayMetrics.density;
            //屏幕上显示的字体比例
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks(){
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig !=null && newConfig.fontScale>0){
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }


        float targetDensity = (float) appDisplayMetrics.widthPixels / 360;
        int targetDensityDpi = (int) (160*targetDensity);
        float targetScaleDensity = targetDensity*(sNoncompatScaledDensity/sNoncompatDensity);
        appDisplayMetrics.density =  targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        //屏幕密度：每英寸点数
        appDisplayMetrics.densityDpi = targetDensityDpi;

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density =  targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;

    }


    private void initData() {
        fragmentArrayList.clear();
        fragmentArrayList.add((Fragment) ARouter.getInstance().build(ARouterConstant.FRAGMENT_CUSTOM_VIEW).navigation());
        fragmentArrayList.add((Fragment) ARouter.getInstance().build(ARouterConstant.FRAGMENT_CUMMUNICATION).navigation());
        fragmentArrayList.add((Fragment) ARouter.getInstance().build(ARouterConstant.FRAGMENT_ARCHITECTURE).navigation());
        fragmentArrayList.add((Fragment) ARouter.getInstance().build(ARouterConstant.FRAGMENT_BASIS).navigation());
        FragmentStateAdapter fragmentAdapter = new FragmentAdapter(this,fragmentArrayList);
        viewPager2.setAdapter(fragmentAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                selectBottomTab(position);
            }
        });
        viewPager2.setCurrentItem(0);

    }
    @OnClick({R.id.ll_tab_1,R.id.ll_tab_2,R.id.ll_tab_3,R.id.ll_tab_4})
    public void onClick(View view){
        llTab1.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab2.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab3.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab4.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        if (R.id.ll_tab_1 == view.getId()){
            viewPager2.setCurrentItem(0,false);
            llTab1.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
        }
        else if (R.id.ll_tab_2 == view.getId()){
            viewPager2.setCurrentItem(1,false);
            llTab2.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
        }
        else if (R.id.ll_tab_3 == view.getId()){
            viewPager2.setCurrentItem(2,false);
            llTab3.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
        }
        else if (R.id.ll_tab_4 == view.getId()){
            viewPager2.setCurrentItem(3,false);
            llTab4.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
        }
    }

    private void selectBottomTab(int position) {
        String homeTabValue = StringArrayUtils.getInstance().getHomeTabValue(position);
        Log.i(TAG, "selectBottomTab: "+homeTabValue);

        llTab1.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab2.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab3.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab4.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        switch (position){
            case 0:
                llTab1.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
                break;
            case 1:
                llTab2.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
                break;
            case 2:
                llTab3.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
                break;
            case 3:
                llTab4.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
                break;
            default:
                break;
        }
    }
}
