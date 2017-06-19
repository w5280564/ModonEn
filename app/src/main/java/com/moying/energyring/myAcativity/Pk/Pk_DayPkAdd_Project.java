package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Goal_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAdapter.Goal_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Pk_DayPkAdd_Project extends Activity {
    XRecyclerView All_XRecy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leran__goal);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#909090"));
        cententtxt.setText("项目");
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);

        All_XRecy = (XRecyclerView) findViewById(R.id.All_XRecy);
        All_XRecy.setPullRefreshEnabled(false);
        All_XRecy.setLoadingMoreEnabled(false);

        return_Btn.setOnClickListener(new return_Btn());
    }

    @Override
    protected void onResume() {
        super.onResume();
        personData(saveFile.BaseUrl + saveFile.dayPkProjectUrl);
//        personData(saveFile.BaseUrl + "/pk/GetProject");
    }

    final int RESULT_CODE = 2333;
    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            setResult(RESULT_CODE);
            finish();
        }
    }
    public void initlist(Context context) {
        GridLayoutManager mMangaer = new GridLayoutManager(context, 3);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        All_XRecy.setHasFixedSize(true);
        Goal_Adapter mAdapter = new Goal_Adapter(context, baseModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Goal_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(final View view, final int position) {
                ScaleAnimation scal = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scal.setDuration(100);
//                scal.setFillAfter(false);//动画执行完后是否停留在执行完的状
                Goal_Adapter.MyViewHolder viewHolder = (Goal_Adapter.MyViewHolder) view.getTag();
                viewHolder.my_Lin.startAnimation(scal);
                scal.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent();
                        intent.putExtra("unit",baseModel.getData().get(position).getProjectUnit());
                        intent.putExtra("titleName", baseModel.getData().get(position).getProjectName());
                        intent.putExtra("ProjectID", baseModel.getData().get(position).getProjectID()+"");
                        intent.putExtra("imgUrl",baseModel.getData().get(position).getFilePath());
                        setResult(RESULT_CODE,intent);
                        finish();
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
//                StaticData.ViewScale(viewHolder.my_simple, 210, 210);
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }
    Goal_Model baseModel;
    public void personData(String baseUrl) {
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
                        initlist(Pk_DayPkAdd_Project.this);
                    } else {
                        Toast.makeText(Pk_DayPkAdd_Project.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Pk_DayPkAdd_Project.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")){
                    Intent intent = new Intent(Pk_DayPkAdd_Project.this,LoginRegister.class);
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
