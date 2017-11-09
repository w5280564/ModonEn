package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.person_daypk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAcativity.PostingActivity;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.viewpagercards.PkSuccess_CardItem;
import com.moying.energyring.waylenBaseView.viewpagercards.PkSuccess_CardPagerAdapter;
import com.moying.energyring.waylenBaseView.viewpagercards.PkSuccess_ShadowTransformer;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Pk_AddSuccess extends Activity {
    ViewPager viewPager;
    TextView gan_Txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeprice);
        setContentView(R.layout.activity_pk__add_success);

        initView();
        initData();
    }

    private void initView() {
        Button sure_Btn = (Button) findViewById(R.id.sure_Btn);
        ImageView success_icon = (ImageView) findViewById(R.id.success_icon);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        gan_Txt = (TextView) findViewById(R.id.gan_Txt);
//        StaticData.ViewScale(sure_Btn,100,80);
        StaticData.ViewScale(success_icon, 434, 352);
        StaticData.ViewScale(viewPager, 0, 550);
        StaticData.ViewScale(gan_Txt, 410, 100);

        sure_Btn.setOnClickListener(new sure_Btn());
        gan_Txt.setOnClickListener(new gan_Txt());
    }

    private class sure_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class gan_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Pk_AddSuccess.this, PostingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initData() {
        String userId = saveFile.getShareData("userId", Pk_AddSuccess.this);
        ListData(Pk_AddSuccess.this, saveFile.BaseUrl + saveFile.DayPk_Url + "?UserID=" + userId);
    }

    private PkSuccess_CardPagerAdapter mCardAdapter;
    private PkSuccess_ShadowTransformer mCardShadowTransformer;

    private void initList(Context context) {
        
        mCardAdapter = new PkSuccess_CardPagerAdapter();
        int size = baseModel.getData().size();
        for (int i = 0; i < size; i++) {
            person_daypk_Model.DataBean oneData = baseModel.getData().get(i);
            NumberFormat nf = new DecimalFormat("#.#");//# 0 不显示
            mCardAdapter.addCardItem(new PkSuccess_CardItem(oneData.getFilePath(), oneData.getProjectName(), nf.format(oneData.getReportNum()) + "", oneData.getRanking() + "",oneData.getProjectUnit()));
        }
        mCardShadowTransformer = new PkSuccess_ShadowTransformer(viewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(false);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
//        viewPager.setCurrentItem(0);
    }


    person_daypk_Model baseModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    baseModel = new Gson().fromJson(resultString, person_daypk_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
                        initList(context);
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
