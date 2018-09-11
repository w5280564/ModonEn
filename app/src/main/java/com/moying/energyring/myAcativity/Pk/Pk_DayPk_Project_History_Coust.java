package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.DayPkDetail_Model;
import com.moying.energyring.Model.ReportHistory_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.History_Coust_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static com.moying.energyring.network.saveFile.getShareData;

public class Pk_DayPk_Project_History_Coust extends Activity implements XRecyclerView.LoadingListener{

    private TextView cententtxt;
    private String projectId, dateTime;
    private SimpleDraweeView history_simple;
    private TextView historyName_Txt, lei_Txt, day_Txt, time_Txt,head_Txt;
    private XRecyclerView other_recycle;
    private int PageIndex;
    private int pageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_pk_project_history_coust);

        Intent intent = getIntent();
//        titleName = intent.getStringExtra("titleName");
        projectId = intent.getStringExtra("projectId");
        dateTime = intent.getStringExtra("dateTime");
        initTitle();
        initView();


        changeData(0);
        initData(projectId);

    }

    private void initTitle() {
        View title_Rel = findViewById(R.id.title_Rel);
        View return_Img = findViewById(R.id.return_Img);
        View colock_Img = findViewById(R.id.colock_Img);
        cententtxt = (TextView) findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this, R.color.colorFristWhite));
        cententtxt.setText("历史统计");
        View right_Img = findViewById(R.id.right_Img);
//        StaticData.ViewScale(title_Rel, 0, 88);
        StaticData.ViewScale(return_Img, 39, 63);
        StaticData.ViewScale(right_Img, 36, 36);
        StaticData.ViewScale(colock_Img, 36, 36);
        viewTouchDelegate.expandViewTouchDelegate(right_Img, 100, 100, 100, 100);
        viewTouchDelegate.expandViewTouchDelegate(colock_Img, 100, 100, 100, 100);
        return_Img.setOnClickListener(new return_Img());
        right_Img.setOnClickListener(new right_Img());
        colock_Img.setOnClickListener(new colock_Img());

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        StaticData.ViewScale(mToolbar, 0, 88);
//        setSupportActionBar(mToolbar);
    }


    private void initView() {
        RelativeLayout history_Rel = (RelativeLayout) findViewById(R.id.history_Rel);
        int relpad = (int) (Float.parseFloat(saveFile.getShareData("scale", this)) * 118);
        history_Rel.setPadding(0, relpad, 0, 0);

        history_simple = (SimpleDraweeView) findViewById(R.id.history_simple);
        historyName_Txt = (TextView) findViewById(R.id.historyName_Txt);
        lei_Txt = (TextView) findViewById(R.id.lei_Txt);
        day_Txt = (TextView) findViewById(R.id.day_Txt);
        time_Txt = (TextView) findViewById(R.id.time_Txt);
        head_Txt = (TextView) findViewById(R.id.head_Txt);

        other_recycle = (XRecyclerView) findViewById(R.id.other_recycle);
//        other_recycle.setLoadingMoreEnabled(false);//底部不加载
        other_recycle.setPullRefreshEnabled(false);
        other_recycle.setLoadingListener(this);//添加事件
        other_recycle.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁

        StaticData.ViewScale(history_simple, 130, 130);
    }

    private void changeData(int isChange) {
//        changeData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportProject_List_Url);
        time_Txt.setText(dateTime);
        myRankData(Pk_DayPk_Project_History_Coust.this, saveFile.BaseUrl + saveFile.My_ReportRank_Url + "?ProjectID=" + projectId + "&date=" + dateTime, 0);
    }


    private void initData(String ProjectID) {
//        ListData(Pk_DayPk_Project_HistoryDetail.this, saveFile.BaseUrl + saveFile.My_Ranking_One_Url + "?ProjectID=" + ProjectID);
//        myRankData(Pk_DayPk_Project_HistoryDetail.this, saveFile.BaseUrl + saveFile.My_ReportRank_Url + "?ProjectID=" + ProjectID, 0);
        PageIndex = 1;
        pageSize = 10;
        ListData(Pk_DayPk_Project_History_Coust.this, saveFile.BaseUrl + saveFile.My_ReportRecordProject_Url + "?ProjectID=" + ProjectID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    @Override
    public void onRefresh() {
        initData(projectId);
    }

    @Override
    public void onLoadMore() {
        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(Pk_DayPk_Project_History_Coust.this, saveFile.BaseUrl + saveFile.My_ReportRecordProject_Url + "?ProjectID=" + projectId + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    private class return_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class right_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showDetele(Pk_DayPk_Project_History_Coust.this, cententtxt);
        }
    }

    private class colock_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Pk_DayPk_Project_History_Coust.this, Pk_AddReport_Colock.class);
            intent.putExtra("ProjectID", projectId + "");
            startActivity(intent);
        }
    }

    private void showDetele(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_daypk_delete, null);
        final BasePopupWindow popupWindow = new BasePopupWindow(mContext);
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
        popupWindow.showAsDropDown(view, 0, 0);
        RelativeLayout my_Rel = (RelativeLayout) contentView.findViewById(R.id.my_Rel);
        TextView hint_Txt = (TextView) contentView.findViewById(R.id.hint_Txt);
        LinearLayout cha_Lin = (LinearLayout) contentView.findViewById(R.id.cha_Lin);
        StaticData.ViewScale(my_Rel, 610, 450);
        StaticData.ViewScale(hint_Txt, 0, 120);
        StaticData.ViewScale(cha_Lin, 0, 120);

        contentView.findViewById(R.id.sure_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String ProjectID = baseModel.getData().get(ac_tab_layout.getSelectedTabPosition()).getProjectID() + "";
                deleData(mContext, saveFile.BaseUrl + saveFile.My_preoject_Dele_Url, projectId);
            }
        });
        contentView.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                title_Rel.setBackgroundColor(Color.parseColor("#00000000"));
