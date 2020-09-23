package com.jason.www.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jason.www.R;
import com.jason.www.http.Article;
import com.jason.www.http.response.Collect;
import com.jason.www.utils.CollectionUtils;
import com.jason.www.utils.DateUtils;
import com.jason.www.utils.IntentUtils;
import com.jason.www.utils.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author：Jason
 * @date：2020/8/11 10:49
 * @email：1129847330@qq.com
 * @description:
 */
public class CollectionAdapter extends BaseQuickAdapter<Collect, BaseViewHolder> implements OnItemChildClickListener, OnItemClickListener {
    public static final int PRAISE_DATA = 1;

    public CollectionAdapter() {
        super(R.layout.item_home_article);
        addChildClickViewIds(R.id.iv_collect, R.id.tv_author);
        setOnItemChildClickListener(this);
        setOnItemClickListener(this);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, Collect collect) {
        holder.setText(R.id.tv_title, collect.getTitle());
        holder.setText(R.id.tv_author, TextUtils.isEmpty(collect.getAuthor()) ? collect.getShareUser() : collect.getAuthor());
        holder.setText(R.id.tv_time, DateUtils.formatTimeStamp2YMDHms(collect.getPublishTime()));
        holder.setGone(R.id.tv_new, !collect.fresh);
        holder.setText(R.id.tv_chapter_name, collect.getSuperChapterName() + "·" + collect.getChapterName());
        holder.setGone(R.id.tv_tag, CollectionUtils.isEmpty(collect.getTags()));
        holder.setText(R.id.tv_tag, CollectionUtils.isEmpty(collect.getTags()) ? "" : collect.getTags().get(0).name);
        ImageView ivCollect = holder.getView(R.id.iv_collect);
        ivCollect.setImageResource(collect.isCollect() ? R.drawable.collect_true : R.drawable.collect_false);
    }

    @Override
    public void onBindViewHolder(@NotNull BaseViewHolder holder, int position, @NotNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Article article = getData().get(position - getHeaderLayoutCount());
            for (Object payload : payloads) {
                switch ((int) payload) {
                    case PRAISE_DATA:
                        ImageView ivCollect = holder.getView(R.id.iv_collect);
                        ivCollect.setImageResource(article.isCollect() ? R.drawable.collect_true : R.drawable.collect_false);
                        break;
                }
            }

        }
    }

    @Override
    protected void setOnItemChildClick(@NotNull View view, int position) {
        super.setOnItemChildClick(view, position);
        Article article = getData().get(position);
        switch (view.getId()) {
            case R.id.iv_collect:
                if (!article.isCollect()) {
                    if (onCollectListener != null) {
                        onCollectListener.addCollect(article);
                    }
                } else {
                    if (onCollectListener != null) {
                        onCollectListener.cancelCollect(article);
                    }
                }
                article.setCollect(!article.isCollect());
                notifyItemChanged(position + getHeaderLayoutCount(), CollectionAdapter.PRAISE_DATA);
                break;
            case R.id.tv_author:
                break;
        }
    }

    @Override
    protected void setOnItemClick(@NotNull View v, int position) {
        super.setOnItemClick(v, position);
        Article homeArticle = getData().get(position);
        IntentUtils.goToWebViewActivity(homeArticle.getLink());
    }

    public void setOnCollectListener(OnCollectListener onCollectListener) {
        this.onCollectListener = onCollectListener;
    }

    private OnCollectListener onCollectListener;

    @Override
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

    }

    public interface OnCollectListener {
        void addCollect(Article article);

        void cancelCollect(Article article);
    }
}