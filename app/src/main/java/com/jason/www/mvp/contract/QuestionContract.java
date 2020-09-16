package com.jason.www.mvp.contract;

import com.jason.www.http.response.Question;
import com.jason.www.mvp.base.IBaseModel;
import com.jason.www.mvp.base.IBasePresenter;
import com.jason.www.mvp.base.IBaseView;
import com.jason.www.mvp.callback.IRequestCallback;

/**
 * @author：动脑学院-Zee老师
 * @date：2020/9/16 22:05
 * @email：575569745@qq.com
 * @description:
 */
public interface QuestionContract {
    abstract class Model extends IBaseModel {
        public abstract void getQuestion(int page, IRequestCallback callback);
    }

    interface View extends IBaseView {
        void successGetQuestion(Question question);
    }

    abstract class Presenter extends IBasePresenter<QuestionContract.Model, QuestionContract.View> {
        public abstract void getQuestion(int page);
    }
}
