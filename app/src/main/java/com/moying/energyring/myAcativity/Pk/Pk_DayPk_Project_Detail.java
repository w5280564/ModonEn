package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.isFristSee_Model;
import com.moying.energyring.Model.newPk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.GuideUtil;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.moying.energyring.network.saveFile.getShareData;

public class Pk_DayPk_Project_Detail extends FragmentActivity {
    //    private String titleName;
    private View my_tab_Lin;
    private TabLayout ac_tab_layout;
    private ViewPager Slideviewpager;
    private String projectId;
    private TextView cententtxt;
    private GuideUtil guideUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeprice);
        setContentView(R.layout.activity_daypk_project_detail);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        Intent intent = getIntent();
//        titleName = intent.getStringExtra("titleName");
        projectId = intent.getStringExtra("projectId");
        initTitle();
        initView();
        initData();
        initGuide();
    }


    private void initTitle() {
        View title_Rel = findViewById(R.id.title_Rel);
        View return_Btn = findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        cententtxt = (TextView) findViewById(R.id.cententtxt);
//        cententtxt.setText(titleName);
        View right_Btn = findViewById(R.id.right_Btn);
        StaticData.ViewScale(title_Rel, 0, 88);
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(right_Btn, 40, 40);
        viewTouchDelegate.expandViewTouchDelegate(right_Btn, 100, 100, 100, 100);
        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showDetele(Pk_DayPk_Project_Detail.this, cententtxt);
        }
    }


    private void initView() {
        ac_tab_layout = (TabLayout) findViewById(R.id.ac_tab_layout);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);
        StaticData.ViewScale(ac_tab_layout, 0, 6);
    }

    private void initGuide() {
        guideFristData(Pk_DayPk_Project_Detail.this, saveFile.BaseUrl + saveFile.GuidePerFirst_Url, 0);//展示功能提醒页
        guideUtil = GuideUtil.getInstance();
    }

    private void setpkbg(int tag) {
        cententtxt.setText(baseModel.getData().get(tag).getProjectName());
        ac_tab_layout.setScrollPosition(tag, 1F, true);//滑动到固定位置
        Slideviewpager.setCurrentItem(tag);
//        initData(projectId);
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
                String ProjectID = baseModel.getData().get(ac_tab_layout.getSelectedTabPosition()).getProjectID() + "";
                deleData(Pk_DayPk_Project_Detail.this, saveFile.BaseUrl + saveFile.My_preoject_Dele_Url, ProjectID);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == Activity.RESULT_OK) {
//            Fragment f = fragments.get(tabCount);
//        /*然后在碎片中调用重写的onActivityResult方法*/
//            f.onActivityResult(requestCode, resultCode, data);
//        }
    }

    private void initData() {
        ListData(this, saveFile.BaseUrl + saveFile.My_ReportProject_List_Url);
    }

    public List<String> userArr;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;

    private void initLocaData(TabLayout myTab) {
        myTab.setTabTextColors(Color.parseColor("#0095a0ab"), Color.parseColor("#00ffd800"));//初始颜色，选中颜色
        myTab.setSelectedTabIndicatorColor(Color.parseColor("#ffd800"));//进度条颜色
        myTab.setTabMode(TabLayout.GRAVITY_CENTER);
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int length = baseModel.getData().size();
//        fragments.add(Pk_DayPkDetail_HistoryFragment.newInstance(1 + "", projectId + "", Integral_Model));
        for (int i = 0; i < length; i++) {
//            fragments.add(Pk_DayPkAddTab_Fragment.newInstance(0 + "", baseModel.getData().get(i).getProjectID() + "", projectModel));
            int ReportID = baseModel.getData().get(i).getReportID();
            int ProjectID = baseModel.getData().get(i).getProjectID();
            String projectName = baseModel.getData().get(i).getProjectName();

            if (baseModel.getData().get(i).getProjectTypeID() == -1) {
                fragments.add(Pk_DayPk_Project_Detail_CoustFragment.newInstance(i,ReportID + "", ProjectID + "",baseModel));
            } else {
                fragments.add(Pk_DayPk_Project_Detail_Fragment.newInstance(i, ReportID + "", ProjectID + "", projectName, baseModel));
            }

        }
    }

    int tabCount = 0;

    private void tabViewSetView(TabLayout myTab) {
        myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        Slideviewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        myTab.setupWithViewPager(Slideviewpager);
//        Slideviewpager.setCurrentItem(1);
//        Slideviewpager.addOnPageChangeListener(this);

        if (projectId != null) {
            int size = baseModel.getData().size();
            for (int i = 0; i < size; i++) {
                if (projectId.equals(baseModel.getData().get(i).getProjectID() + "")) {
                    tabCount = i;
                    setpkbg(i);
                    break;
                }
            }
        }

        ac_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
//                tab_Name.setTextColor(Color.parseColor("#ffffff"));
//                StaticData.ViewScale(tab_Name, 185, 64);
//                initData(tab.getPosition());
                cententtxt.setText(baseModel.getData().get(tab.getPosition()).getProjectName());
//                Pk_DayPk_Project_Detail_Fragment.newInstance("0",baseModel.getData().get(tab.getPosition()).getProjectID()+"");

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
//                tab_Name.setTextColor(Color.parseColor("#95a0ab"));
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
//                tab.setCustomView(myAdapter.getTabView(postion));
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


//        public View getTabView(int position) {
//            View v = LayoutInflater.from(Pk_DayPk_Project_Detail.this).inflate(R.layout.pkfenrank_custom_tab, null);
//            TextView tab_Name = (TextView) v.findViewById(R.id.tab_Name);
//            tab_Name.setText(userArr.get(position));
//            return v;
//        }


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



    newPk_Model baseModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    baseModel = new Gson().fromJson(resultString, newPk_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
                        initLocaData(ac_tab_layout);
                        tabViewSetView(ac_tab_layout);
//                        resetTablayout(ac_tab_layout);
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

    public void guideFristData(final Context context, String baseUrl, final int type) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    isFristSee_Model isFristModel = new Gson().fromJson(resultString, isFristSee_Model.class);
                    if (isFristModel.isIsSuccess()) {
                        if (!isFristModel.getData().isIs_PK_Guide()) {

//                            isFristSee();
//                            Intent intent = new Intent(context, Pk_Guide.class);
//                            intent.putExtra("guideId", "5");
//                            startActivity(intent);
                            guideUtil.initGuide((Activity) context,5,1);
                            updguidePer_Data(context, saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_PK_Guide");//展示功能提醒页
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

    public void updguidePer_Data(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model Model = new Gson().fromJson(resultString, Base_Model.class);
                    if (Model.isIsSuccess()) {

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
