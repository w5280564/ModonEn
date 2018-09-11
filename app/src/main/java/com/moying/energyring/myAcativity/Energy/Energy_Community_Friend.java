package com.moying.energyring.myAcativity.Energy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mob.tools.utils.UIHandler;
import com.moying.energyring.Model.Recommend_Model;
import com.moying.energyring.Model.ShareContent;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.CommunityFriend_Adapter;
import com.moying.energyring.myAdapter.CommunityFriend_Seek_Adapter;
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
import cn.sharesdk.wechat.friends.Wechat;

public class Energy_Community_Friend extends Activity implements XRecyclerView.LoadingListener, PlatformActionListener, Handler.Callback {

    private XRecyclerView All_XRecy;
    private int PageIndex;
    private int pageSize;
    private String SearchKey = "";
    private EditText seek_Edit;
    private LinearLayout findfriend_Lin;
    private ShareContent shareContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.energy_community_friend);

        initTitle();
        initView();
        initData(this);


        String shareTitle = "我正在使用能量圈，快来和我一起培养新习惯吧~";
        String shareConStr = "能量圈--为了打造更好的自己";
        String shareUrl = saveFile.BaseUrl + "Share/Invite?invitecode=" + saveFile.getShareData("InviteCode", this);
        shareContent = new ShareContent(shareUrl, shareTitle, shareConStr, shareUrl);
    }


    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this, R.color.colorFristWhite));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this, R.color.colorFristBlack));
        cententtxt.setText("添加好友");
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

    private void initView() {
        LinearLayout seek_Lin = (LinearLayout) findViewById(R.id.seek_Lin);
        seek_Edit = (EditText) findViewById(R.id.seek_Edit);
        View seek_Img = findViewById(R.id.seek_Img);
        All_XRecy = (XRecyclerView) findViewById(R.id.All_XRecy);
        All_XRecy.setLoadingListener(this);

        StaticData.ViewScale(seek_Lin, 0, 60);
        StaticData.ViewScale(seek_Img, 22, 24);

        initAddListHead(All_XRecy);
        seek_Edit.addTextChangedListener(new seek_Edit());
    }

    private void initAddListHead(XRecyclerView myView) {
        View header = LayoutInflater.from(this).inflate(R.layout.community_friend_head, (ViewGroup) findViewById(android.R.id.content), false);
//        head_recy = (RecyclerView) header.findViewById(R.id.head_recy);
//        head_recy.setFocusable(false);//一定要在java文件中添加
//        head_recy.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁

        findfriend_Lin = (LinearLayout) header.findViewById(R.id.findfriend_Lin);

        LinearLayout phoneadress_Lin = (LinearLayout) header.findViewById(R.id.phoneadress_Lin);
        LinearLayout wechat_Lin = (LinearLayout) header.findViewById(R.id.wechat_Lin);
        LinearLayout sina_Lin = (LinearLayout) header.findViewById(R.id.sina_Lin);
        LinearLayout qq_Lin = (LinearLayout) header.findViewById(R.id.qq_Lin);
        ImageView phoneadress_img = (ImageView) header.findViewById(R.id.phoneadress_img);
        ImageView wechat_img = (ImageView) header.findViewById(R.id.wechat_img);
        ImageView sina_img = (ImageView) header.findViewById(R.id.sina_img);
        ImageView qq_img = (ImageView) header.findViewById(R.id.qq_img);

        StaticData.ViewScale(phoneadress_Lin, 0, 128);
        StaticData.ViewScale(wechat_Lin, 0, 128);
        StaticData.ViewScale(sina_Lin, 0, 128);
        StaticData.ViewScale(qq_Lin, 0, 128);
        StaticData.ViewScale(phoneadress_img, 80, 80);
        StaticData.ViewScale(wechat_img, 80, 80);
        StaticData.ViewScale(sina_img, 80, 80);
        StaticData.ViewScale(qq_img, 80, 80);
        myView.addHeaderView(header);

        phoneadress_Lin.setOnClickListener(new phoneadress_Lin());
        wechat_Lin.setOnClickListener(new wechat_Lin());
        sina_Lin.setOnClickListener(new sina_Lin());
        qq_Lin.setOnClickListener(new qq_Lin());
    }

    public class phoneadress_Lin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Energy_Community_Friend.this,Energy_CommunityFriend_Invite.class);
            startActivity(intent);

        }
    }

    public class wechat_Lin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            shareWechatFriend();
        }
    }

    public class sina_Lin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            shareSina();
        }
    }

    public class qq_Lin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            share_qq();
        }
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
//                head_recy.setBackgroundResource(0);//列表背景框
                SearchKey = "";
