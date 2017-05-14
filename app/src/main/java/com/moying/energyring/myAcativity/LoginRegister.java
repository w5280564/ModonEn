package com.moying.energyring.myAcativity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.util.List;


@ContentView(R.layout.activity_login_register)
public class LoginRegister extends Activity {
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
            LoginMethod(saveFile.BaseUrl + saveFile.LoginUrl);
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
                    if (baseModel.isIsSuccess()){
                        Toast.makeText(LoginRegister.this,baseModel.getMsg(),Toast.LENGTH_SHORT).show();
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


    //普通用户登录
    public void LoginMethod(String baseUrl) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("LoginType", 0);
            obj.put("LoginName", phone_edit.getText());
            obj.put("Code", codeedit.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams(baseUrl);
        params.setAsJsonContent(true);
        params.setBodyContent(obj.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Login_Model baseModel = new Gson().fromJson(resultString, Login_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
//                        initlist(Leran_Goal.this);
                        saveFile.saveShareData("islogin", "true", LoginRegister.this);
//                        setResult(RESULT_OK);
                        mam.finishAllActivity();

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


}
