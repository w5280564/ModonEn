package com.moying.energyring.myAcativity.Pk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.Community_Status_Model;
import com.moying.energyring.Model.newPk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Pk.Training.TrainingTodaySet;
import com.moying.energyring.myAcativity.PostingActivity;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Pk_DayPk_Community extends FragmentActivity {

    private SimpleDraweeView project_simple, join_project_simple;
    private TextView head_Txt, project_Name, project_Count, project_join, join_project_Join, join_project_Name, join_project_Tag, lei_Txt, lian_Txt, title_Txt;
    private View notjoined_Include, community_joined;
    private String ProjectID;
    private Button report_Btn;
    private RelativeLayout head_Rel;
    private ImageView join_grade_Img, join_si_Img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pk_daypk_community);

        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
//        initTitle();
        initView();

        initLocaData();
        tabViewSetView();
        resetTablayout();


    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this, R.color.colorFristWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this, R.color.colorFristBlack));
        cententtxt.setText("");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);


    }


    private ViewPager Slideviewpager;
    private TabLayout tablayout;

    private void initView() {
        tablayout = (TabLayout) findViewById(R.id.ac_tab_layout);
//        StaticData.ViewScale(tablayout, 700, 82);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);
        Button return_Btn = (Button) findViewById(R.id.return_Btn);
        title_Txt = (TextView) findViewById(R.id.title_Txt);

        StaticData.ViewScale(return_Btn, 80, 88);

        notjoined_Include = findViewById(R.id.community_notjoined);
        layoutmarginTop(this, notjoined_Include);
        project_simple = (SimpleDraweeView) notjoined_Include.findViewById(R.id.project_simple);
        head_Txt = (TextView) notjoined_Include.findViewById(R.id.head_Txt);
        project_Name = (TextView) notjoined_Include.findViewById(R.id.project_Name);
        project_Count = (TextView) notjoined_Include.findViewById(R.id.project_Count);
        project_join = (TextView) notjoined_Include.findViewById(R.id.project_join);
        StaticData.ViewScale(notjoined_Include, 0, 228);
        StaticData.ViewScale(project_simple, 150, 150);
        StaticData.ViewScale(project_join, 364, 50);

        report_Btn = (Button) findViewById(R.id.report_Btn);
        community_joined = findViewById(R.id.community_joined);
        layoutmarginTop(this, community_joined);
        head_Rel = (RelativeLayout) community_joined.findViewById(R.id.head_Rel);
        join_project_simple = (SimpleDraweeView) community_joined.findViewById(R.id.join_project_simple);
        join_project_Join = (TextView) community_joined.findViewById(R.id.join_project_Join);
        join_project_Name = (TextView) community_joined.findViewById(R.id.join_project_Name);
        join_grade_Img = (ImageView) community_joined.findViewById(R.id.join_grade_Img);
        join_si_Img = (ImageView) community_joined.findViewById(R.id.join_si_Img);
        join_project_Tag = (TextView) community_joined.findViewById(R.id.join_project_Tag);
        View ka_Lin = community_joined.findViewById(R.id.ka_Lin);
        lei_Txt = (TextView) community_joined.findViewById(R.id.lei_Txt);
        lian_Txt = (TextView) community_joined.findViewById(R.id.lian_Txt);
        View right_arrow = community_joined.findViewById(R.id.right_arrow);
        View lian_Rel = community_joined.findViewById(R.id.lian_Rel);
        StaticData.ViewScale(community_joined, 0, 256);
        StaticData.ViewScale(head_Rel, 0, 140);
        StaticData.ViewScale(join_grade_Img, 56, 28);
        StaticData.ViewScale(join_si_Img, 28, 28);
        StaticData.ViewScale(ka_Lin, 0, 116);
        StaticData.ViewScale(right_arrow, 16, 30);

        return_Btn.setOnClickListener(new return_Btn());
        project_join.setOnClickListener(new project_join());
        ka_Lin.setOnClickListener(new ka_Lin());
//        lian_Rel.setOnClickListener(new lian_Rel());
        report_Btn.setOnClickListener(new report_Btn());


        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.mAppBarLayout);

        //AppBar不能滑动整体页面
        setAppBarDragging(mAppBarLayout, true);
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class project_join extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            AddData();
        }
    }

    private class ka_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Pk_DayPk_Community.this, Pk_DayPk_Community_History.class);
            intent.putExtra("ProjectID", ProjectID);
            startActivity(intent);
        }
    }

    newPk_Model.DataBean projectModel;

    private class report_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Community_Status_Model.DataBean oneData = baseModel.getData();

            if (oneData.getProjectName().equals("健康走")) {
                Intent intent = new Intent(Pk_DayPk_Community.this, PostingActivity.class);
                intent.putExtra("ProjectID", ProjectID);
                startActivity(intent);
            } else if (oneData.isIs_Train()) {
                Intent intent1 = new Intent(Pk_DayPk_Community.this, TrainingTodaySet.class);
                intent1.putExtra("ProjectID", oneData.getProjectID() + "");
                startActivity(intent1);
            } else {
                projectModel = new newPk_Model.DataBean();
                projectModel.setProjectID(oneData.getProjectID());
                projectModel.setFilePath(oneData.getFilePath());
                projectModel.setProjectName(oneData.getProjectName());
                projectModel.setProjectUnit(oneData.getProjectUnit());
                Intent intent = new Intent(Pk_DayPk_Community.this, Pk_AddReport.class);
                intent.putExtra("projectModel", projectModel);
                startActivity(intent);
            }


        }
    }

    private void layoutmarginTop(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 100);
        Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Params.setMargins(0, pad, 0, 0);
        view.setLayoutParams(Params);
    }

    /**
     * AppBar不能滑动整体页面
     *
     * @param appBarLayout
     * @param newValue
     */
    private void setAppBarDragging(AppBarLayout appBarLayout, final boolean newValue) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return newValue;
            }
        });
        params.setBehavior(behavior);
    }


    //社区状态
    private void StatusData() {
        Community_StatusData(Pk_DayPk_Community.this, saveFile.BaseUrl + saveFile.Community_Status_Url + "?ProjectID=" + ProjectID);
    }

    //加入社区
    private void AddData() {
        Community_AddData(Pk_DayPk_Community.this, saveFile.BaseUrl + saveFile.Community_Add_Url + "?ProjectID=" + ProjectID);
    }

    public List<String> userArr;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;

    private void initLocaData() {
//        tablayout.setTabTextColors(ContextCompat.getColor(this,R.color.colorFristBlack), ContextCompat.getColor(this,R.color.colorSecondBlack));//初始颜色，选中颜色
//        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorFristBlack));//进度条颜色
        tablayout.setTabMode(TabLayout.GRAVITY_CENTER);//设置可以滑动 平分居中
//        tablayout.setTabMode(TabLayout.GRAVITY_CENTER);
        tablayout.setSelectedTabIndicatorHeight(0);//去掉下导航条

        userArr = new ArrayList<>();
        userArr.add("人气");
        userArr.add("最新");
        userArr.add("排行榜");

//        tablayout.setSelectedTabIndicatorHeight(0);//去掉下导航条
        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        String UserID = saveFile.getShareData("userId", this);
        fragments.add(Pk_DayPk_Community_PerNew.newInstance(1 + "", UserID + "", ProjectID));
        fragments.add(Pk_DayPk_Community_PerNew.newInstance(2 + "", UserID + "", ProjectID));
//        fragments.add(PersonMyCenter_AndOther_Photo.newInstance(8 + "", UserID + ""));
        fragments.add(Pk_DayPk_Communit_RankFragment.newInstance(0 + "", ProjectID + ""));
    }

    private void tabViewSetView() {
        myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        Slideviewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        tablayout.setupWithViewPager(Slideviewpager);
        reflex(tablayout);//修改下划线宽度
//        Slideviewpager.setCurrentItem(1);
//        Slideviewpager.addOnPageChangeListener(this);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                tab_Name.setTextColor(ContextCompat.getColor(Pk_DayPk_Community.this, R.color.colorFristBlack));
                ImageView tab_Img = (ImageView) tab.getCustomView().findViewById(R.id.tab_Img);
                tab_Img.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                tab_Name.setTextColor(ContextCompat.getColor(Pk_DayPk_Community.this, R.color.colorSecondBlack));
                ImageView tab_Img = (ImageView) tab.getCustomView().findViewById(R.id.tab_Img);
                tab_Img.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    private void resetTablayout() {
        /**
         * 使用tablayout + viewpager时注意 如果设置了setupWithViewPager
         * 则需要重新执行下方对每个条目赋值
         * 否则会出现icon文字不显示的bug
         */
        for (int i = 0; i < myAdapter.getCount(); i++) {
            int postion = i;
            tablayout.getTabAt(postion).setText(userArr.get(postion));
            TabLayout.Tab tab = tablayout.getTabAt(postion);
            if (tab != null) {
                tab.setCustomView(myAdapter.getTabView(postion));
                if (i == 0) {
                    TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                    tab_Name.setTextColor(ContextCompat.getColor(Pk_DayPk_Community.this, R.color.colorFristBlack));
                    ImageView tab_Img = (ImageView) tab.getCustomView().findViewById(R.id.tab_Img);
                    tab_Img.setVisibility(View.VISIBLE);
                }
            }
        }

        Intent intent = getIntent();
        String tabType = intent.getStringExtra("tabType");
        if (tabType != null) {
            Slideviewpager.setCurrentItem(Integer.parseInt(tabType), true);
        } else {
            Slideviewpager.setCurrentItem(0, true);
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragments = list;
        }


        public View getTabView(int position) {
            View v = LayoutInflater.from(Pk_DayPk_Community.this).inflate(R.layout.personandother_custom_tab, null);
            LinearLayout tab_Lin = (LinearLayout) v.findViewById(R.id.tab_Lin);
//            StaticData.ViewScale(tab_Lin, 200, 110);
            TextView tab_Name = (TextView) v.findViewById(R.id.tab_Name);
            tab_Name.setText(userArr.get(position));
            ImageView tab_Img = (ImageView) v.findViewById(R.id.tab_Img);
            StaticData.ViewScale(tab_Img, 50, 5);
            return v;
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }


    Community_Status_Model baseModel;

    public void Community_StatusData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    baseModel = new Gson().fromJson(resultString, Community_Status_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Community_Status_Model.DataBean oneData = baseModel.getData();

                        title_Txt.setText("#" + oneData.getProjectName());
                        if (oneData.getIs_Join() == 0) {
                            notjoined_Include.setVisibility(View.VISIBLE);
                            community_joined.setVisibility(View.GONE);
                            report_Btn.setVisibility(View.GONE);

                            if (oneData.getProfilePicture() != null) {
                                Uri imgUri = Uri.parse(oneData.getProfilePicture());
                                project_simple.setImageURI(imgUri);
                            } else {
                                StaticData.lodingheadBg(project_simple);
                            }
                            head_Txt.setText(oneData.getProjectName());
                            project_Name.setText("坚持" + oneData.getProjectName());
                            project_Count.setText(oneData.getJoin_Num() + "人正在坚持");
                            if (oneData.getJoin_Num() == 0) {
                                project_Count.setVisibility(View.INVISIBLE);
                            }
                            project_join.setText("加入#" + oneData.getProjectName());

                        } else if (oneData.getIs_Join() == 1) {
                            notjoined_Include.setVisibility(View.GONE);
                            community_joined.setVisibility(View.VISIBLE);


                            if (oneData.getProjectName().equals("健康走")) {
                                report_Btn.setText("发布图文动态");

//                                lei_Txt.setText(oneData.getReportNum());

                                double leiStr = oneData.getReportNum();
                                NumberFormat nf = new DecimalFormat("#.#");//# 0不显示
                                String zouNf = "";
                                if (leiStr > 1000) {
                                    zouNf = nf.format(leiStr / 1000) + "K";
                                } else {
                                    zouNf = nf.format(leiStr);
                                }
                                lei_Txt.setText(zouNf + oneData.getProjectUnit());

                            } else if (oneData.isIs_Train()) {
                                report_Btn.setText("打卡");
                                lei_Txt.setText(oneData.getReportFre() + "天");
                            } else {
                                report_Btn.setText("打卡");
                                lei_Txt.setText(oneData.getReportFre() + "天");
                            }

                            report_Btn.setVisibility(View.VISIBLE);

                            if (oneData.getProfilePicture() != null) {
                                Uri imgUri = Uri.parse(oneData.getProfilePicture());
                                join_project_simple.setImageURI(imgUri);
                            } else {
                                StaticData.lodingheadBg(join_project_simple);
                            }
                            join_project_Join.setText(oneData.getMaxContinueDays() + "天");
                            join_project_Name.setText(oneData.getNickName());
                            join_project_Tag.setText("#" + oneData.getProjectName());
//                            lei_Txt.setText(oneData.getReportFre() + "天");
                            lian_Txt.setText(oneData.getReport_Days() + "天");

                            StaticData.setGarde(join_grade_Img, oneData.getIntegralLevel());
                            if (oneData.getPrivacy() == 1) {
                                join_si_Img.setImageResource(R.drawable.privacy_one);
                            } else if (oneData.getPrivacy() == 2) {
                                join_si_Img.setImageResource(R.drawable.privacy_two);
                            } else if (oneData.getPrivacy() == 3) {
                                join_si_Img.setImageResource(R.drawable.privacy_three);
                            }


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


    public void Community_AddData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model baseModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(context, "加入成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, PK_Calner_Set.class);
                        intent.putExtra("ProjectID", ProjectID + "");
                        startActivity(intent);

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


    public void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

//                    int dp10 = dip2px(tabLayout.getContext(), 5);
                    int mag = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_DayPk_Community.this)) * 13);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = mag;
                        params.rightMargin = mag;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
