package com.jason.www.net.response.base;

/**
 * @author：Jason
 * @date：2020/8/4 15:49
 * @email：1129847330@qq.com
 * @description:
 */
public class BaseResponse<T> {
    public String errorMsg;
    public int errorCode = FAIL;
    public T data;
    public static final int SUCCESS = 0;
    public static final int FAIL = -1;

    public boolean isOk() {
        return errorCode == SUCCESS;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "errorMsg='" + errorMsg + '\'' +
                ", errorCode=" + errorCode +
                ", data=" + data +
                '}';
    }
}