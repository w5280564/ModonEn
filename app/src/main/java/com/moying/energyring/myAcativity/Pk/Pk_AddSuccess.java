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
import com.moying.energyring.Model.JiFenAndBadge_Model;
import com.moying.energyring.Model.person_daypk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAcativity.Person.Person_BadgeHas;
import com.moying.energyring.myAcativity.Person.Person_Commendation;
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
    private JiFenAndBadge_Model jiFenmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeprice);
        setContentView(R.layout.activity_pk__add_success);

        initView();
        initData();

        Intent intent = getIntent();
        if (intent.getParcelableExtra("jiFenmodel") != null) {
            jiFenmodel = intent.getParcelableExtra("jiFenmodel");
            if (jiFenmodel.getData().getIntegral() == 0){
                if (!jiFenmodel.getData().get_Badge().isEmpty()) {
                    Intent intentSuccess = new Intent(Pk_AddSuccess.this, Person_BadgeHas.class);
                    intentSuccess.putExtra("jiFenmodel", jiFenmodel);
//                    startActivity(intentSuccess);
                    startActivityForResult(intentSuccess,1003);
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
                }else if (!jiFenmodel.getData().get_Praise().isEmpty()) {
                    Intent intentSuccess = new Intent(Pk_AddSuccess.this, Person_Commendation.class);
                    intentSuccess.putExtra("jiFenmodel", jiFenmodel);
                    startActivity(intentSuccess);
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
                }
            }else{
                Intent intentJiFen = new Intent(Pk_AddSuccess.this, JiFenActivity.class);
                intentJiFen.putExtra("media","daypk");
                intentJiFen.putExtra("jifen", jiFenmodel.getData().getIntegral());
                intentJiFen.putExtra("RewardIntegral", jiFenmodel.getData().getRewardIntegral()+"");
                startActivityForResult(intentJiFen, 1002);

            }
        }
    }

    private void initView() {
        Button sure_Btn = (Button) findViewById(R.id.sure_Btn);
        ImageView success_icon = (ImageView) findViewById(R.id.success_icon);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        gan_Txt = (TextView) findViewById(R.id.gan_Txt);
//        StaticData.ViewScale(sure_Btn,100,80);
        StaticData.ViewScale(success_icon, 434, 352);
        StaticData.ViewScale(viewPager, 750, 550);
//        int pad = (int)(Float.parseFloat(saveFile.getShareData("scale",this)) *40);
//        viewPager.setPaddingRelative(padstart,0,padstart,0);
//        viewPager.setPadding(pad,0,pad,0);

        StaticData.ViewScale(gan_Txt, 410, 100);

        sure_Btn.setOnClickListener(new sure_Btn());
        gan_Txt.setOnClickListener(new gan_Txt());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1002) {
            if (!jiFenmodel.getData().get_Badge().isEmpty()) {
                Intent intent = new Intent(Pk_AddSuccess.this, Person_BadgeHas.class);
                intent.putExtra("jiFenmodel", jiFenmodel);
                startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
            }
        }else if (resultCode == 1003){
            if (!jiFenmodel.getData().get_Praise().isEmpty()) {
                Intent intentSuccess = new Intent(Pk_AddSuccess.this, Person_Commendation.class);
                intentSuccess.putExtra("jiFenmodel", jiFenmodel);
                startActivity(intentSuccess);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
            }
        }
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
//            Intent intent = new Intent(Pk_AddSuccess.this, PostingActivity.class);
//            startActivity(intent);
//            finish();
            finish();
//            Intent intent = new Intent(Pk_AddSuccess.this, Person_BadgeHas.class);
//            intent.putExtra("jiFenmodel", jiFenmodel);
//            startActivity(intent);
//            overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
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
            mCardAdapter.addCardItem(new PkSuccess_CardItem(oneData.getFilePath(), oneData.getProjectName(), nf.format(oneData.getReportNum()) + "", oneData.getRanking() + "", oneData.getProjectUnit()));
        }
        mCardShadowTransformer = new PkSuccess_ShadowTransformer(viewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(false);//去阴影
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
