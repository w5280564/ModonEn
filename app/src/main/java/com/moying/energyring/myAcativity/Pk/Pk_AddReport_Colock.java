package com.moying.energyring.myAcativity.Pk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.Colock_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.wheel.adapters.AbstractWheelTextAdapter;
import com.moying.energyring.waylenBaseView.wheel.views.OnWheelChangedListener;
import com.moying.energyring.waylenBaseView.wheel.views.OnWheelScrollListener;
import com.moying.energyring.waylenBaseView.wheel.views.WheelView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pk_AddReport_Colock extends AppCompatActivity {
    private WheelView wvHour;
    private WheelView wvMine;
    private int maxTextSize = 40;
    private int minTextSize = 20;
    private ArrayList<String> arry_hours = new ArrayList<String>();
    private ArrayList<String> arry_mines = new ArrayList<String>();
    private String selectHour = "0";
    private String selectMinu = "0";
    private CalendarTextAdapter mHourAdapter;
    private CalendarTextAdapter mMineAdapter;
    SwitchButton push_switch;
    private LinearLayout weekchoice_Lin;

    //    private CalendarTextAdapter mHourAdapter;
//    private CalendarTextAdapter mMineAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pk__addreport_colock);

        initTitle();
        initView();
        initData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("闹钟提醒");
        Button right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        right_Btn.setText("保存");

        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);
