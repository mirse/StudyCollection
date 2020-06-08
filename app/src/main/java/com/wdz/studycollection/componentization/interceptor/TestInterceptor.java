package com.wdz.studycollection.componentization.interceptor;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;

import static com.wdz.studycollection.componentization.activity.FirstActivity.NEED_LOGIN;

/**
 * Created by dezhi.wang on 2020/5/28.
 */
@Interceptor(priority = 8,name = "登录跳转拦截器")
public class TestInterceptor implements IInterceptor {
    private static final String TAG = "TestInterceptor";
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (NEED_LOGIN == postcard.getExtra()){



//            boolean isLogin = false;
//            if (isLogin){
//                callback.onContinue(postcard);
//            }
//            else{
//                callback.onInterrupt(new Throwable("被拦截了"));
//                ARouter.getInstance().build("/test/twoActivity").navigation();
//            }

            Log.i(TAG, "process: "+Thread.currentThread().getName());
            Looper.prepare();
            Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();

            ARouter.getInstance().build("/test/twoActivity").navigation();
            //处理完成，交还控制权
            callback.onInterrupt(null);
            Looper.loop();


        }
        else {
            callback.onContinue(postcard);
        }

    }
    Context context;
    @Override
    public void init(Context context) {
        this.context = context;

    }
}
