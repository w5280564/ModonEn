package com.moying.energyring.myAcativity.Energy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by john on 2016/8/15.
 */
public class Energy_WebDetail extends Activity {
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
        layoutRoot.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        layoutRoot.setLayoutParams(params);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        url = "https://www.baidu.com/";

        if (!url.isEmpty()) {
            if (!url.substring(0, 4).equals("http")) {
                url = "http://" + url;
            }
        }
//        LinearLayout.LayoutParams webparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        myWebView = new WebView(this);
//        myWebView.setLayoutParams(webparams);
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
        //增加接口方法,让html页面调用
        myWebView.addJavascriptInterface(this,"android");

        // 调用loadUrl()
        myWebView.loadUrl(url);
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //true内部WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        final View view = LayoutInflater.from(Energy_WebDetail.this).inflate(R.layout.base_titlebar, null);
        RelativeLayout.LayoutParams paramrel = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        StaticData.layoutParamsScale(paramrel, 0, 88);
        view.setLayoutParams(paramrel);
        Button return_Btn = (Button) view.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_icon);
         cententtxt = (TextView) view.findViewById(R.id.cententtxt);
        cententtxt.setText("能量帖");
        StaticData.ViewScale(return_Btn, 48, 48);
        Button right_Btn = (Button) view.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.INVISIBLE);
        layoutRoot.addView(view);


        layoutRoot.addView(myWebView);
        setContentView(layoutRoot);

        return_Btn.setOnClickListener(new return_Btn());
//        right_Btn.setOnClickListener(new right_Btn());

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
//            Intent intent = new Intent(Energy_WebDetail.this, myShareActivity.class);
//            intent.putExtra("shareContent", shareContent);
//            startActivity(intent);
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

    private String imgurl = "";
    /**
     * 下载文件
     */
//    private void downLoadFile(String downloadUrl) {
//        File appDir = new File(Environment.getExternalStorageDirectory(), "EnergyM");//放入新建文件夹
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        int idx = imgurl.lastIndexOf(".");
//        String ext = imgurl.substring(idx);
//        final String fileName = System.currentTimeMillis() + ".jpg";//用时间命名，保持唯一性
//        final File file = new File(appDir, fileName);
//        DownFile(downloadUrl, file, new MyCallBack<File>() {
//
//            @Override
//            public void onSuccess(File result) {
//                super.onSuccess(result);
////                Util.showToast(MainActivity.this, "下载成功");
//                // 其次把文件插入到系统图库
//                try {
//                    MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), fileName, null);
//                    Toast.makeText(Energy_WebDetail.this, "保存成功", Toast.LENGTH_SHORT).show();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    Toast.makeText(Energy_WebDetail.this, "保存失败", Toast.LENGTH_SHORT).show();
//                }
//                // 最后通知图库更新
//                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                super.onError(ex, isOnCallback);
////                Util.showToast(MainActivity.this, "下载失败");
//            }
//
//        });
//    }


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
    public void startActivity(int UserId){
//        Intent intentCenter = new Intent(Energy_WebDetail.this, PersonalDetailsActivity.class);
//        intentCenter.putExtra("otherUserId",UserId);
//        startActivity(intentCenter);
    }

}
