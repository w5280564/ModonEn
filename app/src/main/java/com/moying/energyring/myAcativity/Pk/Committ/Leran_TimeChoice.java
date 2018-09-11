package com.moying.energyring.myAcativity.Pk.Committ;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.waylenBaseView.wheel.pictime.ChangeBirthDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Leran_TimeChoice extends Activity {

    private TextView start_Txt,end_Txt;
    private  int startYear,startMonth,startDay,endYear,endMonth,endDay;
    String unit,titleName,ProjectID;
    EditText count_Edit;
    @SuppressWarnings("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leran__time_choice);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        RelativeLayout goal_Rel = (RelativeLayout) findViewById(R.id.goal_Rel);
        final Button mu_Btn = (Button) findViewById(R.id.mu_Btn);
         count_Edit = (EditText) findViewById(R.id.count_Edit);
        TextView unit_Txt = (TextView) findViewById(R.id.unit_Txt);
         start_Txt = (TextView) findViewById(R.id.start_Txt);
         end_Txt = (TextView) findViewById(R.id.end_Txt);
        StaticData.ViewScale(mu_Btn, 399, 89);

        Intent intent = getIntent();
         unit = intent.getStringExtra("unit");
         titleName = intent.getStringExtra("titleName");
         ProjectID = intent.getStringExtra("ProjectID");

        cententtxt.setText(titleName);
        unit_Txt.setText(unit);

        Date date = new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        startYear = calendar.get(Calendar.YEAR);//当前时间
        startMonth =  calendar.get(Calendar.MONDAY)+1;
        startDay =  calendar.get(Calendar.DAY_OF_MONTH);
//        Calendar.getInstance().MONTH +1;

        calendar.setTime(date);
        calendar.add(calendar.DATE, 4);//把日期往后增加.整数往后推,负数往前移动

        endYear = calendar.get(Calendar.YEAR);//增加后的时间
        endMonth =  calendar.get(Calendar.MONDAY)+1;
        endDay =  calendar.get(Calendar.DAY_OF_MONTH);

//        date=calendar.getTime();   //这个时间就是日期往后推的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(calendar.getTime());
        end_Txt.setText(dateString);

        String startString = formatter.format(date);//当前日期
        start_Txt.setText(startString);

        if (unit.equals("天")) {
            goal_Rel.setVisibility(View.GONE);
            mu_Btn.setBackgroundResource(R.drawable.allperson_btn);
            mu_Btn.setTextColor(Color.parseColor("#ffffff"));
            mu_Btn.setEnabled(true);
        } else {
            goal_Rel.setVisibility(View.VISIBLE);
            mu_Btn.setBackgroundResource(R.drawable.timechioce_next);
            mu_Btn.setTextColor(Color.parseColor("#f24d4d"));
            mu_Btn.setEnabled(false);
        }

        count_Edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    mu_Btn.setBackgroundResource(R.drawable.allperson_btn);
                    mu_Btn.setTextColor(Color.parseColor("#ffffff"));
                    mu_Btn.setEnabled(true);
                } else {
                    mu_Btn.setBackgroundResource(R.drawable.timechioce_next);
                    mu_Btn.setTextColor(Color.parseColor("#f24d4d"));
                    mu_Btn.setEnabled(false);
                }
            }
        });

        return_Btn.setOnClickListener(new return_Btn());
        mu_Btn.setOnClickListener(new mu_Btn());
        start_Txt.setOnClickListener(new start_Txt());
        end_Txt.setOnClickListener(new end_Txt());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class mu_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (getNowTime(start_Txt.getText().toString())){
                Toast.makeText(Leran_TimeChoice.this, "开始日期不能是今天之前", Toast.LENGTH_SHORT).show();
                return;
            }
            if (getBig()){
                Toast.makeText(Leran_TimeChoice.this, "开始日期与结束日期间隔不得少于5天", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Leran_TimeChoice.this,Leran_Overview.class);
            intent.putExtra("unit", unit);
            intent.putExtra("count", count_Edit.getText().toString());
            intent.putExtra("titleName", titleName);
            intent.putExtra("ProjectID", ProjectID);
            intent.putExtra("startTime", start_Txt.getText().toString());
            intent.putExtra("endTime", end_Txt.getText().toString());
            startActivity(intent);

//            Toast.makeText(Leran_TimeChoice.this, "点击成功", Toast.LENGTH_SHORT).show();
        }
    }

    public class start_Txt implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(Leran_TimeChoice.this);
            mChangeBirthDialog.setDate(startYear, startMonth, startDay);
            mChangeBirthDialog.show();
            mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {
                @Override
                public void onClick(String year, String month, String day) {
//                    Toast.makeText(Leran_TimeChoice.this, year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
                    String time = year + "-" + month + "-" + day;
                    startYear = Integer.parseInt(year);
                    startMonth = Integer.parseInt(month);
                    startDay = Integer.parseInt(day);
                    start_Txt.setText(StaticData.getUserDate(time));
                }
            });
        }
    }

    public class end_Txt implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(Leran_TimeChoice.this);
            mChangeBirthDialog.setDate(endYear, endMonth, endDay);
            mChangeBirthDialog.show();
            mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {
                @Override
                public void onClick(String year, String month, String day) {
//                    Toast.makeText(Leran_TimeChoice.this, year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
                    String time = year + "-" + month + "-" + day;
                    endYear = Integer.parseInt(year);
                    endMonth = Integer.parseInt(month);
                    endDay = Integer.parseInt(day);
                    end_Txt.setText(StaticData.getUserDate(time));
                }
            });
        }
    }


    /**
     * true在今天之前flase在之后
     *
     * @param str
     * @return
     */
    public Boolean getNowTime(String str) {
        Boolean flag = null;
        try {
            Date nowdate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            String date = sdf.format(nowdate);
            Date d = sdf.parse(str);
            String datetwo = sdf.format(d);
//            if (d.equals(nowdate)){
            if (date.equals(datetwo)) {
                flag = false;//日期相同
            } else {
                flag = d.before(nowdate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 开始日期与结束日期必须大于5天
     *
     * @param
     * @return
     */
    public Boolean getBig() {
        Boolean flag = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            Date startTime = sdf.parse(start_Txt.getText().toString());
            Date endTime = sdf.parse(end_Txt.getText().toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            calendar.add(Calendar.DATE,4);
            if (calendar.getTime() == endTime){
                flag = true;//日期相同
            }else {
                flag = endTime.before(calendar.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }




}
