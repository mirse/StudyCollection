package com.example.dezhiwang.studycollection.DataSave.Room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dezhiwang.studycollection.DataSave.Room.Entity.Users;
import com.example.dezhiwang.studycollection.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomTestActivity extends AppCompatActivity {

    private UserDao userDao;

    @BindView(R.id.bt_insert)
    Button mBtnInsert;
    @BindView(R.id.bt_search)
    Button mBtnSearch;
    @BindView(R.id.tv_status)
    TextView mTvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_test);
        ButterKnife.bind(RoomTestActivity.this);
        init();
    }
    @OnClick({R.id.bt_insert,R.id.bt_search})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_insert:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userDao.insertAll(new Users(1,"name",2001));
                    }
                });

                break;
            case R.id.bt_search:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Users> users = userDao.loadAll();
                        for (int i=0;i<users.size();i++){
                            Toast.makeText(getApplicationContext(),users.get(i).toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            default:
                break;
        }
    }

    private void init() {
        AppDataBase appDataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "users.db").build();
        userDao = appDataBase.userDao();
    }
}
