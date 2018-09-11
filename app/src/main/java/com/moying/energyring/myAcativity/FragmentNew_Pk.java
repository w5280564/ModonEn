package com.moying.energyring.myAcativity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.JiFenAndBadge_Model;
import com.moying.energyring.Model.Pk_MyIntegral_Model;
import com.moying.energyring.Model.ProjectModel;
import com.moying.energyring.Model.isFristSee_Model;
import com.moying.energyring.Model.myCheckData_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.GuideUtil;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Energy.HasNewActivity;
import com.moying.energyring.myAcativity.Person.Person_BadgeHas;
import com.moying.energyring.myAcativity.Person.Person_Commendation;
import com.moying.energyring.myAcativity.Pk.JiFenActivity;
import com.moying.energyring.myAcativity.Pk.Pk_CalnerFragment;
import com.moying.energyring.myAcativity.Pk.Pk_CheckIn;
import com.moying.energyring.myAcativity.Pk.Pk_DayPKAdd_ProjectSeek;
import com.moying.energyring.myAcativity.Pk.Pk_DayPk_Project_Detail_RankAll;
import com.moying.energyring.myAcativity.Pk.Pk_FenRankList;
import com.moying.energyring.network.saveFile;
import com.sunfusheng.marqueeview.MarqueeView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by waylen on 2017/10/19.
 */

public class FragmentNew_Pk extends Fragment implements  GuideUtil.RemoveListener {
    private View parentView;
    MarqueeView marqueeView;
    public static int guideID = 1001;
    public static int jifenID = 1002;
    GuideUtil guideUtil;
    private SimpleDraweeView my_simple;
    private TextView  fen_Txt;
    private ImageView grade_Img;

    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private String currentDate = "";
    private Calendar calendar;
    private Date date;
    private TextView calendar_Txt,my_Rank_Txt;
    private TabLayout ac_tab_layout;
    private ViewPager Slideviewpager;
    private ImageView left_Img, right_Img;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.framentnew_pk, null);

        initView(parentView);
//        initLocaData();
//        tabViewSetView();
        guideFristData(getActivity(), saveFile.BaseUrl + saveFile.GuidePerFirst_Url);//展示功能提醒页
        guideUtil = GuideUtil.getInstance();
        guideUtil.setRemoveListener(this);


        initDate();

        return parentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
//        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
//        marqueeView.stopFlipping();
    }

    private void initData() {
        checkData(saveFile.BaseUrl + saveFile.Check_Url);//是否签到
        myRankData(getActivity(), saveFile.BaseUrl + saveFile.My_Rank_Url);
//        String userId = saveFile.getShareData("userId", getActivity());
//        ListData(getActivity(), saveFile.BaseUrl + saveFile.DayPk_Url + "?UserID=" + userId);
    }

    //初始时间 今天
    private void initDate() {
        calendar = Calendar.getInstance();
        date = new Date();//当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = sdf.format(date);
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);

        String time = month_c + "月" + day_c + "日";
