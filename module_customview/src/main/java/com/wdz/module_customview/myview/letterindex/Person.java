package com.wdz.module_customview.myview.letterindex;

/**
 * Created by dezhi.wang on 2018/10/26.
 */

public class Person {
    private String name;
    private String pingyin;

    public Person(String name) {
        this.name = name;
        this.pingyin = PingYinUtils.getPinYin(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPingyin() {
        return pingyin;
    }

    public void setPingyin(String pingyin) {
        this.pingyin = pingyin;
    }
}
