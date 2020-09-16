package com.jason.www.mvp.presenter;

import com.jason.www.http.response.FrequentWebSite;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.mvp.callback.IRequestCallback;
import com.jason.www.mvp.contract.FrequentWebSiteContract;
import com.jason.www.mvp.model.FrequentWebSiteModel;

import java.util.List;

/**
 * @author：动脑学院-Zee老师
 * @date：2020/9/16 22:05
 * @email：575569745@qq.com
 * @description:
 */
public class FrequentWebSitePresenter extends FrequentWebSiteContract.Presenter {
    @Override
    public void getFrequentWebSite() {
        getModel().getFrequentWebSite(new IRequestCallback<List<FrequentWebSite>>() {
            @Override
            public void success(BaseResponse<List<FrequentWebSite>> response) {
                if (response.isOk()) {
                    getView().successGetFrequentWebSite(response.data);
                } else {
                    getView().failLoad(response.errorMsg);
                }
            }

            @Override
            public void fail(String msg) {
                getView().failLoad(msg);
            }
        });
    }

    @Override
    protected FrequentWebSiteModel createModel() {
        return new FrequentWebSiteModel();
    }
}