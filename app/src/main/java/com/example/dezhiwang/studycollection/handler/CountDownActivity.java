package com.example.dezhiwang.studycollection.handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;

import android.os.Bundle;
import android.util.Log;

import com.example.dezhiwang.studycollection.BR;
import com.example.dezhiwang.studycollection.handler.model.Login;
import com.example.dezhiwang.studycollection.R;
import com.example.dezhiwang.studycollection.databinding.ActivityCountDownBinding;

public class CountDownActivity extends AppCompatActivity {

    private Login login;
    private static final String TAG = "CountDownActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_count_down);
        ActivityCountDownBinding bind = DataBindingUtil.setContentView(this,R.layout.activity_count_down);
        login = new Login("11", "22","33");
        bind.setLoginInfo(login);
        bind.setLoginHandler(new LoginHandler());
        login.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (propertyId == com.example.dezhiwang.studycollection.BR.name){
                    Log.e(TAG, "onPropertyChanged: name");
                }
                if (propertyId == com.example.dezhiwang.studycollection.BR.check){
                    Log.e(TAG, "onPropertyChanged: check");
                }
            }
        });
    }

    public class LoginHandler{
        public void changeLoginName(){
            login.setName("改变name");
            login.setCheck("顺便改check");
        }

        public void changeLoginPwd(){
            login.setPwd("改变pwd");
            login.setCheck("顺便改check");
        }

    }
}
