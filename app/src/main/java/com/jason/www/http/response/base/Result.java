package com.jason.www.http.response.base;

/**
 * @author：Jason
 * @date：2020/8/17 10:24
 * @email：1129847330@qq.com
 * @description:
 */
public class Result<T> {
    public int page;
    public int page_count;
    public int status;
    public int total_counts;
    public T data;

    @Override
    public String toString() {
        return "Result{" +
                "page=" + page +
                ", page_count=" + page_count +
                ", status=" + status +
                ", total_counts=" + total_counts +
                ", data=" + data +
                '}';
    }
}