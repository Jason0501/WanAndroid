package com.jason.www.activity;

import android.view.View;
import android.widget.ImageView;

import com.jason.www.R;
import com.jason.www.base.BaseActivity;
import com.jason.www.config.Accounts;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.imageview_splash)
    ImageView imageView;

    @Override
    protected void initView(View decorView) {
        super.initView(decorView);
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Accounts.getIsLogin()) {
                    startActivity(HomeTabActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                finish();
            }
        }, 2000);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }
}