package com.moying.energyring.myAcativity.Person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.BadgeList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAdapter.Badge_List_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.lazyLoadFragment;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * Created by Admin on 2016/4/18.
 * 每日pk
 */
public class Person_Badge_ListFragment extends lazyLoadFragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String stringtype;
    private XRecyclerView other_recycle;
    private int PageIndex;
    private int pageSize;
    private TextView my_badgeCount;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Person_Badge) {
            Person_Badge pkActivity = (Person_Badge) context;
            my_badgeCount = (TextView) pkActivity.findViewById(R.id.my_badgeCount);
        }
    }

    public static Person_Badge_ListFragment newInstance(String stringtype) {
        Person_Badge_ListFragment newFragment = new Person_Badge_ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("stringtype", stringtype);
        newFragment.setArguments(bundle);
        //bundle可以在每个标签里传送数据
        return newFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private View parentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.personlistbadge_fragment, container, false);
        Bundle args = getArguments();//放到加载数据那页
        stringtype = args != null ? args.getString("stringtype") : defaultHello;

        other_recycle = (XRecyclerView) parentView.findViewById(R.id.other_recycle);
        other_recycle.setLoadingListener(this);//添加事件
        other_recycle.setPullRefreshEnabled(false);
        other_recycle.setLoadingMoreEnabled(false);
        other_recycle.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁
        return parentView;
    }


    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
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
        ListData(getActivity(), saveFile.BaseUrl + saveFile.badge_List_Url + "?BadgeType=" + stringtype);
//        personData(getActivity(), saveFile.BaseUrl + saveFile.PersonData_Url + "?ProjectID=" + ProjectID);
    }

    @Override
    public void onRefresh() {//刷新
//        PageIndex = 1;
//        pageSize = 10;
//        ListData(getActivity(), saveFile.BaseUrl + saveFile.ReportRankUrl + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
//        ListData(getActivity(), saveFile.BaseUrl + saveFile.ReportRankUrl + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
//        lazyLoad();
    }

    @Override
    public void onLoadMore() {//加载更多
        PageIndex = PageIndex + 1;
        pageSize = 10;
//        ListData(getActivity(), saveFile.BaseUrl + saveFile.ReportRankUrl + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    public void initlist(final Context context) {
        GridLayoutManager mMangaer = new GridLayoutManager(context, 3);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        Badge_List_Adapter mAdapter = new Badge_List_Adapter(context, baseModel);
//        mAdapter = new DayPkFragment_Adapter(context, baseModel, listModel);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Badge_List_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

                String myHaveName = baseModel.getData().get_Badge().get(position).getBadgeName();
                String HaveNum = "-已有" + baseModel.getData().get_Badge().get(position).getHaveNum() + "人获得-";
                String url = "";
                if (baseModel.getData().get_Badge().get(position).isIs_Have()) {
                    url = baseModel.getData().get_Badge().get(position).getFilePath();
                } else {
                    url = baseModel.getData().get_Badge().get(position).getFilePath_Gray();
                }

                new badge_Popup(context, other_recycle, HaveNum, myHaveName, url);

//                Intent intent = new Intent(getActivity(),Person_BadgeHas.class);
//                intent.putExtra("url",url);
//                intent.putExtra("HaveNum",HaveNum);
//                intent.putExtra("myHaveName",myHaveName);
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.zoomin,0);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    BadgeList_Model baseModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    baseModel = new Gson().fromJson(resultString, BadgeList_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
                        my_badgeCount.setText("获得：" + baseModel.getData().getMyBadgeNum() + "个");
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

    public class badge_Popup extends BasePopupWindow {
        public badge_Popup(final Context mContext, View parent, String HaveNum, String myHaveName, String url) {
            super(mContext);
//            setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
//            setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
            setTouchable(true);
            View contentView = View.inflate(mContext, R.layout.popup_badge, null);
            setContentView(contentView);
            showAtLocation(contentView, Gravity.CENTER, 0, 0);

            RelativeLayout badgePopup_Rel = (RelativeLayout) contentView.findViewById(R.id.badgePopup_Rel);
            SimpleDraweeView badge_Img = (SimpleDraweeView) contentView.findViewById(R.id.badge_Img);
            ImageView badge_chaImg = (ImageView) contentView.findViewById(R.id.badge_chaImg);
            TextView badge_Txt = (TextView) contentView.findViewById(R.id.badge_Txt);
            TextView have_Txt = (TextView) contentView.findViewById(R.id.have_Txt);
            StaticData.ViewScale(badgePopup_Rel, 510, 664);
            StaticData.ViewScale(badge_Img, 360, 420);
            StaticData.ViewScale(badge_chaImg, 35, 35);
            viewTouchDelegate.expandViewTouchDelegate(badge_chaImg, 100, 100, 100, 100);
            badge_Img.setImageURI(url);
            badge_Txt.setText(myHaveName);
            have_Txt.setText(HaveNum);
            badge_chaImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
    }


}
