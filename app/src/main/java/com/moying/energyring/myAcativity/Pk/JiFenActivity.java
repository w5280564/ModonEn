package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;


public class JiFenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jifen_popup);

        LinearLayout fen_Lin = (LinearLayout)findViewById(R.id.fen_Lin);
        StaticData.ViewScale(fen_Lin,336,260);
        TextView fen_Txt = (TextView)findViewById(R.id.fen_Txt);

        Intent intent = getIntent();
        int count = intent.getIntExtra("jifen", 0);

        fen_Txt.setText("+"+count);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();

            }

        }, 800);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

}
