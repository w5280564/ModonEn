package com.moying.energyring.myAcativity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.AddPhoto_Model;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.ShareContent;
import com.moying.energyring.Model.UserInfo_Model;
import com.moying.energyring.Model.isFristSee_Model;
import com.moying.energyring.Model.likes_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.GuideUtil;
import com.moying.energyring.StaticData.ImagePickerActivity;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.Energy.myShareActivity;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_And_Other;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_My_Badge;
import com.moying.energyring.myAcativity.Person.Person_Center_FansAndAtten;
import com.moying.energyring.myAcativity.Person.Person_Exchange;
import com.moying.energyring.myAcativity.Person.Person_Feedback;
import com.moying.energyring.myAcativity.Person.Person_LineView;
import com.moying.energyring.myAcativity.Person.Person_Neng;
import com.moying.energyring.myAcativity.Person.Person_Notice;
import com.moying.energyring.myAcativity.Person.Person_Relus;
import com.moying.energyring.myAcativity.Person.Person_Set;
import com.moying.energyring.myAcativity.Person.Person_Task;
import com.moying.energyring.myAcativity.Person.Person_Zong;
import com.moying.energyring.myAcativity.Pk.Pk_Daypk;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.AutoScaleTextView;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

import static com.moying.energyring.myAcativity.Person.PersonMyCenter.REQUEST_CODE_IMAGE_PICK_PERSONHEAD;

/**
 * Created by Admin on 2016/3/25.
 */
public class Fragment4_PersonNew extends Fragment {
    private View parentView;
    private SimpleDraweeView personBg_simple, user_simple;
    private TextView userName_Txt, userCount_Txt, myInto_Txt, fans_Txt, fo_Txt, idearend_Txt, post_Txt;
    private ImageView personnew_sex_Img, grade_Img;
    private String InviteCode;
    private AutoScaleTextView nommunrend_Txt;
    private GuideUtil guideUtil;
    private TextView idea_Unrend_Txt;
    private ImageView invite_Img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (parentView == null) {
            parentView = inflater.inflate(R.layout.fragment4_personnew, null);

            initView(parentView);

        }
        ViewGroup parent = (ViewGroup) parentView.getParent();
        if (parent != null) {
            parent.removeView(parentView);
        }
        return parentView;
    }

    private void initGuide() {
//        if (saveFile.getShareData("isGuidePer", getActivity()).equals("false")) {
//            Intent intent = new Intent(getActivity(), Pk_Guide.class);
//            intent.putExtra("guideId", "2");
//            startActivity(intent);
//        }

//        saveFile.saveShareData("isGuidePer", "true", getActivity());

        guideFristData(getActivity(), saveFile.BaseUrl + saveFile.GuidePerFirst_Url, 0);//展示功能提醒页
        guideUtil = GuideUtil.getInstance();
    }


    private void initView(View view) {

        ImageView personnew_set_icon = (ImageView) view.findViewById(R.id.personnew_set_icon);
        ImageView personnew_nom_icon = (ImageView) view.findViewById(R.id.personnew_nom_icon);
        nommunrend_Txt = (AutoScaleTextView) view.findViewById(R.id.nommunrend_Txt);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int padLeft = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 30);
        int padBot = (int) (Float.parseFloat(saveFile.getShareData("scale", getActivity())) * 40);
        params.setMargins(padLeft, 0, 0, padBot);
        StaticData.layoutParamsScale(params, 40, 40);
        nommunrend_Txt.setLayoutParams(params);

