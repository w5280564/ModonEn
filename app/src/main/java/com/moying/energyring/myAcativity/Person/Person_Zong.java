package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mob.tools.utils.UIHandler;
import com.moying.energyring.Model.Base_DataString_Model;
import com.moying.energyring.Model.ShareContent;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Energy.TaskShareActivity;
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

public class Person_Zong extends Activity implements PlatformActionListener, Handler.Callback {
    private WebView myWebView;
    private TextView cententtxt;
    private ShareContent shareContent;
    String shareTitle = "";
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layoutRoot = new LinearLayout(this);//根布局
        layoutRoot.setBackgroundColor(ContextCompat.getColor(this,R.color.colorAllBg));
        layoutRoot.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        layoutRoot.setLayoutParams(params);

        String todayString = getStringToday();
        String userID = saveFile.getShareData("userId", this);
        url = saveFile.BaseUrl + "/Share/PersonReport?UserID=" + userID + "&date=" + todayString;
        final Intent intent = getIntent();
//        if (content != null) {
//            shareTitle = HtmlToText.delHTMLTag(content);
//        }
        myWebView = new WebView(this);
        myWebView.setLayoutParams(params);

        initWebViewSettings();//webview设置
        //增加接口方法,让html页面调用
        myWebView.addJavascriptInterface(this, "android");
        myWebView.addJavascriptInterface(this, "PersonReport");

        changeCookies(this, url);//同步cookike

        // 调用loadUrl()
        myWebView.loadUrl(url);
        myWebView.setWebViewClient(new WebViewClient() {
            // 允许所有SSL证书
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return false;
            }
        });

        initTitle(layoutRoot);//标题栏

        layoutRoot.addView(myWebView);
        setContentView(layoutRoot);

        myWebView.setOnLongClickListener(new View.OnLongClickListener() {
            public String imgurl;

            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                imgurl = result.getExtra();//长按返回url地址
//                new save_Popup(Energy_WebDetail.this, cententtxt);
                return false;
            }
        });

        initData();
    }


    private void initTitle(LinearLayout mycontentView) {
        final View view = LayoutInflater.from(Person_Zong.this).inflate(R.layout.base_titlebar, null);
        view.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(2f);//阴影
        }
        RelativeLayout.LayoutParams paramrel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        StaticData.layoutParamsScale(paramrel, 0, 88);
        view.setLayoutParams(paramrel);
        Button return_Btn = (Button) view.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        cententtxt = (TextView) view.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText(myWebView.getTitle());
//        cententtxt.setText("能量帖");
        StaticData.ViewScale(return_Btn, 80, 88);
        Button right_Btn = (Button) view.findViewById(R.id.right_Btn);
        right_Btn.setBackgroundResource(R.drawable.share_icon);
        StaticData.ViewScale(right_Btn, 40, 40);
        right_Btn.setVisibility(View.INVISIBLE);
        mycontentView.addView(view);
        return_Btn.setOnClickListener(new return_Btn());
//        right_Btn.setOnClickListener(new right_Btn());
    }


    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(learn_WebDetail.this, ShareActivity.class);

            Intent intent = new Intent(Person_Zong.this, TaskShareActivity.class);
            intent.putExtra("shareContent", shareContent);
            startActivity(intent);
        }
    }

    public  String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
    //JS调用Android JAVA方法名和HTML中的按钮 onclick后的别名后面的名字对应
    @JavascriptInterface
    public void UserID_Get(int UserId) {
        Intent intent = new Intent(Person_Zong.this, PersonMyCenter_And_Other.class);
        intent.putExtra("UserID", UserId + "");
        startActivity(intent);
    }

    //分享
    //PersonReport JAVA方法名和HTML中的按钮 onclick后的别名后面的名字对应
    @JavascriptInterface
    public void PersonReport(String shareName) {
        if (shareName.equals("timeline")) {
            shareWechatQuan();
        } else if (shareName.equals("weixin")) {
            shareWechatFriend();
        } else if (shareName.equals("weibo")) {
            shareSina();
        } else if (shareName.equals("QQ")) {
            share_qq();
        } else if (shareName.equals("Qzone")) {
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

                        shareTitle = shareModel.getData();
                        String shareConStr = "";
                        String shareUrl = url;
                        shareContent = new ShareContent(shareUrl, shareTitle, shareConStr, "");


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


    /**
     * init WebView Settings
     */
    private void initWebViewSettings() {
        //webview在安卓5.0之前默认允许其加载混合网络协议内容
        // 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);//图片缩放
        myWebView.getSettings().setJavaScriptEnabled(true);//支持JS
//        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        myWebView.setBackgroundColor(0);
        //b:加上这两句基本上就可以做到屏幕适配了
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setBlockNetworkImage(false);
    }

    /**
     * 同步一下cookie到网页
     */
    public static void changeCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookies = CookieManager.getInstance();
        cookies.setAcceptCookie(true);
        StringBuilder sbCookie = new StringBuilder();
        sbCookie.append(saveFile.getShareData("JSESSIONID", context));
//        sbCookie.append(String.format("user=%s",UrlVO.getShareData("cookieJSESSIONID", context)));
        sbCookie.append(String.format(";domain=%s", saveFile.getShareData("cookieDomain", context)));
        sbCookie.append(String.format(";path=%s", "/"));
        cookies.setCookie(url, sbCookie.toString());
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
