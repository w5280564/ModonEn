package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Notice_UserList_Model;
import com.moying.energyring.Model.unread_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.Notice_UserList_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.xrecycle.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Person_Notice extends Activity implements XRecyclerView.LoadingListener {

    private RelativeLayout choice_Rel;
    private TextView remind_Txt, mes_Txt,mesunrend_Txt;
    private LinearLayout noticecontent_Lin;
    private TextView fansunrend_Txt, nommunrend_Txt, likeunrend_Txt, noticeunrend_Txt, atmeunrend_Txt;
    private XRecyclerView All_XRecy;
    private String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__notice);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        initView();
        initData();
    }

    private void initView() {
        RelativeLayout title_Rel = (RelativeLayout) findViewById(R.id.title_Rel);
        Button return_Btn = (Button) findViewById(R.id.return_Btn);
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Rel, 0, 88);

        choice_Rel = (RelativeLayout) findViewById(R.id.choice_Rel);
        remind_Txt = (TextView) findViewById(R.id.remind_Txt);
        mes_Txt = (TextView) findViewById(R.id.mes_Txt);
        noticecontent_Lin = (LinearLayout) findViewById(R.id.noticecontent_Lin);
        ImageView fans_icon = (ImageView) findViewById(R.id.fans_icon);
        ImageView nomm_icon = (ImageView) findViewById(R.id.nomm_icon);
        ImageView like_icon = (ImageView) findViewById(R.id.like_icon);
        ImageView notice_iocn = (ImageView) findViewById(R.id.notice_iocn);
        ImageView atme_icon = (ImageView) findViewById(R.id.atme_icon);
        ImageView fans_arrow = (ImageView) findViewById(R.id.fans_arrow);
        ImageView nomm_arrow = (ImageView) findViewById(R.id.nomm_arrow);
        ImageView like_arrow = (ImageView) findViewById(R.id.like_arrow);
        ImageView notice_arrow = (ImageView) findViewById(R.id.notice_arrow);
        ImageView atme_arrow = (ImageView) findViewById(R.id.atme_arrow);
        fansunrend_Txt = (TextView) findViewById(R.id.fansunrend_Txt);
        nommunrend_Txt = (TextView) findViewById(R.id.nommunrend_Txt);
        likeunrend_Txt = (TextView) findViewById(R.id.likeunrend_Txt);
        noticeunrend_Txt = (TextView) findViewById(R.id.noticeunrend_Txt);
        atmeunrend_Txt = (TextView) findViewById(R.id.atmeunrend_Txt);
        RelativeLayout notice_Rel = (RelativeLayout) findViewById(R.id.notice_Rel);
        RelativeLayout fans_Rel = (RelativeLayout) findViewById(R.id.fans_Rel);
        RelativeLayout nomm_Rel = (RelativeLayout) findViewById(R.id.nomm_Rel);
        RelativeLayout like_Rel = (RelativeLayout) findViewById(R.id.like_Rel);
        RelativeLayout atme_Rel = (RelativeLayout) findViewById(R.id.atme_Rel);
         mesunrend_Txt = (TextView) findViewById(R.id.mesunrend_Txt);
        unReadMargin(Person_Notice.this, mesunrend_Txt);

        All_XRecy = (XRecyclerView) findViewById(R.id.All_XRecy);
        All_XRecy.setLoadingListener(this);//添加事件


        StaticData.ViewScale(choice_Rel, 280, 60);
        StaticData.ViewScale(noticecontent_Lin, 710, 750);
        StaticData.ViewScale(fans_icon, 76, 76);
        StaticData.ViewScale(like_icon, 76, 76);
        StaticData.ViewScale(nomm_icon, 76, 76);
        StaticData.ViewScale(notice_iocn, 76, 76);
        StaticData.ViewScale(atme_icon, 76, 76);
        StaticData.ViewScale(fans_arrow, 16, 30);
        StaticData.ViewScale(nomm_arrow, 16, 30);
        StaticData.ViewScale(like_arrow, 16, 30);
        StaticData.ViewScale(notice_arrow, 16, 30);
        StaticData.ViewScale(atme_arrow, 16, 30);
        StaticData.ViewScale(fansunrend_Txt, 36, 36);
        StaticData.ViewScale(nommunrend_Txt, 36, 36);
        StaticData.ViewScale(likeunrend_Txt, 36, 36);
        StaticData.ViewScale(noticeunrend_Txt, 36, 36);
        StaticData.ViewScale(atmeunrend_Txt, 36, 36);
        return_Btn.setOnClickListener(new return_Btn());
        remind_Txt.setOnClickListener(new remind_Txt());
        mes_Txt.setOnClickListener(new mes_Txt());
        notice_Rel.setOnClickListener(new notice_Rel());
        fans_Rel.setOnClickListener(new fans_Rel());
        nomm_Rel.setOnClickListener(new nomm_Rel());
        like_Rel.setOnClickListener(new like_Rel());
        atme_Rel.setOnClickListener(new atme_Rel());
    }

    private int PageIndex;
    private int pageSize;

    @Override
    protected void onResume() {
        super.onResume();
        unreadData(Person_Notice.this, saveFile.BaseUrl + saveFile.Notice_Unread_Url);

        PageIndex = 1;
        pageSize = 10;
        ListData(Person_Notice.this, saveFile.BaseUrl + saveFile.Mess_UserList_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    private void initData() {
        changebg(remind_Txt);
        noticecontent_Lin.setVisibility(View.VISIBLE);
        StaticData.ViewScale(remind_Txt, 160, 0);
        StaticData.ViewScale(mes_Txt, 160, 0);
    }

    private void setinitbg() {
        remind_Txt.setBackgroundResource(0);
        remind_Txt.setTextColor(ContextCompat.getColor(this,R.color.colorSecondBlack));
        mes_Txt.setBackgroundResource(0);
        mes_Txt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
    }

    private void changebg(TextView myView) {
        StaticData.ViewScale(myView, 160, 0);
        myView.setElevation(2f);
        myView.setBackgroundResource(R.drawable.personnotice_choice);
        myView.setTextColor(ContextCompat.getColor(this,R.color.colorFristWhite));
    }

    @Override
    public void onRefresh() {
        onResume();
    }

    @Override
    public void onLoadMore() {
        PageIndex += 1;
        pageSize = 10;
        ListData(Person_Notice.this, saveFile.BaseUrl + saveFile.Mess_UserList_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    private void unReadMargin(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        StaticData.layoutParamsScale(Params, 18, 18);
        Params.addRule(RelativeLayout.ALIGN_TOP, R.id.tab_group);
        Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 30);
        Params.setMargins(0, 0, pad, 0);
        view.setLayoutParams(Params);
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private class remind_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setinitbg();
            changebg(remind_Txt);
            noticecontent_Lin.setVisibility(View.VISIBLE);
            All_XRecy.setVisibility(View.GONE);
        }
    }

    private class mes_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MobclickAgent.onEvent(Person_Notice.this, "MessClick");//统计页签
            setinitbg();
            changebg(mes_Txt);
            noticecontent_Lin.setVisibility(View.GONE);
            All_XRecy.setVisibility(View.VISIBLE);
        }
    }

    //通知
    private class notice_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Notice.this, Person_Notice_NoticeList.class);
            startActivity(intent);
        }
    }

    //新粉丝
    private class fans_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Notice.this, Person_Notice_Fans.class);
            intent.putExtra("UserID", UserID);
            startActivity(intent);
        }
    }

    //评论
    private class nomm_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Notice.this, Person_Notice_Nomm.class);
            startActivity(intent);
        }
    }

    //提到我
    private class atme_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Notice.this, Person_Notice_AtMe.class);
            startActivity(intent);
        }
    }

    //喜欢
    private class like_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Notice.this, Person_Notice_Like.class);
            startActivity(intent);
        }
    }

    Notice_UserList_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能

        All_XRecy.setHasFixedSize(true);
        mAdapter = new Notice_UserList_Adapter(context, baseModel, listModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Notice_UserList_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Person_Notice.this, Person_Notice_Mes.class);
                intent.putExtra("UserID", baseModel.get(position).getUserID() + "");
                intent.putExtra("titleName", baseModel.get(position).getNickName());
                startActivity(intent);
