package com.moying.energyring.waylenBaseView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by john on 2016/8/10.
 */
public class HackyViewPager extends ViewPager {

    private OnClickListener clickListener;

    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {

            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {

        } catch (ArrayIndexOutOfBoundsException e) {

        }
        return false;
    }





}