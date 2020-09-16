package com.jason.www.mvp.presenter;

import com.jason.www.http.response.HomeArticleBody;
import com.jason.www.http.response.HomeBanner;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.mvp.callback.IRequestCallback;
import com.jason.www.mvp.contract.MainContract;
import com.jason.www.mvp.model.MainModel;

import java.util.List;

/**
 * @author：Jason
 * @date：2020/9/16 11:36
 * @email：1129847330@qq.com
 * @description:
 */
public class MainPresenter extends MainContract.Presenter {
    @Override
    protected MainContract.Model createModel() {
        return new MainModel();
    }

    @Override
    public void getBannerHome() {
        getModel().getBannerHome(new IRequestCallback<List<HomeBanner>>() {
            @Override
            public void success(BaseResponse<List<HomeBanner>> response) {
                if (response.isOk()) {
                    getView().successGetBanner(response.data);
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
    public void getHomeArticles(int page) {
        getModel().getHomeArticles(page, new IRequestCallback<HomeArticleBody>() {
            @Override
            public void success(BaseResponse<HomeArticleBody> response) {
                if (response.isOk()) {
                    getView().successGetHomeArticles(response.data);
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
}