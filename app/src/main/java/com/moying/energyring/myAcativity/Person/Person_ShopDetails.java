package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.ShopDetails_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.HtmlToText;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Person_ShopDetails extends Activity {

    private SimpleDraweeView content_simple;
    private TextView dui_Txt, name_Txt, price_Txt, fen_Txt, content_Txt;
    private String Integral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_shopdetails);

        initView();
        initData();
        Intent intent = getIntent();
        Integral = intent.getStringExtra("Integral");
    }

    private void initView() {
        content_simple = (SimpleDraweeView) findViewById(R.id.content_simple);
        dui_Txt = (TextView) findViewById(R.id.dui_Txt);
        name_Txt = (TextView) findViewById(R.id.name_Txt);
        price_Txt = (TextView) findViewById(R.id.price_Txt);
        fen_Txt = (TextView) findViewById(R.id.fen_Txt);
        content_Txt = (TextView) findViewById(R.id.content_Txt);
        Button return_Btn = (Button) findViewById(R.id.return_Btn);
        Button dui_Btn = (Button) findViewById(R.id.dui_Btn);


        StaticData.ViewScale(content_simple, 0, 772);
        StaticData.ViewScale(dui_Txt, 108, 40);
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(dui_Btn, 0, 98);
        return_Btn.setOnClickListener(new return_Btn());
        dui_Btn.setOnClickListener(new dui_Btn());
    }

    public class return_Btn implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class dui_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (Integral == null) {
                return;
            }
            if (Integer.parseInt(Integral) >= Model.getData().getIntegral()) {
                Intent intent = new Intent(Person_ShopDetails.this, Person_ShopDetails_Adress.class);
                intent.putExtra("ProductID", ProductID);
                startActivity(intent);
            } else {
                Toast.makeText(Person_ShopDetails.this, "积分不足", Toast.LENGTH_SHORT).show();
            }

        }
    }

    String ProductID;

    private void initData() {
        Intent intent = getIntent();
        ProductID = intent.getStringExtra("ProductID");
        DetailsData(Person_ShopDetails.this, saveFile.BaseUrl + saveFile.Product_Detrail_Url + "?ProductID=" + ProductID);
    }

    public void rankCountTextsColor(String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#989797")), alltext.length() - 2, alltext.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//修改颜色
        myTxt.setText(styledText);
    }

    ShopDetails_Model Model;

    public void DetailsData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Model = new Gson().fromJson(resultString, ShopDetails_Model.class);
                    if (Model.isIsSuccess() && !Model.getData().equals("[]")) {
                        ShopDetails_Model.DataBean oneData = Model.getData();
                        if (oneData.getFilePath() != null) {
                            Uri imgUri = Uri.parse(oneData.getFilePath());
                            content_simple.setImageURI(imgUri);
                        }
                        name_Txt.setText(oneData.getProductName());
                        price_Txt.setText("市场参考价：" + oneData.getRefPrice() + "元");
                        rankCountTextsColor(oneData.getIntegral() + "积分", fen_Txt);


                        CharSequence charSequence = Html.fromHtml(oneData.getBrief());
                        content_Txt.setText(HtmlToText.delHTMLTag(charSequence.toString()));
                        content_Txt.setMovementMethod(LinkMovementMethod.getInstance());
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
