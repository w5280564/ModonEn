package com.moying.energyring.myAcativity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.basePopup.popupFour;
import com.moying.energyring.myAcativity.Person.Service.BindService;
import com.moying.energyring.myAcativity.Person.Service.DaemonService;
import com.moying.energyring.myAcativity.Person.Service.JobSchedulerService;
import com.moying.energyring.myAcativity.Pk.Committ.Leran_AllPerson;
import com.moying.energyring.myAcativity.Pk.Pk_DayPkAdd_More;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BaseActivity;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.waylenBaseView.MyArcMenu;
import com.umeng.analytics.MobclickAgent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;



@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final int[] ITEM_DRAWABLES = {R.drawable.arcmenu_add, R.drawable.arcmenu_pk, R.drawable.arcmenu_growing,
            R.drawable.arcmenu_goal};
    //    TextView popup_Txt;
    RadioGroup tab_group;
    private RadioButton tab_energy, tab_pk, tab_find, tab_person;
    public List<Fragment> fragments;
    @ViewInject(R.id.popup_Txt)
    TextView popup_Txt;


//   public static Activity mainActivitySta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中
//        setContentView(R.layout.activity_main);


        x.view().inject(this);
        initView();

        MyArcMenu mArcMenu = (MyArcMenu) findViewById(R.id.id_arcmenu);
        initArcMenu(mArcMenu, ITEM_DRAWABLES);

        popup_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {

            }
        });

//        SelfStarting_Manage.jumpStartInterface(this);

//        startService(new Intent(this, DaemonService.class));
//        initJobScheduler();

    }


    @Override
    protected void onResume() {
        super.onResume();
        initAlarmService();
        MobclickAgent.onResume(this); //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initAlarmService() {
        startService(new Intent(this, DaemonService.class));//启动闹钟服务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //JobScheduler
            initJobScheduler();
        }

        //绑定闹钟服务
        Intent intent = new Intent(this, DaemonService.class);
        intent.setAction("android.intent.action.DaemonServiceClock");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private JobScheduler mJobScheduler;
    private void  initJobScheduler(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), JobSchedulerService.class.getName()));

            builder.setPeriodic(60*1000); //每隔60秒运行一次
//            builder.setPeriodic(30000); //每隔30秒运行一次
//            Log.e("tttttttttt","30秒重新注册");
            builder.setRequiresCharging(true);// 设置是否充电的条件,默认false
            builder.setPersisted(true);  //设置设备重启后，是否重新执行任务
            builder.setRequiresDeviceIdle(true);// 设置手机是否空闲的条件,默认false
