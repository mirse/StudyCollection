package com.wdz.studycollection.componentization;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wdz.studycollection.R;

public class ARouterDemoActivity extends AppCompatActivity {
    private static final String TAG = "ARouterDemoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_router_demo);
        ButterKnife.bind(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("title");
        builder.setMessage("XXXXXXX");
        builder.setCancelable(true);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }

    @OnClick(R.id.bt_router)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_router:
                ARouter.getInstance().build("/test/firstActivity")
                        .withString("key","1111111")
                        .navigation(this, new NavigationCallback() {
                            @Override
                            public void onFound(Postcard postcard) {

                                Log.i(TAG, "onFound: ");
                            }

                            @Override
                            public void onLost(Postcard postcard) {
                                Log.i(TAG, "onLost: ");
                            }

                            @Override
                            public void onArrival(Postcard postcard) {
                                Log.i(TAG, "onArrival: ");
                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {
                                Log.i(TAG, "onInterrupt: ");
                            }
                        });
                break;
        }
    }
}
