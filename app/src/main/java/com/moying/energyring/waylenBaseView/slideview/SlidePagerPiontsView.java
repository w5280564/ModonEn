package com.moying.energyring.waylenBaseView.slideview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SlidePagerPiontsView extends LinearLayout {

	private int normalPoint, selectPoint;

	public SlidePagerPiontsView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SlidePagerPiontsView(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param count 
	 * @param normalPoint
	 * @param selectPoint
	 */
	public void setPoints(int count, int normalPoint, int selectPoint) {
		this.normalPoint = normalPoint;
		this.selectPoint = selectPoint;
		setSelectPoint(count, 1);
	}

	public void setSelectPoint(int count, int positon) {
		if (this.getChildCount() != 0) {
			this.removeAllViews();
		}
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				normalPoint);
		for (int i = 1; i <= count; i++) {
			ImageView pointIv = new ImageView(getContext());
			LayoutParams lp = new LayoutParams(
					bitmap.getWidth(), bitmap.getHeight());
			lp.leftMargin = 3;
			lp.rightMargin = 3;
			pointIv.setLayoutParams(lp);
			if (i != positon) {
				pointIv.setBackgroundResource(normalPoint);
			} else {
				pointIv.setBackgroundResource(selectPoint);
			}
			addView(pointIv);
		}
	}
}
