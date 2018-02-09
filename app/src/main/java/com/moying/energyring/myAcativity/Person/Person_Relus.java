package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.umeng.analytics.MobclickAgent;

public class Person_Relus extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__relus);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();
//        ImageView  gui_Img = (ImageView)findViewById(R.id.gui_Img);
//        StaticData.ViewScale(gui_Img,710,1160);
        WebView  relus_WebView = (WebView)findViewById(R.id.relus_WebView);

        final String url = saveFile.BaseUrl+"Share/IntegralRule";
        initWebViewSettings(relus_WebView);//webview设置
        //增加接口方法,让html页面调用
//        relus_WebView.addJavascriptInterface(this, "android");
        changeCookies(this, url);//同步cookike

        // 调用loadUrl()
        relus_WebView.loadUrl(url);
        relus_WebView.setWebViewClient(new WebViewClient() {
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

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Person_Relus"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Person_Relus"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }


    private void initTitle(){
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#2b2a2a"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#ffffff"));
        cententtxt.setText("积分攻略");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
//        right_Btn.setOnClickListener(new right_Btn());
    }
    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    /**
     * init WebView Settings
     */
    private void initWebViewSettings(WebView myView) {
        //webview在安卓5.0之前默认允许其加载混合网络协议内容
        // 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        myView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);//图片缩放
        myView.getSettings().setJavaScriptEnabled(true);//支持JS
//        myWebView.setWebChromeClient(new WebChromeClient());
        myView.getSettings().setAllowFileAccess(true);
        myView.getSettings().setDefaultTextEncodingName("UTF-8");
        myView.setBackgroundColor(0);
        //b:加上这两句基本上就可以做到屏幕适配了
        myView.getSettings().setUseWideViewPort(true);
        myView.getSettings().setLoadWithOverviewMode(true);
        myView.getSettings().setBlockNetworkImage(false);

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

}
