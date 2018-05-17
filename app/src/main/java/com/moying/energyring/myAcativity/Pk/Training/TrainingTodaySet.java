package com.moying.energyring.myAcativity.Pk.Training;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Training_TodaySet_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.HorizontalselectedView;

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
            TargetNum = horView.getSelectedString();
            next_Txt.setEnabled(false);

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
                    BGMFileName =  getBGMname(select_View.getSelectCount());
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
    private String getBGMname(int pos){
        String BGMStr = "梦的海洋";;
        if (pos == 0){
            BGMStr = "梦的海洋";
        }else if (pos == 1){
            BGMStr = "Intro";
        }else if (pos == 2){
            BGMStr = "Pacific Rim";
        }else if (pos == 3){
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

//                        TargetNum = oneData.getTrainlimit();

                        int size = oneData.getTrainlimit() / oneData.getGroupNum();
                        for (int i = 0; i < size; i++) {
                            int index = oneData.getGroupNum() * (i + 1);
                            dataStr.add(index + "");
                        }
                        horView.setshowCenterData(dataStr);
                        horView.setSeeSize(7);

                        TargetNum = horView.getSelectedString();

                        try {
//                            String[] fileNames = context.getResources().getAssets().list("bgm");
                            BGMnameArr = context.getAssets().list("bgm");//获取assets目录下的所有文件及目录名
                            BGMFileName = BGMnameArr[0];
                        } catch (IOException e) {
                            e.printStackTrace();
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
                    Intent intent = new Intent(context, LoginRegister.class);
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
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                next_Txt.setEnabled(true);
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, LoginRegister.class);
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


}
