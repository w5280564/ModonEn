package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.Model.isFristSee_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.GuideUtil;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.FeedbackFragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Person_Feedback extends Activity implements XRecyclerView.LoadingListener {

    private XRecyclerView All_XRecy;
    private int PageIndex;
    private int pageSize;
    private GuideUtil guideUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__feedback);

        initTitle();
        All_XRecy = (XRecyclerView) findViewById(R.id.All_XRecy);
        All_XRecy.setLoadingListener(this);//添加事件
        initAddHeadView(All_XRecy, Person_Feedback.this);
        initData();
        initGuide();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("我要吐槽");
        Button right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        right_Btn.setText("发帖");
//        right_Btn.setBackgroundResource(R.drawable.personnew_idea_icon);
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);
        StaticData.ViewScale(right_Btn, 64, 64);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
    }

    private void initAddHeadView(XRecyclerView myView, Context context) {
        View header = LayoutInflater.from(context).inflate(R.layout.feedback_head, null);
        View rend_Txt_One = header.findViewById(R.id.rend_Txt_One);
        View rend_Txt_Two = header.findViewById(R.id.rend_Txt_Two);
        View rend_Txt_Three = header.findViewById(R.id.rend_Txt_Three);
        View rend_Txt_Four = header.findViewById(R.id.rend_Txt_Four);

        StaticData.ViewScale(rend_Txt_One,42,42);
        StaticData.ViewScale(rend_Txt_Two,42,42);
        StaticData.ViewScale(rend_Txt_Three,42,42);
        StaticData.ViewScale(rend_Txt_Four,42,42);
        myView.addHeaderView(header);
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(Person_Feedback.this,Person_Feedback_Posting.class);
            startActivity(intent);
        }
    }


    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        PageIndex += 1;
        pageSize = 10;
        ListData(Person_Feedback.this, saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=9&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    private void initData() {
        PageIndex = 1;
        pageSize = 10;
        ListData(Person_Feedback.this, saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=9&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    FeedbackFragment_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        mAdapter = new FeedbackFragment_Adapter(context, baseModel, listModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new FeedbackFragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                if (baseModel.get(position).getPostType() == 2)
                String url = saveFile.BaseUrl + "/Share/PostDetails?PostID=" + baseModel.get(position).getPostID();
                Intent intent = new Intent(context, Person_Feedback_Detail.class);
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
                    listModel = new Gson().fromJson(resultString, EnergyList_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
                        if (PageIndex == 1) {
                            baseModel.addAll(listModel.getData());
                            All_XRecy.refreshComplete();
                            initlist(context);
                        } else {
                            All_XRecy.loadMoreComplete();
                            mAdapter.addMoreData(listModel);
                        }

                    } else {
                        Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
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

    //展示功能提醒页
    private void initGuide() {
        guideFristData(Person_Feedback.this, saveFile.BaseUrl + saveFile.GuidePerFirst_Url);
        guideUtil = GuideUtil.getInstance();
    }

    public void guideFristData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    isFristSee_Model isFristModel = new Gson().fromJson(resultString, isFristSee_Model.class);
                    if (isFristModel.isIsSuccess()) {
                        if (!isFristModel.getData().isIs_Suggest()) {

//                            Intent intent = new Intent(context, Pk_Guide.class);
//                            intent.putExtra("guideId", "4");
//                            startActivity(intent);
                            guideUtil.initGuide((Activity) context, 4, 1);
                            updguidePer_Data(context, saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_Suggest");//展示功能提醒页
                        }


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

    public void updguidePer_Data(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model Model = new Gson().fromJson(resultString, Base_Model.class);
                    if (Model.isIsSuccess()) {

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
