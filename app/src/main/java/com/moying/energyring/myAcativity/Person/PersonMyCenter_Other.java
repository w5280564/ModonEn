package com.moying.energyring.myAcativity.Person;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.UserInfo_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.AppBarStateChangeListener;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.MyActivityManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waylen on 2017/6/13.
 */

public class PersonMyCenter_Other extends FragmentActivity {
    private SimpleDraweeView person_bg_simple, user_simple;
    private ImageView gender_img;
    private TextView atten_Txt, fans_Txt, intr_Txt, userName_Txt;
    private String ToUserID;
    private String UserID;
    private TextView person_mes_Img, person_focus_Img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeprice);
//        setContentView(R.layout.fragment1_energy);
        setContentView(R.layout.activity_person_my_center);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");

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
    private int iconImg[] = {R.drawable.person_selector_log, R.drawable.person_selector_goal, R.drawable.person_selector_pkhistory, R.drawable.person_selector_daypk};

    public void initView() {
        tablayout = (TabLayout) findViewById(R.id.ac_tab_layout);
        StaticData.ViewScale(tablayout, 0, 200);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);
        final Button return_Btn = (Button) findViewById(R.id.return_Btn);
        person_bg_simple = (SimpleDraweeView) findViewById(R.id.person_bg_simple);
        user_simple = (SimpleDraweeView) findViewById(R.id.user_simple);
        layoutmarginTop(this, user_simple);
        gender_img = (ImageView) findViewById(R.id.gender_img);
        userName_Txt = (TextView) findViewById(R.id.userName_Txt);
        LinearLayout atten_Lin = (LinearLayout) findViewById(R.id.atten_Lin);
        LinearLayout fans_Lin = (LinearLayout) findViewById(R.id.fans_Lin);
        atten_Txt = (TextView) findViewById(R.id.atten_Txt);
        fans_Txt = (TextView) findViewById(R.id.fans_Txt);
        intr_Txt = (TextView) findViewById(R.id.intr_Txt);

        person_mes_Img = (TextView) findViewById(R.id.person_mes_Img);
        person_focus_Img = (TextView) findViewById(R.id.person_focus_Img);
        LinearLayout other_Mes_Lin = (LinearLayout) findViewById(R.id.other_Mes_Lin);
        other_Mes_Lin.setVisibility(View.VISIBLE);

        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(person_bg_simple, 750, 440);
        StaticData.ViewScale(user_simple, 180, 180);
        StaticData.ViewScale(gender_img, 24, 24);
        StaticData.ViewScale(person_mes_Img, 160, 60);
        StaticData.ViewScale(person_focus_Img, 160, 60);


        return_Btn.setOnClickListener(new return_Btn());
        atten_Lin.setOnClickListener(new atten_Lin());
        fans_Lin.setOnClickListener(new fans_Lin());
        person_mes_Img.setOnClickListener(new person_mes_Img());
        person_focus_Img.setOnClickListener(new person_focus_Img());
        user_simple.setOnClickListener(new user_simple());

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.mAppBarLayout);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if (state == State.EXPANDED) {

                    //展开状态
                    return_Btn.setBackgroundResource(R.drawable.return_icon);
                } else if (state == State.COLLAPSED) {

                    //折叠状态
                    return_Btn.setBackgroundResource(R.drawable.return_black);
                } else {

                    //中间状态

                }
            }
        });

    }

    private void layoutmarginTop(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 100);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 60);
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


    //粉丝与关注
    private class atten_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PersonMyCenter_Other.this, Person_Center_FansAndAtten.class);
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
            Intent intent = new Intent(PersonMyCenter_Other.this, Person_Center_FansAndAtten.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            intent.putExtra("titleName", "粉丝");
            intent.putExtra("Type", "2");
            startActivity(intent);
        }
    }

    //私信
    private class person_mes_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PersonMyCenter_Other.this, Person_Notice_Mes.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            intent.putExtra("titleName", userModel.getData().getNickName());
            startActivity(intent);
        }
    }

    //关注
    private class person_focus_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(PersonMyCenter_Other.this, Person_Notice_Mes.class);
