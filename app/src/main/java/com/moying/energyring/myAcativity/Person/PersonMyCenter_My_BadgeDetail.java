package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Person_BadgeAll_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAdapter.PersonAndOther_BadgeDetail_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by waylen on 2018/7/5.
 */

public class PersonMyCenter_My_BadgeDetail extends Activity implements XRecyclerView.LoadingListener {
    private XRecyclerView All_XRecy;
    String UserID, BadgeType;
    private TextView badgeName_Txt, badgeCount_Txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personmycenter_badge_detail);


        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        BadgeType = intent.getStringExtra("BadgeType");
        Button return_Btn = (Button) findViewById(R.id.return_Btn);
        StaticData.ViewScale(return_Btn, 26, 42);

        All_XRecy = (XRecyclerView) findViewById(R.id.All_XRecy);
        All_XRecy.setLoadingListener(this);//添加事件
//        All_XRecy.setPullRefreshEnabled(false);
        All_XRecy.setLoadingMoreEnabled(false);
        All_XRecy.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁


        initAddHeadView(All_XRecy, PersonMyCenter_My_BadgeDetail.this);
        initData(BadgeType);
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
        initData(BadgeType);
    }

    @Override
    public void onLoadMore() {

    }

    View header;

    private void initAddHeadView(XRecyclerView myView, Context context) {
        header = LayoutInflater.from(context).inflate(R.layout.person_badgedetail_head, null);
        LinearLayout.LayoutParams headParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        StaticData.layoutParamsScale(headParams, 0, 380);
        header.setLayoutParams(headParams);
        View line_View = header.findViewById(R.id.line_View);
        badgeName_Txt = (TextView) header.findViewById(R.id.badgeName_Txt);
        badgeCount_Txt = (TextView) header.findViewById(R.id.badgeCount_Txt);

        StaticData.ViewScale(line_View, 6, 120);
//        View content_Lin = header.findViewById(R.id.content_Lin);
//        View badge_xingImg = header.findViewById(R.id.badge_xingImg);
//        TextView my_badgeCount = (TextView) header.findViewById(R.id.my_badgeCount);
//
//        my_badgeCount.setText("获得：" + "23" + "个");
////        StaticData.ViewScale(badge_Rel,0,246);
//        StaticData.ViewScale(content_Lin, 212, 56);
//        StaticData.ViewScale(badge_xingImg, 32, 30);
        myView.addHeaderView(header);
    }

    private void initData(String BadgeType) {
        ListData(PersonMyCenter_My_BadgeDetail.this, saveFile.BaseUrl + saveFile.Badge_List_Url + "?UserID=" + UserID + "&BadgeType=" + BadgeType);
    }


    PersonAndOther_BadgeDetail_Adapter mAdapter;

    public void initlist(final Context context) {
        GridLayoutManager mMangaer = new GridLayoutManager(context, 3);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        mAdapter = new PersonAndOther_BadgeDetail_Adapter(context, listModel.getData().get(0));
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new PersonAndOther_BadgeDetail_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Person_BadgeAll_Model.DataBean.InsigniaListBean oneData =    listModel.getData().get(0).getInsignia_List().get(position);

//                String myHaveName = oneData.getBadgeName();
                String myHaveName = oneData.getCaption();
                String HaveTime = "尚未获得";
                if (oneData.getCreatDate() != null) {
                    HaveTime = oneData.getCreatDate().substring(0, 8).replace("/", "-") + "获得";
                }
                String url = "";
                if (oneData.isIs_Have()) {
                    url = oneData.getFilePath();
                } else {
                    url = oneData.getBadge_Gray().toString();
                }
                String HaveNum = "-已有" + oneData.getHaveNum() + "人获得-";

                new badge_Popup(context, All_XRecy, HaveTime, myHaveName, url,HaveNum,position);
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
                        All_XRecy.refreshComplete();
                        initlist(context);


                        Person_BadgeAll_Model.DataBean oneData = listModel.getData().get(0);
                        if (oneData.getBackgroundColor() != null) {
                            header.setBackgroundColor(Color.parseColor(oneData.getBackgroundColor()));
                        }
                        badgeName_Txt.setText(oneData.getName());

                        String CountStr = "已获得：%1$d/%2$d个";
                        String Count = String.format(CountStr, oneData.getFinishBadgeCount(), oneData.getBadgeCount());
                        badgeCount_Txt.setText(Count);
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


    public class badge_Popup extends BasePopupWindow {
        public badge_Popup(final Context mContext, View parent, String HaveTime, String myHaveName, String url,String HaveNum,int pos) {
            super(mContext);
//            setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
//            setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
            setTouchable(true);
            View contentView = View.inflate(mContext, R.layout.popup_person_badgedetail, null);
            setContentView(contentView);
            showAtLocation(contentView, Gravity.CENTER, 0, 0);

            RelativeLayout badgePopup_Rel = (RelativeLayout) contentView.findViewById(R.id.badgePopup_Rel);
            LinearLayout badge_Lin = (LinearLayout) contentView.findViewById(R.id.badge_Lin);
            SimpleDraweeView badge_Img = (SimpleDraweeView) contentView.findViewById(R.id.badge_Img);
            ImageView badge_chaImg = (ImageView) contentView.findViewById(R.id.badge_chaImg);
            TextView badge_Txt = (TextView) contentView.findViewById(R.id.badge_Txt);
            TextView badgeTime_Txt = (TextView) contentView.findViewById(R.id.badgeTime_Txt);
            TextView have_Txt = (TextView) contentView.findViewById(R.id.have_Txt);
            LinearLayout share_Lin = (LinearLayout) contentView.findViewById(R.id.share_Lin);
            Button share_Btn = (Button) contentView.findViewById(R.id.share_Btn);
            StaticData.ViewScale(badgePopup_Rel, 550, 850);
            StaticData.ViewScale(badge_Lin, 0, 460);
            StaticData.ViewScale(badge_Img, 390, 390);
            StaticData.ViewScale(badge_chaImg, 35, 35);
            StaticData.ViewScale(share_Btn, 390,60);
            viewTouchDelegate.expandViewTouchDelegate(badge_chaImg, 100, 100, 100, 100);
            badge_Img.setImageURI(url);
            badge_Txt.setText(myHaveName);
            badgeTime_Txt.setText(HaveTime);
            have_Txt.setText(HaveNum);

            have_Txt.setVisibility(View.VISIBLE);
            share_Lin.setVisibility(View.GONE);

            final Person_BadgeAll_Model.DataBean.InsigniaListBean oneData = listModel.getData().get(0).getInsignia_List().get(pos);
            if (oneData.isIs_Have()){
                have_Txt.setVisibility(View.GONE);
                share_Lin.setVisibility(View.VISIBLE);
            }
//            share_Lin.setVisibility(View.VISIBLE);
            badge_chaImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            share_Btn.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    Intent intent = new Intent(mContext,PersonMyCenter_My_BadgeDetail_Share.class);
                    intent.putExtra("ShareText",oneData.getShareText());
                    intent.putExtra("Badge_Path",oneData.getFilePath());
                    intent.putExtra("Caption",oneData.getCaption());
                    startActivity(intent);
                }
            });
        }
    }

}
