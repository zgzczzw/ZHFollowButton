package com.zgzczw.zhfollowbutton;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by eric.zhang on 2016/6/23.
 */
public class RippleButtonView extends Button {
    int[] mLocation = new int[2];
    float mCenterX;
    float mCenterY;
    float mRevealRadius;
    boolean mIsPressed = false;
    boolean mShouldDoAnimation = false;
    Paint mPaint = new Paint();

    public RippleButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCenterX = event.getX();
                mCenterY = event.getY();
                mRevealRadius = 0f;

                mShouldDoAnimation = true;
                setFollowed(!mIsPressed, mShouldDoAnimation);
        }
        return super.onTouchEvent(event);
    }

    protected void setFollowed(boolean isFollowed, boolean needAnimate) {
        mIsPressed = isFollowed;
        if (needAnimate) {
            ValueAnimator animator = ObjectAnimator.ofFloat(this, "empty", 0.0F, (float) Math.hypot(getMeasuredWidth(), getMeasuredHeight()));
            animator.setDuration(500L);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRevealRadius = (Float) animation.getAnimatedValue();
                    invalidate();
                }

            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (mIsPressed) {
                        setTextColor(Color.WHITE);
                        setBackgroundColor(Color.RED);
                        setText("未关注");
                    } else {
                        setTextColor(Color.BLACK);
                        setBackgroundColor(Color.WHITE);
                        setText("关注");
                    }
                    mShouldDoAnimation = false;
                    mRevealRadius = 0;
                    invalidate();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIsPressed) {
            mPaint.setColor(Color.WHITE);
        } else {
            mPaint.setColor(Color.RED);
        }//设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX, mCenterY, mRevealRadius, mPaint);

    }

}
