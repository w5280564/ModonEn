package com.moying.energyring.waylenBaseView;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * @Author Lyf
 * @CreateTime 2018/3/26
 * @Description 手势拦截类。用于简化，拦截水平或垂直的手势。
 **/
public class InterceptTouchListener implements View.OnTouchListener {

    // 上下文，建议用全局上下文
    // private Context mContext;

    // 按压到屏幕时的坐标
    private float[] mCoordinate;
    // 最小的滑动距离，不同手机不同
    private int mMinimumSlop;

    // 两个回调接口，当触发水平或垂直手势时，会被调用
    private OnHorizontalGestureListener mOnHorizontalGestureListener;
    private OnVerticalGestureListener mOnVerticalGestureListener;

    public InterceptTouchListener(Context mContext) {

        mCoordinate = new float[2];
        mMinimumSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // 保存按压到屏幕时的坐标
                setCoordinate(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isClickEvent(event)) {
                    // 不处理点击事件，因为部分机型的点击事件会触发ACTION_MOVE，所以这里要做过滤。
                    break;
                } else {

                    if (mOnHorizontalGestureListener != null
                            && isHorizontalGestureEvent(event)) {
                        return mOnHorizontalGestureListener.onHorizontalGesture(event.getX(), mCoordinate[0]);
                    }

//                    if (mOnVerticalGestureListener != null
//                            && isVerticalGestureEvent(event)) {
//                        return mOnVerticalGestureListener.onVerticalGesture(event.getY(), mCoordinate[1]);
//                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    /**
     * @return return true 如果用户当前正在进行水平方向的手势操作
     */
    private boolean isHorizontalGestureEvent(MotionEvent event) {

        float slopX = getSlopX(event);
        // 滑动距离，必须大于最小滑动距离。
        return slopX > mMinimumSlop && slopX >= getSlopY(event);
    }

    /**
     * @return return true 如果用户当前正在进行垂直方向的手势操作
     */
    private boolean isVerticalGestureEvent(MotionEvent event) {

        float slopY = getSlopY(event);
        // 滑动距离，必须大于最小滑动距离。
        return slopY > mMinimumSlop && getSlopX(event) < slopY;
    }

    /**
     * @return return true 如果并没有移动，只是点击而已。
     */
    private boolean isClickEvent(MotionEvent event) {
        return getSlopX(event) == 0 && getSlopY(event) == 0;
    }

    /**
     * 保存坐标
     */
    private void setCoordinate(MotionEvent event) {

        mCoordinate[0] = event.getX();
        mCoordinate[1] = event.getY();
    }

    /**
     * @return 水平滑动的距离
     */
    private float getSlopX(MotionEvent event) {
        return Math.abs(event.getX() - mCoordinate[0]);
    }

    /**
     * @return 垂直滑动的距离
     */
    private float getSlopY(MotionEvent event) {
        return Math.abs(event.getY() - mCoordinate[1]);
    }

    public InterceptTouchListener setOnHorizontalGestureListener(OnHorizontalGestureListener mOnHorizontalGestureListener) {
        this.mOnHorizontalGestureListener = mOnHorizontalGestureListener;
        return this;
    }

    public InterceptTouchListener setOnVerticalGestureListener(OnVerticalGestureListener mOnVerticalGestureListener) {
        this.mOnVerticalGestureListener = mOnVerticalGestureListener;
        return this;
    }

    public interface OnHorizontalGestureListener {

        /**
         * 当用户在进行水平方向的操作时，该方法会被调用
         *
         * @param currentX 当前的X坐标
         * @param lastX    上一次的X坐标
         * @return return true 如果要拦截事件。
         */
        boolean onHorizontalGesture(float currentX, float lastX);
    }

    public interface OnVerticalGestureListener {
        /**
         * 当用户在进行垂直方向的操作时，该方法会被调用
         *
         * @param currentY 当前的Y坐标
         * @param lastY    上一次的Y坐标
         * @return return true 如果要拦截事件。
         */
        boolean onVerticalGesture(float currentY, float lastY);
    }

}
