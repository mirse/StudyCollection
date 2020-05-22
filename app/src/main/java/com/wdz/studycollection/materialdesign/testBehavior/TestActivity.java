package com.wdz.studycollection.materialdesign.testBehavior;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wdz.studycollection.R;
import com.wdz.studycollection.materialdesign.testBehavior.adapter.MyAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.relativeLayout1)
    ConstraintLayout relativeLayout1;
    public float newScale = 1;
    public int translate = 0;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        getWindow().setAttributes(lp);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN|SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);






        RecyclerView commentList = findViewById(R.id.comment_list);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(i + 1 + "");
        }
        //这是普通的adapter
        MyAdapter adapter = new MyAdapter();
        adapter.addDatas(list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        commentList.setLayoutManager(gridLayoutManager);
        commentList.setAdapter(adapter);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_header, commentList, false);
        ImageView ivBulb = view.findViewById(R.id.iv_bulb);
        LinearLayout roootHead = view.findViewById(R.id.root_head);
        adapter.setHeaderView(view);


        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        relativeLayout1.measure(w, h);
        int height = relativeLayout1.getMeasuredHeight();

        roootHead.measure(w,h);
        ivBulb.measure(w,h);
        linearLayout2.measure(w,h);

        RecyclerView.LayoutParams lp3 = (RecyclerView.LayoutParams) roootHead.getLayoutParams();
        Log.i(TAG, "onCreate: statusHeight:"+getStatusBarHeight()+" height:"+height+" lp3.height:"+roootHead.getMeasuredHeight());

        lp3.topMargin = getStatusBarHeight()+height;

        Log.i(TAG, "onCreate: iv height:"+ivBulb.getMeasuredHeight());

        commentList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                float y = linearLayout2.getY();
                float translateY = y - dy;
                linearLayout2.setTranslationY(translateY);
                float scale = 1 + (translateY)/(ivBulb.getMeasuredHeight());
                Log.i(TAG, "onScrolled: "+scale);
                if (scale<=0.1){
                    relativeLayout1.setBackgroundResource(R.drawable.bg_title);
                    scale = 0;
                }
                else{
                    relativeLayout1.setBackgroundColor(getResources().getColor(R.color.transparent));
                }


                translate +=dy;
                ivBulb.setScaleX(scale);
                ivBulb.setScaleY(scale);

                ivBulb.setPivotX((float) ivBulb.getMeasuredWidth()/2);
                ivBulb.setPivotY(ivBulb.getMeasuredHeight());




            }
        });

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
