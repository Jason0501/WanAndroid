package com.jason.www.utils;

/**
 * @author：Jason
 * @date：2020/8/13 14:24
 * @email：1129847330@qq.com
 * @description:
 */
public class TextUtils {
    public static boolean isEmpty(String s) {
        return android.text.TextUtils.isEmpty(s);
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String textLimited(String s, int maxLength) {
        if (isEmpty(s) || s.length() <= maxLength) {
            return s;
        }
        return s.substring(0, maxLength) + "...";
    }

    public static String showFirstAndLastChar(String s) {
        if (isEmpty(s) || s.length() <= 2) {
            return s;
        }
        return s.charAt(0) + "..." + s.charAt(s.length() - 1);
    }
}