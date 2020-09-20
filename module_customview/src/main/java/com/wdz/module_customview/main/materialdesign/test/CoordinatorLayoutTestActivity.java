package com.wdz.module_customview.main.materialdesign.test;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

public class CoordinatorLayoutTestActivity extends AppCompatActivity {
    private static final String TAG = "CoordinatorLayoutActivi";
    @BindView(R2.id.relativeLayout1)
    ConstraintLayout relativeLayout1;
    @BindView(R2.id.relativeLayout)
    ConstraintLayout relativeLayout;
    @BindView(R2.id.mCollapsingToolbarLayout)
    CollapsingToolbarLayout collapsing;
    @BindView(R2.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R2.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R2.id.iv_bulb)
    ImageView ivBulb;
    @BindView(R2.id.rv_mood)
    RecyclerView mRvMood;
    @BindView(R2.id.ll_root)
    LinearLayout llRoot;
    private MoodSettingTestAdapter mMoodSettingAdapter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_test_layout);
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        getWindow().setAttributes(lp);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN|SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        relativeLayout.measure(w, h);


        int height = relativeLayout.getMeasuredHeight();
        CoordinatorLayout.LayoutParams lp1 = (CoordinatorLayout.LayoutParams) relativeLayout.getLayoutParams();
        lp1.height = getStatusBarHeight()+height;
        relativeLayout.setLayoutParams(lp1);
        relativeLayout.setPadding(0,getStatusBarHeight(),0,0);


        relativeLayout1.measure(w, h);
        ConstraintLayout.LayoutParams lp2 = (ConstraintLayout.LayoutParams) relativeLayout1.getLayoutParams();
        lp2.height = getStatusBarHeight()+height;
        relativeLayout1.setLayoutParams(lp2);
        relativeLayout1.setPadding(0,getStatusBarHeight(),0,0);

//        CoordinatorLayout.LayoutParams lp3 = (CoordinatorLayout.LayoutParams) ivBulb.getLayoutParams();
//        lp3.topMargin = getStatusBarHeight()+height;
        CollapsingToolbarLayout.LayoutParams lp3 = (CollapsingToolbarLayout.LayoutParams) llRoot.getLayoutParams();
        lp3.topMargin = getStatusBarHeight()+height;


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        List<Mood> moods = new ArrayList<>();
        moods.add(new Mood());
        moods.add(new Mood());
        moods.add(new Mood());
        mMoodSettingAdapter = new MoodSettingTestAdapter(moods,this);
        mRvMood.setLayoutManager(gridLayoutManager);
        mRvMood.setAdapter(mMoodSettingAdapter);



        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                Log.i(TAG, "onOffsetChanged: "+ivBulb.getHeight()+" ivBulb.getTop():"+ivBulb.getTop());
                float scale =1 - (float) Math.abs(i)/ivBulb.getHeight();

                ivBulb.setScaleX(scale);
                ivBulb.setScaleY(scale);
                Log.i(TAG, "onOffsetChanged: i:"+i+" appBarLayout:"+appBarLayout.getTotalScrollRange());
            }
        });

        mRvMood.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                RecyclerView.LayoutManager layoutManager = mRvMood.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    int childCount = linearManager.getChildCount();
                    int itemCount = linearManager.getItemCount();
                    Log.i(TAG, "onScrollChange: childCount:"+childCount+" itemCount:"+itemCount);
                    int lastVisibleItemPosition = linearManager.findLastVisibleItemPosition();
                    int lastCompletelyVisibleItemPosition = linearManager.findLastCompletelyVisibleItemPosition();
                    Log.i(TAG, "onScrollChange: lastVisibleItemPosition:"+lastVisibleItemPosition+"lastCompletelyVisibleItemPosition:"+lastCompletelyVisibleItemPosition);
                }
            }
        });


       // banAppBarScroll(false);


    }

    /**
     * 控制appbar的滑动
     * @param isScroll true 允许滑动 false 禁止滑动
     */
    private void banAppBarScroll(boolean isScroll){
        View mAppBarChildAt = appBarLayout.getChildAt(0);
        AppBarLayout.LayoutParams  mAppBarParams = (AppBarLayout.LayoutParams)mAppBarChildAt.getLayoutParams();
        if (isScroll) {
            mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
            mAppBarChildAt.setLayoutParams(mAppBarParams);
        } else {
            mAppBarParams.setScrollFlags(0);
        }

    }

    @OnClick(R2.id.onoff)
    public void onClick(View view)
    {
        if (view.getId() == R.id.onoff) {
            mMoodSettingAdapter.addItem();
        }
    }
    /**
     * 获取statusBar高度
     */
    private int getStatusBarHeight(){
        int height = 0;
        int resourceId = getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * dp->px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    static class Mood{
        public String moodName;
    }

}
