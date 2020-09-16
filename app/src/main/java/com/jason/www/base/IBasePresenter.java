package com.jason.www.base;

/**
 * @author：Jason
 * @date：2020/9/16 10:19
 * @email：1129847330@qq.com
 * @description:
 */
public abstract class IBasePresenter<K extends IBaseModel, T extends IBaseView> {
    private K mModel;
    private T mView;

    public K getModel() {
        return mModel;
    }

    public T getView() {
        return mView;
    }

    public IBasePresenter() {
        this.mModel = createModel();
    }

    protected abstract K createModel();

    public void attach(T view) {
        this.mView = view;
    }

    public void detach(T view) {
        this.mView = null;
    }
}