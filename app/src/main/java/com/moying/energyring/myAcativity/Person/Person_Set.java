package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.DataCleanManager;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import cn.jpush.android.api.JPushInterface;

public class Person_Set extends Activity {

    private RelativeLayout cache_Rel,change_Rel;
    private TextView cache_Txt;
    private SwitchButton push_switch;
    static Activity person_Set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__set);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();
        initView();
        initData();
        person_Set = this;
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#2b2a2a"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#ffffff"));
        cententtxt.setText("设置");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    private void initView() {
        RelativeLayout personData_Rel = (RelativeLayout) findViewById(R.id.personData_Rel);
        ImageView personset_Img = (ImageView) findViewById(R.id.personset_Img);
        ImageView data_arrow = (ImageView) findViewById(R.id.data_arrow);
        change_Rel = (RelativeLayout) findViewById(R.id.change_Rel);
        ImageView phone_Img = (ImageView) findViewById(R.id.phone_Img);
        ImageView change_arrow = (ImageView) findViewById(R.id.change_arrow);
        RelativeLayout idea_Rel = (RelativeLayout) findViewById(R.id.idea_Rel);
        ImageView ideal_Img = (ImageView) findViewById(R.id.ideal_Img);
        ImageView idea_arrow = (ImageView) findViewById(R.id.idea_arrow);
        cache_Rel = (RelativeLayout) findViewById(R.id.cache_Rel);
        cache_Txt = (TextView) findViewById(R.id.cache_Txt);
        ImageView cache_Img = (ImageView) findViewById(R.id.cache_Img);
        ImageView mes_Img = (ImageView) findViewById(R.id.mes_Img);
        RelativeLayout push_Rel = (RelativeLayout) findViewById(R.id.push_Rel);
        push_switch = (SwitchButton) findViewById(R.id.push_switch);
        RelativeLayout about_Rel = (RelativeLayout) findViewById(R.id.about_Rel);
        ImageView about_Img = (ImageView) findViewById(R.id.about_Img);
        ImageView about_arrow = (ImageView) findViewById(R.id.about_arrow);
        Button quit_Btn = (Button) findViewById(R.id.quit_Btn);

        StaticData.ViewScale(personData_Rel, 710, 160);
        StaticData.ViewScale(change_Rel, 710, 160);
        StaticData.ViewScale(idea_Rel, 710, 160);
        StaticData.ViewScale(cache_Rel, 710, 160);
        StaticData.ViewScale(push_Rel, 710, 160);
        StaticData.ViewScale(about_Rel, 710, 160);
        StaticData.ViewScale(personset_Img, 44, 44);
        StaticData.ViewScale(phone_Img, 44, 44);
        StaticData.ViewScale(ideal_Img, 44, 44);
        StaticData.ViewScale(cache_Img, 44, 44);
        StaticData.ViewScale(mes_Img, 44, 44);
        StaticData.ViewScale(about_Img, 44, 44);
        StaticData.ViewScale(data_arrow, 48, 56);
        StaticData.ViewScale(change_arrow, 48, 56);
        StaticData.ViewScale(idea_arrow,  48, 56);
        StaticData.ViewScale(about_arrow, 48, 56);
        StaticData.ViewScale(quit_Btn, 690, 90);

        personData_Rel.setOnClickListener(new personData_Rel());
        quit_Btn.setOnClickListener(new quit_Btn());
        cache_Rel.setOnClickListener(new cache_Rel());
        change_Rel.setOnClickListener(new change_Rel());
        idea_Rel.setOnClickListener(new idea_Rel());
        about_Rel.setOnClickListener(new about_Rel());
        push_switch.setOnClickListener(new push_switch());
    }

    private void initData() {
        if (saveFile.getShareData("ispush", Person_Set.this).equals("false")) {
            push_switch.setChecked(false);
        }else {
            push_switch.setChecked(true);
        }

        try {
            cache_Txt.setText(DataCleanManager.getTotalCacheSize(Person_Set.this));
            if (StaticData.getNumbers(DataCleanManager.getTotalCacheSize(Person_Set.this)).equals("0")) {
                cache_Rel.setEnabled(false);
            } else {
                cache_Rel.setEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private class  push_switch implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (saveFile.getShareData("ispush", Person_Set.this).equals("false")) {
                push_switch.setChecked(true);
                saveFile.saveShareData("ispush", "true", Person_Set.this);
                JPushInterface.resumePush(getApplicationContext());
            }else {
                push_switch.setChecked(false);
                saveFile.saveShareData("ispush", "false", Person_Set.this);
                JPushInterface.stopPush(getApplicationContext());
            }
        }
    }


    //我的资料
    private class personData_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Set.this, Person_Data.class);
            startActivity(intent);
        }
    }


    //清除缓存
    public class cache_Rel implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                DataCleanManager.clearAllCache(Person_Set.this);
                cache_Txt.setText(DataCleanManager.getTotalCacheSize(Person_Set.this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class change_Rel implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Person_Set.this, Person_ChangePhone.class);
            startActivity(i);
        }
    }

    public class idea_Rel implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Person_Set.this, Person_Notice_Idea.class);
            startActivity(i);
        }
    }

    public class about_Rel implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Person_Set.this, Person_Notice_About.class);
            startActivity(i);
        }
    }

    public class quit_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            saveFile.clearShareData("islogin", Person_Set.this);
            saveFile.clearShareData("role", Person_Set.this);
            saveFile.clearShareData("JSESSIONID", Person_Set.this);
            Intent i = new Intent(Person_Set.this, MainLogin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            MyActivityManager.getInstance().finishAllActivity();
//            mam.finishAllActivity();
        }
    }


}
