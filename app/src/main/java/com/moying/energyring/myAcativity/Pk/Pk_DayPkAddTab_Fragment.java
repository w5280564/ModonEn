package com.moying.energyring.myAcativity.Pk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Goal_Model;
import com.moying.energyring.Model.ProjectModel;
import com.moying.energyring.Model.newPk_Model;
import com.moying.energyring.R;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Pk.Training.TrainingTodaySet;
import com.moying.energyring.myAdapter.DayPk_AddTab_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.lazyLoadFragment;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Admin on 2016/4/18.
 * 成长日志
 */
public class Pk_DayPkAddTab_Fragment extends lazyLoadFragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String Type;
    private String ProjectTypeID;
    private RecyclerView All_XRecy;
    private View parentView;
    private int PageIndex;
    private int pageSize;
    private List<ProjectModel> projectModel;

    public static Pk_DayPkAddTab_Fragment newInstance(String Type, String ProjectTypeID, List<ProjectModel> projectModel) {
        Pk_DayPkAddTab_Fragment newFragment = new Pk_DayPkAddTab_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type", Type);
        bundle.putString("ProjectTypeID", ProjectTypeID);
        bundle.putSerializable("projectModel", (Serializable) projectModel);
        newFragment.setArguments(bundle);
        //bundle可以在每个标签里传送数据
        return newFragment;
    }

    //    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//    private View parentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.activity_leran__goal, container, false);
        Bundle args = getArguments();
        Type = args != null ? args.getString("Type") : defaultHello;
        ProjectTypeID = args != null ? args.getString("ProjectTypeID") : defaultHello;
        projectModel = (List<ProjectModel>) (args != null ? args.getSerializable("projectModel") : defaultHello);

        View title_Include = (View) parentView.findViewById(R.id.title_Include);
        title_Include.setVisibility(View.GONE);
        All_XRecy = (RecyclerView) parentView.findViewById(R.id.All_XRecy);
//        All_XRecy.setLoadingMoreEnabled(false);//底部不加载
//        All_XRecy.setPullRefreshEnabled(false);
//        other_recycle.setLoadingListener(this);//添加事件
//        other_recycle.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁
        return parentView;
    }


    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        PageIndex = 1;
        pageSize = 10;
        ListData(getActivity(), saveFile.BaseUrl + saveFile.My_ProjectList_Search_Url + "?ProjectTypeID=" + ProjectTypeID);
    }


    @Override
    public void onRefresh() {//刷新
        lazyLoad();
    }

    @Override
    public void onLoadMore() {//加载更多
        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(getActivity(), saveFile.BaseUrl + saveFile.My_ProjectList_Search_Url + "?ProjectTypeID=" + ProjectTypeID);
    }


    @Override
    public void onResume() {
        super.onResume();
        projectModel = saveFile.getGosnClass(getActivity(), "moreModel", ProjectModel.class);
        isPrepared = true;
        lazyLoad();
    }


    public void initlist(final Context context) {
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
        final DayPk_AddTab_Adapter mAdapter = new DayPk_AddTab_Adapter(context, baseModel, mcheckflag);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new DayPk_AddTab_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(final View view, final int position) {
                DayPk_AddTab_Adapter.MyViewHolder viewHolder = (DayPk_AddTab_Adapter.MyViewHolder) view.getTag();

                if (baseModel.getData().get(position).isIs_Train()) {
                    Intent intent1 = new Intent(getActivity(), TrainingTodaySet.class);
                    intent1.putExtra("ProjectID", baseModel.getData().get(position).getProjectID() + "");
                    startActivity(intent1);

                } else {
                    Goal_Model.DataBean goal = baseModel.getData().get(position);
                    newPk_Model.DataBean model = new newPk_Model.DataBean();
                    model.setProjectID(goal.getProjectID());
                    model.setFilePath(goal.getFilePath());
                    model.setProjectName(goal.getProjectName());
                    model.setProjectUnit(goal.getProjectUnit());

                    Intent intent = new Intent(getActivity(), Pk_AddReport.class);
                    intent.putExtra("projectModel", model);
                    startActivity(intent);
                }


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
//                    saveFile.putClass(context, "moreModel", projectModel);
//                } else {
//                    removeData(position);
//                    saveFile.removeGsonOne(context, "moreModel", baseModel.getData().get(position).getProjectID());
//                }

//                if (mAdapter.mcheckflag.get(position)) {
//                    mAdapter.mcheckflag.put(position, false);
//                    viewHolder.my_Lin.setBackgroundResource(R.drawable.addtab_adapter);
//                    removeData(position);
//                    saveFile.removeGsonOne(context, "moreModel", baseModel.getData().get(position).getProjectID());
//
//                } else {
//                    mAdapter.mcheckflag.put(position, true);
//                    viewHolder.my_Lin.setBackgroundResource(R.drawable.addtab_adapter_select);
//                    Goal_Model.DataBean model = baseModel.getData().get(position);
//                    ProjectModel moreModel = new ProjectModel();
//                    moreModel.setProjectId(model.getProjectID());
//                    moreModel.setName(model.getProjectName());
//                    moreModel.setImgUrl(model.getFilePath());
//                    moreModel.setUnit(model.getProjectUnit());
//                    moreModel.setReportNum("");
//                    moreModel.setLimit(model.getLimit());
//                    projectModel.add(moreModel);
//                    saveFile.putClass(context, "moreModel", projectModel);
//                }
            }


            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
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

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
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


}
