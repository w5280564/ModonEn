package com.moying.energyring.myAcativity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waylen on 2017/11/27.
 */

public class WelcomeNew extends Activity implements ViewPager.OnPageChangeListener {
    private ViewPager mPager;
    // ViewPager-item视图集合的保存
    private ArrayList<LinearLayout> views;
    // ViewPager-item临时视图
    private LinearLayout view1;
    private LinearLayout dot;
    private List<ImageView> dotList;
    // 当前item
    private int currentItem = 1;
    private ImageView wel_onetop;
    private RelativeLayout popup_bg;
    private Button wel_login;
    private LinearLayout wellin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomeactivity);
        initViewPager();
//        initDot();
        ImageView annim_img = (ImageView) findViewById(R.id.annim_img);
        annim_img.setVisibility(View.GONE);
    }


    //滑动页面
    private void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPager.setVisibility(View.VISIBLE);
        mPager.removeAllViews();
        if (views != null) {
            views.clear();
        }
        views = new ArrayList<LinearLayout>();
        LayoutInflater mInflater = LayoutInflater.from(this);
        int length = 3;
        for (int i = 0; i < length; i++) {
            view1 = (LinearLayout) mInflater.inflate(R.layout.welcomeactivity_style, null);
//            popup_bg = (RelativeLayout) view1.findViewById(R.id.popup_bg);
            wel_onetop = (ImageView) view1.findViewById(R.id.wel_onetop);
            wel_login = (Button) view1.findViewById(R.id.wel_login);
            wellin = (LinearLayout) view1.findViewById(R.id.wellin);
            StaticData.ViewScale(wel_onetop, 750, 1334);
//            StaticData.relativeLayoutScale(popup_bg,640,1136);
            if (i == 0) {
                Uri imgurl = Uri.parse("res:///" + R.drawable.we_one);
                wel_onetop.setImageURI(imgurl);
            } else if (i == 1) {
                Uri imgurl = Uri.parse("res:///" + R.drawable.we_two);
                wel_onetop.setImageURI(imgurl);
            }  else if (i == 2) {
                Uri imgurl = Uri.parse("res:///" + R.drawable.we_three);
                wel_onetop.setImageURI(imgurl);
                wel_login.setVisibility(View.VISIBLE);
                int padd = (int) (Float.parseFloat(saveFile.getShareData("scale", WelcomeNew.this)) * 152);
                wellin.setPadding(0, 0, 0, padd);
                StaticData.ViewScale(wel_login, 417, 97);

            }
            wel_login.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    Intent i = new Intent(WelcomeNew.this, MainActivity.class);
                    startActivity(i);
                    WelcomeNew.this.finish();
                }
            });


            views.add(view1);
        }
        mPager.setAdapter(new myPagerAdapter());
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(this);
    }

//    private void initDot() {
//        dot = (LinearLayout) findViewById(R.id.dot);
//        dot.removeAllViews();
//        if (dotList != null) {
//            dotList.clear();
//        }
//        dotList = new ArrayList<ImageView>();
//        for (int i = 0; i < views.size(); i++) {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//            StaticData.layoutParamsScale(params, 15, 15, WelcomeNew.this);
//            int padd = (int) (Float.parseFloat(UrlVO.getShareData("scale", WelcomeNew.this)) * 5);
//            params.setMargins(padd, 0, padd, 0);
//            ImageView m = new ImageView(this);
//            if (i == 0) {// 默认索引0也就是a为选中状态
//                m.setVisibility(View.VISIBLE);
//                m.setBackgroundResource(R.drawable.point_select);
//            } else if (i > 0 && i < views.size()) {
//                m.setVisibility(View.VISIBLE);
//                m.setBackgroundResource(R.drawable.point);
//            }
//            m.setLayoutParams(params);
//            dot.addView(m);
//            dotList.add(m);
//        }
//    }

    //滑动页面适配器
    class myPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        @Override//点击事件
        public Object instantiateItem(View container, final int position) {
            ((ViewPager) container).addView(views.get(position));
            if (views.get(position) != null) {
            }
            return views.get(position);
        }
    }

    @Override//滑动时改变样式
    public void onPageSelected(int arg0) {
        int pageIndex = arg0;
        currentItem = arg0;
        mPager.setCurrentItem(currentItem, false);
        for (int i = 0; i < views.size(); i++) {
            if (i == currentItem) {
//                dotList.get(i).setBackgroundResource(R.drawable.point_select);
            } else {
//                dotList.get(i).setBackgroundResource(R.drawable.point);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }
}

