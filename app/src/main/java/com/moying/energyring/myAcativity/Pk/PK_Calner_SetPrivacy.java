package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class PK_Calner_SetPrivacy extends Activity {

    private String ProjectID;
    private int Privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pk_calner_setprivacy);

        Intent intent = getIntent();

        ProjectID = intent.getStringExtra("ProjectID");
        Privacy = intent.getIntExtra("Privacy", 0);
        initTitle();

        LinearLayout Privacy_Lin = (LinearLayout) findViewById(R.id.Privacy_Lin);
        PrivacyInit(Privacy_Lin);
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this, R.color.colorFristWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this, R.color.colorFristBlack));
        cententtxt.setText("隐私设置");
        Button right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setTextColor(ContextCompat.getColor(this, R.color.colorFristBlack));
        right_Btn.setText("保存");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            initData();
        }
    }

    private void initData() {
        Privacy_UpdateData(PK_Calner_SetPrivacy.this, saveFile.BaseUrl + saveFile.Community_Privacy_Url + "?ProjectID=" + ProjectID + "&Privacy=" + Privacy);
    }


    private String[] PrivacyArr = {"所有人可见", "仅我的好友可见", "自己可见"};
    List<ImageView> myImg = new ArrayList<>();

    public void PrivacyInit(LinearLayout myLin) {
        if (myLin != null) {
            myLin.removeAllViews();
        }
        int size = PrivacyArr.length;
        for (int i = 0; i < size; i++) {
            LinearLayout myview = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.privacyview, null);
            RelativeLayout time_Rel = (RelativeLayout) myview.findViewById(R.id.time_Rel);
            StaticData.ViewScale(time_Rel, 0, 100);
            final TextView cpntentn_Txt = (TextView) myview.findViewById(R.id.cpntentn_Txt);
            final ImageView gou_Img = (ImageView) myview.findViewById(R.id.gou_Img);
            StaticData.ViewScale(gou_Img, 36, 36);
            gou_Img.setImageResource(R.drawable.privacy_not_icon);
            cpntentn_Txt.setText(PrivacyArr[i]);
            myview.setTag(i);
            myImg.add(gou_Img);
            myLin.addView(myview);

            myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();
                    for (int j = 0; j < PrivacyArr.length; j++) {
                        myImg.get(j).setImageResource(R.drawable.privacy_not_icon);
                    }
                    myImg.get(tag).setImageResource(R.drawable.privacy_select_icon);
                    Privacy = tag + 1;
//                    String stopCount;
//                    if (tag == 0) {
//                        stopCount = timeArr[tag];
//                    } else {
//                        stopCount = Long.parseLong(timeArr[tag].substring(0, 2)) * 60 * 1000 + "";
//                    }
//                    saveFile.saveShareData("stopCount", stopCount, PK_Calner_SetPrivacy.this);
//                    saveFile.saveShareData("stopString", timeArr[tag], PK_Calner_SetPrivacy.this);
                }
            });
        }
        if (Privacy == 1) {
            myImg.get(0).setImageResource(R.drawable.privacy_select_icon);
        } else if (Privacy == 2) {
            myImg.get(1).setImageResource(R.drawable.privacy_select_icon);
        } else if (Privacy == 3) {
            myImg.get(2).setImageResource(R.drawable.privacy_select_icon);
        }
    }

    public void Privacy_UpdateData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model baseModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(context, "隐私已修改", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, MainLogin.class);
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


}
