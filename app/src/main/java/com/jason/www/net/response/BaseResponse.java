package com.jason.www.net.response;

/**
 * @author：Jason
 * @date：2020/8/4 15:49
 * @email：1129847330@qq.com
 * @description:
 */
public class BaseResponse<T> {
    public String errorMsg;
    public int errorCode;
    public T data;
}