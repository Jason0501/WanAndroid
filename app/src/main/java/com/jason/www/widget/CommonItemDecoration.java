package com.jason.www.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.jason.www.R;
import com.jason.www.config.AppData;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：Jason
 * @date：2020/7/29 09:58
 * @email：1129847330@qq.com
 * @description: 自定义recyclerview的分割线，可定义颜色和粗细
 */
public class CommonItemDecoration extends RecyclerView.ItemDecoration {
    private static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    private static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private int mSpace = 1;     //间隔
    private Rect mRect = new Rect(0, 0, 0, 0);
    private Paint mPaint = new Paint();
    private int mOrientation;
    private static final int DEFAULT_COLOR = ContextCompat.getColor(AppData.getContext(), R.color.windows_background);
    private static final float DEFAULT_STROKE_WIDTH = AppData.getContext().getResources().getDimension(R.dimen.divider_line_horizontal_height);

    public ItemDecorationPadding getPadding() {
        return padding;
    }

    public void setPadding(ItemDecorationPadding padding) {
        this.padding = padding;
    }

    private ItemDecorationPadding padding;

    public static class ItemDecorationPadding {
        public int paddingLeft, paddingRight;

        public ItemDecorationPadding(int paddingLeft, int paddingRight) {
            this.paddingLeft = paddingLeft;
            this.paddingRight = paddingRight;
        }
    }

    private CommonItemDecoration(int orientation, @ColorInt int color, int space) {
        mOrientation = orientation;
        if (space > 0) {
            mSpace = space;
        }
        mPaint.setColor(color);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + (padding == null ? 0 : padding.paddingLeft);
        final int right = parent.getWidth() - parent.getPaddingRight() - (padding == null ? 0 : padding.paddingRight);

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mSpace;
            mRect.set(left, top, right, bottom);
            c.drawRect(mRect, mPaint);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mSpace;
            mRect.set(left, top, right, bottom);
            c.drawRect(mRect, mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mSpace);
        } else {
            outRect.set(0, 0, mSpace, 0);
        }
    }

    public static CommonItemDecoration createVertical() {
        return new CommonItemDecoration(VERTICAL_LIST, DEFAULT_COLOR, (int) DEFAULT_STROKE_WIDTH);
    }

    public static CommonItemDecoration createHorizontal() {
        return new CommonItemDecoration(HORIZONTAL_LIST, DEFAULT_COLOR, (int) DEFAULT_STROKE_WIDTH);
    }
} 