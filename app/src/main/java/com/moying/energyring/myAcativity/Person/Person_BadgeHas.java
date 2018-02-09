package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.JiFenAndBadge_Model;
import com.moying.energyring.Model.ShareContent;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;


public class Person_BadgeHas extends Activity {

    private TextView have_Txt, badge_Txt;
    int index = 0;
    private SimpleDraweeView badge_Img;
    private JiFenAndBadge_Model jiFenmodel;
    private LinearLayout my_Lin;
    private Button share_Btn;
    private ShareContent shareContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // 设置contentFeature,可使用切换动画
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
////         Transition explode = TransitionInflater.from(this).inflateTransition(R.anim.zoomin);
//        Transition explode = TransitionInflater.from(this).inflateTransition(Android.R.transition.explode);
//        getWindow().setEnterTransition(explode);
        setContentView(R.layout.has_badge);
        setFinishOnTouchOutside(false);//dialog 模式点击外部不消失

        my_Lin = (LinearLayout) findViewById(R.id.my_Lin);
        RelativeLayout hasbadge_Rel = (RelativeLayout) findViewById(R.id.hasbadge_Rel);
        badge_Img = (SimpleDraweeView) findViewById(R.id.badge_Img);
        ImageView badge_chaImg = (ImageView) findViewById(R.id.badge_chaImg);
        have_Txt = (TextView) findViewById(R.id.have_Txt);
        badge_Txt = (TextView) findViewById(R.id.badge_Txt);
        share_Btn = (Button) findViewById(R.id.share_Btn);
        StaticData.ViewScale(hasbadge_Rel, 510, 712);
        StaticData.ViewScale(badge_Img, 360, 420);
        StaticData.ViewScale(badge_chaImg, 35, 35);
        StaticData.ViewScale(share_Btn, 450, 92);
        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.badge_media);//这时就不用调用setDataSource
        mPlayer.start();


        Intent intent = getIntent();
//        String resultStr = "{\n" +
//                "  \"IsSuccess\": true,\n" +
//                "  \"Msg\": \"操作成功！\",\n" +
//                "  \"Status\": 200,\n" +
//                "  \"Data\": {\"Integral\":10,\"_Badge\":[{\"BadgeID\":16,\"BadgeName\":\"累计签到50天徽章\",\"BadgeDays\":50,\"BadgeType\":2,\"Is_Have\":false,\"HaveNum\":280,\"FileID\":0,\"FilePath\":\"http://172.16.0.111/Uploads/2017-11-27/4bc3c882-5115-4be9-99f0-84ef4a7a51ca.png\",\"FileID_Gray\":0,\"FilePath_Gray\":null},{\"BadgeID\":17,\"BadgeName\":\"累计签到100天徽章\",\"BadgeDays\":100,\"BadgeType\":3,\"Is_Have\":false,\"HaveNum\":380,\"FileID\":0,\"FilePath\":\"http://172.16.0.111/Uploads/2017-11-27/4bc3c882-5115-4be9-99f0-84ef4a7a51ca.png\",\"FileID_Gray\":0,\"FilePath_Gray\":null}]}\n" +
//                "}";

//        String resultStr = "{\n" +
//                "  \"IsSuccess\": true,\n" +
//                "  \"Msg\": \"操作成功！\",\n" +
//                "  \"Status\": 200,\n" +
//                "  \"Data\": {\"Integral\":10,\"_Badge\":[{\"BadgeID\":16,\"BadgeName\":\"累计签到50天徽章\",\"BadgeDays\":50,\"BadgeType\":2,\"Is_Have\":false,\"HaveNum\":280,\"FileID\":0,\"FilePath\":\"http://172.16.0.111/Uploads/2017-11-27/4bc3c882-5115-4be9-99f0-84ef4a7a51ca.png\",\"FileID_Gray\":0,\"FilePath_Gray\":null}]}\n" +
//                "}";

//        jiFenmodel = new Gson().fromJson(resultStr, JiFenAndBadge_Model.class);
        if (intent.getParcelableExtra("jiFenmodel") != null) {
            jiFenmodel = intent.getParcelableExtra("jiFenmodel");
            if (!jiFenmodel.getData().get_Badge().isEmpty()) {
                initView(jiFenmodel, index);
                index++;
            }
        }
//        String url = intent.getStringExtra("url");
//        String HaveNum = intent.getStringExtra("HaveNum");
//        String myHaveName = intent.getStringExtra("myHaveName");
//        myHaveName = "恭喜你！\n获得"+ myHaveName;
//
//        badge_Img.setImageURI(url);
//        have_Txt.setText(HaveNum);
//        badge_Txt.setText(myHaveName);

        badge_chaImg.setOnClickListener(new badge_chaImg());
        share_Btn.setOnClickListener(new share_Btn());
    }

    private class badge_chaImg implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            finish();
//            overridePendingTransition(R.anim.zoomout, R.anim.zoomout);
//            Intent intent = new Intent(Person_BadgeHas.this,Person_BadgeHas.class);
//            intent.putExtra("jiFenmodel", (Serializable) jiFenmodel);
//            startActivity(intent);
            if (index + 1 <= jiFenmodel.getData().get_Badge().size()) {
                onCreate(null); //刷新加载
                addAnim(my_Lin);
            } else {
                Intent intent1 = new Intent();
                setResult(1003, intent1);
                finish();
                overridePendingTransition(0, R.anim.zoomout);
            }
        }
    }

    private class share_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_BadgeHas.this, Person_myShareActivity.class);
            intent.putExtra("shareContent", shareContent);
            startActivity(intent);
//            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    private void addAnim(LinearLayout myView) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);// 使用rotate.xml生成动画效果对象
        animation.setFillAfter(true);// 动画停止时保持在该动画结束时的状态
        myView.startAnimation(animation);
    }

    private void initView(JiFenAndBadge_Model model, int pos) {
        JiFenAndBadge_Model.DataBean.BadgeBean oneData = model.getData().get_Badge().get(pos);
        String myHaveName = "恭喜你！\n获得" + oneData.getBadgeName();
        String HaveNum = "-已有" + oneData.getHaveNum() + "人获得-";
        String url = oneData.getFilePath();
        badge_Img.setImageURI(url);
        have_Txt.setText(HaveNum);
        badge_Txt.setText(myHaveName);

        String shareTitle = saveFile.getShareData("NickName",this)+"的能量圈徽章墙";
        String shareConStr = "更多徽章等你发现~";
        String UserID = saveFile.getShareData("userId",this);
        String shareUrl = saveFile.BaseUrl + "/Share/Badge?UserID=" + UserID;
        String imgUrl = null;
        shareContent = new ShareContent(shareUrl, shareTitle, shareConStr, imgUrl);
    }

}
