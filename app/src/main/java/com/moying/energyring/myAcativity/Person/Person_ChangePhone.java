package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataString_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.MD5;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Person_ChangePhone extends Activity {
    private EditText phone_edit,codeedit;
    Button code_Btn;
    String digestStr = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__change_phone);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();
        initView();
        codeHashCode(saveFile.BaseUrl + "ec/Account/HashCode_Get");
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("更换手机号");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private void initView(){
        RelativeLayout phonerel = (RelativeLayout) findViewById(R.id.phonerel);
        RelativeLayout coderel = (RelativeLayout) findViewById(R.id.coderel);
        ImageView login_phone = (ImageView) findViewById(R.id.login_phone);
        ImageView login_code = (ImageView) findViewById(R.id.login_code);
         code_Btn = (Button) findViewById(R.id.code_Btn);
         phone_edit = (EditText) findViewById(R.id.phone_edit);
         codeedit = (EditText) findViewById(R.id.codeedit);
        Button quit_Btn = (Button) findViewById(R.id.quit_Btn);

        StaticData.ViewScale(phonerel, 710, 100);
        StaticData.ViewScale(coderel, 710, 100);
        StaticData.ViewScale(login_phone, 38, 54);
        StaticData.ViewScale(login_code, 38, 54);
        StaticData.ViewScale(quit_Btn, 710, 120);
        code_Btn.setOnClickListener(new code_Btn());
        quit_Btn.setOnClickListener(new quit_Btn());
    }


    public class code_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            if (!StaticData.isPhone(phone_edit.getText().toString())) {
                Toast.makeText(Person_ChangePhone.this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
                return;
            }
            codeMethod(saveFile.BaseUrl + saveFile.CodeUrl + "?PhoneNo=" + phone_edit.getText() + "&Type=4"+"&VerificationCode=" + digestStr);
            time = new TimeCount(60000, 1000);
            time.start();
        }
    }
public class quit_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            if (!StaticData.isPhone(phone_edit.getText().toString())) {
                Toast.makeText(Person_ChangePhone.this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
                return;
            } else if (StaticData.Stringspace(codeedit.getText().toString())) {
                Toast.makeText(Person_ChangePhone.this, "验证码错误", Toast.LENGTH_SHORT).show();
                return;
            }
//            LoginType = 0;
//            LoginMethod(saveFile.BaseUrl + saveFile.LoginUrl,LoginType);
            ChangePhoneData(saveFile.BaseUrl + saveFile.Phone_ChangeUrl + "?PhoneNo=" + phone_edit.getText() + "&Code="+codeedit.getText());

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
                    Toast.makeText(Person_ChangePhone.this, "数据获取失败", Toast.LENGTH_SHORT).show();
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
    public void codeMethod(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model baseModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(Person_ChangePhone.this, baseModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Person_ChangePhone.this, baseModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Person_ChangePhone.this, "数据获取失败", Toast.LENGTH_SHORT).show();
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

    public void ChangePhoneData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model baseModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(Person_ChangePhone.this, "更换成功", Toast.LENGTH_SHORT).show();
                        Person_ChangePhone.this.finish();
                    }else {
                        Toast.makeText(Person_ChangePhone.this, baseModel.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Person_ChangePhone.this, "数据获取失败", Toast.LENGTH_SHORT).show();
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




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
