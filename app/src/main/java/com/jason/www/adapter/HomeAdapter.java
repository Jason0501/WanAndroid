package com.jason.www.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jason.www.R;
import com.jason.www.net.response.HomeArticleBody;
import com.jason.www.utils.DateUtils;

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
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeArticleBody.HomeArticle homeArticle) {
        baseViewHolder.setText(R.id.textview_title_item_home_article, homeArticle.getTitle());
        baseViewHolder.setText(R.id.textview_time_and_author_item_home_article,
                "by " + homeArticle.getChapterName() + " " + DateUtils.formatTimeStamp2YMDHms(homeArticle.getPublishTime()));
    }
}