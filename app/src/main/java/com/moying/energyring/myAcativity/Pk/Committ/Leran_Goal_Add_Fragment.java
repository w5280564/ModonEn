package com.moying.energyring.myAcativity.Pk.Committ;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Goal_Model;
import com.moying.energyring.R;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.Leran_Goal_Add_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.lazyLoadFragment;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * Created by Admin on 2016/4/18.
 * 成长日志
 */
public class Leran_Goal_Add_Fragment extends lazyLoadFragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String Type;
    private String ProjectTypeID;
    private RecyclerView All_XRecy;
    private View parentView;
    private int PageIndex;
    private int pageSize;

    public static Leran_Goal_Add_Fragment newInstance(String Type, String ProjectTypeID) {
        Leran_Goal_Add_Fragment newFragment = new Leran_Goal_Add_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type", Type);
        bundle.putString("ProjectTypeID", ProjectTypeID);
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
        isPrepared = true;
        lazyLoad();
    }


    public void initlist(final Context context) {
        GridLayoutManager mMangaer = new GridLayoutManager(context, 3);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        All_XRecy.setHasFixedSize(true);
        final Leran_Goal_Add_Adapter mAdapter = new Leran_Goal_Add_Adapter(context, baseModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Leran_Goal_Add_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(final View view, final int position) {
                ScaleAnimation scal = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scal.setDuration(100);
//                scal.setFillAfter(false);//动画执行完后是否停留在执行完的状
                Leran_Goal_Add_Adapter.MyViewHolder viewHolder = (Leran_Goal_Add_Adapter.MyViewHolder) view.getTag();
                viewHolder.my_Lin.startAnimation(scal);
                scal.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getActivity(), Leran_TimeChoice.class);
                        intent.putExtra("unit", baseModel.getData().get(position).getProjectUnit());
                        intent.putExtra("titleName", baseModel.getData().get(position).getProjectName());
                        intent.putExtra("ProjectID", baseModel.getData().get(position).getProjectID()+"");
                        startActivity(intent);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });

            }


            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
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
