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
import com.moying.energyring.Model.UserInfo_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.ImagePickerActivity;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Person.PersonDeaft;
import com.moying.energyring.myAcativity.Person.PersonMyCenter;
import com.moying.energyring.myAcativity.Person.Person_Data;
import com.moying.energyring.myAcativity.Person.Person_Integral;
import com.moying.energyring.myAcativity.Person.Person_LineView;
import com.moying.energyring.myAcativity.Person.Person_Notice;
import com.moying.energyring.myAcativity.Person.Person_PiontsList;
import com.moying.energyring.myAcativity.Person.Person_Relus;
import com.moying.energyring.myAcativity.Person.Person_Set;
import com.moying.energyring.myAcativity.Person.Person_Shop;
import com.moying.energyring.myAcativity.Pk.Pk_Guide;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

import static com.moying.energyring.myAcativity.Person.PersonMyCenter.REQUEST_CODE_IMAGE_PICK_PERSONHEAD;

/**
 * Created by Admin on 2016/3/25.
 */
public class Fragment4_Person extends Fragment {
    private View parentView;
    private SimpleDraweeView personBg_simple, user_simple;
    private TextView userName_Txt, userCount_Txt, nommunrend_Txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (parentView == null) {
            parentView = inflater.inflate(R.layout.fragment4_person, null);


            initView(parentView);
            initGuide();

        }
        ViewGroup parent = (ViewGroup) parentView.getParent();
        if (parent != null) {
            parent.removeView(parentView);
        }
        return parentView;
    }

    private void initGuide(){
        if (saveFile.getShareData("isGuidePer",getActivity()).equals("false")){
            Intent intent = new Intent(getActivity(), Pk_Guide.class);
            intent.putExtra("guideId","3");
            startActivity(intent);
        }
        saveFile.saveShareData("isGuidePer","true",getActivity());
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
        nommunrend_Txt = (TextView) view.findViewById(R.id.nommunrend_Txt);
        ImageView person_data_icon = (ImageView) view.findViewById(R.id.person_data_icon);
        ImageView person_cao_icon = (ImageView) view.findViewById(R.id.person_cao_icon);
        ImageView person_fen_icon = (ImageView) view.findViewById(R.id.person_fen_icon);
        RelativeLayout neng_Rel = (RelativeLayout) view.findViewById(R.id.neng_Rel);
        RelativeLayout set_Rel = (RelativeLayout) view.findViewById(R.id.set_Rel);
        RelativeLayout bang_Rel = (RelativeLayout) view.findViewById(R.id.bang_Rel);
        RelativeLayout gui_Rel = (RelativeLayout) view.findViewById(R.id.gui_Rel);
        RelativeLayout che_Rel = (RelativeLayout) view.findViewById(R.id.che_Rel);
        RelativeLayout line_Rel = (RelativeLayout) view.findViewById(R.id.line_Rel);
        ImageView person_neng_icon = (ImageView) view.findViewById(R.id.person_neng_icon);
        ImageView person_set_icon = (ImageView) view.findViewById(R.id.person_set_icon);
        ImageView person_bang_icon = (ImageView) view.findViewById(R.id.person_bang_icon);
        ImageView person_gui_icon = (ImageView) view.findViewById(R.id.person_gui_icon);
        ImageView person_che_icon = (ImageView) view.findViewById(R.id.person_che_icon);
        ImageView person_line_icon = (ImageView) view.findViewById(R.id.person_line_icon);
        ImageView set_arrow = (ImageView) view.findViewById(R.id.set_arrow);
        ImageView gui_arrow = (ImageView) view.findViewById(R.id.gui_arrow);
        ImageView che_arrow = (ImageView) view.findViewById(R.id.che_arrow);
        ImageView line_arrow = (ImageView) view.findViewById(R.id.line_arrow);
        TextView neng_Txt = (TextView) view.findViewById(R.id.neng_Txt);

        String InviteCode = saveFile.getShareData("InviteCode", getActivity());
        neng_Txt.setText(InviteCode);

        StaticData.ViewScale(personBg_simple, 0, 480);
        StaticData.ViewScale(user_simple, 148, 148);
        StaticData.ViewScale(fen_icon, 30, 30);
        StaticData.ViewScale(person_nom_icon, 84, 96);
        StaticData.ViewScale(person_data_icon, 84, 96);
        StaticData.ViewScale(person_cao_icon, 84, 96);
        StaticData.ViewScale(person_fen_icon, 84, 96);
        StaticData.ViewScale(neng_Rel, 0, 100);
        StaticData.ViewScale(set_Rel, 0, 100);
        StaticData.ViewScale(line_Rel, 0, 100);
        StaticData.ViewScale(bang_Rel, 0, 100);
        StaticData.ViewScale(gui_Rel, 0, 100);
        StaticData.ViewScale(che_Rel, 0, 100);
        StaticData.ViewScale(person_neng_icon, 64, 64);
        StaticData.ViewScale(person_set_icon, 64, 64);
        StaticData.ViewScale(person_bang_icon, 64, 64);
        StaticData.ViewScale(person_gui_icon, 64, 64);
        StaticData.ViewScale(person_che_icon, 64, 64);
        StaticData.ViewScale(person_line_icon, 64, 64);
        StaticData.ViewScale(set_arrow, 16, 30);
        StaticData.ViewScale(gui_arrow, 16, 30);
        StaticData.ViewScale(che_arrow, 16, 30);
        StaticData.ViewScale(line_arrow, 16, 30);
        StaticData.ViewScale(nommunrend_Txt, 20, 20);


        layoutmarginTop(getActivity(), usericon_Rel);
        layoutmarginLinTop(getActivity(), data_Lin);

        user_simple.setOnClickListener(new user_simple());
        personBg_simple.setOnClickListener(new personBg_simple());
        person_nom_icon.setOnClickListener(new person_nom_icon());
        person_cao_icon.setOnClickListener(new person_cao_icon());
        person_data_icon.setOnClickListener(new person_data_icon());
        person_fen_icon.setOnClickListener(new person_fen_icon());
        set_Rel.setOnClickListener(new set_Rel());
        bang_Rel.setOnClickListener(new bang_Rel());
        che_Rel.setOnClickListener(new che_Rel());
        gui_Rel.setOnClickListener(new gui_Rel());
        line_Rel.setOnClickListener(new line_Rel());
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
            if (userModel != null) {
                intent.putExtra("UserID", userModel.getData().getUserID() + "");
            }
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
            intent.putExtra("Integral", userModel.getData().getIntegral() + "");
//            intent.putExtra("rankIng", 5 + "");
//            intent.putExtra("rankIngCount", 5 + "");//赞个数
            startActivity(intent);
        }
    }

    private String userId = "0";

    private void initData() {
        UserData(getActivity(), saveFile.BaseUrl + saveFile.UserInfo_Url + "?UserID=" + userId);
        hasMesData(getActivity(), saveFile.BaseUrl + saveFile.NoticeHasMes_Url);
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
                        userCount_Txt.setText(oneData.getIntegral() + "积分");
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
                        } else {
                            nommunrend_Txt.setVisibility(View.INVISIBLE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String path = data.getStringExtra("path");
//            Uri imgUri = Uri.parse(path);
            if (!TextUtils.isEmpty(path)) {


                if (requestCode == REQUEST_CODE_IMAGE_PICK_PERSONHEAD) {
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
                        Uri imgUri = Uri.fromFile(file);
                        user_simple.setImageURI(imgUri);
//                            addSimplePath(user_simple, path);
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
