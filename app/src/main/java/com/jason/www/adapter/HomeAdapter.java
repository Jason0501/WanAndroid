package com.jason.www.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jason.www.R;
import com.jason.www.net.response.HomeArticleBody;
import com.jason.www.utils.DateUtils;
import com.jason.www.utils.TextUtils;

import org.jetbrains.annotations.NotNull;

/**
 * @author：Jason
 * @date：2020/8/11 10:49
 * @email：1129847330@qq.com
 * @description:
 */
public class HomeAdapter extends BaseQuickAdapter<HomeArticleBody.HomeArticle, BaseViewHolder> {
    public HomeAdapter() {
        super(R.layout.item_home_article);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeArticleBody.HomeArticle article) {
        baseViewHolder.setText(R.id.textview_title_item_home_article, article.getTitle());
        baseViewHolder.setText(R.id.textview_time_and_author_item_home_article, String.format("by-%s %s", TextUtils.isEmpty(article.getAuthor()) ?
                article.getShareUser() : article.getAuthor(), DateUtils.formatTimeStamp2YMDHms(article.getPublishTime())));
    }
}