package com.moying.energyring.myAcativity.Person.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.coolerfall.daemon.Daemon;

/**
 * Created by waylen on 2017/7/3.
 */

public class DaemonService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Daemon.run(this, DaemonService.class, Daemon.INTERVAL_ONE_MINUTE*2);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /* do something here */
        grayGuard();
        return super.onStartCommand(intent, flags, startId);
//        return START_STICKY;//部分手机可保活
    }

    private void grayGuard() {
        if (Build.VERSION.SDK_INT < 18) {
            //API < 18 ，此方法能有效隐藏Notification上的图标

            startForeground(GRAY_SERVICE_ID, new Notification());
        } else {
            Intent innerIntent = new Intent(this, DaemonInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

//        String[] timelegth = saveFile.getShareData("timeTxt", this).split(":");
//        Person_Play.calendarWeek(timelegth,this);

        //发送唤醒广播来促使挂掉的UI进程重新启动起来
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent();
//        alarmIntent.setAction(WakeReceiver.GRAY_WAKE_ACTION);
        PendingIntent operation = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long ALARM_INTERVAL = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_INTERVAL, operation);
        } else {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_INTERVAL, operation);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, DaemonService.class));//启动闹钟服务
    }

    private static int GRAY_SERVICE_ID = 666;

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class DaemonInnerService extends Service {


        @Override
        public void onCreate() {
            Log.i("LOG_TAG", "InnerService -> onCreate");
            super.onCreate();
//            Daemon.run(this, DaemonInnerService.class, Daemon.INTERVAL_ONE_MINUTE * 2);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.i("LOG_TAG", "InnerService -> onStartCommand");
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onDestroy() {
            Log.i("LOG_TAG", "InnerService -> onDestroy");
            super.onDestroy();
        }
    }


}
