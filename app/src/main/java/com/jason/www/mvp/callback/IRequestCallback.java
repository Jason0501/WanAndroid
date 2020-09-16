package com.jason.www.mvp.callback;

import com.jason.www.http.response.base.BaseResponse;

/**
 * @author：Jason
 * @date：2020/9/16 14:39
 * @email：1129847330@qq.com
 * @description:
 */
public interface IRequestCallback<T> {
    void success(BaseResponse<T> response);

    void fail(String msg);
}
