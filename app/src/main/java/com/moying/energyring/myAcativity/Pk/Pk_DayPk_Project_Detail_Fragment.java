package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.AddPhoto_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.DayPkDetail_Model;
import com.moying.energyring.Model.DayPkList_Model;
import com.moying.energyring.Model.newPk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.ImagePickerActivity;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Pk.Training.TrainingTodaySet;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Admin on 2016/3/25.
 */
public class Pk_DayPk_Project_Detail_Fragment extends Fragment {
    private String defaultHello = "default value";
    private View parentView;
    private RelativeLayout my_tab_Rel;
    private TabLayout ac_tab_layout;
    private ViewPager Slideviewpager;
    private String Type;
    private String ProjectID, projectName;
    private SimpleDraweeView person_bg_simple, zhan_simple;
    private View zhan_Lin, add_Photo_Lin;
    private TextView zhanTxt;
    private View addpk_Img;
    private newPk_Model newPk_model;
    private int pos;
    final int RESULT_CODE_MORE = 300;
    public final int REQUEST_CODE_IMAGE_PICK = 34;
    private TextView addpkOrTrain_Txt;


    public static Pk_DayPk_Project_Detail_Fragment newInstance(int pos, String Type, String ProjectID, String projectName, newPk_Model newPk_model) {
        Pk_DayPk_Project_Detail_Fragment newFragment = new Pk_DayPk_Project_Detail_Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        bundle.putString("Type", Type);
        bundle.putString("ProjectID", ProjectID);
        bundle.putString("projectName", projectName);
        bundle.putParcelable("newPk_model", newPk_model);
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
        parentView = inflater.inflate(R.layout.pk_daypk_projectdetail_fragment, null);
        Bundle args = getArguments();
        Type = args != null ? args.getString("Type") : defaultHello;
        ProjectID = args != null ? args.getString("ProjectID") : defaultHello;
        projectName = args != null ? args.getString("projectName") : defaultHello;
        newPk_model = (newPk_Model) (args != null ? args.getParcelable("newPk_model") : defaultHello);
        pos = args.getInt("pos", 0);

        initView(parentView);
        initData(ProjectID);
//        changeAddImg(newPk_model);
        changeData(0);
        return parentView;
    }

    private void initView(View view) {
        person_bg_simple = (SimpleDraweeView) view.findViewById(R.id.person_bg_simple);
        zhan_Lin = view.findViewById(R.id.zhan_Lin);
        zhan_simple = (SimpleDraweeView) view.findViewById(R.id.zhan_simple);
        zhanTxt = (TextView) view.findViewById(R.id.zhanTxt);
        add_Photo_Lin = view.findViewById(R.id.add_Photo_Lin);
        View add_photo_Img = view.findViewById(R.id.add_photo_Img);

        StaticData.ViewScale(person_bg_simple, 0, 480);
        StaticData.ViewScale(zhan_simple, 60, 60);
        StaticData.ViewScale(add_photo_Img, 68, 56);


        RelativeLayout my_tab_Rel = (RelativeLayout) view.findViewById(R.id.my_tab_Rel);
        ac_tab_layout = (TabLayout) view.findViewById(R.id.ac_tab_layout);
        addpk_Img = view.findViewById(R.id.addpk_Img);
        addpkOrTrain_Txt = (TextView) view.findViewById(R.id.addpkOrTrain_Txt);
        Slideviewpager = (ViewPager) view.findViewById(R.id.Slideviewpager);
        StaticData.ViewScale(my_tab_Rel, 0, 138);
        StaticData.ViewScale(ac_tab_layout, 410, 80);
        StaticData.ViewScale(addpk_Img, 128, 52);
        StaticData.ViewScale(addpkOrTrain_Txt, 268, 100);
        addpk_Img.setOnClickListener(new addpk_Img());
        addpkOrTrain_Txt.setOnClickListener(new addpkOrTrain_Txt());
        add_Photo_Lin.setOnClickListener(new add_Photo_Lin());
    }

