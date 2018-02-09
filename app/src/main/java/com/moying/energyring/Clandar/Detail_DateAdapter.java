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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moying.energyring.Model.AllPersonDetails_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by waylen on 2017/4/7.
 */

public class Detail_DateAdapter extends BaseAdapter {
    private boolean isLeapyear = false; // 是否为闰年
    private int daysOfMonth = 0; // 某月的天数
    private int dayOfWeek = 0; // 具体某一天是星期几
    private int nextDayOfWeek = 0;
    private int lastDayOfWeek = 0;
    private int lastDaysOfMonth = 0; // 上一个月的总天数
    private int eachDayOfWeek = 0;
    private Context context;
    private SpecialCalendar sc = null;
    private Resources res = null;
    private Drawable drawable = null;
    private String[] dayNumber = new String[7];
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    private int currentFlag = -1; // 用于标记当天
    // 系统当前时间
    private String sysDate = "";
    private String sys_year = "";
    private String sys_month = "";
    private String sys_day = "";
    private String currentYear = "";
    private String currentMonth = "";
    private String currentWeek = "";
    private String currentDay = "";
    private int weeksOfMonth;
    private int default_postion;
    private int clickTemp = -1;
    private int week_num = 0;
    private int week_c = 0;
    private int month = 0;
    private int jumpWeek = 0;
    private int c_month = 0;
    private int c_day_week = 0;
    private int n_day_week = 0;
    private boolean isStart;
    String startTime, endTime;
    List<AllPersonDetails_Model.DataBean.TargetDeialsListBean> baseModel;


    // 标识选择的Item
    public void setSeclection(int position) {
//        clickTemp = position;
        currentFlag = position;
    }


    public Detail_DateAdapter() {
        Date date = new Date();
        sysDate = sdf.format(date); // 当期日期
        sys_year = sysDate.split("-")[0];
        sys_month = sysDate.split("-")[1];
        sys_day = sysDate.split("-")[2];
        month = Integer.parseInt(sys_month);
    }


    public Detail_DateAdapter(Context context, Resources rs, int year_c, int month_c, int week_c, int week_num, int default_postion, boolean isStart, String startTime, String endTime, List<AllPersonDetails_Model.DataBean.TargetDeialsListBean> baseModel) {
        this();
        this.context = context;
        this.res = rs;
        this.default_postion = default_postion;
        this.week_c = week_c;
        this.isStart = isStart;
        this.startTime = startTime;
        this.endTime = endTime;
        sc = new SpecialCalendar();
        this.baseModel = baseModel;

        lastDayOfWeek = sc.getWeekDayOfLastMonth(year_c, month_c, sc.getDaysOfMonth(sc.isLeapYear(year_c), month_c));
        Log.i("life", " lastDayOfWeek = " + lastDayOfWeek);

        currentYear = String.valueOf(year_c);
        // 得到当前的年份
        currentMonth = String.valueOf(month_c); // 得到本月
        // （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
        currentDay = String.valueOf(sys_day);

        // 得到当前日期是哪天
        getCalendar(Integer.parseInt(currentYear), Integer.parseInt(currentMonth));
        currentWeek = String.valueOf(week_c);

        Log.i("life", " week_c = " + week_c);

        getWeek(Integer.parseInt(currentYear), Integer.parseInt(currentMonth), Integer.parseInt(currentWeek));


    }


    public int getTodayPosition() {
        int todayWeek = sc.getWeekDayOfLastMonth(Integer.parseInt(sys_year), Integer.parseInt(sys_month), Integer.parseInt(sys_day));
        if (todayWeek == 7) {
            clickTemp = 0;
        } else {
            clickTemp = todayWeek;
        }
        return clickTemp;
    }


