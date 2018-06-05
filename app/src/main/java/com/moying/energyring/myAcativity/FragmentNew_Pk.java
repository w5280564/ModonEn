package com.moying.energyring.myAcativity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.JiFenAndBadge_Model;
import com.moying.energyring.Model.Pk_MyIntegral_Model;
import com.moying.energyring.Model.ProjectModel;
import com.moying.energyring.Model.isFristSee_Model;
import com.moying.energyring.Model.myCheckData_Model;
import com.moying.energyring.Model.newPk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.GuideUtil;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Energy.HasNewActivity;
import com.moying.energyring.myAcativity.Person.Person_BadgeHas;
import com.moying.energyring.myAcativity.Person.Person_Commendation;
import com.moying.energyring.myAcativity.Pk.JiFenActivity;
import com.moying.energyring.myAcativity.Pk.Pk_AddReport;
import com.moying.energyring.myAcativity.Pk.Pk_CheckIn;
import com.moying.energyring.myAcativity.Pk.Pk_DayPKAdd_Project_Tab;
import com.moying.energyring.myAcativity.Pk.Pk_DayPk_Project_Detail;
import com.moying.energyring.myAcativity.Pk.Pk_DayPk_Project_Detail_RankAll;
import com.moying.energyring.myAcativity.Pk.Pk_FenRankList;
import com.moying.energyring.myAcativity.Pk.Pk_HuiZong;
import com.moying.energyring.myAcativity.Pk.Training.TrainingTodaySet;
import com.moying.energyring.myAdapter.newPk_Fragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.xrecycle.XRecyclerView;
import com.sunfusheng.marqueeview.MarqueeView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.moying.energyring.network.saveFile.getShareData;


/**
 * Created by waylen on 2017/10/19.
 */

public class FragmentNew_Pk extends Fragment implements XRecyclerView.LoadingListener, GuideUtil.RemoveListener {
    private View parentView;
    private XRecyclerView pk_recy;
    LinearLayout check_Lin;
    MarqueeView marqueeView;
    public static int guideID = 1001;
    public static int jifenID = 1002;
    private TextView my_rank_Txt, my_change_Txt, my_Count_Txt;
    private ImageView my_change_Img;
    GuideUtil guideUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.framentnew_pk, null);

        initView(parentView);
