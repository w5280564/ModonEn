package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Person_ShopDetails_Adress extends Activity {

    private String ProductID;
    private EditText adressName_edit, adressPhone_edit, adress_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personshop_details_adress);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();
        initView();

        Intent intent = getIntent();
        ProductID = intent.getStringExtra("ProductID");

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
        cententtxt.setText("收货地址");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    private void initView() {
        LinearLayout adress_Lin = (LinearLayout) findViewById(R.id.adress_Lin);
        Button sure_Btn = (Button) findViewById(R.id.sure_Btn);
        adressName_edit = (EditText) findViewById(R.id.adressName_edit);
        adressPhone_edit = (EditText) findViewById(R.id.adressPhone_edit);
        adress_edit = (EditText) findViewById(R.id.adress_edit);

        StaticData.ViewScale(adress_Lin, 710, 408);
        StaticData.ViewScale(sure_Btn, 536, 100);
        sure_Btn.setOnClickListener(new sure_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private class sure_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            initData(v);
        }
    }

    private void initData(View view) {
        if (StaticData.isSpace(adressName_edit.getText().toString())) {
            Toast.makeText(Person_ShopDetails_Adress.this, "收货人不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StaticData.isPhone(adressPhone_edit.getText().toString())) {
            Toast.makeText(Person_ShopDetails_Adress.this, "联系号码格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StaticData.isSpace(adress_edit.getText().toString())) {
            Toast.makeText(Person_ShopDetails_Adress.this, "收货地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        view.setEnabled(false);
        shopData(Person_ShopDetails_Adress.this, view, saveFile.BaseUrl + saveFile.shop_Adress_Url);
    }


    public void shopData(final Context context, final View view, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("ProductID", ProductID);
            obj.put("Receiver", adressName_edit.getText().toString());
            obj.put("ReceivePhone", adressPhone_edit.getText().toString());
            obj.put("ReceiveAddress", adress_edit.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.setAsJsonContent(true);
        params.setBodyContent(obj.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                view.setEnabled(true);
                if (resultString != null) {
                    Base_Model Model = new Gson().fromJson(resultString, Base_Model.class);
                    if (Model.isIsSuccess()) {
                        Toast.makeText(context, "商品兑换成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                view.setEnabled(true);
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, MainLogin.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
                view.setEnabled(true);
            }

            @Override
            public void onFinished() {
            }
        });
    }


}
