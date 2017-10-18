package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.CheckData_Model;
import com.moying.energyring.Model.CheckList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAdapter.pk_CheckIn_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.xrecycle.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class Pk_CheckIn extends Activity implements XRecyclerView.LoadingListener {

    private XRecyclerView pk_xrecy;
    private TextView allday_Txt,con_Txt,rank_Txt,rankCount_Txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk__check_in);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中


        initView();
        initHead(Pk_CheckIn.this);
        initData(Pk_CheckIn.this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Pk_CheckIn"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Pk_CheckIn"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    public void initView() {
        View title_Include = findViewById(R.id.title_Include);
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_icon);
//        return_Btn.setVisibility(View.GONE);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#ffffff"));
        cententtxt.setText("签到");
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);


        View pink_view = (View) findViewById(R.id.pink_view);
        StaticData.ViewScale(pink_view, 0, 238);
        pk_xrecy = (XRecyclerView) findViewById(R.id.pk_xrecy);
        pk_xrecy.setLoadingListener(this);//添加事件
        return_Btn.setOnClickListener(new return_Btn());
    }

    private int PageIndex;
    private int pageSize;

    public void initHead(Context context) {
        // 添加头部
        View header = LayoutInflater.from(this).inflate(R.layout.checkin_head, (ViewGroup) findViewById(android.R.id.content), false);
        LinearLayout toal_Lin = (LinearLayout) header.findViewById(R.id.toal_Lin);
        ImageView toal_round_one = (ImageView) header.findViewById(R.id.toal_round_one);
        ImageView toa_round_two = (ImageView) header.findViewById(R.id.toa_round_two);
        RelativeLayout top_Rel = (RelativeLayout) header.findViewById(R.id.top_Rel);
         allday_Txt = (TextView) header.findViewById(R.id.allday_Txt);
        con_Txt = (TextView) header.findViewById(R.id.con_Txt);
         rank_Txt = (TextView) header.findViewById(R.id.rank_Txt);
         rankCount_Txt = (TextView) header.findViewById(R.id.rankCount_Txt);
        TextView below_Txt = (TextView) header.findViewById(R.id.below_Txt);
        TextView ban_Txt = (TextView) header.findViewById(R.id.ban_Txt);
        ImageView round_one = (ImageView) header.findViewById(R.id.round_one);
        ImageView round_two = (ImageView) header.findViewById(R.id.round_two);

        layoutmarginTop(Pk_CheckIn.this, R.id.toal_Lin, top_Rel);
        layoutmarginTop(Pk_CheckIn.this, R.id.top_Rel, below_Txt);
        layoutLeft(Pk_CheckIn.this, R.id.toal_Lin, toal_round_one);
        layoutLeft(Pk_CheckIn.this, R.id.top_Rel, round_one);
        layoutRight(Pk_CheckIn.this, R.id.toal_Lin, toa_round_two);
        layoutRight(Pk_CheckIn.this, R.id.top_Rel, round_two);

        StaticData.ViewScale(toal_Lin, 710, 240);
        StaticData.ViewScale(top_Rel, 710, 152);
        StaticData.ViewScale(below_Txt, 710, 85);
        StaticData.ViewScale(ban_Txt, 710, 35);
        StaticData.ViewScale(round_one, 48, 108);
        StaticData.ViewScale(round_two, 48, 108);
        pk_xrecy.addHeaderView(header);
    }

    //向上间隔
    private void layoutmarginTop(Context context, int viewId, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 30);
        Params.setMargins(0, pad, 0, 0);
        Params.addRule(RelativeLayout.BELOW, viewId);
        view.setLayoutParams(Params);
    }

    //左边圆柱
    private void layoutLeft(Context context, int viewId, View view) {
        RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        StaticData.layoutParamsScale(itemParams, 48, 108);
        int padone = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 16);
        int padtop = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * -40);
        itemParams.addRule(RelativeLayout.BELOW, viewId);
        itemParams.setMargins(padone, padtop, 0, 0);
        view.setLayoutParams(itemParams);
    }

    //右边圆柱
    private void layoutRight(Context context, int viewId, View view) {
        RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        StaticData.layoutParamsScale(itemParams, 48, 108);
        int padone = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 16);
        int padtop = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * -40);
        itemParams.addRule(RelativeLayout.ALIGN_RIGHT, R.id.top_Rel);
        itemParams.addRule(RelativeLayout.BELOW, viewId);
        itemParams.setMargins(0, padtop, padone, 0);
        view.setLayoutParams(itemParams);
    }


    public void TextsColor(int size, int endsize, String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
        int padSize = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_CheckIn.this)) * size);
        styledText.setSpan(new AbsoluteSizeSpan(padSize), 0, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //初始字体大小
        int padEndSize = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_CheckIn.this)) * endsize);
        styledText.setSpan(new AbsoluteSizeSpan(padEndSize), 1, alltext.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //修改字体大小
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#f27b7b")), 1, alltext.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//修改颜色
        myTxt.setText(styledText);
    }

    public void rankCountTextsColor(int size, int endsize, String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
        int padSize = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_CheckIn.this)) * size);
        styledText.setSpan(new AbsoluteSizeSpan(padSize), 0, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //初始字体大小
        int padEndSize = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_CheckIn.this)) * endsize);
        styledText.setSpan(new AbsoluteSizeSpan(padEndSize), 0, alltext.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //修改字体大小
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#f27b7b")), 0, alltext.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//修改颜色
        myTxt.setText(styledText);
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }



    public void initData(Context context) {
        CheckData(context, saveFile.BaseUrl + saveFile.checMyDatakUrl);

        PageIndex = 1;
        pageSize = 10;
        ListData(context, saveFile.BaseUrl + saveFile.checkUrl + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    @Override
    public void onRefresh() {
        PageIndex = 1;
        pageSize = 10;
        ListData(Pk_CheckIn.this, saveFile.BaseUrl + saveFile.checkUrl + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    @Override
    public void onLoadMore() {
        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(Pk_CheckIn.this, saveFile.BaseUrl + saveFile.checkUrl + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }



    pk_CheckIn_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        pk_xrecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        pk_xrecy.setHasFixedSize(true);
        mAdapter = new pk_CheckIn_Adapter(context, baseModel, listModel);
        pk_xrecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new pk_CheckIn_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(context, Energy_WebDetail.class);
//                intent.putExtra("TargetID", baseModel.get(position).getTargetID() + "");
//                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<CheckList_Model.DataBean> baseModel;
    CheckList_Model listModel;

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
                    listModel = new Gson().fromJson(resultString, CheckList_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
                        baseModel.addAll(listModel.getData());
                        if (PageIndex == 1) {
                            pk_xrecy.refreshComplete();
                            initlist(context);
                        } else {
                            pk_xrecy.loadMoreComplete();
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

    public void CheckData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {

                   CheckData_Model baseModel = new Gson().fromJson(resultString, CheckData_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {

                        rankCountTextsColor(28, 72,baseModel.getData().getCheckInDays()+"天",allday_Txt);
                        rankCountTextsColor(28, 72,baseModel.getData().getContinueDays()+"天",con_Txt);
                        TextsColor(28, 72,"第"+baseModel.getData().getEarlyRanking()+"名",rank_Txt);
                        rankCountTextsColor(28, 72,baseModel.getData().getEarlyDays()+"天",rankCount_Txt);
//                        baseModel.addAll(listModel.getData());
//                        if (PageIndex == 1) {
//                            pk_xrecy.refreshComplete();
//                            initlist(context);
//                        } else {
//                            pk_xrecy.loadMoreComplete();
//                            mAdapter.addMoreData(baseModel);
//                        }
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


}
