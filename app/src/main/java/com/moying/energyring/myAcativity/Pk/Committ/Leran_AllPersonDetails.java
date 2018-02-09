package com.moying.energyring.myAcativity.Pk.Committ;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.moying.energyring.Clandar.Detail_DateAdapter;
import com.moying.energyring.Clandar.SpecialCalendar;
import com.moying.energyring.Model.AllPersonDetails_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Leran_AllPersonDetails extends Activity implements GestureDetector.OnGestureListener {
    private ViewFlipper flipper1 = null;
    private GridView gridView = null;
    private GestureDetector gestureDetector = null;
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private int week_c = 0;
    private int week_num = 0;
    private String currentDate = "";
    private static int jumpWeek = 0;
    private static int jumpMonth = 0;
    private static int jumpYear = 0;
    private Detail_DateAdapter dateAdapter;
    private int daysOfMonth = 0; // 某月的天数
    private int dayOfWeek = 0; // 具体某月的第一天是星期几
    private int weeksOfMonth = 0;
    private SpecialCalendar sc = null;
    //    private Calendar sc = null;
    private boolean isLeapyear = false; // 是否为闰年
    private int selectPostion = 0;
    private String dayNumbers[] = new String[7];
    private TextView tvDate;
    private int currentYear;
    private int currentMonth;
    private int currentWeek;
    private int currentDay;
    private int currentNum;
    private boolean isStart;// 是否是交接的月初

    //    String startTime, endTime,count,ProjectID ,TargetID;
    private String TargetID;
    TextView timeTxt, dayTxt, start_Txt, finsh_Txt;
    private TextView title_Name;
    private ProgressBar mypross;

    public void Overview(String time) {
//        Date date = new Date();  //显示的时间
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
//        currentDate = sdf.format(date);

        year_c = Integer.parseInt(time.split("-")[0]);
        month_c = Integer.parseInt(time.split("-")[1]);
        day_c = Integer.parseInt(time.split("-")[2]);//9

        currentYear = year_c;
        currentMonth = month_c;
        currentDay = day_c;

        sc = new SpecialCalendar();
//        sc.setTime(Date);
        getCalendar(year_c, month_c);//输入要显示的年月
        week_num = getWeeksOfMonth();//12月份week_num是5
        currentNum = week_num;//5
        if (dayOfWeek == 7) {
            week_c = day_c / 7 + 1;
        } else {
            if (day_c <= (7 - dayOfWeek)) {
                week_c = 1;
            } else {
                if ((day_c - (7 - dayOfWeek)) % 7 == 0) {
                    week_c = (day_c - (7 - dayOfWeek)) / 7 + 1;
                    Log.i("life", "day_c = " + day_c);
                } else {
                    week_c = (day_c - (7 - dayOfWeek)) / 7 + 2;
                }
            }
        }
        currentWeek = week_c;
        getCurrent();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leran_details);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();

        Intent intent = getIntent();
         TargetID = intent.getStringExtra("TargetID");

        title_Name = (TextView) findViewById(R.id.title_Name);
        timeTxt = (TextView) findViewById(R.id.timeTxt);
        dayTxt = (TextView) findViewById(R.id.dayTxt);
        start_Txt = (TextView) findViewById(R.id.start_Txt);
        finsh_Txt = (TextView) findViewById(R.id.finsh_Txt);
        mypross = (ProgressBar) findViewById(R.id.mypross);
        StaticData.ViewScale(mypross, 650, 14);
        LinearLayout ll_week = (LinearLayout) findViewById(R.id.ll_week);
        StaticData.ViewScale(ll_week, 0, 58);

        detailsData(saveFile.BaseUrl + saveFile.Target_DetailsUrl + "?TargetID="+TargetID);
//
//        detailsData(saveFile.BaseUrl + "/Target/My_TargetDetails_List?UserID=" + 2066 + "&StartDate=" + "2017-4-1" + "&EndDate=" + "2017-4-30");


    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            showOut(Leran_AllPersonDetails.this,title_Name);
        }
    }

    private void initTitle(){
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#2b2a2a"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#ffffff"));
        Button right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setBackgroundResource(R.drawable.persondetails_out);
        cententtxt.setText("目标详情");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);
        StaticData.ViewScale(right_Btn, 44, 44);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
    }

    private void showOut(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_save, null);
        final PopupWindow popupWindow = new BasePopupWindow(mContext);
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

        View line = (View) contentView.findViewById(R.id.line);
        line.setVisibility(View.GONE);
        Button topbtn = (Button) contentView.findViewById(R.id.topbtn);
        topbtn.setVisibility(View.GONE);
        Button belowbtn = (Button) contentView.findViewById(R.id.belowbtn);
        belowbtn.setTextColor(Color.parseColor("#F24d40"));
        belowbtn.setText("退出目标");
        Button cancelbtn = (Button) contentView.findViewById(R.id.cancelbtn);
        cancelbtn.setTextColor(Color.parseColor("#F24d40"));
        StaticData.ViewScale(belowbtn, 700, 102);
        StaticData.ViewScale(cancelbtn, 700, 88);

        belowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                showOutConfirm(mContext,title_Name);
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }
   private void showOutConfirm(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_outsure, null);
        final PopupWindow popupWindow = new BasePopupWindow(mContext);
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
       LinearLayout my_Lin = (LinearLayout) contentView.findViewById(R.id.my_Lin);
       StaticData.ViewScale(my_Lin, 500, 300);
       TextView finshTxt = (TextView) contentView.findViewById(R.id.finshTxt);
       finshTxt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                OutData(saveFile.BaseUrl + saveFile.Target_DelsUrl);
           }
       });

    }



    /**
     * 获取两个日间隔几天
     */
    public int getDateDays(String startTime, String endTime) {
        int days = 0;
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date startDate = sdf.parse(startTime);
            c.setTime(startDate);//赋值开始日期
            Date endDate = sdf.parse(endTime);
            long betweenTime = endDate.getTime() - startDate.getTime();//两个日期间隔
            days = (int) (betweenTime / 1000 / 60 / 60 / 24) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 判断某年某月所有的星期数
     *
     * @param year
     * @param month
     */
    public int getWeeksOfMonth(int year, int month) {
// 先判断某月的第一天为星期几
        int preMonthRelax = 0;
        int dayFirst = getWhichDayOfWeek(year, month);

        int days = sc.getDaysOfMonth(sc.isLeapYear(year), month);
        if (dayFirst != 7) {
            preMonthRelax = dayFirst;
        }
        if ((days + preMonthRelax) % 7 == 0) {
            weeksOfMonth = (days + preMonthRelax) / 7;
        } else {
            weeksOfMonth = (days + preMonthRelax) / 7 + 1;
        }
        return weeksOfMonth;


    }

    /**
     * 判断某年某月的第一天为星期几
     *
     * @param year
     * @param month
     * @return
     */
    public int getWhichDayOfWeek(int year, int month) {
        return sc.getWeekdayOfMonth(year, month);
    }

    /**
     * @param year
     * @param month
     */
    public int getLastDayOfWeek(int year, int month) {
        return sc.getWeekDayOfLastMonth(year, month,
                sc.getDaysOfMonth(isLeapyear, month));
    }

    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year); // 是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
    }

    public int getWeeksOfMonth() {
        int preMonthRelax = 0;
        if (dayOfWeek != 7) {//2014年的12月1号是星期一，所以preMonthRelax的值是1
            preMonthRelax = dayOfWeek;
        }

        if ((daysOfMonth + preMonthRelax) % 7 == 0) {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7;
        } else {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7 + 1;//所以2014年的12月的weeksOfMonth值是1
        }

        return weeksOfMonth;
    }


    private void addGridView() {
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.CENTER;
//        StaticData.layoutParamsScale(params,640,124);
        gridView = new GridView(this);
        gridView.setNumColumns(7);
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setBackgroundResource(R.drawable.month_layerlist);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return Leran_AllPersonDetails.this.gestureDetector.onTouchEvent(event);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPostion = position;
                dateAdapter.setSeclection(position);
                dateAdapter.notifyDataSetChanged();
                tvDate.setText(dateAdapter.getCurrentYear(selectPostion) + "-" + dateAdapter.getCurrentMonth(selectPostion));//+ "月" + dayNumbers[position] + "日"
            }
        });
