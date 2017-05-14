package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.waylenBaseView.FlowLayout;

public class Pk_Gui extends Activity {

    private TextView count_Txt;
    private FlowLayout badge_Flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk__gui);
        View title_Include = findViewById(R.id.title_Include);
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setText("徽章规则");
        StaticData.ViewScale(return_Btn, 22, 38);
        StaticData.ViewScale(title_Include, 0, 88);

        count_Txt = (TextView) findViewById(R.id.count_Txt);
        badge_Flow = (FlowLayout) findViewById(R.id.badge_Flow);

        return_Btn.setOnClickListener(new return_Btn());
//        personData(saveFile.BaseUrl + saveFile.PersonData);
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

//    //基本信息
//    private Person_Model model;
//    public void personData(String baseUrl) {
//        RequestParams params = new RequestParams(baseUrl);
//        params.addQueryStringParameter("userid", LoginSession.getSession().getUserInfo().getUse_id() + "");
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String resultString) {
//                model = new Gson().fromJson(resultString, Person_Model.class);
//                if (model.isIsSuccess()) {
//                    imgBadge(badge_Flow,model.getData().get(0).getReportNum());
//
//                    count_Txt.setText("已获得 "+badge+" 枚");
//                } else {
////                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
//                }
//            }
//
//            @Override
//            public void onError(Throwable throwable, boolean b) {
//            }
//
//            @Override
//            public void onCancelled(CancelledException e) {
//            }
//
//            @Override
//            public void onFinished() {
//            }
//        });
//    }
//
//    int badge = 0;
//    //徽章
//    private int[] badgeArr = {R.drawable.badge_30day, R.drawable.badge_50day, R.drawable.badge_100day, R.drawable.badge_150day, R.drawable.badge_200day, R.drawable.badge_250day, R.drawable.badge_300day};
////    private int[] badgeselectArr = {R.drawable.badge_30day_select, R.drawable.badge_50day_select, R.drawable.badge_100day_select, R.drawable.badge_200day_select, R.drawable.badge_300day_select};
//    List<ImageView> mySimpleArr = new ArrayList<>();
//    public void imgBadge(FlowLayout myFlow, int count) {
//        if (myFlow != null) {
//            myFlow.removeAllViews();
//        }
//        int size = badgeArr.length;
//        for (int i = 0; i < size; i++) {
//            RadioGroup.LayoutParams itemParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
//            int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 30);
//            itemParams.setMargins(pad, 0, pad, pad);
//            StaticData.layoutParamsScale(itemParams, 180, 190);
//            ImageView mySimple = new ImageView(this);
//            mySimple.setBackgroundResource(badgeArr[i]);
//            mySimple.getBackground().setAlpha(66);
////            SimpleDraweeView mySimple = new SimpleDraweeView(this);
////            Uri imgUri = Uri.parse("res:// /" + badgeArr[i]);
////            mySimple.setImageURI(imgUri);
//            mySimple.setLayoutParams(itemParams);
//            mySimple.setTag(i);
//            mySimpleArr.add(mySimple);
//            myFlow.addView(mySimple);
//            mySimple.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int tag = (Integer) v.getTag();
//                    myBadgeData(saveFile.BaseUrl + saveFile.MyBadge,tag);
//                }
//            });
//        }
//        if (count >= 30) {
//            int index = 0;
//            setimg(mySimpleArr.get(index),index);
//        }
//        if (count >= 50) {
//            int index = 1;
//            setimg(mySimpleArr.get(index),index);
//        }
//        if (count >= 100) {
//            int index = 2;
//            setimg(mySimpleArr.get(index),index);
//        }
//        if (count >= 150) {
//            int index = 3;
//            setimg(mySimpleArr.get(index),index);
//        }
//        if (count >= 200) {
//            int index = 4;
//            setimg(mySimpleArr.get(index),index);
//        }
//         if (count >= 250) {
//             int index = 5;
//             setimg(mySimpleArr.get(index),index);
//        }
//        if (count >= 300) {
//            int index = 6;
//            setimg(mySimpleArr.get(index),index);
//        }
//    }
//
//    public void setimg(ImageView myview, int index) {
//        myview.setBackgroundResource(badgeArr[index]);
//        myview.getBackground().setAlpha(255);
//        badge = index+1;
//    }
//
//
//    //基本信息
//    public void myBadgeData(String baseUrl, final int tag) {
//        RequestParams params = new RequestParams(baseUrl);
//        params.addQueryStringParameter("UserID", LoginSession.getSession().getUserInfo().getUse_id() + "");
//        x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String resultString) {
//                Badge_Model model = new Gson().fromJson(resultString, Badge_Model.class);
//                if (model.isIsSuccess()) {
//
//                        new badge_Popup(Pk_Gui.this,count_Txt,model.getData().get(tag).getDay(),model.getData().get(tag).getHasNum());
//                } else {
////                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
//                }
//            }
//
//            @Override
//            public void onError(Throwable throwable, boolean b) {
//            }
//
//            @Override
//            public void onCancelled(CancelledException e) {
//            }
//
//            @Override
//            public void onFinished() {
//            }
//        });
//    }
//
//    public class badge_Popup extends PopupWindow {
//        public badge_Popup(final Context mContext, View parent, int day, int per) {
//            super(parent);
//            final View view = View.inflate(mContext, R.layout.popup_badge, null);
//            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//            setOutsideTouchable(true);
//            setFocusable(true);
//            setContentView(view);
//            showAtLocation(parent, Gravity.CENTER, 0, 0);
//            update();
//            ImageView badge_Img = (ImageView) view.findViewById(R.id.badge_Img);
//            TextView day_Txt = (TextView) view.findViewById(R.id.day_Txt);
//            TextView per_Txt = (TextView) view.findViewById(R.id.per_Txt);
//            StaticData.ViewScale(badge_Img,214,252);
//            if (day == 30){
//                badge_Img.setBackgroundResource(R.drawable.popup_badge30);
//                day_Txt.setText("连续训练30天");
//            }else if (day == 50){
//                badge_Img.setBackgroundResource(R.drawable.popup_badge50);
//                day_Txt.setText("连续训练50天");
//            }else if (day == 100){
//                badge_Img.setBackgroundResource(R.drawable.popup_badge100);
//                day_Txt.setText("连续训练100天");
//            }else if (day == 150){
//                badge_Img.setBackgroundResource(R.drawable.popup_badge150);
//                day_Txt.setText("连续训练150天");
//            }else if (day == 200){
//                badge_Img.setBackgroundResource(R.drawable.popup_badge200);
//                day_Txt.setText("连续训练200天");
//            }else if (day == 250){
//                badge_Img.setBackgroundResource(R.drawable.popup_badge250);
//                day_Txt.setText("连续训练250天");
//            }else if (day == 300){
//                badge_Img.setBackgroundResource(R.drawable.popup_badge300);
//                day_Txt.setText("连续训练300天");
//            }
//            per_Txt.setText("已有"+per+"人获得");
//
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dismiss();
//                }
//            });
//
//        }
//    }



}
