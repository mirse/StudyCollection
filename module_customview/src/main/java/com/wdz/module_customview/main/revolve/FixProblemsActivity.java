package com.wdz.module_customview.main.revolve;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;

import java.util.List;

@Route(path = ARouterConstant.ACTIVITY_FIX_PROBLEM)
public class FixProblemsActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private ListAdapter mAdapter;
    private List<String> mDatas;
    private OtherRetainedFragment dataFragment;
    private MyAsyncTask mMyTask;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_customview_activity_fix_problems);
        Log.e(TAG, "onCreate");

        // find the retained fragment on activity restarts
        FragmentManager fm = getFragmentManager();
        dataFragment = (OtherRetainedFragment) fm.findFragmentByTag("data");

        // create the fragment and data the first time
        if (dataFragment == null)
        {
            // add the fragment
            dataFragment = new OtherRetainedFragment();
            fm.beginTransaction().add(dataFragment, "data").commit();
        }
        mMyTask = dataFragment.getData();
        if (mMyTask != null)
        {
            mMyTask.setActivity(this);
        } else
        {
            mMyTask = new MyAsyncTask(this);
            dataFragment.setData(mMyTask);
            mMyTask.execute();
        }
        // the data is available in dataFragment.getData()
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
        mMyTask.setActivity(null); //快销毁时
        Log.e(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();

    }
    /**
     * 回调
     */
    public void onTaskCompleted()
    {
        ListView lv = findViewById(R.id.lv);
        mDatas = mMyTask.getItems();
        mAdapter = new ArrayAdapter<String>(FixProblemsActivity.this,
                android.R.layout.simple_list_item_1, mDatas);
        lv.setAdapter(mAdapter);
    }

}