//        gridView.setLayoutParams(params);
    }


    @Override
    protected void onPause() {
        super.onPause();
        jumpWeek = 0;
    }


    @Override
    public boolean onDown(MotionEvent e) {


        return false;
    }


    @Override
    public void onShowPress(MotionEvent e) {


    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }


    @Override
    public void onLongPress(MotionEvent e) {


    }


    /**
     * 重新计算当前的年月
     */
    public void getCurrent() {
        if (currentWeek > currentNum) {
            if (currentMonth + 1 <= 12) {
                currentMonth++;
            } else {
                currentMonth = 1;
                currentYear++;
            }
            currentWeek = 1;
            currentNum = getWeeksOfMonth(currentYear, currentMonth);
        } else if (currentWeek == currentNum) {
            if (getLastDayOfWeek(currentYear, currentMonth) == 6) {
            } else {
                if (currentMonth + 1 <= 12) {
                    currentMonth++;
                } else {
                    currentMonth = 1;
                    currentYear++;
                }
                currentWeek = 1;
                currentNum = getWeeksOfMonth(currentYear, currentMonth);
            }


        } else if (currentWeek < 1) {
            if (currentMonth - 1 >= 1) {
                currentMonth--;
            } else {
                currentMonth = 12;
                currentYear--;
            }
            currentNum = getWeeksOfMonth(currentYear, currentMonth);
            currentWeek = currentNum - 1;
        }
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int gvFlag = 0;
        if (e1.getX() - e2.getX() > 80) {
            addGridView();
            currentWeek++;
            getCurrent();
            dateAdapter = new Detail_DateAdapter(this, getResources(), currentYear, currentMonth, currentWeek, currentNum, selectPostion, currentWeek == 1 ? true : false, startTime, endTime, baseArr.getData().getTarget_Deials_List());
            dayNumbers = dateAdapter.getDayNumbers();
            dateAdapter.setSeclection(1000);
            gridView.setAdapter(dateAdapter);
            tvDate.setText(dateAdapter.getCurrentYear(selectPostion) + "-" + dateAdapter.getCurrentMonth(selectPostion));// + "月" + dayNumbers[selectPostion] + "日"
            gvFlag++;
            flipper1.addView(gridView, gvFlag);
            this.flipper1.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
            this.flipper1.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            this.flipper1.showNext();
            flipper1.removeViewAt(0);
            return true;
        } else if (e1.getX() - e2.getX() < -80) {
            addGridView();
            currentWeek--;
            getCurrent();
            dateAdapter = new Detail_DateAdapter(this, getResources(), currentYear, currentMonth, currentWeek, currentNum, selectPostion, currentWeek == 1 ? true : false, startTime, endTime, baseArr.getData().getTarget_Deials_List());
            dayNumbers = dateAdapter.getDayNumbers();
            dateAdapter.setSeclection(1000);
            gridView.setAdapter(dateAdapter);
            tvDate.setText(dateAdapter.getCurrentYear(selectPostion) + "-" + dateAdapter.getCurrentMonth(selectPostion));// + "月"+ dayNumbers[selectPostion] + "日"
            gvFlag++;
            flipper1.addView(gridView, gvFlag);
            this.flipper1.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
            this.flipper1.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
            this.flipper1.showPrevious();
            flipper1.removeViewAt(0);
            return true;
// }
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

    //目标详情
    AllPersonDetails_Model baseArr;

    public void detailsData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                baseArr = new Gson().fromJson(resultString, AllPersonDetails_Model.class);
                if (baseArr.isIsSuccess()) {

                    detailsDataMod(baseArr);
                } else {
                    Toast.makeText(Leran_AllPersonDetails.this, baseArr.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")){
                    Intent intent = new Intent(Leran_AllPersonDetails.this,LoginRegister.class);
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

    String startTime, endTime;

    public void detailsDataMod(AllPersonDetails_Model model) {
        AllPersonDetails_Model.DataBean.TargetListBean baseModel = model.getData().getTarget_List().get(0);
        startTime = "2017-04-15";
        endTime = "2017-04-20";

        startTime = StaticData.getUserDate(baseModel.getStartDate().replace("/","-"));
        endTime = StaticData.getUserDate(baseModel.getEndDate().replace("/","-"));

        title_Name.setText(baseModel.getProjectName());
        timeTxt.setText(baseModel.getTotalDays() + "天");
        dayTxt.setText(baseModel.getReportNum() + baseModel.getProjectUnit());

        if (saveFile.getBig(baseModel.getStartDate())) {//判断承诺是否开始 false是目标没开始
            String TxtString = "完成目标第" + baseModel.getFinishDays() + "/" + baseModel.getTotalDays() + "天";
            int allSize = 7 + String.valueOf(baseModel.getFinishDays()).length() + String.valueOf(baseModel.getTotalDays()).length();
            TextsColor(5, allSize - 1, allSize, TxtString, start_Txt);
        } else {
            String TxtString = "目标将于" + saveFile.getUserDate(baseModel.getStartDate()) + "开始";
            int allSize = 6 + saveFile.getUserDate(baseModel.getStartDate()).length();
            TextsColor(4, allSize - 2, allSize, TxtString, start_Txt);
        }

        Integer finshTxt = (int) ((double) baseModel.getFinishDays() / (double) baseModel.getTotalDays() * 100);
        finsh_Txt.setText(finshTxt + "%");
        mypross.setMax(baseModel.getTotalDays());
        mypross.setProgress(baseModel.getFinishDays());


        Overview(startTime);
        tvDate = (TextView) findViewById(R.id.tv_date);

        int month = Integer.parseInt(startTime.split("-")[1]);
        tvDate.setText(startTime.split("-")[0] + "-" + month);

        gestureDetector = new GestureDetector(this);
        flipper1 = (ViewFlipper) findViewById(R.id.flipper1);
        dateAdapter = new Detail_DateAdapter(this, getResources(), currentYear, currentMonth, currentWeek, currentNum, selectPostion, currentWeek == 1 ? true : false, startTime, endTime, model.getData().getTarget_Deials_List());
        addGridView();
        dayNumbers = dateAdapter.getDayNumbers();
        gridView.setAdapter(dateAdapter);
        selectPostion = dateAdapter.getTodayPosition();
        gridView.setSelection(selectPostion);
        flipper1.addView(gridView, 0);
    }

    public void TextsColor(int start, int end, int allSize, String allText, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(allText);
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#989797")), 0, allSize, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        myTxt.setText(styledText);
    }

    //退出目标
    public void OutData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
//        params.addBodyParameter("UserID", LoginSession.getSession().getUserInfo().getUse_id()+"");
//        params.addBodyParameter("Token", LoginSession.getSession().getUserInfo().getToken());
        params.addBodyParameter("TargetID", TargetID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                Base_Model model = new Gson().fromJson(resultString, Base_Model.class);
                if (model.isIsSuccess()) {
                    finish();
                } else {
                    Toast.makeText(Leran_AllPersonDetails.this, model.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")){
                    Intent intent = new Intent(Leran_AllPersonDetails.this,LoginRegister.class);
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
