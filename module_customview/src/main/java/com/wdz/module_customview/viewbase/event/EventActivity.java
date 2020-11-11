package com.wdz.module_customview.viewbase.event;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.base.BaseActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;
import com.wdz.module_customview.viewbase.event.view.MyButton;
import com.wdz.module_customview.viewbase.event.view.FlowLayout;
import com.wdz.module_customview.viewbase.event.view.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
@Route(path = ARouterConstant.ACTIVITY_LAYOUT_EVENT)
public class EventActivity extends AppCompatActivity {
    private static final String TAG = "MyButton";
//    @BindView(R2.id.btn)
//    MyButton btn;
//    @BindView(R2.id.ll_root)
//    FlowLayout ll;
//    @BindView(R2.id.seekbar_width)
//    SeekBar seekBarWidth;
//    @BindView(R2.id.seekbar_height)
//    SeekBar seekBarHeight;
//    @BindView(R2.id.squareImageView)
//    SquareImageView squareImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
//        ll.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.i(TAG, "ll onTouch: ");
//                return false;
//            }
//        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "onClick: ");
//            }
//        });
//        btn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        Log.i(TAG, "onTouch: ACTION_DOWN");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.i(TAG, "onTouch: ACTION_MOVE");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.i(TAG, "onTouch: ACTION_UP");
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
//        final ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) squareImageView.getLayoutParams();
//        seekBarWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Log.i(TAG, "onProgressChanged: "+(float)progress/100);
//                layoutParams.width = (int) (((float)progress/100)*dp2px(EventActivity.this,100));
//                squareImageView.setLayoutParams(layoutParams);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }

    /**
     * dp->px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

