package com.moying.energyring.myAcativity.Energy;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.R;
import com.moying.energyring.myAdapter.AttenttionFragment_Adapter;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 2016/4/18.
 * 我的关注
 */
public class AttentionFragment extends Fragment implements XRecyclerView.LoadingListener{
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

    public static AttentionFragment newInstance(String stringtype, String workType) {
        AttentionFragment newFragment = new AttentionFragment();
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


        other_recycle = (XRecyclerView)parentView.findViewById(R.id.other_recycle);
//        other_recycle.setLoadingMoreEnabled(false);//底部不加载
        other_recycle.setLoadingListener(this);//添加事件
        PageIndex = 1;
        pageSize = 10;
//        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl+"?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
        return parentView;
    }


    @Override
    public void onRefresh() {//刷新
        PageIndex = 1;
        pageSize = 10;
//        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl+"?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    @Override
    public void onLoadMore() {//加载更多
        PageIndex = PageIndex + 1;
        pageSize = 10;
//        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl+"?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    @Override
    public void onResume() {
        super.onResume();
//        my_recycle.refresState(2);
//        tableData(saveFile.BaseUrl+"/Study/Search");
    }

    AttenttionFragment_Adapter mAdapter;
    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new AttenttionFragment_Adapter(context, baseModel, listModel);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new AttenttionFragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(context, Leran_AllPersonDetails.class);
//                intent.putExtra("TargetID", baseModel.get(position).getTargetID() + "");
//                startActivity(intent);
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
