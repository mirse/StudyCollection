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
        String language = locale.getLanguage() + "-" + locale.getCountry();


    }
}
