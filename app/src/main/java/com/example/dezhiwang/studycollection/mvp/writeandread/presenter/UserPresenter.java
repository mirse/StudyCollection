package com.example.dezhiwang.studycollection.mvp.writeandread.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.dezhiwang.studycollection.mvp.writeandread.bean.UserName;
import com.example.dezhiwang.studycollection.mvp.writeandread.model.IUserModel;
import com.example.dezhiwang.studycollection.mvp.writeandread.view.BaseView;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public class UserPresenter implements BasePresenter {
    private BaseView baseView;
    private final IUserModel userModel;
    private Context mcontext;

    public UserPresenter(BaseView baseView, Context context) {
        attachView(baseView);
        userModel = new IUserModel();
        mcontext=context;
    }


    @Override
    public void attachView(BaseView baseView) {
        this.baseView=baseView;
    }

    @Override
    public void detachView() {
        if (this.baseView!=null){
            this.baseView=null;
        }
    }

    @Override
    public void savaData(int id, String firstName, String lastName) {
        userModel.setId(id);
        userModel.setFirst(firstName);
        userModel.setLast(lastName);
        Toast.makeText(mcontext,"success"+firstName+","+lastName,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadData(int id) {
        UserName load = userModel.load(id);
            baseView.setUser("id:"+id+" first:"+load.getFirstName()+" last:"+load.getLastName());
    }

    @Override
    public void setSelected(boolean is) {
        baseView.setSelect(is);
    }
}
