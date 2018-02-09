package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;


public class JiFenActivity extends Activity {
    MediaPlayer mPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jifen_popup);

//        RelativeLayout jifen_Rel = (RelativeLayout) findViewById(R.id.jifen_Rel);
//        StaticData.ViewScale(jifen_Rel, 550, 270);
//        TextView jifen_Txt = (TextView) findViewById(R.id.jifen_Txt);
        ImageView fen_guang_Img = (ImageView) findViewById(R.id.fen_guang_Img);
        StaticData.ViewScale(fen_guang_Img, 680, 680);

        ImageView fen_has_Img = (ImageView) findViewById(R.id.fen_has_Img);
        StaticData.ViewScale(fen_has_Img, 680, 680);

        //动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        fen_guang_Img.startAnimation(animation);

        TextView other_Txt = (TextView) findViewById(R.id.other_Txt);
        RelativeLayout.LayoutParams otherparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int othermargTop = (int) (Float.parseFloat(saveFile.getShareData("scale", JiFenActivity.this)) * 490);
        int othermargLeft = (int) (Float.parseFloat(saveFile.getShareData("scale", JiFenActivity.this)) * 178);
        otherparams.setMargins(othermargLeft, othermargTop, 0, 0);
        other_Txt.setLayoutParams(otherparams);

        Animation otherAnimation = AnimationUtils.loadAnimation(this, R.anim.other_animation);
        other_Txt.startAnimation(otherAnimation);


        TextView jifen_Txt = (TextView) findViewById(R.id.jifen_Txt);
        RelativeLayout.LayoutParams jifenparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        jifenparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        StaticData.layoutParamsScale(succparams, 680, 680);
        int jifenmargTop = (int) (Float.parseFloat(saveFile.getShareData("scale", JiFenActivity.this)) * 360);
        int jifenmargLeft = (int) (Float.parseFloat(saveFile.getShareData("scale", JiFenActivity.this)) * 295);
        jifenparams.setMargins(jifenmargLeft, jifenmargTop, 0, 0);
        jifen_Txt.setLayoutParams(jifenparams);

        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.jifen_animation);
        jifen_Txt.startAnimation(scaleAnimation);



//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        int mag = (int) (Float.parseFloat(saveFile.getShareData("scale", JiFenActivity.this)) * 40);
//        params.setMargins(0, mag, mag, 0);
////        params.addRule(RelativeLayout.BELOW, R.id.edit_Rel);
//        params.addRule(RelativeLayout.CENTER_VERTICAL);
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, R.id.jifen_Rel);
//        jifen_Txt.setLayoutParams(params);

        Intent intent = getIntent();
        int count = intent.getIntExtra("jifen", 0);
        String mediaStr = intent.getStringExtra("media");
        String RewardIntegral = intent.getStringExtra("RewardIntegral");

        if (mediaStr != null) {
            if (mediaStr.equals("daypk")) {
                mPlayer = MediaPlayer.create(this, R.raw.daypk_media);//这时就不用调用setDataSource
                mPlayer.start();
            } else if (mediaStr.equals("posting")) {
                mPlayer = MediaPlayer.create(this, R.raw.posting_media);//这时就不用调用setDataSource
                mPlayer.start();
            } else if (mediaStr.equals("huizong")) {
                mPlayer = MediaPlayer.create(this, R.raw.huizong_media);//这时就不用调用setDataSource
                mPlayer.start();
            } else if (mediaStr.equals("check")) {
                mPlayer = MediaPlayer.create(this, R.raw.check_media);//这时就不用调用setDataSource
                mPlayer.start();
            }
        }

        jifen_Txt.setText(count + "积分");

        if (RewardIntegral != null && !RewardIntegral.equals("0")) {
            fen_has_Img.setImageResource(R.drawable.fen_hasother_icon);
            other_Txt.setVisibility(View.VISIBLE);
            String allTxt = "恭喜你，额外获得了" + RewardIntegral + "积分";
            TextsColor(JiFenActivity.this,RewardIntegral,allTxt,other_Txt);
//            other_Txt.setText(start);
        }else {
            other_Txt.setVisibility(View.INVISIBLE);
            fen_has_Img.setImageResource(R.drawable.fen_no_icon);

        }


//        SimpleDraweeView fen_Sim = (SimpleDraweeView) findViewById(R.id.fen_Sim);
//        Uri uri = Uri.parse("res:// /" + R.drawable.jifen_anm);
//        addGif(uri, 750, 450, fen_Sim);


//        jifen_Txt.setText("+" + count);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mPlayer != null) {
                    mPlayer.stop();
                    mPlayer.release();
                }
                Intent intent1 = new Intent();
                setResult(1002, intent1);
                finish();
//                Intent intent1 = new Intent(JiFenActivity.this, Person_BadgeHas.class);
//                startActivity(intent1);
            }

        }, 3000);

    }

    public void TextsColor(Context context , String RewardIntegral, String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
//        int padSize = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * size);
//        styledText.setSpan(new AbsoluteSizeSpan(padSize), 0, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //初始字体大小
//        int padEndSize = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * endsize);
//        styledText.setSpan(new AbsoluteSizeSpan(padEndSize), 1, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //修改字体大小
        int start = alltext.length() - (RewardIntegral + "积分").length();
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#f24d4d")), start, alltext.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//修改颜色
        myTxt.setText(styledText);
    }


    public void addGif(Uri uri, int width, int height, SimpleDraweeView mySimView) {
        StaticData.ViewScale(mySimView, width, height);
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
