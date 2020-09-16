package com.jason.www.activity;

import android.widget.ImageView;

import com.jason.www.R;
import com.jason.www.base.BaseActivity;
import com.jason.www.utils.glide.GlideUtils;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.imageview_splash)
    ImageView imageView;

    @Override
    protected void initView() {
        GlideUtils.loadImage(R.drawable.splash, imageView);
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (Accounts.getIsLogin()) {
//                    startActivity(MainActivity.class);
//                } else {
//                    startActivity(LoginActivity.class);
//                }
                startActivity(LoginActivity.class);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }
}