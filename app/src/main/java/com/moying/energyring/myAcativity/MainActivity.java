package com.moying.energyring.myAcativity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.isFristSee_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Person.Service.BindService;
import com.moying.energyring.myAcativity.Person.Service.DaemonService;
import com.moying.energyring.myAcativity.Person.Service.JobSchedulerService;
import com.moying.energyring.myAcativity.Pk.Committ.Leran_AllPerson;
import com.moying.energyring.myAcativity.Pk.Pk_DayPkAdd_More;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BaseActivity;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.waylenBaseView.MyArcMenu;
import com.today.step.lib.ISportStepInterface;
import com.today.step.lib.StepAlertManagerUtils;
import com.today.step.lib.VitalityStepService;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


//@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final int[] ITEM_DRAWABLES = {R.drawable.arcmenu_add, R.drawable.arcmenu_pk, R.drawable.arcmenu_growing,
            R.drawable.arcmenu_goal};
    //    TextView popup_Txt;
    RadioGroup tab_group;
    private RadioButton tab_energy, tab_pk, tab_find, tab_person;
    public List<Fragment> fragments;


    private static final int REFRESH_STEP_WHAT = 0;
    //循环取当前时刻的步数中间的间隔时间
    private long TIME_INTERVAL_REFRESH = 500;
    private int mStepSum;
    private Handler mDelayHandler = new Handler(new TodayStepCounterCall());
    private ISportStepInterface iSportStepInterface;
    private TextView unrend_Txt;


//   public static Activity mainActivitySta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中


//        x.view().inject(this);
        initView();

        MyArcMenu mArcMenu = (MyArcMenu) findViewById(R.id.id_arcmenu);
        initArcMenu(mArcMenu, ITEM_DRAWABLES);

//        popup_Txt.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            protected void onNoDoubleClick(View v) {
//
//            }
//        });

//        SelfStarting_Manage.jumpStartInterface(this);

