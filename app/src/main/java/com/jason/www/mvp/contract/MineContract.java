package com.jason.www.mvp.contract;

import com.jason.www.http.response.UserInfo;
import com.jason.www.mvp.base.IBaseModel;
import com.jason.www.mvp.base.IBasePresenter;
import com.jason.www.mvp.base.IBaseView;
import com.jason.www.mvp.callback.IRequestCallback;

/**
 * @author：Jason
 * @date：2020/9/21 15:15
 * @email：1129847330@qq.com
 * @description:
 */
public interface MineContract {
    abstract class Model extends IBaseModel {
        public abstract void getUserInfo(IRequestCallback callback);
    }

    interface View extends IBaseView {
        void successGetUserInfo(UserInfo userInfo);
    }

    abstract class Presenter extends IBasePresenter<Model, View> {
        public abstract void getUserInfo();
    }
}
