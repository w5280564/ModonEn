package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Recommend_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.Person_NoticeFans_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Person_Notice_Fans extends Activity implements XRecyclerView.LoadingListener {
    private XRecyclerView All_XRecy;
    private int PageIndex;
    private int pageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basexrecy_hastiitle);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();

        All_XRecy = (XRecyclerView) findViewById(R.id.All_XRecy);
//        All_XRecy.setBackgroundResource(R.drawable.popupwhite_bg);
        All_XRecy.setLoadingListener(this);//添加事件
        All_XRecy.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁

        initData();

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
        cententtxt.setText("新粉丝");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
//        right_Btn.setOnClickListener(new right_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
    String UserID;
    private void initData() {
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        PageIndex = 1;
        pageSize = 10;
//        seekData(Person_Notice_Nomm.this,saveFile.BaseUrl + saveFile.seekUser_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize + "&SearchKey=" + "S");
        fansData(Person_Notice_Fans.this, saveFile.BaseUrl + saveFile.Notice_Attention_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize+"&ToUserID="+UserID+"&Type=1");
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {
        PageIndex += 1;
        pageSize = 10;
//        ListData(saveFile.BaseUrl + saveFile. + "?Type=1&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
        fansData(Person_Notice_Fans.this, saveFile.BaseUrl + saveFile.Notice_Attention_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize+"&ToUserID="+UserID+"&Type=1");
    }

    Person_NoticeFans_Adapter mAdapter;

    public void initlist(final Context context) {
//        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        itemParams.gravity = Gravity.CENTER_HORIZONTAL;
//        All_XRecy.setLayoutParams(itemParams);
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        mMangaer.setOrientation(LinearLayoutManager.VERTICAL);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        mAdapter = new Person_NoticeFans_Adapter(context, seekListModel, seekModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Person_NoticeFans_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<Recommend_Model.DataBean> seekListModel;
    Recommend_Model seekModel;

    public void fansData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    if (PageIndex == 1) {
                        if (seekListModel != null) {
                            seekListModel.clear();
                        }
                        seekListModel = new ArrayList<>();
                    }
                    seekModel = new Gson().fromJson(resultString, Recommend_Model.class);
                    if (seekModel.isIsSuccess() && !seekModel.getData().equals("[]")) {
                        seekListModel.addAll(seekModel.getData());
                        if (PageIndex == 1) {
                            All_XRecy.refreshComplete();
                            initlist(context);
                        } else {
                            All_XRecy.loadMoreComplete();
                            mAdapter.addMoreData(seekListModel);
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