//        startService(new Intent(this, DaemonService.class));
//        initJobScheduler();

        initData();
        stepCount();//计步服务
    }


    @Override
    protected void onResume() {
        super.onResume();

//        initAlarmService();
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

    private void initJobScheduler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), JobSchedulerService.class.getName()));

            builder.setPeriodic(60 * 1000); //每隔60秒运行一次
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

    private void initData() {
        guideFristData(this, saveFile.BaseUrl + saveFile.GuidePerFirst_Url);
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

//            unbindService(mConnection); //解除绑定服务。
//        } catch (Exception e) {
//
//        }
    }

   public isFristSee_Model isFristModel;
    public void guideFristData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    isFristModel = new Gson().fromJson(resultString, isFristSee_Model.class);
                    if (isFristModel.isIsSuccess()) {

                        hasMesData(context, saveFile.BaseUrl + saveFile.NoticeHasMes_Url);

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
public void hasMesData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    BaseDataInt_Model baseModel = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                    if (baseModel.isIsSuccess()) {

                        if (isFristModel.getData().isIs_FirstEditProfile_Remind() || saveFile.getShareData("isGuidePer", context).equals("false")) {
                            unrend_Txt.setVisibility(View.VISIBLE);
                        } else {
                            unrend_Txt.setVisibility(View.INVISIBLE);
                        }



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


    /**
     * Called when the activity is first created.
     */
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
        unrend_Txt = (TextView) findViewById(R.id.unrend_Txt);

//        StaticData.ViewScale(unrend_Txt,18,18);
        unReadMargin(MainActivity.this, unrend_Txt);

        fragments = new ArrayList<>();
//        fragments.add(new Fragment2_Pk());
        fragments.add(new FragmentNew_Pk());
        fragments.add(new Fragment1_Energy());
//        fragments.add(new Fragment3_Find());
        fragments.add(new Fragment3_FindTest());
        fragments.add(new Fragment4_PersonNew());

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
        tab_pk.setChecked(true);
    }

    private void unReadMargin(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        StaticData.layoutParamsScale(Params, 18, 18);
        Params.addRule(RelativeLayout.ALIGN_TOP, R.id.tab_group);
        Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 30);
        Params.setMargins(0, 0, pad, 0);
        view.setLayoutParams(Params);
    }

    public class tab_group implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            switch (i) {
                case R.id.tab_pk:
                    if (saveFile.getShareData("islogin", MainActivity.this).equals("false")) {
                        Intent intent = new Intent(MainActivity.this, LoginRegister.class);
                        startActivity(intent);
                        tab_pk.setChecked(true);
                    } else {
                        addFragmentStack(fragments, R.id.main_content_layout, 0);
                    }
                    break;
                case R.id.tab_energy:
                    if (saveFile.getShareData("islogin", MainActivity.this).equals("false")) {
                        Intent intent = new Intent(MainActivity.this, LoginRegister.class);
                        startActivity(intent);
                    } else {
                        addFragmentStack(fragments, R.id.main_content_layout, 1);//加载不同的fragment
                    }
                    break;
                case R.id.tab_find:
                    if (saveFile.getShareData("islogin", MainActivity.this).equals("false")) {
                        Intent intent = new Intent(MainActivity.this, LoginRegister.class);
                        startActivity(intent);
                        tab_pk.setChecked(true);
                    } else {
                        addFragmentStack(fragments, R.id.main_content_layout, 2);
                    }
                    break;
                case R.id.tab_person:
                    if (saveFile.getShareData("islogin", MainActivity.this).equals("false")) {
                        Intent intent = new Intent(MainActivity.this, LoginRegister.class);
                        startActivity(intent);
                        tab_pk.setChecked(true);
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

    public Fragment3_FindTest getFragmentFindTest() {
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
//        popupFour popup = new popupFour(MainActivity.this, popup_Txt, "标题", "内容");
//        TextView content_Txt = (TextView) popup.getContentView().findViewById(R.id.content_Txt);
//        content_Txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                popup.dismiss();
//            }
//        });
    }

    public void setTabChange(int pos) {
        ((RadioButton) tab_group.getChildAt(pos)).setChecked(true);
//        tab_person.setChecked(true);
    }

    public void setVisi(int setID) {
        if (setID == 0) {
            unrend_Txt.setVisibility(View.VISIBLE);
        } else {
            unrend_Txt.setVisibility(View.GONE);
        }
    }


    //计步定时服务 计步服务
    public void stepCount() {
        StepAlertManagerUtils.set0SeparateAlertManager(getApplication());
        final Intent intent = new Intent(this, VitalityStepService.class);
        startService(intent);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iSportStepInterface = ISportStepInterface.Stub.asInterface(service);
                try {
                    mStepSum = iSportStepInterface.getCurrTimeSportStep();
                    saveFile.saveShareData("myStep", mStepSum + "", MainActivity.this);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH);

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    class TodayStepCounterCall implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_STEP_WHAT: {

                    if (null != iSportStepInterface) {
                        int step = 0;
                        try {
                            step = iSportStepInterface.getCurrTimeSportStep();

                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        if (mStepSum != step && step != 0) {
                            mStepSum = step;
                            saveFile.saveShareData("myStep", mStepSum + "", MainActivity.this);
//                            updateStepCount();
                        }
                    }
                    mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH);

                    break;
                }

            }
            return false;
        }
    }

    // 获取是否存在NavigationBar

    private boolean checkDeviceHasNavigationBar(Context context) {

        boolean hasNavigationBar = false;

        Resources rs = context.getResources();

        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");

        if (id > 0) {

            hasNavigationBar = rs.getBoolean(id);

        }

        try {

            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");

            Method m = systemPropertiesClass.getMethod("get", String.class);

            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");

            if ("1".equals(navBarOverride)) {

                hasNavigationBar = false;

            } else if ("0".equals(navBarOverride)) {

                hasNavigationBar = true;

            }

        } catch (Exception e) {

        }

        return hasNavigationBar;

    }

    /**
     * 获取虚拟功能键高度
     */

    public int getVirtualBarHeigh() {

        int vh = 0;

        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();

        DisplayMetrics dm = new DisplayMetrics();

        try {

            @SuppressWarnings("rawtypes")

            Class c = Class.forName("android.view.Display");

            @SuppressWarnings("unchecked")

            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);

            method.invoke(display, dm);

            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return vh;

    }


}
