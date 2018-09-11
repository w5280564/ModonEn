package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.ProjectModel;
import com.moying.energyring.Model.ProjectSeek_Comm_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.FlowLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Pk_DayPKAdd_ProjectSeek extends Activity {

    private FlowLayout hot_flexbox, history_flexbox;
    private LinearLayout seek_Lin;
    private List<ProjectModel> seekModel;
    private View clear_Txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeprice);
        setContentView(R.layout.pk_daypkadd_projectseek);

        initTitle();
        initView();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        addHistory();
    }

    private void initTitle() {
        View return_Btn = findViewById(R.id.return_Btn);
        View right_Btn = findViewById(R.id.right_Btn);
        seek_Lin = (LinearLayout) findViewById(R.id.seek_Lin);
        View seek_Img = findViewById(R.id.seek_Img);

        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(right_Btn, 100, 88);
        StaticData.ViewScale(seek_Lin, 0, 60);
        StaticData.ViewScale(seek_Img, 22, 24);
        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
        seek_Lin.setOnClickListener(new seek_Lin());
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
            finish();
        }
    }

    public class seek_Lin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Pk_DayPKAdd_ProjectSeek.this, Pk_DayPkAdd_Project_TabSeek.class);
            intent.putExtra("SearchKey", "");
            startActivity(intent);
        }
    }

    private void initView() {
        hot_flexbox = (FlowLayout) findViewById(R.id.hot_flexbox);
        history_flexbox = (FlowLayout) findViewById(R.id.history_flexbox);
        clear_Txt = (View) findViewById(R.id.clear_Txt);
        clear_Txt.setOnClickListener(new clear_Txt());
    }

    private class clear_Txt extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            saveFile.clearGson(Pk_DayPKAdd_ProjectSeek.this ,"seekModel");
            addHistory();
        }
    }

    private void initData() {
        ListData(Pk_DayPKAdd_ProjectSeek.this, saveFile.BaseUrl + saveFile.ProjectCommunity_List_Url);
    }


    private void addHistory() {
        if (seekModel != null) {
            seekModel.clear();
        }
        seekModel = new ArrayList<>();
        seekModel = saveFile.getGosnClass(Pk_DayPKAdd_ProjectSeek.this, "seekModel", ProjectModel.class);
        projectList(history_flexbox, seekModel);
    }


    ProjectSeek_Comm_Model listModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    listModel = new Gson().fromJson(resultString, ProjectSeek_Comm_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {

                        tagComm(hot_flexbox, listModel);
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


    public void tagComm(FlowLayout myFlex, final ProjectSeek_Comm_Model model) {
        if (myFlex != null) {
            myFlex.removeAllViews();
        }
        int size = model.getData().size();
        for (int i = 0; i < size; i++) {
            TextView projectTxt = new TextView(this);
            projectTxt.setBackgroundResource(R.drawable.projectseek_comm_bg);
//            projectTxt.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAllBg));
            projectTxt.setTextColor(ContextCompat.getColor(this, R.color.colorSecondBlue));
            projectTxt.setGravity(Gravity.CENTER);
            projectTxt.setText("#" + model.getData().get(i).getProjectName());
            RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            StaticData.layoutParamsScale(itemParams, 0, 50);
            int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 28);
            int padbot = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 30);
            itemParams.setMargins(pad, 0, pad, padbot);
            projectTxt.setLayoutParams(itemParams);
            projectTxt.setTag(i);
            myFlex.addView(projectTxt);
            projectTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();
                    Intent intent = new Intent(Pk_DayPKAdd_ProjectSeek.this, Pk_DayPkAdd_Project_TabSeek.class);
                    intent.putExtra("SearchKey", model.getData().get(tag).getProjectName());
                    startActivity(intent);
//                    setpage(tag);
//                    // 实例化汉字转拼音类
//                    CharacterParser characterParser = CharacterParser.getInstance();
//                    String pinyin = characterParser.getSelling(baseModel.getData().get(tag).getProjectName());
//                    MobclickAgent.onEvent(Pk_Daypk.this, pinyin);//统计页签

                }
            });
        }
    }

    public void projectList(FlowLayout myFlex, final List<ProjectModel> model) {
        if (myFlex != null) {
            myFlex.removeAllViews();
        }
        int size = model.size();
        for (int i = 0; i < size; i++) {
            TextView projectTxt = new TextView(this);
            projectTxt.setBackgroundResource(R.drawable.projectseek_comm_bg);
//            projectTxt.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAllBg));
            projectTxt.setTextColor(ContextCompat.getColor(this, R.color.colorSecondBlue));
            projectTxt.setGravity(Gravity.CENTER);
            projectTxt.setText("#" + model.get(i).getName());
            RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            StaticData.layoutParamsScale(itemParams, 0, 50);
            int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 28);
            int padbot = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 30);
            itemParams.setMargins(pad, 0, pad, padbot);
            projectTxt.setLayoutParams(itemParams);
            projectTxt.setTag(i);
            myFlex.addView(projectTxt);
            projectTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();


                    Intent intent = new Intent(Pk_DayPKAdd_ProjectSeek.this, Pk_DayPkAdd_Project_TabSeek.class);
                    intent.putExtra("SearchKey", model.get(tag).getName());
                    startActivity(intent);
//                    setpage(tag);
//                    // 实例化汉字转拼音类
//                    CharacterParser characterParser = CharacterParser.getInstance();
//                    String pinyin = characterParser.getSelling(baseModel.getData().get(tag).getProjectName());
//                    MobclickAgent.onEvent(Pk_Daypk.this, pinyin);//统计页签

                }
            });
        }
    }


}
