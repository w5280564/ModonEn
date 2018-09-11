package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by john on 2016/8/15.
 */
public class Person_PrivacyPolicy extends Activity {
    private WebView myWebView;
    private int articleId;
    private String title;
    private String summary;
    private String share_url;
    private int userId;
    //    private ShareContent shareContent;
    private TextView cententtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.news_web);
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
        //增加接口方法,让html页面调用
        changeCookies(this, url);//同步cookike

        // 调用loadUrl()
        myWebView.loadUrl(url);
        myWebView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            // 允许所有SSL证书
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }


            //是在5.0以上的版本上使用的
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return false;
            }
            //这个方法是在5.0以下使用的
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        initTitle(layoutRoot);//标题栏

        layoutRoot.addView(myWebView);
        setContentView(layoutRoot);

        myWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                imgurl = result.getExtra();//长按返回url地址
//                new save_Popup(Energy_WebDetail.this, cententtxt);
                return false;
            }
        });

    }

    private void initTitle(LinearLayout mycontentView) {
        final View view = LayoutInflater.from(Person_PrivacyPolicy.this).inflate(R.layout.base_titlebar, null);
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
//        cententtxt.setText(myWebView.getTitle());
//        cententtxt.setText("能量帖");
        StaticData.ViewScale(return_Btn, 80, 88);
//        Button right_Btn = (Button) view.findViewById(R.id.right_Btn);
//        right_Btn.setBackgroundResource(R.drawable.share_icon);
//        StaticData.ViewScale(right_Btn, 40, 40);
//        right_Btn.setVisibility(View.VISIBLE);
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



    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Energy_WebDetail"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
        try {
            myWebView.getClass().getMethod("onResume").invoke(myWebView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Energy_WebDetail"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
        try {
            Class.forName("android.webkit.WebView").getMethod("onPause", (Class[]) null).invoke(myWebView, (Object[]) null);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

    private String imgurl = "";



    public static void delete_File(String path) {
        File filepath = new File(path);
        filepath.delete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myWebView.destroy();
        myWebView.setVisibility(View.GONE);
    }


    //由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
    //JS调用Android JAVA方法名和HTML中的按钮 onclick后的别名后面的名字对应
    @JavascriptInterface
    public void UserID_Get(int UserId) {
        Intent intent = new Intent(Person_PrivacyPolicy.this, PersonMyCenter_And_Other.class);
        intent.putExtra("UserID", UserId + "");
        startActivity(intent);
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

        //前端调用了localStorage方法，导致打开html失败
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setAppCacheEnabled(true);

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
