package com.moying.energyring.myAcativity.Pk;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.newPk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Pk.Training.TrainingTodaySet;
import com.moying.energyring.myAdapter.newPk_Fragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.lazyLoadFragment;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.moying.energyring.network.saveFile.getShareData;


/**
 * Created by Admin on 2016/4/18.
 * 成长日志
 */
public class Pk_CalnerFragment extends lazyLoadFragment implements XRecyclerView.LoadingListener {
    private String defaultHello = "default value";
    private String time;
    private String workType;
    private XRecyclerView pk_recy;
    private View parentView;
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private String currentDate = "";
    private Calendar calendar;
    private Date date;
    private newPk_Fragment_Adapter mAdapter;

    public static Pk_CalnerFragment newInstance(String time, String workType) {
        Pk_CalnerFragment newFragment = new Pk_CalnerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("time", time);
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
        time = args != null ? args.getString("time") : defaultHello;
        workType = args != null ? args.getString("workType") : defaultHello;


        pk_recy = (XRecyclerView) parentView.findViewById(R.id.other_recycle);
        pk_recy.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁
//        other_recycle.setLoadingMoreEnabled(false);//底部不加载
        pk_recy.setLoadingListener(this);//添加事件
//        StaticData.changeXRecycleHeadGif(other_recycle, R.drawable.gif_bird_icon, 750, 200);
//        initDate();

//        lazyLoad();
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
//        PageIndex = 1;
//        pageSize = 10;
//        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
//        String time = year_c + "-" + month_c + "-" + day_c;
        initXrecyData(time, 0);
    }

    //初始时间 今天
    private void initDate() {
        calendar = Calendar.getInstance();
        date = new Date();//当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = sdf.format(date);
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);


    }

    private void initXrecyData(String time, int PrevOrNext) {

        ListData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportProject_List_Url + "?date=" + time, PrevOrNext);
    }


    @Override
    public void onRefresh() {//刷新
        lazyLoad();
    }

    @Override
    public void onLoadMore() {//加载更多
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
    }

    public void onPause() {
        super.onPause();
    }


    public void initlist(final Context context) {
        GridLayoutManager mGridMangaer = new GridLayoutManager(context, 2);
        pk_recy.setLayoutManager(mGridMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        pk_recy.setHasFixedSize(true);
        mAdapter = new newPk_Fragment_Adapter(context, baseModel);
        pk_recy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new newPk_Fragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {


                if (saveFile.getTimeBig(time)) {

                    if (baseModel.getData().get(position).getReportID() == 0) { //0今天没有汇报项目
                        if (baseModel.getData().get(position).getProjectName().equals("健康走")) {
//                            Intent intent = new Intent(getActivity(), Pk_DayPk_Project_Detail.class);
//                            intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
//                            startActivity(intent);
                            Intent intent = new Intent(getActivity(), Pk_DayPk_Community.class);
                            intent.putExtra("ProjectID", baseModel.getData().get(position).getProjectID() + "");
                            startActivity(intent);

                        } else if (baseModel.getData().get(position).isIs_Train()) {
                            Intent intent1 = new Intent(getActivity(), TrainingTodaySet.class);
                            intent1.putExtra("ProjectID", baseModel.getData().get(position).getProjectID() + "");
                            startActivity(intent1);

//                        Intent intent1 = new Intent(getActivity(), startTrainingActivity.class);
//                        startActivity(intent1);
                        } else {
                            Intent intent = new Intent(getActivity(), Pk_AddReport.class);
                            intent.putExtra("projectModel", baseModel.getData().get(position));
                            startActivity(intent);
                        }
                    } else {
//                        Intent intent = new Intent(getActivity(), Pk_DayPk_Project_Detail.class);
//                        intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
//                        startActivity(intent);

                        Intent intent = new Intent(getActivity(), Pk_DayPk_Community.class);
                        intent.putExtra("ProjectID", baseModel.getData().get(position).getProjectID() + "");
                        startActivity(intent);


//                    Intent intent = new Intent(getActivity(), Pk_DayPkDetail.class);
//                    intent.putExtra("titleName", baseModel.getData().get(position).getProjectName());
//                intent.putExtra("imgPath",baseModel.getData().get(position).getFilePath());
//                Intent intent = new Intent(getActivity(), Pk_Daypk.class);
//                intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
//                startActivity(intent);
                    }
                } else {
                    //历史数据
//                    if (baseModel.getData().get(position).getProjectTypeID() == -1) {
//                        Intent intent = new Intent(getActivity(), Pk_DayPk_Project_History_Coust.class);
//                        intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
//                        intent.putExtra("dateTime", time);
//                        startActivity(intent);
//
//                    } else {
//                        Intent intent = new Intent(getActivity(), Pk_DayPk_Project_HistoryDetail.class);
//                        intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
//                        intent.putExtra("dateTime", time);
//                        startActivity(intent);
//                    }
                    Intent intent = new Intent(getActivity(), Pk_DayPk_Community.class);
                    intent.putExtra("ProjectID", baseModel.getData().get(position).getProjectID() + "");
                    startActivity(intent);

                }

            }

            @Override
            public void onItemLongClick(View view, int position) {
//                if (baseModel.getData().get(position).isIs_CanDel()) {
                    ScaleAnimation scal = new ScaleAnimation(0.8f, 1.1f, 0.8f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scal.setDuration(100);
                    view.startAnimation(scal);
                    showMore(getActivity(), view, position);
//                }
            }
        });
    }

    private void showRemove(final Context mContext, View view, final int pos) {
        View contentView = View.inflate(mContext, R.layout.training_stop, null);
        final BasePopupWindow popupWindow = new BasePopupWindow(mContext);
//        popupWindow.setmShowAlpha(0.5f);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x1a000000));
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
//        popupWindow.showAsDropDown(view, 0, 0);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
        view.setEnabled(true);

        View my_Rel = contentView.findViewById(R.id.my_Rel);
        View hint_Txt = contentView.findViewById(R.id.hint_Txt);
        View cha_Lin = contentView.findViewById(R.id.cha_Lin);
        TextView content_Txt = (TextView) contentView.findViewById(R.id.content_Txt);
        TextView cancel_Txt = (TextView) contentView.findViewById(R.id.cancel_Txt);
        TextView sure_Txt = (TextView) contentView.findViewById(R.id.sure_Txt);
        StaticData.ViewScale(my_Rel, 610, 490);
        StaticData.ViewScale(hint_Txt, 0, 120);
        StaticData.ViewScale(cha_Lin, 0, 120);

        String contentStr = "确定删除“" + baseModel.getData().get(pos).getProjectName() + "”习惯卡吗?\n删除后可在右下角“+”处添加。";
        content_Txt.setText(contentStr);
        cancel_Txt.setText("删除");
        sure_Txt.setText("点错了");
        cancel_Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ProjectID = baseModel.getData().get(pos).getProjectID() + "";
                deleData(mContext, saveFile.BaseUrl + saveFile.My_preoject_Dele_Url, ProjectID);
                baseModel.getData().remove(pos);
                mAdapter.notifyItemRemoved(pos);
                popupWindow.dismiss();
            }
        });
        sure_Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow.dismiss();
