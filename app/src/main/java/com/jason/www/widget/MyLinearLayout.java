package com.jason.www.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @author：Jason
 * @date：2020/12/14 15:58
 * @email：1129847330@qq.com
 * @description:
 */
public class MyLinearLayout extends LinearLayout {

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("toucheventlog", "MyLinearLayout-->dispatchTouchEvent()：down");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("toucheventlog", "MyLinearLayout-->dispatchTouchEvent()：up");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("toucheventlog", "MyLinearLayout-->dispatchTouchEvent()：cancel");
                break;
            default:
                break;
        }
        boolean b = super.dispatchTouchEvent(event);
        Log.d("toucheventlog", "MyLinearLayout-->dispatchTouchEvent()返回值：" + b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("toucheventlog", "MyLinearLayout-->onInterceptTouchEvent()：down");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("toucheventlog", "MyLinearLayout-->onInterceptTouchEvent()：up");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("toucheventlog", "MyLinearLayout-->onInterceptTouchEvent()：cancel");
                break;
            default:
                break;
        }
        boolean b = super.onInterceptTouchEvent(event);
        Log.d("toucheventlog", "MyLinearLayout-->onInterceptTouchEvent()返回值：" + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("toucheventlog", "MyLinearLayout-->onTouchEvent()：down");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("toucheventlog", "MyLinearLayout-->onTouchEvent()：up");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("toucheventlog", "MyLinearLayout-->onTouchEvent()：cancel");
                break;
            default:
                break;
        }
        boolean b = super.onTouchEvent(event);
        Log.d("toucheventlog", "MyLinearLayout-->onTouchEvent()返回值：" + b);
        return b;
    }
}