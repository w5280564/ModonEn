package com.moying.energyring.myAcativity.Pk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Communit_Rank_Model;
import com.moying.energyring.R;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_And_Other;
import com.moying.energyring.myAdapter.Communit_RankFragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.lazyLoadFragment;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *
 */
public class Pk_DayPk_Communit_RankFragment extends lazyLoadFragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String Type;
    private String ProjectID;
    private XRecyclerView other_recycle;
    private View parentView;
    private int PageIndex;
    private int pageSize;

    public static Pk_DayPk_Communit_RankFragment newInstance(String Type, String ProjectID) {
        Pk_DayPk_Communit_RankFragment newFragment = new Pk_DayPk_Communit_RankFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type", Type);
        bundle.putString("ProjectID", ProjectID);
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

        other_recycle = (XRecyclerView) parentView.findViewById(R.id.other_recycle);
//        other_recycle.setLoadingMoreEnabled(false);//底部不加载
        other_recycle.setPullRefreshEnabled(false);
        other_recycle.setLoadingListener(this);//添加事件
        other_recycle.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁

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
        ListData(getActivity(), saveFile.BaseUrl + saveFile.Community_Ranking_List_Url + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    @Override
    public void onRefresh() {//刷新
        lazyLoad();
    }

    @Override
    public void onLoadMore() {//加载更多
        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(getActivity(), saveFile.BaseUrl + saveFile.Community_Ranking_List_Url + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    @Override
    public void onResume() {
        super.onResume();

        isPrepared = true;
        lazyLoad();
    }



    Communit_RankFragment_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new Communit_RankFragment_Adapter(context, baseModel, listModel,ProjectID);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Communit_RankFragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, PersonMyCenter_And_Other.class);
                intent.putExtra("UserID", baseModel.get(position).getUserID() + "");
                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<Communit_Rank_Model.DataBean.CommRankingBean> baseModel;
    Communit_Rank_Model listModel;

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
                    listModel = new Gson().fromJson(resultString, Communit_Rank_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
                        baseModel.addAll(listModel.getData().getCommRanking());
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

//    DayPkDetail_Model Integral_Model;


}
