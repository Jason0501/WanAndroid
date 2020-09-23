package com.jason.www.mvp.presenter;

import com.jason.www.http.response.BaseListResponse;
import com.jason.www.http.response.Question;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.mvp.callback.IRequestCallback;
import com.jason.www.mvp.contract.QuestionContract;
import com.jason.www.mvp.model.QuestionModel;

/**
 * @author：Jason
 * @date：2020/9/16 11:36
 * @email：1129847330@qq.com
 * @description:
 */
public class QuestionPresenter extends QuestionContract.Presenter {
    @Override
    protected QuestionContract.Model createModel() {
        return new QuestionModel();
    }

    @Override
    public void getQuestion(int page) {
        getModel().getQuestion(page, new IRequestCallback<BaseListResponse<Question>>() {
            @Override
            public void success(BaseResponse<BaseListResponse<Question>> response) {
                if (response.isOk()) {
                    getView().successGetQuestion(response.data);
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