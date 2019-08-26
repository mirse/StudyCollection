package com.example.dezhiwang.studycollection.Mvp.WriteAndRead;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dezhiwang.studycollection.Mvp.WriteAndRead.Presenter.UserPresenter;
import com.example.dezhiwang.studycollection.Mvp.WriteAndRead.View.BaseView;
import com.example.dezhiwang.studycollection.R;

public class SavaActivity extends AppCompatActivity implements BaseView{

    private EditText mLast;
    private EditText mFirst;
    private EditText mId;
    private UserPresenter userPresenter;
    private TextView tvUser;
    private Button btSava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sava);
        mId = findViewById(R.id.et_id);
        mFirst = findViewById(R.id.et_first);
        mLast = findViewById(R.id.et_last);
        btSava = findViewById(R.id.bt_sava);
        Button btRead = findViewById(R.id.bt_read);
        tvUser = findViewById(R.id.tv_user);
        userPresenter = new UserPresenter(this,this);
        btSava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPresenter.savaData(getInt(),getFirst(),getLast());
                userPresenter.setSelected(true);
            }
        });
        btRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPresenter.loadData(getInt());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userPresenter.detachView();
    }


    @Override
    public void setUser(String msg) {
        tvUser.setText(msg);
    }

    @Override
    public String getFirst() {
        return mFirst.getText().toString();
    }

    @Override
    public String getLast() {
        return mLast.getText().toString();
    }

    @Override
    public int getInt() {
        return Integer.parseInt(mId.getText().toString());
    }

    @Override
    public void setSelect(boolean is) {
        btSava.setSelected(is);
    }
}
