package com.moying.energyring.myAcativity.Find;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mob.tools.utils.UIHandler;
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.Model.Recommend_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Energy.Energy_WebDetail;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAdapter.GrowthLogFragment_Adapter;
import com.moying.energyring.myAdapter.HeadAfter_Adapter;
import com.moying.energyring.myAdapter.HeadBefo_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class FindSeekActivity extends Activity implements XRecyclerView.LoadingListener, PlatformActionListener, Handler.Callback {
    private XRecyclerView find_recy;
    private int PageIndex;
    private int pageSize;
    private EditText seek_Edit;
    private RecyclerView head_recy;
    private LinearLayout findfriend_Lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_seek);
        setView();
        initView();
        initData();
    }

    public void setView() {
        setTheme(R.style.MyDialog);
//        setContentView(getIntent().getIntExtra("view", R.layout.activity_find_seek));
//        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
////        layoutParams.gravity = Gravity.BOTTOM;
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        getWindow().setAttributes(layoutParams);
    }

    private void initView() {
        find_recy = (XRecyclerView) findViewById(R.id.find_recy);
        find_recy.setLoadingListener(this);
        initAddListHead(find_recy);
        seek_Edit = (EditText) findViewById(R.id.seek_Edit);
        TextView cancel_Txt = (TextView) findViewById(R.id.cancel_Txt);
        StaticData.ViewScale(seek_Edit, 646, 52);
        StaticData.ViewScale(cancel_Txt, 90, 88);
        seek_Edit.addTextChangedListener(new seek_Edit());
        cancel_Txt.setOnClickListener(new cancel_Txt());

//        StaticData.changeXRecycleHeadGif(find_recy,R.drawable.gif_sun_icon,500,200);
    }

    private void initAddListHead(XRecyclerView myView) {
        View header = LayoutInflater.from(FindSeekActivity.this).inflate(R.layout.findseek_head, (ViewGroup) findViewById(android.R.id.content), false);
        head_recy = (RecyclerView) header.findViewById(R.id.head_recy);
        head_recy.setFocusable(false);//一定要在java文件中添加

        findfriend_Lin = (LinearLayout) header.findViewById(R.id.findfriend_Lin);
        RelativeLayout phoneadress_Rel = (RelativeLayout) header.findViewById(R.id.phoneadress_Rel);
        RelativeLayout friend_Rel = (RelativeLayout) header.findViewById(R.id.friend_Rel);
        RelativeLayout wechat_Rel = (RelativeLayout) header.findViewById(R.id.wechat_Rel);
        RelativeLayout sina_Rel = (RelativeLayout) header.findViewById(R.id.sina_Rel);
        RelativeLayout qq_Rel = (RelativeLayout) header.findViewById(R.id.qq_Rel);
        RelativeLayout qzone_Rel = (RelativeLayout) header.findViewById(R.id.qzone_Rel);
        ImageView phoneadress_img = (ImageView) header.findViewById(R.id.phoneadress_img);
        ImageView friend_img = (ImageView) header.findViewById(R.id.friend_img);
        ImageView wechat_img = (ImageView) header.findViewById(R.id.wechat_img);
        ImageView sina_img = (ImageView) header.findViewById(R.id.sina_img);
        ImageView qq_img = (ImageView) header.findViewById(R.id.qq_img);
        ImageView qzone_img = (ImageView) header.findViewById(R.id.qzone_img);
        ImageView phoneadress_arrow = (ImageView) header.findViewById(R.id.phoneadress_arrow);
        ImageView friend_arrow = (ImageView) header.findViewById(R.id.friend_arrow);
        ImageView wechat_arrow = (ImageView) header.findViewById(R.id.wechat_arrow);
        ImageView sina_arrow = (ImageView) header.findViewById(R.id.sina_arrow);
        ImageView qq_arrow = (ImageView) header.findViewById(R.id.qq_arrow);
        ImageView qzone_arrow = (ImageView) header.findViewById(R.id.qzone_arrow);

        StaticData.ViewScale(phoneadress_Rel, 0, 128);
        StaticData.ViewScale(friend_Rel, 0, 128);
        StaticData.ViewScale(wechat_Rel, 0, 128);
        StaticData.ViewScale(sina_Rel, 0, 128);
        StaticData.ViewScale(qq_Rel, 0, 128);
        StaticData.ViewScale(qzone_Rel, 0, 128);
        StaticData.ViewScale(phoneadress_img, 96, 96);
        StaticData.ViewScale(friend_img, 96, 96);
        StaticData.ViewScale(wechat_img, 96, 96);
        StaticData.ViewScale(sina_img, 96, 96);
        StaticData.ViewScale(qq_img, 96, 96);
        StaticData.ViewScale(qzone_img, 96, 96);
        StaticData.ViewScale(phoneadress_arrow, 16, 30);
        StaticData.ViewScale(friend_arrow, 16, 30);
        StaticData.ViewScale(wechat_arrow, 16, 30);
        StaticData.ViewScale(sina_arrow, 16, 30);
        StaticData.ViewScale(qq_arrow, 16, 30);
        StaticData.ViewScale(qzone_arrow, 16, 30);
        myView.addHeaderView(header);

        friend_Rel.setOnClickListener(new share_friend());
        wechat_Rel.setOnClickListener(new share_mom());
        sina_Rel.setOnClickListener(new share_sina());
        qq_Rel.setOnClickListener(new share_qq());
        qzone_Rel.setOnClickListener(new share_qzone());
    }

    private String SearchKey = "";
    int indexHead;
    int indexSixe;

    private void initData() {
        addHeadData();
        addListData();
    }

    private void addHeadData() {
        indexHead = 1;
        indexSixe = 10;
        seekData(saveFile.BaseUrl + saveFile.Recommend_User_Url + "?PageIndex=" + indexHead + "&PageSize=" + indexSixe);
    }

    private void addListData() {
        PageIndex = 1;
        pageSize = 10;
        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=6&PageIndex=" + PageIndex + "&PageSize=" + pageSize + "&SearchKey=" + SearchKey);
    }

    private void afterData() {//
        indexHead = 1;
        indexSixe = 20;
        seekData(saveFile.BaseUrl + saveFile.seekUser_Url + "?PageIndex=" + indexHead + "&PageSize=" + indexSixe + "&SearchKey=" + SearchKey);
    }

    @Override
    public void onRefresh() {
        if (StaticData.Stringspace(SearchKey)){
            addHeadData();
        }else{
            afterData();
        }
        addListData();
    }

    @Override
    public void onLoadMore() {
        PageIndex += 1;
        pageSize = 10;
        ListData(saveFile.BaseUrl + saveFile.EnergyListUrl + "?Type=6&PageIndex=" + PageIndex + "&PageSize=" + pageSize + "&SearchKey=" + SearchKey);
    }

    private class seek_Edit implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (StaticData.Stringspace(editable.toString())) {
                findfriend_Lin.setVisibility(View.VISIBLE);//分享
                head_recy.setBackgroundResource(0);//列表背景框
                SearchKey = "";
                addHeadData();
                addListData();
            } else {
                findfriend_Lin.setVisibility(View.GONE);
                head_recy.setBackgroundResource(R.drawable.popupwhite_bg);
                SearchKey = editable.toString();
                afterData();
                addListData();
            }
        }
    }

    private class cancel_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    public class share_friend implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            shareWechatQuan();
        }
    }

    public class share_mom implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            shareWechatFriend();
        }
    }

    public class share_sina implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            shareSina();
        }
    }

    public class share_qq implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            share_qq();
        }
    }

    public class share_qzone implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            share_qzone();
        }
    }


    GrowthLogFragment_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        find_recy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        find_recy.setHasFixedSize(true);
        mAdapter = new GrowthLogFragment_Adapter(context, baseModel, listModel);
        find_recy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new com.moying.energyring.myAdapter.GrowthLogFragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, Energy_WebDetail.class);
