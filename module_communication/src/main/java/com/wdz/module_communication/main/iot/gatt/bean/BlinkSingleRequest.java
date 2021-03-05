package com.wdz.module_communication.main.iot.gatt.bean;

/**
 * APP与灯创建GATT连接，开启和关闭GATT连接的灯闪烁命令
 *blink闪烁开关，0：关，1：开；
 * created by huangzhangzhe on 2020/10/7 15:03
 * @author huangzhangzhe
 */

public class BlinkSingleRequest {

    /**
     * blink : 1
     */

    private int blink;

    public int getBlink() {
        return blink;
    }

    public void setBlink(int blink) {
        this.blink = blink;
    }
}
