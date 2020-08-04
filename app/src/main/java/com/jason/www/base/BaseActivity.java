package com.jason.www.base;

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
//    protected T viewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
//        viewBinding = bindDataBingding();
//        View view = viewBinding.getRoot();
        setContentView(getLayoutResId());
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

//    protected abstract T bindDataBingding();

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
//        viewBinding = null;
        unbinder.unbind();
        unbinder = null;
    }
}