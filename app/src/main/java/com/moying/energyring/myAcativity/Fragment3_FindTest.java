package com.moying.energyring.myAcativity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.Model.FindBanner_Model;
import com.moying.energyring.Model.RadioList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.myMediaplayerTest;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.Find.FindRadioListActivityTest;
import com.moying.energyring.myAcativity.Find.FindSeekActivity;
import com.moying.energyring.myAcativity.Find.Find_BannerDetail;
import com.moying.energyring.myAcativity.Find.Find_WebDetail;
import com.moying.energyring.myAcativity.Find.Person_Play;
import com.moying.energyring.myAcativity.Find.Person_StopVideo;
import com.moying.energyring.myAdapter.FindSeek_GrowthLogFragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.moying.energyring.StaticData.myMediaplayerTest.ifplay;

/**
 * Created by Admin on 2016/3/25.
 */
public class Fragment3_FindTest extends Fragment implements XRecyclerView.LoadingListener, ViewPager.OnPageChangeListener {
    private View parentView;
    private XRecyclerView find_recy;
    private int PageIndex;
    private int pageSize;
    private RelativeLayout papercontent;
    private ViewPager mPager;
    private LinearLayout bannerdot, bannerlin;
    private Button seek_Btn;
    private SimpleDraweeView radio_simple;
    private ImageView radio_play_img;
    private TextView radio_Name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (parentView == null) {
            parentView = inflater.inflate(R.layout.fragment3_find, null);
            setStatusBar();

            initTitle(parentView);
            initView(parentView);

//            initData();
        }
        ViewGroup parent = (ViewGroup) parentView.getParent();
        if (parent != null) {
            parent.removeView(parentView);
        }
        return parentView;
    }

    private void initView(View view) {
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 88);
        find_recy = (XRecyclerView) view.findViewById(R.id.find_recy);
        find_recy.setPadding(0,pad,0,0);
        find_recy.setLoadingListener(this);
        find_recy.getItemAnimator().setChangeDuration(0);
        initAddListHead(find_recy);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.getRule(RelativeLayout.CENTER_IN_PARENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        int padTop = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 18);
        params.setMargins(0,padTop,0,0);
        seek_Btn = (Button) view.findViewById(R.id.seek_Btn);
        seek_Btn.setLayoutParams(params);
        StaticData.ViewScale(seek_Btn, 646, 52);
//        seek_Btn.setOnClickListener(new seek_Btn());

//        StaticData.changeXRecycleHeadGif(find_recy,R.drawable.gif_sun_icon,500,200);
    }

    private void initTitle(View view) {
        View title_Include = (View) view.findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#232121"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setVisibility(View.GONE);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#ffffff"));
        cententtxt.setText("发现");
        Button right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setBackgroundResource(R.drawable.find_seek_gray);
        StaticData.ViewScale(title_Include, 0, 88);
        StaticData.ViewScale(right_Btn, 38, 38);
        viewTouchDelegate.expandViewTouchDelegate(right_Btn,100,100,100,100);
        right_Btn.setOnClickListener(new seek_Btn());
    }


    myMediaplayerTest myplayer;

    private void initAddListHead(XRecyclerView myView) {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.find_banner, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        papercontent = (RelativeLayout) header.findViewById(R.id.papercontent);
        mPager = (ViewPager) header.findViewById(R.id.viewpager);
        bannerdot = (LinearLayout) header.findViewById(R.id.bannerdot);
        bannerlin = (LinearLayout) header.findViewById(R.id.bannerlin);
        RelativeLayout radio_Rel = (RelativeLayout) header.findViewById(R.id.radio_Rel);
        View line_View = (View) header.findViewById(R.id.line_View);
        View radio_line_View = (View) header.findViewById(R.id.radio_line_View);
        radio_simple = (SimpleDraweeView) header.findViewById(R.id.radio_simple);
        radio_play_img = (ImageView) header.findViewById(R.id.radio_play_img);
        radio_Name = (TextView) header.findViewById(R.id.radio_Name);
        LinearLayout timeColockPlay_Lin = (LinearLayout) header.findViewById(R.id.timeColockPlay_Lin);
        LinearLayout timeColockPush_Lin = (LinearLayout) header.findViewById(R.id.timeColockPush_Lin);
        ImageView timeColock_playimg = (ImageView) header.findViewById(R.id.timeColock_playimg);
        ImageView timeColock_pushimg = (ImageView) header.findViewById(R.id.timeColock_pushimg);
        ImageView radio_moreimg = (ImageView) header.findViewById(R.id.radio_moreimg);
        StaticData.ViewScale(papercontent, 0, 388);
        StaticData.ViewScale(line_View, 4, 24);
        StaticData.ViewScale(radio_line_View, 4, 24);
        StaticData.ViewScale(radio_Rel, 710, 180);
        StaticData.ViewScale(radio_simple, 80, 80);
        StaticData.ViewScale(radio_play_img, 96, 96);
        StaticData.ViewScale(timeColock_playimg, 60, 60);
        StaticData.ViewScale(timeColock_pushimg, 60, 60);
        StaticData.ViewScale(radio_moreimg, 60, 60);
        myView.addHeaderView(header);
        radio_play_img.setOnClickListener(new radio_play_img());
        radio_moreimg.setOnClickListener(new radio_moreimg());
        timeColockPlay_Lin.setOnClickListener(new timeColockPlay_Lin());
        timeColockPush_Lin.setOnClickListener(new timeColockPush_Lin());
        myplayer = myMediaplayerTest.getInstance();
        initData();
    }

    //    @Override
