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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.DayPkDetail_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static com.moying.energyring.network.saveFile.getShareData;

public class Pk_DayPk_Project_HistoryDetail extends FragmentActivity {

    private TextView cententtxt;
    private String projectId, dateTime;
    private SimpleDraweeView history_simple;
    private TextView historyName_Txt, lei_Txt, day_Txt, time_Txt,leika_Txt,leiday_Txt;
    private TabLayout ac_tab_layout;
    private ViewPager Slideviewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_pk_project_historydetail);

        Intent intent = getIntent();
//        titleName = intent.getStringExtra("titleName");
        projectId = intent.getStringExtra("projectId");
        dateTime = intent.getStringExtra("dateTime");
        initTitle();
        initView();

//        initData(ProjectID);

        changeData(0);

    }

    private void initTitle() {
        View title_Rel = findViewById(R.id.title_Rel);
        View return_Img = findViewById(R.id.return_Img);
        View colock_Img = findViewById(R.id.colock_Img);
        cententtxt = (TextView) findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this, R.color.colorFristWhite));
        cententtxt.setText("历史统计");
        View right_Img = findViewById(R.id.right_Img);
//        StaticData.ViewScale(title_Rel, 0, 88);
        StaticData.ViewScale(return_Img, 39, 63);
        StaticData.ViewScale(right_Img, 36, 36);
        StaticData.ViewScale(colock_Img, 36, 36);
        viewTouchDelegate.expandViewTouchDelegate(right_Img, 100, 100, 100, 100);
        viewTouchDelegate.expandViewTouchDelegate(colock_Img, 100, 100, 100, 100);
        return_Img.setOnClickListener(new return_Img());
        right_Img.setOnClickListener(new right_Img());
        colock_Img.setOnClickListener(new colock_Img());

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        StaticData.ViewScale(mToolbar, 0, 88);
//        setSupportActionBar(mToolbar);
    }


    private void initView() {
        RelativeLayout history_Rel = (RelativeLayout) findViewById(R.id.history_Rel);
        int relpad = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 118);
        history_Rel.setPadding(0, relpad, 0, 0);


//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        int padTop = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 88);
//        params.setMargins(0,padTop,0,0);
//        history_Rel.setLayoutParams(params);
        history_simple = (SimpleDraweeView) findViewById(R.id.history_simple);
        historyName_Txt = (TextView) findViewById(R.id.historyName_Txt);
        lei_Txt = (TextView) findViewById(R.id.lei_Txt);
        day_Txt = (TextView) findViewById(R.id.day_Txt);
        leika_Txt = (TextView) findViewById(R.id.leika_Txt);
        leiday_Txt = (TextView) findViewById(R.id.leiday_Txt);

        ac_tab_layout = (TabLayout) findViewById(R.id.ac_tab_layout);
        time_Txt = (TextView) findViewById(R.id.time_Txt);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);

        StaticData.ViewScale(history_simple, 130, 130);
        StaticData.ViewScale(ac_tab_layout, 420, 80);
    }

    private void changeData(int isChange) {
//        changeData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportProject_List_Url);
        time_Txt.setText(dateTime);
        myRankData(Pk_DayPk_Project_HistoryDetail.this, saveFile.BaseUrl + saveFile.My_ReportRank_Url + "?ProjectID=" + projectId + "&date=" + dateTime, 0);
    }


    private void initData(String ProjectID) {
//        ListData(Pk_DayPk_Project_HistoryDetail.this, saveFile.BaseUrl + saveFile.My_Ranking_One_Url + "?ProjectID=" + ProjectID);
//        myRankData(Pk_DayPk_Project_HistoryDetail.this, saveFile.BaseUrl + saveFile.My_ReportRank_Url + "?ProjectID=" + ProjectID, 0);
    }


    private class return_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class right_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showDetele(Pk_DayPk_Project_HistoryDetail.this, cententtxt);
        }
    }

    private class colock_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Pk_DayPk_Project_HistoryDetail.this, Pk_AddReport_Colock.class);
            intent.putExtra("ProjectID", projectId + "");
            startActivity(intent);
        }
    }

    private void showDetele(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_daypk_delete, null);
        final BasePopupWindow popupWindow = new BasePopupWindow(mContext);
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
        popupWindow.showAsDropDown(view, 0, 0);
        RelativeLayout my_Rel = (RelativeLayout) contentView.findViewById(R.id.my_Rel);
        TextView hint_Txt = (TextView) contentView.findViewById(R.id.hint_Txt);
        LinearLayout cha_Lin = (LinearLayout) contentView.findViewById(R.id.cha_Lin);
        StaticData.ViewScale(my_Rel, 610, 450);
        StaticData.ViewScale(hint_Txt, 0, 120);
        StaticData.ViewScale(cha_Lin, 0, 120);

        contentView.findViewById(R.id.sure_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String ProjectID = baseModel.getData().get(ac_tab_layout.getSelectedTabPosition()).getProjectID() + "";
                deleData(mContext, saveFile.BaseUrl + saveFile.My_preoject_Dele_Url, projectId);
            }
        });
        contentView.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                title_Rel.setBackgroundColor(Color.parseColor("#00000000"));
