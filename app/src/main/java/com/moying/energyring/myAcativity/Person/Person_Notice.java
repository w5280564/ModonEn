package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.UserInfo_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Person_Notice extends Activity {

    private RelativeLayout choice_Rel;
    private TextView remind_Txt, mes_Txt;
    private LinearLayout noticecontent_Lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__notice);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initView();
        initData();
    }

    private void initView() {
        RelativeLayout title_Rel = (RelativeLayout) findViewById(R.id.title_Rel);
        Button return_Btn = (Button) findViewById(R.id.return_Btn);
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Rel, 0, 88);

        choice_Rel = (RelativeLayout) findViewById(R.id.choice_Rel);
        remind_Txt = (TextView) findViewById(R.id.remind_Txt);
        mes_Txt = (TextView) findViewById(R.id.mes_Txt);
         noticecontent_Lin = (LinearLayout) findViewById(R.id.noticecontent_Lin);
        ImageView fans_icon = (ImageView) findViewById(R.id.fans_icon);
        ImageView nomm_icon = (ImageView) findViewById(R.id.nomm_icon);
        ImageView like_icon = (ImageView) findViewById(R.id.like_icon);
        ImageView notice_iocn = (ImageView) findViewById(R.id.notice_iocn);
        ImageView fans_arrow = (ImageView) findViewById(R.id.fans_arrow);
        ImageView nomm_arrow = (ImageView) findViewById(R.id.nomm_arrow);
        ImageView like_arrow = (ImageView) findViewById(R.id.like_arrow);
        ImageView notice_arrow = (ImageView) findViewById(R.id.notice_arrow);
        TextView fansunrend_Txt = (TextView) findViewById(R.id.fansunrend_Txt);
        TextView nommunrend_Txt = (TextView) findViewById(R.id.nommunrend_Txt);
        TextView likeunrend_Txt = (TextView) findViewById(R.id.likeunrend_Txt);
        TextView noticeunrend_Txt = (TextView) findViewById(R.id.noticeunrend_Txt);
        RelativeLayout notice_Rel = (RelativeLayout) findViewById(R.id.notice_Rel);
        RelativeLayout fans_Rel = (RelativeLayout) findViewById(R.id.fans_Rel);
        RelativeLayout nomm_Rel = (RelativeLayout) findViewById(R.id.nomm_Rel);
        RelativeLayout like_Rel = (RelativeLayout) findViewById(R.id.like_Rel);


        StaticData.ViewScale(choice_Rel, 280, 60);
        StaticData.ViewScale(noticecontent_Lin, 710, 600);
        StaticData.ViewScale(fans_icon, 76, 76);
        StaticData.ViewScale(like_icon, 76, 76);
        StaticData.ViewScale(nomm_icon, 76, 76);
        StaticData.ViewScale(notice_iocn, 76, 76);
        StaticData.ViewScale(fans_arrow, 16, 30);
        StaticData.ViewScale(nomm_arrow, 16, 30);
        StaticData.ViewScale(like_arrow, 16, 30);
        StaticData.ViewScale(notice_arrow, 16, 30);
        return_Btn.setOnClickListener(new return_Btn());
        remind_Txt.setOnClickListener(new remind_Txt());
        mes_Txt.setOnClickListener(new mes_Txt());
        notice_Rel.setOnClickListener(new notice_Rel());
        fans_Rel.setOnClickListener(new fans_Rel());
        nomm_Rel.setOnClickListener(new nomm_Rel());
        like_Rel.setOnClickListener(new like_Rel());
    }


    private void initData() {
        changebg(remind_Txt);
        noticecontent_Lin.setVisibility(View.VISIBLE);
        StaticData.ViewScale(remind_Txt, 160, 0);
        StaticData.ViewScale(mes_Txt, 160, 0);
//        UserData(Person_Notice.this, saveFile.BaseUrl + saveFile.);
    }

    private void setinitbg() {
        remind_Txt.setBackgroundResource(0);
        remind_Txt.setTextColor(Color.parseColor("#4d4d4d"));
        mes_Txt.setBackgroundResource(0);
        mes_Txt.setTextColor(Color.parseColor("#4d4d4d"));
    }

    private void changebg(TextView myView) {
        StaticData.ViewScale(myView, 160, 0);
        myView.setElevation(2f);
        myView.setBackgroundResource(R.drawable.personnotice_choice);
        myView.setTextColor(Color.parseColor("#ffffff"));
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private class remind_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setinitbg();
            changebg(remind_Txt);
            noticecontent_Lin.setVisibility(View.VISIBLE);
        }
    }

    private class mes_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setinitbg();
            changebg(mes_Txt);
            noticecontent_Lin.setVisibility(View.GONE
            );
        }
    }

    //通知
    private class notice_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Notice.this, Person_Notice_NoticeList.class);
            startActivity(intent);
        }
    }

    //新粉丝
    private class fans_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Notice.this, Person_Notice_Fans.class);
            startActivity(intent);
        }
    }

    //评论
    private class nomm_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Notice.this, Person_Notice_Nomm.class);
            startActivity(intent);
        }
    }

    //喜欢
    private class like_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Notice.this, Person_Notice_Like.class);
            startActivity(intent);
        }
    }

    public void UserData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    UserInfo_Model userModel = new Gson().fromJson(resultString, UserInfo_Model.class);
                    if (userModel.isIsSuccess() && !userModel.getData().equals("[]")) {
                        UserInfo_Model.DataBean oneData = userModel.getData();

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
