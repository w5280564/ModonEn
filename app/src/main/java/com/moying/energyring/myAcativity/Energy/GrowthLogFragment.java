package com.moying.energyring.myAcativity.Energy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.Model.Sys_NoticeList_Model;
import com.moying.energyring.Model.Version_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.basePopup.popupMes;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAcativity.MainActivity;
import com.moying.energyring.myAdapter.GrowthLogFragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
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
 * 成长日志
 */
public class GrowthLogFragment extends lazyLoadFragment implements XRecyclerView.LoadingListener, ViewPager.OnPageChangeListener {
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

    public static GrowthLogFragment newInstance(String stringtype, String workType) {
        GrowthLogFragment newFragment = new GrowthLogFragment();
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
        other_recycle.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁
//        other_recycle.setLoadingMoreEnabled(false);//底部不加载
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
        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);


        versionNameData(getActivity(), saveFile.BaseUrl + saveFile.Version_Url);
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
        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("GrowthLogFragment"); //统计页面，"GrowthLogFragment"为页面名称，可自定义
//        PageIndex = 1;
//        pageSize = 10;
//        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
//        my_recycle.refresState(2);
//        tableData(saveFile.BaseUrl+"/Study/Search");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("GrowthLogFragment");
    }

    GrowthLogFragment_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new GrowthLogFragment_Adapter(context, baseModel, listModel);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new GrowthLogFragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                if (baseModel.get(position).getPostType() == 2)
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

    ViewPager mPager;
    LinearLayout bannerdot;

    public void showPopup() {
//        saveFile.saveShareData("isSee", "0.0", getActivity());
        final popupMes mesPopup = new popupMes(getActivity(), parentView, "更新", "1、更新曲线\n2、修改发现UI");
        RelativeLayout papercontent = (RelativeLayout) mesPopup.getContentView().findViewById(R.id.papercontent);
        mPager = (ViewPager) mesPopup.getContentView().findViewById(R.id.viewpager);
//        StaticData.ViewScale(mPager, 470, 600);
        bannerdot = (LinearLayout) mesPopup.getContentView().findViewById(R.id.bannerdot);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int padTop = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 1085);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0, padTop, 0, 0);
        bannerdot.setLayoutParams(params);
        initViewPager(mesPopup);
        initDot();
