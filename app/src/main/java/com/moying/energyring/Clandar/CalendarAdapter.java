package com.moying.energyring.Clandar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.moying.energyring.Model.AllPerson_CalModel;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Pk.Committ.Leran_AllPerson;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * 日历gridview中的每一个item显示的textview
 *
 * @author chaogege
 */
public class CalendarAdapter extends BaseAdapter {
    private int lastday;
    private boolean isLeapyear = false; // 是否为闰年
    private int daysOfMonth = 0; // 某月的天数
    private int dayOfWeek = 0; // 具体某一天是星期几
    private int lastDaysOfMonth = 0; // 上一个月的总天数
    private Context context;
    private String[] dayNumber = new String[42]; // 一个gridview中的日期存入此数组中
    private String[] dayNumberbot = new String[42];
    // private static String week[] = {"周日","周一","周二","周三","周四","周五","周六"};
    private SpecialCalendar sc = null;
    //	private LunarCalendar lc = null;
    private Resources res = null;
    private Drawable drawable = null;

    private String currentYear = "";
    private String currentMonth = "";
    private String currentDay = "";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    private int currentFlag = -1; // 用于标记当天
    private int[] schDateTagFlag = null; // 存储当月所有的日程日期

    private String showYear = ""; // 用于在头部显示的年份
    private String showMonth = ""; // 用于在头部显示的月份
    private String animalsYear = "";
    private String leapMonth = ""; // 闰哪一个月
    private String cyclical = ""; // 天干地支
    // 系统当前时间
    private String sysDate = "";
    private String sys_year = "";
    private String sys_month = "";
    private String sys_day = "";
    private int currFocusId;
    AllPerson_CalModel baseArr;
    private int clickFlag = -1; // 用于点击

    public int getCurrFocusId() {
        return currFocusId;
    }

    public void setCurrFocusId(int currFocusId) {
        this.currFocusId = currFocusId;
    }

//    private UrLClient urlclient;
//    private Json JsonGet = new Json();

    public CalendarAdapter() {
        Date date = new Date();
        sysDate = sdf.format(date); // 当期日期
        sys_year = sysDate.split("-")[0];
        sys_month = sysDate.split("-")[1];
        sys_day = sysDate.split("-")[2];


    }

    private ArrayList<TextView> textarr;

    public CalendarAdapter(Context context, Resources rs, int jumpMonth, int jumpYear, int year_c, int month_c, int day_c, AllPerson_CalModel baseArr) {
        this();
        this.context = context;
        sc = new SpecialCalendar();
//		lc = new LunarCalendar();
        this.res = rs;
        if (textarr != null) {
            textarr.clear();
        }
        textarr = new ArrayList<TextView>();
        int stepYear = year_c + jumpYear;
        int stepMonth = month_c + jumpMonth;
        if (stepMonth > 0) {
            // 往下一个月滑动
            if (stepMonth % 12 == 0) {
                stepYear = year_c + stepMonth / 12 - 1;
                stepMonth = 12;
            } else {
                stepYear = year_c + stepMonth / 12;
                stepMonth = stepMonth % 12;
            }
        } else {
            // 往上一个月滑动
            stepYear = year_c - 1 + stepMonth / 12;
            stepMonth = stepMonth % 12 + 12;
            if (stepMonth % 12 == 0) {

            }
        }

        currentYear = String.valueOf(stepYear); // 得到当前的年份
        currentMonth = String.valueOf(stepMonth); // 得到本月
        // （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
        currentDay = String.valueOf(day_c); // 得到当前日期是哪天
        lastday = Leran_AllPerson.getMaxDayByYearMonth(Integer.valueOf(currentYear), Integer.valueOf(currentMonth));//本月最后一天
        getCalendar(Integer.parseInt(currentYear), Integer.parseInt(currentMonth));

        this.baseArr = baseArr;

        String startTime = currentYear + "-" + currentMonth + "-1";
        String endTime = currentYear + "-" + currentMonth + "-" + lastday;
        allPersonData(saveFile.BaseUrl + saveFile.TargetDetails_ListUrl+"?StartDate=" + startTime + "&EndDate=" + endTime);
    }

