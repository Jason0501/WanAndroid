package com.jason.www.base;

import com.jason.www.mvp.base.IBasePresenter;
import com.jason.www.mvp.base.IBaseView;

/**
 * @author：Jason
 * @date：2020/9/16 12:33
 * @email：1129847330@qq.com
 * @description:
 */
public abstract class BaseMvpActivity<P extends IBasePresenter> extends BaseActivity implements IBaseView {
    public P getPresenter() {
        return mBasePresenter;
    }

    private P mBasePresenter;

    @Override
    protected void initMvp() {
        super.initMvp();
        mBasePresenter = createPresenter();
        if (mBasePresenter != null) {
            mBasePresenter.attach(this);
        }
    }

    protected abstract P createPresenter();

    @Override
    public void failLoad(String msg) {
        showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBasePresenter != null) {
            mBasePresenter.detach(this);
        }
    }
}