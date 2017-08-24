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
import com.moying.energyring.Model.DayPkProject_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAdapter.Person_PkHistoryFragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.lazyLoadFragment;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * Created by Admin on 2016/4/18.
 * PK记录
 */
public class PersonPkHistoryFragment extends lazyLoadFragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String Type;
    private String UserID;
    private RecyclerView my_recycler;
    private XRecyclerView other_recycle;
    private View parentView;


    public static PersonPkHistoryFragment newInstance(String Type, String UserID) {
        PersonPkHistoryFragment newFragment = new PersonPkHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type", Type);
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
        Type = args != null ? args.getString("Type") : defaultHello;
        UserID = args != null ? args.getString("UserID") : defaultHello;

        other_recycle = (XRecyclerView) parentView.findViewById(R.id.other_recycle);
        other_recycle.setLoadingMoreEnabled(false);//底部不加载
        other_recycle.setPullRefreshEnabled(false);
//        other_recycle.setLoadingListener(this);//添加事件
        StaticData.changeXRecycleHeadGif(other_recycle, R.drawable.gif_bird_icon, 750, 200);
//        addRecycleHead(other_recycle, getActivity(), parentView);

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
        addData();
    }

    private void addData() {
        ListData(getActivity(),saveFile.BaseUrl + saveFile.PkHistory_Url + "?UserID=" + UserID);
    }


    @Override
    public void onRefresh() {//刷新
        addData();
    }

    @Override
    public void onLoadMore() {//加载更多
//        PageIndex = PageIndex + 1;
//        pageSize = 10;
//        ListData(saveFile.BaseUrl + saveFile.PkHistory_Url + "?Type="+ Type +"&FromUserID="+ FromUserID +"&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    @Override
    public void onResume() {
        super.onResume();
//        addHeadData();
//        my_recycle.refresState(2);
//        tableData(saveFile.BaseUrl+"/Study/Search");
    }


    Person_PkHistoryFragment_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new Person_PkHistoryFragment_Adapter(context, baseModel);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Person_PkHistoryFragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, PersonPkHistoryLineView.class);
                intent.putExtra("ProjectID", baseModel.getData().get(position).getProjectID() + "");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    DayPkProject_Model baseModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    baseModel = new Gson().fromJson(resultString, DayPkProject_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
                        initlist(getActivity());
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


}
