package com.moying.energyring.myAcativity.Pk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Pk_MyIntegral_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAcativity.Person.Person_Integral;
import com.moying.energyring.myAcativity.Person.Person_Relus;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.AppBarStateChangeListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Pk_FenRankList extends AppCompatActivity {

    private TabLayout ac_tab_layout, toolbar_tab_layout;
    private ViewPager Slideviewpager;
    private TextView pk_Txt, rankCount_Txt, all_Txt, name_Txt, my_change_Txt;
    private SimpleDraweeView head_simple;
    private ImageView my_change_Img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk__fen_rank_list);

        initHead(Pk_FenRankList.this);
        initView();
        initData(0);
        initLocaData(ac_tab_layout, toolbar_tab_layout);
        tabViewSetView(ac_tab_layout, toolbar_tab_layout);
        resetTablayout(ac_tab_layout, toolbar_tab_layout);
    }

    public void initHead(Context context) {
        RelativeLayout my_Rel = (RelativeLayout) findViewById(R.id.my_Rel);
        int relpad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 118);
        my_Rel.setPadding(0, relpad, 0, 0);

        RelativeLayout top_Rel = (RelativeLayout) findViewById(R.id.top_Rel);
        TextView zan_Txt = (TextView) findViewById(R.id.zan_Txt);
        zan_Txt.setText("我的积分");
        TextView rank_Txt = (TextView) findViewById(R.id.rank_Txt);
        rankCount_Txt = (TextView) findViewById(R.id.rankCount_Txt);
        LinearLayout my_tab_Lin = (LinearLayout) findViewById(R.id.my_tab_Lin);
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        StaticData.layoutParamsScale(Params, 710, 96);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 30);
        Params.setMargins(0, pad, 0, 0);
        Params.addRule(RelativeLayout.BELOW, R.id.top_Rel);
        Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        my_tab_Lin.setLayoutParams(Params);
        ImageView round_one = (ImageView) findViewById(R.id.round_one);
        RelativeLayout.LayoutParams itemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int padone = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 16);
        int padtop = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * -40);
        itemParams.addRule(RelativeLayout.BELOW, R.id.top_Rel);
        itemParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.top_Rel);
        itemParams.setMargins(padone, padtop, 0, 0);
        round_one.setLayoutParams(itemParams);
        ImageView round_two = (ImageView) findViewById(R.id.round_two);
        RelativeLayout.LayoutParams itemParamstwo = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        itemParamstwo.addRule(RelativeLayout.ALIGN_RIGHT, R.id.top_Rel);
        itemParamstwo.addRule(RelativeLayout.BELOW, R.id.top_Rel);
        itemParamstwo.setMargins(0, padtop, padone, 0);
        round_two.setLayoutParams(itemParamstwo);
        StaticData.ViewScale(top_Rel, 710, 152);
        StaticData.ViewScale(round_one, 48, 108);
        StaticData.ViewScale(round_two, 48, 108);
    }

    public void initView() {

        toolbar_tab_layout = (TabLayout) findViewById(R.id.toolbar_tab_layout);
        ac_tab_layout = (TabLayout) findViewById(R.id.ac_tab_layout);
        StaticData.ViewScale(toolbar_tab_layout, 370, 64);
        StaticData.ViewScale(ac_tab_layout, 370, 64);
        Slideviewpager = (ViewPager) findViewById(R.id.Slideviewpager);
        Button return_Btn = (Button) findViewById(R.id.return_Btn);
        final TextView title_Txt = (TextView) findViewById(R.id.title_Txt);
        TextView rete_Txt = (TextView) findViewById(R.id.rete_Txt);
        TextView raid_Txt = (TextView) findViewById(R.id.raid_Txt);
        pk_Txt = (TextView) findViewById(R.id.pk_Txt);
        RelativeLayout myData_Rel = (RelativeLayout) findViewById(R.id.myData_Rel);
        head_simple = (SimpleDraweeView) findViewById(R.id.head_simple);
        all_Txt = (TextView) findViewById(R.id.all_Txt);
        name_Txt = (TextView) findViewById(R.id.name_Txt);
        my_change_Img = (ImageView) findViewById(R.id.my_change_Img);
        my_change_Txt = (TextView) findViewById(R.id.my_change_Txt);
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(rete_Txt, 52, 42);
        StaticData.ViewScale(pk_Txt, 710, 40);
        StaticData.ViewScale(myData_Rel, 710, 96);
        StaticData.ViewScale(head_simple, 72, 72);
        StaticData.ViewScale(my_change_Img, 16, 18);

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        StaticData.ViewScale(mToolbar, 0, 88);
        setSupportActionBar(mToolbar);

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.mAppBarLayout);
        final int padFen = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_FenRankList.this)) * 20);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                Log.d("STATE", state.name());
                if (state == State.EXPANDED) {
                    //展开状态
//                    mToolbar.setBackgroundColor(Color.parseColor("#00232121"));
                    toolbar_tab_layout.setVisibility(View.INVISIBLE);
                    ac_tab_layout.setVisibility(View.VISIBLE);
                    title_Txt.setVisibility(View.VISIBLE);
                    pk_Txt.setBackgroundColor(Color.parseColor("#343434"));
                    StaticData.ViewScale(pk_Txt, 710, 40);
//                    Slideviewpager.setPadding(padFen,0,padFen,0);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
//                    mToolbar.setBackgroundColor(Color.parseColor("#232121"));
                    toolbar_tab_layout.setVisibility(View.VISIBLE);
                    ac_tab_layout.setVisibility(View.INVISIBLE);
                    title_Txt.setVisibility(View.GONE);
                    pk_Txt.setBackgroundColor(Color.parseColor("#232121"));
                    StaticData.ViewScale(pk_Txt, 710, 64);
//                    Slideviewpager.setPadding(0,0,0,0);
//                    Slideviewpager.invalidate();
                } else {
                    //中间状态
                }
            }
        });

        return_Btn.setOnClickListener(new return_Btn());
        rete_Txt.setOnClickListener(new rete_Txt());
        raid_Txt.setOnClickListener(new raid_Txt());
    }

    private void initData(int type) {
        myRankData(Pk_FenRankList.this, saveFile.BaseUrl + saveFile.My_Rank_Url, type);
    }

    private class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    //积分记录
    private class rete_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Pk_FenRankList.this, Person_Integral.class);
            startActivity(intent);
        }
    }

    //积分攻略
    private class raid_Txt implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Pk_FenRankList.this, Person_Relus.class);
            startActivity(intent);
        }
    }

    public List<String> userArr;
    public List<Fragment> fragments;
    public MyFragmentPagerAdapter myAdapter;

    private void initLocaData(TabLayout myTab, TabLayout toolbar_tab) {
        myTab.setTabMode(TabLayout.MODE_FIXED);//设置可以滑动
        myTab.setSelectedTabIndicatorHeight(0);//去掉下导航条

        toolbar_tab.setTabMode(TabLayout.MODE_FIXED);//设置可以滑动
        toolbar_tab.setSelectedTabIndicatorHeight(0);//去掉下导航条

        if (userArr != null) {
            userArr.clear();
        }
        userArr = new ArrayList<>();
        userArr.add("世界排行");
        userArr.add("好友排行");
        //添加页卡标题
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        int length = userArr.size();
//        for (int i = 0; i < length; i++) {
//        }
        fragments.add(Pk_FenRankFragment.newInstance(0 + "", 2066 + ""));
        fragments.add(Pk_FenRankFragment.newInstance(1 + "", 2066 + ""));
    }

    private void tabViewSetView(TabLayout myTab, TabLayout toolbar_tab) {
        myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        Slideviewpager.setAdapter(myAdapter);
        //将TabLayout和ViewPager关联。
        myTab.setupWithViewPager(Slideviewpager);
//        Slideviewpager.setCurrentItem(1);
//        Slideviewpager.addOnPageChangeListener(this);
        ac_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                tab_Name.setTextColor(Color.parseColor("#ffffff"));
                StaticData.ViewScale(tab_Name, 185, 64);
                if (tab.getPosition() == 0) {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_leftred);
                } else {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_rightred);
                }
