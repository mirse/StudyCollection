package com.wdz.mychoosedialog.datepicker.bean;

import java.util.ArrayList;
import java.util.List;

public class City {
    public String cityCode;//城市编码
    public String cityName;//城市名称
    public List<District> districtList = new ArrayList<>();//区列表
}
