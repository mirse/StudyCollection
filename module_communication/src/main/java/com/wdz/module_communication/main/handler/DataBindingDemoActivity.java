package com.wdz.module_communication.main.handler;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.wdz.module_communication.R;
import com.wdz.module_communication.databinding.ModuleCommunicationActivityCountDownBinding;
import com.wdz.module_communication.main.handler.model.ObservableLogin;

public class DataBindingDemoActivity extends AppCompatActivity {

    private ObservableLogin login;
    private static final String TAG = "DataBindingDemoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ModuleCommunicationActivityCountDownBinding bind = DataBindingUtil.setContentView(this, R.layout.module_communication_activity_count_down);
        login = new ObservableLogin(new ObservableField<String>("11"), new ObservableField<String>("22"),new ObservableField<String>("33"));
        bind.setObservableLogin(login);

        bind.btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.getName().set("change");
            }
        });
//        bind.setLoginHandler(new LoginHandler());
//        login.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
//            @Override
//            public void onPropertyChanged(Observable sender, int propertyId) {
//                if (propertyId == com.example.dezhiwang.studycollection.BR.name){
//                    Log.e(TAG, "onPropertyChanged: name");
//                }
//                if (propertyId == com.example.dezhiwang.studycollection.BR.check){
//                    Log.e(TAG, "onPropertyChanged: check");
//                }
//            }
//        });
    }

//    public class LoginHandler{
//        public void changeLoginName(){
//            Log.i(TAG,"changeLoginName---");
//            login.setName("改变name");
//            login.setCheck("顺便改check");
//        }
//
//        public void changeLoginPwd(){
//            Log.i(TAG,"changeLoginPwd---");
//            login.setPwd("改变pwd");
//            login.setCheck("顺便改check");
//        }
//
//    }


}
