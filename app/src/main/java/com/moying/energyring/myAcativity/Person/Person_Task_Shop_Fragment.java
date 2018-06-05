package com.moying.energyring.myAcativity.Person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Person_ShopList_Model;
import com.moying.energyring.R;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.Person_TaskShop_Fragment_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 2016/3/25.
 */
public class Person_Task_Shop_Fragment extends Fragment implements XRecyclerView.LoadingListener{
    private String defaultHello = "default value";
    private View parentView;
    private XRecyclerView All_XRecy;
    private int PageIndex;
    private int pageSize;
    private String Integral;


    public static Person_Task_Shop_Fragment newInstance(String Integral) {
        Person_Task_Shop_Fragment newFragment = new Person_Task_Shop_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("Integral",Integral);
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

        initData(getActivity());
        return parentView;
    }

    private void initView(View view) {
        All_XRecy = (XRecyclerView) view.findViewById(R.id.All_XRecy);
        All_XRecy.setLoadingListener(this);//添加事件
//        initAddHeadView(All_XRecy, Person_Exchange.this);
    }


    public void initData(Context context) {
        PageIndex = 1;
        pageSize = 10;
        ListData(getActivity(), saveFile.BaseUrl + saveFile.Product_List_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    @Override
    public void onRefresh() {
        initData(getActivity());
    }

    @Override
    public void onLoadMore() {
        PageIndex = PageIndex + 1;
        pageSize = 10;
        ListData(getActivity(), saveFile.BaseUrl + saveFile.Product_List_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize);
    }

    Person_TaskShop_Fragment_Adapter mAdapter;

    public void initlist(final Context context) {
//        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        GridLayoutManager mMangaer = new GridLayoutManager(context, 2);
        All_XRecy.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        All_XRecy.setHasFixedSize(true);
        mAdapter = new Person_TaskShop_Fragment_Adapter(context, baseModel, Model);
        All_XRecy.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Person_TaskShop_Fragment_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, Person_ShopDetails.class);
                intent.putExtra("ProductID", baseModel.get(position).getProductID() + "");
                intent.putExtra("Integral", Integral);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }


    private List<Person_ShopList_Model.DataBean> baseModel;
    Person_ShopList_Model Model;

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
                    Model = new Gson().fromJson(resultString, Person_ShopList_Model.class);
                    if (Model.isIsSuccess() && !Model.getData().equals("[]")) {
                        baseModel.addAll(Model.getData());
                        if (PageIndex == 1) {
                            All_XRecy.refreshComplete();
                            initlist(context);
//                            if (Model.getData().size() > 0) {
//                                rankCountTextsColor(72, Model.getData().get(0).getIntegral() + "", rankCount_Txt);
//                            }
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
