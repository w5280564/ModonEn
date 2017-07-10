package com.moying.energyring.myAcativity.Find;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.AlarmManagerUtil;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Person_Play extends Activity {
    private TextView clock_Time, videoTxt;
    private SwitchButton video_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__play);


        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setText("定时播放电台");
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);

        RelativeLayout play_Rel = (RelativeLayout) findViewById(R.id.play_Rel);
        RelativeLayout video_Rel = (RelativeLayout) findViewById(R.id.video_Rel);
        clock_Time = (TextView) findViewById(R.id.clock_Time);
        videoTxt = (TextView) findViewById(R.id.videoTxt);
        video_switch = (SwitchButton) findViewById(R.id.video_switch);

        return_Btn.setOnClickListener(new return_Btn());
        play_Rel.setOnClickListener(new play_Rel());
        video_Rel.setOnClickListener(new video_Rel());

        video_switch.setOnCheckedChangeListener(new video_switch());

//        Daemon.run(this, DaemonService.class, Daemon.INTERVAL_ONE_MINUTE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String timeWeek = "";
        if (saveFile.getUserList(this, "choiceWeek").isEmpty()) {
            timeWeek = "";
        } else {
            timeWeek = Person_CountDownTimer.week_Txt(Person_Play.this);
        }
        if (!saveFile.getShareData("timeTxt", Person_Play.this).equals("false")) {
            String time = saveFile.getShareData("timeTxt", Person_Play.this);
            timeWeek += "\n" + time;
        }
        clock_Time.setText(timeWeek);

        if (!saveFile.getShareData("englishVideo", Person_Play.this).equals("false")) {
            videoTxt.setText(saveFile.getShareData("englishVideo", Person_Play.this));
        }

        if (saveFile.getShareData("videoSwitch", Person_Play.this).equals("false")) {
            video_switch.setChecked(false);
        } else {
            video_switch.setChecked(true);
        }

    }


    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onBackPressed();
//            Intent intent = new Intent(Person_Play.this, AlarmReceiver.class);
//            PendingIntent sender = PendingIntent.getBroadcast(Person_Play.this, 0, intent, 0);
//            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
////            startAlarm(manager, sender);
//            if (saveFile.getShareData("videoSwitch", Person_Play.this).equals("false")) {
//                manager.cancel(sender);//取消闹钟
//            }else{
//                startAlarm(manager, sender);// 进行闹铃注册
//            }
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Person_Play.this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(Person_Play.this, 0, intent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (saveFile.getShareData("videoSwitch", Person_Play.this).equals("false")) {
            manager.cancel(sender);//取消闹钟
        } else {
            startAlarm(manager, sender);// 进行闹铃注册
        }
    }

    public class play_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Play.this, Person_CountDownTimer.class);
            startActivity(intent);
        }
    }

    public class video_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Play.this, Person_PlayVideoList.class);
            startActivity(intent);
        }
    }

    public class video_switch implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (saveFile.getShareData("timeTxt", Person_Play.this).equals("false")) {
                Toast.makeText(Person_Play.this, "请设置闹钟时间", Toast.LENGTH_SHORT).show();
                video_switch.setChecked(false);
                return;
            } else if (saveFile.getShareData("englishVideo", Person_Play.this).equals("false")) {
                Toast.makeText(Person_Play.this, "请设置电台", Toast.LENGTH_SHORT).show();
                video_switch.setChecked(false);
                return;
            }


            Intent intent = new Intent(Person_Play.this, AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(Person_Play.this, 0, intent, 0);
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (b) {

                saveFile.saveShareData("videoSwitch", "true", Person_Play.this);

                // 过10s 执行这个闹铃
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.add(Calendar.SECOND, 10);
//                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);//设置闹钟
                startAlarm(manager, sender);// 进行闹铃注册

            } else {
                saveFile.saveShareData("videoSwitch", "false", Person_Play.this);
                manager.cancel(sender);//取消闹钟
            }
        }
    }


    //定时闹钟参数
    public void startAlarm(AlarmManager mAlamManager, PendingIntent pi) {

        String[] timelegth = saveFile.getShareData("timeTxt", Person_Play.this).split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis()); //设置日历的时间，主要是让日历的年月日和当前同步
        // 这里时区需要设置一下，不然会有8个小时的时间差
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        calendarWeek(calendar);//设置星期
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timelegth[0]));//时
        calendar.set(Calendar.MINUTE, Integer.parseInt(timelegth[1]));//分
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        // 选择的定时时间
//        long firstTime = SystemClock.elapsedRealtime(); // 开机之后到现在的运行时间(包括睡眠时间)
//        long systemTime = System.currentTimeMillis();
//        long selectTime = calendar.getTimeInMillis();
//        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
//        if (systemTime > selectTime) {
//            Toast.makeText(Person_Play.this, "设置的时间小于当前时间", Toast.LENGTH_SHORT).show();
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//            selectTime = calendar.getTimeInMillis();
//        }
//        // 计算现在时间到设定时间的时间差
//        long time = selectTime - systemTime;
//        firstTime += time;


        Intent intent = new Intent(Person_Play.this, AlarmReceiver.class);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);//添加此Flag 的广播可以被停止状态的应用接收到
//        PendingIntent sender = PendingIntent.getBroadcast(Person_Play.this, 0, intent, 0);
//        // 进行闹铃注册
//        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        initColockTime();//每次定闹钟设定倒计时时间
        calendarWeek(timelegth, this);//周期闹钟

    }

    //    AlarmReceiver alarmReceiver;
//    /*动态注册需在Acticity生命周期onPause通过
//    *unregisterReceiver()方法移除广播接收器，
//    * 优化内存空间，避免内存溢出
//    */
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(alarmReceiver);
//    }

    private void initColockTime() {
        String stopCount;
        String stopString = saveFile.getShareData("stopString", Person_Play.this);
        if (stopString.equals("关闭") || stopString.equals("false")) {
//            stopCount = "关闭";
            stopCount = 10 * 60 * 1000 + "";
        } else {
//            stopCount = Long.parseLong(saveFile.getShareData("stopString", Person_Play.this).substring(0, 2)) * 3000 + "";
            stopCount = Long.parseLong(saveFile.getShareData("stopString", Person_Play.this).substring(0, 2)) * 60 * 1000 + "";
        }
        saveFile.saveShareData("stopCount", stopCount, Person_Play.this);
    }


    public static void calendarWeek(String[] timelegth, Context context) {
        Calendar calendar = Calendar.getInstance();
        // 这里时区需要设置一下，不然会有8个小时的时间差
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfWeek -= 1;
        List<String> myChoice = saveFile.getUserList(context, "choiceWeek");
        for (int i = 0; i < myChoice.size(); i++) {
            if (myChoice.get(i).equals("true")) {
                int weeki = i;
                if (i == 0) {
                    weeki = 7;
                }
                AlarmManagerUtil.setAlarm(context, 0, Integer.parseInt(timelegth[0]), Integer.parseInt(timelegth[1]), i, weeki, "闹钟响了", 1);
            }
        }
    }



}

