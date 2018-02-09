package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.ProjectModel;
import com.moying.energyring.Model.pk_Project_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.groupadapter.BaseViewHolder;
import com.moying.energyring.waylenBaseView.groupadapter.GroupedRecyclerViewAdapter;
import com.moying.energyring.waylenBaseView.groupadapter.NoFooterAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;

public class Pk_DayPKAdd_Project_Classification extends Activity {
    List<ProjectModel> projectModel;
    final int CODE_MORE = 255;
    private EditText seek_Edit;
    private TextView all_Txt;
    private RecyclerView rv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pkday_pkaddproject__classification);


        projectModel = (List<ProjectModel>) getIntent().getSerializableExtra("baseModel");

        View return_Btn = findViewById(R.id.return_Btn);
        seek_Edit = (EditText) findViewById(R.id.seek_Edit);
        View right_Btn = findViewById(R.id.right_Btn);
         all_Txt = (TextView) findViewById(R.id.all_Txt);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);

        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(seek_Edit, 0, 52);
        StaticData.ViewScale(right_Btn, 100, 88);
        StaticData.ViewScale(all_Txt, 100, 60);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());


        personData(Pk_DayPKAdd_Project_Classification.this,saveFile.BaseUrl + saveFile.My_ProjectType_Url);
    }


    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            resultString();
        }
    }

    public class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            resultString();
        }
    }

    private void resultString() {
        Intent intent = new Intent();
        intent.putExtra("projectModel", (Serializable) projectModel);
        setResult(CODE_MORE, intent);
        finish();
    }

    public void initlist(Context context) {
        rv_list.setLayoutManager(new LinearLayoutManager(context));
        NoFooterAdapter adapter = new NoFooterAdapter(this, project_Infication_Model);
        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                      int groupPosition) {
                Toast.makeText(Pk_DayPKAdd_Project_Classification.this, "组头：groupPosition = " + groupPosition,
                        Toast.LENGTH_LONG).show();
                Log.e("eee", adapter.toString() + "  " + holder.toString());
            }
        });

        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,int groupPosition, int childPosition) {
                Toast.makeText(Pk_DayPKAdd_Project_Classification.this, "子项：groupPosition = " + groupPosition
                                + ", childPosition = " + childPosition,
                        Toast.LENGTH_LONG).show();
//              TextView chid =  holder.get(R.id.tv_child);
//              chid.setText("点击"+childPosition);

            }
        });
        rv_list.setAdapter(adapter);
    }

    pk_Project_Model project_Infication_Model;
    public void personData(final Context context , String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                     project_Infication_Model = new Gson().fromJson(resultString, pk_Project_Model.class);

//                    baseModel = new Gson().fromJson(resultString, Goal_Model.class);
                    if (project_Infication_Model.isIsSuccess() && !project_Infication_Model.getData().equals("[]")) {
                        initlist(context);
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



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
