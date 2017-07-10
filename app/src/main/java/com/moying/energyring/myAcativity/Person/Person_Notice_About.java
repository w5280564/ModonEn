package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.waylenBaseView.MyActivityManager;

public class Person_Notice_About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_notice_about);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();
        initView();

        String version = StaticData.getversionName(Person_Notice_About.this);
        TextView versionName = (TextView)findViewById(R.id.versionName);
        versionName.setText("能量圈 "+version);
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
        cententtxt.setText("关于能量圈");
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private void initView() {
        ImageView about_img = (ImageView) findViewById(R.id.about_img);
        RelativeLayout aboutphone_Rel = (RelativeLayout) findViewById(R.id.aboutphone_Rel);
        RelativeLayout aboutfen_Rel = (RelativeLayout) findViewById(R.id.aboutfen_Rel);
        RelativeLayout aboutjie_Rel = (RelativeLayout) findViewById(R.id.aboutjie_Rel);
        ImageView fen_Img = (ImageView) findViewById(R.id.fen_Img);
        ImageView jie_Img = (ImageView) findViewById(R.id.jie_Img);


        StaticData.ViewScale(about_img, 160, 160);
        StaticData.ViewScale(aboutphone_Rel, 0, 100);
        StaticData.ViewScale(aboutfen_Rel, 0, 100);
        StaticData.ViewScale(aboutjie_Rel, 0, 100);
        StaticData.ViewScale(fen_Img, 16, 30);
        StaticData.ViewScale(jie_Img, 16, 30);
        aboutphone_Rel.setOnClickListener(new aboutphone_Rel());
        aboutfen_Rel.setOnClickListener(new aboutfen_Rel());
        aboutjie_Rel.setOnClickListener(new aboutjie_Rel());
    }

    private class aboutphone_Rel extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {

        }
    }

    private class aboutfen_Rel extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.pp.cn/detail.html?appid=6863306&ch_src=pp_dev&ch=default"));
            it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            startActivity(it);
        }
    }

    private class aboutjie_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent it = new Intent(Person_Notice_About.this, Person_Notice_JieShao.class);
            startActivity(it);
        }
    }

}
