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
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import com.moying.energyring.Model.pk_Project_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.GuideUtil;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.pinyin.CharacterParser;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.waylenBaseView.groupadapter.BaseViewHolder;
import com.moying.energyring.waylenBaseView.groupadapter.GroupedGridLayoutManager;
import com.moying.energyring.waylenBaseView.groupadapter.GroupedListAdapter;
import com.moying.energyring.waylenBaseView.groupadapter.GroupedRecyclerViewAdapter;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Pk_DayPk_Project_Detail_RankAll extends FragmentActivity {
    //    private String titleName;
    private View my_tab_Lin;
    private TabLayout ac_tab_layout,tab_layout;
    private ViewPager Slideviewpager;
    private String projectId;
    private TextView cententtxt;
    private ImageView more_Img;
    private GuideUtil guideUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeprice);
        setContentView(R.layout.activity_daypk_project_detail_rankall);
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
        right_Btn.setVisibility(View.INVISIBLE);
        StaticData.ViewScale(title_Rel, 0, 88);
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(right_Btn, 40, 40);

        return_Btn.setOnClickListener(new return_Btn());
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private void initView() {
        RelativeLayout rab_Rel = (RelativeLayout) findViewById(R.id.rab_Rel);
         more_Img =  (ImageView)findViewById(R.id.more_Img);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        ac_tab_layout = (TabLayout) findViewById(R.id.ac_tab_layout);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);
        StaticData.ViewScale(ac_tab_layout, 0, 6);
        StaticData.ViewScale(rab_Rel, 0, 112);
        StaticData.ViewScale(more_Img, 40, 40);
        viewTouchDelegate.expandViewTouchDelegate(more_Img, 100, 100, 100, 100);
        more_Img.setOnClickListener(new more_Img());
    }

    private void initGuide() {
        guideFristData(Pk_DayPk_Project_Detail_RankAll.this, saveFile.BaseUrl + saveFile.GuidePerFirst_Url, 0);//展示功能提醒页
        guideUtil = GuideUtil.getInstance();
    }

    private void setpkbg(int tag) {
        cententtxt.setText(baseModel.getData().get(tag).getProjectName());
        ac_tab_layout.setScrollPosition(tag, 1F, true);//滑动到固定位置
        Slideviewpager.setCurrentItem(tag);
//        initData(projectId);
    }


    private class more_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showProject(Pk_DayPk_Project_Detail_RankAll.this, tab_layout);
        }
    }

    PopupWindow popupWindow;

    private void showProject(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_rankall_project, null);
        popupWindow = new BasePopupWindow(mContext);
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
        popupWindow.showAsDropDown(view);
        RecyclerView  rv_list = (RecyclerView) contentView.findViewById(R.id.rv_list);
//        StaticData.ViewScale(rv_list,0,1134);
        more_Img.setImageResource(R.drawable.rank_cha);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                more_Img.setImageResource(R.drawable.rank_more);
            }
        });
//        imgBadge(myflexbox);
        initlist(mContext,rv_list);
    }

    //加载弹框pk项目
    public void initlist(final Context context, RecyclerView rv_List) {
        GroupedListAdapter adapter = new GroupedListAdapter(context, project_Infication_Model);

        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
            }
        });
        adapter.setOnFooterClickListener(new GroupedRecyclerViewAdapter.OnFooterClickListener() {
            @Override
            public void onFooterClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
            }
        });
        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                     int groupPosition, int childPosition) {
                popupWindow.dismiss();
              String childprojectId =   project_Infication_Model.getData().get(groupPosition).get_Project_List().get(childPosition).getProjectID()+"";
                int size = baseModel.getData().size();
                for (int i = 0; i < size; i++) {
                    if (childprojectId.equals(baseModel.getData().get(i).getProjectID() + "")) {
                        setpkbg(i);
                        break;
                    }
                }
                // 实例化汉字转拼音类
                String ProjectName = project_Infication_Model.getData().get(groupPosition).get_Project_List().get(childPosition).getProjectName();
                CharacterParser characterParser = CharacterParser.getInstance();
                String pinyin = characterParser.getSelling(ProjectName);
                MobclickAgent.onEvent(Pk_DayPk_Project_Detail_RankAll.this, pinyin);//统计页签
//                int tag = (Integer) v.getTag();
//                setpkbg(tag);
            }
        });
        adapter.hasFooter(0);//不显示组尾
        rv_List.setAdapter(adapter);

        //直接使用GroupedGridLayoutManager实现子项的Grid效果
        GroupedGridLayoutManager gridLayoutManager = new GroupedGridLayoutManager(this, 5, adapter);
        rv_List.setLayoutManager(gridLayoutManager);

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
//        ListData(this, saveFile.BaseUrl + saveFile.My_ReportProject_List_Url);
        ListData(this, saveFile.BaseUrl + saveFile.dayPkProjectUrl);
        personData(this,saveFile.BaseUrl + saveFile.My_ProjectType_Url+"?Sort=true");
    }

    public List<String> userArr;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;

    private void initLocaData(TabLayout myTab) {
        myTab.setTabTextColors(Color.parseColor("#0095a0ab"), Color.parseColor("#00ffd800"));//初始颜色，选中颜色
        myTab.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorThridYellow));//进度条颜色
        myTab.setTabMode(TabLayout.GRAVITY_CENTER);

        tab_layout.setTabTextColors(ContextCompat.getColor(this,R.color.colorFristBlack), ContextCompat.getColor(this,R.color.colorFristBlack));//初始颜色，选中颜色
        tab_layout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorThridYellow));//进度条颜色
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置可以滑动 根据标签自适应宽度
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
                fragments.add(Pk_DayPk_Project_Detail_CoustFragment.newInstance(i,ReportID+"", ProjectID + "",baseModel));
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
        reflex(tab_layout);//修改下划线宽度

        if (projectId != null) {
            int size = baseModel.getData().size();
            for (int i = 0; i < size; i++) {
                if (projectId.equals(baseModel.getData().get(i).getProjectID() + "")) {
                    tabCount = i;
                    setpkbg(i);
                    break;
                }
            }
        }else{
            cententtxt.setText(baseModel.getData().get(tabCount).getProjectName());
        }

        ac_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cententtxt.setText(baseModel.getData().get(tab.getPosition()).getProjectName());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tab_layout.setupWithViewPager(Slideviewpager);
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cententtxt.setText(baseModel.getData().get(tab.getPosition()).getProjectName());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
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

    pk_Project_Model project_Infication_Model;
    public void personData(final Context context , String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    project_Infication_Model = new Gson().fromJson(resultString, pk_Project_Model.class);

//                    baseModel = new Gson().fromJson(resultString, Goal_Model.class);
                    if (project_Infication_Model.isIsSuccess() && !project_Infication_Model.getData().equals("[]")) {
//                        initlist(context);
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




    public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

//                    int dp10 = dip2px(tabLayout.getContext(), 5);
                    int mag = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_DayPk_Project_Detail_RankAll.this)) * 13);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = mag;
                        params.rightMargin = mag;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }



}
