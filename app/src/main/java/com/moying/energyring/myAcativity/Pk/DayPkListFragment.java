package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.moying.energyring.Model.AddPhoto_Model;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.DayPkList_Model;
import com.moying.energyring.Model.PersonData_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.ImagePickerActivity;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAdapter.DayPkFragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.lazyLoadFragment;
import com.moying.energyring.xrecycle.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;


/**
 * Created by Admin on 2016/4/18.
 * 每日pk
 */
public class DayPkListFragment extends lazyLoadFragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String stringtype;
    private String ProjectID;
    private RecyclerView my_recycler;
    private XRecyclerView other_recycle;
    private String types;
    private String content;
    private int PageIndex;
    private int pageSize;
    private SimpleDraweeView my_simple, zhan_simple;
    private ImageView zan_img;
    private TextView zan_Txt, zhanTxt, myhui_count_Txt;
    private SimpleDraweeView pkbg_simple;
    private LinearLayout zhan_Lin, zan_Lin;
    private Button daypk_pen;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Pk_Daypk) {
            Pk_Daypk pkActivity = (Pk_Daypk) context;
            pkbg_simple = (SimpleDraweeView) pkActivity.findViewById(R.id.pkbg_simple);
        }
    }

    public static DayPkListFragment newInstance(String stringtype, String projectId) {
        DayPkListFragment newFragment = new DayPkListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("stringtype", stringtype);
        bundle.putString("projectId", projectId);
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
        parentView = inflater.inflate(R.layout.daypkfragment, container, false);
        Bundle args = getArguments();//放到加载数据那页
        stringtype = args != null ? args.getString("stringtype") : defaultHello;
        ProjectID = args != null ? args.getString("projectId") : defaultHello;

        other_recycle = (XRecyclerView) parentView.findViewById(R.id.other_recycle);

        RelativeLayout.LayoutParams headParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int padtop = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 18);
        int padbelow = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 10);
        headParams.addRule(RelativeLayout.BELOW, R.id.zhan_Lin);
        headParams.setMargins(0, padtop, 0, padbelow);

        // 添加头部
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.daypkfragment_head, (ViewGroup) parentView.findViewById(android.R.id.content), false);
        RelativeLayout head_Rel = (RelativeLayout) header.findViewById(R.id.head_Rel);
        RelativeLayout my_nameRel = (RelativeLayout) header.findViewById(R.id.my_nameRel);
        my_nameRel.setLayoutParams(headParams);

        RelativeLayout my_Rel = (RelativeLayout) header.findViewById(R.id.my_Rel);
        LinearLayout set_Lin = (LinearLayout) header.findViewById(R.id.set_Lin);
        Button set_Btn = (Button) header.findViewById(R.id.set_Btn);
        daypk_pen = (Button) header.findViewById(R.id.daypk_pen);
        zan_Lin = (LinearLayout) header.findViewById(R.id.zan_Lin);
        zan_img = (ImageView) header.findViewById(R.id.zan_img);
        zan_Txt = (TextView) header.findViewById(R.id.zan_Txt);
        myhui_count_Txt = (TextView) header.findViewById(R.id.myhui_count_Txt);
        zhan_Lin = (LinearLayout) header.findViewById(R.id.zhan_Lin);
        zhan_simple = (SimpleDraweeView) header.findViewById(R.id.zhan_simple);
        zhanTxt = (TextView) header.findViewById(R.id.zhanTxt);
        my_simple = (SimpleDraweeView) header.findViewById(R.id.my_simple);
//        StaticData.ViewScale(head_Rel, 710, 240);
        StaticData.ViewScale(head_Rel, 710, 0);
        StaticData.ViewScale(my_Rel, 580, 120);
        StaticData.ViewScale(set_Lin, 120, 120);
        StaticData.ViewScale(set_Btn, 60, 60);
        StaticData.ViewScale(daypk_pen, 60, 60);
        StaticData.ViewScale(zan_img, 40, 40);
        StaticData.ViewScale(zhan_simple, 80, 80);
        StaticData.ViewScale(my_simple, 80, 80);
        other_recycle.addHeaderView(header);

        clipPaddTop(720);
