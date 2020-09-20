package com.wdz.module_customview.main.viewbase.scroller.bean;

/**
 * Created by dezhi.wang on 2020/5/12.
 */
public class Device {
    private String name;
    private boolean isSelected;
    private int deviceType;


    public Device() {
    }

    public Device(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public Device(String name, boolean isSelected, int deviceType) {
        this.name = name;
        this.isSelected = isSelected;
        this.deviceType = deviceType;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", isSelected=" + isSelected +
                ", deviceType=" + deviceType +
                '}';
    }
}
