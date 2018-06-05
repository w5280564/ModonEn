package com.moying.energyring.myAcativity.Pk.Committ;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.AllPersonHostory_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Leran_AllHostory extends Activity {
    int PageIndex;
    int pageSize;
    XRecyclerView All_XRecy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leran__all_hostory);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#2b2a2a"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#ffffff"));
        cententtxt.setText("历史目标");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

         All_XRecy = (XRecyclerView)findViewById(R.id.All_XRecy);
        All_XRecy.setPullRefreshEnabled(false);
        All_XRecy.setLoadingMoreEnabled(false);

        return_Btn.setOnClickListener(new return_Btn());

        PageIndex = 1;
        pageSize = 10;
        personData(saveFile.BaseUrl + saveFile.Target_ListUrl + "?Type=2&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
//        personData(saveFile.BaseUrl + "/Target/My_Targrt_List?Type=2&UserID=" + userID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        AllPersonHostory_Adapter mAdapter = new AllPersonHostory_Adapter(context,baseModel, personModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new AllPersonHostory_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<AllPerson_Model.DataBean> baseModel;
    AllPerson_Model personModel;
    public void personData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
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

                    personModel = new Gson().fromJson(resultString, AllPerson_Model.class);
                    baseModel.addAll(personModel.getData());
                    if (personModel.isIsSuccess() && ! personModel.getData().equals("[]")) {
                        if (PageIndex == 1) {
//                            All_XRecy.refreshComplete();
                            initlist(Leran_AllHostory.this);
                        } else {
//                            All_XRecy.loadMoreComplete();
//                            mAdapter.addMoreData(baseModel);
                        }
                    }else{
                        Toast.makeText(Leran_AllHostory.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Leran_AllHostory.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")){
                    Intent intent = new Intent(Leran_AllHostory.this,MainLogin.class);
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
