package com.moying.energyring.myAcativity.Pk.Training;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Training_TodaySet_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.HorizontalselectedView;
import com.moying.energyring.waylenBaseView.wheel.adapters.AbstractWheelTextAdapter;
import com.moying.energyring.waylenBaseView.wheel.views.OnWheelChangedListener;
import com.moying.energyring.waylenBaseView.wheel.views.OnWheelScrollListener;
import com.moying.energyring.waylenBaseView.wheel.views.WheelView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainingTodaySet extends Activity {


    private String coachName, bgMusic;
    private TextView coach_selectName_Txt, bgmusic_selectName_Txt;
    List<String> dataStr, coachArr, bgmArr;
    private boolean popisShow = false;
    private HorizontalselectedView horView;
    View next_Txt;
    private String ProjectID, BGMFileName; //歌曲名
    String TargetNum = "0"; //目标数量
    int SoundType = 1; //教练
    String[] BGMnameArr;
    private WheelView wv_group, wv_count;
    private String GroupCount, GroupNum;

    private int maxTextSize = 40;
    private int minTextSize = 20;
    private ArrayList<String> arry_groups = new ArrayList<String>();
    private ArrayList<String> arry_counts = new ArrayList<String>();
    private String selectGroup = "0";
    private String selectGroupNum = "0";
    private CalendarTextAdapter mGroupAdapter;
    private CalendarTextAdapter mCountAdapter;
    public int Trainlimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_today_set);

        initTitle();
        initView();
        initData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#4b4a4a"));
//        cententtxt.setText("今日目标");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private void initView() {
        horView = (HorizontalselectedView) findViewById(R.id.horView);
        View set_select_Img = findViewById(R.id.set_select_Img);
        View select_Rel = findViewById(R.id.select_Rel);
        View coach_Rel = findViewById(R.id.coach_Rel);
        View coach_arrow = findViewById(R.id.coach_arrow);
        coach_selectName_Txt = (TextView) findViewById(R.id.coach_selectName_Txt);
        View bgmusic_Rel = findViewById(R.id.bgmusic_Rel);
        View bgmusic_arrow = findViewById(R.id.bgmusic_arrow);
        bgmusic_selectName_Txt = (TextView) findViewById(R.id.bgmusic_selectName_Txt);
        next_Txt = findViewById(R.id.next_Txt);
        LinearLayout wheel_Lin = (LinearLayout) findViewById(R.id.wheel_Lin);
        wv_group = (WheelView) findViewById(R.id.wv_group);
        StaticData.ViewScale(wv_group, 0, 400);
//        int color[] = {Color.parseColor("#f6f6f6"),Color.parseColor("#f6f6f6"),Color.parseColor("#f6f6f6")};
//        wv_group.setTopandBotColor(color);
        wv_group.selectCenter(this.getDrawable(R.drawable.wheel_select_color));
        wv_group.setBgColor(Color.parseColor("#f6f6f6"));

        wv_count = (WheelView) findViewById(R.id.wv_count);
        StaticData.ViewScale(wv_count, 0, 400);
        wv_count.selectCenter(this.getDrawable(R.drawable.wheel_select_color));
        wv_count.setBgColor(Color.parseColor("#f6f6f6"));
//        wv_count.setTopandBotColor(color);
//        wv_count.selectCenter(this.getDrawable(R.drawable.wheel_select_color));
//        wv_count.setBgColor(Color.parseColor("#f6f6f6"));


        dataStr = new ArrayList<>();

        coachArr = new ArrayList<>();
        coachArr.add("海哥");
        coachArr.add("熊威");
//        coachArr.add("树安老师");

        bgmArr = new ArrayList<>();
        bgmArr.add("舒缓");
        bgmArr.add("轻松");
        bgmArr.add("激情");
        bgmArr.add("紧张");

        StaticData.ViewScale(set_select_Img, 30, 24);
        StaticData.ViewScale(select_Rel, 0, 296);
        StaticData.ViewScale(coach_Rel, 0, 160);
        StaticData.ViewScale(coach_arrow, 48, 56);
        StaticData.ViewScale(bgmusic_Rel, 0, 160);
        StaticData.ViewScale(bgmusic_arrow, 48, 56);
        StaticData.ViewScale(next_Txt, 0, 120);
        StaticData.ViewScale(wheel_Lin, 0, 500);

        coachName = "";
        bgMusic = "";

        coach_Rel.setOnClickListener(new coach_Rel());
        bgmusic_Rel.setOnClickListener(new bgmusic_Rel());
        next_Txt.setOnClickListener(new next_Txt());
    }

    private void initData() {
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");

        todaySetData(TrainingTodaySet.this, saveFile.BaseUrl + saveFile.PK_Project_Get_Url + "?ProjectID=" + ProjectID);
    }

    private class coach_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showchoice(TrainingTodaySet.this, view, "选择教练", coachArr);
        }
    }

    private class bgmusic_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showchoice(TrainingTodaySet.this, view, "选择音乐", bgmArr);
        }
    }


    private class next_Txt implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String currentTextGroup = (String) mGroupAdapter.getItemText(wv_group.getCurrentItem());
            String currentTextNum = (String) mCountAdapter.getItemText(wv_count.getCurrentItem());

            int targetNum = Integer.parseInt(currentTextGroup) * Integer.parseInt(currentTextNum);
            if (targetNum > Trainlimit){
                Toast.makeText(TrainingTodaySet.this,"总数不能超过"+Trainlimit,Toast.LENGTH_SHORT).show();
                return;
            }

