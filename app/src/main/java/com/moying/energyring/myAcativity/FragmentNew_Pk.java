package com.moying.energyring.myAcativity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.isFristSee_Model;
import com.moying.energyring.Model.myCheckData_Model;
import com.moying.energyring.Model.newPk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.Energy.HasNewActivity;
import com.moying.energyring.myAcativity.Pk.JiFenActivity;
import com.moying.energyring.myAcativity.Pk.Pk_CheckIn;
import com.moying.energyring.myAcativity.Pk.Pk_DayPkAdd_More;
import com.moying.energyring.myAcativity.Pk.Pk_Daypk;
import com.moying.energyring.myAcativity.Pk.Pk_Guide;
import com.moying.energyring.myAdapter.newPk_Fragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;
import com.sunfusheng.marqueeview.MarqueeView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * Created by waylen on 2017/10/19.
 */

public class FragmentNew_Pk extends Fragment implements XRecyclerView.LoadingListener {
    private View parentView;
    TabLayout pk_tab_layout;
    private XRecyclerView pk_recy;
    LinearLayout taglin;
    LinearLayout check_Lin;
    //    VerticalScrollTextView ScrollTextView;
    MarqueeView marqueeView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.framentnew_pk, null);

        initView(parentView);
//        initLocaData();
//        tabViewSetView();


        guideFristData(getActivity(), saveFile.BaseUrl + saveFile.GuidePerFirst_Url,0);//展示功能提醒页
        return parentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

    private void initData() {
        checkData(saveFile.BaseUrl + saveFile.Check_Url);//是否签到
        String userId = saveFile.getShareData("userId", getActivity());
//        ListData(getActivity(), saveFile.BaseUrl + saveFile.DayPk_Url + "?UserID=" + userId);
        ListData(getActivity(), saveFile.BaseUrl + "ec/pk/Report_List_Sta");

    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode){
            String guideId = data.getStringExtra("guideId");
            guideFristData(getActivity(), saveFile.BaseUrl + saveFile.GuidePerFirst_Url,Integer.parseInt(guideId));//展示功能提醒页
        }

    }

    public static int guideID = 1001;
    private void initGuide(boolean isCheck) {
        if (!isCheck) {
            Intent intent = new Intent(getActivity(), Pk_Guide.class);
            intent.putExtra("guideId", "1");
            startActivityForResult(intent,guideID);
//            startActivity(intent);
        }
//        if (saveFile.getShareData("isGuidepk", getActivity()).equals("false")) {
////        if (!((MainActivity)getActivity()).isFristModel.getData().isIs_FirstPool()){
//            Intent intent = new Intent(getActivity(), Pk_Guide.class);
//            intent.putExtra("guideId", "1");
//            startActivity(intent);
//        } else {
//            Intent intent1 = new Intent(getActivity(), HasNewActivity.class);
//            startActivity(intent1);
//        }
//        saveFile.saveShareData("isGuidepk", "true", getActivity());


//        if (ActivityisTop.isForeground(getActivity(), "Pk_Guide") || ActivityisTop.isForeground(getActivity(), "Pk_Guide")) {
////            return;
//        }else{
//            versionNameData(getActivity(), saveFile.BaseUrl + saveFile.Version_Url);
//        }


//        if (!ActivityisTop.isForeground(getActivity(), "Pk_Guide")) {
//            versionNameData(getActivity(), saveFile.BaseUrl + saveFile.Version_Url);
//        }

//        /**获取引导界面工具类的实例**/
//        GuideUtil guideUtil = GuideUtil.getInstance();
////        guideUtil.isFirst()
//        /**调用引导界面**/
//        guideUtil.initGuide(getActivity(), R.drawable.popup_back_white);

    }

    private void isFristSee() {
        Intent intent = new Intent(getActivity(), Pk_Guide.class);
        intent.putExtra("guideId", "2");
        startActivity(intent);
    }

    private void initView(View view) {
//        ScrollTextView = (VerticalScrollTextView) view.findViewById(R.id.ScrollTextView);
        marqueeView = (MarqueeView) view.findViewById(R.id.MarqueeView);
        RelativeLayout titlebg_Rel = (RelativeLayout) view.findViewById(R.id.titlebg_Rel);
        LinearLayout my_Lin = (LinearLayout) view.findViewById(R.id.my_Lin);
        check_Lin = (LinearLayout) view.findViewById(R.id.check_Lin);
        ImageView pk_check_img = (ImageView) view.findViewById(R.id.pk_check_img);
        ImageView pk_more_img = (ImageView) view.findViewById(R.id.pk_more_img);
        TextView hui_Txt = (TextView) view.findViewById(R.id.hui_Txt);
        viewTouchDelegate.expandViewTouchDelegate(pk_more_img, 100, 100, 100, 100);
        pk_tab_layout = (TabLayout) view.findViewById(R.id.pk_tab_layout);
        pk_recy = (XRecyclerView) view.findViewById(R.id.pk_recy);
        pk_recy.setLoadingListener(this);//添加事件
        pk_recy.setPullRefreshEnabled(true);
        pk_recy.setLoadingMoreEnabled(false);
        ImageView pk_ce_Img = (ImageView) view.findViewById(R.id.pk_ce_Img);
        taglin = (LinearLayout) view.findViewById(R.id.taglin);
        StaticData.ViewScale(titlebg_Rel, 0, 108);
        StaticData.ViewScale(my_Lin, 0, 56);
        StaticData.ViewScale(check_Lin, 164, 56);
        StaticData.ViewScale(pk_check_img, 36, 40);
        StaticData.ViewScale(pk_more_img, 38, 20);
        StaticData.ViewScale(pk_ce_Img, 94, 308);
        StaticData.ViewScale(pk_tab_layout, 500, 100);
        StaticData.ViewScale(hui_Txt, 164, 56);
        check_Lin.setOnClickListener(new check_Lin());
        pk_ce_Img.setOnClickListener(new pk_ce_Img());
        marqueeView.setOnItemClickListener(new marqueeView());
        hui_Txt.setOnClickListener(new hui_Txt());
    }


    private class check_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            check_Lin.setEnabled(false);
            AddData(saveFile.BaseUrl + saveFile.CheckAdd_Url);//签到
        }
    }

    private class pk_ce_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent1 = new Intent(getActivity(), Pk_DayPkAdd_More.class);
            startActivityForResult(intent1,guideID);
        }
    }

    private class marqueeView implements MarqueeView.OnItemClickListener {
        @Override
        public void onItemClick(int position, TextView textView) {
            Intent intent = new Intent(getActivity(), Pk_CheckIn.class);
            startActivity(intent);
        }
    }


    private class hui_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            view.setEnabled(false);
            isReteDayPk(view, getActivity(), saveFile.BaseUrl + saveFile.Report_Status_Url);
        }
    }


    newPk_Fragment_Adapter mAdapter;

    public void initlist(final Context context) {
        GridLayoutManager mGridMangaer = new GridLayoutManager(context, 3);
        pk_recy.setLayoutManager(mGridMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        pk_recy.setHasFixedSize(true);
        mAdapter = new newPk_Fragment_Adapter(context, baseModel);
        pk_recy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new newPk_Fragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), Pk_Daypk.class);
                intent.putExtra("projectId", baseModel.getData().get(position).getProjectID() + "");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    newPk_Model baseModel;

    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    baseModel = new Gson().fromJson(resultString, newPk_Model.class);
                    if (baseModel.isIsSuccess() && !baseModel.getData().equals("[]")) {
                        pk_recy.refreshComplete();
                        initlist(getActivity());
//                        tagList(taglin,baseModel);
                    } else {
                        Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, LoginRegister.class);
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


    Base_Model check_Model;

    public void checkData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    check_Model = new Gson().fromJson(resultString, Base_Model.class);
                    if (check_Model.isData()) {
                        myCheckData(saveFile.BaseUrl + saveFile.MyCheckIn_Url);
                    } else {
                        check_Lin.setVisibility(View.VISIBLE);
                        marqueeView.setVisibility(View.INVISIBLE);
                    }

                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(getActivity(), LoginRegister.class);
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

    public void AddData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    BaseDataInt_Model model = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                    if (model.isIsSuccess()) {
                        check_Lin.setEnabled(true);
//                        check_Lin.setVisibility(View.GONE);
//                        ScrollTextView.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getActivity(), JiFenActivity.class);
                        intent.putExtra("jifen", model.getData());
                        startActivity(intent);
                        Toast.makeText(getActivity(), "签到成功", Toast.LENGTH_SHORT);
                        myCheckData(saveFile.BaseUrl + saveFile.MyCheckIn_Url);
                    } else {
                        Toast.makeText(getActivity(), "无法签到请检查网络", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(getActivity(), LoginRegister.class);
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

    public void myCheckData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    myCheckData_Model model = new Gson().fromJson(resultString, myCheckData_Model.class);
                    if (model.isIsSuccess()) {
                        check_Lin.setVisibility(View.GONE);
                        marqueeView.setVisibility(View.VISIBLE);

                        List<String> info = new ArrayList<>();
                        info.add("连续签到" + model.getData().getContinueDays() + "天");
                        info.add("累计签到" + model.getData().getCheckInDays() + "天");
                        marqueeView.startWithList(info);

                    } else {
                        Toast.makeText(getActivity(), "无法签到请检查网络", Toast.LENGTH_SHORT);
                    }
                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(getActivity(), LoginRegister.class);
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
                    BaseDataInt_Model reteModel = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                    int integral = reteModel.getData();
                    if (integral == -1) {
                        Toast.makeText(context, "请汇报更多pk", Toast.LENGTH_SHORT).show();
                    } else if (integral == 0) {
                        //汇报达到上限没分
                        Toast.makeText(context, "汇报成功", Toast.LENGTH_SHORT).show();
                    } else if (integral > 0) {
                        Toast.makeText(context, "汇报成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), JiFenActivity.class);
                        intent.putExtra("jifen", integral);
                        startActivity(intent);
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
                    Intent intent = new Intent(context, LoginRegister.class);
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

    public void guideFristData(final Context context, String baseUrl, final int type) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    isFristSee_Model isFristModel = new Gson().fromJson(resultString, isFristSee_Model.class);
                    if (isFristModel.isIsSuccess()) {
                        if (type == 0) {
                            if (!isFristModel.getData().isIs_CheckIn_Remind()) {
                                initGuide(isFristModel.getData().isIs_CheckIn_Remind());
                                updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_CheckIn_Remind");//展示功能提醒页
                            } else {

                                Intent intent1 = new Intent(getActivity(), HasNewActivity.class);
                                startActivity(intent1);
                            }
                        } else {
                             if (!isFristModel.getData().isIs_FirstPool()) {
//                            Field[] isFrist  =  isFristModel.getData().getClass().getDeclaredFields();
//                            isFrist[0].getName()

                                isFristSee();
                                updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_FirstPool");//展示功能提醒页
                            }
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
                    Intent intent = new Intent(context, LoginRegister.class);
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

    public void updguidePer_Data(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model Model = new Gson().fromJson(resultString, Base_Model.class);
                    if (Model.isIsSuccess()) {

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
                    Intent intent = new Intent(context, LoginRegister.class);
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
