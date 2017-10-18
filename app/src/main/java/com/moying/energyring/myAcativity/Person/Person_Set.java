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
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import cn.jpush.android.api.JPushInterface;

public class Person_Set extends Activity {

    private RelativeLayout cache_Rel,change_Rel;
    private TextView cache_Txt;
    private SwitchButton push_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__set);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();
        initView();
        initData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#909090"));
        cententtxt.setText("设置");
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    private void initView() {
        RelativeLayout push_Rel = (RelativeLayout) findViewById(R.id.push_Rel);
        push_switch = (SwitchButton) findViewById(R.id.push_switch);
        cache_Rel = (RelativeLayout) findViewById(R.id.cache_Rel);
        change_Rel = (RelativeLayout) findViewById(R.id.change_Rel);
        cache_Txt = (TextView) findViewById(R.id.cache_Txt);
        RelativeLayout idea_Rel = (RelativeLayout) findViewById(R.id.idea_Rel);
        RelativeLayout about_Rel = (RelativeLayout) findViewById(R.id.about_Rel);
        ImageView cache_arrow = (ImageView) findViewById(R.id.cache_arrow);
        ImageView change_arrow = (ImageView) findViewById(R.id.change_arrow);
        ImageView idea_arrow = (ImageView) findViewById(R.id.idea_arrow);
        ImageView about_arrow = (ImageView) findViewById(R.id.about_arrow);

        Button quit_Btn = (Button) findViewById(R.id.quit_Btn);


        StaticData.ViewScale(push_Rel, 710, 120);
        StaticData.ViewScale(cache_Rel, 710, 120);
        StaticData.ViewScale(change_Rel, 710, 120);
        StaticData.ViewScale(idea_Rel, 710, 120);
        StaticData.ViewScale(about_Rel, 710, 120);
        StaticData.ViewScale(quit_Btn, 710, 120);
        StaticData.ViewScale(cache_arrow, 16, 30);
        StaticData.ViewScale(change_arrow, 16, 30);
        StaticData.ViewScale(idea_arrow, 16, 30);
        StaticData.ViewScale(about_arrow, 16, 30);

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
            Intent i = new Intent(Person_Set.this, LoginRegister.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            MyActivityManager.getInstance().finishAllActivity();
//            mam.finishAllActivity();
        }
    }


}
