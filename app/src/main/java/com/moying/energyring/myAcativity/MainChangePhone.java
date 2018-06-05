package com.moying.energyring.myAcativity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class MainChangePhone extends Activity {

    private View wu_Rel;
    private Button code_Btn;
    private EditText phone_edit, codeedit, wu_edit;
    String digestStr = "";
    private int LoginType;
    private String PhoneNo, Code, InviteCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_changephone);

        initTitle();
        initView();
        initData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.login_blackcha);
        StaticData.ViewScale(return_Btn, 136, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
            overridePendingTransition(0, 0);
        }
    }

    private void initView() {
        TextView bang_Txt = (TextView) findViewById(R.id.bang_Txt);
        View phone_Rel = findViewById(R.id.phone_Rel);
        View code_Rel = findViewById(R.id.code_Rel);
        code_Btn = (Button) findViewById(R.id.code_Btn);
        wu_Rel = findViewById(R.id.wu_Rel);
        Button loginFinsh_Btn = (Button) findViewById(R.id.loginFinsh_Btn);
        View login_phone = findViewById(R.id.login_phone);
        View login_code = findViewById(R.id.login_code);
        View login_wu = findViewById(R.id.login_wu);


        StaticData.ViewScale(bang_Txt, 570, 297);
        StaticData.ViewScale(phone_Rel, 570, 100);
        StaticData.ViewScale(code_Rel, 570, 100);
        StaticData.ViewScale(wu_Rel, 570, 100);
        StaticData.ViewScale(loginFinsh_Btn, 570, 110);
        StaticData.ViewScale(login_phone, 40, 40);
        StaticData.ViewScale(login_code, 40, 40);
        StaticData.ViewScale(login_wu, 40, 40);

        codeedit = (EditText) findViewById(R.id.codeedit);
        phone_edit = (EditText) findViewById(R.id.phone_edit);
        wu_edit = (EditText) findViewById(R.id.wu_edit);

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
            if (!StaticData.isPhone(phone_edit.getText().toString())) {
                Toast.makeText(MainChangePhone.this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
                return;
            } else if (StaticData.Stringspace(codeedit.getText().toString())) {
                Toast.makeText(MainChangePhone.this, "验证码错误", Toast.LENGTH_SHORT).show();
                return;
            }

//            LoginMethod(MainChangePhone.this, saveFile.BaseUrl + saveFile.LoginUrl, LoginType);
            LoginType = 0;

            PhoneNo = phone_edit.getText().toString();
            Code = codeedit.getText().toString();
            InviteCode = wu_edit.getText().toString();

            if (StaticData.isSpace(wu_edit.getText().toString())) {
                changeAndInviteMethod(MainChangePhone.this, saveFile.BaseUrl + saveFile.Phone_ChangeAndInvite_Url + "?PhoneNo=" + PhoneNo + "&Code=" + Code + "&InviteCode=" + InviteCode);
            } else {
                InviteISHave(MainChangePhone.this, saveFile.BaseUrl + saveFile.InviteISHave_Url + "?InviteCode=" + wu_edit.getText().toString());
            }
        }
    }

    private class code_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            if (!StaticData.isPhone(phone_edit.getText().toString())) {
                Toast.makeText(MainChangePhone.this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
                return;
            }
            codeHadInv(MainChangePhone.this, saveFile.BaseUrl + saveFile.User_IsHaveRefUser_Url);
            codeMethod(MainChangePhone.this, saveFile.BaseUrl + saveFile.CodeUrl + "?PhoneNo=" + phone_edit.getText() + "&Type=4" + "&VerificationCode=" + digestStr);
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
                    Toast.makeText(MainChangePhone.this, "数据获取失败", Toast.LENGTH_SHORT).show();
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

                            changeAndInviteMethod(context, saveFile.BaseUrl + saveFile.Phone_ChangeAndInvite_Url + "?PhoneNo=" + PhoneNo + "&Code=" + Code + "&InviteCode=" + InviteCode);

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


    public void changeAndInviteMethod(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Login_Model baseModel = new Gson().fromJson(resultString, Login_Model.class);
                    if (baseModel.isIsSuccess()) {

                        String userId = baseModel.getData().getUserID() + "";
                        saveFile.saveShareData("islogin", "true", context);
                        saveFile.saveShareData("role", baseModel.getData().getRole() + "", context);//管理员
                        saveFile.saveShareData("userId", userId, context);
                        saveFile.saveShareData("InviteCode", baseModel.getData().getInviteCode(), context);
                        saveFile.saveShareData("NickName", baseModel.getData().getNickName(), context);

                        Toast.makeText(context, "绑定成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
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


}
