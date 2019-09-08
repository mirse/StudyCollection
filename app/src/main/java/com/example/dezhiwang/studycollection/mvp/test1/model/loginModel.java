package com.example.dezhiwang.studycollection.mvp.test1.model;

import android.os.Handler;

public class loginModel extends BaseModel<String> {
    @Override
    public void execute(final onLoadCompleteListener<String> onLoadCompleteListener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               if ("wdz".equals(mParams[0])&& "123".equals(mParams[1])){
                    onLoadCompleteListener.onComplete("login success");
               }
               else{
                   onLoadCompleteListener.onFailed("login failed");
               }
            }
        },2000);
    }
}
