package com.jason.www.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jason.www.R;
import com.jason.www.http.response.FrequentWebSite;

import org.jetbrains.annotations.NotNull;

/**
 * @author：Jason
 * @date：2020/8/11 10:49
 * @email：1129847330@qq.com
 * @description:
 */
public class FrequentWebSiteAdapter extends BaseQuickAdapter<FrequentWebSite, BaseViewHolder> {
    public FrequentWebSiteAdapter() {
        super(R.layout.item_frequentwebsite);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder,
                           FrequentWebSite frequentWebSite) {
        baseViewHolder.setText(R.id.textview_title_item_frequentwebsite, frequentWebSite.getName());
    }
}