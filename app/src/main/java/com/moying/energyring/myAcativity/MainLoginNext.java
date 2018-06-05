package com.moying.energyring.myAcativity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataString_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.Login_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.MD5;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MainLoginNext extends Activity {
    private Button code_Btn;
    private EditText codeedit, phone_edit, wu_edit;
    private View wu_Rel;
    String digestStr = "";
    private int LoginType;
    private MyActivityManager mam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_loginnext);
        mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();
        initView();
        initData();

    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
//        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.login_blackreturn);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#2b2a2a"));
        cententtxt.setText("");
        StaticData.ViewScale(return_Btn, 136, 88);
//        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private void initView() {
        View phone_Rel = findViewById(R.id.phone_Rel);
        View code_Rel = findViewById(R.id.code_Rel);
        code_Btn = (Button) findViewById(R.id.code_Btn);
        wu_Rel = findViewById(R.id.wu_Rel);
        Button loginFinsh_Btn = (Button) findViewById(R.id.loginFinsh_Btn);
        View login_phone = findViewById(R.id.login_phone);
        View login_code = findViewById(R.id.login_code);
        View login_wu = findViewById(R.id.login_wu);
        codeedit = (EditText) findViewById(R.id.codeedit);
        phone_edit = (EditText) findViewById(R.id.phone_edit);
        wu_edit = (EditText) findViewById(R.id.wu_edit);


        StaticData.ViewScale(phone_Rel, 570, 100);
        StaticData.ViewScale(code_Rel, 570, 100);
        StaticData.ViewScale(wu_Rel, 570, 100);
        StaticData.ViewScale(loginFinsh_Btn, 570, 110);
        StaticData.ViewScale(login_phone, 40, 40);
        StaticData.ViewScale(login_code, 40, 40);
        StaticData.ViewScale(login_wu, 40, 40);

        loginFinsh_Btn.setOnClickListener(new loginFinsh_Btn());
        code_Btn.setOnClickListener(new code_Btn());
    }

    private void initData() {
//        ShareSDK.initSDK(this);
//        handler = new Handler(this);
        LoginType = 0;
        codeHashCode(saveFile.BaseUrl + "ec/Account/HashCode_Get");
    }

    private class loginFinsh_Btn extends NoDoubleClickListener{

        @Override
        protected void onNoDoubleClick(View v) {
//            Intent intent = new Intent(MainLoginNext.this, MainChangePhone.class);
//            startActivity(intent);
            if (!StaticData.isPhone(phone_edit.getText().toString())) {
                Toast.makeText(MainLoginNext.this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
                return;
            } else if (StaticData.Stringspace(codeedit.getText().toString())) {
                Toast.makeText(MainLoginNext.this, "验证码错误", Toast.LENGTH_SHORT).show();
                return;
            }
            LoginType = 0;
            if (StaticData.isSpace(wu_edit.getText().toString())) {
                LoginMethod(MainLoginNext.this, saveFile.BaseUrl + saveFile.LoginUrl, LoginType);
            } else {
                InviteISHave(MainLoginNext.this, saveFile.BaseUrl + saveFile.InviteISHave_Url + "?InviteCode=" + wu_edit.getText().toString());
            }

        }
    }

    private class code_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            if (!StaticData.isPhone(phone_edit.getText().toString())) {
                Toast.makeText(MainLoginNext.this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
                return;
            }
            codeHadInv(MainLoginNext.this, saveFile.BaseUrl + saveFile.LoginName_IsHaveRefUser_Url + "?LoginName=" + phone_edit.getText());
            codeMethod(MainLoginNext.this, saveFile.BaseUrl + saveFile.CodeUrl + "?PhoneNo=" + phone_edit.getText() + "&Type=2" + "&VerificationCode=" + digestStr);
            time = new TimeCount(60000, 1000);
            time.start();
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

    public void codeHashCode(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    BaseDataString_Model baseModel = new Gson().fromJson(resultString, BaseDataString_Model.class);

                    digestStr = MD5.MessageDigest(MD5.MessageDigest(baseModel.getData().getBytes()).getBytes());
//                    if (baseModel.isIsSuccess()) {
//                        Toast.makeText(MainLogin.this, baseModel.getMsg(), Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    Toast.makeText(MainLoginNext.this, "数据获取失败", Toast.LENGTH_SHORT).show();
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

    //发送验证码
    public void codeMethod(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model baseModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(context, baseModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
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

    Base_Model invModel;

    //是否绑定过能量源
    public void codeHadInv(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    invModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (invModel.isIsSuccess()) {
                        if (invModel.isData()) {
                            wu_Rel.setVisibility(View.INVISIBLE);
                        } else {
                            wu_Rel.setVisibility(View.VISIBLE);
                        }
                    } else {

                    }
                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
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


    public void InviteISHave(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model baseModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (baseModel.isIsSuccess()) {
                        if (baseModel.isData()) {
                            LoginMethod(MainLoginNext.this, saveFile.BaseUrl + saveFile.LoginUrl, LoginType);
                        } else {
                            Toast.makeText(context, "能量源不存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
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
    public void LoginMethod(final Context context, final String baseUrl, int loginType) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("LoginType", loginType);
            obj.put("LoginName", phone_edit.getText());
            obj.put("Code", codeedit.getText());
            obj.put("InviteCode", wu_edit.getText());

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


                        if (StaticData.isSpace(baseModel.getData().getLoginName())) {
                            Intent intent1 = new Intent(context, MainChangePhone.class);
                            startActivity(intent1);
                            Log.e("LoginName","true");
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


}
