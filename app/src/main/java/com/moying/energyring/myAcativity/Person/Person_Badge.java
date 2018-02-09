package com.moying.energyring.myAcativity.Person;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.network.saveFile;

import java.util.ArrayList;
import java.util.List;

public class Person_Badge extends FragmentActivity {


    private TextView my_badgeCount;
    private TabLayout tablayout;
    private ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeprice);
        setContentView(R.layout.activity_person__badge);

        initView();
        initData();
        tabViewSetView();
    }

    private void initView() {
        RelativeLayout badge_Rel = (RelativeLayout) findViewById(R.id.badge_Rel);
        Button return_Btn = (Button) findViewById(R.id.return_Btn);
        TextView my_badge_Txt = (TextView) findViewById(R.id.my_badge_Txt);
        myBadge(my_badge_Txt);
        LinearLayout badge_Lin = (LinearLayout) findViewById(R.id.badge_Lin);
        haveBadge(badge_Lin);
        ImageView badge_xingImg = (ImageView) findViewById(R.id.badge_xingImg);
        my_badgeCount = (TextView) findViewById(R.id.my_badgeCount);

        tablayout = (TabLayout) findViewById(R.id.ac_tab_layout);
        StaticData.ViewScale(tablayout, 0, 88);
        viewpager = (ViewPager) findViewById(R.id.badgeviewpager);

        StaticData.ViewScale(badge_Rel, 0, 336);
        StaticData.ViewScale(return_Btn, 80, 88);
        viewTouchDelegate.expandViewTouchDelegate(return_Btn, 100, 100, 100, 100);
        StaticData.ViewScale(badge_xingImg, 40, 40);
        return_Btn.setOnClickListener(new return_Btn());
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private String[] badgeStr = {"汇报", "早起", "签到"};

    private void myBadge(TextView myTxt) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int magL = (int) (Float.parseFloat(saveFile.getShareData("scale", Person_Badge.this)) * 30);
        int magT = (int) (Float.parseFloat(saveFile.getShareData("scale", Person_Badge.this)) * 75);
        params.setMargins(magL, magT, 0, 0);
//        int textSize = (int) (Float.parseFloat(saveFile.getShareData("scale", Person_Badge.this)) * 28);
//        myTxt.setTextSize(textSize);
        myTxt.setLayoutParams(params);
    }

    private void haveBadge(LinearLayout myLin) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        StaticData.layoutParamsScale(params, 212, 56);
        int magR = (int) (Float.parseFloat(saveFile.getShareData("scale", Person_Badge.this)) * 30);
        int magT = (int) (Float.parseFloat(saveFile.getShareData("scale", Person_Badge.this)) * 75);
        params.setMargins(0, magT, magR, 0);
        myLin.setLayoutParams(params);
    }

    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;

    private void initData() {
        tablayout.setTabTextColors(Color.parseColor("#95a0ab"), Color.parseColor("#ffd800"));//初始颜色，选中颜色
        tablayout.setSelectedTabIndicatorColor(Color.parseColor("#ffd800"));//进度条颜色
        tablayout.setTabMode(TabLayout.MODE_FIXED);//设置可以滑动 根据标签自适应宽度 TabLayout.MODE_FIXED

//        viewpager.setOffscreenPageLimit(1);


        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int size = badgeStr.length;
        for (int i = 0; i < size; i++) {
//            fragments.add(DayPkListFragment.newInstance(baseModel.getData().get(i).getProjectName(),baseModel.getData().get(i).getProjectID()+""));
            int type = 1;
            if (badgeStr[i].equals("汇报")){
                type = 1;
            }else if (badgeStr[i].equals("早起")){
                type = 3;
            }else if (badgeStr[i].equals("签到")){
                type = 2;
            }
            fragments.add(Person_Badge_ListFragment.newInstance(type+""));
        }

    }


    String projectId;
    int position = 0;

    private void tabViewSetView() {
        myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        tablayout.setupWithViewPager(viewpager);

//        Intent intent = getIntent();
//        projectId = intent.getStringExtra("projectId");
//        if (projectId != null) {
//            int size = badgeStr.length;
//            for (int i = 0; i < size; i++) {
//                if (projectId.equals(baseModel.getData().get(i).getProjectID() + "")) {
//                    position = i;
//                    setpkbg(i);
//                    break;
//                }
//            }
//        }

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 实例化汉字转拼音类
//                CharacterParser characterParser = CharacterParser.getInstance();
//                String pinyin = characterParser.getSelling(baseModel.getData().get(tab.getPosition()).getProjectName());
//                MobclickAgent.onEvent(Person_Badge.this, pinyin);//统计页签

//                initBg(baseModel.getData().get(tab.getPosition()).getProjectID() + "");

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

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
            return badgeStr[position];
        }

    }


}