//            TargetNum = horView.getSelectedString();
                next_Txt.setEnabled(false);

            TargetNum = String.valueOf(targetNum);
            GroupCount = currentTextGroup;
            GroupNum = currentTextNum;

            trainingNext_Data(TrainingTodaySet.this, saveFile.BaseUrl + saveFile.TrainAdd_Post_Url);
        }
    }


    private void showchoice(final Context mContext, View view, final String titleName, List<String> choiceArr) {
        View contentView = View.inflate(mContext, R.layout.setchoice, null);
        final BasePopupWindow popupWindow = new BasePopupWindow(mContext);
        popupWindow.setmShowAlpha(0.5f);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x80000000));
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
        view.setEnabled(true);

        View my_Rel = contentView.findViewById(R.id.my_Rel);
        TextView hint_Txt = (TextView) contentView.findViewById(R.id.hint_Txt);
        View cha_Lin = contentView.findViewById(R.id.cha_Lin);
        TextView cancel_Txt = (TextView) contentView.findViewById(R.id.cancel_Txt);
        View sure_Txt = contentView.findViewById(R.id.sure_Txt);
        View left_Img = contentView.findViewById(R.id.left_Img);
        View right_Img = contentView.findViewById(R.id.right_Img);


        final HorizontalselectedView select_View = (HorizontalselectedView) contentView.findViewById(R.id.select_View);
        select_View.setData(choiceArr);
        select_View.setSeeSize(5);
