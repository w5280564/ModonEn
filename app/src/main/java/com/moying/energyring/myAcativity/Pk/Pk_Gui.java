package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Gui_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.FlowLayout;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class Pk_Gui extends Activity {

    private TextView count_Txt;
    private FlowLayout badge_Flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk__gui);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        View title_Include = findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#000000"));
        cententtxt.setText("徽章规则");
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);

        count_Txt = (TextView) findViewById(R.id.count_Txt);
        badge_Flow = (FlowLayout) findViewById(R.id.badge_Flow);

        return_Btn.setOnClickListener(new return_Btn());
        personData(Pk_Gui.this, saveFile.BaseUrl + saveFile.BadgeGuiZeUrl);
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    //基本信息
    private Gui_Model model;

    public void personData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                model = new Gson().fromJson(resultString, Gui_Model.class);
                if (model.isIsSuccess()) {
                    imgBadge(badge_Flow);
                    count_Txt.setText("已获得 " + badge + " 枚");
                } else {
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    int badge = 0;
    //徽章
//    private int[] badgeArr = {R.drawable.badge_30day, R.drawable.badge_50day, R.drawable.badge_100day, R.drawable.badge_150day, R.drawable.badge_200day, R.drawable.badge_250day, R.drawable.badge_300day};
    List<SimpleDraweeView> mySimpleArr = new ArrayList<>();
    public void imgBadge(FlowLayout myFlow) {
        if (myFlow != null) {
            myFlow.removeAllViews();
        }
        int size = model.getData().size();
        for (int i = 0; i < size; i++) {
            SimpleDraweeView mySimple = new SimpleDraweeView(this);
            RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 30);
            itemParams.setMargins(pad, 0, pad, pad);
            StaticData.layoutParamsScale(itemParams, 180, 190);
            mySimple.setLayoutParams(itemParams);
            if (model.getData().get(i).isIs_Have()) {
//                mySimple.getBackground().setAlpha(255);
                badge = i + 1;
                setimg(mySimple, i);
                mySimple.setAlpha(1f);
            } else {
                setimg(mySimple, i);
                mySimple.setAlpha(0.6f);
            }
            mySimple.setTag(i);
            mySimpleArr.add(mySimple);
            myFlow.addView(mySimple);
            mySimple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();
//                    myBadgeData(saveFile.BaseUrl + saveFile.MyBadge,tag);
                    if (model.getData().get(tag).getFilePath() != null) {
                        new badge_Popup(Pk_Gui.this, count_Txt, model.getData().get(tag).getBadgeDays(), 0, model.getData().get(tag).getFilePath().toString());
                    }
                }
            });
        }
    }

    public void setimg(SimpleDraweeView myview, int index) {
        if (model.getData().get(index).getFilePath() != null) {
            Uri imgUri = Uri.parse(model.getData().get(index).getFilePath().toString());
//            Uri imgUri = Uri.parse("http://192.168.1.111/Uploads/2017-05-16/8648da7f-428f-4ca9-ba21-3df2f6496412.jpg");
            myview.setImageURI(imgUri);
        }
    }


    public class badge_Popup extends BasePopupWindow {
        public badge_Popup(final Context mContext, View parent, int day, int per, String url) {
            super(mContext);
            View contentView = View.inflate(mContext, R.layout.popup_badge, null);
            setTouchable(true);
            setContentView(contentView);
            showAtLocation(contentView, Gravity.CENTER, 0, 0);

            SimpleDraweeView badge_Img = (SimpleDraweeView) contentView.findViewById(R.id.badge_Img);
            TextView day_Txt = (TextView) contentView.findViewById(R.id.day_Txt);
            TextView per_Txt = (TextView) contentView.findViewById(R.id.per_Txt);
            StaticData.ViewScale(badge_Img, 214, 252);
            badge_Img.setImageURI(url);
            per_Txt.setText("已有" + per + "人获得");
        }
    }




}
