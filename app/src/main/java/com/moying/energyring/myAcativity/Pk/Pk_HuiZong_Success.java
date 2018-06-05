package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mob.tools.utils.UIHandler;
import com.moying.energyring.Model.Base_DataString_Model;
import com.moying.energyring.Model.ShareContent;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class Pk_HuiZong_Success extends Activity implements PlatformActionListener, Handler.Callback {
    private ShareContent shareContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeprice);
        setContentView(R.layout.pk__hui_zong__success);

        View success_Img = findViewById(R.id.success_Img);
        RelativeLayout.LayoutParams succparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        succparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        StaticData.layoutParamsScale(succparams, 190, 288);
        int succmargTop = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_HuiZong_Success.this)) * 200);
        succparams.setMargins(0, succmargTop, 0, 0);
        success_Img.setLayoutParams(succparams);

        View cha_Img = findViewById(R.id.cha_Img);
        RelativeLayout.LayoutParams chaparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        chaparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        chaparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        StaticData.layoutParamsScale(chaparams, 64, 64);
        int chamargBot = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_HuiZong_Success.this)) * 90);
        chaparams.setMargins(0, 0, 0, chamargBot);
        cha_Img.setLayoutParams(chaparams);

        View share_Lin = findViewById(R.id.share_Lin);
        RelativeLayout.LayoutParams shareparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        shareparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        shareparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        StaticData.layoutParamsScale(chaparams, 64, 64);
        int sharemargBot = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_HuiZong_Success.this)) * 280);
        shareparams.setMargins(0, 0, 0, sharemargBot);
        share_Lin.setLayoutParams(shareparams);

        LinearLayout share_friend_Lin = (LinearLayout) findViewById(R.id.share_friend_Lin);
        LinearLayout share_wechat_Lin = (LinearLayout) findViewById(R.id.share_wechat_Lin);
        LinearLayout share_sina_Lin = (LinearLayout) findViewById(R.id.share_sina_Lin);
        LinearLayout share_qq_Lin = (LinearLayout) findViewById(R.id.share_qq_Lin);
        LinearLayout share_qzone_Lin = (LinearLayout) findViewById(R.id.share_qzone_Lin);
        ImageView share_friend_Img = (ImageView) findViewById(R.id.share_friend_Img);
        ImageView share_wechat_Img = (ImageView) findViewById(R.id.share_wechat_Img);
        ImageView share_sina_Img = (ImageView) findViewById(R.id.share_sina_Img);
        ImageView share_qq_Img = (ImageView) findViewById(R.id.share_qq_Img);
        ImageView share_qzone_Img = (ImageView) findViewById(R.id.share_qzone_Img);

        StaticData.ViewScale(share_friend_Img, 72, 72);
        StaticData.ViewScale(share_wechat_Img, 72, 72);
        StaticData.ViewScale(share_sina_Img, 72, 72);
        StaticData.ViewScale(share_qzone_Img, 72, 72);
        StaticData.ViewScale(share_qq_Img, 72, 72);
        cha_Img.setOnClickListener(new cha_Img());
        share_friend_Lin.setOnClickListener(new share_friend());
        share_wechat_Lin.setOnClickListener(new share_mom());
        share_sina_Lin.setOnClickListener(new share_sina());
        share_qq_Lin.setOnClickListener(new share_qq());
        share_qzone_Lin.setOnClickListener(new share_qzone());

//        shareContent = getIntent().getParcelableExtra("shareContent");

        initData();
    }


    public class cha_Img implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class share_friend implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            shareWechatQuan();
        }
    }

    public class share_mom implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            shareWechatFriend();
        }
    }

    public class share_sina implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            shareSina();
        }
    }


    public class share_qq implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            share_qq();
        }
    }

    public class share_qzone implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            share_qzone();
        }
    }

    private void initData() {
        shareData(this, saveFile.BaseUrl + saveFile.Share_Project_Url);
    }

    public void shareData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_DataString_Model shareModel = new Gson().fromJson(resultString, Base_DataString_Model.class);
                    if (shareModel.isIsSuccess()) {

                        String shareTitle = shareModel.getData();
                        String shareConStr = "";
                        String todayString = getStringToday();
                        String userID = saveFile.getShareData("userId", context);
                        String shareUrl = saveFile.BaseUrl + "/Share/PersonReport?UserID=" + userID + "&date=" + todayString;

                        shareContent = new ShareContent(shareUrl, shareTitle, shareConStr, null);


                    } else {
                        Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, MainLogin.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    //分享给微信朋友
    public void shareWechatFriend() {
        ShareSDK.initSDK(this);
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle(shareContent.title);
        wechat.setText(shareContent.content);

        wechat.setUrl(shareContent.url);
        if (shareContent.imgpath == null) {
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
        if (shareContent.imgpath == null) {
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
        if (shareContent.imgpath != null) {
            sina.setImageUrl(shareContent.imgpath);
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