//                initData(tab.getPosition());
                changeData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                tab_Name.setTextColor(Color.parseColor("#95a0ab"));
                StaticData.ViewScale(tab_Name, 185, 64);
                if (tab.getPosition() == 0) {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_leftgazy);
                } else {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_rightgazy);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        toolbar_tab.setupWithViewPager(Slideviewpager);
        toolbar_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                tab_Name.setTextColor(Color.parseColor("#ffffff"));
                StaticData.ViewScale(tab_Name, 185, 64);
                if (tab.getPosition() == 0) {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_leftred);
                } else {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_rightred);
                }
//                initData(tab.getPosition());
                changeData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tab_Name = (TextView) tab.getCustomView().findViewById(R.id.tab_Name);
                tab_Name.setTextColor(Color.parseColor("#95a0ab"));
                StaticData.ViewScale(tab_Name, 185, 64);
                if (tab.getPosition() == 0) {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_leftgazy);
                } else {
                    tab_Name.setBackgroundResource(R.drawable.fen_tab_rightgazy);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });
    }


    private void resetTablayout(TabLayout myTab, TabLayout toolbar_tab) {
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
        for (int i = 0; i < myAdapter.getCount(); i++) {
            int postion = i;
//             toolbar_tab.getTabAt(postion).setText(userArr.get(postion));
            TabLayout.Tab tab = toolbar_tab.getTabAt(postion);
            if (tab != null) {
                tab.setCustomView(myAdapter.getTabView(postion));
            }
        }

        Intent intent = getIntent();
        String tabType = intent.getStringExtra("tabType");
        if (tabType != null) {
            Slideviewpager.setCurrentItem(Integer.parseInt(tabType), true);
        } else {
            Slideviewpager.setCurrentItem(1, true);
            myTab.getTabAt(0).select();
            toolbar_tab.getTabAt(0).select();

        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            fragments = list;
        }


        public View getTabView(int position) {
            View v = LayoutInflater.from(Pk_FenRankList.this).inflate(R.layout.pkfenrank_custom_tab, null);
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

    Pk_MyIntegral_Model Integral_Model;

    //type 是tab切换页卡
    public void myRankData(final Context context, String baseUrl, final int type) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Integral_Model = new Gson().fromJson(resultString, Pk_MyIntegral_Model.class);
                    if (Integral_Model.isIsSuccess() && !Integral_Model.getData().equals("[]")) {

                        changeData(type);
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

    private void changeData(int type) {
        if (Integral_Model != null) {
            Pk_MyIntegral_Model.DataBean oneData = Integral_Model.getData();
            rankCountTextsColor(72, oneData.getAllIntegral() + "分", rankCount_Txt);
            if (oneData.getProfilePicture() != null) {
                Uri uri = Uri.parse(oneData.getProfilePicture());
                head_simple.setImageURI(uri);
            } else {
                StaticData.lodingheadBg(head_simple);
            }
            all_Txt.setText(oneData.getAllIntegral() + "分");
            if (type == 0) {
                name_Txt.setText("第" + oneData.getRanking() + "名");
                setChangeRank(oneData.getWorld());


                String pkStr = "您战胜了" + oneData.getWorldExceedNum() + "个对手";
                if (oneData.getRanking() == 1) {
                    pk_Txt.setText(pkStr);
                } else {
                    int rankStr = oneData.getRanking() - 1;
                    pkStr = pkStr + "（您还差" + oneData.getWorldDiffIntegral() + "分可以超越第" + rankStr + "名）";
                    pk_Txt.setText(pkStr);
                }
            } else {
                name_Txt.setText("第" + oneData.getFriendRanking() + "名");
                setChangeRank(oneData.getFriend());

                String pkFriendStr = "您战胜了" + oneData.getFriendExceedNum() + "个好友";
                if (oneData.getFriendRanking() == 1) {
                    pk_Txt.setText(pkFriendStr);
                } else {
                    String nameStr = oneData.getPreFriendName().toString();
                    pkFriendStr = pkFriendStr + "（您还差" + oneData.getFriendDiffIntegral() + "分可以超越" + nameStr + "）";
                    pk_Txt.setText(pkFriendStr);
                }
            }
        }
    }


    public void rankCountTextsColor(int size, String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
//        int padSize = (int) (Float.parseFloat(saveFile.getShareData("scale", Person_PiontsList.this)) * size);
        int padSize = (int) (Float.parseFloat(saveFile.getShareData("scale", Pk_FenRankList.this)) * size);
        styledText.setSpan(new AbsoluteSizeSpan(padSize), 0, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //初始字体大小
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#f27b7b")), 0, alltext.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//修改颜色
//        styledText.setSpan(new AbsoluteSizeSpan(padEndSize), 0, alltext.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //修改字体大小
//        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#f27b7b")),0,alltext.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//修改颜色
        myTxt.setText(styledText);
    }

    //排名变化
    private void setChangeRank(int changeRank) {
        if (changeRank == 0) {
            my_change_Img.setVisibility(View.INVISIBLE);
            my_change_Txt.setVisibility(View.INVISIBLE);
        } else if (changeRank < 0) {
            my_change_Img.setImageResource(R.drawable.change_down);
            my_change_Txt.setTextColor(Color.parseColor("#FF0100"));
            my_change_Txt.setText(changeRank + "");
        } else if (changeRank > 0) {
            my_change_Img.setImageResource(R.drawable.change_update);
            my_change_Txt.setTextColor(Color.parseColor("#0BC10B"));
            my_change_Txt.setText(changeRank + "");
        }
    }


}