    public int getCurrentMonth(int position) {
        int thisDayOfWeek = sc.getWeekdayOfMonth(Integer.parseInt(currentYear), Integer.parseInt(currentMonth));

        if (isStart) {
            if (thisDayOfWeek != 7) {
                if (position < thisDayOfWeek) {
                    return Integer.parseInt(currentMonth) - 1 == 0 ? 12 : Integer.parseInt(currentMonth) - 1;
                } else {
                    return Integer.parseInt(currentMonth);
                }
            } else {
                return Integer.parseInt(currentMonth);
            }
        } else {
            return Integer.parseInt(currentMonth);
        }


    }


    public int getCurrentYear(int position) {
        int thisDayOfWeek = sc.getWeekdayOfMonth(Integer.parseInt(currentYear),
                Integer.parseInt(currentMonth));
        if (isStart) {
            if (thisDayOfWeek != 7) {
                if (position < thisDayOfWeek) {
                    return Integer.parseInt(currentMonth) - 1 == 0 ? Integer.parseInt(currentYear) - 1 : Integer.parseInt(currentYear);
                } else {
                    return Integer.parseInt(currentYear);
                }
            } else {
                return Integer.parseInt(currentYear);
            }
        } else {
            return Integer.parseInt(currentYear);
        }
    }


    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year); // 是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几  1
        lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1);
        nextDayOfWeek = sc.getDaysOfMonth(isLeapyear, month + 1);
    }


    public void getWeek(int year, int month, int week) {
        for (int i = 0; i < dayNumber.length; i++) {
            if (dayOfWeek == 7) {
                dayNumber[i] = String.valueOf((i + 1) + 7 * (week - 1));
            } else {
                if (week == 1) {
                    if (i < dayOfWeek) {
                        Log.i("life", " lastDaysOfMonth = " + lastDaysOfMonth);
                        dayNumber[i] = String.valueOf(lastDaysOfMonth - (dayOfWeek - (i + 1)));
                    } else {
                        dayNumber[i] = String.valueOf(i - dayOfWeek + 1);
                    }
                } else {
                    dayNumber[i] = String.valueOf((7 - dayOfWeek + 1 + i) + 7 * (week - 2));
                }
            }

            int weeknum = getDayInWeek(year, month, i);
            if (currentYear.equals(String.valueOf(year)) && currentMonth.equals(String.valueOf(month)) && currentWeek.equals(weeknum + "")) {
                // 标记当前日期
                currentFlag = i;
            } else {
                currentFlag = 1000;
            }
        }
    }


    public String[] getDayNumbers() {
        return dayNumber;
    }


    /**
     * 得到某月有几周(特殊算法)
     */
    public int getWeeksOfMonth() {
        int preMonthRelax = 0;
        if (dayOfWeek != 7) {
            preMonthRelax = dayOfWeek;
        }
        if ((daysOfMonth + preMonthRelax) % 7 == 0) {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7;
        } else {
            weeksOfMonth = (daysOfMonth + preMonthRelax) / 7 + 1;
        }
        return weeksOfMonth;
    }


    /**
     * 某一天在第几周
     */
    public int getDayInWeek(int year, int month, int day_c) {
        int weelnum;
        if (dayOfWeek == 7) {
            weelnum = day_c / 7 + 1;
        } else {
            if (day_c <= (7 - dayOfWeek)) {
                weelnum = 1;
            } else {
                if ((day_c - (7 - dayOfWeek)) % 7 == 0) {
                    weelnum = (day_c - (7 - dayOfWeek)) / 7 + 1;
                } else {
                    weelnum = (day_c - (7 - dayOfWeek)) / 7 + 2;
                }
            }
        }
        return weelnum;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_calendar, null);
        }
        LinearLayout calenda_Lin = (LinearLayout) convertView.findViewById(R.id.calenda_Lin);
        TextView tvCalendar = (TextView) convertView.findViewById(R.id.tv_calendar);
        StaticData.ViewScale(calenda_Lin, 0, 124);
        StaticData.ViewScale(tvCalendar, 42, 42);
        tvCalendar.setText(dayNumber[position]);