//        nommunrend_Txt.setText(1011+"");
        RelativeLayout usericon_Rel = (RelativeLayout) view.findViewById(R.id.usericon_Rel);
        personBg_simple = (SimpleDraweeView) view.findViewById(R.id.personBg_simple);
        user_simple = (SimpleDraweeView) view.findViewById(R.id.user_simple);
        personnew_sex_Img = (ImageView) view.findViewById(R.id.personnew_sex_Img);
        ImageView personnew_com_icon = (ImageView) view.findViewById(R.id.personnew_com_icon);
        grade_Img = (ImageView) view.findViewById(R.id.grade_Img);

        RelativeLayout.LayoutParams comParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        comParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        comParams.addRule(RelativeLayout.CENTER_VERTICAL, R.id.personBg_simple);
        personnew_com_icon.setLayoutParams(comParams);

        RelativeLayout user_Lin = (RelativeLayout) view.findViewById(R.id.user_Lin);
        userName_Txt = (TextView) view.findViewById(R.id.userName_Txt);
        userCount_Txt = (TextView) view.findViewById(R.id.userCount_Txt);
        myInto_Txt = (TextView) view.findViewById(R.id.myInto_Txt);
        ImageView fen_icon = (ImageView) view.findViewById(R.id.fen_icon);
        LinearLayout zan_Lin = (LinearLayout) view.findViewById(R.id.zan_Lin);
        LinearLayout fans_Lin = (LinearLayout) view.findViewById(R.id.fans_Lin);
        LinearLayout fo_Lin = (LinearLayout) view.findViewById(R.id.fo_Lin);
        LinearLayout post_Lin = (LinearLayout) view.findViewById(R.id.post_Lin);
        fans_Txt = (TextView) view.findViewById(R.id.fans_Txt);
        fo_Txt = (TextView) view.findViewById(R.id.fo_Txt);
        post_Txt = (TextView) view.findViewById(R.id.post_Txt);
        idea_Unrend_Txt = (TextView) view.findViewById(R.id.idea_Unrend_Txt);

        LinearLayout data_Lin = (LinearLayout) view.findViewById(R.id.data_Lin);
        StaticData.ViewScale(personnew_set_icon, 50, 50);
        StaticData.ViewScale(personnew_nom_icon, 50, 50);
        viewTouchDelegate.expandViewTouchDelegate(personnew_set_icon, 100, 100, 100, 100);
        viewTouchDelegate.expandViewTouchDelegate(personnew_nom_icon, 100, 100, 100, 100);
//        StaticData.ViewScale(nommunrend_Txt, 40, 40);
        StaticData.ViewScale(personBg_simple, 0, 366);
        StaticData.ViewScale(user_simple, 120, 120);
        StaticData.ViewScale(personnew_com_icon, 104, 76);
        StaticData.ViewScale(fen_icon, 18, 18);
        StaticData.ViewScale(zan_Lin, 0, 130);
        StaticData.ViewScale(grade_Img, 56, 28);

        RelativeLayout task_Rel = (RelativeLayout) view.findViewById(R.id.task_Rel);
        RelativeLayout hui_Rel = (RelativeLayout) view.findViewById(R.id.hui_Rel);
        RelativeLayout idea_Rel = (RelativeLayout) view.findViewById(R.id.idea_Rel);
        RelativeLayout line_Rel = (RelativeLayout) view.findViewById(R.id.line_Rel);
        RelativeLayout zong_Rel = (RelativeLayout) view.findViewById(R.id.zong_Rel);
        RelativeLayout che_Rel = (RelativeLayout) view.findViewById(R.id.che_Rel);
        RelativeLayout dui_Rel = (RelativeLayout) view.findViewById(R.id.dui_Rel);

        ImageView person_task_icon = (ImageView) view.findViewById(R.id.person_task_icon);
        ImageView personnew_hui_icon = (ImageView) view.findViewById(R.id.personnew_hui_icon);
        ImageView personnew_idea_icon = (ImageView) view.findViewById(R.id.personnew_idea_icon);
        ImageView person_line_icon = (ImageView) view.findViewById(R.id.person_line_icon);
        ImageView person_hui_icon = (ImageView) view.findViewById(R.id.person_hui_icon);
        ImageView person_che_icon = (ImageView) view.findViewById(R.id.person_che_icon);
        ImageView person_dui_icon = (ImageView) view.findViewById(R.id.person_dui_icon);

        ImageView task_arrow = (ImageView) view.findViewById(R.id.task_arrow);
        ImageView set_arrow = (ImageView) view.findViewById(R.id.set_arrow);
        ImageView idea_arrow = (ImageView) view.findViewById(R.id.idea_arrow);
        ImageView line_arrow = (ImageView) view.findViewById(R.id.line_arrow);
        ImageView hui_arrow = (ImageView) view.findViewById(R.id.hui_arrow);
        ImageView che_arrow = (ImageView) view.findViewById(R.id.che_arrow);
        ImageView dui_arrow = (ImageView) view.findViewById(R.id.dui_arrow);
        TextView neng_Txt = (TextView) view.findViewById(R.id.neng_Txt);
        idearend_Txt = (TextView) view.findViewById(R.id.idearend_Txt);
        invite_Img = (ImageView) view.findViewById(R.id.invite_Img);
