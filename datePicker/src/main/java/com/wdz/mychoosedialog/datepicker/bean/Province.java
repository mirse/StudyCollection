package com.wdz.mychoosedialog.datepicker.bean;

import java.util.ArrayList;
import java.util.List;

public class Province {
    public String provinceCode;//省编码
    public String provinceName;//省名称
    public List<City> cityList = new ArrayList<>();//市列表
}