//        initLocaData();
//        tabViewSetView();
        guideFristData(getActivity(), saveFile.BaseUrl + saveFile.GuidePerFirst_Url);//展示功能提醒页
        guideUtil = GuideUtil.getInstance();
        guideUtil.setRemoveListener(this);


        return parentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

    private void initData() {
        checkData(saveFile.BaseUrl + saveFile.Check_Url);//是否签到
        myRankData(getActivity(), saveFile.BaseUrl + saveFile.My_Rank_Url);
        String userId = saveFile.getShareData("userId", getActivity());
//        ListData(getActivity(), saveFile.BaseUrl + saveFile.DayPk_Url + "?UserID=" + userId);
        ListData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportProject_List_Url);

    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (RESULT_OK == resultCode) {
//            String guideId = data.getStringExtra("guideId");
////            jiFenmodel = data.getParcelableExtra("jiFenmodel");
//            guideFristData(getActivity(), saveFile.BaseUrl + saveFile.GuidePerFirst_Url, Integer.parseInt(guideId));//展示功能提醒页
//        } else
        if (resultCode == 1002) {
            if (!jiFenmodel.getData().get_Badge().isEmpty()) {
                Intent intent = new Intent(getActivity(), Person_BadgeHas.class);
                intent.putExtra("jiFenmodel", jiFenmodel);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
            }
        } else if (resultCode == 1003) {
            if (!jiFenmodel.getData().get_Praise().isEmpty()) {
                Intent intentSuccess = new Intent(getActivity(), Person_Commendation.class);
                intentSuccess.putExtra("jiFenmodel", jiFenmodel);
                startActivity(intentSuccess);
                getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
            }
        }
//        else if (resultCode == TixingResult) {
//            Intent intent = new Intent(getActivity(), JiFenActivity.class);
//            intent.putExtra("jifen", jiFenmodel.getData().getIntegral());
//            startActivityForResult(intent, 1002);
//        }

    }

    private void initView(View view) {
        marqueeView = (MarqueeView) view.findViewById(R.id.MarqueeView);
        RelativeLayout titlebg_Rel = (RelativeLayout) view.findViewById(R.id.titlebg_Rel);
        LinearLayout my_Lin = (LinearLayout) view.findViewById(R.id.my_Lin);
        check_Lin = (LinearLayout) view.findViewById(R.id.check_Lin);
        ImageView pk_check_img = (ImageView) view.findViewById(R.id.pk_check_img);
        TextView hui_Txt = (TextView) view.findViewById(R.id.hui_Txt);
        ImageView pk_more_img = (ImageView) view.findViewById(R.id.pk_more_img);
        my_rank_Txt = (TextView) view.findViewById(R.id.my_rank_Txt);
        my_change_Img = (ImageView) view.findViewById(R.id.my_change_Img);
        my_change_Txt = (TextView) view.findViewById(R.id.my_change_Txt);
        my_Count_Txt = (TextView) view.findViewById(R.id.my_Count_Txt);
        View rank_Img = view.findViewById(R.id.rank_Img);
//        View rank_Rel = view.findViewById(R.id.rank_Rel);
//        View rank_icon = view.findViewById(R.id.rank_icon);
        View rank_Txt = view.findViewById(R.id.rank_Txt);


        pk_recy = (XRecyclerView) view.findViewById(R.id.pk_recy);
        pk_recy.setLoadingListener(this);//添加事件
        pk_recy.setPullRefreshEnabled(true);
        pk_recy.setLoadingMoreEnabled(false);
        ImageView pk_ce_Img = (ImageView) view.findViewById(R.id.pk_ce_Img);
//        StaticData.ViewScale(titlebg_Rel, 0, 326);
        StaticData.ViewScale(titlebg_Rel, 0, 232);
        StaticData.ViewScale(my_Lin, 0, 56);
        StaticData.ViewScale(check_Lin, 164, 56);
        StaticData.ViewScale(pk_more_img, 56, 44);
        StaticData.ViewScale(my_change_Img, 16, 18);

        StaticData.ViewScale(pk_check_img, 36, 40);
        StaticData.ViewScale(pk_ce_Img, 94, 308);
        StaticData.ViewScale(hui_Txt, 164, 56);
//        StaticData.ViewScale(rank_Rel, 0, 76);
        StaticData.ViewScale(rank_Txt, 56, 56);
        StaticData.ViewScale(rank_Img, 40, 40);
        check_Lin.setOnClickListener(new check_Lin());
        pk_ce_Img.setOnClickListener(new pk_ce_Img());
        marqueeView.setOnItemClickListener(new marqueeView());
        hui_Txt.setOnClickListener(new hui_Txt());
        titlebg_Rel.setOnClickListener(new titlebg_Rel());
        rank_Txt.setOnClickListener(new rank_Txt());
    }

    @Override
    public void RemoveListener(int index, boolean islast) {
        if (index == 0) {

            guideFristData(getActivity(), saveFile.BaseUrl + saveFile.GuidePerFirst_Url);//展示功能提醒页
        } else if (index == 1) {

            guideFristData(getActivity(), saveFile.BaseUrl + saveFile.GuidePerFirst_Url);//展示功能提醒页
        } else if (index == 2) {

            Intent intent1 = new Intent(getActivity(), HasNewActivity.class);
            startActivity(intent1);
        }
    }


    private class titlebg_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(),);
            Intent intent = new Intent(getActivity(), Pk_FenRankList.class);
            startActivity(intent);

//            Intent intentJiFen = new Intent(getActivity(), JiFenActivity.class);
//            intentJiFen.putExtra("media", "daypk");
//            intentJiFen.putExtra("jifen", 10);
//            intentJiFen.putExtra("RewardIntegral", 10+"");
//            startActivity(intentJiFen);
        }
    }

    private class check_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            check_Lin.setEnabled(false);
            AddData(saveFile.BaseUrl + saveFile.CheckAdd_Url);//签到
        }
    }

    private class pk_ce_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent1 = new Intent(getActivity(), Pk_DayPkAdd_More.class);
//            startActivityForResult(intent1, guideID);
            Intent intent = new Intent(getActivity(), Pk_DayPKAdd_Project_Tab.class);
            List<ProjectModel> projectModel = new ArrayList<>();
            intent.putExtra("baseModel", (Serializable) projectModel);
            //第二个参数为请求码，可以根据业务需求自己编号
