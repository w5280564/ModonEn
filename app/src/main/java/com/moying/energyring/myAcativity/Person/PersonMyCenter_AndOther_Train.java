package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.PersonAndOther_Train_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Pk.Training.TrainingTodaySet;
import com.moying.energyring.myAdapter.PersonMyCenter_AndOther_Train_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class PersonMyCenter_AndOther_Train extends Activity implements XRecyclerView.LoadingListener {

    private XRecyclerView All_XRecy;
    private int PageIndex;
    private int pageSize;
    private String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basexrecy_hastiitle);

        initTitle();
        View has_Lin =  findViewById(R.id.has_Lin);
//        has_Lin.setBackgroundColor(Color.parseColor("#f6f6f6"));
        All_XRecy = (XRecyclerView) findViewById(R.id.All_XRecy);
        All_XRecy.setLoadingListener(this);//添加事件
//        initAddHeadView(All_XRecy, Person_Exchange.this);
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        initData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("参加训练");
//        right_Btn.setBackgroundResource(R.drawable.personnew_idea_icon);
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }


    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
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
        ListData(PersonMyCenter_AndOther_Train.this, saveFile.BaseUrl + saveFile.TrainList_Url + "?UserID=" + UserID+ "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    private void initData() {
        PageIndex = 1;
        pageSize = 10;
        ListData(PersonMyCenter_AndOther_Train.this, saveFile.BaseUrl + saveFile.TrainList_Url+ "?UserID=" + UserID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    PersonMyCenter_AndOther_Train_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        mAdapter = new PersonMyCenter_AndOther_Train_Adapter(context, baseModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new PersonMyCenter_AndOther_Train_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(PersonMyCenter_AndOther_Train.this,Person_Exchange_Detail.class);
//                intent.putExtra("baseModel", baseModel.get(position));
//                startActivity(intent);

                Intent intent1 = new Intent(context, TrainingTodaySet.class);
                intent1.putExtra("ProjectID", baseModel.get(position).getProjectID() + "");
                startActivity(intent1);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<PersonAndOther_Train_Model.DataBean.TarinListBean> baseModel;
    PersonAndOther_Train_Model listModel;

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
                    listModel = new Gson().fromJson(resultString, PersonAndOther_Train_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
                        if (PageIndex == 1) {
                            baseModel.addAll(listModel.getData().getTarin_List());
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