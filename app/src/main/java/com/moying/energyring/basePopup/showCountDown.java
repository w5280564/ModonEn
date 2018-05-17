package com.moying.energyring.basePopup;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.CountDownView;

/**
 * Created by waylen on 2018/3/22.
 */

public class showCountDown extends BasePopupWindow {
    public showCountDown(Context mContext, View view, int time,int changeTime,String projectName, int currtGroup,int GroupCount) {
        super(mContext);
        View contentView = View.inflate(mContext, R.layout.training_count_down, null);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setTouchable(true);
//        setFocusable(false);
        setContentView(contentView);
        showAtLocation(contentView, Gravity.CENTER, 0, 0);
        View my_Rel = contentView.findViewById(R.id.my_Rel);
        TextView bang_Txt = (TextView) contentView.findViewById(R.id.bang_Txt);
        View intr_Lin = contentView.findViewById(R.id.intr_Lin);
        CountDownView countdown_View = (CountDownView)contentView.findViewById(R.id.countdown_View);
        View xiu_Txt = contentView.findViewById(R.id.xiu_Txt);
        TextView after_Txt = (TextView) contentView.findViewById(R.id.after_Txt);
        TextView other_Txt = (TextView) contentView.findViewById(R.id.other_Txt);
        layoutmarginTop(mContext,countdown_View,0,340,0,120);
        layoutmarginBot(mContext,xiu_Txt,R.id.countdown_View);
        bang_Txt.setText("太棒了，已经完成第"+ currtGroup +"组");
        TextsColor(0,3,"接下来\t\t"+projectName +"\t第"+ (currtGroup+1) +"组",after_Txt);
        TextsColor(0,3,"\t剩余\t\t"+ (GroupCount - currtGroup) +"组",other_Txt);

        StaticData.ViewScale(countdown_View,333,333);
        StaticData.ViewScale(intr_Lin,590,270);

        countdown_View.setCountdownTime(time);//倒计时时间
        countdown_View.startCountDown();//倒计时开始
        float width = Float.parseFloat(saveFile.getShareData("scale", mContext)) * 8;
        countdown_View.setmRingWidth(width);
//        countdown_View.setAddCountDownListener(new CountDownView.OnCountDownFinishListener() {
//            @Override
//            public void countDownFinished() {
//                dismiss();
//            }
//        });
//        my_Rel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });

        bang_Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                countdown_View.changeCount(30);//修改倒计时时间
            }
        });
    }
    private void layoutmarginTop(Context context, View view,int left,int top,int right ,int bot) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        int padleft = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * left);
        int padtop = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * top);
        int padright = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * right);
        int padbot = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * bot);
        Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Params.setMargins(padleft, padtop, padright, padbot);
        view.setLayoutParams(Params);
    }
    private void layoutmarginBot(Context context, View view,int id) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int padbot = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 120);
        Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Params.addRule(RelativeLayout.BELOW,id);
        Params.setMargins(0, 0, 0, padbot);
        view.setLayoutParams(Params);
    }

    public void TextsColor(int start ,int end , String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#95a0ab")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//修改颜色
        myTxt.setText(styledText);
    }


}
