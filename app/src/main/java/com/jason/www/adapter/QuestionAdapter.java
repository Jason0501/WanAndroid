package com.jason.www.adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jason.www.R;
import com.jason.www.http.response.Question;

import org.jetbrains.annotations.NotNull;

/**
 * @author：Jason
 * @date：2020/8/11 10:49
 * @email：1129847330@qq.com
 * @description:
 */
public class QuestionAdapter extends BaseQuickAdapter<Question, BaseViewHolder> {
    public QuestionAdapter() {
        super(R.layout.item_question);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Question question) {
        baseViewHolder.setText(R.id.textview_author_item_question, question.getAuthor())
                .setText(R.id.textview_tag_item_question, question.getTags().get(0).getName())
                .setText(R.id.textview_date_item_question, question.getNiceDate())
                .setText(R.id.textview_title_item_question, question.getTitle())
                .setText(R.id.textview_tip_item_question, question.getSuperChapterName() + ":" + question.getChapterName());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            baseViewHolder.setText(R.id.textview_des_item_question, Html.fromHtml(question.getDesc(),
                    Html.FROM_HTML_MODE_COMPACT));
        } else {
            baseViewHolder.setText(R.id.textview_des_item_question, Html.fromHtml(question.getDesc()));
        }
    }
}