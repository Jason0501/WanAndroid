package com.jason.www.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.widget.NestedScrollView;

/**
 * @author：Jason
 * @date: 2020/12/11 18:05
 * @email：1129847330@qq.com
 * @description:
 */
public class PullScrollView extends NestedScrollView {
    private View mMoveView;
    private View mContainer;
    /**
     * 是否正在下拉
     */
    private boolean mIsPulling;
    /**
     * 手指按下屏幕的纵坐标
     */
    private float mInitFingerY;
    /**
     * 滑动系数，系数越大，滑动越快
     */
    private float mPullRatio = 0.5f;
    /**
     * 回弹时间系数，系数越小，回弹越快
     */
    private float mReplyRatio = 0.4f;
    /**
     * 超出屏幕的高度，也是最大拉动距离
     */
    private int mOverHeight;

    public PullScrollView(Context context) {
        this(context, null);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMoveView(View headerView) {
        mMoveView = headerView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("PullScrollView", "onMeasure()");
        if (mMoveView != null) {
            int[] location = new int[2];
            mMoveView.getLocationOnScreen(location);
            int measuredHeight = mMoveView.getHeight();
            mOverHeight = measuredHeight - Math.abs(location[1]);
            Log.d("PullScrollView", "measuredHeight:" + measuredHeight + ", location[1]:" + location[1] + ", mOverHeight:" + mOverHeight);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("PullScrollView", "onFinishInflate()");
        //设置不可过度滚动，否则上移后下拉会出现部分空白的情况
        setOverScrollMode(OVER_SCROLL_NEVER);
        mContainer = getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mContainer == null || mMoveView == null) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!mIsPulling) {
                    //第一次下拉
                    if (getScrollY() == 0) {
                        //在顶部的时候，记录顶部位置
                        mInitFingerY = event.getRawY();
                    } else {
                        break;
                    }
                }
                float moveDistance = event.getRawY() - mInitFingerY;
                if (moveDistance < 0) {
                    break;
                }
                Log.d("PullScrollView", "moveDistance:" + moveDistance);
                if (moveDistance > mOverHeight) {
                    moveDistance = mOverHeight;
                }
                moveView(moveDistance * mPullRatio);
                mIsPulling = true;
                break;
            case MotionEvent.ACTION_UP:
                recover();
                mIsPulling = false;
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 回弹
     */
    private void recover() {
        if (!mIsPulling) {
            return;
        }
        float distance = mContainer.getTranslationY();
        if (distance <= 0) {
            moveView(0);
            return;
        }
        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(distance, 0.0F).setDuration((long) (distance * mReplyRatio));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float distance = ((Float) animation.getAnimatedValue()).floatValue();
                moveView(distance);
            }
        });
        anim.start();
    }

    /**
     * 移动view
     *
     * @param distance
     */
    private void moveView(float distance) {
        mContainer.setTranslationY(distance);
    }
}
