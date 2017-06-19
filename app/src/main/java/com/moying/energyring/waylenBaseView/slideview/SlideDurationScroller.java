package com.moying.energyring.waylenBaseView.slideview;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class SlideDurationScroller extends Scroller {

    private double slideFactor = 1;

    public SlideDurationScroller(Context context) {
        super(context);
    }

    public SlideDurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public void setSlideDurationFactor(double slideFactor) {
        this.slideFactor = slideFactor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int)(duration * slideFactor));
    }
}