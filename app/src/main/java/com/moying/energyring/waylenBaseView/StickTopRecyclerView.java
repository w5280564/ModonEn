package com.moying.energyring.waylenBaseView;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zdh on 16/12/11.
 */

public class StickTopRecyclerView extends RecyclerView {
    public static final int STATUS_TOP = 0;
    public static final int STATUS_FOLLOW = 1;
    protected OnScrollChangedListener mOnScrollChangedListener;
    TopView mSelfTopView;
    protected LinearLayoutManager mLayoutManager;
    protected int mTopMargin;

    protected int mTopViewTopMargin;//距离顶部多远距离

    public StickTopRecyclerView(Context context) {
        super(context);
    }

    public StickTopRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                int scrollY, int scrollRangeX, int scrollRangeY,
                                int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX, 0, isTouchEvent);
    }

    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        if (scrollY < 0 && !clampedY && mSelfTopView != null) {
            mSelfTopView.checkAndSetTopStateChange();
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
        }
        setRecyclerViewScrollChanged(mTopMargin);
    }

    public void setRecyclerViewScrollChanged(int topMargin) {
        mTopMargin = topMargin;
        if (mSelfTopView != null) {
            mSelfTopView.checkAndSetTopStateChange();
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.mOnScrollChangedListener = onScrollChangedListener;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (!(layout instanceof LinearLayoutManager)) {
            layout = new LinearLayoutManager(getContext());
        }
        mLayoutManager = (LinearLayoutManager) layout;
        super.setLayoutManager(mLayoutManager);
    }

    public View getTopView() {
        if (mSelfTopView != null) {
            return mSelfTopView.mTrueTopView;
        }
        return null;
    }

    public void setOnTopViewLayoutChangeListener(OnTopViewLayoutChangeListener onTopViewLayoutChangeListener) {
        if (mSelfTopView != null) {
            mSelfTopView.setOnTopViewLayoutChangeListner(onTopViewLayoutChangeListener);
        }
    }

    public EmptyView setTopView(final FrameLayout containerLayout, final View newTopView, SetTopParams topParams) {
        mTopViewTopMargin = topParams != null ? topParams.marginTopHeight : 0;
        final TopView topView = new TopView(getContext());

        final EmptyView emptyView = new EmptyView(getContext());
//        emptyView.setBackgroundResource(R.color.transparent);
        emptyView.addView(newTopView);
        emptyView.setOrientation(LinearLayout.VERTICAL);
        emptyView.setLayoutParams(newTopView.getLayoutParams());

        final ActualEmptyView actualEmptyView = new ActualEmptyView(getContext());
//        actualEmptyView.setBackgroundResource(R.color.transparent);

        topView.addView(actualEmptyView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        topView.setOrientation(LinearLayout.VERTICAL);

        topView.setTopView(newTopView);
        topView.setEmptyView(emptyView);
        topView.setActualEmptyView(actualEmptyView);
        topView.setVisibility(View.GONE);

        if (mSelfTopView != null) {
            containerLayout.removeView(mSelfTopView);
        }
        mSelfTopView = topView;

        FrameLayout.LayoutParams topViewParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        topViewParams.topMargin = mTopViewTopMargin;
        containerLayout.addView(topView, containerLayout.getChildCount(), topViewParams);

        topView.getViewTreeObserver().dispatchOnGlobalLayout();
        emptyView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (emptyView.getParent() == StickTopRecyclerView.this) {
                    MarginLayoutParams lParams = topView.getMarginParams();
                    int topMargin = emptyView.getTop();
                    if (topMargin <= mTopViewTopMargin) {
                        topMargin = mTopViewTopMargin;
                    }
                    lParams.topMargin = topMargin;
                    topView.setLayoutParams(topView.getLayoutParams());
                    emptyView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    emptyView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        emptyView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (emptyView.getLayoutParams() instanceof LayoutParams) {
                    mSelfTopView.checkAndSetTopStateChange();
                }
            }
        });

        return emptyView;
    }

    public EmptyView setTopView(final FrameLayout containerLayout, final View newTopView) {
        return setTopView(containerLayout, newTopView, null);
    }

    public void updateSetTopParams(SetTopParams setTopParams){
        if (setTopParams == null || mTopViewTopMargin == setTopParams.marginTopHeight){
            return;
        }
        mTopViewTopMargin = setTopParams.marginTopHeight;

    }

    public void updateTopView(TopView view){

    }


    public static class EmptyView extends LinearLayout {
        public EmptyView(Context context) {
            this(context, null);
        }

        public EmptyView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
        }
    }

    public static class ActualEmptyView extends View {
        public ActualEmptyView(Context context) {
            this(context, null);
        }

        public ActualEmptyView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
        }
    }



    private class TopView extends LinearLayout {
        private Integer status = STATUS_FOLLOW;
        private EmptyView mEmptyView;
        private ActualEmptyView mActualEmptyView;
        private View mTrueTopView;
        private int mPosition = -1;
        private int mTopViewHeight = 0;
        private List<OnTopViewLayoutChangeListener> mOnTopViewLayoutChangeListeners;

        public TopView(Context context) {
            this(context, null);
        }

        public TopView(Context context, AttributeSet attrs) {
            super(context, attrs);
//            setBackgroundResource(R.color.transparent);
            mOnTopViewLayoutChangeListeners = new ArrayList<OnTopViewLayoutChangeListener>();
        }

        public void setOnTopViewLayoutChangeListner(
                OnTopViewLayoutChangeListener onTopViewLayoutChangeListener) {
            if (onTopViewLayoutChangeListener != null && mOnTopViewLayoutChangeListeners.indexOf(onTopViewLayoutChangeListener) < 0) {
                mOnTopViewLayoutChangeListeners.add(onTopViewLayoutChangeListener);
            }
        }

        public int getPosition() {
            if (mEmptyView == null || !(mEmptyView.getLayoutParams() instanceof RecyclerView.LayoutParams)) {
                return mPosition;
            }
            int temPosition = mLayoutManager.getPosition(mEmptyView);
            if (temPosition != -1) {
                mPosition = temPosition;
            }
            return mPosition;
        }

        public int getCurrentPosition() {
            if (mEmptyView == null || !(mEmptyView.getLayoutParams() instanceof RecyclerView.LayoutParams)) {
                return -1;
            }
            return mLayoutManager.getPosition(mEmptyView);
        }

        public void setStatus(int newStatus) {
            if (status == newStatus) {
                return;
            }
            this.status = newStatus;
        }

        public void setTopView(View topView) {
            mTrueTopView = topView;
        }

        public void setEmptyView(EmptyView emptyView) {
            mEmptyView = emptyView;
        }

        public void setActualEmptyView(ActualEmptyView mActualEmptyVIew) {
            this.mActualEmptyView = mActualEmptyVIew;
        }

        public void checkAndSetTopStateChange() {
            if (mEmptyView == null || !(mEmptyView.getLayoutParams() instanceof RecyclerView.LayoutParams)) {
                setVisibility(View.GONE);
            } else {

                boolean isSetTop = false;
                int firstPosition = mLayoutManager.findFirstVisibleItemPosition();
                if (firstPosition >= this.getPosition()) {
                    isSetTop = true;
                } else if (mTopViewTopMargin > 0) {
                    Rect rect = new Rect();
                    mEmptyView.getHitRect(rect);
                    isSetTop = rect.top <= mTopViewTopMargin;
                }

                if (firstPosition == this.getPosition() && this.getPosition() == 0) {
                    isSetTop = mTopMargin <= mTopViewTopMargin; //第一个item置顶�?要特殊处�?
                }
                if (!isSetTop && status == STATUS_TOP) {
                    setStatus(STATUS_FOLLOW);
                    setLayoutParams(getLayoutParams());

                    if (mTrueTopView.getParent() != mEmptyView) {
                        removeView(mTrueTopView);
                        mEmptyView.addView(mTrueTopView);
                        mEmptyView.removeView(mActualEmptyView);
                        addView(mActualEmptyView);
                    }
                    setVisibility(View.GONE);

                } else if (isSetTop && status == STATUS_FOLLOW) {
                    setStatus(STATUS_TOP);
                    getMarginParams().topMargin = mTopViewTopMargin;
                    mTopMargin = 0;
                    setLayoutParams(getLayoutParams());

                    if (mTrueTopView.getParent() == mEmptyView) {
                        mEmptyView.removeView(mTrueTopView);
                        removeView(mActualEmptyView);
                        mEmptyView.addView(mActualEmptyView);
                        addView(mTrueTopView);
                    }
                    setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void setLayoutParams(ViewGroup.LayoutParams params) {
            if (params instanceof MarginLayoutParams) {
                ((MarginLayoutParams) params).topMargin += mTopMargin;
                if (mOnTopViewLayoutChangeListeners.size() > 0) {
                    for (int i = 0; i < mOnTopViewLayoutChangeListeners.size(); i++) {
                        mOnTopViewLayoutChangeListeners.get(i).onLayoutLocaitonChangeListener(this.getPosition(), status, ((MarginLayoutParams) params));
                    }
                }
            }
            super.setLayoutParams(params);
        }

        public MarginLayoutParams getMarginParams() {
            if (getLayoutParams() != null) {
                return (MarginLayoutParams) getLayoutParams();
            } else {
                return null;
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            if (mTopViewHeight == mTrueTopView.getMeasuredHeight()) {
                return;
            }
            mTopViewHeight = mTrueTopView.getMeasuredHeight();
            if (mTopViewHeight != mActualEmptyView.getMeasuredHeight()) {
                ViewGroup.LayoutParams lParams = null;
                if (mActualEmptyView.getLayoutParams() == null) {
                    lParams = new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            mTopViewHeight);
                } else {
                    lParams = mActualEmptyView.getLayoutParams();
                    lParams.height = mTopViewHeight;
                }
                mActualEmptyView.setLayoutParams(lParams);
                int emptyHeight = MeasureSpec.makeMeasureSpec(lParams.height, MeasureSpec.EXACTLY);
                mActualEmptyView.measure(mActualEmptyView.getMeasuredWidthAndState(), emptyHeight);
            }
        }
    }

    public interface OnTopViewLayoutChangeListener {
        void onLayoutLocaitonChangeListener(int preTopViewPosition, int preTopViewStatus, MarginLayoutParams lp);
    }

    public interface OnTopViewStatusChangeListener {
        void onTopViewStatusChangeListener(int position, int oldStatus, int newStatus);
    }

    public interface OnScrollChangedListener {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }


    public static class SetTopParams {
        /**
         * 距离顶部多远距离�?始置�?
         */
        public int marginTopHeight = 0;
    }
}