//                Intent intent = new Intent(context, Energy_WebDetail.class);
//                intent.putExtra("TargetID", baseModel.get(position).getTargetID() + "");
//                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    public void unreadData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    unread_Model userModel = new Gson().fromJson(resultString, unread_Model.class);
                    if (userModel.isIsSuccess() && !userModel.getData().equals("[]")) {
                        unread_Model.DataBean oneData = userModel.getData();

                        if (oneData.getNewFriend() > 0) {
                            fansunrend_Txt.setVisibility(View.VISIBLE);
                            fansunrend_Txt.setText(oneData.getNewFriend() + "");
                        } else {
                            fansunrend_Txt.setVisibility(View.INVISIBLE);
                        }

                        if (oneData.getPost_Comment() > 0) {
                            nommunrend_Txt.setVisibility(View.VISIBLE);
                            nommunrend_Txt.setText(oneData.getPost_Comment() + "");
                        } else {
                            nommunrend_Txt.setVisibility(View.INVISIBLE);
                        }

                        if (oneData.getPost_Like() > 0) {
                            likeunrend_Txt.setVisibility(View.VISIBLE);
                            likeunrend_Txt.setText(oneData.getPost_Like() + "");
                        } else {
                            likeunrend_Txt.setVisibility(View.INVISIBLE);
                        }

                        if (oneData.getPost_MenTion() > 0) {
                            atmeunrend_Txt.setVisibility(View.VISIBLE);
                            atmeunrend_Txt.setText(oneData.getPost_MenTion() + "");
                        } else {
                            atmeunrend_Txt.setVisibility(View.INVISIBLE);
                        }

                        if (oneData.getNotice() > 0) {
                            noticeunrend_Txt.setVisibility(View.VISIBLE);
                            noticeunrend_Txt.setText(oneData.getNotice() + "");
                        } else {
                            noticeunrend_Txt.setVisibility(View.INVISIBLE);
                        }

                        if (oneData.getMsg() > 0){
                            mesunrend_Txt.setVisibility(View.VISIBLE);
                            setinitbg();
                            changebg(mes_Txt);
                            noticecontent_Lin.setVisibility(View.GONE);
                            All_XRecy.setVisibility(View.VISIBLE);
                        }else{
                            mesunrend_Txt.setVisibility(View.INVISIBLE);
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

    private List<Notice_UserList_Model.DataBean> baseModel;
    Notice_UserList_Model listModel;

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
                    listModel = new Gson().fromJson(resultString, Notice_UserList_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
                        baseModel.addAll(listModel.getData());
                        if (PageIndex == 1) {
                            All_XRecy.refreshComplete();
                            initlist(context);
                        } else {
                            All_XRecy.loadMoreComplete();
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
