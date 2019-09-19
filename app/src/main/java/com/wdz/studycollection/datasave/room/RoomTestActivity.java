package com.wdz.studycollection.datasave.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wdz.studycollection.R;
import com.wdz.studycollection.datasave.room.entity.Users;
import com.wdz.studycollection.datasave.room.entity.UsersAll;
import com.wdz.studycollection.datasave.room.entity.UsersChild;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomTestActivity extends AppCompatActivity {

    private static final String TAG = "RoomTestActivity";
    private UserDao userDao;

    @BindView(R.id.bt_insert)
    Button mBtnInsert;
    @BindView(R.id.bt_find)
    Button mBtnFind;
    @BindView(R.id.bt_insert_child)
    Button mBtnInsertChild;
    @BindView(R.id.bt_delete)
    Button mBtnDelete;
    @BindView(R.id.bt_find_child)
    Button mBtnFindChild;


    @BindView(R.id.et_id)
    EditText mEtId;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_pro)
    EditText mEtPro;

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
    @OnClick({R.id.bt_insert,R.id.bt_find,R.id.bt_insert_child,R.id.bt_delete,R.id.bt_delete_child,R.id.bt_find_child})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_insert:
                new insertAsyncTask(userDao,mEtId,mEtName,mEtPro).execute();
                break;
            case R.id.bt_insert_child:
                new insertChildAsyncTask(userDao,mEtId,mEtName,mEtPro).execute();
                break;
            case R.id.bt_find:
                new loadAsyncTask(userDao,mEtId).execute();
                break;
            case R.id.bt_delete:
                new deleteAsyncTask(userDao).execute();
                break;
            case R.id.bt_delete_child:
                new deleteChildAsyncTask(userDao).execute();
                break;
            case R.id.bt_find_child:
                new loadChildAsyncTask(userDao).execute();
                break;

            default:
                break;
        }
    }


    private static class deleteAsyncTask extends AsyncTask<Users, String, String> {

        private UserDao userDao;

        deleteAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected String doInBackground(Users... users) {
            userDao.deleteAll();
            return "success";
        }

        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"删除："+aVoid);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

    private static class deleteChildAsyncTask extends AsyncTask<Users, String, String> {

        private UserDao userDao;

        deleteChildAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected String doInBackground(Users... users) {
            userDao.deleteAllChild();
            return "success";
        }

        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"删除child："+aVoid);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }


    private static class loadChildAsyncTask extends AsyncTask<Users, String, String> {

        private UserDao userDao;

        loadChildAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected String doInBackground(Users... users) {
            List<UsersChild> users1 = userDao.findAllChild();
            for (int i=0;i<users1.size();i++){
                return users1.toString();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"查询child："+aVoid);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }


    private static class loadAsyncTask extends AsyncTask<Users, String, String> {

        private UserDao userDao;
        //private final int id;

        loadAsyncTask(UserDao userDao,EditText mEtId) {
            this.userDao = userDao;
//            id = Integer.parseInt(mEtId.getText().toString());
        }

        @Override
        protected String doInBackground(Users... users) {
            List<UsersAll> users1 = userDao.findAll();
            for (int i=0;i<users1.size();i++){
                return users1.toString();
            }
           return "";
        }

        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"查询："+aVoid);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }


    private static class insertAsyncTask extends AsyncTask<Users, Void, String> {

        private UserDao userDao;
        private EditText mEtId,mEtName,mEtPro;
        private final int id;
        private final String name;
        private final String identify;

        insertAsyncTask(UserDao userDao,EditText mEtId,EditText mEtName,EditText mEtPro) {
            this.userDao = userDao;
            this.mEtName = mEtName;
            this.mEtId = mEtId;
            this.mEtPro = mEtPro;

            id = Integer.parseInt(mEtId.getText().toString());
            name = mEtName.getText().toString();
            identify = mEtPro.getText().toString();


        }

        @Override
        protected String doInBackground(Users... users) {
            userDao.insertAll(new Users(id,name,identify));
            return "insert success";
        }

        //doInBackground 执行完成后
        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"插入："+aVoid);
            super.onPostExecute(aVoid);
        }

        //在publishProgress方法被调用后
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    private static class insertChildAsyncTask extends AsyncTask<Users, Void, String> {

        private UserDao userDao;
        private EditText mEtId,mEtName,mEtPro;
        private final int id;
        private final String name;
        private final String identify;

        insertChildAsyncTask(UserDao userDao,EditText mEtId,EditText mEtName,EditText mEtPro) {
            this.userDao = userDao;
            this.mEtName = mEtName;
            this.mEtId = mEtId;
            this.mEtPro = mEtPro;

            id = Integer.parseInt(mEtId.getText().toString());
            name = mEtName.getText().toString();
            identify = mEtPro.getText().toString();


        }

        @Override
        protected String doInBackground(Users... users) {
            userDao.insertAll(new UsersChild(id,name,identify));
            return "insert success";
        }

        //doInBackground 执行完成后
        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"插入："+aVoid);
            super.onPostExecute(aVoid);
        }

        //在publishProgress方法被调用后
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



}
