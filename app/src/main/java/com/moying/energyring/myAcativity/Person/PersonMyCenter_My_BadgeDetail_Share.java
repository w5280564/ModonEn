package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanjay.selectorphotolibrary.utils.PermissionUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mob.tools.utils.UIHandler;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class PersonMyCenter_My_BadgeDetail_Share extends Activity implements PlatformActionListener, Handler.Callback {

    private TextView userName_Txt, badge_Txt,userContent_Txt;
    private String ShareText, Badge_Path,Caption;
    private SimpleDraweeView badge_Sim;
    private ImageView fixed;
    int shareIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.personmycenter_my_badge_detail_share);
        setView();
        Intent intent = getIntent();
        ShareText = intent.getStringExtra("ShareText");
        Badge_Path = intent.getStringExtra("Badge_Path");
        Caption = intent.getStringExtra("Caption");


        initView();
        initData();
    }

    public void setView() {
//        setTheme(R.style.MyDialog);
        setContentView(getIntent().getIntExtra("view", R.layout.personmycenter_my_badge_detail_share));
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(layoutParams);
    }


    private void initView() {
        View badge_chaImg = (ImageView) findViewById(R.id.badge_chaImg);
        View badge_Rel = findViewById(R.id.badge_Rel);

        RelativeLayout.LayoutParams headParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int magTop = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 100);
        int magBot = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 50);
        headParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        headParams.setMargins(0, magTop, 0, magBot);
        StaticData.layoutParamsScale(headParams, 610, 960);
        badge_Rel.setLayoutParams(headParams);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        badge_Sim = (SimpleDraweeView) findViewById(R.id.badge_Sim);
        userContent_Txt = (TextView) findViewById(R.id.userContent_Txt);
        userName_Txt = (TextView) findViewById(R.id.userName_Txt);
        badge_Txt = (TextView) findViewById(R.id.badge_Txt);
        View badge_share_Img = findViewById(R.id.badge_share_Img);
        LinearLayout share_Lin = (LinearLayout) findViewById(R.id.share_Lin);
        LinearLayout share_wechat_Lin = (LinearLayout) findViewById(R.id.share_wechat_Lin);
        View badge_share_wechat = findViewById(R.id.badge_share_wechat);
        LinearLayout share_sina_Lin = (LinearLayout) findViewById(R.id.share_sina_Lin);
        View badge_share_sina = findViewById(R.id.badge_share_sina);
        LinearLayout share_qq_Lin = (LinearLayout) findViewById(R.id.share_qq_Lin);
        View badge_share_qq = findViewById(R.id.badge_share_qq);
        LinearLayout share_friend_Lin = (LinearLayout) findViewById(R.id.share_friend_Lin);
        View badge_share_friend = findViewById(R.id.badge_share_friend);
        fixed = (ImageView) findViewById(R.id.fixed);


        StaticData.ViewScale(badge_chaImg, 35, 35);
