package com.moying.energyring.myAcativity.Energy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;

/**
 * Created by john on 2016/8/15.
 */
public class SysNotice_Web extends Activity {
    private WebView myWebView;
    private TextView cententtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layoutRoot = new LinearLayout(this);//根布局
        layoutRoot.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        layoutRoot.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        layoutRoot.setLayoutParams(params);
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");

        myWebView = new WebView(this);
        myWebView.setLayoutParams(params);
        initWebViewSettings();//webview设置
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
    }

    private void initTitle(LinearLayout mycontentView) {
        final View view = LayoutInflater.from(SysNotice_Web.this).inflate(R.layout.base_titlebar, null);
        view.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(2f);//阴影
        }
        RelativeLayout.LayoutParams paramrel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        StaticData.layoutParamsScale(paramrel, 0, 88);
        view.setLayoutParams(paramrel);
        Button return_Btn = (Button) view.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        cententtxt = (TextView) view.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("");
        StaticData.ViewScale(return_Btn, 80, 88);
        mycontentView.addView(view);
        return_Btn.setOnClickListener(new return_Btn());
    }


    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        myWebView.destroy();
        myWebView.setVisibility(View.GONE);
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


}