//            startActivityForResult(intent, RESULT_CODE_MORE);
            startActivity(intent);
        }
    }

    private class marqueeView implements MarqueeView.OnItemClickListener {
        @Override
        public void onItemClick(int position, TextView textView) {
            Intent intent = new Intent(getActivity(), Pk_CheckIn.class);
            startActivity(intent);

//            String resultStr = "{\n" +
//                    "  \"IsSuccess\": true,\n" +
//                    "  \"Msg\": \"操作成功!\",\n" +
//                    "  \"Status\": 200,\n" +
//                    "  \"Data\": {\n" +
//                    "    \"Integral\": 10,\n" +
//                    "    \"RewardIntegral\": 10,\n" +
//                    "    \"_Badge\": [],\n" +
//                    "    \"_Praise\": null,\n" +
//                    "    \"DailyTask\": {\n" +
//                    "      \"TaskID\": 1,\n" +
//                    "      \"TaskName\": \"每日签到\",\n" +
//                    "      \"Summary\": \"啦啦啦啦啦绿绿绿\",\n" +
//                    "      \"Integral\": 30,\n" +
//                    "      \"Condition\": \"3\",\n" +
//                    "      \"BtnText\": \"去签到,已签到\"\n" +
//                    "    }\n" +
//                    "  }\n" +
//                    "}";
//            jiFenmodel = new Gson().fromJson(resultStr, JiFenAndBadge_Model.class);
//
//            Intent intent = new Intent(getActivity(), JiFenActivity.class);
//            intent.putExtra("jifen", jiFenmodel.getData().getIntegral());
//            intent.putExtra("DailyTask",jiFenmodel.getData().getDailyTask());
//            intent.putExtra("RewardIntegral", "10");
//            startActivityForResult(intent, jifenID);
        }
    }


    private class hui_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            view.setEnabled(false);
            isReteDayPk(view, getActivity(), saveFile.BaseUrl + saveFile.haveNewRepost_Url);
        }
    }

    private class rank_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Pk_DayPk_Project_Detail_RankAll.class);
