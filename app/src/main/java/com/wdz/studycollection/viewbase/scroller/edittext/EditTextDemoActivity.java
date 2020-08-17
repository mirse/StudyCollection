package com.wdz.studycollection.viewbase.scroller.edittext;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.wdz.studycollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTextDemoActivity extends AppCompatActivity {
    private static final String TAG = "EditTextDemoActivity";
    @BindView(R.id.editText)
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_demo1);
        ButterKnife.bind(this);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        //适配凹凸异形屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        SoftKeyBoardListener softKeyBoardListener = new SoftKeyBoardListener(this);
        softKeyBoardListener.setListener( new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                Log.i(TAG, "keyBoardShow: ");

            }

            @Override
            public void keyBoardHide(int height) {
                Log.i(TAG, "keyBoardHide: ");
            }
        });
        softKeyBoardListener.removeListener();


    }
}
