package com.wdz.common.util;

import java.util.regex.Pattern;

/**
 * @Author dezhi.wang
 * @Date 2021/2/26 9:23
 */
public class StringUtils {
    /**
     * 检查是否时MAC地址格式（E0:AA:22:11:EE:33）
     * @param mac
     * @return
     */
    public static boolean isMacPattern(String mac){
        String pattern = "^([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}$";
        return Pattern.matches(pattern,mac);
    }
}
