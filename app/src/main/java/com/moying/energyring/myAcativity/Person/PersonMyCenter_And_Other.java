package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.PersonAndOther_Train_Model;
import com.moying.energyring.Model.Person_OtherBadge_Model;
import com.moying.energyring.Model.UserInfo_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.ImagePickerActivity;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Pk.Training.TrainingTodaySet;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.AppBarStateChangeListener;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

import static com.moying.energyring.myAcativity.Pk.DayPkListFragment.REQUEST_CODE_IMAGE_PICK;

/**
 * Created by waylen on 2017/6/13.
 */

public class PersonMyCenter_And_Other extends FragmentActivity {
    private SimpleDraweeView person_bg_simple, user_simple;
    private ImageView gender_img, photo_Img, personandother_mes_Img, personandother_focus_Img, report_Img;
    private TextView intr_Txt, userName_Txt, title_Txt, fans_Txt, fo_Txt, post_Txt, trainCoubt_Txt;
    private RelativeLayout train_Title_Rel, badge_Rel;
    private LinearLayout train_Lin, badge_Lin;
    private View garytwo_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppThemeprice);
//        setContentView(R.layout.fragment1_energy);
        setContentView(R.layout.person_mycenter_andother);
//        setContentView(R.layout.activity_person_my_center_black);

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
    private int iconImg[] = {R.drawable.person_selector_goal, R.drawable.person_selector_daypk, R.drawable.person_selector_pkhistory, R.drawable.person_selector_log};
    private String iconString[] = {"公众承诺", "每日PK", "PK记录", "成长日志",};

    public void initView() {
        tablayout = (TabLayout) findViewById(R.id.ac_tab_layout);
//        StaticData.ViewScale(tablayout, 0, 100);
        StaticData.ViewScale(tablayout, 700, 82);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);
        final Button return_Btn = (Button) findViewById(R.id.return_Btn);
        title_Txt = (TextView) findViewById(R.id.title_Txt);
        report_Img = (ImageView) findViewById(R.id.report_Img);

        person_bg_simple = (SimpleDraweeView) findViewById(R.id.person_bg_simple);
        photo_Img = (ImageView) findViewById(R.id.photo_Img);
        user_simple = (SimpleDraweeView) findViewById(R.id.user_simple);
//        layoutmarginTop(this, user_simple);

        gender_img = (ImageView) findViewById(R.id.gender_img);
        userName_Txt = (TextView) findViewById(R.id.userName_Txt);
        LinearLayout zan_Lin = (LinearLayout) findViewById(R.id.zan_Lin);
        LinearLayout fans_Lin = (LinearLayout) findViewById(R.id.fans_Lin);
        LinearLayout fo_Lin = (LinearLayout) findViewById(R.id.fo_Lin);
        LinearLayout post_Lin = (LinearLayout) findViewById(R.id.post_Lin);
        fans_Txt = (TextView) findViewById(R.id.fans_Txt);
        fo_Txt = (TextView) findViewById(R.id.fo_Txt);
        post_Txt = (TextView) findViewById(R.id.post_Txt);
        intr_Txt = (TextView) findViewById(R.id.intr_Txt);
        train_Title_Rel = (RelativeLayout) findViewById(R.id.train_Title_Rel);
        trainCoubt_Txt = (TextView) findViewById(R.id.trainCoubt_Txt);
        train_Lin = (LinearLayout) findViewById(R.id.train_Lin);
        badge_Rel = (RelativeLayout) findViewById(R.id.badge_Rel);
        garytwo_view = findViewById(R.id.garytwo_view);
        View badge_arrow = (ImageView) findViewById(R.id.badge_arrow);
        badge_Lin = (LinearLayout) findViewById(R.id.badge_Lin);

        View train_arrow = findViewById(R.id.train_arrow);
        personandother_mes_Img = (ImageView) findViewById(R.id.personandother_mes_Img);
        personandother_focus_Img = (ImageView) findViewById(R.id.personandother_focus_Img);

        StaticData.ViewScale(return_Btn, 80, 88);
