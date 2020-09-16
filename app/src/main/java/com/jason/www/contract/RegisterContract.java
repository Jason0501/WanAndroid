package com.jason.www.contract;

import com.jason.www.base.IBaseModel;
import com.jason.www.base.IBasePresenter;
import com.jason.www.base.IBaseView;
import com.jason.www.http.response.Register;

/**
 * @author：Jason
 * @date：2020/9/16 12:25
 * @email：1129847330@qq.com
 * @description:
 */
public interface RegisterContract {
    abstract class Model extends IBaseModel {
        public abstract IBaseModel register(String username, String password, String rePassword, IBaseModel.IRequestCallback callback);
    }

    interface View extends IBaseView {
        void successRegister(Register register);
    }

    abstract class Presenter extends IBasePresenter<RegisterContract.Model, RegisterContract.View> {
        public abstract void register(String username, String password, String rePassword);
    }
}