//        InviteCode = saveFile.getShareData("InviteCode", getActivity());
//        if (!InviteCode.equals("false")) {
//            neng_Txt.setText(InviteCode);
//        }
        StaticData.ViewScale(task_Rel, 0, 130);
        StaticData.ViewScale(hui_Rel, 0, 130);
        StaticData.ViewScale(idea_Rel, 0, 130);
        StaticData.ViewScale(line_Rel, 0, 130);
        StaticData.ViewScale(che_Rel, 0, 130);
        StaticData.ViewScale(zong_Rel, 0, 130);
        StaticData.ViewScale(dui_Rel, 0, 130);
        StaticData.ViewScale(person_task_icon, 64, 64);
        StaticData.ViewScale(personnew_hui_icon, 64, 64);
        StaticData.ViewScale(personnew_idea_icon, 64, 64);
        StaticData.ViewScale(person_che_icon, 64, 64);
        StaticData.ViewScale(person_line_icon, 64, 64);
        StaticData.ViewScale(person_hui_icon, 64, 64);
        StaticData.ViewScale(person_dui_icon, 64, 64);
        StaticData.ViewScale(task_arrow, 60, 60);
        StaticData.ViewScale(set_arrow, 60, 60);
        StaticData.ViewScale(idea_arrow, 60, 60);
        StaticData.ViewScale(line_arrow, 60, 60);
        StaticData.ViewScale(hui_arrow, 60, 60);
        StaticData.ViewScale(che_arrow, 60, 60);
        StaticData.ViewScale(dui_arrow, 60, 60);
        StaticData.ViewScale(invite_Img, 260, 128);

        seximgMargin(getActivity(), personnew_sex_Img);
        layoutmarginTop(getActivity(), usericon_Rel);
//        layoutmarginZan(getActivity(), zan_Lin);
//        layoutmarginLinTop(getActivity(), data_Lin);

        user_simple.setOnClickListener(new user_simple());
        personBg_simple.setOnClickListener(new personBg_simple());
        personnew_set_icon.setOnClickListener(new personnew_set_icon());
        personnew_nom_icon.setOnClickListener(new person_nom_icon());
        task_Rel.setOnClickListener(new task_Rel());
        hui_Rel.setOnClickListener(new hui_Rel());
        idea_Rel.setOnClickListener(new idea_Rel());
        line_Rel.setOnClickListener(new line_Rel());
        zong_Rel.setOnClickListener(new zong_Rel());
        che_Rel.setOnClickListener(new che_Rel());
        fans_Lin.setOnClickListener(new fans_Lin());
        fo_Lin.setOnClickListener(new fo_Lin());
        post_Lin.setOnClickListener(new post_Lin());
        dui_Rel.setOnClickListener(new dui_Rel());
        invite_Img.setOnClickListener(new invite_Img());
    }

    private void changeRedGui(TextView redTxt) {
        if (saveFile.getShareData("RedHui", getActivity()).equals("false")) {
            redTxt.setVisibility(View.VISIBLE);
        } else {
            redTxt.setVisibility(View.INVISIBLE);
        }
    }


    private void seximgMargin(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        StaticData.layoutParamsScale(Params, 25, 25);
        int padbot = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 82);
        int padleft = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 80);
        Params.setMargins(padleft, 0, 0, padbot);
        view.setLayoutParams(Params);
    }

    private void layoutmarginTop(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 220);
        int padleft = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 40);
        Params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.personBg_simple);
        Params.setMargins(padleft, 0, 0, 0);
        view.setLayoutParams(Params);
    }

    private void layoutmarginZan(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        StaticData.layoutParamsScale(Params, 710, 60);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 430);
        Params.setMargins(0, pad, 0, 0);
        view.setLayoutParams(Params);
    }

    private void layoutmarginLinTop(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        StaticData.layoutParamsScale(Params, 710, 0);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 530);
        Params.setMargins(0, pad, 0, 0);
        view.setLayoutParams(Params);
    }


