package com.moying.energyring.myAcativity.Energy;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.moying.energyring.Model.ShareContent;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class myShareActivity extends Activity implements PlatformActionListener, Handler.Callback {

    private ShareContent shareContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.MyDialog);
        setContentView(R.layout.activity_my_share);
        setPosition();

        initView();
    }

    /**
     * 设置对话框窗口位置和大小等
     */
    private void setPosition() {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = dm.widthPixels;
//        layoutParams.alpha=0.95f;//设置透明度,0.0为完全透明，1.0为完全不透明
        getWindow().setAttributes(layoutParams);
    }

    private void initView() {
        shareContent = getIntent().getParcelableExtra("shareContent");
        RelativeLayout share_Rel = (RelativeLayout) findViewById(R.id.share_Rel);
        StaticData.ViewScale(share_Rel, 675, 460);
        LinearLayout share_friend_Lin = (LinearLayout) findViewById(R.id.share_friend_Lin);
        LinearLayout share_wechat_Lin = (LinearLayout) findViewById(R.id.share_wechat_Lin);
        LinearLayout share_sina_Lin = (LinearLayout) findViewById(R.id.share_sina_Lin);
        LinearLayout share_qzone_Lin = (LinearLayout) findViewById(R.id.share_qzone_Lin);
        ImageView share_friend_Img = (ImageView) findViewById(R.id.share_friend_Img);
        ImageView share_wechat_Img = (ImageView) findViewById(R.id.share_wechat_Img);
        ImageView share_sina_Img = (ImageView) findViewById(R.id.share_sina_Img);
        ImageView share_qzone_Img = (ImageView) findViewById(R.id.share_qzone_Img);
        LinearLayout cha_Lin = (LinearLayout) findViewById(R.id.cha_Lin);


        StaticData.ViewScale(share_friend_Img, 72, 72);
        StaticData.ViewScale(share_wechat_Img, 72, 72);
        StaticData.ViewScale(share_sina_Img, 72, 72);
        StaticData.ViewScale(share_qzone_Img, 72, 72);
        cha_Lin.setOnClickListener(new cha_Lin());

        share_friend_Lin.setOnClickListener(new share_friend());
        share_wechat_Lin.setOnClickListener(new share_mom());
        share_sina_Lin.setOnClickListener(new share_sina());
        share_qzone_Lin.setOnClickListener(new share_qzone());
    }

    private class cha_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    }

    public class share_friend implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
            shareWechatQuan();
        }
    }

    public class share_mom implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
            shareWechatFriend();
        }
    }

    public class share_sina implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
            shareSina();
        }
    }


    public class share_qzone implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
            share_qzone();
        }
    }

//    private String shareTitle = "";
//    private String shareContentStr = "";
//    private String shareUrl = "";

    //分享给微信朋友
    public void shareWechatFriend() {
        ShareSDK.initSDK(this);
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle(shareContent.title);
        wechat.setText(shareContent.content);

        wechat.setUrl(shareContent.url);
        if (shareContent.imgpath  == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ring);
//        msg.thumbData = Util.bmpToByteArray(bitmap, true);
//        InputStream is = getResources().openRawResource(R.drawable.ring_icon);
//        Bitmap mBitmap = BitmapFactory.decodeStream(is);
            wechat.setImageData(bitmap);
        } else {
            wechat.setImageUrl(shareContent.imgpath);//网络图用Url
        }
//        wechat.setImageData(wechatbit);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(this, Wechat.NAME);
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);
    }


    //分享-微信朋友圈
    public void shareWechatQuan() {
        ShareSDK.initSDK(this);
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle(shareContent.title);
        wechat.setText(shareContent.content);

        wechat.setUrl(shareContent.url);
        if (shareContent.imgpath  == null) {
//            InputStream is = getResources().openRawResource(R.drawable.ring_icon);
//        Bitmap mBitmap = BitmapFactory.decodeStream(is);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ring);
            wechat.setImageData(bitmap);
        } else {
            wechat.setImageUrl(shareContent.imgpath);//网络图用Url

        }
//        wechat.setImageData(wechatfriendbit);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(this, WechatMoments.NAME);
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);
    }

    //分享-新浪微博
    public void shareSina() {
        ShareSDK.initSDK(this);
        SinaWeibo.ShareParams sina = new SinaWeibo.ShareParams();
        sina.setText(shareContent.title + shareContent.url);
//        sina.setImagePath(sianimg);
        if (shareContent.imgpath != null ) {
            sina.setImagePath(shareContent.imgpath);
        }
        Platform sinap = ShareSDK.getPlatform(SinaWeibo.NAME);
        sinap.setPlatformActionListener(this);
//        sinap.SSOSetting(true);//审核未通过
        sinap.share(sina);
    }

    public void share_qq() {
        ShareSDK.initSDK(this);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(shareContent.title);
        sp.setTitleUrl(shareContent.url); // 标题的超链接
        sp.setText(shareContent.content);
        if (shareContent.imgpath != null) {
            sp.setImageUrl(shareContent.imgpath);
        }
//        sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
//        sp.setSite("发布分享的网站名称");
//        sp.setSiteUrl("发布分享网站的地址");
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(this); // 设置分享事件回调
        qq.share(sp);
    }

    public void share_qzone() {
        ShareSDK.initSDK(this);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(shareContent.title);
        sp.setTitleUrl(shareContent.url);
        sp.setText(shareContent.content);
        sp.setSite("能量圈");//分享应用的名称
        sp.setSiteUrl("http://m.pp.cn/detail.html?appid=6863306&ch_src=pp_dev&ch=default");//分享应用的网页地址
        if (shareContent.imgpath != null) {
            sp.setImageUrl(shareContent.imgpath);
        }
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(this); // 设置分享事件回调
        qzone.share(sp);
    }


    /*
    *
    *分享事件回调
    *
    *
    * */
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                // 成功
                Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
            }
            break;
            case 2: {
                // 失败
//					Toast.makeText(HomePage_NewsMan_XiangQing.this,"分享失败", 10000).show();
                Toast.makeText(this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
            break;
            case 3: {
                // 取消
//                Toast.makeText(this, "分享取消", Toast.LENGTH_SHORT).show();
            }
            break;
        }

        return false;
    }

    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;

    @Override
    public void onCancel(Platform arg0, int arg1) {
        // 取消
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = arg1;
        msg.obj = arg0;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
        // 成功
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = arg1;
        msg.obj = arg2;
        UIHandler.sendMessage(msg, this);

    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {
        // 失敗
        //打印错误信息,print the error msg
        arg2.printStackTrace();
        //错误监听,handle the error msg
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = arg1;
        msg.obj = arg2;
        UIHandler.sendMessage(msg, this);
    }


}