    public CalendarAdapter(Context context, Resources rs, int year, int month, int day) {
        this();
        this.context = context;
        sc = new SpecialCalendar();
//		lc = new LunarCalendar();
        this.res = rs;
        currentYear = String.valueOf(year);// 得到跳转到的年份
        currentMonth = String.valueOf(month); // 得到跳转到的月份
        currentDay = String.valueOf(day); // 得到跳转到的天
        getCalendar(Integer.parseInt(currentYear), Integer.parseInt(currentMonth));
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dayNumber.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public boolean isColor(int pos, AllPerson_CalModel.DataBean myBean) {
        boolean isboolean = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String nowTime = currentYear + "-" + currentMonth + "-" + dayNumber[pos].split("\\.")[0];
        Date date = null, curryDate = null;

        for (int i = 0; i < myBean.getTarget_Deials_List().size(); i++) {
            try {
                date = sdf.parse(nowTime);
                curryDate = sdf.parse(myBean.getTarget_Deials_List().get(i).getReportDate());
                if (curryDate.equals(date)) {
                    isboolean = true;
                    break;
                } else {
                    isboolean = false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return isboolean;
    }

    //匹配红色的颜色
    public boolean redCorlor(int pos, AllPerson_CalModel.DataBean myBean) {
        boolean redboolean = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            String nowTime = currentYear + "-" + currentMonth + "-" + dayNumber[pos].split("\\.")[0];
            Date nowDate = sdf.parse(nowTime);
            for (int i = 0; i < myBean.getTarget_Deials_List().size(); i++) {

                Date sDate = sdf.parse(myBean.getTarget_Deials_List().get(i).getReportDate());

//                if (nowDate.equals(sDate) && myBean.getTarget_Deials_List().get(i).getIs_Finish() == 1) {
                if (nowDate.equals(sDate) && myBean.getTarget_Deials_List().get(i).isIs_Finish()) {
                    redboolean = true;
                    break;
                } else {
                    redboolean = false;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return redboolean;
    }

    // 标识选择的Item
    public void setSeclection(int position) {
//        clickTemp = position;
        clickFlag = position;
    }


    int dataPos = 0;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calendarmonth_item, null);
        }

        RelativeLayout calenRel = (RelativeLayout) convertView.findViewById(R.id.calenRel);
        StaticData.ViewScale(calenRel, 0, 70);
        TextView itemtxt = (TextView) convertView.findViewById(R.id.tvtext);
        StaticData.ViewScale(itemtxt, 42, 42);
        TextView lireuntxt = (TextView) convertView.findViewById(R.id.lireuntxt);
        StaticData.ViewScale(lireuntxt, 12, 10);
        lireuntxt.setTextColor(Color.parseColor("#dc5656"));
        String d = dayNumber[position].split("\\.")[0];
        itemtxt.setText(d);

        if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {//只显示当前月其他隐藏
            itemtxt.setTextColor(android.graphics.Color.parseColor("#989797"));
            itemtxt.setVisibility(TextView.VISIBLE);
//
            if (sys_year.equals(currentYear) && sys_month.equals(currentMonth)) {//true是当前月份
                if (position == currentFlag) {
                    lireuntxt.setVisibility(View.VISIBLE);
                    lireuntxt.setBackgroundResource(R.drawable.calendar_today);
                }
            }

            if (baseArr.getData().getTarget_Deials_List() != null) {
                if (isColor(position, baseArr.getData())) {
                    itemtxt.setTextColor(Color.WHITE);

                    if (redCorlor(position, baseArr.getData())) {//匹配红色
                        itemtxt.setBackgroundResource(R.drawable.calendar_item_red);
                    } else {
                        itemtxt.setBackgroundResource(R.drawable.calendar_item_gray);
                    }
                }
            }

            if (clickFlag == position) {
                itemtxt.setSelected(true);
                itemtxt.setTextColor(Color.parseColor("#ffe313"));
            }


        } else {
            itemtxt.setVisibility(TextView.INVISIBLE);
            lireuntxt.setVisibility(View.INVISIBLE);

        }
        return convertView;
    }

    // 得到某年的某月的天数且这月的第一天是星期几
    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year); // 是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
        lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1); // 上一个月的总天数
        Log.d("DAY", isLeapyear + " ======  " + daysOfMonth + "  ============  " + dayOfWeek + "  =========   " + lastDaysOfMonth);
        getweek(year, month);
        matchScheduleDate(year, month);
    }

