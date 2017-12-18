package com.moying.energyring.myAcativity.Pk.Committ;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.moying.energyring.Clandar.CalendarAdapter;
import com.moying.energyring.Model.AllPerson_CalModel;
import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAcativity.Person.PersonMyCenter;
import com.moying.energyring.myAdapter.AllPersonOneDay_Adapter;
import com.moying.energyring.myAdapter.AllPerson_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.xrecycle.XRecyclerView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Leran_AllPerson extends Activity implements XRecyclerView.LoadingListener {

    SlidingUpPanelLayout mLayout;
    private static final String TAG = "Leran_AllPerson";
    int PageIndex;
    int pageSize;
    private String userID;
    XRecyclerView All_XRecy,oneday_XRecy;
    AllPerson_Adapter mAdapter;
    private Button right_Btn;

    private GridView gridView = null;
    private static int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private static int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private CalendarAdapter calV = null;
    private String currentDate = "";
    /**
     * 每次添加gridview到viewflipper中时给的标记
     */
    private int gvFlag = 0;
    private ViewFlipper flipper;
    private GestureDetector gestureDetector = null;
    private TextView currentMonth,month_lineTxt;
    private int lastday;
    private ImageView month_topimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leran__all_person);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#2b2a2a"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#ffffff"));
        right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setBackgroundResource(R.drawable.person_more);
        cententtxt.setText("公众承诺");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);
        StaticData.ViewScale(right_Btn, 44, 44);


        All_XRecy = (XRecyclerView) findViewById(R.id.All_XRecy);
        All_XRecy.setLoadingListener(this);
        All_XRecy.setPullRefreshEnabled(true);
        All_XRecy.setLoadingMoreEnabled(false);

        oneday_XRecy = (XRecyclerView) findViewById(R.id.oneday_XRecy);
        oneday_XRecy.setPullRefreshEnabled(false);
        oneday_XRecy.setLoadingMoreEnabled(false);

        RelativeLayout title_Rel = (RelativeLayout) findViewById(R.id.title_Rel);
        StaticData.ViewScale(title_Rel, 0, 120);

        // 添加头部
        View header = LayoutInflater.from(this).inflate(R.layout.allperson_adapter, (ViewGroup) findViewById(android.R.id.content), false);
        RelativeLayout mu_Rel = (RelativeLayout) header.findViewById(R.id.mu_Rel);
        Button mu_Btn = (Button) header.findViewById(R.id.mu_Btn);
        StaticData.ViewScale(mu_Rel, 714, 380);
        StaticData.ViewScale(mu_Btn, 399, 90);
        All_XRecy.addHeaderView(header);
        mu_Rel.setOnClickListener(new mu_Rel());

        Date date = new Date();//当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date);
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);
        lastday = StaticData.getMaxDayByYearMonth(year_c, month_c);//本月最后一天