//        if (clickTemp == position) {
//        if (currentFlag == position) {
//            tvCalendar.setSelected(true);
//            tvCalendar.setTextColor(Color.parseColor("#ffe313"));
//        } else {
//            tvCalendar.setSelected(false);
//            tvCalendar.setTextColor(Color.parseColor("#acacac"));
//            tvCalendar.setBackgroundColor(Color.TRANSPARENT);
//            nowCorlor(position,tvCalendar,"#ffe313");//今天
//        }


        if (isColor(position, startTime, endTime)) {//true是在开始结束日期之间
            tvCalendar.setSelected(true);
            tvCalendar.setTextColor(Color.WHITE);
            if (redCorlor(position)) {//匹配红色
                tvCalendar.setBackgroundResource(R.drawable.calendar_item_red);
            } else {
                tvCalendar.setBackgroundResource(R.drawable.calendar_item_gray);
            }
        } else {
            tvCalendar.setSelected(false);
            tvCalendar.setTextColor(Color.parseColor("#acacac"));
            tvCalendar.setBackgroundColor(Color.TRANSPARENT);
        }

        if (currentFlag == position) {
            tvCalendar.setSelected(true);
            tvCalendar.setTextColor(Color.parseColor("#ffe313"));
        } else {
            nowCorlor(position, tvCalendar, "#ffe313");//今天
        }


        return convertView;
    }

    String[] time = {"2017-04-07", "2017-04-08", "2017-04-09", "2017-04-10", "2017-04-11", "2017-04-12"};

    public boolean isColor(int pos, String startTime, String endTime) {
        boolean isboolean = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String nowTime = currentYear + "-" + getCurrentMonth(pos) + "-" + getDayNumbers()[pos];
        Date datetwo = null, startDate = null, endDate = null;
        try {
            datetwo = sdf.parse(nowTime);
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if ((datetwo.after(startDate) && datetwo.before(endDate)) || (datetwo.equals(startDate) || datetwo.equals(endDate))) {
            isboolean = true;
        } else {
            isboolean = false;
        }
        return isboolean;
    }

    //今天的颜色
    public void nowCorlor(int pos, TextView myView, String corlor) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String nowTime = currentYear + "-" + getCurrentMonth(pos) + "-" + getDayNumbers()[pos];
        try {
            Date nowDate = sdf.parse(nowTime);
            Date sDate = sdf.parse(sysDate);
            if (nowDate.equals(sDate)) {
                myView.setSelected(true);
                myView.setTextColor(Color.parseColor(corlor));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    //匹配红色的颜色
    public boolean redCorlor(int pos) {
        boolean redboolean = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            String nowTime = currentYear + "-" + getCurrentMonth(pos) + "-" + getDayNumbers()[pos];
            Date nowDate = sdf.parse(nowTime);
            for (int i = 0; i < baseModel.size(); i++) {
                Date sDate = sdf.parse(baseModel.get(i).getReportDate());
                if (nowDate.equals(sDate) && baseModel.get(i).isIs_Finish()) {
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


    /**
     * 获取两个日期间集合
     */
    public List<String> getAnddate(String startTime, String endTime) {
        List<String> dates = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            long betweenTime = endDate.getTime() - startDate.getTime();//两个日期间隔
            int days = (int) (betweenTime / 1000 / 60 / 60 / 24);
            for (int i = 0; i <= days; i++) {
                c.setTime(startDate);//赋值开始日期
                c.add(c.DATE, i);//增加天数到达指定月最后一天，会自动到下月
                String mYear = String.valueOf(c.get(Calendar.YEAR));//增加后的时间
                String mMonth = String.valueOf(c.get(Calendar.MONDAY) + 1);
                String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
//                String finshdate = mYear + "-" + mMonth + "-" + mDay;
                String finshdate = sdf.format(c.getTime());
                dates.add(finshdate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return dates;
    }


}