package com.wdz.module_customview.main.materialdesign;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
@Route(path = ARouterConstant.ACTIVITY_COORDINATOR)
public class CoordinatorLayoutActivity extends AppCompatActivity {
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
    private MoodSettingAdapter mMoodSettingAdapter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
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

        CoordinatorLayout.LayoutParams lp3 = (CoordinatorLayout.LayoutParams) ivBulb.getLayoutParams();
        lp3.topMargin = getStatusBarHeight()+height;





        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        List<Mood> moods = new ArrayList<>();
        moods.add(new Mood());
        moods.add(new Mood());
        moods.add(new Mood());
        mMoodSettingAdapter = new MoodSettingAdapter(moods,this);
        mRvMood.setLayoutManager(gridLayoutManager);
        mRvMood.setAdapter(mMoodSettingAdapter);


        //banAppBarScroll(false);


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