//        DateMod();

        currentMonth = (TextView) findViewById(R.id.currentMonth);
        currentMonth.setText(year_c+"/"+month_c);
        month_lineTxt = (TextView) findViewById(R.id.month_lineTxt);
        month_topimg = (ImageView) findViewById(R.id.month_topimg);
        StaticData.ViewScale(month_topimg,28,14);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setPanelHeight(180);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset" + slideOffset);
                if (slideOffset == 1.0) {
                    month_lineTxt.setVisibility(View.GONE);
                    month_topimg.setVisibility(View.GONE);
                    String startTime = year_c + "-" + month_c + "-1";
                    String endTime = year_c + "-" + month_c + "-" + lastday;
                    allPersonData(saveFile.BaseUrl + saveFile.TargetDetails_ListUrl+"?StartDate=" + startTime + "&EndDate=" + endTime);
                }else{
                    month_lineTxt.setVisibility(View.VISIBLE);
                    month_topimg.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);

            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });


        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());

    }


    @Override
    protected void onResume() {
        super.onResume();
        PageIndex = 1;
        pageSize = 10;
        personData(saveFile.BaseUrl + saveFile.Target_ListUrl + "?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showPopupWindow(Leran_AllPerson.this, right_Btn);
        }
    }

    public class mu_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MobclickAgent.onEvent(Leran_AllPerson.this, "setagoal");//统计页签
            Intent intent = new Intent(Leran_AllPerson.this, Leran_Goal.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo, menu);
        MenuItem item = menu.findItem(R.id.action_toggle);
        if (mLayout != null) {
            if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.HIDDEN) {
                item.setTitle("显示");
            } else {
                item.setTitle("不显示");
            }
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_toggle: {
                if (mLayout != null) {
                    if (mLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN) {
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                        item.setTitle("显示");
                    } else {
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        item.setTitle("不显示");
                    }
                }
                return true;
            }
            case R.id.action_anchor: {
                if (mLayout != null) {
                    if (mLayout.getAnchorPoint() == 1.0f) {
                        mLayout.setAnchorPoint(0.7f);
//                        mLayout.offsetTopAndBottom(1);
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                        item.setTitle("不可点击");
                    } else {
                        mLayout.setAnchorPoint(1.0f);
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        item.setTitle("可点击");
                    }
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onRefresh() {
        PageIndex = 1;
        pageSize = 10;
        personData(saveFile.BaseUrl + saveFile.Target_ListUrl + "?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    @Override
    public void onLoadMore() {
        PageIndex = PageIndex + 1;
        pageSize = 10;
        personData(saveFile.BaseUrl + saveFile.Target_ListUrl + "?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    private List<AllPerson_Model.DataBean> baseModel;
    AllPerson_Model personModel;

    public void personData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        params.setConnectTimeout(10000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    if (PageIndex == 1) {
                        if (baseModel != null) {
                            baseModel.clear();
                        }
                        baseModel = new ArrayList<>();
                    }

                    personModel = new Gson().fromJson(resultString, AllPerson_Model.class);
                    if (personModel.isIsSuccess() && !personModel.getData().equals("[]")) {
                        baseModel.addAll(personModel.getData());
                        if (PageIndex == 1) {
                            All_XRecy.refreshComplete();
                            initlist(Leran_AllPerson.this);
                        } else {
                            All_XRecy.loadMoreComplete();
                            mAdapter.addMoreData(baseModel);
                        }
                    } else {
                        Toast.makeText(Leran_AllPerson.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Leran_AllPerson.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")){
                    Intent intent = new Intent(Leran_AllPerson.this,LoginRegister.class);
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

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        mAdapter = new AllPerson_Adapter(context, baseModel, personModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new AllPerson_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Leran_AllPerson.this, Leran_AllPersonDetails.class);
                intent.putExtra("TargetID", baseModel.get(position).getTargetID() + "");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }

    private void showPopupWindow(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_more, null);
        final PopupWindow popupWindow = new BasePopupWindow(mContext);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
        popupWindow.showAsDropDown(view);
        LinearLayout popup_Lin = (LinearLayout) contentView.findViewById(R.id.popup_Lin);
        LinearLayout shuo_Lin = (LinearLayout) contentView.findViewById(R.id.shuo_Lin);
        LinearLayout hostoy_Lin = (LinearLayout) contentView.findViewById(R.id.hostoy_Lin);
        LinearLayout hostoy_Lintwo = (LinearLayout) contentView.findViewById(R.id.hostoy_Lintwo);
        ImageView person_shuo = (ImageView) contentView.findViewById(R.id.person_shuo);
        ImageView person_hostoy = (ImageView) contentView.findViewById(R.id.person_hostoy);
        ImageView person_hostoytwo = (ImageView) contentView.findViewById(R.id.person_hostoytwo);
        StaticData.ViewScale(popup_Lin, 240, 320);
        StaticData.ViewScale(person_shuo, 40, 40);
        StaticData.ViewScale(person_hostoy, 40, 40);
        StaticData.ViewScale(person_hostoytwo, 40, 40);
        shuo_Lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                showGui(mContext, right_Btn);
            }
        });
        hostoy_Lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent intent = new Intent(Leran_AllPerson.this, Leran_AllHostory.class);
                startActivity(intent);

            }
        });
        hostoy_Lintwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent intent = new Intent(Leran_AllPerson.this, PersonMyCenter.class);
                intent.putExtra("UserID","0");
                intent.putExtra("tabType","2");
                startActivity(intent);
//                Intent intent = new Intent(Leran_AllPerson.this, pkDay.class);
//                startActivity(intent);
            }
        });
    }

    private void showGui(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_gui, null);
        PopupWindow popupWindow = new BasePopupWindow(mContext);
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(false);
        popupWindow.setContentView(contentView);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        TextView popup_Txt = (TextView) contentView.findViewById(R.id.popup_Txt);
        StaticData.ViewScale(popup_Txt, 540, 960);
        String txtString = "公众承诺区目标最低天数：\n5天，低于5天的目标无法设立\n\n完成当日目标，积分奖励政策如下：\n5-10天 10分/天\n11-20天 30分/天\n21天以上 50分/天\n\n若有一天未达成承诺，\n所有积分均无法获得\n\n任何情况下取消承诺\n将无法获得奖励积分，\n并且追加扣除100分作为失信惩罚";
        TextsColor(12, 27, txtString.length(), txtString, popup_Txt);
    }

    public void TextsColor(int start, int end, int allSize, String allText, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(allText);
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#262626")), 0, allSize, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#e71211")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#e71211")), 44, 82, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#e71211")), 115, 126, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        myTxt.setText(styledText);
    }


    public void DateMod() {
        Date date = new Date();//当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date);
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);
        int lastday = StaticData.getMaxDayByYearMonth(year_c, month_c);//本月最后一天
    }


    public void calendarview() {
        gestureDetector = new GestureDetector(this, new MyGestureListener());

        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.removeAllViews();

        calV = new CalendarAdapter(this, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,baseArr);
        addGridView();
        gridView.setAdapter(calV);
        flipper.addView(gridView, 0);
        addTextToTopTextView(currentMonth);
    }
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
            if (e1.getX() - e2.getX() > 120) {
                // 像左滑动

                enterNextMonth(gvFlag);
                return true;
            } else if (e1.getX() - e2.getX() < -120) {
                // 向右滑动
                enterPrevMonth(gvFlag);
                return true;
            }
            return false;
        }
    }
    /**
     * 移动到下一个月
     *
     * @param gvFlag
     */
    private void enterNextMonth(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpMonth++; // 下一个月
        calV = new CalendarAdapter(this, this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,baseArr);
        gridView.setAdapter(calV);
        addTextToTopTextView(currentMonth); // 移动到下一月后，将当月显示在头标题中
        gvFlag++;
        flipper.addView(gridView, gvFlag);
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
        flipper.showNext();
        flipper.removeViewAt(0);
    }

    /**
     * 移动到上一个月
     *
     * @param gvFlag
     */
    private void enterPrevMonth(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpMonth--; // 上一个月
        calV = new CalendarAdapter(this, this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,baseArr);
        gridView.setAdapter(calV);
        gvFlag++;

        addTextToTopTextView(currentMonth); // 移动到上一月后，将当月显示在头标题中
        flipper.addView(gridView, gvFlag);
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
        flipper.showPrevious();
        flipper.removeViewAt(0);
    }

    /**
     * 添加头部的年份 闰哪月等信息
     *
     * @param view
     */
    public void addTextToTopTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        textDate.append(calV.getShowYear()).append("/").append(calV.getShowMonth()).append("\t");
        if (Integer.valueOf(calV.getShowMonth()) == 1) {
        } else if (Integer.valueOf(calV.getShowMonth()) == 12) {
        }
        view.setText(textDate);
    }

    private void addGridView() {//日历控件
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 取得屏幕的宽度和高度
        StaticData.layoutParamsScale(params,0,430);
        gridView = new GridView(this);
        gridView.setNumColumns(7);
//        gridView.setColumnWidth(40);
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setVerticalScrollBarEnabled(false);//滚动条隐藏
        gridView.setBackgroundResource(R.drawable.month_layerlist);//底部阴影
        gridView.setOnTouchListener(new View.OnTouchListener() {
            // 将gridview中的触摸事件回传给gestureDetector
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return gestureDetector.onTouchEvent(event);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                int startPosition = calV.getStartPositon();
                int endPosition = calV.getEndPosition();
                if (startPosition <= position + 7 && position <= endPosition - 7) {
//                    calV.setCurrFocusId(position);
//                    calV.notifyDataSetChanged();
                    String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0]; // 这一天的阳历
                    String scheduleYear = calV.getShowYear();
                    String scheduleMonth = calV.getShowMonth();
                    Log.d("时间", scheduleYear + "-" + scheduleMonth + "-" + scheduleDay);

                    String Time = scheduleYear + "-" + scheduleMonth + "-" + scheduleDay;
//                    String endTime = scheduleYear + "-" + scheduleMonth + "-" + scheduleDay;
//                    dayData(saveFile.BaseUrl + "/Target/My_TargetDetails_List_Android?UserID=" + UserID + "&StartDate=" + Time + "&EndDate=" + Time);
                    oneDayData(saveFile.BaseUrl + saveFile.TargetDetails_ListUrl+"?StartDate=" + Time + "&EndDate=" + Time);
                    calV.setSeclection(position);//点击变色
                    calV.notifyDataSetChanged();

                }
            }
        });
        gridView.setLayoutParams(params);
    }

    public static int getMaxDayByYearMonth(int year, int month) {
        int maxDay = 0;
        int day = 1;
        /**
         * 与其他语言环境敏感类一样，Calendar 提供了一个类方法 getInstance，
         * 以获得此类型的一个通用的对象。Calendar 的 getInstance 方法返回一
         * 个 Calendar 对象，其日历字段已由当前日期和时间初始化：
         */
        Calendar calendar = Calendar.getInstance();
        /**
         * 实例化日历各个字段,这里的day为实例化使用
         */
        calendar.set(year, month - 1, day);
        /**
         * Calendar.Date:表示一个月中的某天
         * calendar.getActualMaximum(int field):返回指定日历字段可能拥有的最大值
         */
        maxDay = calendar.getActualMaximum(Calendar.DATE);
        return maxDay;
    }

    int nowMonthCount = 0;
    //
    AllPerson_CalModel baseArr;
    public void allPersonData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        params.setConnectTimeout(10000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                baseArr = new Gson().fromJson(resultString, AllPerson_CalModel.class);
//               int size =  baseArr.getData().length;
                if (baseArr.isIsSuccess()) {
//                    if (nowMonthCount == 0){
                        calendarview();
//                    }else if (nowMonthCount == 1){
//                    }
                } else {
//                    Toast.makeText(Leran_AllPerson.this, baseArr.getMsg(), 3000).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")){
                    Intent intent = new Intent(Leran_AllPerson.this,LoginRegister.class);
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

    AllPerson_CalModel oneArr;
    //某一天目标
    public void oneDayData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        params.setConnectTimeout(10000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                  oneArr = new Gson().fromJson(resultString, AllPerson_CalModel.class);
                if (oneArr != null){
                    if (oneArr.isIsSuccess() && !oneArr.getData().equals("{}")) {
                        oneDayinitlist(Leran_AllPerson.this,oneArr);
                    } else {
                    }
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")){
                    Intent intent = new Intent(Leran_AllPerson.this,LoginRegister.class);
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

    AllPersonOneDay_Adapter oneDAymAdapter;
    public void oneDayinitlist(Context context, final AllPerson_CalModel myModel) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        oneday_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        oneday_XRecy.setHasFixedSize(true);
         oneDAymAdapter = new AllPersonOneDay_Adapter(context, myModel);
        oneday_XRecy.setAdapter(oneDAymAdapter);
        oneDAymAdapter.setOnItemClickLitener(new AllPersonOneDay_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Leran_AllPerson.this, Leran_AllPersonDetails.class);
                intent.putExtra("TargetID", myModel.getData().getTarget_List().get(position).getTargetID() + "");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }



}
