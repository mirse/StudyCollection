package com.wdz.studycollection.materialdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.wdz.studycollection.R;

public class CoordinatorLayoutActivity extends AppCompatActivity {
    private static final String TAG = "CoordinatorLayoutActivi";
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                Log.i(TAG, "onOffsetChanged: "+i+" range:"+appBarLayout.getTotalScrollRange());
                if (Math.abs(i) >= appBarLayout.getTotalScrollRange()){
                    //toolbar.setBackgroundColor(getResources().getColor(R.color.bg_top));
                }
                else{
                    //toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
                }

            }
        });
//        Window window = getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.TRANSPARENT);
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
}
