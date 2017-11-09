package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;


public class setDataActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setdataactivity);

        RelativeLayout fen_Rel = (RelativeLayout)findViewById(R.id.fen_Rel);
        Button sure_Btn = (Button)findViewById(R.id.sure_Btn);
        StaticData.ViewScale(fen_Rel,610,330);
        StaticData.ViewScale(sure_Btn,0,120);

//        Intent intent = getIntent();
//        int count = intent.getIntExtra("jifen", 0);
//
//        fen_Txt.setText("+"+count);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finish();
//
//            }
//
//        }, 800);
        sure_Btn.setOnClickListener(new sure_Btn());
    }

    private class sure_Btn implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

}