//       initXrecyData(time,0);
        calendar_Txt.setText(time);

        initLocaData(ac_tab_layout);
        tabViewSetView(ac_tab_layout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (RESULT_OK == resultCode) {
//            String guideId = data.getStringExtra("guideId");
////            jiFenmodel = data.getParcelableExtra("jiFenmodel");
//            guideFristData(getActivity(), saveFile.BaseUrl + saveFile.GuidePerFirst_Url, Integer.parseInt(guideId));//展示功能提醒页
//        } else
        if (resultCode == 1002) {
            if (!jiFenmodel.getData().get_Badge().isEmpty()) {
                Intent intent = new Intent(getActivity(), Person_BadgeHas.class);
                intent.putExtra("jiFenmodel", jiFenmodel);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
            }
        } else if (resultCode == 1003) {
            if (!jiFenmodel.getData().get_Praise().isEmpty()) {
                Intent intentSuccess = new Intent(getActivity(), Person_Commendation.class);
                intentSuccess.putExtra("jiFenmodel", jiFenmodel);
                startActivity(intentSuccess);
                getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
            }
        }
//        else if (resultCode == TixingResult) {
//            Intent intent = new Intent(getActivity(), JiFenActivity.class);
//            intent.putExtra("jifen", jiFenmodel.getData().getIntegral());
//            startActivityForResult(intent, 1002);
//        }

    }

    private void initView(View view) {
//        View rank_Txt = view.findViewById(R.id.rank_Txt);
        left_Img = (ImageView) view.findViewById(R.id.left_Img);
        right_Img = (ImageView) view.findViewById(R.id.right_Img);
        View calendar_Img = view.findViewById(R.id.calendar_Img);
        calendar_Txt = (TextView) view.findViewById(R.id.calendar_Txt);
        my_Rank_Txt = (TextView) view.findViewById(R.id.my_Rank_Txt);

        ac_tab_layout = (TabLayout) view.findViewById(R.id.ac_tab_layout);
        Slideviewpager = (ViewPager) view.findViewById(R.id.Slideviewpager);
        StaticData.ViewScale(ac_tab_layout, 0, 6);

        ImageView pk_ce_Img = (ImageView) view.findViewById(R.id.pk_ce_Img);
        StaticData.ViewScale(pk_ce_Img, 94, 308);
        StaticData.ViewScale(left_Img, 18, 30);
        StaticData.ViewScale(right_Img, 18, 30);
        StaticData.ViewScale(calendar_Img, 30, 30);
        pk_ce_Img.setOnClickListener(new pk_ce_Img());

        RelativeLayout title_rel = (RelativeLayout) view.findViewById(R.id.title_rel);
        View Pkbg_Img = view.findViewById(R.id.Pkbg_Img);
        my_simple = (SimpleDraweeView) view.findViewById(R.id.my_simple);
        grade_Img = (ImageView) view.findViewById(R.id.grade_Img);
        fen_Txt = (TextView) view.findViewById(R.id.fen_Txt);
        LinearLayout bang_Lin = (LinearLayout) view.findViewById(R.id.bang_Lin);
        ImageView bang_Img = (ImageView) view.findViewById(R.id.bang_Img);
        ImageView arrow_Img = (ImageView) view.findViewById(R.id.arrow_Img);

        StaticData.ViewScale(title_rel, 0, 264);
        StaticData.ViewScale(Pkbg_Img, 0, 152);
        StaticData.ViewScale(grade_Img, 56, 28);
        StaticData.ViewScale(my_simple, 138, 138);
        StaticData.ViewScale(bang_Img, 36, 36);
        StaticData.ViewScale(arrow_Img, 28, 24);

        bang_Lin.setOnClickListener(new bang_Lin());
        left_Img.setOnClickListener(new left_Img());
        right_Img.setOnClickListener(new right_Img());
        calendar_Txt.setOnClickListener(new calendar_Txt());
    }

    @Override
    public void RemoveListener(int index, boolean islast) {
        if (index == 0) {

            guideFristData(getActivity(), saveFile.BaseUrl + saveFile.GuidePerFirst_Url);//展示功能提醒页
        } else if (index == 1) {

            guideFristData(getActivity(), saveFile.BaseUrl + saveFile.GuidePerFirst_Url);//展示功能提醒页
        } else if (index == 2) {

            Intent intent1 = new Intent(getActivity(), HasNewActivity.class);
            startActivity(intent1);
        }
    }


    private class titlebg_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(),);
            Intent intent = new Intent(getActivity(), Pk_FenRankList.class);
            startActivity(intent);

//            Intent intentJiFen = new Intent(getActivity(), JiFenActivity.class);
//            intentJiFen.putExtra("media", "daypk");
//            intentJiFen.putExtra("jifen", 10);
//            intentJiFen.putExtra("RewardIntegral", 10+"");
//            startActivity(intentJiFen);
        }
    }

    private class check_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            check_Lin.setEnabled(false);
            AddData(saveFile.BaseUrl + saveFile.CheckAdd_Url);//签到
        }
    }

    private class pk_ce_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent1 = new Intent(getActivity(), Pk_DayPkAdd_More.class);
//            startActivityForResult(intent1, guideID);

//            Intent intent = new Intent(getActivity(), Pk_DayPKAdd_Project_Tab.class);
            Intent intent = new Intent(getActivity(), Pk_DayPKAdd_ProjectSeek.class);
            List<ProjectModel> projectModel = new ArrayList<>();
            intent.putExtra("baseModel", (Serializable) projectModel);
            //第二个参数为请求码，可以根据业务需求自己编号
