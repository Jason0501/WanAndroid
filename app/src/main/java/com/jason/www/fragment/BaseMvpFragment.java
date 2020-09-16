package com.jason.www.fragment;

import com.jason.www.base.BaseFragment;
import com.jason.www.mvp.base.IBasePresenter;
import com.jason.www.mvp.base.IBaseView;

/**
 * @author：Jason
 * @date：2020/9/16 16:49
 * @email：1129847330@qq.com
 * @description:
 */
public abstract class BaseMvpFragment<P extends IBasePresenter> extends BaseFragment implements IBaseView {
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mBasePresenter != null) {
            mBasePresenter.detach(this);
        }
    }
}