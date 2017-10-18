package com.moying.energyring.myAcativity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.animated.base.AbstractAnimatedDrawable;
import com.facebook.imagepipeline.image.ImageInfo;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.pk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Person.PersonMyCenter;
import com.moying.energyring.myAcativity.Pk.Committ.Leran_AllPerson;
import com.moying.energyring.myAcativity.Pk.JiFenActivity;
import com.moying.energyring.myAcativity.Pk.Pk_CheckIn;
import com.moying.energyring.myAcativity.Pk.Pk_Daypk;
import com.moying.energyring.myAcativity.Pk.Pk_Gui;
import com.moying.energyring.myAcativity.Pk.pk_ZanRanking;
import com.moying.energyring.network.saveFile;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Field;

/**
 * Created by Admin on 2016/3/25.
 */
public class Fragment2_Pk extends Fragment {

    private View parentView;
    private FlexboxLayout myflexbox;
    public SimpleDraweeView pk_check_gif;
    private ImageView pk_check_simple;
    private LinearLayout pk_check_Lin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (parentView == null) {
//            if (saveFile.getShareData("islogin", getActivity()).equals("false")) {
//                ((MainActivity) getActivity()).backFragment(0);
//            }
            parentView = inflater.inflate(R.layout.fragment2_pk, null);
            initTitle(parentView);
            initView(parentView);
        }
        ViewGroup parent = (ViewGroup) parentView.getParent();
        if (parent != null) {
            parent.removeView(parentView);
        }
        return parentView;
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Fragment2_Pk"); //统计页面，"MainScreen"为页面名称，可自定义
        pkData(saveFile.BaseUrl + saveFile.pkUrl);
        checkData(saveFile.BaseUrl + saveFile.Check_Url);//是否签到
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Fragment2_Pk");
    }

    View title_Include;
    private void initTitle(View view){
        title_Include = view.findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            title_Include.setElevation(2f);//阴影
//        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setVisibility(View.GONE);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#000000"));
        cententtxt.setText("PK");
        StaticData.ViewScale(title_Include, 0, 88);
//         right_Btn = (Button)title_Include.findViewById(R.id.right_Btn);
//        right_Btn.setVisibility(View.VISIBLE);
//        right_Btn.setTextColor(Color.parseColor("#676767"));
//        right_Btn.setText("每日汇总");
//        right_Btn.setOnClickListener(new right_Btn());
    }


    public void initView(View view) {

        myflexbox = (FlexboxLayout) view.findViewById(R.id.myflexbox);
        Button pk_day_btn = (Button) view.findViewById(R.id.pk_day_btn);
        Button pk_comm_btn = (Button) view.findViewById(R.id.pk_comm_btn);
        pk_check_Lin = (LinearLayout) view.findViewById(R.id.pk_check_Lin);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 70);
        pk_check_Lin.setPadding(0, 0, pad, 0);
        RelativeLayout icon_Rel = (RelativeLayout) view.findViewById(R.id.icon_Rel);
        StaticData.ViewScale(icon_Rel, 200, 200);
        icon_Rel.setGravity(Gravity.CENTER);
        pk_check_simple = (ImageView) view.findViewById(R.id.pk_check_simple);
        pk_check_simple.setImageResource(R.drawable.pk_check_before);
        pk_check_gif = (SimpleDraweeView) view.findViewById(R.id.pk_check_gif);

        RelativeLayout.LayoutParams gifParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int padleft = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 66);
        int padtop = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 70);
        gifParams.setMargins(padleft, padtop, 0, 0);
        pk_check_gif.setLayoutParams(gifParams);


        StaticData.ViewScale(pk_day_btn, 692, 252);
        StaticData.ViewScale(pk_comm_btn, 692, 252);
        StaticData.ViewScale(pk_check_Lin, 692, 252);
        StaticData.ViewScale(pk_check_simple, 200, 200);
        StaticData.ViewScale(pk_check_gif, 70, 70);
