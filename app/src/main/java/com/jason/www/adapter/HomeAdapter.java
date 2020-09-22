package com.jason.www.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jason.www.R;
import com.jason.www.http.response.HomeArticleBody;
import com.jason.www.utils.CollectionUtils;
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
        addChildClickViewIds(R.id.iv_collect, R.id.tv_author);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeArticleBody.HomeArticle article) {
        baseViewHolder.setText(R.id.tv_title, article.getTitle());
        baseViewHolder.setText(R.id.tv_author, TextUtils.isEmpty(article.getAuthor()) ? article.getShareUser() : article.getAuthor());
        baseViewHolder.setText(R.id.tv_time, DateUtils.formatTimeStamp2YMDHms(article.getPublishTime()));
        baseViewHolder.setGone(R.id.tv_new, !article.fresh);
        baseViewHolder.setText(R.id.tv_chapter_name, article.getSuperChapterName() + "·" + article.getChapterName());
        baseViewHolder.setGone(R.id.tv_tag, CollectionUtils.isEmpty(article.getTags()));
        baseViewHolder.setText(R.id.tv_tag, CollectionUtils.isEmpty(article.getTags()) ? "" : article.getTags().get(0).name);
        ImageView ivCollect = baseViewHolder.getViewOrNull(R.id.iv_collect);
        ivCollect.setImageResource(article.isCollect() ? R.drawable.collect_true : R.drawable.collect_false);
    }
}