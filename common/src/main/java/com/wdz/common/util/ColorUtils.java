package com.wdz.common.util;

import android.graphics.Color;

/**
 * 颜色转换,及颜色比对工具类
 * @author dezhi.wang
 */
public class ColorUtils {
    /**
     * 灯默认颜色，由于五路灯，初始时或调节色温会导致rgb三路关闭，所以为黑色
     */
    public static String defaultColor = "000000";

    /**
     * rgb音频渐变颜色区间
     */
    public static final String[] COLOR_LIST = {
//            "1d1f8c","02459c","0267b9",
//            "0286d1","00a1ea", "019fc2",
//            "029d97","009d69","029a45",
//            "23ac3a","90c221","d1db01",
//            "fff002","f9c802","f29700",
//            "ec6000","e50112","e70036",
//            "e60251","e50069","e5007f",
//            "bf017f","930784","611786",


//            "CC3333","003366","993333",
//            "CCCC00","663366", "CCCC99",
//            "CCCC99","FF6666","FFFF00",
//            "0066CC","CC0033","CCCC00",
//            "FF0033","FF6600","009966",
//            "CCFF66","9933CC","009966",
//            "009999","CC3333","FF0033",
//            "CC3333","FF0033","CC3333"

            "ff0000","ff5a00","ffdc00", "00ff00", "ff00ff",
            "ff0000","ff5a00","ffdc00", "00ff00", "ff00ff",
            "ff0000","ff5a00","ffdc00", "00ff00", "ff00ff",

//            "FFFF00","ADFF2F","7FFF00",
//            "00FF00","008000","008080",
//            "00FFFF","00BFFF","1E90FF","4169E1",
//            "00008B","0000FF","EE82EE","FF00FF",
//            "C71585","FF69B4","FF1493","FF0000"

    };

    public static boolean checkIfRgbColor(String color){
        if (null == color || "" .equals(color)  || ColorUtils.defaultColor.equals(color)){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * hex值转int[r,g,b]
     * @param hex
     * @return
     */
    public static int[] hex2rgb (String hex) {
        int[] ints = new int[3];
        StringBuffer sb = new StringBuffer();
        // 用Integer转为十六进制的rgb值
        hex = "0x"+hex;
//        hex = hex.replace("#","0x");
        int rgb = Integer.parseInt(hex.substring(2),16);
        // 实例化java.awt.Color,获取对应的r、g、b值
        int red = (rgb & 0xff0000) >> 16;
        ints[0] = red;
        int green = (rgb & 0x00ff00) >> 8;
        ints[1] = green;
        int blue = (rgb & 0x0000ff);
        ints[2] = blue;
        return ints;
    }



    /**
     * rgb数组转Color的16进制颜色值
     * rgb - rgb数组——[63,226,197]
     * return Color的16进制颜色值——#3FE2C5
     * */
    public static String rgb2Hex(int[] rgb){
        String hexCode="";
        for(int i=0;i<rgb.length;i++){
            int rgbItem = rgb[i];
            if(rgbItem < 0){
                rgbItem = 0;
            }else if(rgbItem > 255){
                rgbItem = 255;
            }
            String[] code = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
            int lCode = rgbItem / 16;//先获取商，例如，255 / 16 == 15
            int rCode = rgbItem % 16;//再获取余数，例如，255 % 16 == 15
            hexCode += code[lCode] + code[rCode];//FF
        }
        return hexCode;
    }

    /**
     * rgb数组转Color的16进制颜色值
     * rgb - rgb数组——[63,226,197]
     * return Color的16进制颜色值——3FE2C5,不带#
     * */
    public static String rgb2HexValue(int[] rgb){
        String hexCode="";
        for(int i=0;i<rgb.length;i++){
            int rgbItem = rgb[i];
            if(rgbItem < 0){
                rgbItem = 0;
            }else if(rgbItem > 255){
                rgbItem = 255;
            }
            String[] code = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
            int lCode = rgbItem / 16;//先获取商，例如，255 / 16 == 15
            int rCode = rgbItem % 16;//再获取余数，例如，255 % 16 == 15
            hexCode += code[lCode] + code[rCode];//FF
        }
        return hexCode;
    }

    /**Color的16进制颜色值 转 Color的Int整型
     * colorHex - Color的16进制颜色值——#3FE2C5
     * return colorInt - -12590395
     * */
    public static int hex2Int(String colorHex){
        int colorInt = 0;
        if (colorHex != null){
            if (colorHex.startsWith("#")){
                colorInt = Color.parseColor(colorHex);
                return colorInt;
            }
            else{
                if (!"".equals(colorHex)){
                    colorInt = Color.parseColor("#"+colorHex);
                }
                return colorInt;
            }
        }
        return colorInt;


    }

    /**
     * 判断颜色是否显示
     * @param color
     * @return
     */
    public static boolean checkIsColorVisible(String color){
        if ("ffffff".equals(color) || "FFFFFF".equals(color)||"000000".equals(color)) {
            return false;
        }
        return true;

    }
}
