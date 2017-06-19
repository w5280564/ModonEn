package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Person_ShopList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Person_ShopDetails extends Activity {

    private SimpleDraweeView content_simple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_shopdetails);

        initView();
        initData();

    }

    private void initView(){
        content_simple = (SimpleDraweeView)findViewById(R.id.content_simple);
        TextView dui_Txt = (TextView)findViewById(R.id.dui_Txt);
        Button return_Btn = (Button)findViewById(R.id.return_Btn);
        Button dui_Btn = (Button)findViewById(R.id.dui_Btn);


        StaticData.ViewScale(content_simple,0,772);
        StaticData.ViewScale(dui_Txt,108,40);
        StaticData.ViewScale(return_Btn,40,40);
        StaticData.ViewScale(dui_Btn,0,98);
        dui_Btn.setOnClickListener(new dui_Btn());
    }

    private class dui_Btn implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_ShopDetails.this,Person_ShopDetails_Adress.class);
            startActivity(intent);
        }
    }

    private void initData(){
//        DetailsData(Person_ShopDetails.this, saveFile.BaseUrl + saveFile.);
    }

    Person_ShopList_Model Model;
    public void DetailsData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Model = new Gson().fromJson(resultString, Person_ShopList_Model.class);
                    if (Model.isIsSuccess() && !Model.getData().equals("[]")) {

                    } else {
                        Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    startActivity(intent);
                }
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
