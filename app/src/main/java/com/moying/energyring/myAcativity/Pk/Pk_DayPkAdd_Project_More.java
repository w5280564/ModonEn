package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Goal_Model;
import com.moying.energyring.Model.ProjectModel;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAdapter.Goal_Adapter_More;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pk_DayPkAdd_Project_More extends Activity {
    RecyclerView All_XRecy;
    List<ProjectModel> projectModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leran__goal);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#2b2a2a"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#ffffff"));
        cententtxt.setText("项目");
        Button right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setTextColor(Color.parseColor("#ffffff"));
        right_Btn.setText("完成");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);
        StaticData.ViewScale(right_Btn, 100, 80);

        All_XRecy = (RecyclerView) findViewById(R.id.All_XRecy);
//        All_XRecy.setPullRefreshEnabled(false);
//        All_XRecy.setLoadingMoreEnabled(false);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());

        projectModel = (List<ProjectModel>) getIntent().getSerializableExtra("baseModel");
//        choiceModel = baseModel;
//        choiceLin_List(choiceModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        personData(saveFile.BaseUrl + saveFile.DayPk_ProjectNotWalk);
//        personData(saveFile.BaseUrl + "/pk/GetProject");
    }

    final int CODE_MORE = 255;

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

    @Override
    public void onBackPressed() {
        resultString();
        super.onBackPressed();
    }

    public void initlist(Context context) {
        GridLayoutManager mMangaer = new GridLayoutManager(context, 3);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        All_XRecy.setHasFixedSize(true);
        Map<Integer, Boolean> mcheckflag = new HashMap<>();
        for (int i = 0; i < baseModel.getData().size(); i++) {//初始化
            mcheckflag.put(i, false);
        }
        if (projectModel != null || projectModel.size() > 0) {//有选择@人
            for (int k = 0; k < baseModel.getData().size(); k++) {
                for (int i = 0; i < projectModel.size(); i++) {
                    if (projectModel.get(i).getProjectId() == (baseModel.getData().get(k).getProjectID())) {
                        mcheckflag.put(k, true);
                    }
                }
            }
        }
        final Goal_Adapter_More mAdapter = new Goal_Adapter_More(context, baseModel, mcheckflag);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Goal_Adapter_More.OnItemClickLitener() {
            @Override
            public void onItemClick(final View view, final int position) {
                Goal_Adapter_More.MyViewHolder viewHolder = (Goal_Adapter_More.MyViewHolder) view.getTag();
                viewHolder.choice_check.toggle();
                mAdapter.mcheckflag.put(position, viewHolder.choice_check.isChecked());
                if (viewHolder.choice_check.isChecked()) {
                    Goal_Model.DataBean model = baseModel.getData().get(position);
                    ProjectModel moreModel = new ProjectModel();
                    moreModel.setProjectId(model.getProjectID());
                    moreModel.setName(model.getProjectName());
                    moreModel.setImgUrl(model.getFilePath());
                    moreModel.setUnit(model.getProjectUnit());
                    moreModel.setReportNum("");
                    projectModel.add(moreModel);
                    saveFile.putClass(Pk_DayPkAdd_Project_More.this, "moreModel", projectModel);
                } else {
                    removeData(position);
                    saveFile.removeGsonOne(Pk_DayPkAdd_Project_More.this, "moreModel", baseModel.getData().get(position).getProjectID());
                }
            }


            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        //为RecycleView绑定触摸事件
//        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
//            @Override
//            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                //首先回调的方法 返回int表示是否监听该方向
//                int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//拖拽
//                int swipeFlags = 0;//侧滑删除
//                return makeMovementFlags(dragFlags,swipeFlags);
////                int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;//拖拽
////                int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//侧滑删除
////                return makeMovementFlags(dragFlags,swipeFlags);
//            }
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                //滑动事件
//                Collections.swap(baseModel.getData(),viewHolder.getAdapterPosition(),target.getAdapterPosition());
//                mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                //侧滑事件
//                baseModel.getData().remove(viewHolder.getAdapterPosition());
//                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//            }
//
//            @Override
//            public boolean isLongPressDragEnabled() {
//                //是否可拖拽
//                return true;
//            }
//        });
//        helper.attachToRecyclerView(All_XRecy);

    }

    //删除数据
    private void removeData(int pos) {
        for (int i = 0; i < projectModel.size(); i++) {
            if (projectModel.get(i).getProjectId() == baseModel.getData().get(pos).getProjectID()) {
                projectModel.remove(i);
            }
        }
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
                        initlist(Pk_DayPkAdd_Project_More.this);
                    } else {
                        Toast.makeText(Pk_DayPkAdd_Project_More.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Pk_DayPkAdd_Project_More.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(Pk_DayPkAdd_Project_More.this, LoginRegister.class);
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
