package com.zgzczw.zhfollowbutton;

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
    float mMaxRadius = 200;
    public static int INVALIDATE_DURATION = 1;
    boolean mShouldDoAnimation = false;
    float mRevealRadiusGap = 5f;
    int mMinBetweenWidthAndHeight = 10;

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
                mIsPressed = !mIsPressed;
                if (mIsPressed) {
                    setTextColor(Color.WHITE);
                } else {
                    setTextColor(Color.BLACK);
                }
                mShouldDoAnimation = true;
                invalidate();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShouldDoAnimation) {
            mMaxRadius = getMeasuredWidth() + 50;
            if (mRevealRadius > mMinBetweenWidthAndHeight / 2)
                mRevealRadius += mRevealRadiusGap * 4;
            else
                mRevealRadius += mRevealRadiusGap;//半径变大

            Paint mPaint = new Paint();
            if (!mIsPressed) {
                mPaint.setColor(Color.WHITE);
            } else {
                mPaint.setColor(Color.RED);
            }//设置画笔颜色
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mCenterX, mCenterY, mRevealRadius, mPaint);

            if (mRevealRadius <= mMaxRadius) {
                //一定时间后再刷新
                postInvalidateDelayed(INVALIDATE_DURATION);
            } else {
                if (mIsPressed) {
                    setTextColor(Color.WHITE);
                    this.setBackgroundColor(Color.RED);
                } else {
                    setTextColor(Color.BLACK);
                    this.setBackgroundColor(Color.WHITE);
                }
                mShouldDoAnimation = false;
                invalidate();
            }
        }
    }

}
