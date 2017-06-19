package com.moying.energyring.myAcativity.Find;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Person_PlayVideoList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__play_video_list);

        View title_Include = findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setText("选择电台");
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);

        LinearLayout stopTime_Lin = (LinearLayout) findViewById(R.id.stopTime_Lin);

        timeInit(stopTime_Lin);

        return_Btn.setOnClickListener(new return_Btn());
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    LinkedHashMap<Integer, Boolean> myPlayMap = new LinkedHashMap<>();
    private String[] timeArr = {"BBC", "CNN", "FOX News", "NPR", "Radio Australia", "Ted", "LBC", "VOA", "JPR"};
    //    private int[] badgeselectArr = {R.drawable.badge_30day_select, R.drawable.badge_50day_select, R.drawable.badge_100day_select, R.drawable.badge_200day_select, R.drawable.badge_300day_select};
    List<ImageView> myImg = new ArrayList<>();

    public void timeInit(LinearLayout myLin) {
        if (myLin != null) {
            myLin.removeAllViews();
        }
        int size = timeArr.length;
        for (int i = 0; i < size; i++) {
            LinearLayout myview = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.timeview, null);
            RelativeLayout time_Rel = (RelativeLayout) myview.findViewById(R.id.time_Rel);
            StaticData.ViewScale(time_Rel, 0, 80
            );
            final TextView time_Txt = (TextView) myview.findViewById(R.id.time_Txt);
            final ImageView gou_Img = (ImageView) myview.findViewById(R.id.gou_Img);
            myPlayMap.put(i, false);
            if (saveFile.getShareData("englishVideo", Person_PlayVideoList.this).equals(timeArr[i])) {
                gou_Img.setImageResource(R.drawable.gou_img);
                myPlayMap.put(i, true);
            }
            StaticData.ViewScale(gou_Img, 44, 30);
            time_Txt.setText(timeArr[i]);
            myview.setTag(i);
            myImg.add(gou_Img);
            myLin.addView(myview);
            myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();
                    for (int j = 0; j < timeArr.length; j++) {
                        myImg.get(j).setImageResource(0);
                        myPlayMap.put(j, false);
                    }
                    myImg.get(tag).setImageResource(R.drawable.gou_img);
                    myPlayMap.put(tag, true);//保存这图map，传递到电台
                    saveFile.saveShareData("englishVideo", timeArr[tag], Person_PlayVideoList.this);
                    saveFile.putHashMap(myPlayMap, "videoPlay", Person_PlayVideoList.this);//存电台播放的tag
                }
            });
        }
    }


}
