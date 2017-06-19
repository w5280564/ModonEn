package com.moying.energyring.myAcativity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.Login_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;


@ContentView(R.layout.activity_login_register)
public class LoginRegister extends Activity implements PlatformActionListener,Handler.Callback{
    @ViewInject(R.id.phonerel)
    RelativeLayout phonerel;
    @ViewInject(R.id.coderel)
    RelativeLayout coderel;
    @ViewInject(R.id.sure_btn)
    Button sure_btn;
    @ViewInject(R.id.login_phone)
    ImageView login_phone;
    @ViewInject(R.id.login_code)
    ImageView login_code;
    @ViewInject(R.id.share_wechat)
    Button share_wechat;
    @ViewInject(R.id.share_sina)
    Button share_sina;
    @ViewInject(R.id.share_qq)
    Button share_qq;
    @ViewInject(R.id.code_Btn)
    Button code_Btn;
    @ViewInject(R.id.phone_edit)
    EditText phone_edit;
    @ViewInject(R.id.codeedit)
    EditText codeedit;

    @ViewInject(R.id.title_Include)
    View title_Include;
    @ViewInject(R.id.return_Btn)
    Button return_Btn;
    @ViewInject(R.id.cententtxt)
    TextView cententtxt;

    MyActivityManager mam;
    private Handler handler;
    private int LoginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中
        x.view().inject(this);

        return_Btn.setBackgroundResource(R.drawable.login_return);
        cententtxt.setText("登录");
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);

        StaticData.ViewScale(phonerel, 520, 100);
        StaticData.ViewScale(coderel, 520, 100);
        StaticData.ViewScale(login_phone, 38, 54);
        StaticData.ViewScale(login_code, 38, 54);
        StaticData.ViewScale(sure_btn, 510, 100);
        StaticData.ViewScale(share_wechat, 84, 84);
        StaticData.ViewScale(share_sina, 84, 84);
        StaticData.ViewScale(share_qq, 84, 84);

        sure_btn.setOnClickListener(new sure_btn());
        share_wechat.setOnClickListener(new share_wechat());
        share_sina.setOnClickListener(new share_sina());
        share_qq.setOnClickListener(new share_qq());
        initData();
    }

    private void initData (){
        ShareSDK.initSDK(this);
        handler = new Handler(this);
        LoginType = 0;
    }

    @Event(type = View.OnClickListener.class, value = R.id.sure_btn)
    private void sureOnclick(View v) {
        Intent intent = new Intent(LoginRegister.this, MainActivity.class);
        startActivity(intent);
    }

    @Event(type = View.OnClickListener.class, value = R.id.return_Btn)
    private void returnOnclick(View v) {
        onBackPressed();
    }

    //新浪登录
    public class share_sina implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            UrlVO.saveShareData("stupthree","1",LoginRegister.this);
            LoginType = 3;
            Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
            authorize(sina);
        }
    }

    //QQ登录
    public class share_qq implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            UrlVO.saveShareData("stupthree","2",LoginRegister.this);
            LoginType = 1;
            //QQ登录配置
//            <QQ
//                    Id="7"
//            SortId="7"
//            AppId="1104987324"
//            AppKey="Icfd6jAfqflPmMuL"
//            ShareByAppClient="true"
//            Enable="true" />


//            HashMap<String,Object> map = new HashMap<String,Object>();
//            map.put("Id","7");
//            map.put("SortId", "7");
//            map.put("AppId","1104987324");
//            map.put("AppKey","Icfd6jAfqflPmMuL");
//            map.put("ShareByAppClient","true");
//            map.put("Enable","true");
////            map.put("RedirectUrl", "http://www.sharesdk.cn");
//            ShareSDK.setPlatformDevInfo(QQ.NAME,map);
            Platform qq = ShareSDK.getPlatform(QZone.NAME);
            authorize(qq);
        }
    }

    //微信登录
    public class share_wechat implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            Log.e("weixinweixinweixin","微信点击微信点击");
