package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.moying.energyring.Model.AddPhoto_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.UserInfo_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.ImagePickerActivity;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.AppBarStateChangeListener;
import com.moying.energyring.waylenBaseView.MyActivityManager;

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

import static com.moying.energyring.myAcativity.Pk.DayPkListFragment.REQUEST_CODE_IMAGE_PICK;

/**
 * Created by waylen on 2017/6/13.
 */

public class PersonMyCenter extends AppCompatActivity {
    private SimpleDraweeView person_bg_simple, user_simple;
    private ImageView gender_img;
    private TextView atten_Txt, fans_Txt, intr_Txt, userName_Txt, title_Txt, rete_Txt;
    private LinearLayout fen_Lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppThemeprice);
//        setContentView(R.layout.fragment1_energy);
        setContentView(R.layout.activity_person_my_center);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initView();
        initAddData();
//        initLocaData();
    }

    private int currentItem = 1;
    private ViewPager Slideviewpager;
    private TabLayout tablayout;
    public List<String> userArr;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;
    private int iconImg[] = {R.drawable.person_selector_goal,R.drawable.person_selector_daypk,  R.drawable.person_selector_pkhistory,R.drawable.person_selector_log };

    public void initView() {
        tablayout = (TabLayout) findViewById(R.id.ac_tab_layout);
        StaticData.ViewScale(tablayout, 0, 200);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);
        final Button return_Btn = (Button) findViewById(R.id.return_Btn);
        title_Txt = (TextView) findViewById(R.id.title_Txt);
        rete_Txt = (TextView) findViewById(R.id.rete_Txt);

        person_bg_simple = (SimpleDraweeView) findViewById(R.id.person_bg_simple);
        user_simple = (SimpleDraweeView) findViewById(R.id.user_simple);
        layoutmarginTop(this, user_simple);
        gender_img = (ImageView) findViewById(R.id.gender_img);
        userName_Txt = (TextView) findViewById(R.id.userName_Txt);
        fen_Lin = (LinearLayout) findViewById(R.id.fen_Lin);
        LinearLayout atten_Lin = (LinearLayout) findViewById(R.id.atten_Lin);
        LinearLayout fans_Lin = (LinearLayout) findViewById(R.id.fans_Lin);
        atten_Txt = (TextView) findViewById(R.id.atten_Txt);
        fans_Txt = (TextView) findViewById(R.id.fans_Txt);
        intr_Txt = (TextView) findViewById(R.id.intr_Txt);
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(person_bg_simple, 0, 440);
        StaticData.ViewScale(user_simple, 180, 180);
        StaticData.ViewScale(gender_img, 24, 24);


        return_Btn.setOnClickListener(new return_Btn());
        person_bg_simple.setOnClickListener(new person_bg_simple());
        user_simple.setOnClickListener(new user_simple());
        atten_Lin.setOnClickListener(new atten_Lin());
        fans_Lin.setOnClickListener(new fans_Lin());
        rete_Txt.setOnClickListener(new rete_Txt());

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.mAppBarLayout);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                Log.d("STATE", state.name());
                if (state == State.EXPANDED) {
                    //展开状态
                    title_Txt.setVisibility(View.GONE);
                    rete_Txt.setVisibility(View.GONE);
                    return_Btn.setBackgroundResource(R.drawable.return_icon);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    title_Txt.setVisibility(View.VISIBLE);
                    rete_Txt.setVisibility(View.GONE);
                    return_Btn.setBackgroundResource(R.drawable.return_black);
                } else {
                    //中间状态
                }
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    private void layoutmarginTop(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 100);
        Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Params.setMargins(0, pad, 0, 0);
        view.setLayoutParams(Params);
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class person_bg_simple extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intentimagepic = new Intent(PersonMyCenter.this, ImagePickerActivity.class);
            startActivityForResult(intentimagepic, REQUEST_CODE_IMAGE_PICK);
        }
    }


    private class user_simple extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intentimagepic = new Intent(PersonMyCenter.this, ImagePickerActivity.class);
            startActivityForResult(intentimagepic, REQUEST_CODE_IMAGE_PICK_PERSONHEAD);
        }
    }

    //粉丝与关注
    private class atten_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PersonMyCenter.this, Person_Center_FansAndAtten.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            intent.putExtra("titleName", "关注");
            intent.putExtra("Type", "1");
            startActivity(intent);
        }
    }

    //粉丝与关注
    private class fans_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PersonMyCenter.this, Person_Center_FansAndAtten.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            intent.putExtra("titleName", "粉丝");
            intent.putExtra("Type", "2");
            startActivity(intent);
        }
    }


    private class rete_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            rete_Txt.setEnabled(false);
            isReteDayPk(PersonMyCenter.this, saveFile.BaseUrl + saveFile.Report_Status_Url);
        }
    }


    private String userId = "0";
    private void initAddData() {
        UserData(PersonMyCenter.this, saveFile.BaseUrl + saveFile.UserInfo_Url + "?UserID=" + userId);
    }

    private void initLocaData() {
        userArr = new ArrayList<>();
        userArr.add("公众承诺");
        userArr.add("每日PK");
        userArr.add("Pk记录");
        userArr.add("成长日志");
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置可以滑动 根据标签自适应宽度
        tablayout.setSelectedTabIndicatorHeight(0);//去掉下导航条
        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int length = userArr.size();
//        for (int i = 0; i < length; i++) {
//        }
        fragments.add(PersonCommittedFragment.newInstance(8 + "", userModel.getData().getUserID() + ""));
        fragments.add(PersonDayPkFragment.newInstance(userArr.get(3), userModel.getData().getUserID() + ""));
        fragments.add(PersonPkHistoryFragment.newInstance(userArr.get(2), userModel.getData().getUserID() + ""));
        fragments.add(PersonGrowthLogFragment.newInstance(7 + "", userModel.getData().getUserID() + ""));
    }

    private void tabViewSetView() {
        myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        Slideviewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        tablayout.setupWithViewPager(Slideviewpager);
//        Slideviewpager.setCurrentItem(1);
//        Slideviewpager.addOnPageChangeListener(this);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout tab_Lin = (LinearLayout) tab.getCustomView().findViewById(R.id.tab_Lin);
                StaticData.ViewScale(tab_Lin, 260, 140);
                ImageView tab_Img = (ImageView) tab.getCustomView().findViewById(R.id.tab_Img);
                StaticData.ViewScale(tab_Img, 100, 112);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout tab_Lin = (LinearLayout) tab.getCustomView().findViewById(R.id.tab_Lin);
                StaticData.ViewScale(tab_Lin, 200, 110);
                ImageView tab_Img = (ImageView) tab.getCustomView().findViewById(R.id.tab_Img);
                StaticData.ViewScale(tab_Img, 80, 90);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });
    }


    private void resetTablayout() {
        /**
         * 使用tablayout + viewpager时注意 如果设置了setupWithViewPager
         * 则需要重新执行下方对每个条目赋值
         * 否则会出现icon文字不显示的bug
         */
        for (int i = 0; i < myAdapter.getCount(); i++) {
            int postion = i;
            tablayout.getTabAt(postion).setText(userArr.get(postion));
            TabLayout.Tab tab = tablayout.getTabAt(postion);
            if (tab != null) {
                tab.setCustomView(myAdapter.getTabView(postion));

//                if (i == 0) {
//                    LinearLayout tab_Lin = (LinearLayout) tab.getCustomView().findViewById(R.id.tab_Lin);
//                    StaticData.ViewScale(tab_Lin, 260, 140);
//                    tab_Lin.setSelected(true);
////                    tab.getCustomView().findViewById(R.id.tab_Lin).setSelected(true);//第一个tab被选中
//                }

            }
        }

        Intent intent = getIntent();
        String tabType = intent.getStringExtra("tabType");
        if (tabType != null) {
//            TabLayout.Tab tabnow = tablayout.getTabAt(Integer.parseInt(tabType));
//            LinearLayout TypeLin = (LinearLayout) tabnow.getCustomView().findViewById(R.id.tab_Lin);
//            StaticData.ViewScale(TypeLin, 260, 140);
//            TypeLin.setSelected(true);
            Slideviewpager.setCurrentItem(Integer.parseInt(tabType), true);
        } else {
            Slideviewpager.setCurrentItem(1,true);
//            TabLayout.Tab tabnow = tablayout.getTabAt(3);
//            LinearLayout TypeLin = (LinearLayout) tabnow.getCustomView().findViewById(R.id.tab_Lin);
//            StaticData.ViewScale(TypeLin, 260, 140);
//            TypeLin.setSelected(true);
        }
    }

    public static final int REQUEST_CODE_IMAGE_PICK_PERSONHEAD = 35;

    UserInfo_Model userModel;

    public void UserData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    userModel = new Gson().fromJson(resultString, UserInfo_Model.class);
                    if (userModel.isIsSuccess() && !userModel.getData().equals("[]")) {
                        UserInfo_Model.DataBean oneData = userModel.getData();
                        if (oneData.getCoverImg() != null) {
                            Uri bgUri = Uri.parse(String.valueOf(oneData.getCoverImg()));
                            person_bg_simple.setImageURI(bgUri);
                        } else {
                            StaticData.PersonBg(person_bg_simple);
                        }
                        if (oneData.getProfilePicture() != null) {
                            Uri imgUri = Uri.parse(oneData.getProfilePicture());
                            user_simple.setImageURI(imgUri);
                        } else {
                            StaticData.lodingheadBg(user_simple);
                        }

                        userName_Txt.setText(oneData.getNickName());
                        title_Txt.setText(oneData.getNickName());
                        atten_Txt.setText(oneData.getAttention() + "");
                        fans_Txt.setText(oneData.getAttention_Me() + "");
                        if (StaticData.isSpace(oneData.getBrief())) {
                            intr_Txt.setText("简介：这个人太懒了，没有留下什么");
                        } else {
                            intr_Txt.setText("简介：" + oneData.getBrief());
                        }

                        int sexId = oneData.getGender();

                        if (sexId == 1) {//男
                            gender_img.setBackgroundResource(R.drawable.sex_man);
                        } else if (sexId == 2) {// 女
                            gender_img.setBackgroundResource(R.drawable.sex_woman);
                        } else {
                            gender_img.setVisibility(View.GONE);
                        }


                        initLocaData();
                        tabViewSetView();
                        resetTablayout();
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


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragments = list;
        }

        ImageView tab_Img;

        public View getTabView(int position) {
            View v = LayoutInflater.from(PersonMyCenter.this).inflate(R.layout.person_custom_tab, null);
            LinearLayout tab_Lin = (LinearLayout) v.findViewById(R.id.tab_Lin);
            StaticData.ViewScale(tab_Lin, 200, 110);
            tab_Img = (ImageView) v.findViewById(R.id.tab_Img);
            StaticData.ViewScale(tab_Img, 80, 90);
            tab_Img.setImageResource(iconImg[position]);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String path = data.getStringExtra("path");
//            Uri imgUri = Uri.parse(path);
            if (!TextUtils.isEmpty(path)) {


                if (requestCode == REQUEST_CODE_IMAGE_PICK) {
                    compressSingleListener(new File(path), Luban.FIRST_GEAR, REQUEST_CODE_IMAGE_PICK);//压缩 THIRD_GEAR  普通压缩 FIRST_GEAR快速压缩
                } else {
                    compressSingleListener(new File(path), Luban.FIRST_GEAR, REQUEST_CODE_IMAGE_PICK_PERSONHEAD);
                }


            }
        }
    }

    //压缩图片
    private void compressSingleListener(File file, int gear, final int type) {
//        if (file.isEmpty()) {
//            return;
//        }
        Luban.compress(file, getFilesDir())
                .putGear(gear)
                .launch(new OnCompressListener() {
                    @Override
                    public void onStart() {
//                        Log.i(TAG, "start");
                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.i("TAG", file.getAbsolutePath());
//                        mImageViews.get(0).setImageURI(Uri.fromFile(file));
//                        Log.e("图片尺寸1111111111111111111",file.length() / 1024 + "k");
                        Uri imgUri = Uri.fromFile(file);
                        if (type == REQUEST_CODE_IMAGE_PICK) {
                            person_bg_simple.setImageURI(imgUri);
//                            addSimplePath(person_bg_simple, path);
                            upload_PhotoData(type, PersonMyCenter.this, saveFile.BaseUrl + saveFile.uploadPhoto_Url, file);
                        } else if (type == REQUEST_CODE_IMAGE_PICK_PERSONHEAD) {
                            user_simple.setImageURI(imgUri);
//                            addSimplePath(user_simple, path);
                            upload_PhotoData(type, PersonMyCenter.this, saveFile.BaseUrl + saveFile.uploadPhoto_Url, file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }


    //缩小本地图片
    private void addSimplePath(SimpleDraweeView simple, String path) {
        Uri uri = Uri.fromFile(new File(path));
        int width = 50, height = 50;
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
//                .setOldController(simple.getController())
                .setImageRequest(request).build();
        simple.setController(controller);
    }


    //上传图片
    public void upload_PhotoData(final int type, final Context context, String baseUrl, File file) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        params.setMultipart(true);//表单格式
        params.setCancelFast(true);//支持断点续传
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
                    if (type == REQUEST_CODE_IMAGE_PICK) {
                        //上传背景ID
                        AddPersonBg_AndHead(PersonMyCenter.this, saveFile.BaseUrl + saveFile.PersonBg_Url + "?FileID=" + files, files);
                    } else if (type == REQUEST_CODE_IMAGE_PICK_PERSONHEAD) {
                        //上传头像ID
                        AddPersonBg_AndHead(PersonMyCenter.this, saveFile.BaseUrl + saveFile.PersonHead_Url + "?FileID=" + files, files);
                    }
                } else {
//                    Toast.makeText(ChangePhone.this, model.getMsg(), 3000).show();
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


    public void AddPersonBg_AndHead(final Context context, String baseUrl, String fileId) {
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


    public void isReteDayPk(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    rete_Txt.setEnabled(true);
                    Base_Model reteModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (reteModel.isData()) {
                        Toast.makeText(context, "汇报成功", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "请汇报更多pk", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                rete_Txt.setEnabled(true);
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
                rete_Txt.setEnabled(true);
            }

            @Override
            public void onFinished() {
                rete_Txt.setEnabled(true);
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}