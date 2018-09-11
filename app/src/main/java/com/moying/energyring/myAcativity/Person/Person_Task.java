package com.moying.energyring.myAcativity.Person;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class Person_Task extends BaseActivity {
    private TabLayout ac_tab_layout;
    private ViewPager Slideviewpager;
    private String Integral;
    private TextView fen_Txt;
    private String tabType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persontask);



        Intent intent = getIntent();
        Integral = intent.getStringExtra("Integral");
        tabType = intent.getStringExtra("tabType");


        initTitle();
        initView();
        initData();
    }


    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("我的积分");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }


    private void initView() {
        ac_tab_layout = (TabLayout) findViewById(R.id.ac_tab_layout);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);
        fen_Txt = (TextView)findViewById(R.id.fen_Txt);
        StaticData.ViewScale(ac_tab_layout, 410, 100);

        fen_Txt.setText(Integral);
    }

    private void initData(){
        initLocaData(ac_tab_layout);
        tabViewSetView(ac_tab_layout);
        resetTablayout(ac_tab_layout);

    }


    public List<String> userArr;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;

    private void initLocaData(TabLayout myTab) {
        myTab.setTabMode(TabLayout.MODE_FIXED);//设置可以滑动
//        myTab.setSelectedTabIndicatorHeight(0);//去掉下导航条
        myTab.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorThridYellow));//进度条颜色
        myTab.setTabMode(TabLayout.GRAVITY_CENTER);
        if (userArr != null) {
            userArr.clear();
        }
        userArr = new ArrayList<>();
        userArr.add("积分商城");
        userArr.add("每日任务");
        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int length = userArr.size();
        fragments.add(Person_Task_Shop_Fragment.newInstance(Integral));
        fragments.add(Person_Task_List_Fragment.newInstance(Integral));
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
                int txtSize = (int) (Float.parseFloat(saveFile.getShareData("scale",Person_Task.this)) * 30);
                tab_Name.setTextSize(20);
                tab_Name.setTextColor(ContextCompat.getColor(Person_Task.this,R.color.colorThridYellow));
                StaticData.ViewScale(tab_Name, 205, 80);

//                if (tab.getPosition() == 0) {
//                    tab_Name.setTextSize(20);
//                    tab_Name.setTextColor(Color.parseColor("#ffd800"));
//                } else {
//                    tab_Name.setTextSize(15);
//                    tab_Name.setTextColor(Color.parseColor("#95a0ab"));
//                }

//                initData();
//                initData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                int txtSize = (int) (Float.parseFloat(saveFile.getShareData("scale", Person_Task.this)) * 20);
                tab_Name.setTextSize(15);
                tab_Name.setTextColor(ContextCompat.getColor(Person_Task.this,R.color.colorSecondWhite));
                StaticData.ViewScale(tab_Name, 205, 80);

//                if (tab.getPosition() == 0) {
//                    tab_Name.setTextSize(20);
//                    tab_Name.setTextColor(Color.parseColor("#ffd800"));
//                } else {
//                    tab_Name.setTextSize(15);
//                    tab_Name.setTextColor(Color.parseColor("#95a0ab"));
//                }
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


        if (tabType != null) {
            Slideviewpager.setCurrentItem(Integer.parseInt(tabType), true);
        } else {
            Slideviewpager.setCurrentItem(0, true);
//            myTab.addTab(myTab.newTab().setText("积分商城"),true);
            //解决默认不调用addOnTabSelectedListener问题
            myTab.getTabAt(1).select();
            myTab.getTabAt(0).select();
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragments = list;
        }


        public View getTabView(int position) {
            View v = LayoutInflater.from(Person_Task.this).inflate(R.layout.pkfenrank_custom_tab, null);
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

}
