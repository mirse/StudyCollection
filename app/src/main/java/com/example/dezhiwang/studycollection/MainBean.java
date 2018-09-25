package com.example.dezhiwang.studycollection;

/**
 * Created by dezhi.wang on 2018/9/21.
 */

public class MainBean {
    private String title;
    private String btn;
    private String btn2;
    private String btn3;
    private String btn4;
    private String btn5;
    private String btn6;

    public MainBean(String title, String btn, String btn2, String btn3, String btn4, String btn5, String btn6) {
        this.title = title;
        this.btn = btn;
        this.btn2 = btn2;
        this.btn3 = btn3;
        this.btn4 = btn4;
        this.btn5 = btn5;
        this.btn6 = btn6;
    }

    public String getTitle() {
        return title;
    }

    public String getBtn() {
        return btn;
    }

    public String getBtn2() {
        return btn2;
    }

    public String getBtn3() {
        return btn3;
    }

    public String getBtn4() {
        return btn4;
    }

    public String getBtn5() {
        return btn5;
    }

    public String getBtn6() {
        return btn6;
    }
}
