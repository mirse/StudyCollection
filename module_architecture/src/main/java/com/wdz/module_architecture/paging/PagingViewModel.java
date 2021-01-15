package com.wdz.module_architecture.paging;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.wdz.module_architecture.paging.room.DBInstance;
import com.wdz.module_architecture.paging.room.User;

/**
 * @Author dezhi.wang
 * @Date 2021/1/9 9:00
 */
public class PagingViewModel extends ViewModel {

    public LiveData<PagedList<User>> listLiveData = new MutableLiveData<>();
    //是否有数据
    private MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();
    private Context context;

    public PagedList.Config getConfig(){

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(15)              // 每页加载的数据量
                .setInitialLoadSizeHint(30)   // 定义首次加载时需要加载的Item数目通常>pageSize
                .setPrefetchDistance(10)      // 预取数据的距离
                .setEnablePlaceholders(false) // 是否启用占位符
                .build();
        return config;

    }
    public LiveData<PagedList<User>> getUser(Context context){
        //为pagelist提供数据
        DataSource.Factory<Integer, User> integerUserFactory =
                DBInstance.getInstance(context).personDao().queryUsers();
        //PagedListBuilder将数据源工厂和相关配置统一管理
        listLiveData = new LivePagedListBuilder(integerUserFactory, getConfig())
                //监听数据边界
                .setBoundaryCallback(new PagedList.BoundaryCallback() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                        booleanMutableLiveData.postValue(false);
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);
                        booleanMutableLiveData.postValue(true);
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull Object itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                        booleanMutableLiveData.postValue(false);
                    }
                }).build();

        return listLiveData;
    }

    public void insertUsers(int count,Context context){
        this.context = context;
        User user = new User();
        user.userId = count;
        user.userName = "王"+count;
        new InsertPersonAsyncTask().execute(user);
    }


    private class InsertPersonAsyncTask extends AsyncTask<User, Void, String> {
        InsertPersonAsyncTask (){

        }

        @Override
        protected String doInBackground(User... users) {

            DBInstance.getInstance(context).personDao().insertPerson(users[0]);
            return "insert success";
        }

        //doInBackground 执行完成后
        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