//                StaticData.ViewScale(more_Btn, 48, 28);
//                more_Btn.setBackgroundResource(R.drawable.more_icon);
//                popupWindow.dismiss();
            }
        });
    }

    //删帖
    public void deleData(final Context context, String baseUrl, String projectId) {
        RequestParams params = new RequestParams(baseUrl);
        if (getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", getShareData("JSESSIONID", context));
        }
        params.addBodyParameter("ProjectID", projectId + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model model = new Gson().fromJson(resultString, Base_Model.class);
                    if (model.isIsSuccess()) {

                        finish();
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

    DayPkDetail_Model Integral_Model;

    public void myRankData(final Context context, String baseUrl, final int isChange) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Integral_Model = new Gson().fromJson(resultString, DayPkDetail_Model.class);
                    if (Integral_Model.isIsSuccess() && !Integral_Model.getData().equals("[]")) {
//                        if (Integral_Model.getData().getIs_SenPost() == 0) {
//                            pos_Txt.setVisibility(View.VISIBLE);
//                        } else {
//                            pos_Txt.setVisibility(View.GONE);
//                        }
//                        initLocaData(ac_tab_layout);
//                        tabViewSetView(ac_tab_layout);
//                        resetTablayout(ac_tab_layout);
                        changeAddImg(Integral_Model);

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


    private void changeAddImg(DayPkDetail_Model pkModel) {
        //是否可以打卡

        String Type = pkModel.getData().getReportID() + "";
        String projectName = pkModel.getData().getProjectName();
        int Limit = pkModel.getData().getLimit();
        boolean isTrain = pkModel.getData().isIs_Train();
        double leiStr = pkModel.getData().getReportNum_All();
        double dayStr = pkModel.getData().getReportNum_Month();
        String Unit = pkModel.getData().getProjectUnit();



        NumberFormat nf = new DecimalFormat("#.#");//# 0不显示
        String leiNf = "";
        if (leiStr > 1000) {
            leiNf = nf.format(leiStr / 1000) + "K";
        }else {
            leiNf = nf.format(leiStr);
        }
        String dayNf = "";
        if (dayStr > 1000) {
            dayNf = nf.format(dayStr / 1000) + "K";
        }else {
            dayNf = nf.format(dayStr);
        }

        historyName_Txt.setText(projectName);
        lei_Txt.setText(leiNf + Unit);
        day_Txt.setText(dayNf + Unit);
        head_Txt.setText(projectName);

//        if (pkModel.getData().getFilePath() != null) {
//            Uri uri = Uri.parse(pkModel.getData().getFilePath() );
//            history_simple.setImageURI(uri);
//        } else {
//            StaticData.lodingheadBg(history_simple);
//        }
    }

    History_Coust_Adapter mAdapter;
    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        other_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        other_recycle.setHasFixedSize(true);
        mAdapter = new History_Coust_Adapter(context, baseModel, listModel);
        other_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new History_Coust_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(context, PersonMyCenter_And_Other.class);
//                intent.putExtra("UserID", baseModel.get(position).getUserID() + "");
//                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<ReportHistory_Model.DataBean> baseModel;
    ReportHistory_Model listModel;

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
                    listModel = new Gson().fromJson(resultString, ReportHistory_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
                        baseModel.addAll(listModel.getData());
                        if (PageIndex == 1) {
                            other_recycle.refreshComplete();
                            initlist(context);
                        } else {
                            other_recycle.loadMoreComplete();
                            mAdapter.addMoreData(baseModel);
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




}
