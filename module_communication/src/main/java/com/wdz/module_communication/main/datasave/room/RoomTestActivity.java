package com.wdz.module_communication.main.datasave.room;

import android.Manifest;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.base.PermissionActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;
import com.wdz.module_communication.main.datasave.room.entity.Clothes;
import com.wdz.module_communication.main.datasave.room.entity.Market;
import com.wdz.module_communication.main.datasave.room.entity.MyAddress;
import com.wdz.module_communication.main.datasave.room.entity.Person;
import com.wdz.module_communication.main.datasave.room.entity.Vendor;
import com.wdz.module_communication.main.datasave.room.entity.embedded.PersonInfo;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = ARouterConstant.ACTIVITY_ROOM)
public class RoomTestActivity extends PermissionActivity {

    private static final String TAG = "RoomTestActivity";
    private PersonDao personDao;

    @BindView(R2.id.et_person_id)
    EditText mEtPersonid;
    @BindView(R2.id.et_person_name)
    EditText mEtPersonName;
    @BindView(R2.id.et_person_age)
    EditText mEtPersonAge;

    @BindView(R2.id.et_market_id)
    EditText mEtMarketId;
    @BindView(R2.id.et_market_add)
    EditText mEtMarketAdd;

    @BindView(R2.id.et_vendor_id)
    EditText mEtVendorId;
    @BindView(R2.id.et_vendor_name)
    EditText mEtVendorName;

    @BindView(R2.id.et_clo_per_id)
    EditText mEtCloPerId;
    @BindView(R2.id.et_clo_mar_id)
    EditText mEtCloMarId;
    @BindView(R2.id.et_clo_color)
    EditText mEtCloColor;

    @BindView(R2.id.tv_person_status)
    TextView mTvPersonStatus;
    @BindView(R2.id.tv_market_status)
    TextView mTvMarketStatus;
    @BindView(R2.id.tv_vendor_status)
    TextView mTvVendorStatus;
    @BindView(R2.id.tv_clothes_status)
    TextView mTvClothesStatus;
    private Person person;
    private Market market;
    public Context context = RoomTestActivity.this;


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
        DBInstance.getInstance(this).personDao().findAllPerson().observe(this, new Observer<List<?>>() {
            @Override
            public void onChanged(List<?> people) {
                if (people instanceof Person){
                    Log.i(TAG, "onChanged: "+person.toString());
                }
                else if (people instanceof Clothes){
                    Log.i(TAG, "onChanged: "+person.toString());
                }

            }
        });


        DBInstance.getInstance(this).marketDao().findAllMarket().observe(this, new Observer<List<Market>>() {
            @Override
            public void onChanged(List<Market> markets) {
                mTvMarketStatus.setText(markets.toString());
            }
        });

//        DBInstance.getInstance(this).vendorDao().findAllVendor().observe(this, new Observer<List<Vendor>>() {
//            @Override
//            public void onChanged(List<Vendor> people) {
//                mTvVendorStatus.setText(people.toString());
//            }
//        });

        DBInstance.getInstance(this).clothesDao().findAllClothes().observe(this, new Observer<List<Clothes>>() {
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


    @OnClick({R2.id.bt_add_person,R2.id.bt_add_market,R2.id.bt_add_vendor,R2.id.bt_add_clo,R2.id.btn_delete})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_add_person) {
            person = new Person(mEtPersonName.getText().toString(), Integer.parseInt(mEtPersonAge.getText().toString()), Integer.parseInt(mEtPersonid.getText().toString()));
            new insertPersonAsyncTask().execute(person);
        } else if (id == R.id.bt_add_market) {
            market = new Market(Integer.parseInt(mEtMarketId.getText().toString()), mEtMarketAdd.getText().toString());
            new insertMarketAsyncTask().execute(market);
        } else if (id == R.id.bt_add_vendor) {
            new insertVendorAsyncTask().execute(new Vendor(Integer.parseInt(mEtVendorId.getText().toString()), mEtVendorName.getText().toString()));
        } else if (id == R.id.bt_add_clo) {
            new insertClothesAsyncTask().execute(new Clothes(mEtCloColor.getText().toString(), Integer.parseInt(mEtCloPerId.getText().toString()), Integer.parseInt(mEtCloMarId.getText().toString())));
        } else if (id == R.id.btn_delete) {
            new deletePersonAsyncTask().execute();
        }
    }







    private class getPersonInfoTask extends AsyncTask<Person, String, List<PersonInfo>> {


        getPersonInfoTask() {
        }

        @Override
        protected List<PersonInfo> doInBackground(Person... person) {
            List<PersonInfo> personInfos = DBInstance.getInstance(context).personDao().loadAllInfoById(1);

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



    private class insertPersonAsyncTask extends AsyncTask<Person, Void, String> {
        insertPersonAsyncTask (){

        }

    @Override
    protected String doInBackground(Person... person) {
        Log.i(TAG,person[0].toString());
        person[0].address = new MyAddress("厦门","海沧");
        person[0].money = 100;
        DBInstance.getInstance(context).personDao().insertPerson(person[0],new Clothes("红衣服",1,1));
        return "insert success";
    }

    //doInBackground 执行完成后
    @Override
    protected void onPostExecute(String aVoid) {
        Log.i(TAG,"插入："+aVoid);
        super.onPostExecute(aVoid);
    }
    }

    private class deletePersonAsyncTask extends AsyncTask<Person, Void, String>{

        @Override
        protected String doInBackground(Person... people) {
            DBInstance.getInstance(context).personDao().deletePerson(person,market);
            return "success";
        }
        //doInBackground 执行完成后
        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"删除："+aVoid);
            super.onPostExecute(aVoid);
        }
    }

    private class insertMarketAsyncTask extends AsyncTask<Market, Void, String> {

        public insertMarketAsyncTask() {

        }

        @Override
        protected String doInBackground(Market... markets) {
            DBInstance.getInstance(context).marketDao().insertMarket(markets[0]);
            return "insert success";
        }

        //doInBackground 执行完成后
        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"插入："+aVoid);
            super.onPostExecute(aVoid);
        }
    }

    private class insertVendorAsyncTask extends AsyncTask<Vendor, Void, String> {

        public insertVendorAsyncTask() {

        }

        @Override
        protected String doInBackground(Vendor... vendors) {
            DBInstance.getInstance(context).vendorDao().insertVendor(vendors[0]);
            return "insert success";
        }

        //doInBackground 执行完成后
        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"插入："+aVoid);
            super.onPostExecute(aVoid);
        }
    }

    private class insertClothesAsyncTask extends AsyncTask<Clothes, Void, String> {



        insertClothesAsyncTask() {

        }

        @Override
        protected String doInBackground(Clothes... clothes) {
            DBInstance.getInstance(context).clothesDao().insertClothes(clothes[0]);
            return "insert success";
        }

        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"插入："+aVoid);
            super.onPostExecute(aVoid);
        }


    }






}
