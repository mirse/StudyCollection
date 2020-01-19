package com.wdz.studycollection.datasave.room;

import androidx.lifecycle.Observer;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.wdz.studycollection.R;
import com.wdz.studycollection.base.PermissionActivity;
import com.wdz.studycollection.datasave.room.entity.Clothes;
import com.wdz.studycollection.datasave.room.entity.Market;
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

    @BindView(R.id.tv_status)
    EditText mTvStatus;

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

        DBInstance.getInstance().marketDao().findAllMarket().observe(this, new Observer<List<Market>>() {
            @Override
            public void onChanged(List<Market> markets) {
                mTvMarket.setText(markets.toString());
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

        DBInstance.getInstance().marketDao().findAllMarket().observe(this, new Observer<List<Market>>() {
            @Override
            public void onChanged(List<Market> markets) {
                mTvMarket.setText(markets.toString());
            }
        });
    }

    @Override
    protected void onDenyPermission() {
        finish();
    }


    @OnClick({R.id.bt_add_person,R.id.bt_add_market,R.id.bt_add_vendor,R.id.bt_add_person})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_add_person:
                new insertPersonAsyncTask().execute(new Person(mEtPersonName.getText().toString(),Integer.parseInt(mEtPersonAge.getText().toString()),Integer.parseInt(mEtPersonid.getText().toString())));
                break;

            case R.id.bt_add_market:
                new insertClothesAsyncTask(new Clothes(mEtClothesColor.getText().toString(),Integer.parseInt(mEtPersonid.getText().toString()),Integer.parseInt(mEtFatherId.getText().toString()))).execute();
                break;
            case R.id.bt_add_vendor:
                new insertMarketAsyncTask(new Market(Integer.parseInt(mEtFatherId.getText().toString()),"街道market")).execute();
                break;
            case R.id.bt_add_person:
                new getPersonInfoTask(mEtFatherId).execute();
                break;


            default:
                break;
        }
    }







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

    private static class deletePersonAsyncTask extends AsyncTask<Person, Void, String> {


        private final int modifyId;

        deletePersonAsyncTask (EditText mEtModifyId){
            modifyId = Integer.parseInt(mEtModifyId.getText().toString());
        }

        @Override
        protected String doInBackground(Person... person) {
            DBInstance.getInstance().personDao().deletePersonById(modifyId);
            return "delete success";
        }

        //doInBackground 执行完成后
        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"删除："+aVoid);
            super.onPostExecute(aVoid);
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


        private final Clothes clothes1;

        insertClothesAsyncTask(Clothes clothes) {
            clothes1 = clothes;
        }

        @Override
        protected String doInBackground(Person... person) {
            DBInstance.getInstance().clothesDao().insertClothes(clothes1);
            return "insert success";
        }

        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"插入："+aVoid);
            super.onPostExecute(aVoid);
        }


    }

    private static class insertMarketAsyncTask extends AsyncTask<Person, Void, String> {


        private final Market market1;

        insertMarketAsyncTask(Market market) {
            market1 = market;
        }

        @Override
        protected String doInBackground(Person... person) {
            DBInstance.getInstance().marketDao().insertMarket(market1);
            return "insert success";
        }

        //doInBackground 执行完成后
        @Override
        protected void onPostExecute(String aVoid) {
            Log.i(TAG,"插入："+aVoid);
            super.onPostExecute(aVoid);
        }

    }




}
