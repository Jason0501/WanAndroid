package com.jason.www.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.jason.www.config.AppData;

public class DisplayUtils {

    public static DisplayMetrics display() {
        if (AppData.getContext() == null) {
            return null;
        }
        return AppData.getContext().getResources().getDisplayMetrics();
    }

    public static int width() {
        DisplayMetrics display = display();
        if (display == null) {
            return 0;
        }
        return display.widthPixels;
    }

    public static int height() {
        DisplayMetrics display = display();
        if (display == null) {
            return 0;
        }
        return display.heightPixels;
    }

    public static int realWidth() {
        Point outPoint = getDisplayPoint();
        return outPoint.x;
    }

    public static int realHeight() {
        Point outPoint = getDisplayPoint();
        return outPoint.y;
    }

    private static Point getDisplayPoint() {
        WindowManager windowManager = (WindowManager) AppData.getContext().getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint);
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint);
        }
        return outPoint;
    }

    public static int dp2px(float dp) {
        DisplayMetrics display = display();
        if (display == null) {
            return 0;
        }
        final float scale = display.density;
        return (int) (dp * scale + 0.5f);
    }

    public static float px2dp(int px) {
        DisplayMetrics display = display();
        if (display == null) {
            return 0;
        }
        return px / display.density;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = AppData.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