//    private void initData() {
//        Uri headUri = Uri.parse("http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0");
//        myHead_Simple.setImageURI(headUri);
//    }


    @Override
    public void onResume() {
        super.onResume();
        changeRedGui(idearend_Txt);
        initData();
        initGuide();
    }

    private class user_simple implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), PersonMyCenter.class);
//            startActivity(intent);
//            showHead(getActivity(), user_simple);
            Intent intentimagepic = new Intent(getActivity(), ImagePickerActivity.class);
            startActivityForResult(intentimagepic, REQUEST_CODE_IMAGE_PICK_PERSONHEAD);
        }
    }

    private class personBg_simple implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), PersonMyCenter.class);
//            startActivity(intent);
            Intent intent = new Intent(getActivity(), PersonMyCenter_And_Other.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            startActivity(intent);

        }
    }


    private class fans_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(getActivity(), Person_Center_FansAndAtten.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            intent.putExtra("titleName", "粉丝");
            intent.putExtra("Type", "2");
            startActivity(intent);
        }
    }


    private class fo_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(getActivity(), Person_Center_FansAndAtten.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            intent.putExtra("titleName", "关注");
            intent.putExtra("Type", "1");
            startActivity(intent);
        }
    }

    private class post_Lin extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
//            Intent intent = new Intent(getActivity(), PersonMyCenter.class);
//            intent.putExtra("UserID", "0");
//            intent.putExtra("tabType", "3");
//            startActivity(intent);
            Intent intent = new Intent(getActivity(), PersonMyCenter_And_Other.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            startActivity(intent);
        }
    }

    private class task_Rel extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(getActivity(), Person_Task.class);
            intent.putExtra("Integral", userModel.getData().getIntegral() + "");
            intent.putExtra("tabType", "1");
            startActivity(intent);
        }
    }

    private class dui_Rel extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(getActivity(), Person_Exchange.class);
            startActivity(intent);
        }
    }


    private class neng_Rel extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(getActivity(), Person_Neng.class);
            startActivity(intent);
        }
    }

    private class copy_Img extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            StaticData.copy(InviteCode, getActivity());
            Toast.makeText(getActivity(), "已复制", Toast.LENGTH_SHORT).show();
        }
    }

    //设置
    private class personnew_set_icon implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Person_Set.class);
            if (userModel != null) {
                intent.putExtra("UserID", userModel.getData().getUserID() + "");
            }
            startActivity(intent);

        }
    }

    //通知
    private class person_nom_icon implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Person_Notice.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            startActivity(intent);
        }
    }

    //徽章
    private class hui_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), Pk_Gui.class);
//            startActivity(intent);

            Intent intent = new Intent(getActivity(), PersonMyCenter_My_Badge.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            intent.putExtra("FilePath", userModel.getData().getProfilePicture());
            startActivity(intent);
        }
    }

    //我要吐槽
    private class idea_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Person_Feedback.class);
            startActivity(intent);
        }
    }

    //排行榜
    private class bang_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), Person_PiontsList.class);
//            intent.putExtra("rankIng", 5 + "");
//            intent.putExtra("rankIngCount", 5 + "");//赞个数
//            startActivity(intent);
            Intent intent = new Intent(getActivity(), Pk_Daypk.class);
            startActivity(intent);
        }
    }


    //积分商城
    private class che_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MobclickAgent.onEvent(getActivity(), "person_Shop");//统计页签