//        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 500);
//        other_recycle.setPadding(0, pad, 0, 0);
//        other_recycle.setLoadingMoreEnabled(false);//底部不加载
        other_recycle.setLoadingListener(this);//添加事件
        other_recycle.setPullRefreshEnabled(false);
        other_recycle.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁


        daypk_pen.setOnClickListener(new daypk_pen());
        zan_Lin.setOnClickListener(new zan_Lin());
        set_Btn.setOnClickListener(new set_Btn());
        return parentView;
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("DayPkListFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("DayPkListFragment"); //统计页面，"MainScreen"为页面名称，可自定义

        isPrepared = true;
        lazyLoad();
//        onRefresh();
//        PageIndex = 1;
//        pageSize = 10;
//        ListData(getActivity(), saveFile.BaseUrl + saveFile.ReportRankUrl + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
//        other_recycle.refresState(2);
//        tableData(saveFile.BaseUrl+"/Study/Search");
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
        ListData(getActivity(), saveFile.BaseUrl + saveFile.ReportRankUrl + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
        personData(getActivity(), saveFile.BaseUrl + saveFile.PersonData_Url + "?ProjectID=" + ProjectID);
    }


    @Override
    public void onRefresh() {//刷新
//        PageIndex = 1;
//        pageSize = 10;
//        ListData(getActivity(), saveFile.BaseUrl + saveFile.ReportRankUrl + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
//        ListData(getActivity(), saveFile.BaseUrl + saveFile.ReportRankUrl + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
        lazyLoad();
    }

    @Override
    public void onLoadMore() {//加载更多
        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(getActivity(), saveFile.BaseUrl + saveFile.ReportRankUrl + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    public void clipPaddTop(int index) {
        index = index - 128 + 26;//128状态栏加tab高度 26是底部高出部分
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * index);
        other_recycle.setClipToPadding(false);
        other_recycle.setPadding(0, pad, 0, 0);
    }

    private class zan_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            int ReportID = PersonModel.getData().getReportID();
            zanData(getActivity(), saveFile.BaseUrl + saveFile.like_Url + "?ReportID=" + ReportID);
        }
    }

    public static final int REQUEST_CODE_IMAGE_PICK = 34;

    private class set_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intentimagepic = new Intent(getActivity(), ImagePickerActivity.class);
            startActivityForResult(intentimagepic, REQUEST_CODE_IMAGE_PICK);
        }
    }

    private class daypk_pen implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MobclickAgent.onEvent(getActivity(), "DayPkListAdd");//统计页签
            Intent intent = new Intent(getActivity(), Pk_DayPkAdd.class);
            startActivity(intent);
        }
    }

    DayPkFragment_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new DayPkFragment_Adapter(context, baseModel, listModel);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new com.moying.energyring.myAdapter.DayPkFragment_Adapter.OnItemClickLitener() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_PICK) {
            if (resultCode == Activity.RESULT_OK) {
                String path = data.getStringExtra("path");
                //更换pk背景图
                if (!TextUtils.isEmpty(path)) {
//                    Uri imgUri = Uri.parse(String.valueOf(path));
//                    pkbg_simple.setImageURI(imgUri);
//                    upload_PhotoData(getActivity() , saveFile.BaseUrl + saveFile.uploadPhoto_Url,path);

                    compressSingleListener(new File(path), Luban.FIRST_GEAR, REQUEST_CODE_IMAGE_PICK);//压缩 THIRD_GEAR  普通压缩 FIRST_GEAR快速压缩
                }
            }
        }
    }

    //压缩图片
    private void compressSingleListener(File file, int gear, final int type) {
        if (StaticData.isSpace(file.getName())) {
            return;
        }
        Luban.compress(file, getActivity().getFilesDir())
                .putGear(gear)
                .launch(new OnCompressListener() {
                    @Override
                    public void onStart() {
//                        Log.i(TAG, "start");
                    }

                    @Override
                    public void onSuccess(File file) {
//                        Log.e("图片尺寸1111111111111111111",file.length() / 1024 + "k");
                        Uri imgUri = Uri.fromFile(file);
                        pkbg_simple.setImageURI(imgUri);
                        //上传每日PK背景图
                        upload_PhotoData(getActivity(), saveFile.BaseUrl + saveFile.uploadPhoto_Url, file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }


    private List<DayPkList_Model.DataBean> baseModel;
    DayPkList_Model listModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
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
                    listModel = new Gson().fromJson(resultString, DayPkList_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
                        baseModel.addAll(listModel.getData());
                        if (PageIndex == 1) {

                            if (baseModel.size() == 0) {
                                clipPaddTop(1080);
                            } else if (baseModel.size() == 1) {
                                clipPaddTop(960);
                            } else if (baseModel.size() == 2) {
                                clipPaddTop(840);
                            } else {
                                clipPaddTop(720);
                            }

//                            myheadData();//列表头部数据

                            fragmentbg(baseModel);//背景图

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

    PersonData_Model PersonModel;

    public void personData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    PersonModel = new Gson().fromJson(resultString, PersonData_Model.class);
                    if (PersonModel.isIsSuccess() && !PersonModel.getData().equals("[]")) {
                        myheadData();//列表头部个人数据

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

    public void zanData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {

                    BaseDataInt_Model model = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                    if (model.isIsSuccess()) {

                        PersonData_Model.DataBean onedata = PersonModel.getData();
                        if (onedata.isIs_Like()) {
                            onedata.setIs_Like(false);
                            if (onedata.getLikes() == 0) {
                                onedata.setLikes(0);
                            } else {
                                onedata.setLikes(onedata.getLikes() - 1);
                            }
                        } else {
                            onedata.setIs_Like(true);
                            onedata.setLikes(onedata.getLikes() + 1);
                        }

                        myheadData();//列表头部个人数据

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

    //自己数据
    private void myheadData() {
        PersonData_Model.DataBean perdata = PersonModel.getData();
        if (perdata.getProfilePicture() != null) {
//            StaticData.addPlaceRound(my_simple,getActivity());//占位图
            Uri imgUri = Uri.parse(String.valueOf(perdata.getProfilePicture()));
            my_simple.setImageURI(imgUri);
        } else {
            StaticData.lodingheadBg(my_simple);
        }

        if (perdata.getReportNum() == 0) {//无数据
            zan_Lin.setVisibility(View.GONE);
//            daypk_pen.setVisibility(View.VISIBLE);
        } else {
//            daypk_pen.setVisibility(View.GONE);
            zan_Txt.setText(perdata.getLikes() + "");
//            perdata.setIs_Like(true);
            if (perdata.isIs_Like()) {
                zan_img.setImageResource(R.drawable.like_red_icon);
            } else {
                zan_img.setImageResource(R.drawable.energy_like);
            }
            NumberFormat nf = new DecimalFormat("#.#");//# 0 不显示
            if (perdata.getReportNum() >= perdata.getLimit()) {
                myhui_count_Txt.setText(nf.format(perdata.getLimit()) + "+");
            } else {
                myhui_count_Txt.setText(nf.format(perdata.getReportNum()) + perdata.getProjectUnit());
            }
        }

    }

    //背景图占领封面item
    private void fragmentbg(List<DayPkList_Model.DataBean> Model) {
//        if (getUserVisibleHint()) {
        if (Model.size() != 0) {
            int pos = 0;
            DayPkList_Model.DataBean oneData = Model.get(pos);
            zhan_Lin.setVisibility(View.VISIBLE);//占领封面item
            if (oneData.getProfilePicture() != null) {
//                StaticData.addPlaceRound(zhan_simple,getActivity());//占位图
                Uri imgUri = Uri.parse(String.valueOf(oneData.getProfilePicture()));
                zhan_simple.setImageURI(imgUri);
            }
            if (oneData.getNickName() != null) {
                zhanTxt.setText(String.valueOf(oneData.getNickName()));
            }else{
                zhanTxt.setText("没有名字");
            }
            if (oneData.getPKCoverImg() != null) {
//                StaticData.addPlace(pkbg_simple,getActivity());//占位图
                Uri imgUri = Uri.parse(String.valueOf(oneData.getPKCoverImg()));
                pkbg_simple.setImageURI(imgUri);
            } else {
                StaticData.PkBg(pkbg_simple);
//                Uri uri = Uri.parse("res:///" + R.drawable.loading_icon);
//                pkbg_simple.setImageURI(uri);
            }
        } else {
            StaticData.PkBg(pkbg_simple);

//            Uri uri = Uri.parse("res:///" + R.drawable.loading_icon);
//            pkbg_simple.setImageURI(uri);
        }
//        }
    }

    public void upload_PhotoData(final Context context, String baseUrl, File file) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        params.setMultipart(true);//表单格式
        params.setCancelFast(true);//支持断点续传
//            params.addBodyParameter("file"+i,photoPaths.get(i),null,photoPaths.get(i));
        try {
            FileInputStream fileStream = new FileInputStream(file);
            params.addBodyParameter("file", fileStream, null, file.getName());
            //最后fileName InputStream参数获取不到文件名, 最好设置, 除非服务端不关心这个参数.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                AddPhoto_Model model = new Gson().fromJson(resultString, AddPhoto_Model.class);
                if (model.isIsSuccess()) {
                    String files = model.getData().toString().replace("[", "").replace("]", "");
                    AddPkBg_Data(getActivity(), saveFile.BaseUrl + saveFile.AddPkBg_Url + "?FileID=" + files, files);
                } else {
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
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

    public void AddPkBg_Data(final Context context, String baseUrl, String fileId) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
//        params.addParameter("FileID", fileId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                Base_Model model = new Gson().fromJson(resultString, Base_Model.class);
                if (model.isIsSuccess()) {
//                    finish();
                } else {
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
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
