package com.moying.energyring.myAcativity.Find;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.moying.energyring.R;
import com.moying.energyring.network.saveFile;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "闹铃响了, 可以做点事情了~~", Toast.LENGTH_LONG).show();
        if (intent.getAction().equals("com.android.deskclock.ALARM_ALERT")) {
            String stopCount;
            String stopString = saveFile.getShareData("stopString", context);
            if (stopString.equals("关闭") || stopString.equals("false")) {
                stopCount = "关闭";
            } else {
                stopCount = Long.parseLong(saveFile.getShareData("stopString", context).substring(0, 2)) * 60 * 1000 + "";
            }
            saveFile.saveShareData("stopCount", stopCount, context);

            Intent intent2 = new Intent(context, FindRadioListActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent2.putExtra("myPlayMap", (Serializable) saveFile.getHashMap("videoPlay", context));
//        intent2.putExtra("myPlayMap", (Serializable) myPlayMap);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //例如这个id就是你传过来的
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            String time = saveFile.getShareData("timeTxt", context);
            String video = saveFile.getShareData("englishVideo", context);
            builder.setContentTitle("能量圈").setContentText("一个暖心的提醒").setSubText(time + "啦！能量圈提醒您应该收听" + video + "电台啦！点击本消息收听~").setSmallIcon(R.drawable.ring_icon).setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true);
            if (calendarWeek(context)) {
                manager.notify(1, builder.build());
            }
        }
    }


    public boolean calendarWeek(Context context) {
        boolean isweek = false;
        Calendar calendar = Calendar.getInstance();
        // 这里时区需要设置一下，不然会有8个小时的时间差
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek -= 1;
        List<String> myChoice = saveFile.getUserList(context, "choiceWeek");
        for (int i = 0; i < myChoice.size(); i++) {
            if (myChoice.get(i).equals("true") && i == dayOfWeek) {
                isweek = true;
                break;
            }
        }
        return isweek;
    }


//    public NotificationManager mNotificationManager;
//    /** NotificationCompat 构造器*/
//    NotificationCompat.Builder mBuilder;
//    /** Notification 的ID */
//    int notifyId = 101;
//
//    //（五）发送自定义通知（仿今日头条）
//    public void sendMyselfNotification(View view){
//        //先设定RemoteViews
//        RemoteViews view_custom = new RemoteViews(getPackageName(), R.layout.view_custom);
//        //设置对应IMAGEVIEW的ID的资源图片
//        view_custom.setImageViewResource(R.id.custom_icon, R.drawable.ic_action_my_fans);
//        view_custom.setTextViewText(R.id.tv_custom_title, "今日头条");
//        view_custom.setTextViewText(R.id.tv_custom_content, "金州勇士官方宣布球队已经聘请了主帅布鲁斯-卢，随后宣布了最后的结果。");
//        mBuilder = new NotificationCompat.Builder(this);
//        mBuilder.setContent(view_custom)
//                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
//                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
//                .setTicker("有新资讯")
//                .setAutoCancel(true)
//                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
//                .setOngoing(false)//不是正在进行的   true为正在进行  效果和.flag一样
//                .setSmallIcon(R.drawable.ic_action_my_fans);
//
//        Notification notify = mBuilder.build();
//        notify.contentView = view_custom;
//        mNotificationManager.notify(notifyId, notify);
//    }
//
//    public PendingIntent getDefalutIntent(int flags){
//        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
//        return pendingIntent;
//    }

}
