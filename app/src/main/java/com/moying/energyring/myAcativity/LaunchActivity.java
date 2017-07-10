package com.moying.energyring.myAcativity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.R;

import static com.moying.energyring.network.saveFile.saveShareData;

/**
 * Handler刷新实现启动页
 */
public class LaunchActivity extends Activity {
    private String FIRSTINIT = "firstinit";
    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.launchstyle);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        Float scale = (float) dm.widthPixels / 750;
        Float heghtSclae = (float) dm.heightPixels / 1334;
        saveShareData("scale", scale + "", this);
        saveShareData("heghtSclae", heghtSclae + "", this);

//		JPushInterface.resumePush(getApplicationContext());//推送注册
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        SimpleDraweeView lunchimg = (SimpleDraweeView) findViewById(R.id.lunchimg);
        Uri imgurl = Uri.parse("res:// /" + R.drawable.launch_icon);
        lunchimg.setImageURI(imgurl);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getFirstInit()) {
                    saveFirstInit(false);
                    Intent i = new Intent(LaunchActivity.this, WelcomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
//					intent.putExtra("regtype", "2");
                    startActivity(intent);
                    finish();
                }
            }

        }, 500);
    }

    public void saveFirstInit(boolean is) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FIRSTINIT, is);
        editor.commit();
    }


    public boolean getFirstInit() {
        return preferences.getBoolean(FIRSTINIT, true);
    }

    //屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

}