//                popup.setmShowAlpha(0.5f);
            }
        });

    }

    //更多
    private void showMore(final Context mContext, View view, final int pos) {
        View contentView = View.inflate(mContext, R.layout.popup_calner_more, null);
        final PopupWindow ReportPopup = new BasePopupWindow(mContext);
        ReportPopup.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        ReportPopup.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);
        ReportPopup.setTouchable(true);
        ReportPopup.setContentView(contentView);
        ReportPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        TextView report_one_Txt = (TextView) contentView.findViewById(R.id.report_one_Txt);
        TextView report_two_Txt = (TextView) contentView.findViewById(R.id.report_two_Txt);
        TextView report_three_Txt = (TextView) contentView.findViewById(R.id.report_three_Txt);
        TextView report_four_Txt = (TextView) contentView.findViewById(R.id.report_four_Txt);
        report_four_Txt.setVisibility(View.VISIBLE);

        if (baseModel.getData().get(pos).isIs_CanDel()){
            report_three_Txt.setVisibility(View.VISIBLE);
        }else {
            report_three_Txt.setVisibility(View.GONE);
        }

        final String ProjectID = baseModel.getData().get(pos).getProjectID() + "";

        report_one_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Intent intent = new Intent(mContext, Pk_AddReport_Colock.class);
                intent.putExtra("ProjectID", ProjectID + "");
                startActivity(intent);
                ReportPopup.dismiss();
            }
        });
        report_two_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Intent intent = new Intent(mContext, PK_Calner_Set.class);
                intent.putExtra("ProjectID", ProjectID + "");
                startActivity(intent);
                ReportPopup.dismiss();
            }
        });

        report_three_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                deleData(mContext, saveFile.BaseUrl + saveFile.Community_Del_Url, ProjectID);
                baseModel.getData().remove(pos);
                mAdapter.notifyItemRemoved(pos);
                ReportPopup.dismiss();
            }
        });


        report_four_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ReportPopup.dismiss();
            }
        });

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportPopup.dismiss();
            }
        });
    }


    newPk_Model baseModel;

    public void ListData(final Context context, String baseUrl, final int PrevOrNext) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    baseModel = new Gson().fromJson(resultString, newPk_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
                        pk_recy.refreshComplete();

//                        calendarview();//添加滑动监听

                        initlist(getActivity());

//                        if (PrevOrNext == 0){
//                          TodayData(0);
//                        } else if (PrevOrNext == 1){
//                            enterPrevMonth(0);
//                        }else if (PrevOrNext == 2){
//                            enterNextMonth(0);
//                        }
//                        tagList(taglin,baseModel);
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

    //删帖
    public void deleData(final Context context, String baseUrl, String projectId) {
        RequestParams params = new RequestParams(baseUrl);
        if (getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", getShareData("JSESSIONID", context));
        }
        params.addBodyParameter("ProjectID", projectId + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model model = new Gson().fromJson(resultString, Base_Model.class);
                    if (model.isIsSuccess()) {

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


    protected void setStatusBar() {
//        StatusBarUtil.setColor(getActivity(), Color.parseColor("#f3f3f3"));
//        StatusBarUtil.setTranslucent(this);
//        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(),0,seek_Btn);
    }


}
