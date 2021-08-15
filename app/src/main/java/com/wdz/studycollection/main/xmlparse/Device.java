package com.wdz.studycollection.main.xmlparse;

public class Device {
    public String type;
    public String len;
    public String express;
    public String data;
    public String unit;
    public String addr;

    @Override
    public String toString() {
        return "Device{" +
                "type='" + type + '\'' +
                ", len='" + len + '\'' +
                ", express='" + express + '\'' +
                ", data='" + data + '\'' +
                ", unit='" + unit + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
