package com.moying.energyring.waylenBaseView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.moying.energyring.R;
import com.sunfusheng.marqueeview.Utils;

/**
 * Created by waylen on 2018/3/21.
 */

public class CountDownView extends View {
    //圆轮颜色
    private int mRingColor;
    //圆轮宽度
    private float mRingWidth;
    //圆轮进度值文本大小
    private int mRingProgessTextSize;
    //宽度
    private int mWidth;
    //高度
    private int mHeight;
    private Paint mPaint;
    //圆环的矩形区域
    private RectF mRectF;
    //
    private int mProgessTextColor;
    private int mCountdownTime;
    private float mCurrentProgress;
    private OnCountDownFinishListener mListener;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        mRingColor = a.getColor(R.styleable.CountDownView_ringColor, ContextCompat.getColor(context,R.color.colorAccent));
        mRingWidth = a.getFloat(R.styleable.CountDownView_ringWidth, 40);
        mRingProgessTextSize = a.getDimensionPixelSize(R.styleable.CountDownView_progressTextSize, Utils.sp2px(context, 20));
        mProgessTextColor = a.getColor(R.styleable.CountDownView_progressTextColor, ContextCompat.getColor(context,R.color.colorAccent));
        mCountdownTime = a.getInteger(R.styleable.CountDownView_countdownTime, 60);
        a.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        this.setWillNotDraw(false);
    }

    public void setCountdownTime(int mCountdownTime) {
        this.mCountdownTime = mCountdownTime;
    }

    public void setmRingWidth(float width) {
        this.mRingWidth = width;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRectF = new RectF(0 + mRingWidth / 2, 0 + mRingWidth / 2,
                mWidth - mRingWidth / 2, mHeight - mRingWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         *圆环
         */
        //颜色
        mPaint.setColor(mRingColor);
        //空心
        mPaint.setStyle(Paint.Style.STROKE);
        //宽度
        mPaint.setStrokeWidth(mRingWidth);
        canvas.drawArc(mRectF, -90, 360 - mCurrentProgress, false, mPaint);
        //绘制文本
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        String text = mCountdownTime - (int) (mCurrentProgress / 360f * mCountdownTime) + "";
        textPaint.setTextSize(mRingProgessTextSize);
        textPaint.setColor(mProgessTextColor);

        //文字居中显示
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (int) ((mRectF.bottom + mRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawText(text, mRectF.centerX(), baseline, textPaint);
    }

    private ValueAnimator getValA(long countdownTime) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100);
        valueAnimator.setDuration(countdownTime);
        valueAnimator.setInterpolator(new LinearInterpolator());//插值器
        valueAnimator.setRepeatCount(0);//循环次数
        return valueAnimator;
    }

    /**
     * 开始倒计时
     */
    ValueAnimator valueAnimator;

    public void startCountDown() {
        setClickable(false);
        valueAnimator = getValA(mCountdownTime * 1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float i = Float.valueOf(String.valueOf(animation.getAnimatedValue()));
                mCurrentProgress = (int) (360 * (i / 100f));
                invalidate();
            }
        });
        valueAnimator.start();

        isbackFinish(true);
    }


    private void isbackFinish(final boolean isBack) {
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //倒计时结束回调
                if (isBack) {
                    if (mListener != null) {
                        mListener.countDownFinished();
                    }
                }
                setClickable(true);
            }

        });

    }

    //增加时间
    public void changeCount(int mCountdownTime) {
        this.mCountdownTime = mCountdownTime;
        long currentCount = valueAnimator.getCurrentPlayTime();
        valueAnimator.setDuration(mCountdownTime * 1000);
        valueAnimator.setCurrentPlayTime(currentCount);
        valueAnimator.resume();

    }

    public void stopAnimator() {
        isbackFinish(false);
    }

    public void setAddCountDownListener(OnCountDownFinishListener mListener) {
        this.mListener = mListener;
    }

    public interface OnCountDownFinishListener {
        void countDownFinished();
    }


}
