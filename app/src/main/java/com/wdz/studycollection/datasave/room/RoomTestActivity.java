package com.wdz.studycollection.datasave.room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wdz.studycollection.R;
import com.wdz.studycollection.base.PermissionActivity;
import com.wdz.studycollection.datasave.room.entity.Clothes;
import com.wdz.studycollection.datasave.room.entity.MyAddress;
import com.wdz.studycollection.datasave.room.entity.Person;
import com.wdz.studycollection.datasave.room.entity.PersonInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomTestActivity extends PermissionActivity {

    private static final String TAG = "RoomTestActivity";
    private PersonDao personDao;


    @BindView(R.id.et_person_name)
    EditText mEtPersonName;
    @BindView(R.id.et_person_age)
    EditText mEtPersonAge;
    @BindView(R.id.et_clothes_color)
    EditText mEtClothesColor;
    @BindView(R.id.et_father_id)
    EditText mEtFatherId;
    @BindView(R.id.tv_db)
    TextView mTvDb;
    @BindView(R.id.tv_clothes)
    TextView mTvClothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_test);
        ButterKnife.bind(RoomTestActivity.this);
        initMorePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.READ_CONTACTS});
    }


    @Override
    protected void alreadyGetPermission() {
        DBInstance.getInstance().personDao().findPerson().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                mTvDb.setText(people.toString());
            }
        });

        DBInstance.getInstance().clothesDao().findAllClothes().observe(this, new Observer<List<Clothes>>() {
            @Override
            public void onChanged(List<Clothes> people) {
                mTvClothes.setText(people.toString());
            }
        });
    }

    @Override
    protected void onGetPermission() {
        DBInstance.getInstance().personDao().findPerson().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                mTvDb.setText(people.toString());
            }
        });

        DBInstance.getInstance().clothesDao().findAllClothes().observe(this, new Observer<List<Clothes>>() {
            @Override
            public void onChanged(List<Clothes> people) {
                mTvClothes.setText(people.toString());
            }
        });
    }

    @Override
    protected void onDenyPermission() {
        finish();
    }

//    private void initPermission() {
//        int hasWriteStoragePermission  = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED){
//
//            DBInstance.getInstance().personDao().findPerson().observe(this, new Observer<List<Person>>() {
//                @Override
//                public void onChanged(List<Person> people) {
//                    mTvDb.setText(people.toString());
//                }
//            });
//
//            DBInstance.getInstance().clothesDao().findAllClothes().observe(this, new Observer<List<Clothes>>() {
//                @Override
//                public void onChanged(List<Clothes> people) {
//                    mTvClothes.setText(people.toString());
//                }
//            });
//
//        }
//        else{
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode==0){
//            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//
//                DBInstance.getInstance().personDao().findPerson().observe(this, new Observer<List<Person>>() {
//                    @Override
//                    public void onChanged(List<Person> people) {
//                        mTvDb.setText(people.toString());
//                    }
//                });
//
//                DBInstance.getInstance().clothesDao().findAllClothes().observe(this, new Observer<List<Clothes>>() {
//                    @Override
//                    public void onChanged(List<Clothes> people) {
//                        mTvClothes.setText(people.toString());
//                    }
//                });
//
//            }
//            else{
//                finish();
//            }
//        }
//    }


    @OnClick({R.id.bt_add_person,R.id.bt_add_clothes,R.id.bt_find_person})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_add_person:
                new insertPersonAsyncTask().execute(new Person(mEtPersonName.getText().toString(),Integer.parseInt(mEtPersonAge.getText().toString())));
                break;
            case R.id.bt_add_clothes:
                new insertClothesAsyncTask(personDao,mEtClothesColor,mEtFatherId).execute();
                break;
            case R.id.bt_find_person:
//                new getPersonInfoTask(mEtFatherId).execute();
                new getPersonInfoTask(mEtFatherId).execute();
                break;

            default:
                break;
        }
    }


//    private static class deleteAsyncTask extends AsyncTask<Users, String, String> {
//
//        private UserDao userDao;
//
//        deleteAsyncTask(UserDao userDao) {
//            this.userDao = userDao;
//        }
//
//        @Override
//        protected String doInBackground(Users... users) {
//            userDao.deleteAll();
//            return "success";
//        }
//
//        @Override
//        protected void onPostExecute(String aVoid) {
//            Log.i(TAG,"删除："+aVoid);
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//        }
//    }
//
//    private static class deleteChildAsyncTask extends AsyncTask<Users, String, String> {
//
//        private UserDao userDao;
//
//        deleteChildAsyncTask(UserDao userDao) {
//            this.userDao = userDao;
//        }
//
//        @Override
//        protected String doInBackground(Users... users) {
//            userDao.deleteAllChild();
//            return "success";
//        }
//
//        @Override
//        protected void onPostExecute(String aVoid) {
//            Log.i(TAG,"删除child："+aVoid);
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//        }
//    }
//
//
//    private static class loadChildAsyncTask extends AsyncTask<Users, String, String> {
//
//        private UserDao userDao;
//
//        loadChildAsyncTask(UserDao userDao) {
//            this.userDao = userDao;
//        }
//
//        @Override
//        protected String doInBackground(Users... users) {
//            List<UsersChild> users1 = userDao.findAllChild();
//            for (int i=0;i<users1.size();i++){
//                return users1.toString();
//            }
//            return "";
//        }
//
//        @Override
//        protected void onPostExecute(String aVoid) {
//            Log.i(TAG,"查询child："+aVoid);
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//        }
//    }
//
//




    private static class getPersonInfoTask extends AsyncTask<Person, String, PersonInfo> {


        int fatherId;
        getPersonInfoTask(EditText mEtFatherId) {
            fatherId = Integer.parseInt(mEtFatherId.getText().toString());
        }

        @Override
        protected PersonInfo doInBackground(Person... person) {
            PersonInfo personInfo = DBInstance.getInstance().clothesDao().getPersonInfo(fatherId);

            return personInfo;
        }

        @Override
        protected void onPostExecute(PersonInfo personInfo) {
            Log.i(TAG,"查询："+personInfo.toString());
            super.onPostExecute(personInfo);
        }

    }


private static class insertPersonAsyncTask extends AsyncTask<Person, Void, String> {



    insertPersonAsyncTask (){
    }

    @Override
    protected String doInBackground(Person... person) {
        Log.i(TAG,person[0].toString());
        person[0].address = new MyAddress("厦门","海沧");
        person[0].money = 100;
        DBInstance.getInstance().personDao().insertPerson(person[0]);
        return "insert success";
    }

    //doInBackground 执行完成后
    @Override
    protected void onPostExecute(String aVoid) {
        Log.i(TAG,"插入："+aVoid);
        super.onPostExecute(aVoid);
    }

    //在publishProgress方法被调用insert success后
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

    private static class insertClothesAsyncTask extends AsyncTask<Person, Void, String> {

        private PersonDao personDao;
        private EditText mEtClothesColor,mEtFatherId;
        private String clothesColor;
        private int fatherId;

        insertClothesAsyncTask(PersonDao personDao, EditText mEtClothesColor, EditText mEtFatherId) {
            this.personDao = personDao;
            this.mEtClothesColor = mEtClothesColor;
            this.mEtFatherId = mEtFatherId;

            clothesColor = mEtClothesColor.getText().toString();
            fatherId = Integer.parseInt(mEtFatherId.getText().toString());


        }

        @Override
        protected String doInBackground(Person... person) {
            DBInstance.getInstance().clothesDao().insertClothes(new Clothes(clothesColor,fatherId));
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
