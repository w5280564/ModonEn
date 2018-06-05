package com.moying.energyring.myAcativity.Pk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.DayPkDetail_Model;
import com.moying.energyring.Model.ReportHistory_Model;
import com.moying.energyring.Model.newPk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.DayPk_CoustHistoryFragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 2016/4/18.
 * 成长日志
 */
public class Pk_DayPk_Project_Detail_CoustFragment extends Fragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String Type;
    private String ProjectID;
    private RecyclerView my_recycler;
    private XRecyclerView other_recycle;
    private String types;
    private String content;
    private View parentView;
    private int PageIndex;
    private int pageSize;
    private TextView ka_Txt, zong_Txt, ka_Hint, zong_Hint, zongka_Txt, zongka_Hint, sure_Txt;
    private LinearLayout zong_Lin, zongka_Lin;
    private LinearLayout sure_Lin;
    private newPk_Model newPk_model;
    private int pos;

    public static Pk_DayPk_Project_Detail_CoustFragment newInstance(int pos, String Type, String ProjectID, newPk_Model newPk_model) {
        Pk_DayPk_Project_Detail_CoustFragment newFragment = new Pk_DayPk_Project_Detail_CoustFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        bundle.putString("Type", Type);
        bundle.putString("ProjectID", ProjectID);
        bundle.putParcelable("newPk_model", newPk_model);
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
        parentView = inflater.inflate(R.layout.pk_daypk_coustfragment, container, false);
        Bundle args = getArguments();
        Type = args != null ? args.getString("Type") : defaultHello;
        ProjectID = args != null ? args.getString("ProjectID") : defaultHello;
        newPk_model = (newPk_Model) (args != null ? args.getParcelable("newPk_model") : defaultHello);
        pos = args.getInt("pos", 0);

        sure_Lin = (LinearLayout) parentView.findViewById(R.id.sure_Lin);
        sure_Txt = (TextView) parentView.findViewById(R.id.sure_Txt);
        StaticData.ViewScale(sure_Lin, 0, 127);
        StaticData.ViewScale(sure_Txt, 410, 80);

        other_recycle = (XRecyclerView) parentView.findViewById(R.id.other_recycle);
//        other_recycle.setLoadingMoreEnabled(false);//底部不加载
        other_recycle.setPullRefreshEnabled(false);
        other_recycle.setLoadingListener(this);//添加事件
        other_recycle.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁
        initAddHeadView(other_recycle, getActivity(), parentView);
        clipPaddTop(127);
        sure_Txt.setOnClickListener(new sure_Txt());
        return parentView;
    }

    private void initAddHeadView(XRecyclerView myView, Context context, View view) {
        View header = LayoutInflater.from(context).inflate(R.layout.historyfragment_head, null);
        RelativeLayout.LayoutParams headParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        int magTop = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 10);
        headParams.setMargins(0, magTop, 0, 0);
        StaticData.layoutParamsScale(headParams, 0, 128);
        header.setLayoutParams(headParams);

        ka_Txt = (TextView) header.findViewById(R.id.ka_Txt);
        ka_Hint = (TextView) header.findViewById(R.id.ka_Hint);
        zong_Lin = (LinearLayout) header.findViewById(R.id.zong_Lin);
        zong_Txt = (TextView) header.findViewById(R.id.zong_Txt);
        zong_Hint = (TextView) header.findViewById(R.id.zong_Hint);
        zongka_Lin = (LinearLayout) header.findViewById(R.id.zongka_Lin);
        zongka_Txt = (TextView) header.findViewById(R.id.zongka_Txt);
        zongka_Hint = (TextView) header.findViewById(R.id.zongka_Hint);

//        StaticData.ViewScale(colock_Img,40,40);
        myView.addHeaderView(header);
    }

    public void clipPaddTop(int index) {
        index = index;//128状态栏加tab高度 26是底部高出部分
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * index);
        other_recycle.setClipToPadding(false);
        other_recycle.setPadding(0, 0, 0, pad);
    }


    private class sure_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Pk_AddReport.class);
            intent.putExtra("projectModel", newPk_model.getData().get(pos));
            startActivity(intent);
        }
    }

    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;

