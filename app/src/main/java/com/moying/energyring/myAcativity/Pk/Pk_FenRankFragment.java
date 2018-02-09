package com.moying.energyring.myAcativity.Pk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Pk_FenRankList_Model;
import com.moying.energyring.R;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAdapter.Pk_FenRank_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.lazyLoadFragment;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 2016/4/18.
 * 成长日志
 */
public class Pk_FenRankFragment extends lazyLoadFragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String Type;
    private String FromUserID;
    private RecyclerView my_recycler;
    private XRecyclerView other_recycle;
    private String types;
    private String content;
    private View parentView;
    private int PageIndex;
    private int pageSize;

    public static Pk_FenRankFragment newInstance(String Type, String FromUserID) {
        Pk_FenRankFragment newFragment = new Pk_FenRankFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type", Type);
        bundle.putString("FromUserID", FromUserID);
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
        parentView = inflater.inflate(R.layout.fenrankfragment, container, false);
        Bundle args = getArguments();
        Type = args != null ? args.getString("Type") : defaultHello;
        FromUserID = args != null ? args.getString("FromUserID") : defaultHello;

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
        if (Type.equals("0")) {
            ListData(getActivity(), saveFile.BaseUrl + saveFile.PersonRank_List_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
        }else{
            ListData(getActivity(), saveFile.BaseUrl + saveFile.My_FriendRank_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
        }
    }


    @Override
    public void onRefresh() {//刷新
        lazyLoad();

//        PageIndex = 1;
//        pageSize = 10;
//        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    @Override
    public void onLoadMore() {//加载更多
        PageIndex = PageIndex + 1;
        pageSize = 10;
        if (Type.equals("0")) {
            ListData(getActivity(), saveFile.BaseUrl + saveFile.PersonRank_List_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
        }else{
            ListData(getActivity(), saveFile.BaseUrl + saveFile.My_FriendRank_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
    }

    Pk_FenRank_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new Pk_FenRank_Adapter(context, baseModel, pionts_Model);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Pk_FenRank_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(context, Energy_WebDetail.class);
//                intent.putExtra("TargetID", baseModel.get(position).getTargetID() + "");
//                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<Pk_FenRankList_Model.DataBean> baseModel;
    Pk_FenRankList_Model pionts_Model;

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
                    pionts_Model = new Gson().fromJson(resultString, Pk_FenRankList_Model.class);
                    if (pionts_Model.isIsSuccess() && !pionts_Model.getData().equals("[]")) {
                        baseModel.addAll(pionts_Model.getData());
                        if (PageIndex == 1) {
//                            TextsColor(28, 72, "第" + pionts_Model.getData().getUser_Sort().getRanking() + "名", rank_Txt);
//                            rankCountTextsColor( 72, pionts_Model.getData().getUser_Sort().getIntegral()+"", rankCount_Txt);
                            other_recycle.refreshComplete();
                            initlist(context);
                        } else {
                            other_recycle.loadMoreComplete();
                            mAdapter.addMoreData(baseModel);
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