    private class addpk_Img extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(getActivity(), Pk_AddReport.class);
            intent.putExtra("projectModel", newPk_model.getData().get(pos));
//            intent.putExtra("projectModel", Integral_Model.getData());
            startActivityForResult(intent, RESULT_CODE_MORE);
        }
    }

    private class addpkOrTrain_Txt implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (addpkOrTrain_Txt.getText().equals("打卡")) {
                Intent intent = new Intent(getActivity(), Pk_AddReport.class);
                intent.putExtra("projectModel", newPk_model.getData().get(pos));
                startActivityForResult(intent, RESULT_CODE_MORE);
            } else if (addpkOrTrain_Txt.getText().equals("开始训练")) {
                Intent intent1 = new Intent(getActivity(), TrainingTodaySet.class);
                intent1.putExtra("ProjectID", ProjectID);
                startActivity(intent1);
            }
        }
    }

    private class add_Photo_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intentimagepic = new Intent(getActivity(), ImagePickerActivity.class);
            startActivityForResult(intentimagepic, REQUEST_CODE_IMAGE_PICK);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CODE_MORE) {
            if (RESULT_OK == resultCode) {
                String guideId = data.getStringExtra("guideId");
                if (guideId.equals("1")) {
//                    addpk_Img.setVisibility(View.GONE);
//                initData(ProjectID);
//                changeData();
//                myRankData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportRank_Url + "?ProjectID=" + ProjectID);
                }
            }

        } else if (requestCode == REQUEST_CODE_IMAGE_PICK) {
            if (resultCode == Activity.RESULT_OK) {
                String path = data.getStringExtra("path");
                //更换pk背景图
                if (!TextUtils.isEmpty(path)) {
//                    Uri imgUri = Uri.parse(String.valueOf(path));
//                    pkbg_simple.setImageURI(imgUri);
//                    upload_PhotoData(getActivity() , saveFile.BaseUrl + saveFile.uploadPhoto_Url,path);

                    compressSingleListener(new File(path), Luban.FIRST_GEAR, REQUEST_CODE_IMAGE_PICK);//压缩 THIRD_GEAR  普通压缩 FIRST_GEAR快速压缩
                }
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        changeData(1);
    }

    private void changeData(int isChange) {
//        changeData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportProject_List_Url);
        myRankData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportRank_Url + "?ProjectID=" + ProjectID, 1);
    }


    private void initData(String ProjectID) {
        ListData(getActivity(), saveFile.BaseUrl + saveFile.My_Ranking_One_Url + "?ProjectID=" + ProjectID);
        myRankData(getActivity(), saveFile.BaseUrl + saveFile.My_ReportRank_Url + "?ProjectID=" + ProjectID, 0);
    }


    public List<String> userArr;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;

    private void initLocaData(TabLayout myTab) {
        myTab.setTabMode(TabLayout.MODE_FIXED);//设置可以滑动
        myTab.setSelectedTabIndicatorHeight(0);//去掉下导航条
        if (userArr != null) {
            userArr.clear();
        }
        userArr = new ArrayList<>();
        userArr.add("排行榜");
        userArr.add("回顾");
        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int length = userArr.size();
        fragments.add(Pk_DayPkDetail_RankFragment.newInstance(0 + "", ProjectID + "", Integral_Model));
        fragments.add(Pk_DayPkDetail_HistoryFragment.newInstance(1 + "", ProjectID + "", Integral_Model));
    }

    private void tabViewSetView(TabLayout myTab) {
        myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments);
        Slideviewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        myTab.setupWithViewPager(Slideviewpager);
//        Slideviewpager.setCurrentItem(1);
//        Slideviewpager.addOnPageChangeListener(this);
        ac_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                int txtSize = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 30);
                tab_Name.setTextSize(30);
                tab_Name.setTextColor(Color.parseColor("#f24d4c"));
                StaticData.ViewScale(tab_Name, 205, 80);

//                if (tab.getPosition() == 0) {
//                    tab_Name.setBackgroundResource(R.drawable.fen_tab_leftred);
//                } else {
//                    tab_Name.setBackgroundResource(R.drawable.fen_tab_rightred);
//                }

