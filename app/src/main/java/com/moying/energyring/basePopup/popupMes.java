package com.moying.energyring.basePopup;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.moying.energyring.R;
import com.moying.energyring.waylenBaseView.BasePopupWindow;

/**
 * Created by waylen on 2017/4/25.
 */

public class popupMes extends BasePopupWindow {
    public popupMes(Context context, View view, String titleStr, String contentStr) {
        super(context);
        View contentView = View.inflate(context, R.layout.popup_mes, null);
        setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        setTouchable(true);
        setContentView(contentView);
        showAtLocation(view, Gravity.CENTER, 0, 0);

        RelativeLayout papercontent = (RelativeLayout) contentView.findViewById(R.id.papercontent);
        ViewPager mPager = (ViewPager) contentView.findViewById(R.id.viewpager);
        LinearLayout bannerdot = (LinearLayout) contentView.findViewById(R.id.bannerdot);
    }
}
