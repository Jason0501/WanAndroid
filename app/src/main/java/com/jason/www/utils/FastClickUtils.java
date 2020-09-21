package com.jason.www.utils;

/**
 * @author：Jason
 * @date：2020/7/24 10:42
 * @email：1129847330@qq.com
 * @description: 判断连续点击，可自定义两次点击的间隔时长
 */
public class FastClickUtils {
    private static long lastTime;
    private static final int DURATION = 1000;//两次点击的时间间距，单位：毫秒

    public static boolean isFastClicked() {
        return isFastClicked(DURATION);
    }

    /**
     * @param duration 两次点击的间隔时长，单位：毫秒
     * @return
     */
    public static boolean isFastClicked(int duration) {
        boolean flag = false;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime < duration) {
            flag = true;
        }
        lastTime = currentTime;
        return flag;
    }
}