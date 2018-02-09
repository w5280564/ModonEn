package com.moying.energyring.myAcativity.Find;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.moying.energyring.Model.ShareContent;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.HtmlToText;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Energy.MyCallBack;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_Other;
import com.moying.energyring.network.saveFile;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by john on 2016/8/15.
 */
public class Find_WebDetail extends Activity {
    private WebView myWebView;
    private int articleId;
    private String title;
    private String summary;
    private String share_url;
    private int userId;
    //    private ShareContent shareContent;
    private TextView cententtxt;
    private ShareContent shareContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.news_web);
        LinearLayout layoutRoot = new LinearLayout(this);//根布局
        layoutRoot.setBackgroundColor(Color.parseColor("#232121"));
        layoutRoot.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        layoutRoot.setLayoutParams(params);
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        final String postId = intent.getStringExtra("postId");
        final String content = intent.getStringExtra("content");
        final String imgpath = intent.getStringExtra("imgpath");

        String shareTitle ="";
        if (content != null) {
             shareTitle = HtmlToText.delHTMLTag(content);
        }
//        String shareTitle = "";
        String shareConStr = "我的能量源是" + saveFile.getShareData("InviteCode", Find_WebDetail.this);
        String shareUrl = saveFile.BaseUrl + "Share/PostDetails?PostID=" + postId;
        shareContent = new ShareContent(shareUrl, shareTitle, shareConStr,"");
        shareContent.setImgpath(imgpath);

        myWebView = new WebView(this);
        myWebView.setLayoutParams(params);

        initWebViewSettings();//webview设置
        //增加接口方法,让html页面调用
        myWebView.addJavascriptInterface(this, "android");
//        myWebView.addJavascriptInterface(this, "downimg");
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
        final View view = LayoutInflater.from(Find_WebDetail.this).inflate(R.layout.base_titlebar, null);
        view.setBackgroundColor(Color.parseColor("#2b2a2a"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(2f);//阴影
        }
        RelativeLayout.LayoutParams paramrel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        StaticData.layoutParamsScale(paramrel, 0, 88);
        view.setLayoutParams(paramrel);
        Button return_Btn = (Button) view.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        cententtxt = (TextView) view.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#ffffff"));
//        cententtxt.setText(myWebView.getTitle());
        cententtxt.setText("能量帖");
        StaticData.ViewScale(return_Btn, 80, 88);
        Button right_Btn = (Button) view.findViewById(R.id.right_Btn);
        right_Btn.setBackgroundResource(R.drawable.share_icon);
        StaticData.ViewScale(right_Btn, 40, 40);
        right_Btn.setVisibility(View.VISIBLE);
        mycontentView.addView(view);
        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
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

            Intent intent = new Intent(Find_WebDetail.this, ShareFindActivity.class);
            intent.putExtra("shareContent", shareContent);
            startActivity(intent);
        }
    }

//    public class save_Popup extends PopupWindow {
//        public save_Popup(final Context mContext, View parent) {
//            super(parent);
//            View view = View.inflate(mContext, R.layout.popup_save, null);
//            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//            setOutsideTouchable(true);
//            setFocusable(true);
//            setContentView(view);
//            showAtLocation(parent, Gravity.CENTER, 0, 0);
//            update();
//            Button topbtn = (Button) view.findViewById(R.id.topbtn);
//            topbtn.setText("保存图片");
//            Button belowbtn = (Button) view.findViewById(R.id.belowbtn);
//            belowbtn.setText("复制图片链接");
//            Button cancelbtn = (Button) view.findViewById(R.id.cancelbtn);
//            StaticData.ViewScale(topbtn, 750, 102);
//            StaticData.ViewScale(belowbtn, 750, 102);
//            StaticData.ViewScale(cancelbtn, 750, 88);
//            topbtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (imgurl != null){
//                        downLoadFile(imgurl);
//                    }
//                    dismiss();
//                }
//            });
//            belowbtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//                    if (imgurl != null){
//                    cmb.setText(imgurl);
//                    }
//                    dismiss();
//                }
//            });
//            cancelbtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dismiss();
//                }
//            });
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dismiss();
//                }
//            });
//
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Find_WebDetail"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
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
        MobclickAgent.onPageEnd("Find_WebDetail"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
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

    /**
     * 下载文件
     */
    private void downLoadFile(String downloadUrl) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "EnergyM");//放入新建文件夹
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        int idx = downloadUrl.lastIndexOf(".");
        String ext = downloadUrl.substring(idx);
        final String fileName = System.currentTimeMillis() + ".jpg";//用时间命名，保持唯一性
        final File file = new File(appDir, fileName);
        DownFile(downloadUrl, file, new MyCallBack<File>() {

            @Override
            public void onSuccess(File result) {
                super.onSuccess(result);
//                Util.showToast(MainActivity.this, "下载成功");
                // 其次把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), fileName, null);
                    Toast.makeText(Find_WebDetail.this, "保存成功", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(Find_WebDetail.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
                // 最后通知图库更新
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
//                Util.showToast(MainActivity.this, "下载失败");
            }

        });
    }

    public static <T> Callback.Cancelable DownFile(String url, File filepath, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath.getAbsolutePath());
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

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
        Intent intent = new Intent(Find_WebDetail.this, PersonMyCenter_Other.class);
        intent.putExtra("UserID", UserId + "");
        startActivity(intent);
    }


    @JavascriptInterface
    public void downLoadimg(String url) {
        if (url != null) {
            downLoadFile(url);
        }
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
