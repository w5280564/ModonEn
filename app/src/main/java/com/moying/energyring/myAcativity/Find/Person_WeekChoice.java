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

public class Person_WeekChoice extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__date_choice);

        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("设置时间");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        LinearLayout stopTime_Lin = (LinearLayout) findViewById(R.id.stopTime_Lin);


        return_Btn.setOnClickListener(new return_Btn());


        if (saveFile.getUserList(this, "choiceWeek").isEmpty()) {
            for (int i = 0; i < timeArr.length; i++) {
                myChoice.add("false");
            }
//            saveFile.saveUserList(thi
//     s,myChoice,"choiceWeek");
        } else {
            myChoice = saveFile.getUserList(this, "choiceWeek");
        }

        timeInit(stopTime_Lin);

    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }


    private String[] timeArr = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    List<ImageView> myImg = new ArrayList<>();
    List<String> myChoice = new ArrayList<>();

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
            StaticData.ViewScale(gou_Img, 44, 30);
            time_Txt.setText(timeArr[i]);
            if (myChoice.get(i).equals("true")) {
                gou_Img.setImageResource(R.drawable.gou_img);
            } else {
                gou_Img.setImageResource(0);
            }
            myview.setTag(i);
            myImg.add(gou_Img);
            myLin.addView(myview);
            myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();
                    if (myChoice.get(tag).equals("true")) {
                        myImg.get(tag).setImageResource(0);
                        myChoice.set(tag, "false");
                    } else {
                        myImg.get(tag).setImageResource(R.drawable.gou_img);
                        myChoice.set(tag, "true");
                    }
                    saveFile.saveUserList(Person_WeekChoice.this, myChoice, "choiceWeek");
                }
            });


        }
    }


}
