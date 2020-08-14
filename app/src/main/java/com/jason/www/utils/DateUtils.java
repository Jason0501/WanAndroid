package com.jason.www.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author：Jason
 * @date：2020/8/11 11:09
 * @email：1129847330@qq.com
 * @description:
 */
public class DateUtils {
    public static final String PATTERN_YMDHMS = "yyyy-MM-DD HH:mm:ss";
    private static SimpleDateFormat format_ymdhms = new SimpleDateFormat(PATTERN_YMDHMS);

    public static String formatTimeStamp2YMDHms(long timestamp) {
        return format_ymdhms.format(new Date(timestamp));
    }
}