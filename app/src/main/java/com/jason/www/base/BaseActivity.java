package com.jason.www.base;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.jason.www.utils.LogUtils;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        mActivity = this;
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(getLayoutResId());
        ActivityStackManager.getInstance().addActivity(this);
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected void beforeSetContentView() {
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLog(String log) {
        LogUtils.i(log);
    }

    protected abstract int getLayoutResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getInstance().finishActivity(this);
        unbinder.unbind();
        unbinder = null;
    }
}