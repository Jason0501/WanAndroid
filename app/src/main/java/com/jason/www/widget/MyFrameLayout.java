package com.jason.www.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author：Jason
 * @date：2020/12/14 15：58
 * @email：1129847330@qq.com
 * @description：
 */
public class MyFrameLayout extends FrameLayout {

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("toucheventlog", "MyFrameLayout-->dispatchTouchEvent()：down");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("toucheventlog", "MyFrameLayout-->dispatchTouchEvent()：up");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("toucheventlog", "MyFrameLayout-->dispatchTouchEvent()：cancel");
                break;
            default:
                break;
        }
        boolean b = super.dispatchTouchEvent(event);
        Log.d("toucheventlog", "MyFrameLayout-->dispatchTouchEvent()返回值：" + b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("toucheventlog", "MyFrameLayout-->onInterceptTouchEvent()：down");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("toucheventlog", "MyFrameLayout-->onInterceptTouchEvent()：up");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("toucheventlog", "MyFrameLayout-->onInterceptTouchEvent()：cancel");
                break;
            default:
                break;
        }
        boolean b = super.onInterceptTouchEvent(event);
        Log.d("toucheventlog", "MyFrameLayout-->onInterceptTouchEvent()返回值：" + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("toucheventlog", "MyFrameLayout-->onTouchEvent()：down");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("toucheventlog", "MyFrameLayout-->onTouchEvent()：up");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("toucheventlog", "MyFrameLayout-->onTouchEvent()：cancel");
                break;
            default:
                break;
        }
        boolean b = super.onTouchEvent(event);
        Log.d("toucheventlog", "MyFrameLayout-->onTouchEvent()返回值：" + b);
        return b;
    }
}