    // 将一个月中的每一天的值添加入数组dayNuber中
    public void getweek(int year, int month) {
        int j = 1;
        int flag = 0;

        // 得到当前月的所有日程日期(这些日期需要标记)

        for (int i = 0; i < dayNumber.length; i++) {
            // 周一
            // if(i<7){
            // dayNumber[i]=week[i]+"."+" ";
            // }
            if (i < dayOfWeek) { // 前一个月
                int temp = lastDaysOfMonth - dayOfWeek + 1;
                dayNumber[i] = (temp + i) + ".1";

            } else if (i < daysOfMonth + dayOfWeek) { // 本月
                String day = String.valueOf(i - dayOfWeek + 1); // 得到的日期
                dayNumber[i] = i - dayOfWeek + 1 + ".1";
                // 对于当前月才去标记当前日期
                if (currentYear.equals(String.valueOf(year)) && currentMonth.equals(String.valueOf(month)) && currentDay.equals(day)) {
                    // 标记当前日期
                    currentFlag = i;
                }
                currFocusId = currentFlag;
                setShowYear(String.valueOf(year));
                setShowMonth(String.valueOf(month));
            } else { // 下一个月
                dayNumber[i] = j + ".1";
                j++;
            }
        }


        String abc = "";
        for (int i = 0; i < dayNumber.length; i++) {
            abc = abc + dayNumber[i] + ":";
        }
        Log.d("DAYNUMBER", abc);

    }

    private String[] datearr = {"2015-5-20", "2015-5-26", "2015-5-28", "2015-6-20"};

    private ArrayList<Integer> selectarr = new ArrayList<Integer>();

    public void matchScheduleDate(int year, int month) {
        int j = 1;
        // 得到当前月的所有日程日期(这些日期需要标记)
        for (int i = 0; i < dayNumber.length; i++) {
            if (i < daysOfMonth + dayOfWeek) { // 本月
                String day = String.valueOf(i - dayOfWeek + 1); // 得到的日期
                for (int m = 0; m < datearr.length; m++) {
                    String selectyear = datearr[m].split("-")[0];
                    String selectmonth = datearr[m].split("-")[1];
                    String selectday = datearr[m].split("-")[2];
                    if (selectyear.equals(String.valueOf(year)) && selectmonth.equals(String.valueOf(month)) && selectday.equals(day)) {
                        // 标记当前日期
                        selectarr.add(i);
                    }
                }
            }
        }

        String abc = "";
        for (int i = 0; i < dayNumber.length; i++) {
            abc = abc + dayNumber[i] + ":";
        }
        Log.d("DAYNUMBER", abc);
    }

    public String gettoday() {
        return sys_day;
    }

    /**
     * 点击每一个item时返回item中的日期
     *
     * @param position
     * @return
     */
    public String getDateByClickItem(int position) {
        return dayNumber[position];
    }

    /**
     * 在点击gridView时，得到这个月中第一天的位置
     *
     * @return
     */
    public int getStartPositon() {
        return dayOfWeek + 7;
    }

    /**
     * 在点击gridView时，得到这个月中最后一天的位置
     *
     * @return
     */
    public int getEndPosition() {
        return (dayOfWeek + daysOfMonth + 7) - 1;
    }

    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    public String getAnimalsYear() {
        return animalsYear;
    }

    public void setAnimalsYear(String animalsYear) {
        this.animalsYear = animalsYear;
    }

    public String getLeapMonth() {
        return leapMonth;
    }

    public void setLeapMonth(String leapMonth) {
        this.leapMonth = leapMonth;
    }

    public String getCyclical() {
        return cyclical;
    }

    public void setCyclical(String cyclical) {
        this.cyclical = cyclical;
    }

    public ArrayList<TextView> getTextarr() {
        return textarr;
    }

    //    AllPerson_CalModel baseArr;
    public void allPersonData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        params.setConnectTimeout(10000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (baseArr != null) {
                    baseArr = null;
                }
                baseArr = new Gson().fromJson(resultString, AllPerson_CalModel.class);
                if (baseArr.isIsSuccess()) {
                    notifyDataSetChanged();
                } else {
//                    Toast.makeText(context, baseArr.getMsg(), 3000).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
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
