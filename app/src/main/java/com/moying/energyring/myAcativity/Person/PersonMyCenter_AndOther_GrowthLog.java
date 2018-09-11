package com.moying.energyring.myAcativity.Person;

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
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Energy.Energy_WebDetail;
import com.moying.energyring.myAdapter.PersonAndOther_GrowthLog_Adapter;
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
 *
 */
public class PersonMyCenter_AndOther_GrowthLog extends lazyLoadFragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String stringtype;
    private String UserID;
    private RecyclerView my_recycler;
    private XRecyclerView other_recycle;
    private String types;
    private String content;
    private View parentView;
    private int PageIndex;
    private int pageSize;
    private RecyclerView head_recycle;

    public static PersonMyCenter_AndOther_GrowthLog newInstance(String stringtype, String UserID) {
        PersonMyCenter_AndOther_GrowthLog newFragment = new PersonMyCenter_AndOther_GrowthLog();
        Bundle bundle = new Bundle();
        bundle.putString("stringtype", stringtype);
        bundle.putString("UserID", UserID);
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
        stringtype = args != null ? args.getString("stringtype") : defaultHello;
        UserID = args != null ? args.getString("UserID") : defaultHello;


        other_recycle = (XRecyclerView) parentView.findViewById(R.id.other_recycle);
        other_recycle.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁
//        other_recycle.setLoadingMoreEnabled(false);//底部不加载
        other_recycle.setPullRefreshEnabled(false);
        other_recycle.setLoadingListener(this);//添加事件
        StaticData.changeXRecycleHeadGif(other_recycle, R.drawable.gif_bird_icon, 750, 200);
        isPrepared = true;
        lazyLoad();


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
//        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=" + stringtype + "&FromUserID=" + UserID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    @Override
    public void onRefresh() {//刷新
//        lazyLoad();
    }

    @Override
    public void onLoadMore() {//加载更多
        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=" + stringtype + "&FromUserID=" + UserID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);

    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("GrowthLogFragment"); //统计页面，"GrowthLogFragment"为页面名称，可自定义
//        PageIndex = 1;
//        pageSize = 10;
//        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
//        my_recycle.refresState(2);
//        tableData(saveFile.BaseUrl+"/Study/Search");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("GrowthLogFragment");
    }



    PersonAndOther_GrowthLog_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new PersonAndOther_GrowthLog_Adapter(context, baseModel, listModel);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new PersonAndOther_GrowthLog_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                if (baseModel.get(position).getPostType() == 2)
                String content = baseModel.get(position).getPostContent();
                String postId = baseModel.get(position).getPostID() + "";
                String url = saveFile.BaseUrl + "Share/PostDetails?PostID=" + baseModel.get(position).getPostID();
                Intent intent = new Intent(context, Energy_WebDetail.class);
                intent.putExtra("content", content);
                intent.putExtra("postId", postId);
                intent.putExtra("url", url);
                intent.putExtra("imgUrl", baseModel.get(position).getFilePath());
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<EnergyList_Model.DataBean> baseModel;
    EnergyList_Model listModel;

    public void ListData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
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
                    listModel = new Gson().fromJson(resultString, EnergyList_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
                        if (PageIndex == 1) {
                            baseModel.addAll(listModel.getData());
                            other_recycle.refreshComplete();
                            initlist(getActivity());
                        } else {
                            other_recycle.loadMoreComplete();
                            mAdapter.addMoreData(listModel);
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
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }



    protected void setStatusBar() {
//        StatusBarUtil.setColor(getActivity(), Color.parseColor("#f3f3f3"));
//        StatusBarUtil.setTranslucent(this);
//        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(),0,seek_Btn);
    }


}