//        StaticData.ViewScale(right_Btn, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
    }


    private void initView() {
        push_switch = (SwitchButton) findViewById(R.id.push_switch);
        View switch_Rel = findViewById(R.id.switch_Rel);
        View wheel_Lin = findViewById(R.id.wheel_Lin);
        weekchoice_Lin = (LinearLayout) findViewById(R.id.weekchoice_Lin);

        wvHour = (WheelView) findViewById(R.id.wv_hour);
        wvHour.setBgColor(ContextCompat.getColor(this,R.color.colorAllBg));
        wvMine = (WheelView) findViewById(R.id.wv_mine);
        wvMine.setBgColor(ContextCompat.getColor(this,R.color.colorAllBg));

//        int color[] = {Color.WHITE,Color.WHITE,Color.WHITE};
//        wvHour.setTopandBotColor(color);
//        wvHour.selectCenter(this.getDrawable(R.drawable.wheel_select_color));
//        wvHour.setBgColor(Color.parseColor("#ffffff"));

        StaticData.ViewScale(switch_Rel, 0, 128);
        StaticData.ViewScale(wheel_Lin, 0, 500);
        push_switch.setOnCheckedChangeListener(new push_switch());


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
            RemindTime = selectHour + ":" + selectMinu;
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < mcheckflag.size(); i++) {
                if (mcheckflag.get(i)) {
                    if (i < 6) {
                        stringBuffer.append((i + 1) + ",");
//                        RemindDate = RemindDate + "," + (i + 1);
                    } else {
                        stringBuffer.append(0 + ",");
//                        RemindDate = RemindDate + "," + 0;
                    }
                }
            }
            RemindDate = stringBuffer.toString();
            Clock_Add_Data(Pk_AddReport_Colock.this, saveFile.BaseUrl + saveFile.Clock_Add_Url);

        }
    }

    private class push_switch implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                Is_Enabled = true;
                timeInit(weekchoice_Lin, Pk_AddReport_Colock.this,Is_Enabled);
            } else {
                Is_Enabled = false;
                timeInit(weekchoice_Lin, Pk_AddReport_Colock.this,Is_Enabled);
            }
        }
    }


    private void initSelector() {
        initHours();
        mHourAdapter = new CalendarTextAdapter(this, arry_hours, getHour(selectHour), maxTextSize, minTextSize);
        wvHour.isRectCanvas(false);
        wvHour.setVisibleItems(5);
        wvHour.setViewAdapter(mHourAdapter);
        wvHour.setCurrentItem(getHour(selectHour));
        String currentText = (String) mHourAdapter.getItemText(wvHour.getCurrentItem());
        setTextviewSize(currentText, mHourAdapter);

//        wvHour.addScrollingListener(null);

        initMinus();
        mMineAdapter = new CalendarTextAdapter(this, arry_mines, getMinu(selectMinu), maxTextSize, minTextSize);
//        setTextviewSize(String.valueOf(getMinu(selectMinu)), mMineAdapter);
        wvMine.isRectCanvas(false);
        wvMine.setVisibleItems(5);
        wvMine.setViewAdapter(mMineAdapter);
        wvMine.setCurrentItem(getMinu(selectMinu));

        String currentTextMine = (String) mMineAdapter.getItemText(wvMine.getCurrentItem());
        setTextviewSize(currentTextMine, mMineAdapter);

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

    private void initData() {
        ProjectID = getIntent().getStringExtra("ProjectID");
        UserID = saveFile.getShareData("userId", this);
        colockData(Pk_AddReport_Colock.this, saveFile.BaseUrl + saveFile.Project_Clock_Data_Url + "?ProjectID=" + ProjectID);
    }

    private String[] timeArr = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    final Map<Integer, Boolean> mcheckflag = new HashMap<>();

    public void timeInit(LinearLayout myLin, Context context,boolean Is_Enabled) {
        if (myLin != null) {
            myLin.removeAllViews();
        }
        int size = timeArr.length;
        for (int i = 0; i < size; i++) {
            View myview = LayoutInflater.from(this).inflate(R.layout.colock_textview, null);
            final TextView week_Txt = (TextView) myview.findViewById(R.id.time_Txt);
            StaticData.ViewScale(week_Txt, 80, 70);
            week_Txt.setText(timeArr[i]);
            if (Is_Enabled){
                if (mcheckflag.get(i)) {
                    week_Txt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
                    week_Txt.setBackgroundResource(R.drawable.colock_yello);
                } else {
                    week_Txt.setTextColor(ContextCompat.getColor(this,R.color.colorSecondWhite));
                    week_Txt.setBackgroundResource(R.drawable.colock_gazy);
                }
                myview.setEnabled(true);
            }else {
                week_Txt.setTextColor(ContextCompat.getColor(this,R.color.colorSecondWhite));
                week_Txt.setBackgroundResource(R.drawable.colock_gazy);
                myview.setEnabled(false);
            }

            myview.setTag(i);
            myLin.addView(myview);
            myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();
                    if (mcheckflag.get(tag)) {
                        week_Txt.setTextColor(ContextCompat.getColor(Pk_AddReport_Colock.this,R.color.colorSecondWhite));
                        week_Txt.setBackgroundResource(R.drawable.colock_gazy);
                        mcheckflag.put(tag, false);
                    } else {
                        week_Txt.setTextColor(ContextCompat.getColor(Pk_AddReport_Colock.this,R.color.colorFristBlack));
                        week_Txt.setBackgroundResource(R.drawable.colock_yello);
                        mcheckflag.put(tag, true);
                    }
//                    saveFile.saveUserList(Pk_AddReport_Colock.this, myChoice, "choiceWeek");
                }
            });


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
            super(context, R.layout.item_time_colock, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
          setItemTextResource(R.id.tempValue);
//         if (getItemText(currentItem) == ){
//
//         }
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
                StaticData.ViewScale(textvew, 0, 140);
                textvew.setTextColor(Color.parseColor("#ffffff"));
                textvew.setTextSize(maxTextSize);
            } else {
                StaticData.ViewScale(textvew, 0, 90);
                textvew.setTextColor(Color.parseColor("#919090"));
                textvew.setTextSize(minTextSize);
            }
        }
    }


    public void colockData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Colock_Model colockModel = new Gson().fromJson(resultString, Colock_Model.class);

                    for (int i = 0; i < timeArr.length; i++) {//初始化
                        mcheckflag.put(i, false);
                    }
                    if (colockModel.getData() != null) {
                        if (colockModel.isIsSuccess() && !colockModel.getData().equals("{}")) {
                            push_switch.setChecked(true);
                            ClockID = colockModel.getData().getClockID() + "";
                            Is_Enabled = true;
                            RemindTime = colockModel.getData().getRemindTime();
                            String[] time = colockModel.getData().getRemindTime().split(":");
                            setTimes(time[0], time[1]);
                            RemindDate = colockModel.getData().getRemindDate();
                            String[] DateArr = colockModel.getData().getRemindDate().split(",");

                            for (int i = 0; i < DateArr.length; i++) {//初始化
                                int index = Integer.parseInt(DateArr[i]);
                                if (DateArr[i].equals("0")) {
                                    mcheckflag.put(mcheckflag.size() - 1, true);
                                } else {
                                    mcheckflag.put(index - 1, true);
                                }
                            }

                            initSelector();
                            timeInit(weekchoice_Lin, context,Is_Enabled);

                        } else {
                            Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        ClockID = "0";
                        Is_Enabled = false;
                        initSelector();
                        timeInit(weekchoice_Lin, context,Is_Enabled);
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


    private String ClockID = "0";
    private boolean Is_Enabled = false;
    private String ProjectID;
    private String UserID;
    private String RemindTime;
    private String RemindDate;

    //修改用户资料
    public void Clock_Add_Data(final Context context, String baseUrl) {
//        UserInfo_Model.DataBean oneData = userModel.getData();
        JSONObject obj = new JSONObject();
        try {
            obj.put("ClockID", ClockID);
            obj.put("ProjectID", ProjectID);
            obj.put("UserID", UserID);
            obj.put("Is_Enabled", Is_Enabled);
            obj.put("RemindTime", RemindTime);
            obj.put("RemindDate", RemindDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        params.setAsJsonContent(true);
        params.setBodyContent(obj.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model baseModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (baseModel.isIsSuccess()) {
                        finish();
                    } else {
                        Toast.makeText(context, "请核对时间，并选择日期", Toast.LENGTH_SHORT).show();
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
