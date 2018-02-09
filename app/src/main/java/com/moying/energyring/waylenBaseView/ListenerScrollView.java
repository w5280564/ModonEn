package com.moying.energyring.waylenBaseView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ScrollView;

/**
 * package : com.moying.energyring.widget<br>
 * description:<B>        </B><br>
 * email : cookiexie@adinnet.cn<br>
 * date : 2016/1/12 14:25<br>
 *
 * @author Cookie Xie
 * @version v1.0
 */

public class ListenerScrollView extends ScrollView implements PullToRefreshLayout.Pullable {
    private float dp230;
    private boolean isInterceptTouchEvent = true;

    public ListenerScrollView(Context context) {
        this(context, null);
    }

    public ListenerScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListenerScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        dp230 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 230, getResources().getDisplayMetrics());
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (t <= dp230) {
            isInterceptTouchEvent = true;
        } else {
            isInterceptTouchEvent = false;
        }
        if (onScrollPercentListener != null) {
            float end = dp230 * 8 / 10;
            if (t <= end) {
                onScrollPercentListener.onScrollPercent(t / end);
            }
        }
    }

    @Override
    public boolean canPullDown() {
        if (getScrollY() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
            return true;
        else
            return false;
    }

    private OnScrollPercentListener onScrollPercentListener;

    public void setOnScrollPercentListener(OnScrollPercentListener onScrollPercentListener) {
        this.onScrollPercentListener = onScrollPercentListener;
    }

    public interface OnScrollPercentListener {
        /**
         * 下拉距离百分比
         */
        void onScrollPercent(float percent);
    }
}
