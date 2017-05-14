package com.moying.energyring.myAcativity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;

/**
 * Created by Admin on 2016/3/25.
 */
public class Fragment2_Pk extends Fragment {

    private View parentView;
    private FlexboxLayout myflexbox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (parentView == null) {
//            if (saveFile.getShareData("islogin", getActivity()).equals("false")) {
//                ((MainActivity) getActivity()).backFragment(0);
//            }
            parentView = inflater.inflate(R.layout.fragment2_pk, null);
            initView(parentView);
        }
        ViewGroup parent = (ViewGroup) parentView.getParent();
        if (parent != null) {
            parent.removeView(parentView);
        }
        return parentView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void initView(View view){
        View title_Include = view.findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setVisibility(View.GONE);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#000000"));
        cententtxt.setText("PK");
        StaticData.ViewScale(title_Include, 0, 88);
        myflexbox = (FlexboxLayout)view.findViewById(R.id.myflexbox);
        imgBadge(getActivity(),myflexbox,0);
        Button pk_day_btn = (Button)view.findViewById(R.id.pk_day_btn);
        Button pk_comm_btn = (Button)view.findViewById(R.id.pk_comm_btn);
        LinearLayout pk_check_Lin = (LinearLayout)view.findViewById(R.id.pk_check_Lin);
        StaticData.ViewScale(pk_day_btn,692,252);
        StaticData.ViewScale(pk_comm_btn,692,252);
        StaticData.ViewScale(pk_check_Lin,692,252);
    }

    private int[] badgeArr = {R.drawable.pk_report, R.drawable.pk_allday, R.drawable.pk_zan, R.drawable.pk_zanranking, R.drawable.pk_rule};
    private String[] nameArr = {"次","天","个","名","徽章规则"};
    private String[] tagArr = {"总汇报次数","累积天数","获赞个数","获赞排名",""};
    public void imgBadge(Context context, FlexboxLayout myFlex, int count) {
        if (myFlex != null) {
            myFlex.removeAllViews();
        }
        int size = badgeArr.length;
        for (int i = 0; i < size; i++) {
            View myView = LayoutInflater.from(context).inflate(R.layout.pk_flexbox,null);
            LinearLayout pk_Lin = (LinearLayout) myView.findViewById(R.id.pk_Lin);
            ImageView pk_img = (ImageView)myView.findViewById(R.id.pk_img);
            TextView content_Txt = (TextView)myView.findViewById(R.id.content_Txt);
            TextView tag_Txt = (TextView)myView.findViewById(R.id.tag_Txt);
            StaticData.ViewScale(pk_img,48,48);
            pk_img.setImageResource(badgeArr[i]);
            tag_Txt.setText(tagArr[i]);
            if (tagArr[i].isEmpty()){
                content_Txt.setText(nameArr[i]);
                tag_Txt.setVisibility(View.GONE);
            }else{
                int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 30);
                int pad2 = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 14);
                TextsColor(pad,pad2,999+nameArr[i],content_Txt);
            }

            RadioGroup.LayoutParams itemParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            StaticData.layoutParamsScale(itemParams,216,94);
            int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 15);
            itemParams.setMargins(pad, pad, pad, pad);
            pk_Lin.setLayoutParams(itemParams);
            pk_Lin.setTag(i);

            myFlex.addView(myView);
            pk_Lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();
//                    myBadgeData(saveFile.BaseUrl + saveFile.MyBadge,tag);
                }
            });
        }
    }

    public void TextsColor(int size, int endsize,String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
        styledText.setSpan(sizeSpan, 0, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan endSizeSpan = new AbsoluteSizeSpan(endsize);
        styledText.setSpan(endSizeSpan, alltext.length()-1, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        myTxt.setText(styledText);
    }




}
