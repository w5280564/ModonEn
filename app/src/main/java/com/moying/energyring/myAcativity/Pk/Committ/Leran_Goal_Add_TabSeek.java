package com.moying.energyring.myAcativity.Pk.Committ;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Goal_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.Leran_Goal_Add_TabSeek_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Leran_Goal_Add_TabSeek extends Activity {
    RecyclerView All_XRecy;
    private EditText seek_Edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyDialog);
//        setContentView(R.layout.pk_daypkadd_project_tabseek);
        setView();
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        View return_Btn = findViewById(R.id.return_Btn);
        seek_Edit = (EditText) findViewById(R.id.seek_Edit);
        View right_Btn = findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.INVISIBLE);

        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(seek_Edit, 0, 52);
        StaticData.ViewScale(right_Btn, 100, 88);


        All_XRecy = (RecyclerView) findViewById(R.id.All_XRecy);
//        All_XRecy.setPullRefreshEnabled(false);
//        All_XRecy.setLoadingMoreEnabled(false);
        return_Btn.setOnClickListener(new return_Btn());
        seek_Edit.addTextChangedListener(new seek_Edit());
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

    @Override
    protected void onResume() {
        super.onResume();
//        personData(saveFile.BaseUrl + "/pk/GetProject");
    }


    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private String SearchKey;

    private class seek_Edit implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (StaticData.Stringspace(editable.toString())) {
                SearchKey = "";
                addListData(SearchKey);
            } else {
                SearchKey = editable.toString();
                addListData(SearchKey);
            }
        }
    }

    private void addListData(String seekStr) {
        ListData(this, saveFile.BaseUrl + saveFile.My_ProjectList_Search_Url + "?SearchKey=" + seekStr);
    }


    public void initlist(Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        All_XRecy.setHasFixedSize(true);
        final Leran_Goal_Add_TabSeek_Adapter mAdapter = new Leran_Goal_Add_TabSeek_Adapter(context, baseModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Leran_Goal_Add_TabSeek_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(final View view, final int position) {
                ScaleAnimation scal = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scal.setDuration(100);
//                scal.setFillAfter(false);//动画执行完后是否停留在执行完的状
                Leran_Goal_Add_TabSeek_Adapter.MyViewHolder viewHolder = (Leran_Goal_Add_TabSeek_Adapter.MyViewHolder) view.getTag();
                viewHolder.my_Rel.startAnimation(scal);
                scal.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(Leran_Goal_Add_TabSeek.this, Leran_TimeChoice.class);
                        intent.putExtra("unit", baseModel.getData().get(position).getProjectUnit());
                        intent.putExtra("titleName", baseModel.getData().get(position).getProjectName());
                        intent.putExtra("ProjectID", baseModel.getData().get(position).getProjectID()+"");
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });


            }


            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

    }


    Goal_Model baseModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    baseModel = new Gson().fromJson(resultString, Goal_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
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
