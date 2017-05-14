package com.moying.energyring.basePopup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.waylenBaseView.BasePopupWindow;

/**
 * Created by waylen on 2017/4/25.
 */

public class popupFour extends BasePopupWindow {
    public popupFour(Context context, View view, String titleStr, String contentStr) {
        super(context);
        View contentView = View.inflate(context, R.layout.popup_four, null);
        setTouchable(true);
        setContentView(contentView);
        showAtLocation(view, Gravity.CENTER,0,0);
        RelativeLayout my_Rel = (RelativeLayout) contentView.findViewById(R.id.my_Rel);
        StaticData.ViewScale(my_Rel, 470, 581);
        ImageView popup_Img = (ImageView) contentView.findViewById(R.id.popup_Img);
        StaticData.ViewScale(popup_Img, 470, 330);
        TextView content_Txt = (TextView) contentView.findViewById(R.id.content_Txt);
        StaticData.ViewScale(content_Txt, 406, 256);

        LinearLayout cha_Lin = (LinearLayout) contentView.findViewById(R.id.cha_Lin);
        StaticData.ViewScale(cha_Lin, 100, 100);
        ImageView cha_img = (ImageView) contentView.findViewById(R.id.cha_img);
        StaticData.ViewScale(cha_img, 30, 30);
        Button sure_btn = (Button) contentView.findViewById(R.id.sure_btn);
        StaticData.ViewScale(sure_btn, 280, 100);

//        content_Txt.setText(contentStr);
        cha_Lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
