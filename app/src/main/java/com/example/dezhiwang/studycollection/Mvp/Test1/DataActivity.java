package com.example.dezhiwang.studycollection.Mvp.Test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dezhiwang.studycollection.Mvp.Test1.Presenter.MainPresenter;
import com.example.dezhiwang.studycollection.Mvp.Test1.view.DataView;
import com.example.dezhiwang.studycollection.R;

public class DataActivity extends AppCompatActivity implements DataView{

    private TextView mData;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Button mGet = findViewById(R.id.bt_get);
        mData = findViewById(R.id.tv_data);
        mainPresenter = new MainPresenter(this);
        mGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.loadData();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    public void showData(String data) {
        mData.setText(data);
    }

    @Override
    public void showLoading() {
        Toast.makeText(this,"加载中......",Toast.LENGTH_SHORT).show();
    }
}
