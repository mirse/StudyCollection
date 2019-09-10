package com.wdz.studycollection.handler.model;

import androidx.databinding.ObservableField;

public class ObservableLogin {
    private ObservableField<String> name = new ObservableField<>();
    private ObservableField<String> pwd = new ObservableField<>();
    private ObservableField<String> check = new ObservableField<>();

    public ObservableLogin(ObservableField<String> name, ObservableField<String> pwd, ObservableField<String> check) {
        this.name = name;
        this.pwd = pwd;
        this.check = check;
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    public ObservableField<String> getPwd() {
        return pwd;
    }

    public void setPwd(ObservableField<String> pwd) {
        this.pwd = pwd;
    }

    public ObservableField<String> getCheck() {
        return check;
    }

    public void setCheck(ObservableField<String> check) {
        this.check = check;
    }
}
