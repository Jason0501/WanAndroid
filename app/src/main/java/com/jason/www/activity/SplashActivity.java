package com.jason.www.activity;

import android.widget.ImageView;

import com.jason.www.R;
import com.jason.www.base.BaseActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.imageview_splash)
    ImageView imageView;

    @Override
    protected void initView() {
        super.initView();
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
                finish();
            }
        }, 1000);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }
}