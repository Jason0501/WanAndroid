package com.jason.www.utils;

/**
 * @author：Jason
 * @date：2020/8/20 18:42
 * @email：1129847330@qq.com
 * @description:
 */
public class KBUtils {
    public static final String UNIT_KB = "K";
    public static final String UNIT_MB = "M";
    public static final String UNIT_GB = "G";

    /**
     * 将MB转成GB
     *
     * @param size
     * @return
     */
    public static String convert(int size) {
        if (size < 1024) {
            return size + "M";
        } else {
            int gb = size / 1024;
            int temp = size % 1024;
            if (temp == 0) {
                return gb + "G";
            } else {
                return String.format("%.1fG", gb + temp / 1024f);
            }
        }
    }
}