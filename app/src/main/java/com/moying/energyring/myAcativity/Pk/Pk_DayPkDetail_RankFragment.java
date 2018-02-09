package com.moying.energyring.myAcativity.Pk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.DayPkDetail_Model;
import com.moying.energyring.Model.DayPkList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_Other;
import com.moying.energyring.myAcativity.Person.Person_Relus;
import com.moying.energyring.myAdapter.DayPk_DetailFragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.lazyLoadFragment;
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
public class Pk_DayPkDetail_RankFragment extends lazyLoadFragment implements XRecyclerView.LoadingListener {
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
    private SimpleDraweeView project_Sim;
    private TextView rank_Txt, rankChange_Txt, time_Txt, fen_Txt;
    private  DayPkDetail_Model Integral_Model;
    private ImageView colock_Img;

    public static Pk_DayPkDetail_RankFragment newInstance(String Type, String ProjectID,DayPkDetail_Model Integral_Model) {
        Pk_DayPkDetail_RankFragment newFragment = new Pk_DayPkDetail_RankFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type", Type);
        bundle.putString("ProjectID", ProjectID);
        bundle.putParcelable("Integral_Model", Integral_Model);
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
        parentView = inflater.inflate(R.layout.basexrecycle, container, false);
        Bundle args = getArguments();
        Type = args != null ? args.getString("Type") : defaultHello;
        ProjectID = args != null ? args.getString("ProjectID") : defaultHello;
        Integral_Model = (DayPkDetail_Model) (args != null ? args.getParcelable("Integral_Model") : defaultHello);

        other_recycle = (XRecyclerView) parentView.findViewById(R.id.other_recycle);
//        other_recycle.setLoadingMoreEnabled(false);//底部不加载
        other_recycle.setPullRefreshEnabled(false);
        other_recycle.setLoadingListener(this);//添加事件
        other_recycle.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁
        initAddHeadView(other_recycle, getActivity(), parentView);

        return parentView;
    }

    private void initAddHeadView(XRecyclerView myView, Context context, View view) {
        View header = LayoutInflater.from(context).inflate(R.layout.rankfragment_head, null);
        RelativeLayout.LayoutParams headParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        StaticData.layoutParamsScale(headParams, 0, 248);
        header.setLayoutParams(headParams);
        project_Sim = (SimpleDraweeView) header.findViewById(R.id.project_Sim);
         colock_Img = (ImageView) header.findViewById(R.id.colock_Img);
        rank_Txt = (TextView) header.findViewById(R.id.rank_Txt);
        rankChange_Txt = (TextView) header.findViewById(R.id.rankChange_Txt);
        time_Txt = (TextView) header.findViewById(R.id.time_Txt);
        fen_Txt = (TextView) header.findViewById(R.id.fen_Txt);
        StaticData.ViewScale(project_Sim, 80, 80);
        StaticData.ViewScale(colock_Img, 40, 40);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        int mag = (int)(Float.parseFloat(saveFile.getShareData("scale",getActivity())) *10);
//        params.setMargins(mag,mag,0,0);
//        tui_Txt.setLayoutParams(params);
//        head_recycle = (RecyclerView) header.findViewById(R.id.head_recycle);
//        head_recycle.setFocusable(false);
        fen_Txt.setOnClickListener(new fen_Txt());
        colock_Img.setOnClickListener(new colock_Img());
        myView.addHeaderView(header);
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
        myRankData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportRank_Url + "?ProjectID=" + ProjectID);

        PageIndex = 1;
        pageSize = 10;
        ListData(getActivity(), saveFile.BaseUrl + saveFile.ReportRankUrl + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    @Override
    public void onRefresh() {//刷新
        lazyLoad();
    }

    @Override
    public void onLoadMore() {//加载更多
        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(getActivity(), saveFile.BaseUrl + saveFile.ReportRankUrl + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    @Override
    public void onResume() {
        super.onResume();

        isPrepared = true;
        lazyLoad();
    }

    private class fen_Txt implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Person_Relus.class);
            startActivity(intent);
        }
    }
private class colock_Img implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(),Pk_AddReport_Colock.class);
            intent.putExtra("ProjectID",ProjectID+"");
            startActivity(intent);
        }
    }

    private void initsetView(){
        if (Integral_Model.isIsSuccess() && !Integral_Model.getData().equals("[]")) {
            DayPkDetail_Model.DataBean oneData = Integral_Model.getData();
            if (oneData.getProjectName().equals("健康走")){
                colock_Img.setVisibility(View.INVISIBLE);
            }else {
                colock_Img.setVisibility(View.VISIBLE);
            }

            if (oneData.getFilePath() != null) {
                Uri uri = Uri.parse(oneData.getFilePath());
                project_Sim.setImageURI(uri);
            }
            if (oneData.getLimit() == 1){
                time_Txt.setVisibility(View.GONE);
                fen_Txt.setVisibility(View.GONE);
            }

            if (oneData.getRanking() == 0){
                rank_Txt.setText("---");

                String rankString;
                if (oneData.getProjectName().equals("早起")){
                    rankString = "您今日还未打卡，暂无排名 \n 早晨5:00-7:00累计签到有效";
                }else {
                    rankString = "您今日还未打卡，暂无排名";
                }
                rankChange_Txt.setText(rankString);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.BELOW,R.id.rankChange_Txt);
                time_Txt.setLayoutParams(params);

            }else {
                rank_Txt.setText("第" + oneData.getRanking() + "名");
                String rankString;
                if (oneData.getUpNum() > 0) {
                    NumberFormat nf = new DecimalFormat("#.#");//# 0不显示
                    rankString = "比昨天进步了" + nf.format(oneData.getUpNum()) + oneData.getProjectUnit() + "，您将获得3积分";
//                    rankChange_Txt.setText(rankString);
                }else{
                    rankString = "";
//                    rankChange_Txt.setText(rankString);
                }
                if (oneData.getProjectName().equals("早起")){
                    rankString = rankString + "\n 早晨5:00-7:00累计签到有效";
                }
                rankChange_Txt.setText(rankString);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW,R.id.rankChange_Txt);
                time_Txt.setLayoutParams(params);

            }

        } else {
            Toast.makeText(getActivity(), "今日未打卡", Toast.LENGTH_SHORT).show();
        }
    }

    DayPk_DetailFragment_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new DayPk_DetailFragment_Adapter(context, baseModel, listModel);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new DayPk_DetailFragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, PersonMyCenter_Other.class);
                intent.putExtra("UserID", baseModel.get(position).getUserID() + "");
                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<DayPkList_Model.DataBean> baseModel;
    DayPkList_Model listModel;

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
                    listModel = new Gson().fromJson(resultString, DayPkList_Model.class);
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

//    DayPkDetail_Model Integral_Model;

    public void myRankData(final Context context, String baseUrl) {
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




}
