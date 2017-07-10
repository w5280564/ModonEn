package com.moying.energyring.myAcativity.Energy;

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
import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.Model.Committed_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAcativity.Pk.Committ.Leran_AllPersonDetails;
import com.moying.energyring.myAdapter.CommHead_Adapter;
import com.moying.energyring.myAdapter.CommittedFragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.lazyLoadFragment;
import com.moying.energyring.xrecycle.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 2016/4/18.
 * 公众承诺
 */
public class CommittedFragment extends lazyLoadFragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String stringtype;
    private String workType;
    private RecyclerView my_recycler;
    private XRecyclerView other_recycle;
    private String types;
    private String content;
    private View parentView;
    private int PageIndex;
    private int pageSize;


    public static CommittedFragment newInstance(String stringtype, String workType) {
        CommittedFragment newFragment = new CommittedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("stringtype", stringtype);
        bundle.putString("workType", workType);
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
        workType = args != null ? args.getString("workType") : defaultHello;

        other_recycle = (XRecyclerView) parentView.findViewById(R.id.other_recycle);
//        other_recycle.setLoadingMoreEnabled(false);//底部不加载
        other_recycle.setLoadingListener(this);//添加事件
        other_recycle.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁
        StaticData.changeXRecycleHeadGif(other_recycle, R.drawable.gif_bird_icon, 750, 200);
        addRecycleHead(other_recycle, getActivity(), parentView);

        isPrepared = true;
        lazyLoad();

        return parentView;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("CommittedFragment"); //统计页面，"CommittedFragment"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("CommittedFragment");
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
        addHeadData();
    }


    RecyclerView head_recy;

    private void addRecycleHead(XRecyclerView recyview, Context context, View view) {
        View header = LayoutInflater.from(context).inflate(R.layout.commfragment_head, (ViewGroup) view.findViewById(android.R.id.content), false);
        head_recy = (RecyclerView) header.findViewById(R.id.head_recy);
        head_recy.setFocusable(false);//一定要在java文件中添加
        recyview.addHeaderView(header);

    }

    private void addHeadData() {
        int PageheadIndex = 1;
        int pageHeadSize = 10;
        noCommData(saveFile.BaseUrl + saveFile.Target_ListUrl + "?Type=1&PageIndex=" + PageheadIndex + "&PageSize=" + pageHeadSize);
    }


    @Override
    public void onRefresh() {//刷新
        addHeadData();
    }

    @Override
    public void onLoadMore() {//加载更多
        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=3&PageIndex=" + PageIndex + "&PageSize=" + pageSize);

    }


//    @Override
//    public void onResume() {
//        super.onResume();
////        addHeadData();
////        my_recycle.refresState(2);
////        tableData(saveFile.BaseUrl+"/Study/Search");
//    }

    public void inithead(final Context context, final AllPerson_Model allModel) {
        LinearLayoutManager headMangaer = new LinearLayoutManager(context);
        headMangaer.setOrientation(LinearLayoutManager.HORIZONTAL);
        head_recy.setLayoutManager(headMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        head_recy.setHasFixedSize(true);
        CommHead_Adapter headAdapter = new CommHead_Adapter(context, allModel);
        head_recy.setAdapter(headAdapter);
        headAdapter.setOnItemClickLitener(new CommHead_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, Leran_AllPersonDetails.class);
                intent.putExtra("TargetID", allModel.getData().get(position).getTargetID() + "");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    CommittedFragment_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new CommittedFragment_Adapter(context, baseModel);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new CommittedFragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(context, Leran_AllPersonDetails.class);
//                intent.putExtra("TargetID", baseModel.get(position).getPostID() + "");
//                startActivity(intent);
                String content = baseModel.get(position).getPostContent();
                String postId = baseModel.get(position).getPostID() + "";
                String url = saveFile.BaseUrl + "/Share/PostDetails?PostID=" + baseModel.get(position).getPostID();
                Intent intent = new Intent(context, Energy_WebDetail.class);
                intent.putExtra("content", content);
                intent.putExtra("postId", postId);
                intent.putExtra("url", url);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }

    AllPerson_Model allModel;

    public void noCommData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    allModel = new Gson().fromJson(resultString, AllPerson_Model.class);
                    if (allModel.isIsSuccess() && !allModel.getData().equals("[]")) {
                        inithead(getActivity(), allModel);

                        PageIndex = 1;
                        pageSize = 10;
                        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=3&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
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
                    Intent intent = new Intent(getActivity(), LoginRegister.class);
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


    private List<Committed_Model.DataBean> baseModel;
    Committed_Model listModel;

    public void ListData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
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
                    listModel = new Gson().fromJson(resultString, Committed_Model.class);
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


}
