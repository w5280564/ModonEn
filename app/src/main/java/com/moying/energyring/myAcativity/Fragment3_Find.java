package com.moying.energyring.myAcativity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.moying.energyring.StaticData.myMediaplayer;
import com.moying.energyring.myAcativity.Energy.Energy_WebDetail;
import com.moying.energyring.myAcativity.Find.FindRadioListActivityTest;
import com.moying.energyring.myAcativity.Find.FindSeekActivity;
import com.moying.energyring.myAcativity.Find.radioInfo.LocalRadioInfo;
import com.moying.energyring.myAcativity.Find.radioInfo.radio_Model;
import com.moying.energyring.myAdapter.GrowthLogFragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Admin on 2016/3/25.
 */
public class Fragment3_Find extends Fragment implements XRecyclerView.LoadingListener, ViewPager.OnPageChangeListener {
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
        find_recy = (XRecyclerView) view.findViewById(R.id.find_recy);
        find_recy.setLoadingListener(this);
        initAddListHead(find_recy);
        seek_Btn = (Button) view.findViewById(R.id.seek_Btn);
        StaticData.ViewScale(seek_Btn, 646, 52);
        seek_Btn.setOnClickListener(new seek_Btn());
//        StaticData.changeXRecycleHeadGif(find_recy,R.drawable.gif_sun_icon,500,200);
    }

    myMediaplayer myplayer;

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
        myplayer = myMediaplayer.getInstance();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
//        initData();
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

    LocalRadioInfo radioInfo;

    private void addRadioVideo() {
        radioInfo = new LocalRadioInfo(getActivity());

//        if (!radioInfo.getUserInfo().getRadioName().equals("false")) {
//
//            if (radioInfo.getUserInfo().getRadioisPlay() == 2) {//正在播放
//                radio_play_img.setImageResource(R.drawable.radio_push);
//                myplayer.playUrl(radioInfo.getUserInfo().getRadioUrl(),radio_play_img);
//            } else {
//                radio_play_img.setImageResource(R.drawable.radio_play);
//            }
//            Uri radioUri = Uri.parse(radioInfo.getUserInfo().getRadioImgpath());
//            radio_simple.setImageURI(radioUri);
//            radio_Name.setText(radioInfo.getUserInfo().getRadioName());
//        } else {
        RadioData(saveFile.BaseUrl + saveFile.RadioList_Url + "?PageIndex=" + 1 + "&PageSize=" + 20);
//        }

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

    private class seek_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), FindSeekActivity.class);
//            intent.putExtra("radioList",radioModel);
            startActivity(intent);
        }
    }


    private static final int PLAY = 101;//播放
    private static final int PUSH = 102;//暂停
    private static final int STOP = 103;//停止
    private static final int RELEASE = 104;//释放

    //    private boolean isPause = false;//是否暂停
    private class radio_play_img implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (myplayer.mediaPlayer == null) {
//                radio_play_img.setImageResource(R.drawable.radio_push);
//                myplayer.playUrl(radioInfo.getUserInfo().getRadioUrl(),radio_play_img);


//                radioModel.getData().get(0).setIsPlay(true);


//                int idex = radioModel.getData().size();
//                String locaName = "";
//                if (!StaticData.isSpace(radio_Name.getText().toString())) {
//                    locaName = radio_Name.getText().toString();
//                }
//                for (int i = 0; i < idex; i++) {
//                    RadioList_Model.DataBean oneData = radioModel.getData().get(i);
//                    String radioName = oneData.getRadioName();
//                    if (locaName.equals(radioName)) {
//                        myplayer.playUrl(oneData.getRadioUrl(), radio_play_img);
//                        break;
//                    }
//                }

                radioUrl();

            } else if (myplayer.mediaPlayer != null && myplayer.mediaPlayer.isPlaying()) { //正在播放

                myplayer.pause();
                radioPauseset();

            } else {
//                radio_play_img.setImageResource(R.drawable.radio_push);
//                myplayer.playUrl(radioInfo.getUserInfo().getRadioUrl(), radio_play_img);
                radioUrl();
            }

//            if (myplayer.mediaPlayer != null && myplayer.mediaPlayer.isPlaying()) { //正在播放
//                AddRadioState(0);
//                radio_play_img.setImageResource(R.drawable.radio_play);
//                myplayer.pause();
//            } else {
//                AddRadioState(2);
//                radio_play_img.setImageResource(R.drawable.radio_push);
////                RadioList_Model.DataBean oneData = radioModel.getData().get(0);
//                myplayer.playUrl(radioInfo.getUserInfo().getRadioUrl(),radio_play_img);
////                AddOneLocaInfo(oneData);
//            }
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

