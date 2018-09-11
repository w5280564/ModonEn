package com.moying.energyring.myAcativity.Person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Person_Photo_Model;
import com.moying.energyring.R;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.PersonAndOther_Photo_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.lazyLoadFragment;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.moying.energyring.network.saveFile.getShareData;


/**
 */
public class PersonMyCenter_AndOther_Photo extends lazyLoadFragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String Type;
    private String UserID;
    private RecyclerView my_recycler;
    private XRecyclerView other_recycle;
    private View parentView;
    private int PageIndex;
    private int pageSize;


    public static PersonMyCenter_AndOther_Photo newInstance(String Type, String UserID) {
        PersonMyCenter_AndOther_Photo newFragment = new PersonMyCenter_AndOther_Photo();
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
//        other_recycle.setLoadingMoreEnabled(false);//底部不加载
        other_recycle.setPullRefreshEnabled(false);
        other_recycle.setLoadingListener(this);//添加事件
//        StaticData.changeXRecycleHeadGif(other_recycle,R.drawable.gif_bird_icon,750,200);
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
        PageIndex = 1;
        pageSize = 15;
        ListData(getActivity(),saveFile.BaseUrl + saveFile.PhotoList_Url + "?UserID=" + UserID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    @Override
    public void onRefresh() {//刷新
//        addData();
    }

    @Override
    public void onLoadMore() {//加载更多
        PageIndex = PageIndex + 1;
        pageSize = 15;
        ListData(getActivity(),saveFile.BaseUrl + saveFile.PhotoList_Url + "?UserID=" + UserID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    @Override
    public void onResume() {
        super.onResume();
//        addHeadData();
//        my_recycle.refresState(2);
//        tableData(saveFile.BaseUrl+"/Study/Search");
    }


    PersonAndOther_Photo_Adapter mAdapter;

    public void initlist(final Context context) {
        GridLayoutManager mMangaer = new GridLayoutManager(context, 3);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new PersonAndOther_Photo_Adapter(context, baseModel);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new PersonAndOther_Photo_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                int tag = position;
                List<String> myArr = new ArrayList<>();
//                    myArr.addAll(myBean.get(myposition).getArtPic());
                for (int i = 0; i < baseModel.size(); i++) {
                    myArr.add(baseModel.get(i).getFilePath());
                }
                Intent i = new Intent(context, PersonMyCenter_AndOther_Photo_Detail.class);
//                    i.putExtra("imgArr", (Parcelable) myBean);
                i.putExtra("imgArr", (Serializable) myArr);
                i.putExtra("tag", tag);
                i.putParcelableArrayListExtra("Photo_Model", (ArrayList<? extends Parcelable>) baseModel);
                context.startActivity(i);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<Person_Photo_Model.DataBean> baseModel;
    Person_Photo_Model listModel;

    public void ListData(final Context context,String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", getShareData("JSESSIONID", context));
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
                    listModel = new Gson().fromJson(resultString, Person_Photo_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
                        baseModel.addAll(listModel.getData());
                        if (PageIndex == 1) {
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
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, MainLogin.class);
                    context.startActivity(intent);
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