//    public void onResume() {
//        super.onResume();
////        initData();
//    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Fragment3_FindTest"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Fragment3_FindTest");
    }


    private void initData() {
        addRadioVideo();
        addData();
    }

    private void addData() {
        BannerData(getActivity(), saveFile.BaseUrl + saveFile.banner_Url);
        PageIndex = 1;
        pageSize = 10;
        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=6&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

//    LocalRadioInfo radioInfo;

    private void addRadioVideo() {
        RadioData(saveFile.BaseUrl + saveFile.RadioList_Url + "?PageIndex=" + 1 + "&PageSize=" + 20);
    }

    @Override
    public void onRefresh() {
        addData();
    }

    @Override
    public void onLoadMore() {
        PageIndex += 1;
//        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=6&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    //打開定時鬧鐘
    private class timeColockPlay_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MobclickAgent.onEvent(getActivity(), "PlayClock");//统计页签
            Intent intent = new Intent(getActivity(), Person_Play.class);
            startActivity(intent);
        }
    }

    //打開倒計時時長
    private class timeColockPush_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MobclickAgent.onEvent(getActivity(), "StopTime");//统计页签
            Intent intent = new Intent(getActivity(), Person_StopVideo.class);
            startActivity(intent);

        }
    }


    private class seek_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), FindSeekActivity.class);
//            intent.putExtra("radioList",radioModel);
            startActivity(intent);
        }
    }

    private class radio_play_img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (myplayer != null && !ifplay) {
                radioUrl();
            } else {
                myplayer.pause();
                radioPauseset();
            }
        }
    }

    private void radioUrl() {
        int idex = radioModel.getData().size();
        String locaName = "";
        if (!StaticData.isSpace(radio_Name.getText().toString())) {
            locaName = radio_Name.getText().toString();
        }
        for (int i = 0; i < idex; i++) {
            RadioList_Model.DataBean oneData = radioModel.getData().get(i);
            String radioName = oneData.getRadioName();
            if (locaName.equals(radioName)) {
                oneData.setIsPlay(true);
                radio_play_img.setImageResource(R.drawable.radio_push);
                myplayer.playUrl(oneData.getRadioUrl(), radio_play_img);
                break;
            }
        }
    }

    private void radioPauseset() {

        int idex = radioModel.getData().size();
        String locaName = "";
        if (!StaticData.isSpace(radio_Name.getText().toString())) {
            locaName = radio_Name.getText().toString();
        }
        for (int i = 0; i < idex; i++) {
            RadioList_Model.DataBean oneData = radioModel.getData().get(i);
            String radioName = oneData.getRadioName();
            if (locaName.equals(radioName)) {
                oneData.setIsPlay(false);
                radio_play_img.setImageResource(R.drawable.radio_play);
                break;
            }
        }

    }

    public static int RadioRESULT = 301;

    private class radio_moreimg implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), FindRadioListActivity.class);
            Intent intent = new Intent(getActivity(), FindRadioListActivityTest.class);
            intent.putExtra("radioList", radioModel);
            intent.putExtra("nowRadioName", radio_Name.getText());
