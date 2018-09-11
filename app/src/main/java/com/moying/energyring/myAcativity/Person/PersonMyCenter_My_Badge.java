package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Person_BadgeAll_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAdapter.PersonMyCenter_AndOther_BadgeAll_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class PersonMyCenter_My_Badge extends Activity implements XRecyclerView.LoadingListener {

    private XRecyclerView All_XRecy;
    private String UserID, FilePath;
    private Button return_Btn;
    private SimpleDraweeView my_Head;
    private TextView my_badgeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personmycenter_andother_badge);

        return_Btn = (Button) findViewById(R.id.return_Btn);
        StaticData.ViewScale(return_Btn, 80, 88);
        All_XRecy = (XRecyclerView) findViewById(R.id.All_XRecy);
        All_XRecy.setLoadingListener(this);//添加事件
        All_XRecy.setLoadingMoreEnabled(false);

        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        FilePath = intent.getStringExtra("FilePath");

        initAddHeadView(All_XRecy, PersonMyCenter_My_Badge.this);
        initData(0);
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
        initData(0);
    }

    @Override
    public void onLoadMore() {
    }

    private void initAddHeadView(XRecyclerView myView, Context context) {
        View header = LayoutInflater.from(context).inflate(R.layout.person_badge_head, null);
        LinearLayout.LayoutParams headParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int magTop = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 88);
        headParams.setMargins(0, magTop, 0, 0);
        StaticData.layoutParamsScale(headParams, 0, 248);
        header.setLayoutParams(headParams);
//        View badge_Rel = header.findViewById(R.id.badge_Rel);
        my_Head = (SimpleDraweeView) header.findViewById(R.id.my_Head);
        View content_Lin = header.findViewById(R.id.content_Lin);
        View badge_xingImg = header.findViewById(R.id.badge_xingImg);
        my_badgeCount = (TextView) header.findViewById(R.id.my_badgeCount);

//        my_badgeCount.setText("获得："+"23" +"个");
//        StaticData.ViewScale(badge_Rel,0,246);
        StaticData.ViewScale(my_Head, 130, 130);
        StaticData.ViewScale(content_Lin, 212, 56);
        StaticData.ViewScale(badge_xingImg, 32, 30);
        myView.addHeaderView(header);
    }


    private void initData(int BadgeType) {
        ListData(PersonMyCenter_My_Badge.this, saveFile.BaseUrl + saveFile.Badge_List_Url + "?UserID=" + UserID + "&BadgeType=" + BadgeType);
    }


    PersonMyCenter_AndOther_BadgeAll_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        mAdapter = new PersonMyCenter_AndOther_BadgeAll_Adapter(context, listModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new PersonMyCenter_AndOther_BadgeAll_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(PersonMyCenter_My_Badge.this, PersonMyCenter_My_BadgeDetail.class);
                intent.putExtra("BadgeType", listModel.getData().get(position).getTypeID() + "");
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    Person_BadgeAll_Model listModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    listModel = new Gson().fromJson(resultString, Person_BadgeAll_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {

                        if (FilePath != null) {
                            Uri imgUri = Uri.parse(FilePath);
                            my_Head.setImageURI(imgUri);
                        } else {
                            StaticData.lodingheadBg(my_Head);
                        }

                        int finshCount = 0;
                        for (int i = 0; i <listModel.getData().size();i++){
                            finshCount = finshCount+listModel.getData().get(i).getFinishBadgeCount();
                        }
                            my_badgeCount.setText("获得：" + finshCount+ "个");

                        All_XRecy.refreshComplete();
                        initlist(context);
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



