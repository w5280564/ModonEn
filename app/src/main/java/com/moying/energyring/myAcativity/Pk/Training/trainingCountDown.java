package com.moying.energyring.myAcativity.Pk.Training;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.CountDownView;

public class trainingCountDown extends Activity {

    private CountDownView countdown_View;
    private RelativeLayout my_Rel;
    private TextView bang_Txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_count_down);

        my_Rel = (RelativeLayout)findViewById(R.id.my_Rel);
        bang_Txt = (TextView)findViewById(R.id.bang_Txt);
        countdown_View = (CountDownView)findViewById(R.id.countdown_View);
        StaticData.ViewScale(countdown_View,333,333);
        countdown_View.setCountdownTime(10);
        countdown_View.startCountDown();
        float width = Float.parseFloat(saveFile.getShareData("scale", this)) * 8;
        countdown_View.setmRingWidth(width);
        countdown_View.setAddCountDownListener(new CountDownView.OnCountDownFinishListener() {
            @Override
            public void countDownFinished() {
                finish();
            }
        });

        my_Rel.setOnClickListener(new my_Rel());
        bang_Txt.setOnClickListener(new bang_Txt());
    }

    private class my_Rel implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            finish();

        }
    }
    private class bang_Txt implements View.OnClickListener{
        @Override
        public void onClick(View view) {
           countdown_View.changeCount(30);
        }
    }
}