//                intent.putExtra("TargetID", baseModel.get(position).getTargetID() + "");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }

    HeadBefo_Adapter BefomAdapter;

    public void initBefoHead(final Context context) {
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        itemParams.gravity = Gravity.CENTER_VERTICAL;
        head_recy.setLayoutParams(itemParams);
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        mMangaer.setOrientation(LinearLayoutManager.HORIZONTAL);
        head_recy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        head_recy.setHasFixedSize(true);
        BefomAdapter = new HeadBefo_Adapter(context, seekListModel, seekModel);
        head_recy.setAdapter(BefomAdapter);
        BefomAdapter.setOnItemClickLitener(new HeadBefo_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }

    HeadAfter_Adapter AftermAdapter;
    public void initAfterHead(final Context context) {
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        itemParams.gravity = Gravity.CENTER_HORIZONTAL;
        head_recy.setLayoutParams(itemParams);
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        mMangaer.setOrientation(LinearLayoutManager.VERTICAL);
        head_recy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        head_recy.setHasFixedSize(true);
        AftermAdapter = new HeadAfter_Adapter(context, seekListModel, seekModel);
        head_recy.setAdapter(AftermAdapter);
        AftermAdapter.setOnItemClickLitener(new HeadAfter_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<EnergyList_Model.DataBean> baseModel;
    EnergyList_Model listModel;

    public void ListData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
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
                    listModel = new Gson().fromJson(resultString, EnergyList_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
                        baseModel.addAll(listModel.getData());
                        if (PageIndex == 1) {
                            find_recy.refreshComplete();
                            initlist(FindSeekActivity.this);
                        } else {
                            find_recy.loadMoreComplete();
                            mAdapter.addMoreData(baseModel);
                        }
                    } else {
                        Toast.makeText(FindSeekActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(FindSeekActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
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

    private List<Recommend_Model.DataBean> seekListModel;
    Recommend_Model seekModel;

    public void seekData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    if (indexHead == 1) {
                        if (seekListModel != null) {
                            seekListModel.clear();
                        }
                        seekListModel = new ArrayList<>();
                    }
                    seekModel = new Gson().fromJson(resultString, Recommend_Model.class);
                    if (seekModel.isIsSuccess() && !seekModel.getData().equals("[]")) {
                        seekListModel.addAll(seekModel.getData());
                        if (StaticData.Stringspace(seek_Edit.getText().toString())) {//搜索是否是空
                            if (indexHead == 1) {
                                initBefoHead(FindSeekActivity.this);
                            } else {

                            }
                        } else {
                            if (indexHead == 1) {
                                initAfterHead(FindSeekActivity.this);
                            } else {

                            }
                        }

                    } else {
                        Toast.makeText(FindSeekActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(FindSeekActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(FindSeekActivity.this, LoginRegister.class);
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


    private String shareTitle = "能量圈";
    private String shareContent = "能量圈";
    private String shareUrl = "http://172.16.0.111/Share/PostDetails";

    //分享给微信朋友
    public void shareWechatFriend() {
        ShareSDK.initSDK(this);
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle(shareTitle);
        wechat.setText(shareContent);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        msg.thumbData = Util.bmpToByteArray(bitmap, true);
//        InputStream is = getResources().openRawResource(R.drawable.ring_icon);
//        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        wechat.setImageData(bitmap);
        wechat.setUrl(shareUrl);
//        wechat.setImageData(wechatbit);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(this, Wechat.NAME);
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);
    }


    //分享-微信朋友圈
    public void shareWechatQuan() {
        ShareSDK.initSDK(this);
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle(shareTitle);
        wechat.setText(shareContent);
//        InputStream is = getResources().openRawResource(R.drawable.ring_icon);
//        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        wechat.setImageData(bitmap);
        wechat.setUrl(shareUrl);
//        wechat.setImageData(wechatfriendbit);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(this, WechatMoments.NAME);
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);
    }

    //分享-新浪微博
    public void shareSina() {
        ShareSDK.initSDK(this);
        SinaWeibo.ShareParams sina = new SinaWeibo.ShareParams();
        sina.setText(shareTitle + shareUrl);
//        sina.setImagePath(sianimg);
        Platform sinap = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        sinap.setPlatformActionListener(this);
//        sinap.SSOSetting(true);//审核未通过
        sinap.share(sina);
    }

    public void share_qq() {
        ShareSDK.initSDK(this);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(shareTitle);
        sp.setTitleUrl(shareUrl); // 标题的超链接
        sp.setText(shareContent);
//        sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
//        sp.setSite("发布分享的网站名称");
//        sp.setSiteUrl("发布分享网站的地址");
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(this); // 设置分享事件回调
        qq.share(sp);
    }

    public void share_qzone() {
        ShareSDK.initSDK(this);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(shareTitle);
        sp.setTitleUrl(shareUrl);
        sp.setText(shareContent);
        sp.setSite("能量圈");//分享应用的名称
        sp.setSiteUrl("https://www.baidu.com/");//分享应用的网页地址
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(this); // 设置分享事件回调
        qzone.share(sp);
    }


    /*
    *
    *分享事件回调
    *
    *
    * */
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                // 成功
                Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
            }
            break;
            case 2: {
                // 失败
//					Toast.makeText(HomePage_NewsMan_XiangQing.this,"分享失败", 10000).show();
                Toast.makeText(this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
            break;
            case 3: {
                // 取消
//                Toast.makeText(this, "分享取消", Toast.LENGTH_SHORT).show();
            }
            break;
        }

        return false;
    }

    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;

    @Override
    public void onCancel(Platform arg0, int arg1) {
        // 取消
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = arg1;
        msg.obj = arg0;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
        // 成功
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = arg1;
        msg.obj = arg2;
        UIHandler.sendMessage(msg, this);

    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {
        // 失敗
        //打印错误信息,print the error msg
        arg2.printStackTrace();
        //错误监听,handle the error msg
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = arg1;
        msg.obj = arg2;
        UIHandler.sendMessage(msg, this);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
