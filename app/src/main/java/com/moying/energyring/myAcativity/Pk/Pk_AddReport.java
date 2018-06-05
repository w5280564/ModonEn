package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.JiFenAndBadge_Model;
import com.moying.energyring.Model.newPk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.PostingActivity;
import com.moying.energyring.network.saveFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Pk_AddReport extends Activity {

    private View reportbg_Rel;
    private SimpleDraweeView poject_sim;
    private TextView report_Name, report_Unit, report_zaoTxt;
    newPk_Model.DataBean projectModel;
    private EditText project_Edit;
    Button report_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk__add_report);
        reportbg_Rel = findViewById(R.id.reportbg_Rel);
        reportbg_Rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        View report_Rel = findViewById(R.id.report_Rel);
        View report_Title_Rel = findViewById(R.id.report_Title_Rel);
        poject_sim = (SimpleDraweeView) findViewById(R.id.poject_sim);
        report_Name = (TextView) findViewById(R.id.report_Name);
        project_Edit = (EditText) findViewById(R.id.project_Edit);
        report_Unit = (TextView) findViewById(R.id.report_Unit);
        report_zaoTxt = (TextView) findViewById(R.id.report_zaoTxt);
        report_Btn = (Button) findViewById(R.id.report_Btn);
        View colock_Name = findViewById(R.id.colock_Name);

        StaticData.ViewScale(report_Rel, 710, 500);
        StaticData.ViewScale(report_Title_Rel, 0, 120);
        StaticData.ViewScale(poject_sim, 80, 80);
        StaticData.ViewScale(report_Btn, 0, 120);
        StaticData.ViewScale(colock_Name, 40, 40);
        Intent intent = getIntent();
        projectModel = intent.getParcelableExtra("projectModel");

        initData();
        report_Btn.setOnClickListener(new report_Btn());
        colock_Name.setOnClickListener(new colock_Name());
    }

    private void initData() {
        if (!projectModel.equals("[]") && projectModel != null) {
            if (projectModel.getFilePath() != null) {
                Uri uri = Uri.parse(projectModel.getFilePath());
                poject_sim.setImageURI(uri);
            }
            report_Name.setText(projectModel.getProjectName());
            report_Unit.setText(projectModel.getProjectUnit());
            if (projectModel.getProjectName().equals("早起")) {
                report_zaoTxt.setVisibility(View.VISIBLE);
            }
            if (projectModel.getProjectName().equals("健康走")) {
//                saveFile.removeGsonOne(Pk_DayPkAdd_More.this, "moreModel", myBean.get(i).getProjectId());
//                continue;
            } else if (projectModel.getProjectName().equals("戒网络小说") || projectModel.getProjectName().equals("戒游戏") || projectModel.getProjectName().equals("早起")) {
                project_Edit.setEnabled(false);
                project_Edit.setText("1");
            }

        }
    }

    private class colock_Name implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Pk_AddReport.this, Pk_AddReport_Colock.class);
            intent.putExtra("ProjectID", projectModel.getProjectID() + "");
            startActivity(intent);
        }
    }

    private class report_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            if (StaticData.isSpace(project_Edit.getText().toString()) || project_Edit.getText().toString().equals("0")) {
                Toast.makeText(Pk_AddReport.this, "请填写pk数", Toast.LENGTH_SHORT).show();
                return;
            }
            report_Btn.setEnabled(false);
            AddPk_Data(Pk_AddReport.this, saveFile.BaseUrl + saveFile.AddPk_Url, "");
        }
    }


    public void AddPk_Data(final Context context, String baseUrl, String files) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
            params.setHeader("version", StaticData.getversionName(context));
        }
        JSONObject allObj = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
//            for (int i = 0; i < 1; i++) {
            JSONObject obj = new JSONObject();
            obj.put("ProjectID", projectModel.getProjectID());
            obj.put("ReportNum", project_Edit.getText().toString());
            jsonArray.put(obj);
//            }
            allObj.put("Report_Items", jsonArray);
            allObj.put("FileIDs", files);
            allObj.put("PostContent", "");
            boolean sync = false;
//            if (add_energy_Img.isChecked()) {
//                sync = true;
//            }
            allObj.put("Is_Sync", sync);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        params.setBodyContent(allObj.toString());
        params.setAsJsonContent(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                report_Btn.setEnabled(true);
                JiFenAndBadge_Model model = new Gson().fromJson(resultString, JiFenAndBadge_Model.class);
                if (model.isIsSuccess()) {
                    Toast.makeText(Pk_AddReport.this, "发布成功", Toast.LENGTH_SHORT).show();



                    Intent intent = new Intent(context, PostingActivity.class);
                    startActivity(intent);

                    Intent intentsucc = new Intent(context, Pk_AddReport_Succ.class);
                    intentsucc.putExtra("jiFenmodel", model);
                    startActivity(intentsucc);

                    finish();

                } else {
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                report_Btn.setEnabled(true);
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, MainLogin.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
                report_Btn.setEnabled(true);
            }

            @Override
            public void onFinished() {
            }
        });
    }


}
