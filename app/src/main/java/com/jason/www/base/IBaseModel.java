package com.jason.www.base;

import com.jason.www.http.response.base.BaseResponse;

/**
 * @author：Jason
 * @date：2020/9/16 10:19
 * @email：1129847330@qq.com
 * @description:
 */
public abstract class IBaseModel {
    public interface IRequestCallback<T> {
        void success(BaseResponse<T> response);

        void fail(String msg);
    }
}