//                addHeadData();
//                Type = 6;
                addListData(Energy_Community_Friend.this);
            } else {
                findfriend_Lin.setVisibility(View.GONE);
//                head_recy.setBackgroundResource(R.drawable.popupwhite_bg);
                SearchKey = editable.toString();
//                afterData();
//                Type = 1;
//                addListData();
                addSeekData(Energy_Community_Friend.this);
            }
        }
    }


    @Override
    public void onRefresh() {
        if (StaticData.Stringspace(SearchKey)) {
            addListData(Energy_Community_Friend.this);
        } else {
            addSeekData(Energy_Community_Friend.this);
        }
    }

    @Override
    public void onLoadMore() {
        if (StaticData.Stringspace(SearchKey)) {
            PageIndex += 1;
            pageSize = 10;
            ListData(this, saveFile.BaseUrl + saveFile.Recommend_User_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
        }
    }

    private void initData(Context context) {
        addListData(context);
    }

    private void addListData(Context context) {
        //Type = 6 精选帖子 ，1 =  普通帖子
        PageIndex = 1;
        pageSize = 10;
        ListData(context, saveFile.BaseUrl + saveFile.Recommend_User_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    private void addSeekData(Context context) {
        PageIndex = 1;
        pageSize = 100;
        seekData(context, saveFile.BaseUrl + saveFile.seekUser_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize + "&SearchKey=" + SearchKey);
    }


    CommunityFriend_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        mAdapter = new CommunityFriend_Adapter(context, baseModel, listModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new CommunityFriend_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }

    CommunityFriend_Seek_Adapter AftermAdapter;

    public void initAfterHead(final Context context) {
        if (All_XRecy != null) {
            All_XRecy.removeAllViews();
        }
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        itemParams.gravity = Gravity.CENTER_HORIZONTAL;
        All_XRecy.setLayoutParams(itemParams);
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        mMangaer.setOrientation(LinearLayoutManager.VERTICAL);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        AftermAdapter = new CommunityFriend_Seek_Adapter(context, seekListModel, seekModel);
        All_XRecy.setAdapter(AftermAdapter);
        AftermAdapter.setOnItemClickLitener(new CommunityFriend_Seek_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<Recommend_Model.DataBean> baseModel;
    Recommend_Model listModel;

    public void ListData(final Context context, String baseUrl) {
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
                    listModel = new Gson().fromJson(resultString, Recommend_Model.class);
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

    private List<Recommend_Model.DataBean> seekListModel;
    Recommend_Model seekModel;

    public void seekData(final Context context, String baseUrl) {
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
                            initAfterHead(context);
                        } else {
                            All_XRecy.loadMoreComplete();
//                            mAdapter.addMoreData(seekModel);
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

    //分享给微信朋友
    public void shareWechatFriend() {
        ShareSDK.initSDK(this);
        Platform.ShareParams wechat = new Platform.ShareParams();
        wechat.setTitle(shareContent.title);
        wechat.setText(shareContent.content);

        wechat.setUrl(shareContent.url);
        if (shareContent.imgpath == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ring);
//        msg.thumbData = Util.bmpToByteArray(bitmap, true);
//        InputStream is = getResources().openRawResource(R.drawable.ring_icon);
//        Bitmap mBitmap = BitmapFactory.decodeStream(is);
            wechat.setImageData(bitmap);
        } else {
            wechat.setImageUrl(shareContent.imgpath);//网络图用Url
        }
//        wechat.setImageData(wechatbit);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(this, Wechat.NAME);
        weixin.setPlatformActionListener(this);
        weixin.share(wechat);
    }


    //分享-新浪微博
    public void shareSina() {
        ShareSDK.initSDK(this);
        SinaWeibo.ShareParams sina = new SinaWeibo.ShareParams();
        sina.setText(shareContent.title + shareContent.url);
//        sina.setImagePath(sianimg);
        if (shareContent.imgpath != null) {
            sina.setImageUrl(shareContent.imgpath);
        }
        Platform sinap = ShareSDK.getPlatform(SinaWeibo.NAME);
        sinap.setPlatformActionListener(this);
//        sinap.SSOSetting(true);//审核未通过
        sinap.share(sina);
    }

    public void share_qq() {
        ShareSDK.initSDK(this);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(shareContent.title);
        sp.setTitleUrl(shareContent.url); // 标题的超链接
        sp.setText(shareContent.content);
        if (shareContent.imgpath != null) {
            sp.setImageUrl(shareContent.imgpath);
        }
//        sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
//        sp.setSite("发布分享的网站名称");
//        sp.setSiteUrl("发布分享网站的地址");
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(this); // 设置分享事件回调
        qq.share(sp);
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
