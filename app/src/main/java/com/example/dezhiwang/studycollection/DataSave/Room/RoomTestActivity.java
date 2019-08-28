package com.example.dezhiwang.studycollection.DataSave.Room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dezhiwang.studycollection.DataSave.Room.Entity.Users;
import com.example.dezhiwang.studycollection.R;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomTestActivity extends AppCompatActivity {

    private static final String TAG = "RoomTestActivity";
    private UserDao userDao;

    @BindView(R.id.bt_insert)
    Button mBtnInsert;
    @BindView(R.id.bt_search)
    Button mBtnSearch;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_year)
    EditText mEtYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_test);
        ButterKnife.bind(RoomTestActivity.this);
        init();
    }
    private void init() {
        AppDataBase appDataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "users.db").build();
        userDao = appDataBase.userDao();
    }
    @OnClick({R.id.bt_insert,R.id.bt_search})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_insert:
                new insertAsyncTask(userDao,mEtName,mEtYear).execute();
                break;
            case R.id.bt_search:
                new loadAsyncTask(userDao,mTvStatus).execute();
                break;
            default:
                break;
        }
    }


    private static class loadAsyncTask extends AsyncTask<Users, String, String> {

        private UserDao userDao;
        private TextView mTvText;

        loadAsyncTask(UserDao userDao, TextView mTvText) {
            this.userDao = userDao;
            this.mTvText = mTvText;

        }

        @Override
        protected String doInBackground(Users... users) {
            List<Users> users1 = userDao.loadAll();
            for (int i=0;i<users1.size();i++){
                publishProgress(users1.get(i).getName());
            }
            return "";
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            mTvText.setText(values[0]);
        }
    }


    private static class insertAsyncTask extends AsyncTask<Users, Void, Void> {

        private UserDao userDao;
        private EditText mEtName;
        private EditText mEtYear;


        insertAsyncTask(UserDao userDao,EditText mEtName,EditText mEtYear) {
            this.userDao = userDao;
            this.mEtName = mEtName;
            this.mEtYear = mEtYear;

        }

        @Override
        protected Void doInBackground(Users... users) {
            userDao.insertAll(new Users(mEtName.getText().toString(),mEtYear.getText().toString()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



}
