package com.wdz.studycollection.mvp.writeandread.model;

import com.wdz.studycollection.mvp.writeandread.bean.UserName;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public interface UserModel {
    void setId(int id);
    void setFirst(String first);
    void setLast(String last);
    UserName load(int id);
}