//                initData();
//                initData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                int txtSize = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 20);
                tab_Name.setTextSize(20);
                tab_Name.setTextColor(Color.parseColor("#95a0ab"));
                StaticData.ViewScale(tab_Name, 205, 80);

//                if (tab.getPosition() == 0) {
//                    tab_Name.setBackgroundResource(R.drawable.fen_tab_leftgazy);
//                } else {
//                    tab_Name.setBackgroundResource(R.drawable.fen_tab_rightgazy);
//                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    private void resetTablayout(TabLayout myTab) {
        /**
         * 使用tablayout + viewpager时注意 如果设置了setupWithViewPager
         * 则需要重新执行下方对每个条目赋值
         * 否则会出现icon文字不显示的bug
         */
        for (int i = 0; i < myAdapter.getCount(); i++) {
            int postion = i;
//            myTab.getTabAt(postion).setText(userArr.get(postion));
            TabLayout.Tab tab = myTab.getTabAt(postion);
            if (tab != null) {
                tab.setCustomView(myAdapter.getTabView(postion));
            }
        }

        Intent intent = getActivity().getIntent();
        String tabType = intent.getStringExtra("tabType");
        if (tabType != null) {
            Slideviewpager.setCurrentItem(Integer.parseInt(tabType), true);
        } else {
            Slideviewpager.setCurrentItem(1, true);
            myTab.getTabAt(0).select();
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragments = list;
        }


        public View getTabView(int position) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.pkfenrank_custom_tab, null);
            TextView tab_Name = (TextView) v.findViewById(R.id.tab_Name);
            tab_Name.setText(userArr.get(position));
            return v;
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    DayPkDetail_Model Integral_Model;

    public void myRankData(final Context context, String baseUrl, final int isChange) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Integral_Model = new Gson().fromJson(resultString, DayPkDetail_Model.class);
                    if (Integral_Model.isIsSuccess() && !Integral_Model.getData().equals("[]")) {
//                        if (Integral_Model.getData().getIs_SenPost() == 0) {
//                            pos_Txt.setVisibility(View.VISIBLE);
//                        } else {
//                            pos_Txt.setVisibility(View.GONE);
//                        }
                        if (isChange == 0) {
                            initLocaData(ac_tab_layout);
                            tabViewSetView(ac_tab_layout);
                            resetTablayout(ac_tab_layout);
                        } else {
                            changeAddImg(Integral_Model);
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


    public void ListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    DayPkList_Model DayPk_OneModel = new Gson().fromJson(resultString, DayPkList_Model.class);
                    if (DayPk_OneModel.isIsSuccess() && !DayPk_OneModel.getData().equals("[]")) {

                        fragmentbg(DayPk_OneModel);//背景图
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

    //背景图占领封面item
    private void fragmentbg(DayPkList_Model Model) {
        if (Model.getData().size() != 0) {
            DayPkList_Model.DataBean oneData = Model.getData().get(0);

            if (oneData.getPKCoverImg() != null) {
                Uri imgUri = Uri.parse(String.valueOf(oneData.getPKCoverImg()));
                person_bg_simple.setImageURI(imgUri);
            } else {
                Uri uri = Uri.parse("res:///" + R.drawable.daypk_bg);
                person_bg_simple.setImageURI(uri);
            }
            zhan_Lin.setVisibility(View.VISIBLE);//占领封面item
            if (oneData.getProfilePicture() != null) {
//                StaticData.addPlaceRound(zhan_simple,getActivity());//占位图
                Uri imgUri = Uri.parse(String.valueOf(oneData.getProfilePicture()));
                zhan_simple.setImageURI(imgUri);
            }
            if (oneData.getNickName() != null) {
                zhanTxt.setText(String.valueOf(oneData.getNickName()));
            } else {
                zhanTxt.setText("没有名字");
            }

            if (saveFile.getShareData("userId", getActivity()).equals(oneData.getUserID() + "")) {
                add_Photo_Lin.setVisibility(View.VISIBLE);
            }
        } else {
            Uri uri = Uri.parse("res:///" + R.drawable.daypk_bg);
            person_bg_simple.setImageURI(uri);
            add_Photo_Lin.setVisibility(View.VISIBLE);
        }
//        else {
//            ((Pk_Daypk) (getActivity())).setPkbg_simple(null);
//            StaticData.PkBg(pkbg_simple);
//            Uri uri = Uri.parse("res:///" + R.drawable.loading_icon);
//            pkbg_simple.setImageURI(uri);
//        }
//        }
    }


    public void changeData(final Context context, final String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    newPk_Model addModel = new Gson().fromJson(resultString, newPk_Model.class);
                    if (addModel.isIsSuccess() && !addModel.getData().equals("[]")) {
//                        changeAddImg(addModel);

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

    private void changeAddImg(DayPkDetail_Model pkModel) {
        //是否可以打卡

        Type = pkModel.getData().getReportID() + "";
        projectName = pkModel.getData().getProjectName();
        int Limit = pkModel.getData().getLimit();
        boolean isTrain = pkModel.getData().isIs_Train();
        if (Limit == 1) { //上限是1的项目
            if (Type.equals("0")) { // 每日只能打卡一次 还未汇报
//                addpk_Img.setVisibility(View.VISIBLE);
//                if (isTrain){
                addpkOrTrain_Txt.setVisibility(View.VISIBLE);
                addpkOrTrain_Txt.setText("打卡");
//                }
            } else {
//                addpk_Img.setVisibility(View.GONE);
                addpkOrTrain_Txt.setVisibility(View.GONE);
            }
        } else {
            //可多次汇报的项目
            if (projectName.equals("健康走")) {
//                addpk_Img.setVisibility(View.GONE);
                addpkOrTrain_Txt.setVisibility(View.GONE);
            } else {
//                addpk_Img.setVisibility(View.VISIBLE);
                //不是健康走 是否是训练项目
                if (isTrain) {
                    addpkOrTrain_Txt.setText("开始训练");
                }else {
                    addpkOrTrain_Txt.setText("打卡");
                }
                addpkOrTrain_Txt.setVisibility(View.VISIBLE);
            }
        }


    }

    //压缩图片
    private void compressSingleListener(File file, int gear, final int type) {
        if (StaticData.isSpace(file.getName())) {
            return;
        }
        Luban.compress(file, getActivity().getFilesDir())
                .putGear(gear)
                .launch(new OnCompressListener() {
                    @Override
                    public void onStart() {
//                        Log.i(TAG, "start");
                    }

                    @Override
                    public void onSuccess(File file) {
//                        Log.e("图片尺寸1111111111111111111",file.length() / 1024 + "k");
                        Uri imgUri = Uri.fromFile(file);
                        person_bg_simple.setImageURI(imgUri);
                        //上传每日PK背景图
                        upload_PhotoData(getActivity(), saveFile.BaseUrl + saveFile.uploadPhoto_Url, file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }


    public void upload_PhotoData(final Context context, String baseUrl, File file) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        params.setMultipart(true);//表单格式
        params.setCancelFast(true);//支持断点续传
//            params.addBodyParameter("file"+i,photoPaths.get(i),null,photoPaths.get(i));
        try {
            FileInputStream fileStream = new FileInputStream(file);
            params.addBodyParameter("file", fileStream, null, file.getName());
            //最后fileName InputStream参数获取不到文件名, 最好设置, 除非服务端不关心这个参数.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                AddPhoto_Model model = new Gson().fromJson(resultString, AddPhoto_Model.class);
                if (model.isIsSuccess()) {
                    String files = model.getData().toString().replace("[", "").replace("]", "");
                    AddPkBg_Data(getActivity(), saveFile.BaseUrl + saveFile.AddPkBg_Url + "?FileID=" + files, files);
                } else {
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
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

    public void AddPkBg_Data(final Context context, String baseUrl, String fileId) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
//        params.addParameter("FileID", fileId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                Base_Model model = new Gson().fromJson(resultString, Base_Model.class);
                if (model.isIsSuccess()) {
//                    finish();
                } else {
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
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
