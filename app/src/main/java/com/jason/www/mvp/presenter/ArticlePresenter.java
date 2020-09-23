package com.jason.www.mvp.presenter;

import com.jason.www.http.Article;
import com.jason.www.http.response.BaseListResponse;
import com.jason.www.http.response.HomeBanner;
import com.jason.www.http.response.base.BaseBean;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.mvp.callback.IRequestCallback;
import com.jason.www.mvp.contract.ArticleContract;
import com.jason.www.mvp.model.ArticleModel;

import java.util.List;

/**
 * @author：Jason
 * @date：2020/9/16 11:36
 * @email：1129847330@qq.com
 * @description:
 */
public class ArticlePresenter extends ArticleContract.Presenter {
    @Override
    protected ArticleContract.Model createModel() {
        return new ArticleModel();
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
        getModel().getHomeArticles(page, new IRequestCallback<BaseListResponse<Article>>() {
            @Override
            public void success(BaseResponse<BaseListResponse<Article>> response) {
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

    @Override
    public void addCollection(int articleId) {
        getModel().addCollection(articleId, new IRequestCallback<BaseBean>() {
            @Override
            public void success(BaseResponse<BaseBean> response) {
                if (response.isOk()) {
                    getView().successAddCollection();
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
    public void cancelCollection(int articleId) {
        getModel().cancelCollection(articleId, new IRequestCallback<BaseBean>() {
            @Override
            public void success(BaseResponse<BaseBean> response) {
                if (response.isOk()) {
                    getView().successCancelCollection();
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