//            startActivityForResult(intent, RESULT_CODE_MORE);
            startActivity(intent);
        }
    }

    private class marqueeView implements MarqueeView.OnItemClickListener {
        @Override
        public void onItemClick(int position, TextView textView) {
            Intent intent = new Intent(getActivity(), Pk_CheckIn.class);
            startActivity(intent);

//            String resultStr = "{\n" +
//                    "  \"IsSuccess\": true,\n" +
//                    "  \"Msg\": \"操作成功!\",\n" +
//                    "  \"Status\": 200,\n" +
//                    "  \"Data\": {\n" +
//                    "    \"Integral\": 10,\n" +
//                    "    \"RewardIntegral\": 10,\n" +
//                    "    \"_Badge\": [],\n" +
//                    "    \"_Praise\": null,\n" +
//                    "    \"DailyTask\": {\n" +
//                    "      \"TaskID\": 1,\n" +
//                    "      \"TaskName\": \"每日签到\",\n" +
//                    "      \"Summary\": \"啦啦啦啦啦绿绿绿\",\n" +
//                    "      \"Integral\": 30,\n" +
//                    "      \"Condition\": \"3\",\n" +
//                    "      \"BtnText\": \"去签到,已签到\"\n" +
//                    "    }\n" +
//                    "  }\n" +
//                    "}";
//            jiFenmodel = new Gson().fromJson(resultStr, JiFenAndBadge_Model.class);
//
//            Intent intent = new Intent(getActivity(), JiFenActivity.class);
//            intent.putExtra("jifen", jiFenmodel.getData().getIntegral());
//            intent.putExtra("DailyTask",jiFenmodel.getData().getDailyTask());
//            intent.putExtra("RewardIntegral", "10");
//            startActivityForResult(intent, jifenID);
        }
    }


    private class hui_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            view.setEnabled(false);

        }
    }

    private class rank_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Pk_DayPk_Project_Detail_RankAll.class);
//            intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
            startActivity(intent);
        }
    }

    private class bang_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Pk_DayPk_Project_Detail_RankAll.class);
//            intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
            startActivity(intent);
        }
    }

    private class left_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setImg(ac_tab_layout.getSelectedTabPosition() - 1 );

            if (ac_tab_layout.getSelectedTabPosition() == 0) {
            } else {
                int tag = ac_tab_layout.getSelectedTabPosition()- 1;
                ac_tab_layout.setScrollPosition(tag, 1F, true);//滑动到固定位置
                Slideviewpager.setCurrentItem(tag);
            }
        }
    }

    private class right_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setImg(ac_tab_layout.getSelectedTabPosition() + 1);
            if (ac_tab_layout.getSelectedTabPosition() == fragmentLength-1) {
            } else {
                int tag = ac_tab_layout.getSelectedTabPosition() + 1;
                ac_tab_layout.setScrollPosition(tag, 1F, true);//滑动到固定位置
                Slideviewpager.setCurrentItem(tag);
            }
        }
    }

    private void setImg(int pos){
        if (pos == 0){
            left_Img.setImageResource(R.drawable.pknew_left_gray);
            right_Img.setImageResource(R.drawable.pknew_right_select);
        }else if (pos == fragmentLength-1){
            right_Img.setImageResource(R.drawable.pknew_right);
            left_Img.setImageResource(R.drawable.pknew_left_select);
        }else if (pos > 0 && pos < (fragmentLength -1 )){
            left_Img.setImageResource(R.drawable.pknew_left_select);
            right_Img.setImageResource(R.drawable.pknew_right_select);
        }
    }
    private class calendar_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//           initDate();
            final int tag = userArr.size() - 1;
            setImg(tag);
            ac_tab_layout.setScrollPosition(tag, 1F, true);//滑动到固定位置
            Slideviewpager.setCurrentItem(tag);
        }
    }

    public List<String> userArr;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;
    int fragmentLength = 30;

    private void initLocaData(TabLayout myTab) {
        myTab.setTabTextColors(Color.parseColor("#0095a0ab"), Color.parseColor("#00ffd800"));//初始颜色，选中颜色
        myTab.setSelectedTabIndicatorColor(Color.parseColor("#ffd800"));//进度条颜色
        myTab.setTabMode(TabLayout.GRAVITY_CENTER);
        if (userArr != null) {
            userArr.clear();
        }
        userArr = new ArrayList<>();

        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();


        for (int i = 0; i < fragmentLength; i++) {
            userArr.add(i + "");
//            int ReportID = baseModel.getData().get(i).getReportID();
//            int ProjectID = baseModel.getData().get(i).getProjectID();
//            String projectName = baseModel.getData().get(i).getProjectName();

            fragments.add(Pk_CalnerFragment.newInstance(ChangeTime(i), 0 + ""));

        }
    }

    @SuppressLint("WrongConstant")
    private String ChangeTime(int index) {

        int changePos = fragmentLength - 1 - index;
        calendar.setTime(date);
        calendar.add(calendar.DATE, -changePos);//把日期往后增加.整数往后推,负数往前移动

        //增加后的时间
        year_c = calendar.get(Calendar.YEAR);
        month_c = calendar.get(Calendar.MONDAY) + 1;
        day_c = calendar.get(Calendar.DAY_OF_MONTH);


//        String time = year_c + "-" + month_c + "-" + day_c;

        return year_c + "-" + month_c + "-" + day_c;
    }

    int tabCount = 0;

    private void tabViewSetView(TabLayout myTab) {
        myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments);
        Slideviewpager.setAdapter(myAdapter);
        Slideviewpager.setOffscreenPageLimit(2);
        //将TabLayout和ViewPager关联。
        myTab.setupWithViewPager(Slideviewpager);
