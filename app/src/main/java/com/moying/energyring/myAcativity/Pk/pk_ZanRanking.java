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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.ZanList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.pk_ZanRanking_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class pk_ZanRanking extends Activity implements XRecyclerView.LoadingListener {
    private XRecyclerView pk_xrecy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk__zan_ranking);

        initView();
        initHead(pk_ZanRanking.this);
        initData(pk_ZanRanking.this);
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("pk_ZanRanking"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("pk_ZanRanking"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }


    private int PageIndex;
    private int pageSize;

    public void initView() {
        View title_Include = findViewById(R.id.title_Include);
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_icon);
//        return_Btn.setVisibility(View.GONE);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#ffffff"));
        cententtxt.setText("获赞排名");
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);

        View pink_view = (View) findViewById(R.id.pink_view);
        StaticData.ViewScale(pink_view,0,176);
        pk_xrecy = (XRecyclerView) findViewById(R.id.pk_xrecy);
        pk_xrecy.setLoadingListener(this);//添加事件
        return_Btn.setOnClickListener(new return_Btn());
    }

    public void initHead(Context context) {
        Intent intent = getIntent();
        String rankIng = intent.getStringExtra("rankIng");
        String rankIngCount = intent.getStringExtra("rankIngCount");
        // 添加头部
        View header = LayoutInflater.from(this).inflate(R.layout.pkranking_head, (ViewGroup) findViewById(android.R.id.content), false);
        RelativeLayout top_Rel = (RelativeLayout) header.findViewById(R.id.top_Rel);
        TextView rank_Txt = (TextView) header.findViewById(R.id.rank_Txt);
        TextView rankCount_Txt = (TextView) header.findViewById(R.id.rankCount_Txt);
        TextView below_Txt = (TextView) header.findViewById(R.id.below_Txt);
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 30);
        Params.setMargins(0, pad, 0, 0);
        Params.addRule(RelativeLayout.BELOW, R.id.top_Rel);
        below_Txt.setLayoutParams(Params);
        View ban_view = (View) header.findViewById(R.id.ban_view);
        ImageView round_one = (ImageView) header.findViewById(R.id.round_one);
        RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int padone = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 16);
        int padtop = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * -40);
        itemParams.addRule(RelativeLayout.BELOW, R.id.top_Rel);
        itemParams.setMargins(padone, padtop, 0, 0);
        round_one.setLayoutParams(itemParams);
        ImageView round_two = (ImageView) header.findViewById(R.id.round_two);
        RelativeLayout.LayoutParams itemParamstwo = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        itemParamstwo.addRule(RelativeLayout.ALIGN_RIGHT, R.id.top_Rel);
        itemParamstwo.addRule(RelativeLayout.BELOW, R.id.top_Rel);
        itemParamstwo.setMargins(0, padtop, padone, 0);
        round_two.setLayoutParams(itemParamstwo);
        StaticData.ViewScale(top_Rel, 710, 152);
        StaticData.ViewScale(below_Txt, 710, 85);
        StaticData.ViewScale(ban_view, 710, 15);
        StaticData.ViewScale(round_one, 48, 108);
        StaticData.ViewScale(round_two, 48, 108);
        pk_xrecy.addHeaderView(header);

        TextsColor(28, 72, "第" + rankIng + "名", rank_Txt);
        rankCountTextsColor(28, 72, rankIngCount + "赞", rankCount_Txt);
    }

    public void TextsColor(int size, int endsize, String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
        int padSize = (int) (Float.parseFloat(saveFile.getShareData("scale", pk_ZanRanking.this)) * size);
        styledText.setSpan(new AbsoluteSizeSpan(padSize), 0, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //初始字体大小
        int padEndSize = (int) (Float.parseFloat(saveFile.getShareData("scale", pk_ZanRanking.this)) * endsize);
        styledText.setSpan(new AbsoluteSizeSpan(padEndSize), 1, alltext.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //修改字体大小
        myTxt.setText(styledText);
    }

    public void rankCountTextsColor(int size, int endsize, String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
        int padSize = (int) (Float.parseFloat(saveFile.getShareData("scale", pk_ZanRanking.this)) * size);
        styledText.setSpan(new AbsoluteSizeSpan(padSize), 0, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //初始字体大小
        int padEndSize = (int) (Float.parseFloat(saveFile.getShareData("scale", pk_ZanRanking.this)) * endsize);
        styledText.setSpan(new AbsoluteSizeSpan(padEndSize), 0, alltext.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //修改字体大小
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#f27b7b")),0,alltext.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//修改颜色
        myTxt.setText(styledText);
    }


    public void initData(Context context) {
        PageIndex = 1;
        pageSize = 10;
        ListData(context, saveFile.BaseUrl + saveFile.rankUrl + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }
    @Override
    public void onRefresh() {
        PageIndex = 1;
        pageSize = 10;
        ListData(pk_ZanRanking.this, saveFile.BaseUrl + saveFile.rankUrl + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    @Override
    public void onLoadMore() {
        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(pk_ZanRanking.this, saveFile.BaseUrl + saveFile.rankUrl + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    public class return_Btn implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    pk_ZanRanking_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        pk_xrecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        pk_xrecy.setHasFixedSize(true);
        mAdapter = new pk_ZanRanking_Adapter(context, baseModel, listModel);
        pk_xrecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new pk_ZanRanking_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(context, Energy_WebDetail.class);
//                intent.putExtra("TargetID", baseModel.get(position).getTargetID() + "");
//                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position)  {
            }
        });
    }


    private List<ZanList_Model.DataBean> baseModel;
    ZanList_Model listModel;

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
                    listModel = new Gson().fromJson(resultString, ZanList_Model.class);
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
