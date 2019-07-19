package com.example.dezhiwang.studycollection.Mvp.WriteAndRead.Model;

import com.example.dezhiwang.studycollection.Mvp.WriteAndRead.Bean.UserName;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public interface UserModel {
    void setId(int id);
    void setFirst(String first);
    void setLast(String last);
    UserName load(int id);
}