//        StaticData.ViewScale(badge_Rel, 610, 960);
        StaticData.ViewScale(badge_Sim, 390, 390);
        StaticData.ViewScale(badge_share_Img, 362, 202);
        StaticData.ViewScale(share_Lin, 610, 140);
        StaticData.ViewScale(badge_share_wechat, 70, 70);
        StaticData.ViewScale(badge_share_sina, 70, 70);
        StaticData.ViewScale(badge_share_qq, 70, 70);
        StaticData.ViewScale(badge_share_friend, 70, 70);

        badge_chaImg.setOnClickListener(new badge_chaImg());
        share_wechat_Lin.setOnClickListener(new share_wechat_Lin());
        share_sina_Lin.setOnClickListener(new share_sina_Lin());
        share_qq_Lin.setOnClickListener(new share_qq_Lin());
        share_friend_Lin.setOnClickListener(new share_friend_Lin());
    }

    private void initData() {

        String NickName = saveFile.getShareData("NickName", this);
        userName_Txt.setText(NickName);
        badge_Txt.setText(ShareText);
        userContent_Txt.setText(Caption

        );

        if (Badge_Path != null) {
            Uri imgUri = Uri.parse(Badge_Path);
            badge_Sim.setImageURI(imgUri);
        }
    }


    private class badge_chaImg implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }


    private class share_wechat_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            shareIndex = 1;
            setPermission(shareIndex);
        }
    }

    private class share_sina_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            shareIndex = 2;
            setPermission(shareIndex);
        }
    }

    private class share_qq_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            shareIndex = 3;
            setPermission(shareIndex);
        }
    }

    private class share_friend_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            shareIndex = 4;
            setPermission(shareIndex);
        }
    }


    private void setPermission(int shareIndex) {
         /*申请读取存储的权限*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, permissionGrant);
        } else {
            setFixed();
            if (shareIndex == 1) {
                shareWechatFriend();
            } else if (shareIndex == 2) {
                shareSina();
            } else if (shareIndex == 3) {
                share_qq();
            } else if (shareIndex == 4) {
                shareWechatQuan();
            }
        }
    }

    private PermissionUtils.PermissionGrant permissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
//                Toast.makeText(ImagePickerActivity.this, "读取存储权限已打开", Toast.LENGTH_SHORT).show();
//                    startSelectionDoc();
//                    choosePhoto();
                    setFixed();
                    if (shareIndex == 1) {
                        shareWechatFriend();
                    } else if (shareIndex == 2) {
                        shareSina();
                    } else if (shareIndex == 3) {
                        share_qq();
                    } else if (shareIndex == 4) {
                        shareWechatQuan();
                    }
                    break;
            }
        }
    };

    /*申请权限的回调*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, permissionGrant);
    }


    Bitmap sharebit;
    String sharestr;

    private void setFixed() {
        if (sharebit == null) {
            int viewX = (int) (Float.parseFloat(saveFile.getShareData("scale", PersonMyCenter_My_BadgeDetail_Share.this)) * 84);
            int viewY = (int) (Float.parseFloat(saveFile.getShareData("scale", PersonMyCenter_My_BadgeDetail_Share.this)) * 114);
            int widht = (int) (Float.parseFloat(saveFile.getShareData("scale", PersonMyCenter_My_BadgeDetail_Share.this)) * 596);
            int heght = (int) (Float.parseFloat(saveFile.getShareData("scale", PersonMyCenter_My_BadgeDetail_Share.this)) * 946);
            sharebit = StaticData.myfixed(PersonMyCenter_My_BadgeDetail_Share.this, viewX, viewY, widht, heght);
            sharestr = StaticData.createDir(System.currentTimeMillis() + ".jpg", sharebit);
//            fixed.setImageBitmap(sharebit);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //分享给微信朋友
    public void shareWechatFriend() {
        ShareSDK.initSDK(this);
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle("");
        wechat.setText("");
        wechat.setImageData(sharebit);
        wechat.setShareType(Platform.SHARE_IMAGE);
        Platform weixin = ShareSDK.getPlatform(this, Wechat.NAME);
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);
    }


    //分享-微信朋友圈
    public void shareWechatQuan() {
        ShareSDK.initSDK(this);
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle("");
        wechat.setText("");
        wechat.setImageData(sharebit);
        wechat.setShareType(Platform.SHARE_IMAGE);
        Platform weixin = ShareSDK.getPlatform(this, WechatMoments.NAME);
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);
    }

    //分享-新浪微博
    public void shareSina() {
        ShareSDK.initSDK(this);
        SinaWeibo.ShareParams sina = new SinaWeibo.ShareParams();
//        sina.setText("");
        sina.setImagePath(sharestr);
        Platform sinap = ShareSDK.getPlatform(SinaWeibo.NAME);
        sinap.setPlatformActionListener(this);
//        sinap.SSOSetting(true);//审核未通过
        sinap.share(sina);
    }

    public void share_qq() {
        ShareSDK.initSDK(this);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(null);
        sp.setTitleUrl(null); // 标题的超链接
        sp.setText(null);

        sp.setImagePath(sharestr);
        sp.setShareType(Platform.SHARE_IMAGE);
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
        sp.setTitle("");
        sp.setTitleUrl("");
        sp.setText("");
        sp.setSite("能量圈");//分享应用的名称
        sp.setSiteUrl("http://m.pp.cn/detail.html?appid=6863306&ch_src=pp_dev&ch=default");//分享应用的网页地址
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