//        taggletHandler.sleep(3000);
    }


    private List<View> views;
    private int currentItem = 1;

    private void initViewPager(final BasePopupWindow mesPopup) {
        if (views != null) {
            views.clear();
        }
        views = new ArrayList<>();
//        int length = listModel.size();
        int length = showSys_Model.size();
        for (int i = 0; i < length; i++) {
            View popupView;
            final Sys_NoticeList_Model.DataBean oneData = showSys_Model.get(i);
            if (i == 0) {
                if (oneData.getNoticeType() == 1) {
                    MobclickAgent.onEvent(getActivity(), "updateView");//统计更新查看
                } else if (oneData.getNoticeType() == 2) {
                    MobclickAgent.onEvent(getActivity(), "featuresView");//统计功能查看
                }else if (oneData.getNoticeType() == 3) {
                    MobclickAgent.onEvent(getActivity(), "aciView"+ oneData.getSys_NoticeID());//统计活动查看
                }
            }
//            if (oneData.getNoticeType() == 1) {
            if (oneData.getNoticeType() == 1) {
                popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_three, null);
                RelativeLayout my_Rel = (RelativeLayout) popupView.findViewById(R.id.my_Rel);
//                StaticData.ViewScale(my_Rel, 470, 581);
                StaticData.ViewScale(my_Rel, 600, 800);
                ImageView popup_Img = (ImageView) popupView.findViewById(R.id.popup_Img);
                StaticData.ViewScale(popup_Img, 600, 330);
                TextView title_Txt = (TextView) popupView.findViewById(R.id.title_Txt);
                title_Txt.setTextColor(Color.parseColor("#ffffff"));
                TextView content_Txt = (TextView) popupView.findViewById(R.id.content_Txt);
                StaticData.ViewScale(content_Txt, 520, 500);
                Button sure_btn = (Button) popupView.findViewById(R.id.sure_btn);
                Button cancel_btn = (Button) popupView.findViewById(R.id.cancel_btn);
                StaticData.ViewScale(sure_btn, 190, 100);
                StaticData.ViewScale(cancel_btn, 190, 100);
                title_Txt.setText("版本更新");
                content_Txt.setText(oneData.getNotice_Content());
                sure_btn.setText("更新");
                cancel_btn.setText("取消");
                sure_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MobclickAgent.onEvent(getActivity(), "updateSure");//统计更新点击
                        Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.moying.energyring");
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(uri);
                        startActivity(intent);
                        mesPopup.dismiss();
                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MobclickAgent.onEvent(getActivity(), "updateCancel");//统计更新取消
                        mesPopup.dismiss();
                    }
                });

                views.add(popupView);

            } else {
                popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_aci, null);
                RelativeLayout my_Rel = (RelativeLayout) popupView.findViewById(R.id.my_Rel);
                StaticData.ViewScale(my_Rel, 600, 800);
                SimpleDraweeView aci_simple = (SimpleDraweeView) popupView.findViewById(R.id.aci_simple);
                Button detail_btn = (Button) popupView.findViewById(R.id.detail_btn);
                Button cancel_btn = (Button) popupView.findViewById(R.id.cancel_btn);
                StaticData.ViewScale(detail_btn, 190, 100);
                StaticData.ViewScale(cancel_btn, 190, 100);
                cancel_btn.setText("知道了");

                detail_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (oneData.getNoticeType() == 2) {
                            MobclickAgent.onEvent(getActivity(), "featuresSure");//统计功能点击
                            ((MainActivity) getActivity()).setTabChange(oneData.getTarget());

                        } else {
                            MobclickAgent.onEvent(getActivity(), "aciSure" + oneData.getSys_NoticeID());//统计活动点击加活动ID
                            if (oneData.getNoticeUrl() != null) {
                                String url = oneData.getNoticeUrl().toString();
                                Intent intent = new Intent(getActivity(), SysNotice_Web.class);
                                intent.putExtra("url", url);
                                startActivity(intent);
                            }
                        }
                        mesPopup.dismiss();
                    }
                });

                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (oneData.getNoticeType() == 2) {
                            MobclickAgent.onEvent(getActivity(), "featuresCancel");//统计功能取消点击

                        } else {
                            MobclickAgent.onEvent(getActivity(), "aciCancel" + oneData.getSys_NoticeID());//统计活动取消点击加活动ID
                        }
                        mesPopup.dismiss();
                    }
                });

                if (oneData.getFilePath() != null) {
                    Uri imgUri = Uri.parse(oneData.getFilePath().toString());
                    aci_simple.setImageURI(imgUri);
                }

                if (oneData.getNoticeType() == 2) {
                    detail_btn.setText("去看看");
                } else {
                    detail_btn.setText("去报名");
                }
                views.add(popupView);
            }


        }
        mPager.setAdapter(new myPagerAdapter());
        mPager.setCurrentItem(0);
        mPager.addOnPageChangeListener(this);
    }

    private List<ImageView> dotList;

    private void initDot() {
        bannerdot.removeAllViews();
        if (dotList != null) {
            dotList.clear();
        }
        dotList = new ArrayList<>();
        for (int i = 0; i < views.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            int padd = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 20);
            StaticData.layoutParamsScale(params, 48, 4);
            params.setMargins(0, 0, padd, 0);
            ImageView m = new ImageView(getActivity());
            if (i == 0) {// 默认索引0也就是a为选中状态
                m.setVisibility(View.VISIBLE);
                m.setBackgroundResource(R.drawable.cursor_select);
            } else if (i > 0 && i < views.size()) {
                m.setVisibility(View.VISIBLE);
                m.setBackgroundResource(R.drawable.cursor_icon);
            }
            m.setLayoutParams(params);
            bannerdot.addView(m);
            dotList.add(m);
        }
    }


    class myPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            ((ViewPager) container).addView(views.get(position));
            if (views.get(position) != null) {
                views.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
            return views.get(position);
        }
    }

    @Override//滑动时改变样式
    public void onPageSelected(int arg0) {
        final Sys_NoticeList_Model.DataBean oneData = showSys_Model.get(arg0);
        if (oneData.getNoticeType() == 1) {
            MobclickAgent.onEvent(getActivity(), "updateView");//统计更新查看
        } else if (oneData.getNoticeType() == 2) {
            MobclickAgent.onEvent(getActivity(), "featuresView");//统计功功能查看
        }else if (oneData.getNoticeType() == 3) {
            MobclickAgent.onEvent(getActivity(), "aciView"+ oneData.getSys_NoticeID());//统计活动查看
        }
        int pageIndex = arg0;
        if (arg0 == 0) {
            arg0 = showSys_Model.size();
        } else if (arg0 == showSys_Model.size()) {
            pageIndex = 1;
        }
        if (arg0 != pageIndex) {
            currentItem = pageIndex;
        } else {
            currentItem = arg0;
        }
        mPager.setCurrentItem(currentItem, false);
        for (int i = 0; i < showSys_Model.size(); i++) {
            if (i == currentItem) {
                dotList.get(i).setBackgroundResource(R.drawable.cursor_select);
            } else {
                dotList.get(i).setBackgroundResource(R.drawable.cursor_icon);
            }
        }
    }

    //计时器，自动滚动
