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
    public int starti =0;
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
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        getWindow().setAttributes(lp);
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN|SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);






        RecyclerView commentList = findViewById(R.id.comment_list);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
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
                Log.i(TAG, "onScrollChange: dy:"+dy+" y:"+y+" translateY:"+translateY+" height:"+linearLayout2.getHeight()+" lp3.topMargin:"+lp3.topMargin);
                linearLayout2.setTranslationY(translateY);;
                Log.i(TAG, "onScrolled: "+ivBulb.getHeight()+" ivBulb.getTop():"+ivBulb.getY());

                float scale = 1+ (translateY)/(ivBulb.getHeight());

                translate = dy-starti;
                starti = translate;

                ivBulb.setPivotY(lp3.topMargin+600-translate);
                ivBulb.setScaleX(scale);
                ivBulb.setScaleY(scale);


//                //获得Bitmap的高和宽
//                int bmpWidth=bitmap.getWidth();
//                int bmpHeight=bitmap.getHeight();
//
////设置缩小比例
//
////计算出这次要缩小的比例
//                float scaleWidth=(float)(bmpWidth*scale);
//                float scaleHeight=(float)(bmpHeight*scale);
//
////产生resize后的Bitmap对象
//                Matrix matrix=new Matrix();
//                matrix.postScale(scaleWidth, scaleHeight);
//                Bitmap resizeBmp=Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);
//
//                ivBulb.setImageBitmap(resizeBmp);
//


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
