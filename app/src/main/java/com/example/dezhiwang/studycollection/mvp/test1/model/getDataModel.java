package com.example.dezhiwang.studycollection.mvp.test1.model;

import android.os.Handler;

public class getDataModel extends BaseModel<String> {
    @Override
    public void execute(final onLoadCompleteListener<String> onLoadCompleteListener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (mParams[0]){
                    case "success":
                        onLoadCompleteListener.onComplete("success");
                        break;
                    case "failed":
                        onLoadCompleteListener.onFailed("failed");
                        break;
                     default:
                         break;
                }
            }
        },2000);
    }
}