//        return_Btn.setAlpha(1f);
        StaticData.ViewScale(person_bg_simple, 0, 326);
        StaticData.ViewScale(user_simple, 120, 120);
        StaticData.ViewScale(gender_img, 24, 24);
        StaticData.ViewScale(zan_Lin, 0, 130);
        StaticData.ViewScale(train_arrow, 60, 60);
        StaticData.ViewScale(photo_Img, 36, 30);
        StaticData.ViewScale(personandother_mes_Img, 140, 60);
        StaticData.ViewScale(personandother_focus_Img, 60, 60);
        StaticData.ViewScale(report_Img, 38, 40);
        StaticData.ViewScale(train_Title_Rel, 0, 80);
        StaticData.ViewScale(badge_Rel, 0, 204);
        StaticData.ViewScale(badge_arrow, 60, 60);


        return_Btn.setOnClickListener(new return_Btn());
        photo_Img.setOnClickListener(new photo_Img());
        user_simple.setOnClickListener(new user_simple());
        report_Img.setOnClickListener(new report_Img());
        fans_Lin.setOnClickListener(new fans_Lin());
        fo_Lin.setOnClickListener(new fo_Lin());
        post_Lin.setOnClickListener(new post_Lin());
        train_Title_Rel.setOnClickListener(new train_Title_Rel());
        personandother_mes_Img.setOnClickListener(new personandother_mes_Img());
        personandother_focus_Img.setOnClickListener(new personandother_focus_Img());
        badge_Rel.setOnClickListener(new badge_Rel());

        seximgMargin(this, gender_img);

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        StaticData.ViewScale(mToolbar, 0, 188);

//        setSupportActionBar(mToolbar);

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.mAppBarLayout);

        //AppBar不能滑动整体页面
        setAppBarDragging(mAppBarLayout, true);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if (state == State.EXPANDED) {
                    //展开状态
//                    mToolbar.setBackgroundColor(Color.parseColor("#00232121"));
                    title_Txt.setVisibility(View.GONE);
//                    report_Img.setVisibility(View.VISIBLE);
                    return_Btn.setBackgroundResource(R.drawable.return_black);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
//                    mToolbar.setBackgroundColor(Color.parseColor("#232121"));
                    title_Txt.setVisibility(View.VISIBLE);
//                    report_Img.setVisibility(View.VISIBLE);
                    return_Btn.setBackgroundResource(R.drawable.return_black);
                } else {
                    //中间状态
                    title_Txt.setVisibility(View.GONE);
                }
            }
        });


    }

    private void seximgMargin(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        StaticData.layoutParamsScale(Params, 25, 25);
        int padbot = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 82);
        int padleft = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 80);
        Params.setMargins(padleft, 0, 0, padbot);
        view.setLayoutParams(Params);
    }


    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class photo_Img extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intentimagepic = new Intent(PersonMyCenter_And_Other.this, ImagePickerActivity.class);
            startActivityForResult(intentimagepic, REQUEST_CODE_IMAGE_PICK);
        }
    }


    private class user_simple extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intentimagepic = new Intent(PersonMyCenter_And_Other.this, ImagePickerActivity.class);
            startActivityForResult(intentimagepic, REQUEST_CODE_IMAGE_PICK_PERSONHEAD);
        }
    }


    private class report_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            rete_Txt.setEnabled(false);
//            isReteDayPk(PersonMyCenter_And_Other.this, saveFile.BaseUrl + saveFile.Report_Status_Url);
            showReport(PersonMyCenter_And_Other.this, report_Img);
        }
    }

    //粉丝与关注
    private class fans_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(PersonMyCenter_And_Other.this, Person_Center_FansAndAtten.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            intent.putExtra("titleName", "粉丝");
            intent.putExtra("Type", "2");
            startActivity(intent);
        }
    }

    //粉丝与关注
    private class fo_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(PersonMyCenter_And_Other.this, Person_Center_FansAndAtten.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            intent.putExtra("titleName", "关注");
            intent.putExtra("Type", "1");
            startActivity(intent);
        }
    }

    private class post_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {

            Slideviewpager.setCurrentItem(0, true);
        }
    }

    private class train_Title_Rel extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(PersonMyCenter_And_Other.this, PersonMyCenter_AndOther_Train.class);
            intent.putExtra("UserID", UserID);
            startActivity(intent);
        }
    }

    private class badge_Rel extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