//            Intent intent = new Intent(getActivity(), com.moying.energyring.waylenBaseView.viewpagercards.MainActivity.class);
            startActivityForResult(intent, RadioRESULT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            radioModel = data.getParcelableExtra("radioList");
            String nowRadioName = data.getStringExtra("nowRadioName");

            radioResult(nowRadioName);
//            updataMyData(userModel);
        }
//        addRadioVideo();
    }

    private void radioResult(String locaName) {
        int idex = radioModel.getData().size();
        for (int i = 0; i < idex; i++) {
            RadioList_Model.DataBean oneData = radioModel.getData().get(i);
            String radioName = oneData.getRadioName();

            if (locaName.equals(radioName)) {
                if (oneData.getRadio_Icon() != null) {
                    Uri radioUri = Uri.parse(oneData.getRadio_Icon());
                    radio_simple.setImageURI(radioUri);
                }
                radio_Name.setText(oneData.getRadioName());

                if (oneData.getIsPlay()) {
                    oneData.setIsPlay(true);
                    radio_play_img.setImageResource(R.drawable.radio_push);
                    myplayer.playUrl(oneData.getRadioUrl(), radio_play_img);
                    break;
                }
            }

        }


    }

    FindSeek_GrowthLogFragment_Adapter mAdapter;

    public void initlist(final Context context) {
        final LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        find_recy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        find_recy.setHasFixedSize(true);
        mAdapter = new FindSeek_GrowthLogFragment_Adapter(context, baseModel, listModel);
        find_recy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new FindSeek_GrowthLogFragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                String imgpath = "";
                if (baseModel.get(position).getFilePath() != null) {
                    imgpath = String.valueOf(baseModel.get(position).getFilePath());
                }
                String content = baseModel.get(position).getPostTitle();
                String postId = baseModel.get(position).getPostID() + "";
                String url = saveFile.BaseUrl + "/Share/PostDetails?PostID=" + baseModel.get(position).getPostID();
                Intent intent = new Intent(context, Find_WebDetail.class);
                intent.putExtra("content", content);
                intent.putExtra("postId", postId);
                intent.putExtra("imgpath", imgpath);
                intent.putExtra("url", url);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        find_recy.addOnScrollListener(new XRecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (find_recy.isOnTop()) {
                    seek_Btn.setBackgroundResource(R.drawable.find_seekbg);
                } else {
                    seek_Btn.setBackgroundResource(R.drawable.find_seekbg_white);
                }
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
                            find_recy.refreshComplete();
                            initlist(getActivity());
                        } else {
                            find_recy.loadMoreComplete();
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

    //    private List<Map<String,Object>> bannerArr;
    List<FindBanner_Model.DataBean> listBannerModel;

    public void BannerData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    FindBanner_Model baseModel = new Gson().fromJson(resultString, FindBanner_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
                        if (listBannerModel != null) {
                            listBannerModel.clear();
                        }
                        listBannerModel = new ArrayList<FindBanner_Model.DataBean>();
                        List<FindBanner_Model.DataBean> listLinkModel = new ArrayList<>();

                        for (int i = 0; i < baseModel.getData().size(); i++) {
                            if (baseModel.getData().get(i).getViewArea() == 1) {
                                listBannerModel.add(baseModel.getData().get(i));
                            } else {
                                listLinkModel.add(baseModel.getData().get(i));
                            }
                        }

                        initViewPager(listBannerModel);
                        initDot();
                        taggletHandler.sleep(3000);

                        bannerList(bannerlin, listLinkModel);
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

    RadioList_Model radioModel;

    //电台
    public void RadioData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    radioModel = new Gson().fromJson(resultString, RadioList_Model.class);
                    if (radioModel.isIsSuccess() && !radioModel.getData().equals("[]")) {
                        RadioList_Model.DataBean oneData = radioModel.getData().get(0);

//                        AddOneLocaInfo(oneData,0);//保存数据

                        if (oneData.getRadio_Icon() != null) {
                            Uri radioUri = Uri.parse(oneData.getRadio_Icon());
                            radio_simple.setImageURI(radioUri);
                        }
                        radio_Name.setText(oneData.getRadioName());
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


    //banner推荐
    public void bannerList(LinearLayout myFlow, final List<FindBanner_Model.DataBean> myBean) {
        if (myFlow != null) {
            myFlow.removeAllViews();
        }
        List<SimpleDraweeView> mySimpleArr = new ArrayList<>();
        int size = myBean.size();
        for (int i = 0; i < size; i++) {
            RelativeLayout myview = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.bannerlist, null);
            RadioGroup.LayoutParams itemParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            StaticData.layoutParamsScale(itemParams, 220, 120);
            myview.setLayoutParams(itemParams);
            final SimpleDraweeView mySimple = (SimpleDraweeView) myview.findViewById(R.id.redio_sim);
//            mySimple.setScaleType(ImageView.ScaleType.FIT_XY);
            StaticData.ViewScale(mySimple, 190, 88);
            if (myBean.get(i).getFilePath() != null) {

//                StaticData.addPlace(mySimple, getActivity());
                Uri imgUri = Uri.parse(myBean.get(i).getFilePath());
                mySimple.setImageURI(imgUri);
            }
            TextView banner_Txt = (TextView) myview.findViewById(R.id.banner_Txt);
            banner_Txt.setText(myBean.get(i).getBannerName());
            mySimple.setTag(i);
            mySimpleArr.add(mySimple);
            myFlow.addView(myview);
            mySimple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
//                    String url = myBean.get(tag).getBannerContent();
                    MobclickAgent.onEvent(getActivity(), "bannerList");
                    String content = myBean.get(tag).getBannerName();
                    String url = myBean.get(tag).getBannerContent();
                    Intent intent = new Intent(getActivity(), Find_BannerDetail.class);
                    intent.putExtra("content", content);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            });
        }
    }


    private List<ImageView> views;
    private int currentItem = 1;

    private void initViewPager(List<FindBanner_Model.DataBean> listModel) {
        if (views != null) {
            views.clear();
        }
        views = new ArrayList<>();
        int length = listModel.size();
        for (int i = 0; i < length; i++) {
            SimpleDraweeView imgview = new SimpleDraweeView(getActivity());
            imgview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            FindBanner_Model.DataBean oneData = listModel.get(i);
            if (oneData.getFilePath() != null) {

//                StaticData.addPlace(imgview, getActivity());
                Uri imgurl = Uri.parse(oneData.getFilePath());
                imgview.setImageURI(imgurl);
            }
            views.add(imgview);
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
                        String content = listBannerModel.get(position).getBannerName();
                        String url = listBannerModel.get(position).getBannerContent();
                        Intent intent = new Intent(getActivity(), Find_BannerDetail.class);
                        intent.putExtra("url", url);
                        intent.putExtra("content", content);
                        startActivity(intent);
                    }
                });
            }
            return views.get(position);
        }
    }

    @Override//滑动时改变样式
    public void onPageSelected(int arg0) {
        int pageIndex = arg0;
        if (arg0 == 0) {
            arg0 = listBannerModel.size();
        } else if (arg0 == listBannerModel.size()) {
            pageIndex = 1;
        }
        if (arg0 != pageIndex) {
            currentItem = pageIndex;
        } else {
            currentItem = arg0;
        }
        mPager.setCurrentItem(currentItem, false);
        for (int i = 0; i < listBannerModel.size(); i++) {
            if (i == currentItem) {
                dotList.get(i).setBackgroundResource(R.drawable.cursor_select);
            } else {
                dotList.get(i).setBackgroundResource(R.drawable.cursor_icon);
            }
        }
    }

    //计时器，自动滚动
    TaggleHandler taggletHandler = new TaggleHandler();

    class TaggleHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            mPager.setCurrentItem(currentItem);
            taggletHandler.sleep(3000);
            if (currentItem >= views.size()) {
                currentItem = 0;
            } else {
                currentItem++;
            }
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            this.sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

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


}