//    @Override
//    protected void lazyLoad() {
//        if (!isPrepared || !isVisible) {
//            return;
//        }
//        PageIndex = 1;
//        pageSize = 10;
//        ListData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportRecordProject_Url + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
//    }

    @Override
    public void onRefresh() {//刷新
//        lazyLoad();
    }

    @Override
    public void onLoadMore() {//加载更多
        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportRecordProject_Url + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
//        lazyLoad();
        initData();
    }

    private void initData() {
        myRankData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportRank_Url + "?ProjectID=" + ProjectID, 1);
        PageIndex = 1;
        pageSize = 10;
        ListData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportRecordProject_Url + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    private void initsetView() {
        if (Integral_Model.isIsSuccess() && !Integral_Model.getData().equals("[]")) {
            DayPkDetail_Model.DataBean oneData = Integral_Model.getData();
            NumberFormat nf = new DecimalFormat("#.#");//# 0不显示
            ka_Hint.setText("总累计数");
            zong_Hint.setText("本月累计数");
            ka_Txt.setText(nf.format(oneData.getReportNum_All()));
            if (oneData.getLimit() == 1) {
                zong_Txt.setText(nf.format(oneData.getReportFre_Month()));
//                zong_Lin.setVisibility(View.VISIBLE);
            } else {
//                ka_Txt.setText(nf.format(oneData.getReportNum_All()));
//                ka_Hint.setText("总打卡数");
//                zong_Lin.setVisibility(View.GONE);
                zong_Txt.setText(nf.format(oneData.getReportNum_Month()));
            }
            if (oneData.getProjectName().equals("健康走")) {
                zongka_Lin.setVisibility(View.GONE);
            } else {
                zongka_Lin.setVisibility(View.VISIBLE);
                zongka_Hint.setText("累计打卡");
                zongka_Txt.setText(oneData.getReportFre_All() + "");
            }
        } else {
            Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    DayPk_CoustHistoryFragment_Adapter mAdapter;

    float mLastY = -1;

    @SuppressLint("ClickableViewAccessibility")
    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new DayPk_CoustHistoryFragment_Adapter(context, baseModel, listModel);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new DayPk_CoustHistoryFragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(context, PersonMyCenter_Other.class);
//                intent.putExtra("UserID", baseModel.get(position).getUserID() + "");
//                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        other_recycle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mLastY == -1) {
                    mLastY = motionEvent.getRawY();
                }
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!other_recycle.isOnTop()) {
                            sure_Lin.setBackgroundColor(Color.parseColor("#992b2a2a"));
                            sure_Txt.setVisibility(View.GONE);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        sure_Lin.setBackgroundColor(Color.parseColor("#ff2b2a2a"));
                        sure_Txt.setVisibility(View.VISIBLE);
                        break;
                }

                return false;
            }
        });

    }


    DayPkDetail_Model Integral_Model;

    public void myRankData(final Context context, String baseUrl, final int isChange) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Integral_Model = new Gson().fromJson(resultString, DayPkDetail_Model.class);
                    if (Integral_Model.isIsSuccess() && !Integral_Model.getData().equals("[]")) {
//                        if (isChange == 0) {
//                            initLocaData(ac_tab_layout);
//                            tabViewSetView(ac_tab_layout);
//                            resetTablayout(ac_tab_layout);
//                        } else {
                        changeAddImg(Integral_Model);
//                        }
                        initsetView();

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

    private void changeAddImg(DayPkDetail_Model pkModel) {
        //是否可以打卡

        Type = pkModel.getData().getReportID() + "";
        String projectName = pkModel.getData().getProjectName();
        int Limit = pkModel.getData().getLimit();

        if (Limit == 1) { //上限是1的项目
            if (Type.equals("0")) {
                sure_Lin.setVisibility(View.VISIBLE);
            } else {
                sure_Lin.setVisibility(View.GONE);
            }
        } else {
            if (projectName.equals("健康走")) {
                sure_Lin.setVisibility(View.GONE);
            } else {
                sure_Lin.setVisibility(View.VISIBLE);
            }
        }


    }


    private List<ReportHistory_Model.DataBean> baseModel;
    ReportHistory_Model listModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    if (PageIndex == 1) {
                        if (baseModel != null) {
                            baseModel.clear();
                        }
                        baseModel = new ArrayList<>();
                    }
                    listModel = new Gson().fromJson(resultString, ReportHistory_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
                        baseModel.addAll(listModel.getData());
                        if (PageIndex == 1) {
                            other_recycle.refreshComplete();
                            initlist(getActivity());
                        } else {
                            other_recycle.loadMoreComplete();
                            mAdapter.addMoreData(baseModel);
                        }
                    } else {
                        Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
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