//                StaticData.ViewScale(more_Btn, 48, 28);
//                more_Btn.setBackgroundResource(R.drawable.more_icon);
//                popupWindow.dismiss();
            }
        });
    }

    //删帖
    public void deleData(final Context context, String baseUrl, String projectId) {
        RequestParams params = new RequestParams(baseUrl);
        if (getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", getShareData("JSESSIONID", context));
        }
        params.addBodyParameter("ProjectID", projectId + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model model = new Gson().fromJson(resultString, Base_Model.class);
                    if (model.isIsSuccess()) {

                        finish();
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
                    context.startActivity(intent);
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

    DayPkDetail_Model Integral_Model;

    public void myRankData(final Context context, String baseUrl, final int isChange) {
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
//                        if (Integral_Model.getData().getIs_SenPost() == 0) {
//                            pos_Txt.setVisibility(View.VISIBLE);
//                        } else {
//                            pos_Txt.setVisibility(View.GONE);
//                        }
                        initLocaData(ac_tab_layout);
                        tabViewSetView(ac_tab_layout);
                        resetTablayout(ac_tab_layout);
                        changeAddImg(Integral_Model);
                        if (isChange == 0) {

                        } else {
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


    private void changeAddImg(DayPkDetail_Model pkModel) {
        //是否可以打卡

        DayPkDetail_Model.DataBean oneData = pkModel.getData();

        String Type = oneData.getReportID() + "";
        String projectName = oneData.getProjectName();
        int Limit = oneData.getLimit();
        boolean isTrain = oneData.isIs_Train();
        double leiStr =oneData.getReportNum_All();
        double dayStr = oneData.getReportNum_Month();
        String Unit = oneData.getProjectUnit();

        NumberFormat nf = new DecimalFormat("#.#");//# 0不显示
        String leiNf = "";
        if (leiStr > 1000) {
            leiNf = nf.format(leiStr / 1000) + "K";
        }else {
            leiNf = nf.format(leiStr);
        }
        String dayNf = "";
        if (dayStr > 1000) {
            dayNf = nf.format(dayStr / 1000) + "K";
        }else {
            dayNf = nf.format(dayStr);
        }

        historyName_Txt.setText(projectName);
        lei_Txt.setText(leiNf + Unit);
        day_Txt.setText(dayNf + Unit);

        if (oneData.getFilePath() != null) {
            Uri uri = Uri.parse(oneData.getFilePath());
            history_simple.setImageURI(uri);
        } else {
            StaticData.lodingheadBg(history_simple);

        }

        if (oneData.getProjectName().equals("健康走")){
            leika_Txt.setText("总步数");
            leiday_Txt.setText("本月步数");
        }else {
            leika_Txt.setText("累计打卡");
            leiday_Txt.setText("累计天数");
        }
//        if (Limit == 1) { //上限是1的项目
//            if (Type.equals("0")) { // 每日只能打卡一次 还未汇报
////                addpk_Img.setVisibility(View.VISIBLE);
////                if (isTrain){
//                addpkOrTrain_Txt.setVisibility(View.VISIBLE);
//                addpkOrTrain_Txt.setText("打卡");
////                }
//            } else {
////                addpk_Img.setVisibility(View.GONE);
//                addpkOrTrain_Txt.setVisibility(View.GONE);
//            }
//        } else {
//            //可多次汇报的项目
//            if (projectName.equals("健康走")) {
////                addpk_Img.setVisibility(View.GONE);
//                addpkOrTrain_Txt.setVisibility(View.GONE);
//            } else {
////                addpk_Img.setVisibility(View.VISIBLE);
//                //不是健康走 是否是训练项目
//                if (isTrain) {
//                    addpkOrTrain_Txt.setText("开始训练");
//                }else {
//                    addpkOrTrain_Txt.setText("打卡");
//                }
//                addpkOrTrain_Txt.setVisibility(View.VISIBLE);
//            }
//        }
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
        userArr.add("回顾");
        userArr.add("排行榜");
        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int length = userArr.size();
        fragments.add(Pk_DayPk_Project_HistoryDetail_HistoryFragment.newInstance(1 + "", projectId + "", Integral_Model));
        fragments.add(Pk_DayPk_Project_historyDetail_RankFragment.newInstance(0 + "", projectId + "", Integral_Model));
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
//                int txtSize = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_DayPk_Project_HistoryDetail.this)) * 30);
//                tab_Name.setTextSize(30);
                tab_Name.setTextColor(ContextCompat.getColor(Pk_DayPk_Project_HistoryDetail.this, R.color.colorFristWhite));
                StaticData.ViewScale(tab_Name, 210, 80);

                if (tab.getPosition() == 0) {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_leftred);
                } else {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_rightred);
                }

//                initData();
//                initData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                int txtSize = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_DayPk_Project_HistoryDetail.this)) * 20);
//                tab_Name.setTextSize(20);
                tab_Name.setTextColor(ContextCompat.getColor(Pk_DayPk_Project_HistoryDetail.this, R.color.colorSecondWhite));
                StaticData.ViewScale(tab_Name, 210, 80);

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
            View v = LayoutInflater.from(Pk_DayPk_Project_HistoryDetail.this).inflate(R.layout.daypk_historydetail_customtab, null);
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
