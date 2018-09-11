package com.moying.energyring.myAcativity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Login_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Person.Person_PrivacyPolicy;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

public class MainLogin extends Activity implements PlatformActionListener, Handler.Callback {

    private Handler handler;
    private int LoginType;
    MyActivityManager mam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中


        initView();
        initData();
    }

    private void initView() {
        TextView login_Txt = (TextView) findViewById(R.id.login_Txt);
        Button login_Btn = (Button) findViewById(R.id.login_Btn);
        Button share_qq = (Button) findViewById(R.id.share_qq);
        Button share_sina = (Button) findViewById(R.id.share_sina);
        Button share_wechat = (Button) findViewById(R.id.share_wechat);
        View PrivacyPolicy_Txt = findViewById(R.id.PrivacyPolicy_Txt);

        StaticData.ViewScale(login_Txt, 0, 430);
        StaticData.ViewScale(login_Btn, 570, 110);
        StaticData.ViewScale(share_qq, 110, 112);
        StaticData.ViewScale(share_sina, 110, 112);
        StaticData.ViewScale(share_wechat, 110, 112);


        login_Btn.setOnClickListener(new login_Btn());
        share_qq.setOnClickListener(new share_qq());
        share_sina.setOnClickListener(new share_sina());
        share_wechat.setOnClickListener(new share_wechat());
        PrivacyPolicy_Txt.setOnClickListener(new PrivacyPolicy_Txt());
    }

    private class login_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(MainLogin.this, MainLoginNext.class);
            startActivity(intent);
        }
    }

    private void initData() {
        ShareSDK.initSDK(this);
        handler = new Handler(this);
        LoginType = 0;
    }

    //
    public class PrivacyPolicy_Txt implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String url = saveFile.BaseUrl + "Terms/PrivacyPolicy";
            Intent intent = new Intent(MainLogin.this, Person_PrivacyPolicy.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }
    }    //新浪登录

    public class share_sina implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LoginType = 3;
            Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
            authorize(sina);
        }
    }

    //QQ登录
    public class share_qq implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LoginType = 1;
            //QQ登录配置
            Platform qq = ShareSDK.getPlatform(QZone.NAME);
            authorize(qq);
        }
    }

    //微信登录
    public class share_wechat implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LoginType = 2;
            Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
            authorize(wechat);
        }
    }


    //普通用户与第三方登录
    public void LoginMethod(final Context context, final String baseUrl, int loginType) {
        JSONObject obj = new JSONObject();
        try {
            //第三方
            obj.put("LoginType", loginType);
            obj.put("OpenID", myuserId);
            obj.put("NickName", myuserName);
            obj.put("ProfilePicture", myuserimg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams(baseUrl);
        params.setAsJsonContent(true);
        params.setBodyContent(obj.toString());
//        Log.e("第三方数据",obj.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
//                    Log.e("第三方数据",resultString);
                    Login_Model baseModel = new Gson().fromJson(resultString, Login_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
//                        initlist(Leran_Goal.this);
//                        setResult(RESULT_OK);

                        String userId = baseModel.getData().getUserID() + "";
                        saveFile.saveShareData("islogin", "true", context);
                        saveFile.saveShareData("role", baseModel.getData().getRole() + "", context);//管理员
                        saveFile.saveShareData("userId", userId, context);
                        saveFile.saveShareData("InviteCode", baseModel.getData().getInviteCode(), context);
                        saveFile.saveShareData("NickName", baseModel.getData().getNickName(), context);
//                        finish();


                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);


                        Log.e("LoginName", resultString);

                        if (StaticData.isSpace(baseModel.getData().getLoginName())) {
                            Log.e("LoginName", "true");
                            Intent intent1 = new Intent(context, MainChangePhone.class);
                            startActivity(intent1);
                        }

                        saveFile.saveShareData("ispush", "true", context);
                        JPushInterface.resumePush(context);
                        JPushInterface.setAlias(context, "ET_" + userId, null);


                        mam.finishAllActivity();
                    } else {
                        Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
//                    JPushInterface.resumePush(Man_Login.this);//注册
//                    JPushInterface.setAliasAndTags(Man_Login.this,JsonGet.getReturnValue(resultString, "userid"),null);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
                DbCookieStore dbCookie = DbCookieStore.INSTANCE;
                List cookies = dbCookie.getCookies();
                if (cookies.size() != 0) {
                    saveFile.saveShareData("JSESSIONID", cookies.get(cookies.size() - 1).toString(), context);
                    saveFile.saveShareData("cookieDomain", dbCookie.getCookies().get(cookies.size() - 1).getDomain(), context);
                }
            }
        });
    }


    private static final int MSG_SMSSDK_CALLBACK = 1;
    private static final int MSG_AUTH_CANCEL = 2;
    private static final int MSG_AUTH_ERROR = 3;
    private static final int MSG_AUTH_COMPLETE = 4;

    //执行授权,获取用户信息
    //文档：http://wiki.mob.com/Android_%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E8%B5%84%E6%96%99

    private void authorize(Platform plat) {
        plat.setPlatformActionListener(this);//若本地没有授权过就请求用户数据
        //关闭SSO授权
        plat.SSOSetting(false);//参数false  调起客户端
        plat.showUser(null);
    }

    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            msg.what = MSG_AUTH_COMPLETE;
            msg.obj = new Object[]{platform.getName(), res};
            handler.sendMessage(msg);
        }
    }

    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_ERROR);
        }
        t.printStackTrace();
//            Log.e("错误", t.toString());
    }

    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_CANCEL);
        }
    }

    private String myuserId;
    private String myuserimg;
    private String myuserName;

    public boolean handleMessage(Message msg) {
//        Log.e("msgmsgmsgmsgmsgmsg",msg.toString());
        switch (msg.what) {
            case MSG_AUTH_CANCEL: {
                //取消授权
                Toast.makeText(this, "授权操作已取消", Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_ERROR: {
                //授权失败
                Log.e("第三方数据", msg.toString());
                Toast.makeText(this, "授权操作遇到错误", Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_COMPLETE: {
                //授权成功
                Toast.makeText(this, "授权成功，正在跳转登录操作…", Toast.LENGTH_SHORT).show();
                Object[] objs = (Object[]) msg.obj;
                String platform = (String) objs[0];
                HashMap<String, Object> res = (HashMap<String, Object>) objs[1];

                Platform plat = ShareSDK.getPlatform(platform);
                myuserId = plat.getDb().getUserId();
                if (LoginType == 1) {
                    myuserimg = res.get("figureurl_qq_2").toString();//qq头像高清地址
                } else {
                    myuserimg = plat.getDb().getUserIcon();
                }
                myuserName = plat.getDb().getUserName();
                plat.removeAccount(true);//清空缓存用户信息，防止客户端登录其他账号拿到资料是缓存信息。

                if (!TextUtils.isEmpty(myuserId)) {
                    LoginMethod(MainLogin.this, saveFile.BaseUrl + saveFile.LoginUrl, LoginType);//第三方登录
                }
            }
            break;
        }
        return false;
    }

    //一定要停止
    @Override
    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }


}
