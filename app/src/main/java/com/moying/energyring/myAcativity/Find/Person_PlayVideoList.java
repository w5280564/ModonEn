package com.moying.energyring.myAcativity.Find;

import android.app.Activity;
import android.content.Context;
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
import com.moying.energyring.Model.RadioList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Person_PlayVideoList extends Activity {

    private LinearLayout stopTime_Lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__play_video_list);

        View title_Include = findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("选择电台");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

         stopTime_Lin = (LinearLayout) findViewById(R.id.stopTime_Lin);


        initData();

        return_Btn.setOnClickListener(new return_Btn());
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private void initData() {
        RadioData(Person_PlayVideoList.this,saveFile.BaseUrl + saveFile.RadioList_Url + "?PageIndex=" + 1 + "&PageSize=" + 20);
    }

    LinkedHashMap<Integer, Boolean> myPlayMap = new LinkedHashMap<>();
//    private String[] timeArr = {"BBC", "CNN", "FOX News", "NPR", "Radio Australia", "LBC", "VOA", "JPR"};
    //    private int[] badgeselectArr = {R.drawable.badge_30day_select, R.drawable.badge_50day_select, R.drawable.badge_100day_select, R.drawable.badge_200day_select, R.drawable.badge_300day_select};
    List<ImageView> myImg = new ArrayList<>();

    public void timeInit(LinearLayout myLin) {
        if (myLin != null) {
            myLin.removeAllViews();
        }
        int size = radioModel.getData().size();
        for (int i = 0; i < size; i++) {
            LinearLayout myview = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.timeview, null);
            RelativeLayout time_Rel = (RelativeLayout) myview.findViewById(R.id.time_Rel);
            StaticData.ViewScale(time_Rel, 0, 80
            );
            final TextView time_Txt = (TextView) myview.findViewById(R.id.time_Txt);
            final ImageView gou_Img = (ImageView) myview.findViewById(R.id.gou_Img);
            myPlayMap.put(i, false);
            RadioList_Model.DataBean oneData = radioModel.getData().get(i);
            if (saveFile.getShareData("englishVideo", Person_PlayVideoList.this).equals(oneData.getRadioName())) {
                gou_Img.setImageResource(R.drawable.gou_img);
                myPlayMap.put(i, true);
            }
            StaticData.ViewScale(gou_Img, 44, 30);
            time_Txt.setText(oneData.getRadioName());
            myview.setTag(i);
            myImg.add(gou_Img);
            myLin.addView(myview);
            myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();
                    for (int j = 0; j <  radioModel.getData().size(); j++) {
                        myImg.get(j).setImageResource(0);
                        myPlayMap.put(j, false);
                    }
                    myImg.get(tag).setImageResource(R.drawable.gou_img);
                    myPlayMap.put(tag, true);//保存这图map，传递到电台
                    saveFile.saveShareData("englishVideo", radioModel.getData().get(tag).getRadioName(), Person_PlayVideoList.this);
                    saveFile.putHashMap(myPlayMap, "videoPlay", Person_PlayVideoList.this);//存电台播放的tag
                }
            });
        }
    }

    RadioList_Model radioModel;

    //电台
    public void RadioData(final Context context , String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    radioModel = new Gson().fromJson(resultString, RadioList_Model.class);
                    if (radioModel.isIsSuccess() && !radioModel.getData().equals("[]")) {

                        timeInit(stopTime_Lin);
                    } else {
                        Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
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
