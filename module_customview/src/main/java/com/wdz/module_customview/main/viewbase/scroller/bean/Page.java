package com.wdz.module_customview.main.viewbase.scroller.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dezhi.wang on 2020/5/12.
 */
public class Page {
    private List<Device> device = new ArrayList<>();

    public List<Device> getDevice() {
        return device;
    }

    public void setDevice(List<Device> mDevice) {
        this.device = mDevice;
    }

    @Override
    public String toString() {
        return "Page{" +
                "mDevice=" + device +
                '}';
    }
}
