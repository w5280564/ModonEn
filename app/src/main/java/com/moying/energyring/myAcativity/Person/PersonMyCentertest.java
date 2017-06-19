package com.moying.energyring.myAcativity.Person;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Energy.AttentionFragment;
import com.moying.energyring.myAcativity.Energy.CommittedFragment;
import com.moying.energyring.myAcativity.Energy.GrowthLogFragment;
import com.moying.energyring.waylenBaseView.AppBarStateChangeListener;

import java.util.ArrayList;
import java.util.List;

public class PersonMyCentertest extends FragmentActivity implements ViewPager.OnPageChangeListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppThemeprice);
//        setContentView(R.layout.fragment1_energy);
        setContentView(R.layout.activity_person_my_center);

        initView();
        initData();
        tabViewSetView();
        resetTablayout();
    }

    private int currentItem = 1;
    private ViewPager Slideviewpager;
    private TabLayout tablayout;
    public List<String> userArr;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;
    private int iconImg[] = {R.drawable.tab_energy_selectorlog, R.drawable.tab_energy_selectorgoal, R.drawable.tab_energy_selectorme, R.drawable.tab_energy_selectorgoal};

    public void initView() {
        tablayout = (TabLayout) findViewById(R.id.ac_tab_layout);
        StaticData.ViewScale(tablayout, 0, 88);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);

        AppBarLayout mAppBarLayout = (AppBarLayout)findViewById(R.id.mAppBarLayout);
        final Button return_Btn = (Button)findViewById(R.id.return_Btn);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if( state == State.EXPANDED ) {

                    //展开状态

                }else if(state == State.COLLAPSED){

                    //折叠状态
                    return_Btn.setVisibility(View.VISIBLE);
                }else {

                    //中间状态

                }
            }
        });

    }

    private void initData() {
        userArr = new ArrayList<>();
        userArr.add("成长日志");
        userArr.add("公众承诺");
        userArr.add("我的关注");
        userArr.add("每日PK");
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置可以滑动 根据标签自适应宽度
        tablayout.setSelectedTabIndicatorHeight(0);//去掉下导航条
        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int length = userArr.size() + 2;
        for (int i = 0; i < length; i++) {
            int postion = i - 1;
            if (i == 0) {
                postion = length - 3;
            } else if (i == length - 1) {
                postion = 0;
            }
        }
        fragments.add(GrowthLogFragment.newInstance(userArr.get(0), 0 + ""));
        fragments.add(CommittedFragment.newInstance(userArr.get(1), 1 + ""));
        fragments.add(AttentionFragment.newInstance(userArr.get(2), 2 + ""));
        fragments.add(CommittedFragment.newInstance(userArr.get(3), 3 + ""));
        fragments.add(CommittedFragment.newInstance(userArr.get(3), 3 + ""));
        fragments.add(CommittedFragment.newInstance(userArr.get(3), 3 + ""));
    }

    private void tabViewSetView() {
        myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        Slideviewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        tablayout.setupWithViewPager(Slideviewpager);
//        Slideviewpager.setAutoSlideDuration(10000);
        Slideviewpager.setCurrentItem(1);
//        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager));
//        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
//        Slideviewpager.addOnPageChangeListener(this);
//        tablayout.addOnTabSelectedListener(new tablayout());
    }


    private void resetTablayout() {
        /**
         * 使用tablayout + viewpager时注意 如果设置了setupWithViewPager
         * 则需要重新执行下方对每个条目赋值
         * 否则会出现icon文字不显示的bug
         */
        for (int i = 0; i < myAdapter.getCount(); i++) {
            int postion = i - 1;
            if (i == 0) {
                postion = myAdapter.getCount() - 3;
            } else if (i == myAdapter.getCount() - 1) {
                postion = 0;
            }
            tablayout.getTabAt(postion).setText(userArr.get(postion));
            TabLayout.Tab tab = tablayout.getTabAt(postion);
            if (tab != null) {
                tab.setCustomView(myAdapter.getTabView(postion));
//                if (i == 0) {
//                    tab.getCustomView().findViewById(R.id.tab_Img).setSelected(true);//第一个tab被选中
//                }
            }
        }
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragments = list;
        }

        public View getTabView(int position) {
            View v = LayoutInflater.from(PersonMyCentertest.this).inflate(R.layout.custom_tab, null);
            LinearLayout Lin = (LinearLayout) v.findViewById(R.id.Lin);
            StaticData.ViewScale(Lin, 250, 80);
            LinearLayout tab_Lin = (LinearLayout) v.findViewById(R.id.tab_Lin);
            StaticData.ViewScale(tab_Lin, 160, 56);
            ImageView tab_Img = (ImageView) v.findViewById(R.id.tab_Img);
            StaticData.ViewScale(tab_Img, 40, 40);
            tab_Img.setImageResource(iconImg[position]);
            TextView tab_Txt = (TextView) v.findViewById(R.id.tab_Txt);
            tab_Txt.setText(userArr.get(position));
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int arg0) {
        int pageIndex = arg0;
        // c' a b c a'
        // 向右滑动到a'时,将a'页设置为a的位置，则可以继续向右滑动。同理当向左滑动到 c'时，将c'页设置为c。
        if (arg0 == 0) {
            // 当视图在第一个时，将页面号设置为图片的最后一张。
            pageIndex = userArr.size(); // 2'>2
        } else if (arg0 == userArr.size() + 1) {
            // 当视图在最后一个是,将页面号设置为图片的第一张。
            pageIndex = 1; // 0'>0
        }
        if (arg0 != pageIndex) {
            // mPager.setCurrentItem(pageIndex, false);
            currentItem = pageIndex;
        } else {
            currentItem = arg0;
        }
        Slideviewpager.setCurrentItem(currentItem, false);
        tablayout.setScrollPosition(currentItem, 1F, true);//滑动到固定位置

//        if (userArr.size() > 1) { //多于1，才会循环跳转
//            if (position < 1) { //首位之前，跳转到末尾（N）
//                position = 3;
//                tablayout.setScrollPosition(position, 1F, false);//滑动到固定位置
//                Slideviewpager.setCurrentItem(position, false);
//            } else if (position > 3) { //末位之后，跳转到首位（1）
//                position = 1;
//
//                tablayout.setScrollPosition(position, 1F, false);//滑动到固定位置
//                Slideviewpager.setCurrentItem(position, false); //false:不显示跳转过程的动画
//            }
//        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