//            intent.putExtra("UserID", userModel.getData().getUserID() + "");
//            intent.putExtra("titleName", userModel.getData().getNickName());
//            startActivity(intent);
            int FriendUserID = userModel.getData().getUserID();
            focusData(PersonMyCenter_Other.this, saveFile.BaseUrl + saveFile.Friend_Add_User_Url + "?FriendUserID=" + FriendUserID);
        }
    }
    private class user_simple extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            showHead(PersonMyCenter_Other.this, user_simple);
        }
    }



    private void initAddData() {
        UserData(PersonMyCenter_Other.this, saveFile.BaseUrl + saveFile.UserInfo_Url + "?ToUserID=" + UserID);
    }

    private void initLocaData() {
        userArr = new ArrayList<>();
        userArr.add("成长日志");
        userArr.add("公众承诺");
        userArr.add("Pk记录");
        userArr.add("每日PK");
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
        fragments.add(PersonGrowthLogFragment.newInstance(7 + "", userModel.getData().getUserID() + ""));
        fragments.add(PersonCommittedFragment.newInstance(8 + "", userModel.getData().getUserID() + ""));
        fragments.add(PersonPkHistoryFragment.newInstance(userArr.get(2), userModel.getData().getUserID() + ""));
        fragments.add(PersonDayPkFragment.newInstance(userArr.get(3), userModel.getData().getUserID() + ""));
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
                if (i == 0) {
                    LinearLayout tab_Lin = (LinearLayout) tab.getCustomView().findViewById(R.id.tab_Lin);
                    StaticData.ViewScale(tab_Lin, 260, 140);
                    tab_Lin.setSelected(true);
//                    tab.getCustomView().findViewById(R.id.tab_Img).setSelected(true);//第一个tab被选中
                }
            }
        }
    }


    //放大头像
    private void showHead(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_myhead, null);
        final PopupWindow headPopup = new BasePopupWindow(mContext);
        headPopup.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        headPopup.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);
        headPopup.setTouchable(true);
        headPopup.setContentView(contentView);
        headPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
        SimpleDraweeView popup_head = (SimpleDraweeView) contentView.findViewById(R.id.popup_head);
        StaticData.ViewScale(popup_head, 750, 750);

        UserInfo_Model.DataBean oneData = userModel.getData();
        if (oneData.getProfilePicture() != null) {
            Uri imgUri = Uri.parse(oneData.getProfilePicture());
            popup_head.setImageURI(imgUri);
        } else {
            StaticData.lodingheadBg(popup_head);
        }
        popup_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headPopup.dismiss();
            }
        });

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headPopup.dismiss();
            }
        });
    }


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

                        if (oneData.is_Attention()){
                            person_focus_Img.setBackgroundResource(R.drawable.person_focusselecet_icon);
                        } else {
                            person_focus_Img.setBackgroundResource(R.drawable.person_focus_icon);
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
                            person_focus_Img.setBackgroundResource(R.drawable.person_focus_icon);

                            if (oneData.getAttention_Me() > 0){
                                int fansCount = oneData.getAttention_Me() - 1;
                                oneData.setAttention_Me(fansCount);
                                fans_Txt.setText(fansCount +"");
                            }
                        } else {
                            oneData.setIs_Attention(true);
                            person_focus_Img.setBackgroundResource(R.drawable.person_focusselecet_icon);

                            int fansCount = oneData.getAttention_Me() + 1;
                            oneData.setAttention_Me(fansCount);
                            fans_Txt.setText(fansCount +"");
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



    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragments = list;
        }

        ImageView tab_Img;

        public View getTabView(int position) {
            View v = LayoutInflater.from(PersonMyCenter_Other.this).inflate(R.layout.person_custom_tab, null);
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
    protected void onDestroy() {
        super.onDestroy();
    }
}