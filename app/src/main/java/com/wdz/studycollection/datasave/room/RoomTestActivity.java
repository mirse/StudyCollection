package com.wdz.studycollection.datasave.room;

import androidx.lifecycle.Observer;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wdz.studycollection.R;
import com.wdz.studycollection.base.PermissionActivity;
import com.wdz.studycollection.datasave.room.entity.Clothes;
import com.wdz.studycollection.datasave.room.entity.Market;
import com.wdz.studycollection.datasave.room.entity.MyAddress;
import com.wdz.studycollection.datasave.room.entity.Person;
import com.wdz.studycollection.datasave.room.entity.PersonInfo;
import com.wdz.studycollection.datasave.room.entity.Vendor;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomTestActivity extends PermissionActivity {

    private static final String TAG = "RoomTestActivity";
    private PersonDao personDao;

    @BindView(R.id.et_person_id)
    EditText mEtPersonid;
    @BindView(R.id.et_person_name)
    EditText mEtPersonName;
    @BindView(R.id.et_person_age)
    EditText mEtPersonAge;

    @BindView(R.id.et_market_id)
    EditText mEtMarketId;
    @BindView(R.id.et_market_add)
    EditText mEtMarketAdd;

    @BindView(R.id.et_vendor_id)
    EditText mEtVendorId;
    @BindView(R.id.et_vendor_name)
    EditText mEtVendorName;

    @BindView(R.id.et_clo_per_id)
    EditText mEtCloPerId;
    @BindView(R.id.et_clo_mar_id)
    EditText mEtCloMarId;
    @BindView(R.id.et_clo_color)
    EditText mEtCloColor;

    @BindView(R.id.tv_person_status)
    TextView mTvPersonStatus;
    @BindView(R.id.tv_market_status)
    TextView mTvMarketStatus;
    @BindView(R.id.tv_vendor_status)
    TextView mTvVendorStatus;
    @BindView(R.id.tv_clothes_status)
    TextView mTvClothesStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_test);
        ButterKnife.bind(RoomTestActivity.this);
        initMorePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.READ_CONTACTS});
    }


    @Override
    protected void alreadyGetPermission() {
        refreshUI();
        new getPersonInfoTask().execute();

    }

    private void refreshUI() {
        DBInstance.getInstance().personDao().findAllPerson().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                mTvPersonStatus.setText(people.toString());
            }
        });


        DBInstance.getInstance().marketDao().findAllMarket().observe(this, new Observer<List<Market>>() {
            @Override
            public void onChanged(List<Market> markets) {
                mTvMarketStatus.setText(markets.toString());
            }
        });

        DBInstance.getInstance().vendorDao().findAllVendor().observe(this, new Observer<List<Vendor>>() {
            @Override
            public void onChanged(List<Vendor> people) {
                mTvVendorStatus.setText(people.toString());
            }
        });

        DBInstance.getInstance().clothesDao().findAllClothes().observe(this, new Observer<List<Clothes>>() {
            @Override
            public void onChanged(List<Clothes> people) {
                mTvClothesStatus.setText(people.toString());
            }
        });
    }

    @Override
    protected void onGetPermission() {
        refreshUI();
    }

    @Override
    protected void onDenyPermission() {
        finish();
    }


    @OnClick({R.id.bt_add_person,R.id.bt_add_market,R.id.bt_add_vendor,R.id.bt_add_clo})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_add_person:
                new insertPersonAsyncTask().execute(new Person(mEtPersonName.getText().toString(),Integer.parseInt(mEtPersonAge.getText().toString()),Integer.parseInt(mEtPersonid.getText().toString())));
                break;

            case R.id.bt_add_market:
                new insertMarketAsyncTask().execute(new Market(Integer.parseInt(mEtMarketId.getText().toString()),mEtMarketAdd.getText().toString()));
                break;
            case R.id.bt_add_vendor:
                new insertVendorAsyncTask().execute(new Vendor(Integer.parseInt(mEtVendorId.getText().toString()),mEtVendorName.getText().toString()));
                break;
            case R.id.bt_add_clo:
                new insertClothesAsyncTask().execute(new Clothes(mEtCloColor.getText().toString(),Integer.parseInt(mEtCloPerId.getText().toString()),Integer.parseInt(mEtCloMarId.getText().toString())));
                break;


            default:
                break;
        }
    }







    private static class getPersonInfoTask extends AsyncTask<Person, String, List<PersonInfo>> {


        getPersonInfoTask() {
        }

        @Override
        protected List<PersonInfo> doInBackground(Person... person) {
            List<PersonInfo> personInfos = DBInstance.getInstance().personDao().loadAllInfoById(1);

            return personInfos;
        }

        @Override
        protected void onPostExecute(List<PersonInfo> personInfos) {
            Log.i(TAG,"查询："+personInfos.size());
            if (personInfos.size()!=0){
                for (PersonInfo personInfo:personInfos) {
                    Log.i(TAG,personInfo.toString());
                }
            }
            super.onPostExecute(personInfos);
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
    }

    private static class insertMarketAsyncTask extends AsyncTask<Market, Void, String> {

        public insertMarketAsyncTask() {

        }

        @Override
        protected String doInBackground(Market... markets) {
            DBInstance.getInstance().marketDao().insertMarket(markets[0]);
            return "insert success";
        }

        //doInBackground 执行完成后
        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"插入："+aVoid);
            super.onPostExecute(aVoid);
        }
    }

    private static class insertVendorAsyncTask extends AsyncTask<Vendor, Void, String> {

        public insertVendorAsyncTask() {

        }

        @Override
        protected String doInBackground(Vendor... vendors) {
            DBInstance.getInstance().vendorDao().insertVendor(vendors[0]);
            return "insert success";
        }

        //doInBackground 执行完成后
        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"插入："+aVoid);
            super.onPostExecute(aVoid);
        }
    }

    private static class insertClothesAsyncTask extends AsyncTask<Clothes, Void, String> {



        insertClothesAsyncTask() {

        }

        @Override
        protected String doInBackground(Clothes... clothes) {
            DBInstance.getInstance().clothesDao().insertClothes(clothes[0]);
            return "insert success";
        }

        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"插入："+aVoid);
            super.onPostExecute(aVoid);
        }


    }






}
