package com.moying.energyring.waylenBaseView;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    private static final String TAG = "JPush";
    public static Context mycontext;

    @Override
    public void onCreate() {
        super.onCreate();


        //xUtils初始化
        x.Ext.init(this);
//		x.Ext.setDebug(true); // 是否输出debug日志

        //Jush初始化
//		Log.d(TAG, "[ExampleApplication] onCreate");
        initJpush();

//		//Fresco初始化
        Fresco.initialize(this);

        mycontext = this;

        MobclickAgent.openActivityDurationTrack(false);//禁止默认的友盟页面统计方式，这样将不会再自动统计Activity
//		MobclickAgent.setDebugMode(true);
    }

    /**
     * 初始化极光推送
     */
    private void initJpush() {
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