//            Intent intent = new Intent(PersonMyCenter_And_Other.this, PersonMyCenter_AndOther_Badge.class);
//            intent.putExtra("UserID", UserID);
//            startActivity(intent);

            if (UserID.equals(dbUserId)) {
                Intent intent = new Intent(PersonMyCenter_And_Other.this, PersonMyCenter_My_Badge.class);
                intent.putExtra("UserID", userModel.getData().getUserID() + "");
                intent.putExtra("FilePath", userModel.getData().getProfilePicture());
                startActivity(intent);
            } else {
                Intent intent = new Intent(PersonMyCenter_And_Other.this, PersonMyCenter_Other_Badge.class);
                intent.putExtra("FilePath", userModel.getData().getProfilePicture());
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }

        }
    }

    //私信
    private class personandother_mes_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PersonMyCenter_And_Other.this, Person_Notice_Mes.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            intent.putExtra("titleName", userModel.getData().getNickName());
            startActivity(intent);
        }
    }


    //关注
    private class personandother_focus_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(PersonMyCenter_Other.this, Person_Notice_Mes.class);
//            intent.putExtra("UserID", userModel.getData().getUserID() + "");
//            intent.putExtra("titleName", userModel.getData().getNickName());
//            startActivity(intent);
            int FriendUserID = userModel.getData().getUserID();
            focusData(PersonMyCenter_And_Other.this, saveFile.BaseUrl + saveFile.Friend_Add_User_Url + "?FriendUserID=" + FriendUserID);
        }
    }


    String UserID;
    String dbUserId;

    private void initAddData() {
        UserID = "0";
        dbUserId = "0";
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        dbUserId = saveFile.getShareData("userId", PersonMyCenter_And_Other.this);
        changeUserView(UserID, dbUserId);
        if (UserID.equals(dbUserId)) {
            UserData(PersonMyCenter_And_Other.this, saveFile.BaseUrl + saveFile.UserInfo_Url + "?UserID=" + UserID);
        } else {

            UserData(PersonMyCenter_And_Other.this, saveFile.BaseUrl + saveFile.UserInfo_Url + "?ToUserID=" + UserID);
        }

        int PageIndex = 1;
        int pageSize = 6;
        BadgeListData(PersonMyCenter_And_Other.this, saveFile.BaseUrl + saveFile.OtherBadge_List_Url + "?UserID=" + UserID + "&PageIndex=" + PageIndex + "&PageSize=" + pageSize);

        TrainData(PersonMyCenter_And_Other.this, saveFile.BaseUrl + saveFile.TrainList_Url + "?UserID=" + UserID + "&PageIndex=" + 1 + "&PageSize=" + 3);
    }

    private void changeUserView(String userID, String dbUserId) {
        if (userID.equals(dbUserId)) {
            photo_Img.setEnabled(true);
            photo_Img.setVisibility(View.VISIBLE);
            user_simple.setEnabled(true);
            personandother_mes_Img.setVisibility(View.GONE);
            personandother_focus_Img.setVisibility(View.GONE);
            report_Img.setVisibility(View.GONE);

        } else {
            photo_Img.setEnabled(false);
            photo_Img.setVisibility(View.GONE);
            user_simple.setEnabled(false);
            personandother_mes_Img.setVisibility(View.VISIBLE);
            personandother_focus_Img.setVisibility(View.VISIBLE);
            report_Img.setVisibility(View.VISIBLE);
        }
    }

    private void initLocaData() {
//        tablayout.setTabTextColors(ContextCompat.getColor(this,R.color.colorFristBlack), ContextCompat.getColor(this,R.color.colorSecondBlack));//初始颜色，选中颜色
//        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorFristBlack));//进度条颜色
        tablayout.setTabMode(TabLayout.GRAVITY_CENTER);//设置可以滑动 平分居中
//        tablayout.setTabMode(TabLayout.GRAVITY_CENTER);
        tablayout.setSelectedTabIndicatorHeight(0);//去掉下导航条

        userArr = new ArrayList<>();
        userArr.add("动态");
        userArr.add("相册");

//        tablayout.setSelectedTabIndicatorHeight(0);//去掉下导航条
        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int length = userArr.size();
//        for (int i = 0; i < length; i++) {
//        }
        fragments.add(PersonMyCenter_AndOther_GrowthLog.newInstance(7 + "", userModel.getData().getUserID() + ""));
        fragments.add(PersonMyCenter_AndOther_Photo.newInstance(8 + "", userModel.getData().getUserID() + ""));
    }

    private void tabViewSetView() {
        myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        Slideviewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        tablayout.setupWithViewPager(Slideviewpager);
        reflex(tablayout);//修改下划线宽度
//        Slideviewpager.setCurrentItem(1);
//        Slideviewpager.addOnPageChangeListener(this);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
//                int txtSize = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_DayPk_Project_HistoryDetail.this)) * 30);
//                tab_Name.setTextSize(30);
                tab_Name.setTextColor(ContextCompat.getColor(PersonMyCenter_And_Other.this, R.color.colorFristBlack));
//                StaticData.ViewScale(tab_Name, 210, 80);
                ImageView tab_Img = (ImageView) tab.getCustomView().findViewById(R.id.tab_Img);
                tab_Img.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
//                int txtSize = (int) (Float.parseFloat(saveFile.getShareData("scale", PersonMyCenter_And_Other.this)) * 20);
//                tab_Name.setTextSize(20);
                tab_Name.setTextColor(ContextCompat.getColor(PersonMyCenter_And_Other.this, R.color.colorSecondBlack));
//                StaticData.ViewScale(tab_Name, 210, 80);
                ImageView tab_Img = (ImageView) tab.getCustomView().findViewById(R.id.tab_Img);
                tab_Img.setVisibility(View.INVISIBLE);
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
            Slideviewpager.setCurrentItem(1, true);
            tablayout.getTabAt(0).select();
//            TabLayout.Tab tabnow = tablayout.getTabAt(3);
//            LinearLayout TypeLin = (LinearLayout) tabnow.getCustomView().findViewById(R.id.tab_Lin);
//            StaticData.ViewScale(TypeLin, 260, 140);
//            TypeLin.setSelected(true);
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragments = list;
        }


        public View getTabView(int position) {
            View v = LayoutInflater.from(PersonMyCenter_And_Other.this).inflate(R.layout.personandother_custom_tab, null);
            LinearLayout tab_Lin = (LinearLayout) v.findViewById(R.id.tab_Lin);
//            StaticData.ViewScale(tab_Lin, 200, 110);
            TextView tab_Name = (TextView) v.findViewById(R.id.tab_Name);
            tab_Name.setText(userArr.get(position));
            ImageView tab_Img = (ImageView) v.findViewById(R.id.tab_Img);
            StaticData.ViewScale(tab_Img, 50, 5);
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

//                        atten_Txt.setText(oneData.getAttention() + "");
//                        fans_Txt.setText(oneData.getAttention_Me() + "");

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

                        if (oneData.is_Attention()) {
                            personandother_focus_Img.setImageResource(R.drawable.personandother_select_focus);
                        } else {
                            personandother_focus_Img.setImageResource(R.drawable.personandother_focus);
                        }


                        fans_Txt.setText(oneData.getAttention() + "");
                        fo_Txt.setText(oneData.getAttention_Me() + "");
                        post_Txt.setText(oneData.getPostCount() + "");


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
                            upload_PhotoData(type, PersonMyCenter_And_Other.this, saveFile.BaseUrl + saveFile.uploadPhoto_Url, file);
                        } else if (type == REQUEST_CODE_IMAGE_PICK_PERSONHEAD) {
                            user_simple.setImageURI(imgUri);
//                            addSimplePath(user_simple, path);
                            upload_PhotoData(type, PersonMyCenter_And_Other.this, saveFile.BaseUrl + saveFile.uploadPhoto_Url, file);
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
                        AddPersonBg_AndHead(PersonMyCenter_And_Other.this, saveFile.BaseUrl + saveFile.PersonBg_Url + "?FileID=" + files, files);
                    } else if (type == REQUEST_CODE_IMAGE_PICK_PERSONHEAD) {
                        //上传头像ID
                        AddPersonBg_AndHead(PersonMyCenter_And_Other.this, saveFile.BaseUrl + saveFile.PersonHead_Url + "?FileID=" + files, files);
                    }
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


    public void TrainData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    PersonAndOther_Train_Model trainModel = new Gson().fromJson(resultString, PersonAndOther_Train_Model.class);
                    if (trainModel.isIsSuccess() && !trainModel.getData().equals("[]")) {

                        if (trainModel.getData().getTrainCount() == 0) {
                            train_Title_Rel.setVisibility(View.GONE);
                        }
                        trainCoubt_Txt.setText(trainModel.getData().getTrainCount() + "");
                        trainInit(train_Lin, trainModel, context);
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

    public void trainInit(LinearLayout myLin, final PersonAndOther_Train_Model train_Model, final Context context) {
        if (myLin != null) {
            myLin.removeAllViews();
        }
        int size = train_Model.getData().getTarin_List().size();
        for (int i = 0; i < size; i++) {
            LinearLayout myview = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.personandother_train, null);
            RelativeLayout train_Rel = (RelativeLayout) myview.findViewById(R.id.train_Rel);
            StaticData.ViewScale(train_Rel, 0, 100);
            TextView trainName_Txt = (TextView) myview.findViewById(R.id.trainName_Txt);
            TextView trainCount_Txt = (TextView) myview.findViewById(R.id.trainCount_Txt);
            TextView trainTime_Txt = (TextView) myview.findViewById(R.id.trainTime_Txt);
            View train_arrow = myview.findViewById(R.id.train_arrow);
            StaticData.ViewScale(train_arrow, 60, 60);

            final PersonAndOther_Train_Model.DataBean.TarinListBean oneData = train_Model.getData().getTarin_List().get(i);
            trainName_Txt.setText(oneData.getProjectName());
            trainCount_Txt.setText("完成" + oneData.getFinishCount() + "次");

            int Duration = oneData.getDuration() / 60;
            if (Duration < 1) {
                Duration = 1;
            }
            String time = Duration + "分钟";
            trainTime_Txt.setText(time);

            myview.setTag(i);
            myLin.addView(myview);
            myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();

                    Intent intent1 = new Intent(context, TrainingTodaySet.class);
                    intent1.putExtra("ProjectID", train_Model.getData().getTarin_List().get(tag).getProjectID() + "");
                    startActivity(intent1);

                }
            });
        }
    }

    public void BadgeListData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Person_OtherBadge_Model listModel = new Gson().fromJson(resultString, Person_OtherBadge_Model.class);
                    if (listModel.isIsSuccess() && !listModel.getData().equals("[]")) {
//                        All_XRecy.refreshComplete();
//                        initlist(context);

                        if (listModel.getData().size() == 0) {
                            badge_Rel.setVisibility(View.GONE);
                            garytwo_view.setVisibility(View.GONE);

                        } else {
                            badge_Rel.setVisibility(View.VISIBLE);
                            garytwo_view.setVisibility(View.VISIBLE);
                            hasBadgeInit(badge_Lin, listModel, context);
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
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public void hasBadgeInit(LinearLayout myLin, final Person_OtherBadge_Model badge_Model, final Context context) {
        if (myLin != null) {
            myLin.removeAllViews();
        }

        int size = badge_Model.getData().size();
        for (int i = 0; i < size; i++) {
            LinearLayout myview = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.personandother_adapeter_badgelist, null);

            View pross_Rel = (View) myview.findViewById(R.id.pross_Rel);
            View badge_Lin = (View) myview.findViewById(R.id.badge_Lin);
            SimpleDraweeView badge_Sim = (SimpleDraweeView) myview.findViewById(R.id.badge_Sim);
            TextView badgeName_Txt = (TextView) myview.findViewById(R.id.badgeName_Txt);
            TextView badgeContent_Txt = (TextView) myview.findViewById(R.id.badgeContent_Txt);
            ProgressBar mypross = (ProgressBar) myview.findViewById(R.id.mypross);
            TextView mypross_Txt = (TextView) myview.findViewById(R.id.mypross_Txt);
            StaticData.ViewScale(badge_Lin, 112, 145);
            StaticData.ViewScale(badge_Sim, 92, 92);
//            StaticData.ViewScale(mypross, 140, 12);

            Person_OtherBadge_Model.DataBean oneData = badge_Model.getData().get(i);
            String ImgPath = null;
            if (oneData.isIs_Have()) {
                ImgPath = oneData.getFilePath();
            } else {
                ImgPath = oneData.getBadge_Gray().toString();
            }
            if (ImgPath != null) {
                Uri imgUri = Uri.parse(ImgPath);
                badge_Sim.setImageURI(imgUri);
            }
            badgeName_Txt.setVisibility(View.GONE);
            badgeContent_Txt.setVisibility(View.GONE);
            pross_Rel.setVisibility(View.GONE);

            mypross.setMax(100);
            mypross.setProgress(20);
            mypross.setVisibility(View.GONE);

            myview.setTag(i);
            myLin.addView(myview);
//            myview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int tag = (Integer) v.getTag();
//
////                    Intent intent1 = new Intent(context, TrainingTodaySet.class);
////                    intent1.putExtra("ProjectID", train_Model.getData().getTarin_List().get(tag).getProjectID() + "");
////                    context.startActivity(intent1);
//
//                }
//            });
        }
    }


    public void focusData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    BaseDataInt_Model model = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                    if (model.isIsSuccess()) {
                        UserInfo_Model.DataBean oneData = userModel.getData();

                        if (oneData.is_Attention()) {
                            oneData.setIs_Attention(false);
                            personandother_focus_Img.setImageResource(R.drawable.personandother_focus);

                            if (oneData.getAttention_Me() > 0) {
                                int fansCount = oneData.getAttention_Me() - 1;
                                oneData.setAttention_Me(fansCount);
                                fans_Txt.setText(fansCount + "");
                            }
                            Toast.makeText(context, "已取消关注", Toast.LENGTH_SHORT).show();
                        } else {
                            oneData.setIs_Attention(true);
                            personandother_focus_Img.setImageResource(R.drawable.personandother_select_focus);

                            int fansCount = oneData.getAttention_Me() + 1;
                            oneData.setAttention_Me(fansCount);
                            fans_Txt.setText(fansCount + "");
                            Toast.makeText(context, "已关注", Toast.LENGTH_SHORT).show();
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
                    context.startActivity(intent);
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

    //举报
    private void showReport(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_report, null);
        final PopupWindow ReportPopup = new BasePopupWindow(mContext);
        ReportPopup.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        ReportPopup.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);
        ReportPopup.setTouchable(true);
        ReportPopup.setContentView(contentView);
        ReportPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        TextView report_four_Txt = (TextView) contentView.findViewById(R.id.report_four_Txt);
        report_four_Txt.setVisibility(View.VISIBLE);
        TextView report_five_Txt = (TextView) contentView.findViewById(R.id.report_five_Txt);
        report_five_Txt.setVisibility(View.VISIBLE);

        report_four_Txt.setText("广告");
        report_five_Txt.setText("其他");

        final String myuserId = saveFile.getShareData("userId", mContext);
        final String SourceName = "User";
        report_four_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String ReportType = "2";
                String baseUrl = saveFile.BaseUrl + saveFile.Report_Add_Url;
                ReportMethod(mContext, baseUrl, myuserId, UserID, SourceName, ReportType);
                ReportPopup.dismiss();

            }
        });
        report_five_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String ReportType = "4";
                String baseUrl = saveFile.BaseUrl + saveFile.Report_Add_Url;
                ReportMethod(mContext, baseUrl, myuserId, UserID, SourceName, ReportType);
                ReportPopup.dismiss();
            }
        });

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportPopup.dismiss();
            }
        });
    }


    public void ReportMethod(final Context context, final String baseUrl, String myuserId, String SourceID, String SourceName, String ReportType) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("UserID", myuserId);
            obj.put("SourceID", SourceID);
            obj.put("SourceName", SourceName);
            obj.put("ReportType", ReportType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams(baseUrl);
        params.setAsJsonContent(true);
        params.setBodyContent(obj.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model baseModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(context, "举报成功", Toast.LENGTH_SHORT).show();
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

    /**
     * AppBar不能滑动整体页面
     *
     * @param appBarLayout
     * @param newValue
     */
    private void setAppBarDragging(AppBarLayout appBarLayout, final boolean newValue) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return newValue;
            }
        });
        params.setBehavior(behavior);
    }


    public void reflex(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

//                    int dp10 = dip2px(tabLayout.getContext(), 5);
                    int mag = (int) (Float.parseFloat(saveFile.getShareData("scale", PersonMyCenter_And_Other.this)) * 13);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = mag;
                        params.rightMargin = mag;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}