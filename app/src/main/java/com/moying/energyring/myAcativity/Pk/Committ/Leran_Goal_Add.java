package com.moying.energyring.myAcativity.Pk.Committ;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Pk_AddTab_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Leran_Goal_Add extends FragmentActivity {
    final int CODE_MORE = 255;
    private Button seek_Btn;
    private TabLayout ac_tab_layout;
    private ViewPager Slideviewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeprice);
        setContentView(R.layout.pkday_pkaddproject_tab);



        View return_Btn = findViewById(R.id.return_Btn);
        seek_Btn = (Button) findViewById(R.id.seek_Btn);
        View right_Btn = findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.INVISIBLE);

        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(seek_Btn, 0, 52);
        StaticData.ViewScale(right_Btn, 100, 80);

        return_Btn.setOnClickListener(new return_Btn());
        seek_Btn.setOnClickListener(new seek_Btn());

        initView();

        tabData(this, saveFile.BaseUrl + saveFile.My_ProjectType_List_Url + "?Type=1");
    }

    private void initView() {
        ac_tab_layout = (TabLayout) findViewById(R.id.ac_tab_layout);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }


    private class seek_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Leran_Goal_Add.this, Leran_Goal_Add_TabSeek.class);
            startActivity(intent);
        }
    }



    Pk_AddTab_Model project_Model;

    public void tabData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    project_Model = new Gson().fromJson(resultString, Pk_AddTab_Model.class);
                    if (project_Model.isIsSuccess() && !project_Model.getData().equals("[]")) {
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


    //    public List<String> userArr;
    public List<Fragment> fragments;
    public Leran_Goal_Add.MyFragmentPagerAdapter myAdapter;

    private void initLocaData(TabLayout myTab) {
        myTab.setTabMode(TabLayout.GRAVITY_CENTER);
        myTab.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));//进度条颜色
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int length = project_Model.getData().size();
//        fragments.add(Pk_DayPkDetail_HistoryFragment.newInstance(1 + "", projectId + "", Integral_Model));
        for (int i = 0; i < length; i++) {
            fragments.add(Leran_Goal_Add_Fragment.newInstance(0 + "", project_Model.getData().get(i).getProjectTypeID() + ""));
//            fragments.add(Person_Badge_ListFragment.newInstance(i + ""));
        }
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
//                StaticData.ViewScale(tab_Name, 185, 64);
//                initData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                tab_Name.setTextColor(Color.parseColor("#989797"));
//                StaticData.ViewScale(tab_Name, 185, 64);
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
            View v = LayoutInflater.from(Leran_Goal_Add.this).inflate(R.layout.daypk_addproject_tab, null);
            LinearLayout tab_Lin = (LinearLayout) v.findViewById(R.id.tab_Lin);
            SimpleDraweeView tab_Sim = (SimpleDraweeView) v.findViewById(R.id.tab_Sim);
            TextView tab_Name = (TextView) v.findViewById(R.id.tab_Name);
//            StaticData.ViewScale(tab_Lin, 100, 0);
            StaticData.ViewScale(tab_Sim, 28, 28);
            Pk_AddTab_Model.DataBean oneData = project_Model.getData().get(position);
            tab_Name.setText(oneData.getProjectTypeName());
            if (oneData.getFilePath() != null) {
                Uri imgUri = Uri.parse(oneData.getFilePath().toString());
                tab_Sim.setImageURI(imgUri);
            }
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