//            UrlVO.saveShareData("stupthree","3",LoginRegister.this);
            LoginType = 2;
            Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
            authorize(wechat);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        setResult(RESULT_CANCELED);
        finish();
    }

    @Event(type = View.OnClickListener.class, value = R.id.code_Btn)
    private void codeOnclick(View v) {
        if (!StaticData.isPhone(phone_edit.getText().toString())) {
            Toast.makeText(LoginRegister.this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        codeMethod(saveFile.BaseUrl + saveFile.CodeUrl + "?PhoneNo=" + phone_edit.getText() + "&Type=2");
        time = new TimeCount(60000, 1000);
        time.start();
    }

    public class sure_btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            if (!StaticData.isPhone(phone_edit.getText().toString())) {
                Toast.makeText(LoginRegister.this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
                return;
            } else if (StaticData.Stringspace(codeedit.getText().toString())) {
                Toast.makeText(LoginRegister.this, "验证码错误", Toast.LENGTH_SHORT).show();
                return;
            }
            LoginType = 0;
            LoginMethod(saveFile.BaseUrl + saveFile.LoginUrl,LoginType);
        }
    }

    private TimeCount time;

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            code_Btn.setClickable(false);
            code_Btn.setText("等待" + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            code_Btn.setText("重新获取");
            code_Btn.setClickable(true);
        }
    }

    //发送验证码
    public void codeMethod(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model baseModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(LoginRegister.this, baseModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginRegister.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }


    //普通用户与第三方登录
    public void LoginMethod(String baseUrl,int loginType) {
        JSONObject obj = new JSONObject();
        try {
            if (loginType == 0){
                obj.put("LoginType", loginType);
                obj.put("LoginName", phone_edit.getText());
                obj.put("Code", codeedit.getText());
            }else{
                //第三方
                obj.put("LoginType", loginType);
                obj.put("OpenID", myuserId);
                obj.put("NickName", myuserName);
                obj.put("ProfilePicture", myuserimg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams(baseUrl);
        params.setAsJsonContent(true);
        params.setBodyContent(obj.toString());
        Log.e("第三方数据",obj.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Log.e("第三方数据",resultString);
                    Login_Model baseModel = new Gson().fromJson(resultString, Login_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
//                        initlist(Leran_Goal.this);
//                        setResult(RESULT_OK);
                        saveFile.saveShareData("islogin", "true", LoginRegister.this);
                        saveFile.saveShareData("role", baseModel.getData().getRole() + "", LoginRegister.this);//管理员
//                        finish();
                        Intent intent = new Intent(LoginRegister.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        mam.outOneActivity(LoginRegister.this);

                    } else {
                        Toast.makeText(LoginRegister.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginRegister.this, "数据获取失败", Toast.LENGTH_SHORT).show();
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
                    saveFile.saveShareData("JSESSIONID", cookies.get(cookies.size() - 1).toString(), LoginRegister.this);
                }
            }
        });
    }

    private static final int MSG_SMSSDK_CALLBACK = 1;
    private static final int MSG_AUTH_CANCEL = 2;
    private static final int MSG_AUTH_ERROR= 3;
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
            msg.obj = new Object[] {platform.getName(), res};
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
        switch(msg.what) {
            case MSG_AUTH_CANCEL: {
                //取消授权
                Toast.makeText(this, "授权操作已取消", Toast.LENGTH_SHORT).show();
            } break;
            case MSG_AUTH_ERROR: {
                //授权失败
                Log.e("第三方数据",msg.toString());
                Toast.makeText(this, "授权操作遇到错误", Toast.LENGTH_SHORT).show();
            } break;
            case MSG_AUTH_COMPLETE: {
                //授权成功
                Toast.makeText(this, "授权成功，正在跳转登录操作…", Toast.LENGTH_SHORT).show();
                Object[] objs = (Object[]) msg.obj;
                String platform = (String) objs[0];
                HashMap<String, Object> res = (HashMap<String, Object>) objs[1];

                Platform  plat = ShareSDK.getPlatform(platform);
                myuserId =  plat.getDb().getUserId();
                if(LoginType == 1){
                    myuserimg =  res.get("figureurl_qq_2").toString();//qq头像高清地址
                }else{
                    myuserimg =  plat.getDb().getUserIcon();
                }
                myuserName =  plat.getDb().getUserName();
                plat.removeAccount(true);//清空缓存用户信息，防止客户端登录其他账号拿到资料是缓存信息。

                if (! TextUtils.isEmpty(myuserId)){
                    LoginMethod(saveFile.BaseUrl + saveFile.LoginUrl,LoginType);//第三方登录
                }
            } break;
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
