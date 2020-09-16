package com.jason.www.contract;

import com.jason.www.base.IBaseModel;
import com.jason.www.base.IBasePresenter;
import com.jason.www.base.IBaseView;
import com.jason.www.http.response.HomeArticleBody;
import com.jason.www.http.response.HomeBanner;

import java.util.List;

/**
 * @author：Jason
 * @date：2020/9/16 11:36
 * @email：1129847330@qq.com
 * @description:
 */
public interface MainContract {
    abstract class Model extends IBaseModel {
        public abstract void getBannerHome(IBaseModel.IRequestCallback callback);

        public abstract void getHomeArticles(int page, IBaseModel.IRequestCallback callback);
    }

    interface View extends IBaseView {
        void successGetBanner(List<HomeBanner> list);

        void successGetHomeArticles(HomeArticleBody homeArticleBody);
    }

    abstract class Presenter extends IBasePresenter<MainContract.Model, MainContract.View> {
        public abstract void getBannerHome();

        public abstract void getHomeArticles(int page);
    }
}