//        Slideviewpager.setCurrentItem(1);
//        Slideviewpager.addOnPageChangeListener(this);

        final int tag = userArr.size() - 1;
        setImg(tag);
        ac_tab_layout.setScrollPosition(tag, 1F, true);//滑动到固定位置
        Slideviewpager.setCurrentItem(tag);

//        if (projectId != null) {
//            int size = baseModel.getData().size();
//            for (int i = 0; i < size; i++) {
//                if (projectId.equals(baseModel.getData().get(i).getProjectID() + "")) {
//                    tabCount = i;
//                    setpkbg(i);
//                    break;
//                }
//            }
//        }

        ac_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                cententtxt.setText(baseModel.getData().get(tab.getPosition()).getProjectName());
                String time = ChangeTime(tab.getPosition());
                int month_c = Integer.parseInt(time.split("-")[1]);
                int day_c = Integer.parseInt(time.split("-")[2]);

                String nowTime = month_c + "月" + day_c + "日";
                calendar_Txt.setText(nowTime);
                setImg(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


//    private void resetTablayout(TabLayout myTab) {
//        /**
//         * 使用tablayout + viewpager时注意 如果设置了setupWithViewPager
//         * 则需要重新执行下方对每个条目赋值
//         * 否则会出现icon文字不显示的bug
//         */
//        for (int i = 0; i < myAdapter.getCount(); i++) {
//            int postion = i;
////            myTab.getTabAt(postion).setText(userArr.get(postion));
//            TabLayout.Tab tab = myTab.getTabAt(postion);
//            if (tab != null) {
////                tab.setCustomView(myAdapter.getTabView(postion));
//            }
//        }
//
//        Intent intent = getIntent();
//        String tabType = intent.getStringExtra("tabType");
//        if (tabType != null) {
//            Slideviewpager.setCurrentItem(Integer.parseInt(tabType), true);
//        } else {
//            Slideviewpager.setCurrentItem(1, true);
//            myTab.getTabAt(0).select();
//        }
//    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragments = list;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        // 设置tablayout标题
        @Override
        public CharSequence getPageTitle(int position) {
            return userArr.get(position);
        }

    }




    Base_Model check_Model;

    public void checkData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    check_Model = new Gson().fromJson(resultString, Base_Model.class);
                    if (check_Model.isData()) {
//                        myCheckData(saveFile.BaseUrl + saveFile.MyCheckIn_Url);
                    } else {
//                        check_Lin.setVisibility(View.VISIBLE);
//                        marqueeView.setVisibility(View.INVISIBLE);
                        AddData(saveFile.BaseUrl + saveFile.CheckAdd_Url);//签到
                    }

                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(getActivity(), MainLogin.class);
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

    JiFenAndBadge_Model jiFenmodel;

    public void AddData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    jiFenmodel = new Gson().fromJson(resultString, JiFenAndBadge_Model.class);

                    if (jiFenmodel.isIsSuccess()) {
//                        check_Lin.setVisibility(View.GONE);
//                        ScrollTextView.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getActivity(), JiFenActivity.class);
                        intent.putExtra("media", "check");
                        intent.putExtra("jifen", jiFenmodel.getData().getIntegral());
                        intent.putExtra("DailyTask", jiFenmodel.getData().getDailyTask());
                        startActivityForResult(intent, jifenID);
//                        startActivity(intent);
                        Toast.makeText(getActivity(), "签到成功", Toast.LENGTH_SHORT);
                        myCheckData(saveFile.BaseUrl + saveFile.MyCheckIn_Url);
                    } else {
                        Toast.makeText(getActivity(), "无法签到请检查网络", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(getActivity(), MainLogin.class);
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

    public void myCheckData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    myCheckData_Model model = new Gson().fromJson(resultString, myCheckData_Model.class);
                    if (model.isIsSuccess()) {
//                        check_Lin.setVisibility(View.GONE);
//                        marqueeView.setVisibility(View.VISIBLE);

                        List<String> info = new ArrayList<>();
                        info.add("连续签到" + model.getData().getContinueDays() + "天");
                        info.add("累计签到" + model.getData().getCheckInDays() + "天");
                        marqueeView.startWithList(info);

                    } else {
                        Toast.makeText(getActivity(), "无法签到请检查网络", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(getActivity(), MainLogin.class);
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


    public void guideFristData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    isFristSee_Model isFristModel = new Gson().fromJson(resultString, isFristSee_Model.class);
                    if (isFristModel.isIsSuccess()) {
                        if (!isFristModel.getData().isIs_CheckIn_Remind()) {
//                                initGuide(isFristModel.getData().isIs_CheckIn_Remind());
//                                updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_CheckIn_Remind");//展示功能提醒页
                            guideUtil.initGuide(getActivity(), 0, 1);
                            updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_CheckIn_Remind");//展示功能提醒页
                        } else if (!isFristModel.getData().isIs_FirstPool()) {
//                            Field[] isFrist  =  isFristModel.getData().getClass().getDeclaredFields();
//                            isFrist[0].getName()

//                            isFristSee();
//                            updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_FirstPool");//展示功能提醒页

                            guideUtil.initGuide(getActivity(), 1, 1);
                            updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_FirstPool");//展示功能提醒页
                        } else if (!isFristModel.getData().isIs_First_Train()) {
//                            Field[] isFrist  =  isFristModel.getData().getClass().getDeclaredFields();
//                            isFrist[0].getName()

//                            isFristSee();
//                            updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_FirstPool");//展示功能提醒页

                            guideUtil.initGuide(getActivity(), 2, 1);
                            updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_First_Train");//展示功能提醒页
                        }else {

                            Intent intent1 = new Intent(getActivity(), HasNewActivity.class);
                            startActivity(intent1);
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
                    Intent intent = new Intent(context, MainLogin.class);
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

    public void updguidePer_Data(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model Model = new Gson().fromJson(resultString, Base_Model.class);
                    if (Model.isIsSuccess()) {

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
                    Intent intent = new Intent(context, MainLogin.class);
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

    public void myRankData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Pk_MyIntegral_Model Integral_Model = new Gson().fromJson(resultString, Pk_MyIntegral_Model.class);
                    if (Integral_Model.isIsSuccess() && !Integral_Model.getData().equals("[]")) {
//                        my_Count_Txt.setText("积分" + Integral_Model.getData().getAllIntegral());
//                        my_rank_Txt.setText("第" + Integral_Model.getData().getRanking() + "名");
//                        if (Integral_Model.getData().getWorld() == 0) {
//                            my_change_Img.setVisibility(View.INVISIBLE);
//                            my_change_Txt.setVisibility(View.INVISIBLE);
//                        } else if (Integral_Model.getData().getWorld() < 0) {
//                            my_change_Img.setImageResource(R.drawable.change_down);
//                            my_change_Txt.setTextColor(Color.parseColor("#FF0100"));
//                            my_change_Txt.setText(Integral_Model.getData().getWorld() + "");
//                        } else if (Integral_Model.getData().getWorld() > 0) {
//                            my_change_Img.setImageResource(R.drawable.change_update);
//                            my_change_Txt.setTextColor(Color.parseColor("#0BC10B"));
//                            my_change_Txt.setText(Integral_Model.getData().getWorld() + "");
//                        }

                        my_Rank_Txt.setText("今日排名" + Integral_Model.getData().getRanking() + "名");

                        StaticData.setGarde(grade_Img, Integral_Model.getData().getIntegralLevel());
                        fen_Txt.setText("积分：" + Integral_Model.getData().getAllIntegral());

                        if (Integral_Model.getData().getProfilePicture() != null) {
                            Uri contentUri = Uri.parse(Integral_Model.getData().getProfilePicture());
                            my_simple.setImageURI(contentUri);
                        } else {
                            StaticData.lodingheadBg(my_simple);
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
                    Intent intent = new Intent(context, MainLogin.class);
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



}
