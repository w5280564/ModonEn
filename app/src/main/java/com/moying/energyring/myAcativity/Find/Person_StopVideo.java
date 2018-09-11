package com.moying.energyring.myAcativity.Find;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import java.util.List;

public class Person_StopVideo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__stop_video);

        View title_Include = findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("定时停止电台");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        LinearLayout stopTime_Lin = (LinearLayout) findViewById(R.id.stopTime_Lin);

        timeInit(stopTime_Lin);
        return_Btn.setOnClickListener(new return_Btn());
    }

    public class return_Btn implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            finish();
        }
    }
    private String[] timeArr = {"关闭", "10分钟","20分钟","30分钟","40分钟","50分钟","60分钟"};
    List<ImageView> myImg = new ArrayList<>();
    public void timeInit(LinearLayout myLin) {
        if (myLin != null) {
            myLin.removeAllViews();
        }
        int size = timeArr.length;
        for (int i = 0; i < size; i++) {
            LinearLayout myview =   (LinearLayout)LayoutInflater.from(this).inflate(R.layout.timeview, null);
            RelativeLayout time_Rel = (RelativeLayout)myview.findViewById(R.id.time_Rel);
            StaticData.ViewScale(time_Rel,0,100);
           final TextView time_Txt = (TextView)myview.findViewById(R.id.time_Txt);
            final ImageView gou_Img = (ImageView)myview.findViewById(R.id.gou_Img);
            StaticData.ViewScale(gou_Img,44,30);
            if (saveFile.getShareData("stopString",Person_StopVideo.this).equals(timeArr[i])) {
                gou_Img.setImageResource(R.drawable.gou_img);
            }
            time_Txt.setText(timeArr[i]);
            myview.setTag(i);
            myImg.add(gou_Img);
            myLin.addView(myview);
            myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();
                    for (int j=0; j< timeArr.length;j++){
                        myImg.get(j).setImageResource(0);
                    }
                    myImg.get(tag).setImageResource(R.drawable.gou_img);
                    String stopCount;
                    if (tag == 0){
                        stopCount = timeArr[tag];
                    }else{
                        stopCount =  Long.parseLong(timeArr[tag].substring(0,2)) *60 *1000 +"";
                    }
                    saveFile.saveShareData("stopCount",stopCount,Person_StopVideo.this);
                    saveFile.saveShareData("stopString",timeArr[tag],Person_StopVideo.this);
                }
            });
        }
    }


}
