package com.moying.energyring.myAcativity.Pk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Pk_AddTab_Model;
import com.moying.energyring.Model.ProjectModel;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pk_DayPKAdd_Project_Tab extends FragmentActivity {
    List<ProjectModel> projectModel;
    final int CODE_MORE = 255;
    private Button seek_Btn;
    private TabLayout ac_tab_layout;
    private ViewPager Slideviewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeprice);
        setContentView(R.layout.pkday_pkaddproject_tab);


        projectModel = (List<ProjectModel>) getIntent().getSerializableExtra("baseModel");

        View return_Btn = findViewById(R.id.return_Btn);
        seek_Btn = (Button) findViewById(R.id.seek_Btn);
        View right_Btn = findViewById(R.id.right_Btn);
        View sure_Lin = findViewById(R.id.sure_Lin);
        sure_Lin.setVisibility(View.GONE);
        TextView sure_Txt = (TextView) findViewById(R.id.sure_Txt);

        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(seek_Btn, 0, 52);
        StaticData.ViewScale(right_Btn, 100, 88);
        StaticData.ViewScale(sure_Lin, 0, 127);
        StaticData.ViewScale(sure_Txt, 410, 80);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
        seek_Btn.setOnClickListener(new seek_Btn());
        sure_Txt.setOnClickListener(new sure_Txt());

        initView();

        tabData(Pk_DayPKAdd_Project_Tab.this, saveFile.BaseUrl + saveFile.My_ProjectType_List_Url + "?Type=1");
    }

    private void initView() {
        ac_tab_layout = (TabLayout) findViewById(R.id.ac_tab_layout);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            resultString();
        }
    }

    public class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            resultString();
            Intent intent = new Intent(Pk_DayPKAdd_Project_Tab.this, Pk_DayPkAdd_Project_Crate.class);
            intent.putExtra("project","");
            startActivity(intent);
        }
    }

    public class sure_Txt implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            resultString();
        }
    }

    private class seek_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Pk_DayPKAdd_Project_Tab.this, Pk_DayPkAdd_Project_TabSeek.class);
            intent.putExtra("baseModel", (Serializable) projectModel);
            startActivity(intent);
        }
    }


    private void resultString() {
        Intent intent = new Intent();
        intent.putExtra("projectModel", (Serializable) projectModel);
        setResult(CODE_MORE, intent);
        finish();
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


    //    public List<String> userArr;
    public List<Fragment> fragments;
    public Pk_DayPKAdd_Project_Tab.MyFragmentPagerAdapter myAdapter;

    private void initLocaData(TabLayout myTab) {
        myTab.setTabMode(TabLayout.GRAVITY_CENTER);
        myTab.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorFristBlack));//进度条颜色
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int length = project_Model.getData().size();
//        fragments.add(Pk_DayPkDetail_HistoryFragment.newInstance(1 + "", projectId + "", Integral_Model));
        for (int i = 0; i < length; i++) {
            fragments.add(Pk_DayPkAddTab_Fragment.newInstance(0 + "", project_Model.getData().get(i).getProjectTypeID() + "", projectModel));
//            fragments.add(Person_Badge_ListFragment.newInstance(i + ""));
        }
    }

    private void tabViewSetView(TabLayout myTab) {
        myAdapter = new Pk_DayPKAdd_Project_Tab.MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        Slideviewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        myTab.setupWithViewPager(Slideviewpager);
//        Slideviewpager.setCurrentItem(1);
//        Slideviewpager.addOnPageChangeListener(this);
        ac_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                tab_Name.setTextColor(ContextCompat.getColor(Pk_DayPKAdd_Project_Tab.this,R.color.colorFristBlack));
//                StaticData.ViewScale(tab_Name, 185, 64);
//                initData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                tab_Name.setTextColor(ContextCompat.getColor(Pk_DayPKAdd_Project_Tab.this,R.color.colorSecondWhite));
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
            View v = LayoutInflater.from(Pk_DayPKAdd_Project_Tab.this).inflate(R.layout.daypk_addproject_tab, null);
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
