package com.jason.www.mvp.contract;

import com.jason.www.http.Article;
import com.jason.www.http.response.BaseListResponse;
import com.jason.www.http.response.HomeBanner;
import com.jason.www.mvp.base.IBaseModel;
import com.jason.www.mvp.base.IBasePresenter;
import com.jason.www.mvp.base.IBaseView;
import com.jason.www.mvp.callback.IRequestCallback;

import java.util.List;

/**
 * @author：Jason
 * @date：2020/9/16 11:36
 * @email：1129847330@qq.com
 * @description:
 */
public interface ArticleContract {
    abstract class Model extends IBaseModel {
        public abstract void getBannerHome(IRequestCallback callback);

        public abstract void getHomeArticles(int page, IRequestCallback callback);

        public abstract void addCollection(int articleId, IRequestCallback callback);

        public abstract void cancelCollection(int articleId, IRequestCallback callback);
    }

    interface View extends IBaseView {
        void successGetBanner(List<HomeBanner> list);

        void successGetHomeArticles(BaseListResponse<Article> baseListResponse);

        void successAddCollection();

        void successCancelCollection();
    }

    abstract class Presenter extends IBasePresenter<ArticleContract.Model, ArticleContract.View> {
        public abstract void getBannerHome();

        public abstract void getHomeArticles(int page);

        public abstract void addCollection(int articleId);

        public abstract void cancelCollection(int articleId);
    }
}
