package com.wdz.module_communication.main.iot.gatt.bean;

/**
 * APP通过GATT连接灯分发vendor消息，让选中的灯闪烁10秒
 *blink闪烁开关，0：关，1：开；
 * created by huangzhangzhe on 2020/10/7 15:03
 * @author huangzhangzhe
 */

public class RemoteBlinkSingleRequest {


    /**
     * msg : blink start
     * mac : xxxxxxxxxxxx
     */

    /**
     * }让此mac的灯开始闪烁
     */
    public final static String BLINK_START = "blink start";
    /**
     * 让此mac的灯停止闪烁
     */
    public final static String BLINK_STOP = "blink stop";

    private String msg;
    private String mac;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
