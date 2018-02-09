package com.moying.energyring.myAcativity.Find;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.wheel.adapters.AbstractWheelTextAdapter;
import com.moying.energyring.waylenBaseView.wheel.views.OnWheelChangedListener;
import com.moying.energyring.waylenBaseView.wheel.views.OnWheelScrollListener;
import com.moying.energyring.waylenBaseView.wheel.views.WheelView;

import java.util.ArrayList;
import java.util.List;

public class Person_CountDownTimer extends Activity {


    private ArrayList<String> arry_hours = new ArrayList<String>();
    private ArrayList<String> arry_mines = new ArrayList<String>();
    private WheelView wvHour, wvMine;
    private TextView week_Txt;

    private int maxTextSize = 60;
    private int minTextSize = 35;

    private String selectHour = "0";
    private String selectMinu = "0";
    private CalendarTextAdapter mHourAdapter;
    private CalendarTextAdapter mMineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__count_down_timer);

        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#2b2a2a"));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setText("设置时间");
        Button right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setTextColor(Color.parseColor("#ffffff"));
        right_Btn.setGravity(Gravity.CENTER);
        right_Btn.setText("保存");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);
        StaticData.ViewScale(right_Btn, 100, 88);

        RelativeLayout week_Rel = (RelativeLayout) findViewById(R.id.week_Rel);
        week_Txt = (TextView) findViewById(R.id.week_Txt);
        ImageView arrow_right = (ImageView) findViewById(R.id.arrow_right);
        StaticData.ViewScale(arrow_right, 60, 60);


        if (saveFile.getShareData("timeTxt", Person_CountDownTimer.this).equals("false")) {
            setTimes("00", "00");
        } else {
            String[] time = saveFile.getShareData("timeTxt", Person_CountDownTimer.this).split(":");
            setTimes(time[0], time[1]);
        }


        wvHour = (WheelView) findViewById(R.id.wv_hour);
        wvMine = (WheelView) findViewById(R.id.wv_mine);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
        week_Rel.setOnClickListener(new week_Rel());


        initHours();
        mHourAdapter = new CalendarTextAdapter(this, arry_hours, getHour(selectHour), maxTextSize, minTextSize);
        wvHour.setVisibleItems(5);
        wvHour.setViewAdapter(mHourAdapter);
        wvHour.setCurrentItem(getHour(selectHour));

        initMinus();
        mMineAdapter = new CalendarTextAdapter(this, arry_mines, getMinu(selectMinu), maxTextSize, minTextSize);
        wvMine.setVisibleItems(5);
        wvMine.setViewAdapter(mMineAdapter);
        wvMine.setCurrentItem(getMinu(selectMinu));


        wvHour.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                selectHour = currentText;
                setTextviewSize(currentText, mHourAdapter);
            }
        });

        wvHour.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mHourAdapter);
            }
        });

        wvMine.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mMineAdapter.getItemText(wheel.getCurrentItem());
                selectMinu = currentText;
                setTextviewSize(currentText, mMineAdapter);
            }
        });

        wvMine.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mMineAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mMineAdapter);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (saveFile.getUserList(this, "choiceWeek").isEmpty()) {
            week_Txt.setText("");
        } else {
            week_Txt.setText(week_Txt(Person_CountDownTimer.this));
        }
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
            saveFile.saveShareData("timeTxt", selectHour + ":" + selectMinu, Person_CountDownTimer.this);
            finish();
        }
    }

    public class week_Rel implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Person_CountDownTimer.this, Person_WeekChoice.class);
            startActivity(intent);
        }
    }

    // 设置当前时间
    public void setTimes(String hour, String minu) {
        this.selectHour = hour;
        this.selectMinu = minu;
    }

    /**
     * 初始化小时
     */
    public void initHours() {
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                arry_hours.add("0" + i);
            } else {
                arry_hours.add(i + "");
            }
        }
    }

    /**
     * 获取小时的索引
     *
     * @param hour
     * @return
     */
    public int getHour(String hour) {
        int h = Integer.parseInt(hour);
        for (int i = 0; i < 24; i++) {
            if (h == i)
                return i;
        }
        return 0;
    }

    /**
     * 初始和分钟
     */
    public void initMinus() {
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                arry_mines.add("0" + i);
            } else {
                arry_mines.add(i + "");
            }
        }
    }

    /**
     * 获取分钟索引
     *
     * @param minu
     * @return
     */
    public int getMinu(String minu) {
        int m = Integer.parseInt(minu);
        for (int i = 0; i < 60; i++) {
            if (i == m)
                return m;
        }
        return 0;
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }

    public static String week_Txt(Context context) {
        List<String> myChoice = saveFile.getUserList(context, "choiceWeek");
        StringBuffer sbf = new StringBuffer();
        String txt = "";
        if (myChoice.size() == 0) {
            sbf.setLength(0);
        } else {
            sbf.append("星期");
            for (int i = 0; i < myChoice.size(); i++) {
                if (myChoice.get(i).equals("true")) {
                    sbf.append(weekday(i)).append("、");
                }
            }
            if (sbf.length() == 2) {
                sbf.setLength(0);
            } else {
                txt = sbf.substring(0, sbf.length() - 1);
            }
        }
        return txt;
    }

    public static String weekday(int index) {
        String day = null;
        if (index == 0) {
            day = "日";
        } else if (index == 1) {
            day = "一";
        } else if (index == 2) {
            day = "二";
        } else if (index == 3) {
            day = "三";
        } else if (index == 4) {
            day = "四";
        } else if (index == 5) {
            day = "五";
        } else if (index == 6) {
            day = "六";
        }
        return day;
    }


}
