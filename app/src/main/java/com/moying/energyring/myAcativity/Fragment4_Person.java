package com.moying.energyring.myAcativity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.UserInfo_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Person.PersonDeaft;
import com.moying.energyring.myAcativity.Person.PersonMyCenter;
import com.moying.energyring.myAcativity.Person.Person_Data;
import com.moying.energyring.myAcativity.Person.Person_Integral;
import com.moying.energyring.myAcativity.Person.Person_Notice;
import com.moying.energyring.myAcativity.Person.Person_PiontsList;
import com.moying.energyring.myAcativity.Person.Person_Relus;
import com.moying.energyring.myAcativity.Person.Person_Set;
import com.moying.energyring.myAcativity.Person.Person_Shop;
import com.moying.energyring.network.saveFile;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Admin on 2016/3/25.
 */
public class Fragment4_Person extends Fragment {
    private View parentView;
    private SimpleDraweeView personBg_simple, user_simple;
    private TextView userName_Txt, userCount_Txt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (parentView == null) {
            parentView = inflater.inflate(R.layout.fragment4_person, null);


            initView(parentView);


        }
        ViewGroup parent = (ViewGroup) parentView.getParent();
        if (parent != null) {
            parent.removeView(parentView);
        }
        return parentView;
    }


    private void initView(View view) {
        personBg_simple = (SimpleDraweeView) view.findViewById(R.id.personBg_simple);
        user_simple = (SimpleDraweeView) view.findViewById(R.id.user_simple);
        userName_Txt = (TextView) view.findViewById(R.id.userName_Txt);
        userCount_Txt = (TextView) view.findViewById(R.id.userCount_Txt);
        LinearLayout user_Lin = (LinearLayout) view.findViewById(R.id.user_Lin);
        RelativeLayout usericon_Rel = (RelativeLayout) view.findViewById(R.id.usericon_Rel);
        ImageView fen_icon = (ImageView) view.findViewById(R.id.fen_icon);
        LinearLayout data_Lin = (LinearLayout) view.findViewById(R.id.data_Lin);
        ImageView person_nom_icon = (ImageView) view.findViewById(R.id.person_nom_icon);
        ImageView person_data_icon = (ImageView) view.findViewById(R.id.person_data_icon);
        ImageView person_cao_icon = (ImageView) view.findViewById(R.id.person_cao_icon);
        ImageView person_fen_icon = (ImageView) view.findViewById(R.id.person_fen_icon);
        RelativeLayout neng_Rel = (RelativeLayout) view.findViewById(R.id.neng_Rel);
        RelativeLayout set_Rel = (RelativeLayout) view.findViewById(R.id.set_Rel);
        RelativeLayout bang_Rel = (RelativeLayout) view.findViewById(R.id.bang_Rel);
        RelativeLayout gui_Rel = (RelativeLayout) view.findViewById(R.id.gui_Rel);
        RelativeLayout che_Rel = (RelativeLayout) view.findViewById(R.id.che_Rel);
        ImageView person_neng_icon = (ImageView) view.findViewById(R.id.person_neng_icon);
        ImageView person_set_icon = (ImageView) view.findViewById(R.id.person_set_icon);
        ImageView person_bang_icon = (ImageView) view.findViewById(R.id.person_bang_icon);
        ImageView person_gui_icon = (ImageView) view.findViewById(R.id.person_gui_icon);
        ImageView person_che_icon = (ImageView) view.findViewById(R.id.person_che_icon);
        ImageView set_arrow = (ImageView) view.findViewById(R.id.set_arrow);
        ImageView gui_arrow = (ImageView) view.findViewById(R.id.gui_arrow);
        ImageView che_arrow = (ImageView) view.findViewById(R.id.che_arrow);
        TextView neng_Txt = (TextView) view.findViewById(R.id.neng_Txt);

        String InviteCode = saveFile.getShareData("InviteCode",getActivity());
        neng_Txt.setText(InviteCode);

        StaticData.ViewScale(personBg_simple, 0, 480);
        StaticData.ViewScale(user_simple, 148, 148);
        StaticData.ViewScale(fen_icon, 30, 30);
        StaticData.ViewScale(person_nom_icon, 84, 96);
        StaticData.ViewScale(person_data_icon, 84, 96);
        StaticData.ViewScale(person_cao_icon, 84, 96);
        StaticData.ViewScale(person_fen_icon, 84, 96);
        StaticData.ViewScale(neng_Rel, 0, 120);
        StaticData.ViewScale(set_Rel, 0, 120);
        StaticData.ViewScale(bang_Rel, 0, 120);
        StaticData.ViewScale(gui_Rel, 0, 120);
        StaticData.ViewScale(che_Rel, 0, 120);
        StaticData.ViewScale(person_neng_icon, 64, 64);
        StaticData.ViewScale(person_set_icon, 64, 64);
        StaticData.ViewScale(person_bang_icon, 64, 64);
        StaticData.ViewScale(person_gui_icon, 64, 64);
        StaticData.ViewScale(person_che_icon, 64, 64);
        StaticData.ViewScale(set_arrow, 16, 30);
        StaticData.ViewScale(gui_arrow, 16, 30);
        StaticData.ViewScale(che_arrow, 16, 30);


        layoutmarginTop(getActivity(), usericon_Rel);
        layoutmarginLinTop(getActivity(), data_Lin);

        personBg_simple.setOnClickListener(new personBg_simple());
        person_nom_icon.setOnClickListener(new person_nom_icon());
        person_cao_icon.setOnClickListener(new person_cao_icon());
        person_data_icon.setOnClickListener(new person_data_icon());
        person_fen_icon.setOnClickListener(new person_fen_icon());
        set_Rel.setOnClickListener(new set_Rel());
        bang_Rel.setOnClickListener(new bang_Rel());
        che_Rel.setOnClickListener(new che_Rel());
        gui_Rel.setOnClickListener(new gui_Rel());
//        RoundingParams roundingParams = RoundingParams.asCircle();
//        roundingParams.setRoundAsCircle(true);
//        user_simple.getHierarchy().setRoundingParams(roundingParams);
    }


    private void layoutmarginTop(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 230);
        int padleft = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 56);
        Params.setMargins(padleft, pad, 0, 0);
        view.setLayoutParams(Params);
    }

    private void layoutmarginLinTop(Context context, View view) {
        RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        StaticData.layoutParamsScale(Params, 662, 130);
        int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 415);
        Params.setMargins(0, pad, 0, 0);
        view.setLayoutParams(Params);
    }