//        Uri uri = Uri.parse("res:///" + R.drawable.pk_check_before);
//        pk_check_simple.setImageURI(uri);

        pk_day_btn.setOnClickListener(new pk_day_btn());
        pk_check_Lin.setOnClickListener(new pk_check_Lin());
        pk_check_simple.setOnClickListener(new pk_check_simple());
        pk_comm_btn.setOnClickListener(new pk_comm_btn());
    }

//    private class right_Btn implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            right_Btn.setEnabled(false);
//            isReteDayPk(getActivity(), saveFile.BaseUrl + saveFile.Report_Status_Url);
//        }
//    }

    public class pk_day_btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Pk_Daypk.class);
            startActivity(intent);
        }
    }

    public class pk_check_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (check_Model.isData()) {
                Intent intent = new Intent(getActivity(), Pk_CheckIn.class);
                startActivity(intent);
            } else {
                pk_check_Lin.setEnabled(false);
                pk_check_gif.setVisibility(View.VISIBLE);
                Uri gifuri = Uri.parse("res:///" + R.drawable.pk_check_gif);
                addGif(getActivity(), pk_check_gif, gifuri);
                AddData(saveFile.BaseUrl + saveFile.CheckAdd_Url);//签到
            }
        }
    }

    private class pk_check_simple implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MobclickAgent.onEvent(getActivity(), "pkCheck");//簽到點擊統計
            pk_check_simple.setEnabled(false);
            pk_check_gif.setVisibility(View.VISIBLE);
            Uri gifuri = Uri.parse("res:///" + R.drawable.pk_check_gif);
            addGif(getActivity(), pk_check_gif, gifuri);
            AddData(saveFile.BaseUrl + saveFile.CheckAdd_Url);//签到
        }
    }

    private class pk_comm_btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Leran_AllPerson.class);
            startActivity(intent);
        }
    }


    private int[] badgeArr = {R.drawable.pk_report, R.drawable.pk_allday, R.drawable.pk_zan, R.drawable.pk_zanranking, R.drawable.pk_rule,R.drawable.pk_rete_icon};
    private String[] nameArr = {"次", "天", "个", "名", "徽章规则"};
    private String[] tagArr = {"总汇报次数", "累计天数", "获赞个数", "获赞排名", ""};

    public void imgBadge(Context context, FlexboxLayout myFlex, int count) {
        if (myFlex != null) {
            myFlex.removeAllViews();
        }
//        List<pk_Model.DataBean> modelData = model.getData();

        int size = model.getData().size();
        for (int i = 0; i < size; i++) {
            View myView = LayoutInflater.from(context).inflate(R.layout.pk_flexbox, null);
            LinearLayout pk_Lin = (LinearLayout) myView.findViewById(R.id.pk_Lin);
            ImageView pk_img = (ImageView) myView.findViewById(R.id.pk_img);
            TextView content_Txt = (TextView) myView.findViewById(R.id.content_Txt);
            TextView tag_Txt = (TextView) myView.findViewById(R.id.tag_Txt);
            StaticData.ViewScale(pk_img, 48, 48);

            pk_img.setImageResource(badgeArr[i]);
            final pk_Model.DataBean oneData = model.getData().get(i);
            if (oneData.getProjectName().equals("徽章规则")) {
                content_Txt.setText(oneData.getProjectName());
                tag_Txt.setVisibility(View.GONE);
            } else if (oneData.getProjectName().equals("一键汇总")){
                content_Txt.setText(oneData.getProjectName());
                tag_Txt.setVisibility(View.GONE);
            }else{
                tag_Txt.setText(oneData.getProjectName());
                int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 30);
                int pad2 = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 14);
                String nameStr = oneData.getNum() + oneData.getUnit();
                TextsColor(pad, pad2, nameStr, content_Txt);
            }

            RadioGroup.LayoutParams itemParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            StaticData.layoutParamsScale(itemParams, 216, 94);
            int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 15);
            itemParams.setMargins(pad, pad, pad, pad);
            pk_Lin.setLayoutParams(itemParams);
            pk_Lin.setTag(i);

            myFlex.addView(myView);
            pk_Lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();
                    pk_Model.DataBean tagData = model.getData().get(tag);
                    if (tagData.getProjectName().equals("获赞排名")) {
                        Intent intent = new Intent(getActivity(), pk_ZanRanking.class);
                        intent.putExtra("rankIng", tagData.getNum() + "");
                        intent.putExtra("rankIngCount", model.getData().get(tag - 1).getNum() + "");//赞个数
                        startActivity(intent);
                    } else if (tagData.getProjectName().equals("徽章规则")) {
                        Intent intent = new Intent(getActivity(), Pk_Gui.class);
                        startActivity(intent);
                    } else if (tagData.getProjectName().equals("总汇报次数") || tagData.getProjectName().equals("累计天数") || tagData.getProjectName().equals("获赞个数")) {
                        Intent intent = new Intent(getActivity(), PersonMyCenter.class);
                        intent.putExtra("UserID", "0");
                        intent.putExtra("tabType", "1");
                        startActivity(intent);
                    }else if (tagData.getProjectName().equals("一键汇总")){
                        v.setEnabled(false);
                        isReteDayPk(v,getActivity(), saveFile.BaseUrl + saveFile.Report_Status_Url);
                    }
                }
            });
        }
    }

    public void TextsColor(int size, int endsize, String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
        styledText.setSpan(sizeSpan, 0, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan endSizeSpan = new AbsoluteSizeSpan(endsize);
        styledText.setSpan(endSizeSpan, alltext.length() - 1, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        myTxt.setText(styledText);
    }

    public void addGif(final Context context, final SimpleDraweeView draweeView, Uri uri) {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, final Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (animatable != null) {
                    int duration = 0;//获取动图时间
                    try {
                        Field field = AbstractAnimatedDrawable.class.getDeclaredField("mTotalLoops");
                        field.setAccessible(true);
                        field.set(animatable, 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    animatable.start();
                    if (animatable instanceof AbstractAnimatedDrawable) { // 只有fresco 0.13.0+才有getDuration()的方法
                        duration = ((AbstractAnimatedDrawable) animatable).getDuration();
                    }
                    if (duration > 0) {
                        draweeView.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.DONUT)
                            @Override
                            public void run() {
                                if (animatable.isRunning()) {
                                    animatable.stop();
                                    pk_check_simple.setImageResource(R.drawable.pk_check_after);
//                                    Uri afterUri = Uri.parse("res:///" + R.drawable.pk_check_after);
//                                    pk_check_simple.setImageURI(afterUri);
                                }
                            }
                        }, duration);
                    }
                }
            }
        };

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setControllerListener(controllerListener)
                .setAutoPlayAnimations(false)//是否自动播放
                .build();
        draweeView.setController(controller);
    }


    public pk_Model model;

    public void pkData(String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", getActivity()) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", getActivity()));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    model = new Gson().fromJson(resultString, pk_Model.class);
                    if (model.isIsSuccess() && !model.getData().equals("[]")) {
//                        baseModel.addAll(listModel.getData());
                        imgBadge(getActivity(), myflexbox, 0);
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
                        pk_check_simple.setEnabled(false);
                        pk_check_simple.setImageResource(R.drawable.pk_check_after);
                    } else {
                        pk_check_gif.setEnabled(true);
                        pk_check_simple.setImageResource(R.drawable.pk_check_before);
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
                        pk_check_Lin.setEnabled(true);
                        Intent intent = new Intent(getActivity(), JiFenActivity.class);
                        intent.putExtra("jifen", model.getData());
                        startActivity(intent);
                        Toast.makeText(getActivity(), "签到成功", Toast.LENGTH_SHORT);
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
                    if (integral == -1){
                        Toast.makeText(context, "请汇报更多pk", Toast.LENGTH_SHORT).show();
                    }else if (integral == 0){
                        //汇报达到上限没分
                        Toast.makeText(context, "汇报成功", Toast.LENGTH_SHORT).show();
                    }else if (integral > 0){
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


}