//            builder.setOverrideDeadline(5000);// 设置deadline，若到期还没有达到规定的条件则会开始执行
            if (mJobScheduler.schedule(builder.build()) <= 0) {
//                Log.e("tttttttttt","服务出问题");
                //If something goes wrong
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        try {//判断是否有闹钟，没有则关闭闹钟服务

//            String alarm = localPreferencesHelper.getString(LocalPreferencesHelper.ALARM_CLOCK);
//
//            if (daemonService != -1 && mIRemoteService != null) {
////                android.os.Process.killProcess(daemonService);
//                mIRemoteService.resetAlarm();
//            }
//
//            if (!alarm.equals("[]")) {
//                if (daemonService != -1) {
//                    startService(new Intent(this, DaemonService.class));
//                }
//            } else {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    mJobScheduler.cancel(1);
//                }
//
//            }
            unbindService(mConnection); //解除绑定服务。
//        } catch (Exception e) {
//
//        }
    }
    /** Called when the activity is first created. */
    private BindService myService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((BindService.MyBinder) service).getService();
//            System.out.println("Service连接成功");
            // 执行Service内部自己的方法
            myService.excute();
        }
    };



    private void initArcMenu(final MyArcMenu menu, int[] itemDrawables) {
        menu.setmMargin(10);
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(itemDrawables[i]);
            item.setTag(i);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 100);
            StaticData.layoutParamsScale(layoutParams, 80, 80);
            item.setLayoutParams(layoutParams);

            final int position = i;
            menu.addView(item);
        }
        menu.setOnMenuItemClickListener(new MyArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                if (saveFile.getShareData("islogin", MainActivity.this).equals("false")) {
                    Intent intent = new Intent(MainActivity.this, LoginRegister.class);
                    startActivity(intent);
//                            tab_energy.setChecked(true);
                } else {
                    switch (pos) {
                        case 0:
                            MobclickAgent.onEvent(MainActivity.this, "Pk_DayPkAdd");//统计页签
//                            Intent intent1 = new Intent(MainActivity.this, Pk_DayPkAdd.class);
                            Intent intent1 = new Intent(MainActivity.this, Pk_DayPkAdd_More.class);
                            startActivity(intent1);
                            break;
                        case 1:
                            MobclickAgent.onEvent(MainActivity.this, "PostAdd");//统计页签
                            Intent intent2 = new Intent(MainActivity.this, PostingActivity.class);
                            startActivity(intent2);
                            break;
                        case 2:
                            MobclickAgent.onEvent(MainActivity.this, "AllPerson");//统计页签
                            Intent intent3 = new Intent(MainActivity.this, Leran_AllPerson.class);
                            startActivity(intent3);
                            break;
                    }
                }
 //                Toast.makeText(MainActivity.this, view.getTag() + "; position :" + pos, Toast.LENGTH_SHORT).show();
            }
        });
        menu.setStatusChange(new MyArcMenu.StatusChange() {
            @Override
            public void arcMenuStatus(MyArcMenu.Status mStatus) {
                menu.setBackgroundColor(mStatus == MyArcMenu.Status.OPEN ? Color.parseColor("#80000000") : Color.TRANSPARENT);
            }
        });
    }





    private void initView() {
        fragments = new ArrayList<>();
        fragments.add(new Fragment1_Energy());
        fragments.add(new Fragment2_Pk());
//        fragments.add(new Fragment3_Find());
        fragments.add(new Fragment3_FindTest());
        fragments.add(new Fragment4_Person());

        tab_group = (RadioGroup) findViewById(R.id.tab_group);
        StaticData.ViewScale(tab_group, 0, 98);
        tab_energy = (RadioButton) findViewById(R.id.tab_energy);
        tab_pk = (RadioButton) findViewById(R.id.tab_pk);
        RadioButton tab_add = (RadioButton) findViewById(R.id.tab_add);
        tab_find = (RadioButton) findViewById(R.id.tab_find);
        tab_person = (RadioButton) findViewById(R.id.tab_person);
        StaticData.ViewScale(tab_energy, 150, 98);
        StaticData.ViewScale(tab_pk, 150, 98);
        StaticData.ViewScale(tab_add, 150, 98);
        StaticData.ViewScale(tab_find, 150, 98);
        StaticData.ViewScale(tab_person, 150, 98);

        tab_group.setOnCheckedChangeListener(new tab_group());
        tab_energy.setChecked(true);
    }

    public class tab_group implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

            switch (i) {
                case R.id.tab_energy:
                    addFragmentStack(fragments, R.id.main_content_layout, 0);//加载不同的fragment
                    break;
                case R.id.tab_pk:
                    if (saveFile.getShareData("islogin", MainActivity.this).equals("false")) {
                        Intent intent = new Intent(MainActivity.this, LoginRegister.class);
                        startActivity(intent);
                        tab_energy.setChecked(true);
                    } else {
                        addFragmentStack(fragments, R.id.main_content_layout, 1);
                    }
                    break;
                case R.id.tab_find:
                    if (saveFile.getShareData("islogin", MainActivity.this).equals("false")) {
                        Intent intent = new Intent(MainActivity.this, LoginRegister.class);
                        startActivity(intent);
                        tab_energy.setChecked(true);
                    } else {
                        addFragmentStack(fragments, R.id.main_content_layout, 2);
                    }
                    break;
                case R.id.tab_person:
                    if (saveFile.getShareData("islogin", MainActivity.this).equals("false")) {
                        Intent intent = new Intent(MainActivity.this, LoginRegister.class);
                        startActivity(intent);
                        tab_energy.setChecked(true);
                    } else {
                        addFragmentStack(fragments, R.id.main_content_layout, 3);
                    }
                    break;
            }
        }
    }

    /**
     * 回转到上一次的fragment
     */
    public void backFragment(int pos) {
        ((RadioButton) tab_group.getChildAt(pos)).setChecked(true);
        addFragmentStack(fragments, R.id.main_content_layout, pos);//加载不同的fragment
    }

    public Fragment3_FindTest getFragmentFindTest(){
        return (Fragment3_FindTest) fragments.get(2);
    }


//    @Event(type = View.OnClickListener.class, value = R.id.popup_Txt)
//    public void onClick(View v) {
//        showPopup();
//    }


    public void showPopup() {
//         popupOne popup = new popupOne(MainActivity.this, popup_Txt,"内容");
//        final popupTwo popup = new popupTwo(MainActivity.this, popup_Txt,"标题","内容");
//        popupThree popup = new popupThree(MainActivity.this, popup_Txt,"标题","内容");
        popupFour popup = new popupFour(MainActivity.this, popup_Txt, "标题", "内容");
//        TextView content_Txt = (TextView) popup.getContentView().findViewById(R.id.content_Txt);
//        content_Txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                popup.dismiss();
//            }
//        });
    }



}