//            intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
            startActivity(intent);
        }
    }


    newPk_Fragment_Adapter mAdapter;

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
                if (baseModel.getData().get(position).getReportID() == 0) { //0今天没有汇报项目
                    if (baseModel.getData().get(position).getProjectName().equals("健康走")) {
                        Intent intent = new Intent(getActivity(), Pk_DayPk_Project_Detail.class);
                        intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
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

                    Intent intent = new Intent(getActivity(), Pk_DayPk_Project_Detail.class);
                    intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
                    startActivity(intent);
//                    Intent intent = new Intent(getActivity(), Pk_DayPkDetail.class);
//                    intent.putExtra("titleName", baseModel.getData().get(position).getProjectName());
//                intent.putExtra("imgPath",baseModel.getData().get(position).getFilePath());
//                Intent intent = new Intent(getActivity(), Pk_Daypk.class);
//                intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
//                startActivity(intent);
                }


            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (baseModel.getData().get(position).isIs_CanDel()) {
                    ScaleAnimation scal = new ScaleAnimation(0.8f, 1.1f, 0.8f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scal.setDuration(100);
                    view.startAnimation(scal);
                    showRemove(getActivity(), view, position);
                }
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


    newPk_Model baseModel;

    public void ListData(final Context context, String baseUrl) {
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
                        initlist(getActivity());
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


    Base_Model check_Model;

    public void checkData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    check_Model = new Gson().fromJson(resultString, Base_Model.class);
                    if (check_Model.isData()) {
                        myCheckData(saveFile.BaseUrl + saveFile.MyCheckIn_Url);
                    } else {
                        check_Lin.setVisibility(View.VISIBLE);
                        marqueeView.setVisibility(View.INVISIBLE);
                    }

                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(getActivity(), MainLogin.class);
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

    JiFenAndBadge_Model jiFenmodel;

    public void AddData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    jiFenmodel = new Gson().fromJson(resultString, JiFenAndBadge_Model.class);

                    if (jiFenmodel.isIsSuccess()) {
                        check_Lin.setEnabled(true);
//                        check_Lin.setVisibility(View.GONE);
//                        ScrollTextView.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getActivity(), JiFenActivity.class);
                        intent.putExtra("media", "check");
                        intent.putExtra("jifen", jiFenmodel.getData().getIntegral());
                        intent.putExtra("DailyTask",jiFenmodel.getData().getDailyTask());
                        startActivityForResult(intent, jifenID);
//                        startActivity(intent);
                        Toast.makeText(getActivity(), "签到成功", Toast.LENGTH_SHORT);
                        myCheckData(saveFile.BaseUrl + saveFile.MyCheckIn_Url);
                    } else {
                        Toast.makeText(getActivity(), "无法签到请检查网络", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                check_Lin.setEnabled(true);
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(getActivity(), MainLogin.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
                check_Lin.setEnabled(true);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void myCheckData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    myCheckData_Model model = new Gson().fromJson(resultString, myCheckData_Model.class);
                    if (model.isIsSuccess()) {
                        check_Lin.setVisibility(View.GONE);
                        marqueeView.setVisibility(View.VISIBLE);

                        List<String> info = new ArrayList<>();
                        info.add("连续签到" + model.getData().getContinueDays() + "天");
                        info.add("累计签到" + model.getData().getCheckInDays() + "天");
                        marqueeView.startWithList(info);

                    } else {
                        Toast.makeText(getActivity(), "无法签到请检查网络", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(getActivity(), MainLogin.class);
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

    public void isReteDayPk(final View view, final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    view.setEnabled(true);
                    Base_Model reteModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (reteModel.isData()) {
                        Intent intent = new Intent(getActivity(), Pk_HuiZong.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "请汇报更多pk", Toast.LENGTH_SHORT).show();
                    }

//                    int integral = reteModel.getData();
//                    if (integral == -1) {
//                        Toast.makeText(context, "请汇报更多pk", Toast.LENGTH_SHORT).show();
//                    }else if (integral >= 0){
//                        Intent intent = new Intent(getActivity(), Pk_HuiZong.class);
//                        startActivity(intent);
//                    }
//                    else if (integral == 0) {
//                        //汇报达到上限没分
//                        Toast.makeText(context, "汇报成功", Toast.LENGTH_SHORT).show();
//                    } else if (integral > 0) {
//                        Toast.makeText(context, "汇报成功", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getActivity(), JiFenActivity.class);
//                        intent.putExtra("jifen", integral);
//                        startActivity(intent);
//                    }

                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                view.setEnabled(true);
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, MainLogin.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
                view.setEnabled(true);
            }

            @Override
            public void onFinished() {
                view.setEnabled(true);
            }
        });
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
                        if (!isFristModel.getData().isIs_CheckIn_Remind()) {
//                                initGuide(isFristModel.getData().isIs_CheckIn_Remind());
//                                updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_CheckIn_Remind");//展示功能提醒页
                            guideUtil.initGuide(getActivity(), 0, 1);
                            updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_CheckIn_Remind");//展示功能提醒页
                        } else if (!isFristModel.getData().isIs_FirstPool()) {
//                            Field[] isFrist  =  isFristModel.getData().getClass().getDeclaredFields();
//                            isFrist[0].getName()

//                            isFristSee();
//                            updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_FirstPool");//展示功能提醒页

                            guideUtil.initGuide(getActivity(), 1, 1);
                            updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_FirstPool");//展示功能提醒页
                        } else if (!isFristModel.getData().isIs_First_Train()) {
//                            Field[] isFrist  =  isFristModel.getData().getClass().getDeclaredFields();
//                            isFrist[0].getName()

//                            isFristSee();
//                            updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_FirstPool");//展示功能提醒页

                            guideUtil.initGuide(getActivity(), 2, 1);
                            updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_First_Train");//展示功能提醒页
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

    public void myRankData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Pk_MyIntegral_Model Integral_Model = new Gson().fromJson(resultString, Pk_MyIntegral_Model.class);
                    if (Integral_Model.isIsSuccess() && !Integral_Model.getData().equals("[]")) {
                        my_Count_Txt.setText("积分" + Integral_Model.getData().getAllIntegral());
                        my_rank_Txt.setText("第" + Integral_Model.getData().getRanking() + "名");
                        if (Integral_Model.getData().getWorld() == 0) {
                            my_change_Img.setVisibility(View.INVISIBLE);
                            my_change_Txt.setVisibility(View.INVISIBLE);
                        } else if (Integral_Model.getData().getWorld() < 0) {
                            my_change_Img.setImageResource(R.drawable.change_down);
                            my_change_Txt.setTextColor(Color.parseColor("#FF0100"));
                            my_change_Txt.setText(Integral_Model.getData().getWorld() + "");
                        } else if (Integral_Model.getData().getWorld() > 0) {
                            my_change_Img.setImageResource(R.drawable.change_update);
                            my_change_Txt.setTextColor(Color.parseColor("#0BC10B"));
                            my_change_Txt.setText(Integral_Model.getData().getWorld() + "");
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


}
