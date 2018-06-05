package com.moying.energyring.myAcativity.Pk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.DayPkDetail_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.PostingActivity;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Pk_DayPkDetail extends FragmentActivity {
    private String titleName, imgPath;
    private View my_tab_Lin;
    private TabLayout ac_tab_layout;
    private View pos_Txt;
    private ViewPager Slideviewpager;
    private String projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeprice);
        setContentView(R.layout.activity_pk__daypk_detail);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        Intent intent = getIntent();
        titleName = intent.getStringExtra("titleName");
        projectId = intent.getStringExtra("projectId");
        initTitle();
        initView();
        initData();
    }

    private void initTitle() {
        View title_Rel = findViewById(R.id.title_Rel);
        View return_Btn = findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) findViewById(R.id.cententtxt);
        cententtxt.setText(titleName);
        View right_Btn = findViewById(R.id.right_Btn);
        View right_BtnTwo = findViewById(R.id.right_BtnTwo);
        StaticData.ViewScale(title_Rel, 0, 88);
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(right_Btn, 40, 40);
        StaticData.ViewScale(right_BtnTwo, 40, 40);
        viewTouchDelegate.expandViewTouchDelegate(right_Btn, 100, 100, 100, 100);
        viewTouchDelegate.expandViewTouchDelegate(right_BtnTwo, 100, 100, 100, 100);
        return_Btn.setOnClickListener(new return_Btn());
        right_BtnTwo.setOnClickListener(new right_BtnTwo());
//        right_Btn.setOnClickListener(new right_Btn());
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class right_BtnTwo implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private void initView() {
        my_tab_Lin = findViewById(R.id.my_tab_Lin);
        ac_tab_layout = (TabLayout) findViewById(R.id.ac_tab_layout);
        pos_Txt = findViewById(R.id.pos_Txt);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);

        StaticData.ViewScale(my_tab_Lin, 0, 128);
        StaticData.ViewScale(ac_tab_layout, 370, 64);
        StaticData.ViewScale(pos_Txt, 0, 60);
        pos_Txt.setOnClickListener(new pos_Txt());
    }

    private class pos_Txt implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent2 = new Intent(Pk_DayPkDetail.this, PostingActivity.class);
            startActivity(intent2);
        }
    }

    private void initData() {
        myRankData(Pk_DayPkDetail.this, saveFile.BaseUrl + saveFile.My_ReportRank_Url + "?ProjectID=" + projectId);
    }

    public List<String> userArr;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;

    private void initLocaData(TabLayout myTab) {
        myTab.setTabMode(TabLayout.MODE_FIXED);//设置可以滑动
        myTab.setSelectedTabIndicatorHeight(0);//去掉下导航条
        if (userArr != null) {
            userArr.clear();
        }
        userArr = new ArrayList<>();
        userArr.add("排行榜");
        userArr.add("回顾");
        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int length = userArr.size();
        fragments.add(Pk_DayPkDetail_RankFragment.newInstance(0 + "", projectId + "", Integral_Model));
        fragments.add(Pk_DayPkDetail_HistoryFragment.newInstance(1 + "", projectId + "", Integral_Model));
    }

    private void tabViewSetView(TabLayout myTab) {
        myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        Slideviewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        myTab.setupWithViewPager(Slideviewpager);
//        Slideviewpager.setCurrentItem(1);
//        Slideviewpager.addOnPageChangeListener(this);
        ac_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                tab_Name.setTextColor(Color.parseColor("#ffffff"));
                StaticData.ViewScale(tab_Name, 185, 64);
                if (tab.getPosition() == 0) {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_leftred);
                } else {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_rightred);
                }
//                initData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                tab_Name.setTextColor(Color.parseColor("#95a0ab"));
                StaticData.ViewScale(tab_Name, 185, 64);
                if (tab.getPosition() == 0) {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_leftgazy);
                } else {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_rightgazy);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    private void resetTablayout(TabLayout myTab) {
        /**
         * 使用tablayout + viewpager时注意 如果设置了setupWithViewPager
         * 则需要重新执行下方对每个条目赋值
         * 否则会出现icon文字不显示的bug
         */
        for (int i = 0; i < myAdapter.getCount(); i++) {
            int postion = i;
//            myTab.getTabAt(postion).setText(userArr.get(postion));
            TabLayout.Tab tab = myTab.getTabAt(postion);
            if (tab != null) {
                tab.setCustomView(myAdapter.getTabView(postion));
            }
        }

        Intent intent = getIntent();
        String tabType = intent.getStringExtra("tabType");
        if (tabType != null) {
            Slideviewpager.setCurrentItem(Integer.parseInt(tabType), true);
        } else {
            Slideviewpager.setCurrentItem(1, true);
            myTab.getTabAt(0).select();
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragments = list;
        }


        public View getTabView(int position) {
            View v = LayoutInflater.from(Pk_DayPkDetail.this).inflate(R.layout.pkfenrank_custom_tab, null);
            TextView tab_Name = (TextView) v.findViewById(R.id.tab_Name);
            tab_Name.setText(userArr.get(position));
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

    DayPkDetail_Model Integral_Model;

    public void myRankData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Integral_Model = new Gson().fromJson(resultString, DayPkDetail_Model.class);
                    if (Integral_Model.isIsSuccess() && !Integral_Model.getData().equals("[]")) {
                        if (Integral_Model.getData().getIs_SenPost() == 0) {
                            pos_Txt.setVisibility(View.VISIBLE);
                        } else {
                            pos_Txt.setVisibility(View.GONE);
                        }
                        initLocaData(ac_tab_layout);
                        tabViewSetView(ac_tab_layout);
                        resetTablayout(ac_tab_layout);
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