//    TaggleHandler taggletHandler = new TaggleHandler();
//    class TaggleHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            mPager.setCurrentItem(currentItem);
//            taggletHandler.sleep(3000);
//            if (currentItem >= views.size()) {
//                currentItem = 0;
//            } else {
//                currentItem++;
//            }
//        }
//
//        public void sleep(long delayMillis) {
//            this.removeMessages(0);
//            this.sendMessageDelayed(obtainMessage(0), delayMillis);
//        }
//    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }


    protected void setStatusBar() {
//        StatusBarUtil.setColor(getActivity(), Color.parseColor("#f3f3f3"));
//        StatusBarUtil.setTranslucent(this);
//        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(),0,seek_Btn);
    }

    Version_Model ver_Model;

    public void versionNameData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    ver_Model = new Gson().fromJson(resultString, Version_Model.class);
                    if (ver_Model.isIsSuccess() && !ver_Model.getData().equals("[]")) {
//                        Toast.makeText(context, "汇报成功", Toast.LENGTH_SHORT).show();

                        saveFile.saveShareData("isSee", "true", context);
                        sys_NoticeData(getActivity(), saveFile.BaseUrl + saveFile.Sys_Notice_Url);
//                        if (ver_Model.equals(nowVer)){
//
//                        }
                    } else {
//                        Toast.makeText(context, "请汇报更多pk", Toast.LENGTH_SHORT).show();
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

    Sys_NoticeList_Model sys_Model;
    List<Sys_NoticeList_Model.DataBean> showSys_Model;
    List<String> aciId;
    List<String> driveId;

    public void sys_NoticeData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    sys_Model = new Gson().fromJson(resultString, Sys_NoticeList_Model.class);
                    if (sys_Model.isIsSuccess() && !sys_Model.getData().equals("[]")) {
                        List<Sys_NoticeList_Model.DataBean> oneData = sys_Model.getData();
                        if (showSys_Model != null) {
                            showSys_Model.clear();
                        }
                        showSys_Model = new ArrayList<>();

                        if (aciId != null) {
                            aciId.clear();
                        }
                        aciId = new ArrayList<>();

                        if (driveId != null) {
                            driveId.clear();
                        }
                        driveId = new ArrayList<>();

                        Double nowVer = Double.valueOf(StaticData.getversionName(context));//当前版本
                        String driveStr = ver_Model.getData().getAndroid();//服务器版本
                        Double VerD;//本地版本
                        if (saveFile.getShareData("VersionID", getActivity()).equals("false")) {
                            VerD = 0.0;
                        } else {
                            VerD = Double.valueOf(saveFile.getShareData("VersionID", getActivity()));
                        }
                        driveId = getdriveId(oneData, 3);//服务器活动ID

                        if (saveFile.getShareData("VersionID", getActivity()).equals("false") && nowVer < Double.valueOf(driveStr)) {//第一次打开 本机版本小于服务器版本
                            showPopType(showSys_Model, 1, oneData, true);
                            showPopup();//打开弹框
                        } else if (!saveFile.getShareData("VersionID", getActivity()).equals(driveStr) && VerD != 0) {//本地存储版本与服务器不同 有新版本更新
                            if (VerD < Double.valueOf(driveStr)) { //本地版小于服务器版 ，打开提示更新
                                showPopType(showSys_Model, 1, oneData);

                                if (!saveFile.getUserList(context, "aciId").isEmpty()) {
                                    aciId = saveFile.getUserList(context, "aciId");//本地活动ID
                                    List<String> diffId = getDiffrent(aciId, driveId);//不同ID
                                    if (!diffId.isEmpty()) {
                                        showPopAci(showSys_Model, 3, oneData, diffId);//要显示窗口
                                    }
                                }
                                showPopup();//打开弹框
                            }

                        } else if (saveFile.getShareData("isSeeFeatures", getActivity()).equals("false") && nowVer.equals(Double.valueOf(driveStr))) { //是否看过功能 并且本机版本等于服务器版本
                            saveFile.saveShareData("isSeeFeatures", "true", context);
                            showPopType(showSys_Model, 2, oneData, true);
                            showPopup();//打开弹框


                        } else {

                            if (!saveFile.getUserList(context, "aciId").isEmpty()) {
//                                showPopType(showSys_Model, 3, oneData);
                                aciId = saveFile.getUserList(context, "aciId");//本地活动ID
                                List<String> diffId = getDiffrent(aciId, driveId);//不同ID
                                if (!diffId.isEmpty()) {
                                    showPopAci(showSys_Model, 3, oneData, diffId);//要显示窗口
                                    showPopup();
                                }
                            }

                        }


//                        showPopup();
                        saveFile.saveUserList(context, driveId, "aciId");//存服务器活动ID
                        saveFile.saveShareData("VersionID", ver_Model.getData().getAndroid(), context);//存当前服务器版本

                    } else {
//                        Toast.makeText(context, "请汇报更多pk", Toast.LENGTH_SHORT).show();
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

    //第一次打开添加显示活动
    private void showPopType(List<Sys_NoticeList_Model.DataBean> showSys_Model, int type, List<Sys_NoticeList_Model.DataBean> oneData, boolean isAciShow) {
        for (int i = 0; i < oneData.size(); i++) {
//            if (oneData.get(i).getNoticeType() == type || oneData.get(i).getNoticeType() == 3) {//1是更新  3是活动
            if (oneData.get(i).getNoticeType() == type || oneData.get(i).getNoticeType() == 3) {//1是更新  3是活动
                showSys_Model.add(oneData.get(i));
//                oneData.get(i).setDisPlay(true);
            }
//                                    break;
        }
    }

    private void showPopType(List<Sys_NoticeList_Model.DataBean> showSys_Model, int type, List<Sys_NoticeList_Model.DataBean> oneData) {
        for (int i = 0; i < oneData.size(); i++) {
//            if (oneData.get(i).getNoticeType() == type || oneData.get(i).getNoticeType() == 3) {//1是更新  3是活动
            if (oneData.get(i).getNoticeType() == type) {//1是更新  3是活动
                showSys_Model.add(oneData.get(i));
//                oneData.get(i).setDisPlay(true);
            }
//                                    break;
        }
    }

    //只显示不同的新活动
    private void showPopAci(List<Sys_NoticeList_Model.DataBean> showSys_Model, int type, List<Sys_NoticeList_Model.DataBean> oneData, List<String> aciIdArr) {
        for (int i = 0; i < oneData.size(); i++) {
            for (int k = 0; k < aciIdArr.size(); k++) {
                if (oneData.get(i).getNoticeType() == type && (oneData.get(i).getSys_NoticeID() + "").equals(aciIdArr.get(k))) {
                    showSys_Model.add(oneData.get(i));
                }
            }
        }
    }

    //服务器活动ID
    private List getdriveId(List<Sys_NoticeList_Model.DataBean> oneData, int type) {
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < oneData.size(); i++) {
            if (oneData.get(i).getNoticeType() == type) {
                list2.add(oneData.get(i).getSys_NoticeID() + "");
            }
        }
        return list2;
    }

    /**
     * 获取两个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getDiffrent(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<>();
        for (String str : list2) {
            if (!list1.contains(str)) {
                diff.add(str);
            }
        }
        return diff;
    }


}
