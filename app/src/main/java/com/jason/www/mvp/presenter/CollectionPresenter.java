package com.jason.www.mvp.presenter;

import com.jason.www.http.response.BaseListResponse;
import com.jason.www.http.response.Collect;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.mvp.callback.IRequestCallback;
import com.jason.www.mvp.contract.CollectionContract;
import com.jason.www.mvp.model.CollectionModel;

/**
 * @author：Jason
 * @date：2020/9/23 11:34
 * @email：1129847330@qq.com
 * @description:
 */
public class CollectionPresenter extends CollectionContract.Presenter {
    @Override
    public void getWebSiteCollection(int page) {
        getModel().getWebSiteCollection(page, new IRequestCallback<BaseListResponse<Collect>>() {
            @Override
            public void success(BaseResponse<BaseListResponse<Collect>> response) {
                if (response.isOk()) {
                    getView().successGetWebSiteCollection(response.data);
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
    public void getArticleCollection(int page) {
        getModel().getArticleCollection(page, new IRequestCallback<BaseListResponse<Collect>>() {
            @Override
            public void success(BaseResponse<BaseListResponse<Collect>> response) {
                if (response.isOk()) {
                    getView().successGetArticleCollection(response.data);
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
        getModel().addCollection(articleId, new IRequestCallback() {
            @Override
            public void success(BaseResponse response) {
                if (response.isOk()) {
                    getView().successAddCollection();
                } else {
                    getView().failLoad(response.errorMsg);
                }
            }

            @Override
            public void fail(String errorMsg) {
                getView().failLoad(errorMsg);
            }
        });
    }

    @Override
    public void cancelCollection(int articleId) {
        getModel().cancelCollection(articleId, new IRequestCallback() {
            @Override
            public void success(BaseResponse response) {
                if (response.isOk()) {
                    getView().successCancelCollection();
                } else {
                    getView().failLoad(response.errorMsg);
                }
            }

            @Override
            public void fail(String errorMsg) {
                getView().failLoad(errorMsg);
            }
        });
    }

    @Override
    protected CollectionContract.Model createModel() {
        return new CollectionModel();
    }
}