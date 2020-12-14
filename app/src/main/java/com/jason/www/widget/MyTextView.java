package com.jason.www.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author：Jason
 * @date：2020/12/14 16:01
 * @email：1129847330@qq.com
 * @description:
 */
public class MyTextView extends AppCompatTextView {

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("toucheventlog", "MyTextView-->dispatchTouchEvent()：down");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("toucheventlog", "MyTextView-->dispatchTouchEvent()：up");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("toucheventlog", "MyTextView-->dispatchTouchEvent()：cancel");
                break;
            default:
                break;
        }
        boolean b = super.dispatchTouchEvent(event);
        Log.d("toucheventlog", "MyTextView-->dispatchTouchEvent()返回值：" + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("toucheventlog", "MyTextView-->onTouchEvent()：down");
                return true;
            case MotionEvent.ACTION_UP:
                Log.d("toucheventlog", "MyTextView-->onTouchEvent()：up");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("toucheventlog", "MyTextView-->onTouchEvent()：cancel");
                break;
            default:
                break;
        }
        boolean b = super.onTouchEvent(event);
        Log.d("toucheventlog", "MyTextView-->onTouchEvent()返回值：" + b);
        return b;
    }
}