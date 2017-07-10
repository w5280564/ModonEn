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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.moying.energyring.Model.DayPkProject_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.pinyin.CharacterParser;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Pk_Daypk extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk__daypk);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initView();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Pk_Daypk"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Pk_Daypk"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    private Button more_Btn;
    private RelativeLayout title_Rel;
    private ViewPager viewpager;
    private TabLayout tablayout;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;

    public void initView() {
        title_Rel = (RelativeLayout) findViewById(R.id.title_Rel);
        Button return_Btn = (Button) findViewById(R.id.return_Btn);
        more_Btn = (Button) findViewById(R.id.more_Btn);
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(more_Btn, 48, 28);
        tablayout = (TabLayout) findViewById(R.id.ac_tab_layout);
        StaticData.ViewScale(tablayout, 0, 88);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        more_Btn.setOnClickListener(new more_Btn());
        return_Btn.setOnClickListener(new return_Btn());

        dayPkData(Pk_Daypk.this, saveFile.BaseUrl + saveFile.dayPkProjectUrl);
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class more_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showProject(Pk_Daypk.this, title_Rel);
        }
    }

    private void initData() {
        tablayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"));//初始颜色，选中颜色
        tablayout.setSelectedTabIndicatorColor(Color.parseColor("#ffd800"));//进度条颜色
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置可以滑动 根据标签自适应宽度
//        viewpager.setOffscreenPageLimit(1);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 实例化汉字转拼音类
                CharacterParser  characterParser = CharacterParser.getInstance();
                String pinyin = characterParser.getSelling(baseModel.getData().get(tab.getPosition()).getProjectName());
                MobclickAgent.onEvent(Pk_Daypk.this, pinyin);//统计页签
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int size = baseModel.getData().size();
        for (int i = 0; i < size; i++) {
//            fragments.add(DayPkListFragment.newInstance(baseModel.getData().get(i).getProjectName(),baseModel.getData().get(i).getProjectID()+""));
            fragments.add(DayPkListFragment.newInstance(baseModel.getData().get(i).getProjectName(), baseModel.getData().get(i).getProjectID() + ""));
        }
    }


    private void tabViewSetView() {
        myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        tablayout.setupWithViewPager(viewpager);
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
            return baseModel.getData().get(position).getProjectName();
        }

    }

    DayPkProject_Model baseModel;
    public void dayPkData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    baseModel = new Gson().fromJson(resultString, DayPkProject_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
                        initData();
                        tabViewSetView();
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

    PopupWindow popupWindow;

    private void showProject(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_pkproject, null);
        popupWindow = new BasePopupWindow(mContext);
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
        popupWindow.showAsDropDown(view);
        FlexboxLayout myflexbox = (FlexboxLayout) contentView.findViewById(R.id.myflexbox);

        title_Rel.setBackgroundColor(Color.parseColor("#99000000"));
        StaticData.ViewScale(more_Btn, 48, 48);
        more_Btn.setBackgroundResource(R.drawable.login_return);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                title_Rel.setBackgroundColor(Color.parseColor("#00000000"));
                StaticData.ViewScale(more_Btn, 48, 28);
                more_Btn.setBackgroundResource(R.drawable.more_icon);
            }
        });
        imgBadge(myflexbox);
    }


    //加载弹框pk项目
    public void imgBadge(FlexboxLayout myFlex) {
        if (myFlex != null) {
            myFlex.removeAllViews();
        }
        int size = baseModel.getData().size();
        for (int i = 0; i < size; i++) {
            TextView projectTxt = new TextView(this);
            projectTxt.setTextColor(Color.parseColor("#ffffff"));
            projectTxt.setGravity(Gravity.CENTER);
            RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            StaticData.layoutParamsScale(itemParams, 187, 100);
            projectTxt.setLayoutParams(itemParams);
            projectTxt.setText(baseModel.getData().get(i).getProjectName());
            projectTxt.setTag(i);
            myFlex.addView(projectTxt);
            projectTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    int tag = (Integer) v.getTag();
                    setpage(tag);
                    // 实例化汉字转拼音类
                    CharacterParser  characterParser = CharacterParser.getInstance();
                    String pinyin = characterParser.getSelling(baseModel.getData().get(tag).getProjectName());
                    MobclickAgent.onEvent(Pk_Daypk.this, pinyin);//统计页签

//                    StringBuffer sbf = new StringBuffer();
//                    for (int i1 =0;i1<baseModel.getData().size();i1++){
//                        String piny = characterParser.getSelling(baseModel.getData().get(i1).getProjectName());
//                        sbf.append(piny+",");
//                    }
//
//                    Log.e("pinyin",sbf.toString());
                }
            });
        }
    }


    //移到点击的tab
    public void setpage(int tag) {
        tablayout.setScrollPosition(tag, 1F, true);//滑动到固定位置
        viewpager.setCurrentItem(tag);
    }

//    public DayPkListFragment setFragment(int pos){
//        return (DayPkListFragment) fragments.get(pos);
//    }

//    public static void setImg(String imguri){
//        if (imguri != null){
//            pkbg_simple.setImageURI(imguri);
//        }else {
//            StaticData.lodingheadBg(pkbg_simple);
//        }
//    }


}
