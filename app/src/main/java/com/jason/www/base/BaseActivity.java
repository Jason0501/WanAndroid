package com.jason.www.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

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
        ActivityManagerDelegate.getInstance().addActivity(this);
        unbinder = ButterKnife.bind(this);
        initImmersionBar();
        initMvp();
        initView();
        initData();
        initEvent();
    }

    protected void hideStatusBar() {
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_BAR)
                .init();
    }

    protected void showStatusBar() {
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_SHOW_BAR)
                .init();
    }

    protected void initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this)
                .init();
    }

    protected void initMvp() {
    }

    protected void initView() {
    }

    protected void initData() {
    }

    protected void initEvent() {
    }

    protected void beforeSetContentView() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected boolean isRegisterEventBus() {
        return false;
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
        ActivityManagerDelegate.getInstance().finishActivity(this);
        unbinder.unbind();
        unbinder = null;
    }
}