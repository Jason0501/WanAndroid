package com.jason.www.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.jason.www.config.AppData;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * @author：Jason
 * @date：2020/7/24 16:21
 * @email：1129847330@qq.com
 * @description:
 */
public abstract class BaseFragment extends LazyFragment {
    protected AppCompatActivity mActivity;
    protected Context mContext;
    private Toast mToast;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (AppCompatActivity) context;
    }

    @Override
    protected void initView() {
        super.initView();
        initImmersionBar();
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    protected void initImmersionBar() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected boolean isRegisterEventBus() {
        return false;
    }

    protected int getColor(int resId) {
        return ContextCompat.getColor(mContext, resId);
    }

    protected Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(mContext, resId);
    }

    protected void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(AppData.getContext(), msg, Toast.LENGTH_SHORT);
        mToast.setText(msg);
        mToast.show();
    }

    public void startActivity(Class<?> clazz) {
        startActivity(new Intent(mContext, clazz));
    }
}