package com.jason.www.mvp.contract;

import com.jason.www.http.response.BaseListResponse;
import com.jason.www.http.response.Collect;
import com.jason.www.mvp.base.IBaseModel;
import com.jason.www.mvp.base.IBasePresenter;
import com.jason.www.mvp.base.IBaseView;
import com.jason.www.mvp.callback.IRequestCallback;

/**
 * @author：Jason
 * @date：2020/9/23 11:34
 * @email：1129847330@qq.com
 * @description:
 */
public interface CollectionContract {
    abstract class Model extends IBaseModel {
        public abstract void addCollection(int articleId, IRequestCallback callback);

        public abstract void cancelCollection(int articleId, IRequestCallback callback);

        public abstract void getWebSiteCollection(int page, IRequestCallback callback);

        public abstract void getArticleCollection(int page, IRequestCallback callback);
    }

    interface View extends IBaseView {
        void successGetArticleCollection(BaseListResponse<Collect> list);

        void successGetWebSiteCollection(BaseListResponse<Collect> list);

        void successAddCollection();

        void successCancelCollection();
    }

    abstract class Presenter extends IBasePresenter<Model, View> {
        public abstract void getWebSiteCollection(int page);

        public abstract void getArticleCollection(int page);

        public abstract void addCollection(int articleId);

        public abstract void cancelCollection(int articleId);
    }
}
