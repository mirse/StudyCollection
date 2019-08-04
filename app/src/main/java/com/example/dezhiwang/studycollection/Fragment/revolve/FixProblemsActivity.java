package com.example.dezhiwang.studycollection.Fragment.revolve;

import java.util.List;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.dezhiwang.studycollection.R;

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
        setContentView(R.layout.activity_fix_problems);
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

