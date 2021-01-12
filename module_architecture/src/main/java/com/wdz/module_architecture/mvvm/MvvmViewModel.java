package com.wdz.module_architecture.mvvm;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wdz.module_architecture.paging.room.DBInstance;
import com.wdz.module_architecture.paging.room.User;

import java.util.List;

/**
 * @Author dezhi.wang
 * @Date 2021/1/12 11:18
 *
 * 1、viewModel不会随屏幕旋转而销毁
 * 2、在对应的作用域内，保证只有一个唯一的实例
 *
 */
public class MvvmViewModel extends ViewModel {


    public LiveData<List<User>> persons = new MutableLiveData<>();


    public LoginInfo loginInfo = new LoginInfo("","");


    /*
     * liveData
     * 1、解决内存泄漏问题 ->liveData感知生命周期，在onDestroy时会自动解绑
     * 2、防止出现View空指针异常 ->liveData感知生命周期，在界面可见时才会响应
     * 3、避免大量繁琐的回调 ->数据与界面绑定
     */
    public MutableLiveData<Boolean> loginStatus = new MutableLiveData<>();


    public void login(View view) {
        if (loginInfo.getUser().equals("wdz") && loginInfo.getPwd().equals("111")) {
            loginStatus.postValue(true);
        } else {
            loginStatus.postValue(false);
        }
    }

    public TextWatcher userTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            loginInfo.setUser(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public TextWatcher pwdTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            loginInfo.setPwd(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void getUsers(Context context){
        persons = DBInstance.getInstance(context).personDao().queryUsersByLiveData();
        persons = Transformations.map(persons, new Function<List<User>, List<User>>() {
            @Override
            public List<User> apply(List<User> input) {
                for (int i = 0; i < input.size(); i++) {
                    input.get(i).userName = "张" + i;
                }
                return input;
            }
        });
    }
}
