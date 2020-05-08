package com.wdz.studycollection.viewbase.scroller;

import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wdz.studycollection.R;

import java.util.Locale;
import java.util.Objects;

public class ScrollerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        Locale locale = Locale.getDefault();
//Locale.getDefault() 和 LocaleList.getAdjustedDefault().get(0) 同等效果，还不需要考虑版本问题，推荐直接使用
        String language = locale.getLanguage() + "-" + locale.getCountry();
        Toast.makeText(getApplicationContext(),"language:"+language,Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(R.layout.dialog_check_app_new_version);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.5);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.6);    //宽度设置为屏幕的0.5
        alertDialog.getWindow().setAttributes(p);

    }
}
