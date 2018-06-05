package com.moying.energyring.myAcativity.Person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.Person_TaskList_Model;
import com.moying.energyring.Model.ProjectModel;
import com.moying.energyring.Model.ShareContent;
import com.moying.energyring.R;
import com.moying.energyring.myAcativity.Energy.TaskShareActivity;
import com.moying.energyring.myAcativity.Energy.myShareActivity;
import com.moying.energyring.myAcativity.MainActivity;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Pk.Pk_DayPKAdd_Project_Tab;
import com.moying.energyring.myAcativity.Pk.Pk_HuiZong;
import com.moying.energyring.myAcativity.PostingActivity;
import com.moying.energyring.myAdapter.Person_TaskList_Fragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Admin on 2016/3/25.
 */
public class Person_Task_List_Fragment extends Fragment{
    private String defaultHello = "default value";
    private View parentView;
    private XRecyclerView All_XRecy;
    private String Integral;
    public ShareContent shareContent;

    public static Person_Task_List_Fragment newInstance(String Integral) {
        Person_Task_List_Fragment newFragment = new Person_Task_List_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("Integral", Integral);
        newFragment.setArguments(bundle);
        //bundle可以在每个标签里传送数据
        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.person_task_shop_fragment, null);
        Bundle args = getArguments();
        Integral = args != null ? args.getString("Integral") : defaultHello;
//        ProjectID = args != null ? args.getString("ProjectID") : defaultHello;
//        projectName = args != null ? args.getString("projectName") : defaultHello;
//        newPk_model = (newPk_Model) (args != null ? args.getParcelable("newPk_model") : defaultHello);
//        pos = args.getInt("pos", 0);

        initView(parentView);


        return parentView;
    }

    private void initView(View view) {
        All_XRecy = (XRecyclerView) view.findViewById(R.id.All_XRecy);
        All_XRecy.setPullRefreshEnabled(false);
        All_XRecy.setLoadingMoreEnabled(false);
//        initAddHeadView(All_XRecy, Person_Exchange.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(getActivity());
    }

    public void initData(Context context) {
        ListData(getActivity(), saveFile.BaseUrl + saveFile.Task_List_Url);
    }


    Person_TaskList_Fragment_Adapter mAdapter;

    public void initlist(final Context context) {
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        mAdapter = new Person_TaskList_Fragment_Adapter(context, Model);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Person_TaskList_Fragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });


        mAdapter.setonTaskListener(new Person_TaskList_Fragment_Adapter.TaskClickLitener() {

            @Override
            public void taskListener(View view, int position) {
                final Person_TaskList_Model.DataBean oneData = Model.getData().get(position);

                if (oneData.getCondition().equals("1")){//发布图片动态
                    Intent intent = new Intent(getActivity(), PostingActivity.class);
                    startActivity(intent);
                }else if (oneData.getCondition().equals("2")){//"邀请好友
                    String shareTitle = " 嘿，快来能量圈和我一起开启打卡之旅吧";
                    String shareConStr = "hello，我正在使用能量圈，这里有很多共同目标的伙伴...";
                    String InviteCode = saveFile.getShareData("InviteCode",getActivity());
                    String shareUrl  = saveFile.BaseUrl + "Share/Invite?invitecode="+ InviteCode;
                    shareContent = new ShareContent(shareUrl, shareTitle, shareConStr,null);
                    Intent intent = new Intent(getActivity(), myShareActivity.class);
                    intent.putExtra("shareContent", shareContent);
                    startActivity(intent);

                }else if (oneData.getCondition().equals("3")){//"签到
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else if (oneData.getCondition().equals("4")){//每日汇总

                    isReteDayPk(view, getActivity(), saveFile.BaseUrl + saveFile.haveNewRepost_Url);

                }else if (oneData.getCondition().equals("5")){//每日分享

                    String shareTitle = "的个人报告";
                    if (!saveFile.getShareData("NickName",getActivity()).equals("false")){
                        shareTitle = saveFile.getShareData("NickName",getActivity()) + shareTitle;
                    }
                    String shareConStr = "这份报告记录了我今天的战绩，欢迎来挑战！";
                    String todayString = getStringToday();
                    String userID = saveFile.getShareData("userId", getActivity());
                    String shareUrl  = saveFile.BaseUrl + "/Share/PersonReport?UserID=" + userID + "&date=" + todayString;
                    shareContent = new ShareContent(shareUrl, shareTitle, shareConStr,null);
                    Intent intent = new Intent(getActivity(), TaskShareActivity.class);
                    intent.putExtra("shareContent", shareContent);
                    startActivity(intent);

                }else if (oneData.getCondition().equals("6")){//完成训练
                    Intent intent = new Intent(getActivity(), Pk_DayPKAdd_Project_Tab.class);
                    List<ProjectModel> projectModel = new ArrayList<>();
                    intent.putExtra("baseModel", (Serializable) projectModel);
                    startActivity(intent);
                }else if (oneData.getCondition().equals("7")){//送出3个赞
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("tabType","1");
                    startActivity(intent);
                }
            }
        });
    }


    Person_TaskList_Model Model;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Model = new Gson().fromJson(resultString, Person_TaskList_Model.class);
                    if (Model.isIsSuccess() && !Model.getData().equals("[]")) {
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

    public  String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public void isReteDayPk(final View view, final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    view.setEnabled(true);
                    Base_Model reteModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (reteModel.isData()) {
                        Intent intent = new Intent(getActivity(), Pk_HuiZong.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(context, "请汇报更多pk", Toast.LENGTH_SHORT).show();
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
                view.setEnabled(true);
            }
        });
    }


}