//        hint_Txt.setText(select_View.getSelectedString());
        hint_Txt.setText(titleName);
        if (titleName.equals("选择教练")) {
            select_View.setSelectOne(coach_selectName_Txt.getText().toString());
        } else {
            select_View.setSelectOne(bgmusic_selectName_Txt.getText().toString());
        }
        StaticData.ViewScale(my_Rel, 610, 490);
        StaticData.ViewScale(hint_Txt, 0, 120);
        StaticData.ViewScale(cha_Lin, 0, 120);
        StaticData.ViewScale(left_Img, 24, 40);
        StaticData.ViewScale(right_Img, 24, 40);
        viewTouchDelegate.expandViewTouchDelegate(left_Img, 100, 100, 100, 100);
        viewTouchDelegate.expandViewTouchDelegate(right_Img, 100, 100, 100, 100);
        cancel_Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        sure_Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

                if (titleName.equals("选择教练")) {
                    SoundType = select_View.getSelectCount() + 1;
                    coach_selectName_Txt.setText(select_View.getSelectedString());
                } else {
                    bgmusic_selectName_Txt.setText(select_View.getSelectedString());
                    BGMFileName = getBGMname(select_View.getSelectCount());
//                    BGMFileName = BGMnameArr[select_View.getSelectCount()];
                }
            }
        });
        left_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_View.setAnRightOffset();
            }
        });
        right_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_View.setAnLeftOffset();
            }
        });

    }

    private String getBGMname(int pos) {
        String BGMStr = "梦的海洋";
        ;
        if (pos == 0) {
            BGMStr = "梦的海洋";
        } else if (pos == 1) {
            BGMStr = "Intro";
        } else if (pos == 2) {
            BGMStr = "Pacific Rim";
        } else if (pos == 3) {
            BGMStr = "Battle";
        }
        return BGMStr;
    }

    public void todaySetData(final Context context, final String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Training_TodaySet_Model setModel = new Gson().fromJson(resultString, Training_TodaySet_Model.class);
                    if (setModel.isIsSuccess() && !setModel.getData().equals("[]")) {
                        Training_TodaySet_Model.DataBean oneData = setModel.getData();

                        Trainlimit = oneData.getTrainlimit();
//                        TargetNum = oneData.getTrainlimit();

//                        int size = oneData.getTrainlimit() / oneData.getGroupNum();
//                        for (int i = 0; i < size; i++) {
//                            int index = oneData.getGroupNum() * (i + 1);
//                            dataStr.add(index + "");
//                        }
//                        horView.setshowCenterData(dataStr);
//                        horView.setSeeSize(7);
//
//                        TargetNum = horView.getSelectedString();

                        try {
//                            String[] fileNames = context.getResources().getAssets().list("bgm");
                            BGMnameArr = context.getAssets().list("bgm");//获取assets目录下的所有文件及目录名
                            BGMFileName = BGMnameArr[0];
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        setTimes("00", "00");
                        initSelector(setModel.getData().getGroupCount(), setModel.getData().getGroupNums());

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

    public void trainingNext_Data(final Context context, String baseUrl) {

        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("ProjectID", ProjectID);
            obj.put("TargetNum", TargetNum);//目标数
            obj.put("SoundType", SoundType);//教练声
            obj.put("BGMFileName", BGMFileName);//背景音乐
            obj.put("GroupCount", GroupCount);
            obj.put("GroupNum", GroupNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.setBodyContent(obj.toString());
        params.setAsJsonContent(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                next_Txt.setEnabled(true);
                BaseDataInt_Model model = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                if (model.isIsSuccess()) {
//                    Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();


                    Intent intent1 = new Intent(TrainingTodaySet.this, startTrainingActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent1.putExtra("TrainID", model.getData() + "");
                    startActivity(intent1);

//                    finish();
                    overridePendingTransition(0, 0);
                } else {
                    Toast.makeText(context, model.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                next_Txt.setEnabled(true);
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, MainLogin.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
                next_Txt.setEnabled(true);
            }

            @Override
            public void onFinished() {
                next_Txt.setEnabled(true);
            }
        });
    }

    private void initSelector(int group, String groupnums) {
        initHours(group);
        mGroupAdapter = new CalendarTextAdapter(this, arry_groups, getHour(selectGroup), maxTextSize, minTextSize);
        wv_group.isRectCanvas(false);
        wv_group.setVisibleItems(5);
        wv_group.setViewAdapter(mGroupAdapter);
        wv_group.setCurrentItem(getHour(selectGroup));
        String currentText = (String) mGroupAdapter.getItemText(wv_group.getCurrentItem());
        setTextviewSize(currentText, mGroupAdapter);

//        wvHour.addScrollingListener(null);


        String[] groupNums = groupnums.split(",");
        initMinus(groupNums);
        mCountAdapter = new CalendarTextAdapter(this, arry_counts, getMinu(selectGroupNum), maxTextSize, minTextSize);
//        setTextviewSize(String.valueOf(getMinu(selectMinu)), mMineAdapter);
        wv_count.isRectCanvas(false);
        wv_count.setVisibleItems(5);
        wv_count.setViewAdapter(mCountAdapter);
//        wv_count.setCurrentItem(getMinu(selectMinu));

        String currentTextMine = (String) mCountAdapter.getItemText(wv_count.getCurrentItem());
        setTextviewSize(currentTextMine, mCountAdapter);

        wv_group.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mGroupAdapter.getItemText(wheel.getCurrentItem());
                selectGroup = currentText;
                setTextviewSize(currentText, mGroupAdapter);
            }
        });

        wv_group.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mGroupAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mGroupAdapter);
            }
        });

        wv_count.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mCountAdapter.getItemText(wheel.getCurrentItem());
                selectGroupNum = currentText;
                setTextviewSize(currentText, mCountAdapter);
            }
        });

        wv_count.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mCountAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mCountAdapter);
            }
        });
    }

    // 设置当前时间
    public void setTimes(String hour, String minu) {
        this.selectGroup = hour;
        this.selectGroupNum = minu;
    }

    /**
     * 初始化小时
     */
    public void initHours(int group) {
        for (int i = 0; i < group; i++) {
            if (i < group) {
                arry_groups.add("" + (i + 1));
            } else {
                arry_groups.add(i + "");
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
    public void initMinus(String[] groupNums) {
        for (int i = 0; i < groupNums.length; i++) {
//            if (i < 10) {
//                arry_counts.add("0" + i);
//            } else {
//                arry_counts.add(i + "");
//            }
            arry_counts.add(groupNums[i]);
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
        private TextView zu_Txt;
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.trainset_group_item, NO_RESOURCE, currentItem, maxsize, minsize);
            View view = LayoutInflater.from(context).inflate(R.layout.trainset_group_item, null);
            this.list = list;
            setItemTextResource(R.id.tempValue);
//            setItemResource(R.id.zu_Txt);
            zu_Txt = (TextView) view.findViewById(R.id.zu_Txt);
//             if (currentItem == getHour(selectHour)){
//                 zu_Txt.setVisibility(View.VISIBLE);
//             }


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
                StaticData.ViewScale(textvew, 0, 130);
                textvew.setTextColor(Color.parseColor("#2b2a2a"));
                textvew.setTextSize(maxTextSize);
            } else {
                StaticData.ViewScale(textvew, 0, 90);
                textvew.setTextColor(Color.parseColor("#2b2a2a"));
                textvew.setTextSize(minTextSize);
            }
        }
    }


}