//    private void initData() {
//
//        Uri headUri = Uri.parse("http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0");
//        myHead_Simple.setImageURI(headUri);
//    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private class personBg_simple implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), PersonMyCenter.class);
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


    //我的资料
    private class person_data_icon implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Person_Data.class);
            startActivity(intent);
        }
    }

    //草稿箱
    private class person_cao_icon implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), PersonDeaft.class);
            startActivity(intent);
        }
    }


    //积分记录
    private class person_fen_icon implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Person_Integral.class);
            startActivity(intent);
        }
    }

    //设置
    private class set_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Person_Set.class);
            intent.putExtra("UserID", userModel.getData().getUserID() + "");
            startActivity(intent);

        }
    }

    //积分排行榜
    private class bang_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Person_PiontsList.class);
            intent.putExtra("rankIng", 5 + "");
            intent.putExtra("rankIngCount", 5 + "");//赞个数
            startActivity(intent);
        }
    }

    //积分规则
    private class gui_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MobclickAgent.onEvent(getActivity(), "guiClick");//统计页签
            Intent intent = new Intent(getActivity(), Person_Relus.class);
            startActivity(intent);
        }
    }


    //积分商城
    private class che_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MobclickAgent.onEvent(getActivity(), "person_Shop");//统计页签
            Intent intent = new Intent(getActivity(), Person_Shop.class);
            intent.putExtra("Integral",userModel.getData().getIntegral()+"");
//            intent.putExtra("rankIng", 5 + "");
//            intent.putExtra("rankIngCount", 5 + "");//赞个数
            startActivity(intent);
        }
    }

    private String userId = "0";

    private void initData() {
        UserData(getActivity(), saveFile.BaseUrl + saveFile.UserInfo_Url + "?UserID=" + userId);
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
                        userCount_Txt.setText(oneData.getIntegral()+"");
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


}
