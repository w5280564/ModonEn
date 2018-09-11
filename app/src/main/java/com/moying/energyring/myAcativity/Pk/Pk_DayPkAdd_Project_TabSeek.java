package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.ProjectModel;
import com.moying.energyring.Model.Project_TabSeek_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.Pk_Project_TabSeek_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Pk_DayPkAdd_Project_TabSeek extends Activity {
    RecyclerView All_XRecy;
    List<ProjectModel> seekModel;
    private EditText seek_Edit;
    private View ch_Rel;
    private TextView no_Txt, ch_Txt, sure_Txt;
    Button right_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyDialog);
//        setContentView(R.layout.pk_daypkadd_project_tabseek);
        setView();
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        View return_Btn = findViewById(R.id.return_Btn);
        LinearLayout seek_Lin = (LinearLayout) findViewById(R.id.seek_Lin);
        View seek_Img = findViewById(R.id.seek_Img);
        seek_Edit = (EditText) findViewById(R.id.seek_Edit);
        right_Btn = (Button) findViewById(R.id.right_Btn);
        ch_Rel = findViewById(R.id.ch_Rel);
        no_Txt = (TextView) findViewById(R.id.no_Txt);
        ch_Txt = (TextView) findViewById(R.id.ch_Txt);

        StaticData.ViewScale(return_Btn, 80, 88);
//        StaticData.ViewScale(seek_Edit, 0, 52);
        StaticData.ViewScale(right_Btn, 100, 88);
        StaticData.ViewScale(ch_Rel, 0, 110);
        StaticData.ViewScale(seek_Lin, 0, 60);
        StaticData.ViewScale(seek_Img, 22, 24);

        All_XRecy = (RecyclerView) findViewById(R.id.All_XRecy);
//        All_XRecy.setPullRefreshEnabled(false);
//        All_XRecy.setLoadingMoreEnabled(false);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
//        seek_Edit.addTextChangedListener(new seek_Edit());
        ch_Txt.setOnClickListener(new ch_Txt());
        seek_Edit.setOnEditorActionListener(new seek_Edit());

        seekModel = new ArrayList<>();
//        projectModel = (List<ProjectModel>) getIntent().getSerializableExtra("baseModel");
        SearchKey = getIntent().getStringExtra("SearchKey");
//        choiceModel = baseModel;
//        choiceLin_List(choiceModel);
//        personData(saveFile.BaseUrl + saveFile.DayPk_ProjectNotWalk);

//        right_Btn.setText("创建");
        initData();
    }

    public void setView() {
//        setTheme(R.style.MyDialog);
        setContentView(getIntent().getIntExtra("view", R.layout.pk_daypkadd_project_tabseek));
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(layoutParams);
    }

    private void initData() {

        if (!StaticData.isSpace(SearchKey)) {
            seek_Edit.setText(SearchKey);
            addListData(SearchKey);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        personData(saveFile.BaseUrl + "/pk/GetProject");
    }

    final int CODE_MORE = 255;

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent();
//            intent.putExtra("projectModel", (Serializable) projectModel);
//            setResult(CODE_MORE, intent);
            finish();
        }
    }

    public class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
//            if (right_Btn.getText().toString().equals("创建")) {
//                Intent intent = new Intent(Pk_DayPkAdd_Project_TabSeek.this, Pk_DayPkAdd_Project_Crate.class);
//                intent.putExtra("project", seek_Edit.getText().toString());
//                startActivity(intent);
//            } else {
//                resultString();
//            }
        }
    }

    public class ch_Txt implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Pk_DayPkAdd_Project_TabSeek.this, Pk_DayPkAdd_Project_Crate.class);
            intent.putExtra("project", seek_Edit.getText().toString());
            startActivity(intent);
        }
    }

    private void resultString() {
//        Intent intent = new Intent();
//        intent.putExtra("projectModel", (Serializable) projectModel);
//        setResult(CODE_MORE, intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        resultString();
        super.onBackPressed();
    }

    private String SearchKey;

