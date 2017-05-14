package com.moying.energyring.basePopup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.waylenBaseView.BasePopupWindow;

/**
 * Created by waylen on 2017/4/25.
 */

public class popupOne extends BasePopupWindow {
    public popupOne(Context context,View view,String contentString) {
        super(context);
        View contentView = View.inflate(context, R.layout.popup_one, null);
        setTouchable(true);
        setContentView(contentView);
        showAtLocation(view, Gravity.CENTER,0,0);
        RelativeLayout my_Rel = (RelativeLayout) contentView.findViewById(R.id.my_Rel);
        StaticData.ViewScale(my_Rel, 470, 356);
        ImageView popup_Img = (ImageView) contentView.findViewById(R.id.popup_Img);
        StaticData.ViewScale(popup_Img, 470, 220);
        TextView content_Txt = (TextView) contentView.findViewById(R.id.content_Txt);
        StaticData.ViewScale(content_Txt, 406, 256);
        content_Txt.setText(contentString);
    }
}
