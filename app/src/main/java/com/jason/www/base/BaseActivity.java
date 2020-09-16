package com.jason.www.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author：Jason
 * @date：2020/8/4 10:36
 * @email：1129847330@qq.com
 * @description:
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;
    protected Context mContext;
    protected AppCompatActivity mActivity;
    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        mActivity = this;
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(getLayoutResId());
        ActivityStackManager.getInstance().addActivity(this);
        unbinder = ButterKnife.bind(this);
        initMvp();
        initView(getWindow().getDecorView());
        initData();
        initEvent();
    }

    protected void initMvp() {
    }

    protected void initView(View decorView) {
    }

    protected void initData() {
    }

    protected void initEvent() {
    }

    protected void beforeSetContentView() {
    }

    protected void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    protected abstract int getLayoutResId();

    protected void startActivity(Class targetActivity) {
        startActivity(new Intent(mContext, targetActivity));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getInstance().finishActivity(this);
        unbinder.unbind();
        unbinder = null;
    }
}