//    private class seek_Edit implements TextWatcher {
//
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//            if (StaticData.Stringspace(editable.toString())) {
//                right_Btn.setText("创建");
//                SearchKey = "";
////                addListData(SearchKey);
//                ch_Rel.setVisibility(View.GONE);
//                All_XRecy.setVisibility(View.GONE);
//            } else {
//                right_Btn.setText("确定");
//                SearchKey = editable.toString();
//                addListData(SearchKey);
//            }
//        }
//    }

    public class seek_Edit implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (!StaticData.isSpace(seek_Edit.getText().toString())) {
                SearchKey = seek_Edit.getText().toString();

                ProjectModel moreModel = new ProjectModel();
//                moreModel.setProjectId("");
                moreModel.setName(SearchKey);
//                moreModel.setImgUrl("");
//                moreModel.setUnit("");
//                moreModel.setReportNum("");
                seekModel.add(moreModel);
                //增加一条数据
                saveFile.saveGsonOne(Pk_DayPkAdd_Project_TabSeek.this, "seekModel", moreModel);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {//打开软键盘
                    imm.hideSoftInputFromWindow(seek_Edit.getWindowToken(), 0);
                }
                addListData(SearchKey);
            }
            return true;
        }
    }

    private void addListData(String seekStr) {
        ListData(this, saveFile.BaseUrl + saveFile.ProjectCommunity_Seek_Url + "?SearchKey=" + seekStr);
    }


    public void initlist(Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        mMangaer.setOrientation(LinearLayoutManager.VERTICAL);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
//        Map<Integer, Boolean> mcheckflag = new HashMap<>();
//        for (int i = 0; i < baseModel.getData().size(); i++) {//初始化
//            mcheckflag.put(i, false);
//        }
//        if (projectModel != null || projectModel.size() > 0) {//有选择@人
//            for (int k = 0; k < baseModel.getData().size(); k++) {
//                for (int i = 0; i < projectModel.size(); i++) {
//                    if (projectModel.get(i).getProjectId() == (baseModel.getData().get(k).getProjectID())) {
//                        mcheckflag.put(k, true);
//                    }
//                }
//            }
//        }

        final Pk_Project_TabSeek_Adapter mAdapter = new Pk_Project_TabSeek_Adapter(context, baseModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Pk_Project_TabSeek_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(final View view, final int position) {

                Intent intent = new Intent(Pk_DayPkAdd_Project_TabSeek.this, Pk_DayPk_Community.class);
                intent.putExtra("ProjectID", baseModel.getData().get(position).getProjectID() + "");
                startActivity(intent);
//                Pk_Project_TabSeek_Adapter.MyViewHolder viewHolder = (Pk_Project_TabSeek_Adapter.MyViewHolder) view.getTag();
//                viewHolder.choice_check.toggle();
//                mAdapter.mcheckflag.put(position, viewHolder.choice_check.isChecked());
//                if (viewHolder.choice_check.isChecked()) {
//                    Goal_Model.DataBean model = baseModel.getData().get(position);
//                    ProjectModel moreModel = new ProjectModel();
//                    moreModel.setProjectId(model.getProjectID());
//                    moreModel.setName(model.getProjectName());
//                    moreModel.setImgUrl(model.getFilePath());
//                    moreModel.setUnit(model.getProjectUnit());
//                    moreModel.setReportNum("");
//                    projectModel.add(moreModel);
//                    saveFile.putClass(Pk_DayPkAdd_Project_TabSeek.this, "moreModel", projectModel);
//                } else {
//                    removeData(position);
//                    saveFile.removeGsonOne(Pk_DayPkAdd_Project_TabSeek.this, "moreModel", baseModel.getData().get(position).getProjectID());
//                }

//                if (baseModel.getData().get(position).isIs_Train()) {
//                    Intent intent1 = new Intent(Pk_DayPkAdd_Project_TabSeek.this, TrainingTodaySet.class);
//                    intent1.putExtra("ProjectID", baseModel.getData().get(position).getProjectID() + "");
//                    startActivity(intent1);

//                } else {
//                    Goal_Model.DataBean goal = baseModel.getData().get(position);
//                    newPk_Model.DataBean model = new newPk_Model.DataBean();
//                    model.setProjectID(goal.getProjectID());
//                    model.setFilePath(goal.getFilePath());
//                    model.setProjectName(goal.getProjectName());
//                    model.setProjectUnit(goal.getProjectUnit());
//
//                    Intent intent = new Intent(Pk_DayPkAdd_Project_TabSeek.this, Pk_AddReport.class);
//                    intent.putExtra("projectModel", model);
//                    startActivity(intent);
//                }

            }


            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

    }

    //删除数据
//    private void removeData(int pos) {
//        for (int i = 0; i < projectModel.size(); i++) {
//            if (projectModel.get(i).getProjectId() == baseModel.getData().get(pos).getProjectID()) {
//                projectModel.remove(i);
//            }
//        }
//    }


    Project_TabSeek_Model baseModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    baseModel = new Gson().fromJson(resultString, Project_TabSeek_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
                        if (baseModel.getData().size() == 0) {

                            String contextStr = "未找到习惯";
//                            String endStr = String.format(contextStr, seek_Edit.getText().toString());
                            String endStr = contextStr +"#"+seek_Edit.getText().toString();
                            TextsColor(contextStr.length(), endStr.length(),endStr,no_Txt);
                            ch_Rel.setVisibility(View.VISIBLE);
                            All_XRecy.setVisibility(View.GONE);
                        } else {
                            ch_Rel.setVisibility(View.GONE);
                            All_XRecy.setVisibility(View.VISIBLE);
                            initlist(context);
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

    public void TextsColor(int startSize, int endSize, String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
        styledText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorSecondBlue)), startSize, endSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//修改颜色
        myTxt.setText(styledText);
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
