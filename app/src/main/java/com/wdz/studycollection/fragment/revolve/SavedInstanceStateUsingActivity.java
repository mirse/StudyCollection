package com.wdz.studycollection.fragment.revolve;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.wdz.studycollection.R;
import com.wdz.studycollection.fragment.ProgressDialogFragment;
/**
 * 不考虑加载时，进行旋转的情况，有意的避开这种情况，后面例子会介绍解决方案
 * @author zhy
 *
 */
public class SavedInstanceStateUsingActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private ListAdapter mAdapter;
    private ArrayList<String> mDatas;
    private ProgressDialogFragment mLoadingDialog;
    private LoadDataAsyncTask mLoadDataAsyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_instance_state);
        Log.e(TAG, "onCreate");
        initData(savedInstanceState);
    }

    /**
     * 初始化数据
     */
    private void initData(Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
            mDatas = savedInstanceState.getStringArrayList("mDatas");

        if (mDatas == null)
        {
            mLoadingDialog = new ProgressDialogFragment();
            mLoadingDialog.show(getSupportFragmentManager(),"1");
            mLoadDataAsyncTask = new LoadDataAsyncTask();
            mLoadDataAsyncTask.execute();

        } else
        {
            initAdapter();
        }

    }



    /**
     * 初始化适配器
     */
    private void initAdapter()
    {
        ListView lv = findViewById(R.id.lv);
        mAdapter = new ArrayAdapter<String>(
                SavedInstanceStateUsingActivity.this,
                android.R.layout.simple_list_item_1, mDatas);
        lv.setAdapter(mAdapter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state)
    {
        super.onRestoreInstanceState(state);
        Log.e(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState");
        outState.putSerializable("mDatas", mDatas);

    }

    /**
     * 模拟耗时操作
     *
     * @return
     */
    private ArrayList<String> generateTimeConsumingDatas()
    {
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e)
        {
        }
        return new ArrayList<String>(Arrays.asList("通过Fragment保存大量数据",
                "onSaveInstanceState保存数据",
                "getLastNonConfigurationInstance已经被弃用", "RabbitMQ", "Hadoop",
                "Spark"));
    }

    private class LoadDataAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {

            mDatas = generateTimeConsumingDatas();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            mLoadingDialog.dismiss();
            initAdapter();
        }
    }

    @Override
    protected void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

}
