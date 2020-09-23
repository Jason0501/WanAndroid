package com.jason.www.http.response;

import java.util.List;

/**
 * @author：Jason
 * @date：2020/8/11 10:55
 * @email：1129847330@qq.com
 * @description:
 */
public class BaseListResponse<T> {

    /**
     * curPage : 2
     * datas :[]
     * offset : 20
     * over : false
     * pageCount : 451
     * size : 20
     * total : 9002
     */

    public int curPage;
    public int offset;
    public boolean over;
    public int pageCount;
    public int size;
    public int total;
    public List<T> datas;

    @Override
    public String toString() {
        return "BaseListResponse{" +
                "curPage=" + curPage +
                ", offset=" + offset +
                ", over=" + over +
                ", pageCount=" + pageCount +
                ", size=" + size +
                ", total=" + total +
                ", datas=" + datas +
                '}';
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}