//            Intent intent = new Intent(getActivity(), Person_Shop.class);
//            intent.putExtra("Integral", userModel.getData().getIntegral() + "");
////            intent.putExtra("rankIng", 5 + "");
////            intent.putExtra("rankIngCount", 5 + "");//赞个数
//            startActivity(intent);

            Intent intent = new Intent(getActivity(), Person_Task.class);
            intent.putExtra("Integral", userModel.getData().getIntegral() + "");
//            intent.putExtra("tabType","0");
            startActivity(intent);

        }
    }


    //数据曲线
    private class line_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Person_LineView.class);
            startActivity(intent);

        }
    }

    private class invite_Img implements View.OnClickListener {
        @Override
        public void onClick(View view) {

//            String url = saveFile.BaseUrl + "Share/Invite?invitecode=" + saveFile.getShareData("InviteCode", getActivity());
//            Intent intent = new Intent(getActivity(), Person_PrivacyPolicy.class);
//            intent.putExtra("url", url);
//            startActivity(intent);

            String shareTitle = "我正在使用能量圈，快来和我一起培养新习惯吧~";
            String shareConStr = "能量圈--为了打造更好的自己" ;
            String shareUrl = saveFile.BaseUrl + "Share/Invite?invitecode=" + saveFile.getShareData("InviteCode", getActivity());
            ShareContent shareContent = new ShareContent(shareUrl, shareTitle, shareConStr,shareUrl);

            Intent intent = new Intent(getActivity(), myShareActivity.class);
            intent.putExtra("shareContent", shareContent);
            startActivity(intent);
        }
    }

    //    //我的资料
//    private class person_data_icon implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), Person_Data.class);
//            startActivity(intent);
//        }
//    }
//    //草稿箱
//    private class person_cao_icon implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), PersonDeaft.class);
//            startActivity(intent);
//        }
//    }
//    //积分记录
//    private class person_fen_icon implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), Person_Integral.class);
//            startActivity(intent);
//        }
//    }
    //积分攻略
    private class gui_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            MobclickAgent.onEvent(getActivity(), "guiClick");//统计页签
            Intent intent = new Intent(getActivity(), Person_Relus.class);
            startActivity(intent);
        }
    }

    //积分攻略
    private class zong_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            saveFile.saveShareData("RedHui", "true", getActivity());
            String imgpath = "";
//            if (baseModel.get(position).getFilePath() != null) {
//                imgpath = String.valueOf(baseModel.get(position).getFilePath());
//            }
//            String content = baseModel.get(position).getPostTitle();
//            String postId = baseModel.get(position).getPostID() + "";

//            String content = "";
//            String postId = "";
//            String todayString = getStringToday();
//            String url = saveFile.BaseUrl + "/Share/PersonReport?UserID=" + userModel.getData().getUserID() + "&date=" + todayString;
            Intent intent = new Intent(getActivity(), Person_Zong.class);
