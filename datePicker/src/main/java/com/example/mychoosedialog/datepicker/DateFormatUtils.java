package com.example.mychoosedialog.datepicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtils {
    private static final String DATE_FORMAT_PATTERN_YMD = "yyyy-MM-dd";
    /**
     * 时间戳转字符串
     *
     * @param timestamp     时间戳
     * @return 格式化的日期字符串
     */
    public static String long2Str(long timestamp) {
        return new SimpleDateFormat(DATE_FORMAT_PATTERN_YMD, Locale.CHINA).format(new Date(timestamp));
    }

    /**
     * 字符串转时间戳
     *
     * @param dateStr       日期字符串
     * @return 时间戳
     */
    public static long str2Long(String dateStr) {
        try {
            return new SimpleDateFormat(DATE_FORMAT_PATTERN_YMD, Locale.CHINA).parse(dateStr).getTime();
        }
        catch (Throwable ignored) {
        }
        return 0;
    }

}
