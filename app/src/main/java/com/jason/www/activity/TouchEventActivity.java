package com.jason.www.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jason.www.R;

import androidx.appcompat.app.AppCompatActivity;

public class TouchEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);

        getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("touchevent", "DecorView-->onTouchEvent()-->down");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("touchevent", "DecorView-->onTouchEvent()-->up");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.myfl:
//                break;
//            case R.id.myll:
//                break;
//            case R.id.mytv:
//                break;
//        }
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean b = super.dispatchTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("touchevent", "TouchEventActivity-->dispatchTouchEvent()：down, 返回值：" + b);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("touchevent", "TouchEventActivity-->dispatchTouchEvent()：up, 返回值：" + b);
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("touchevent", "TouchEventActivity-->dispatchTouchEvent()：cancel, 返回值：" + b);
                break;
            default:
                break;
        }
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("touchevent", "TouchEventActivity-->onTouchEvent()：down, 返回值：" + b);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("touchevent", "TouchEventActivity-->onTouchEvent()：up, 返回值：" + b);
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("touchevent", "TouchEventActivity-->onTouchEvent()：cancel, 返回值：" + b);
                break;
            default:
                break;
        }
        return b;
    }

}