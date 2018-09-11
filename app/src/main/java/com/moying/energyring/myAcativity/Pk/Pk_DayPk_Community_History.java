package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Community_HistoryList_Model;
import com.moying.energyring.Model.Community_Status_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Person.Person_Feedback_Detail;
import com.moying.energyring.myAdapter.Community_HistoryList_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Pk_DayPk_Community_History extends Activity implements XRecyclerView.LoadingListener {

    private XRecyclerView All_XRecy;
    private SimpleDraweeView head_simple;
    private TextView Nick_Txt;
    private ImageView join_grade_Img;
    private TextView gong_Txt, chi_Txt, lei_Txt, ka_Txt;
    private String ProjectID;
    private int PageIndex;
    private int pageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pk_daypk_community_history);


        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        initTitle();
        initView();
        initData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this, R.color.colorFristWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this, R.color.colorFristBlack));
        cententtxt.setText("回顾");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        PageIndex += 1;
        pageSize = 10;
        ListData(Pk_DayPk_Community_History.this, saveFile.BaseUrl + saveFile.My_Community_Post_List + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private void initView() {

        All_XRecy = (XRecyclerView) findViewById(R.id.All_XRecy);
        All_XRecy.setLoadingListener(this);//添加事件
        initAddHeadView(All_XRecy, Pk_DayPk_Community_History.this);

    }

    private void initAddHeadView(XRecyclerView myView, Context context) {
        View header = LayoutInflater.from(context).inflate(R.layout.community_history_head, null);
        head_simple = (SimpleDraweeView) header.findViewById(R.id.head_simple);
        Nick_Txt = (TextView) header.findViewById(R.id.Nick_Txt);
        join_grade_Img = (ImageView) header.findViewById(R.id.join_grade_Img);
        View gong_Rel = header.findViewById(R.id.gong_Rel);
        View chi_Rel = header.findViewById(R.id.chi_Rel);
        View lei_Rel = header.findViewById(R.id.lei_Rel);
        View ka_Rel = header.findViewById(R.id.ka_Rel);

        gong_Txt = (TextView) header.findViewById(R.id.gong_Txt);
        chi_Txt = (TextView) header.findViewById(R.id.chi_Txt);
        lei_Txt = (TextView) header.findViewById(R.id.lei_Txt);
        ka_Txt = (TextView) header.findViewById(R.id.ka_Txt);

        StaticData.ViewScale(head_simple, 120, 120);
        StaticData.ViewScale(join_grade_Img, 56, 28);
        StaticData.ViewScale(gong_Rel, 660, 90);
        StaticData.ViewScale(chi_Rel, 660, 90);
        StaticData.ViewScale(lei_Rel, 660, 90);
        StaticData.ViewScale(ka_Rel, 660, 90);
        myView.addHeaderView(header);
    }

    private void initData() {
        StatusData();
        PageIndex = 1;
        pageSize = 10;
        ListData(Pk_DayPk_Community_History.this, saveFile.BaseUrl + saveFile.My_Community_Post_List + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    //社区状态
    private void StatusData() {
        Community_StatusData(Pk_DayPk_Community_History.this, saveFile.BaseUrl + saveFile.Community_Status_Url + "?ProjectID=" + ProjectID);
    }

    Community_HistoryList_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        mAdapter = new Community_HistoryList_Adapter(context,listModel, baseModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Community_HistoryList_Adapter.OnItemClickLitener() {
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


    public void Community_StatusData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Community_Status_Model baseModel = new Gson().fromJson(resultString, Community_Status_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Community_Status_Model.DataBean oneData = baseModel.getData();

                        if (oneData.getProfilePicture() != null) {
                            Uri imgUri = Uri.parse(oneData.getProfilePicture());
                            head_simple.setImageURI(imgUri);
                        } else {
                            StaticData.lodingheadBg(head_simple);
                        }

                        StaticData.setGarde(join_grade_Img, oneData.getIntegralLevel());
                        Nick_Txt.setText(oneData.getProjectName());
                        gong_Txt.setText(oneData.getJoinDays() + "天");
                        chi_Txt.setText(oneData.getReport_Days() + "天");
                        NumberFormat nf = new DecimalFormat("#.#");//# 0不显示

                        lei_Txt.setText(nf.format(oneData.getReportNum()) + oneData.getProjectUnit());
                        ka_Txt.setText(oneData.getReportFre() + "次");

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

    private List<Community_HistoryList_Model.DataBean> baseModel;
    Community_HistoryList_Model listModel;

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
                    listModel = new Gson().fromJson(resultString, Community_HistoryList_Model.class);
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




}
