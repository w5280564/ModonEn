package com.moying.energyring.myAcativity.Person;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Notice_UserList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.Notice_Mes_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.waylenBaseView.myActivity;
import com.moying.energyring.xrecycle.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class Person_Notice_Mes extends myActivity implements XRecyclerView.LoadingListener {
    private TextView cententtxt;
    private XRecyclerView All_XRecy;
    private int PageIndex;
    private int pageSize;
    private String UserID, titleName;
    private EditText mess_edit;
    private Button send_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_notice_mes);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        titleName = intent.getStringExtra("titleName");

        initTitle();
        initView();
    }


    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText(titleName);
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    private void initView() {
        All_XRecy = (XRecyclerView) findViewById(R.id.All_XRecy);
        All_XRecy.setLoadingListener(this);//添加事件
        All_XRecy.setPullRefreshEnabled(false);

        RelativeLayout mess_bg = (RelativeLayout) findViewById(R.id.mess_bg);
        send_Btn = (Button) findViewById(R.id.send_Btn);
        mess_edit = (EditText) findViewById(R.id.mess_edit);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//不会挤压界面屏幕整体上移
        StaticData.ViewScale(mess_bg, 0, 0);
        StaticData.ViewScale(send_Btn, 100, 100);
        send_Btn.setOnClickListener(new send_Btn());
    }




//    @Override
//    protected void onResume() {
//        super.onResume();
//        initData();
//    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Person_Notice_Mes"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长

        initData();
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Person_Notice_Mes"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }



    private void initData() {
        PageIndex = 1;
        pageSize = 10;
        ListData(Person_Notice_Mes.this, saveFile.BaseUrl + saveFile.MesList_Url + "?ToUserID=" + UserID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    @Override
    public void onRefresh() {
       initData();
    }

    @Override
    public void onLoadMore() {
        PageIndex += 1;
        pageSize = 10;
        ListData(Person_Notice_Mes.this, saveFile.BaseUrl + saveFile.MesList_Url + "?ToUserID=" + UserID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }


    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private class send_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            if (StaticData.Stringspace(mess_edit.getText().toString())) {
                Toast.makeText(Person_Notice_Mes.this, "请先输入一些内容吧！", Toast.LENGTH_SHORT).show();
                return;
            }
            v.setEnabled(false);
            sendMessData(Person_Notice_Mes.this,v,saveFile.BaseUrl+saveFile.SendMess_Url);
        }
    }

    Notice_Mes_Adapter mAdapter;

    public void initlist(final Context context) {
//        Collections.reverse(baseModel);
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);

//        SnapHelper snapHelperTop = new Gravity(Gravity.TOP);
//        mMangaer.setStackFromEnd(true);//滑动到底部显示
        mMangaer.setReverseLayout(true);//反转列表
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能

        All_XRecy.setHasFixedSize(true);
        mAdapter = new Notice_Mes_Adapter(context, baseModel, listModel);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Notice_Mes_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
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

    public void sendMessData(final Context context, final View view, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("ToUserID", UserID);
            obj.put("MessageContent", mess_edit.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.setAsJsonContent(true);
        params.setBodyContent(obj.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                view.setEnabled(true);
                if (resultString != null) {
                    BaseDataInt_Model Model = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                    if (Model.isIsSuccess()) {
//                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
//                        finish();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            view.clearFocus();
                        }
                        mess_edit.setText("");
                        mess_edit.clearFocus();
                        
                        initData();
                    } else {
                        Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                view.setEnabled(true);
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, MainLogin.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
                view.setEnabled(true);
            }

            @Override
            public void onFinished() {
            }
        });
    }

}