//        radio_play_img.setBackgroundResource(R.drawable.radio_play);
//        RadioList_Model.DataBean oneData = radioModel.getData().get(pos);
//        oneData.setIsPlay(false);
    }


    //存储一条电台数据
    public void AddOneLocaInfo(RadioList_Model.DataBean onedata, int state) {
        radio_Model model = new radio_Model();
        model.setRadioName(onedata.getRadioName());
        model.setRadioImgpath(onedata.getRadio_Icon());
        model.setRadioUrl(onedata.getRadioUrl());
        model.setRadioisPlay(state);
        radioInfo.setUserInfo(model);
    }

    private void AddRadioState(int state) {
        radio_Model model = new radio_Model();
        model.setRadioName(radioInfo.getUserInfo().getRadioName());
        model.setRadioImgpath(radioInfo.getUserInfo().getRadioImgpath());
        model.setRadioUrl(radioInfo.getUserInfo().getRadioUrl());
        model.setRadioisPlay(state);
        radioInfo.setUserInfo(model);
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

//                radio_play_img.setImageResource(R.drawable.radio_play);
                if (oneData.getIsPlay()) {
                    oneData.setIsPlay(true);
                    radio_play_img.setImageResource(R.drawable.radio_push);
                    myplayer.playUrl(oneData.getRadioUrl(), radio_play_img);
                    break;
                }
            }

        }


//        int idex = radioModel.getData().size();
//        for (int i = 0; i < idex; i++) {
//            RadioList_Model.DataBean oneData = radioModel.getData().get(i);
//            if (oneData.getIsPlay()) {
////                startUpTimer();//启动计时器
////                oneData.setIsPlay(true);
//                if (oneData.getRadio_Icon() != null) {
//                    Uri radioUri = Uri.parse(oneData.getRadio_Icon());
//                    radio_simple.setImageURI(radioUri);
//                }
//                radio_Name.setText(oneData.getRadioName());
//                radio_play_img.setBackgroundResource(R.drawable.radio_list_push);
//                myplayer.playUrl(oneData.getRadioUrl(), radio_play_img);
//                break;
//            }
//        }
    }
//    private void radioResult() {
//        int idex = radioModel.getData().size();
//        String locaName = "";
//        if (!StaticData.isSpace(radio_Name.getText().toString())) {
//            locaName = radio_Name.getText().toString();
//        }
//        for (int i = 0; i < idex; i++) {
//            RadioList_Model.DataBean oneData = radioModel.getData().get(i);
//            String radioName = oneData.getRadioName();
//            if (locaName.equals(radioName)) {
//                oneData.setIsPlay(true);
//                radio_play_img.setImageResource(R.drawable.radio_push);
//                myplayer.playUrl(oneData.getRadioUrl(), radio_play_img);
//                break;
//            }
//        }
//    }


    GrowthLogFragment_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        find_recy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        find_recy.setHasFixedSize(true);
        mAdapter = new GrowthLogFragment_Adapter(context, baseModel, listModel);
        find_recy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new com.moying.energyring.myAdapter.GrowthLogFragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, Energy_WebDetail.class);
//                intent.putExtra("TargetID", baseModel.get(position).getTargetID() + "");
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
//                    int tag = (int) v.getTag();
//                    String url = myBean.get(tag).getBannerContent();
//                    Intent i = new Intent(getActivity(), bannerList_WebDetail.class);
//                    i.putExtra("url", url);
//                    startActivity(i);
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
//                        if (bannerArr.get(position).get("studyType").equals("1")){
//                            int id = Integer.parseInt(bannerArr.get(position).get("id").toString());
//                            int userId = LoginSession.getSession().getUserInfo().getUse_id();
//                            String title = bannerArr.get(position).get("title").toString();
//                            String summary = bannerArr.get(position).get("summary").toString();
//
//                            String url = bannerArr.get(position).get("summary").toString();
//                            Log.e("url",url);
//
//                            Intent intent = new Intent(getActivity(),learn_WebDetail.class);
//                            intent.putExtra("url",url);
//                            intent.putExtra("articleId", id);
//                            intent.putExtra("title", title);
//                            intent.putExtra("summary", summary);
//                            intent.putExtra("userId",userId);
//
////                                String url = saveFile.BaseUrl +"/html/Study/html/StudyDetail.aspx?id="+ bannerArr.get(position-1).get("id")+"&userId="+ LoginSession.getSession().getUserInfo().getUse_id();
////                                Intent intent = new Intent(getActivity(),learn_WebDetail.class);
////                                intent.putExtra("url",url);
//                            startActivity(intent);
//                        }else if(bannerArr.get(position).get("studyType").equals("2")){
//                            List<String> userArr = saveFile.getUserList(getActivity(), "listselect");
//                            String mykeyName = bannerArr.get(position).get("summary").toString();
//                            if (isexistence(userArr,mykeyName)){
//                                ((MainActivity)getActivity()).getfragment().setpage(userArr,mykeyName);//跳到已有fragment位置
//                            }else {
//                                ((MainActivity)getActivity()).getfragment().addpage(mykeyName);//添加一个新fragment
//                            }
//                        }
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


}

