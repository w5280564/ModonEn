package com.moying.energyring.myAcativity;

import android.os.Bundle;
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
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Energy.AttentionFragment;
import com.moying.energyring.myAcativity.Energy.CommittedFragment;
import com.moying.energyring.myAcativity.Energy.GrowthLogFragment;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Admin on 2016/3/25.
 */
public class Fragment1_Energy extends Fragment {
    private View parentView;
    private ViewPager viewpager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (parentView == null) {
            parentView = inflater.inflate(R.layout.fragment1_energy, null);

            initView(parentView);
//        }
//        ViewGroup parent = (ViewGroup) parentView.getParent();
//        if (parent != null) {
//            parent.removeView(parentView);
//        }

        return parentView;
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);

    }

    public List<String> userArr;
    private TabLayout tablayout;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;
    private int iconImg[] = {R.drawable.tab_energy_selectorlog, R.drawable.tab_energy_selectorgoal, R.drawable.tab_energy_selectorme};

    public void initView(View view) {
        tablayout = (TabLayout) view.findViewById(R.id.ac_tab_layout);
        StaticData.ViewScale(tablayout, 0, 88);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        initData();
        tabViewSetView();
        resetTablayout();
    }

    private void initData() {
        userArr = new ArrayList<>();
        userArr.add("成长日志");
        userArr.add("公众承诺");
        userArr.add("我的关注");
        tablayout.setTabMode(TabLayout.GRAVITY_CENTER);//设置可以滑动 根据标签自适应宽度
        tablayout.setSelectedTabIndicatorHeight(0);//去掉下导航条
        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
//        for (int i = 0; i < userArr.size(); i++) {
            fragments.add(GrowthLogFragment.newInstance(userArr.get(0), 0 + ""));
            fragments.add(CommittedFragment.newInstance(userArr.get(1), 1 + ""));
            fragments.add(AttentionFragment.newInstance(userArr.get(2), 2 + ""));
//        }
    }

    private void tabViewSetView() {
        myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments);
        viewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        tablayout.setupWithViewPager(viewpager);
    }


    private void resetTablayout() {
        /**
         * 使用tablayout + viewpager时注意 如果设置了setupWithViewPager
         * 则需要重新执行下方对每个条目赋值
         * 否则会出现icon文字不显示的bug
         */
        for (int i = 0; i < myAdapter.getCount(); i++) {
            tablayout.getTabAt(i).setText(userArr.get(i));
            TabLayout.Tab tab = tablayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(myAdapter.getTabView(i));
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
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            LinearLayout Lin = (LinearLayout) v.findViewById(R.id.Lin);
            StaticData.ViewScale(Lin, 0, 80);
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

        //设置tablayout标题
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return userArr.get(position);
//        }

    }

}
