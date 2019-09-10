package com.wdz.studycollection.handler.model;

import androidx.databinding.ObservableField;

public class ObservableTimer {
    private ObservableField<String> time = new ObservableField<>();

    public ObservableTimer(ObservableField<String> time) {
        this.time = time;
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public void setTime(ObservableField<String> time) {
        this.time = time;
    }
}
