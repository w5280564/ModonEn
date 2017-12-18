package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;


public class JiFenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jifen_popup);

        RelativeLayout jifen_Rel = (RelativeLayout)findViewById(R.id.jifen_Rel);
        StaticData.ViewScale(jifen_Rel,550,270);
        TextView jifen_Txt = (TextView)findViewById(R.id.jifen_Txt);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int mag = (int) (Float.parseFloat(saveFile.getShareData("scale", JiFenActivity.this)) * 40);
        params.setMargins(0, mag, mag, 0);
//        params.addRule(RelativeLayout.BELOW, R.id.edit_Rel);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,R.id.jifen_Rel);
        jifen_Txt.setLayoutParams(params);

        SimpleDraweeView fen_Sim = (SimpleDraweeView)findViewById(R.id.fen_Sim);
        Uri uri = Uri.parse("res:// /" + R.drawable.jifen_anm);
        addGif(uri, 750, 450,fen_Sim);

        Intent intent = getIntent();
        int count = intent.getIntExtra("jifen", 0);

        jifen_Txt.setText("+"+count);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                mPlayer.stop();
//                mPlayer.release();
                Intent intent1 = new Intent();
                setResult(1002, intent1);
                finish();
//                Intent intent1 = new Intent(JiFenActivity.this, Person_BadgeHas.class);
//                startActivity(intent1);
            }

        }, 1400);

        //1400 毫秒
    }


    public  void addGif(Uri uri, int width, int height,SimpleDraweeView mySimView) {
        StaticData.ViewScale(mySimView,width,height);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        mySimView.setController(controller);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

}
