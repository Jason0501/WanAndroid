package com.jason.www.mvp.contract;

import com.jason.www.http.response.FrequentWebSite;
import com.jason.www.mvp.base.IBaseModel;
import com.jason.www.mvp.base.IBasePresenter;
import com.jason.www.mvp.base.IBaseView;
import com.jason.www.mvp.callback.IRequestCallback;

import java.util.List;

/**
 * @author：动脑学院-Zee老师
 * @date：2020/9/16 22:05
 * @email：575569745@qq.com
 * @description:
 */
public interface FrequentWebSiteContract {
    abstract class Model extends IBaseModel {
        public abstract void getFrequentWebSite(IRequestCallback callback);
    }

    interface View extends IBaseView {
        void successGetFrequentWebSite(List<FrequentWebSite> list);
    }

    abstract class Presenter extends IBasePresenter<FrequentWebSiteContract.Model, FrequentWebSiteContract.View> {
        public abstract void getFrequentWebSite();
    }
}