//            intent.putExtra("content", content);
//            intent.putExtra("postId", postId);
//            intent.putExtra("imgpath", imgpath);
//            intent.putExtra("url", url);
            startActivity(intent);
        }
    }

    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    private String userId = "0";

    private void initData() {
        UserData(getActivity(), saveFile.BaseUrl + saveFile.UserInfo_Url + "?UserID=" + userId);
        hasMesData(getActivity(), saveFile.BaseUrl + saveFile.NoticeHasMes_Url);
//        likesData(getActivity(), saveFile.BaseUrl + saveFile.Likes_Url);
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
                        if (!isFristModel.getData().isIs_FirstEditProfile_Remind()) {

//                            isFristSee();
//                            Intent intent = new Intent(getActivity(), Pk_Guide.class);
//                            intent.putExtra("guideId", "3");
//                            startActivity(intent);
                            guideUtil.initGuide(getActivity(), 3, 1);
                            updguidePer_Data(getActivity(), saveFile.BaseUrl + saveFile.upd_guidePerFirst_Url + "?str=" + "Is_FirstEditProfile_Remind");//展示功能提醒页
                        }
                        if (!isFristModel.getData().isIs_Suggest()) {
                            idea_Unrend_Txt.setVisibility(View.VISIBLE);
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
//                            StaticData.addPlace(personBg_simple, getActivity());//占位图
                            Uri bgUri = Uri.parse(String.valueOf(oneData.getCoverImg()));
                            personBg_simple.setImageURI(bgUri);
                        } else {
                            StaticData.PersonBg(personBg_simple);
                        }

                        if (oneData.getProfilePicture() != null) {
//                            StaticData.addPlaceRound(user_simple, getActivity());//占位图
                            Uri imgUri = Uri.parse(oneData.getProfilePicture());
                            user_simple.setImageURI(imgUri);
//                            addSimplePath(user_simple, oneData.getProfilePicture());
                        } else {
                            StaticData.lodingheadBg(user_simple);
                        }

                        userName_Txt.setText(oneData.getNickName());
                        userCount_Txt.setText(oneData.getIntegral() + "");
                        if (StaticData.isSpace(oneData.getBrief())) {
                            myInto_Txt.setText("简介：这个人太懒了，没有留下什么");
                        } else {
                            myInto_Txt.setText("简介：" + oneData.getBrief());
                        }

                        int sexId = oneData.getGender();

                        if (sexId == 1) {//男
                            personnew_sex_Img.setBackgroundResource(R.drawable.personnew_man);
                        } else if (sexId == 2) {// 女
                            personnew_sex_Img.setBackgroundResource(R.drawable.personnew_woman);
                        } else {
                            personnew_sex_Img.setVisibility(View.GONE);
                        }

                        StaticData.setGarde(grade_Img, oneData.getIntegralLevel());

                        fans_Txt.setText(oneData.getAttention() + "");
                        fo_Txt.setText(oneData.getAttention_Me() + "");
                        post_Txt.setText(oneData.getPostCount() + "");

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

    public void hasMesData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    BaseDataInt_Model baseModel = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                    if (baseModel.isIsSuccess()) {
                        if (baseModel.getData() != 0) {
                            nommunrend_Txt.setVisibility(View.VISIBLE);
                            nommunrend_Txt.setText(baseModel.getData() + "");
                            ((MainActivity) getActivity()).setVisi(0);
                        } else {
                            nommunrend_Txt.setVisibility(View.INVISIBLE);
                            ((MainActivity) getActivity()).setVisi(1);
                        }

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

    public void likesData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    likes_Model likeModel = new Gson().fromJson(resultString, likes_Model.class);
                    if (userModel.isIsSuccess() && !userModel.getData().equals("[]")) {
//                        UserInfo_Model.DataBean oneData = userModel.getData();


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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String path = data.getStringExtra("path");
//            Uri imgUri = Uri.parse(path);
            if (!TextUtils.isEmpty(path)) {
//                Uri imgUri = Uri.parse("file:// /"+path);
//                user_simple.setImageURI(imgUri);
//                if (requestCode == REQUEST_CODE_IMAGE_PICK_PERSONHEAD) {
                compressSingleListener(new File(path), Luban.FIRST_GEAR, REQUEST_CODE_IMAGE_PICK_PERSONHEAD);
//                }


            }
        }
    }

    //压缩图片
    private void compressSingleListener(File file, int gear, final int type) {
//        if (file.isEmpty()) {
//            return;
//        }

        Luban.compress(file, getActivity().getFilesDir())
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
//                        Uri imgUri = Uri.fromFile(file);
//                        user_simple.setImageURI(imgUri);
                        upload_PhotoData(type, getActivity(), saveFile.BaseUrl + saveFile.uploadPhoto_Url, file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
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
                    if (type == REQUEST_CODE_IMAGE_PICK_PERSONHEAD) {
                        //上传头像ID
                        AddPersonBg_AndHead(context, saveFile.BaseUrl + saveFile.PersonHead_Url + "?FileID=" + files, files);
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
                    UserData(getActivity(), saveFile.BaseUrl + saveFile.UserInfo_Url + "?UserID=" + userId);
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
//        popup_head.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Intent intentimagepic = new Intent(PersonMyCenter.this, ImagePickerActivity.class);
//                startActivityForResult(intentimagepic, REQUEST_CODE_IMAGE_PICK_PERSONHEAD);
//                return false;
//            }
//        });
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


}
