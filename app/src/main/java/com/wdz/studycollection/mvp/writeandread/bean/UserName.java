package com.wdz.studycollection.mvp.writeandread.bean;

/**
 * Created by dezhi.wang on 2018/10/31.
 */

public class UserName {
    private int id;
    private String firstName;
    private String lastName;

    public UserName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
