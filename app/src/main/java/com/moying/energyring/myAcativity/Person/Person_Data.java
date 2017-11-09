package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.waylenBaseView.wheel.pictime.ChangeBirthDialog_old;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

import static com.moying.energyring.myAcativity.Person.PersonMyCenter.REQUEST_CODE_IMAGE_PICK_PERSONHEAD;
import static com.moying.energyring.myAcativity.Person.Person_Set.person_Set;


public class Person_Data extends Activity {

    private TextView bir_Txt;
    private EditText Nick_input, email_input, intr_input;
    Button sex_man_Btn, sex_woman_Btn;
    SimpleDraweeView user_simple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__data);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();
        initView();
        initData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#909090"));
        cententtxt.setText("我的资料");
        Button right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setTextColor(Color.parseColor("#999999"));
        right_Btn.setText("保存");

//        right_Btn.setBackgroundResource(R.drawable.persondetails_out);
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);
//        StaticData.ViewScale(right_Btn, 48, 48);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            String emailStr = email_input.getText().toString().trim();
//            if (!StaticData.isEmail(emailStr)) {
//                Toast.makeText(Person_Data.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
//                return;
//            }
            EditUserInfo_Data(Person_Data.this, saveFile.BaseUrl + saveFile.EditInfo_Url);
        }
    }

    private void initView() {
        LinearLayout persondata_Lin = (LinearLayout) findViewById(R.id.persondata_Lin);

        RelativeLayout personData_Rel = (RelativeLayout) findViewById(R.id.personData_Rel);
        user_simple = (SimpleDraweeView) findViewById(R.id.user_simple);
        RelativeLayout nick_Rel = (RelativeLayout) findViewById(R.id.nick_Rel);
//        RelativeLayout name_Rel = (RelativeLayout) findViewById(R.id.name_Rel);
        RelativeLayout email_Rel = (RelativeLayout) findViewById(R.id.email_Rel);
        RelativeLayout intr_Rel = (RelativeLayout) findViewById(R.id.intr_Rel);
        RelativeLayout bir_Rel = (RelativeLayout) findViewById(R.id.bir_Rel);
        RelativeLayout sex_Rel = (RelativeLayout) findViewById(R.id.sex_Rel);

        ImageView head_Img = (ImageView) findViewById(R.id.head_Img);
        ImageView nick_Img = (ImageView) findViewById(R.id.nick_Img);
        ImageView bir_Img = (ImageView) findViewById(R.id.bir_Img);
        ImageView sex_Img = (ImageView) findViewById(R.id.sex_Img);
        ImageView email_Img = (ImageView) findViewById(R.id.email_Img);
        ImageView intr_Img = (ImageView) findViewById(R.id.intr_Img);
        sex_man_Btn = (Button) findViewById(R.id.sex_man_Img);
        sex_woman_Btn = (Button) findViewById(R.id.sex_woman_Img);

//        Nick_Txt = (TextView) findViewById(R.id.Nick_Txt);
//        sex_Txt = (TextView) findViewById(R.id.sex_Txt);
        bir_Txt = (TextView) findViewById(R.id.bir_Txt);
//        email_Txt = (TextView) findViewById(R.id.email_Txt);
//        intr_Txt = (TextView) findViewById(R.id.intr_Txt);

        Nick_input = (EditText) findViewById(R.id.Nick_input);
        email_input = (EditText) findViewById(R.id.email_input);
        intr_input = (EditText) findViewById(R.id.intr_input);
        StaticData.ViewScale(personData_Rel, 710, 160);
        StaticData.ViewScale(nick_Rel, 710, 160);
        StaticData.ViewScale(email_Rel, 710, 160);
        StaticData.ViewScale(intr_Rel, 710, 190);
        StaticData.ViewScale(bir_Rel, 710, 160);
        StaticData.ViewScale(sex_Rel, 710, 160);
        StaticData.ViewScale(head_Img, 44, 44);
        StaticData.ViewScale(nick_Img, 44, 44);
        StaticData.ViewScale(bir_Img, 44, 44);
        StaticData.ViewScale(sex_Img, 44, 44);
        StaticData.ViewScale(email_Img, 44, 44);
        StaticData.ViewScale(intr_Img, 44, 44);
        StaticData.ViewScale(sex_man_Btn, 120, 60);
        StaticData.ViewScale(sex_woman_Btn, 120, 60);
        StaticData.ViewScale(user_simple, 80, 80);

        personData_Rel.setOnClickListener(new personData_Rel());
        sex_Rel.setOnClickListener(new sex_Rel());
        bir_Rel.setOnClickListener(new bir_Rel());
        sex_man_Btn.setOnClickListener(new sex_man_Btn());
        sex_woman_Btn.setOnClickListener(new sex_woman_Btn());
        Nick_input.addTextChangedListener(new Nick_input());
        email_input.addTextChangedListener(new email_input());
        intr_input.addTextChangedListener(new intr_input());
    }

    private class sex_man_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            sex_man_Btn.setSelected(true);
            sex_woman_Btn.setSelected(false);
            userModel.getData().setGender(1);
        }
    }

    private class sex_woman_Btn implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            sex_man_Btn.setSelected(false);
            sex_woman_Btn.setSelected(true);
            userModel.getData().setGender(2);
        }
    }

    private class Nick_input implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            userModel.getData().setNickName(editable.toString());
        }
    }

    private class email_input implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            userModel.getData().setEmail(editable.toString());
        }
    }

    private class intr_input implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            userModel.getData().setBrief(editable.toString());
        }
    }

    private class sex_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            showSex(Person_Data.this, sex_Txt);
        }
    }

    private class personData_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intentimagepic = new Intent(Person_Data.this, ImagePickerActivity.class);
            startActivityForResult(intentimagepic, REQUEST_CODE_IMAGE_PICK_PERSONHEAD);
        }
    }

    private class bir_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ChangeBirthDialog_old mChangeBirthDialog = new ChangeBirthDialog_old(Person_Data.this);
            mChangeBirthDialog.setDate(1991, 11, 14);
            mChangeBirthDialog.show();
            mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog_old.OnBirthListener() {
                @Override
                public void onClick(String year, String month, String day) {
                    String time = year + "-" + month + "-" + day;
//                    startYear = Integer.parseInt(year);
//                    startMonth = Integer.parseInt(month);
//                    startDay = Integer.parseInt(day);
                    userModel.getData().setBirthday(time);
                    bir_Txt.setText(StaticData.getUserDate(time));
                }
            });
        }
    }

    private String userId = "0";

    private void initData() {
        UserData(Person_Data.this, saveFile.BaseUrl + saveFile.UserInfo_Url + "?UserID=" + userId);
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
                        updataMyData(userModel);
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

    private void updataMyData(UserInfo_Model myModel) {
        UserInfo_Model.DataBean oneData = myModel.getData();
        Nick_input.setText(oneData.getNickName());


//        Nick_Txt.setText(oneData.getNickName());
//        name_Txt.setText(oneData.getUserName());
        int sexId = oneData.getGender();
        if (sexId == 1) {
            sex_man_Btn.setSelected(true);
            sex_woman_Btn.setSelected(false);
        } else if (sexId == 2) {
            sex_man_Btn.setSelected(false);
            sex_woman_Btn.setSelected(true);
        } else {
            sex_man_Btn.setSelected(false);
            sex_woman_Btn.setSelected(false);
        }
        bir_Txt.setText(StaticData.Datatypetwo(oneData.getBirthday()));
//        email_Txt.setText(oneData.getEmail());
//        intr_Txt.setText(oneData.getBrief());
        email_input.setText(oneData.getEmail());
        intr_input.setText(oneData.getBrief());
        if (oneData.getProfilePicture() != null) {
            Uri imgUri = Uri.parse(oneData.getProfilePicture());
            user_simple.setImageURI(imgUri);
        } else {
            StaticData.lodingheadBg(user_simple);
        }
    }

    //修改用户资料
    public void EditUserInfo_Data(final Context context, String baseUrl) {
        UserInfo_Model.DataBean oneData = userModel.getData();
        JSONObject obj = new JSONObject();
        try {
            obj.put("UserID", oneData.getUserID());
            obj.put("UserName", oneData.getUserName());
            obj.put("NickName", oneData.getNickName());
            obj.put("Birthday", oneData.getBirthday());
            obj.put("Gender", oneData.getGender());
            obj.put("Email", oneData.getEmail());
            obj.put("Brief", oneData.getBrief());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        params.setAsJsonContent(true);
        params.setBodyContent(obj.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    BaseDataInt_Model baseModel = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                    if (baseModel.isIsSuccess()) {

                        if (baseModel.getData() == 0) {
                            Toast.makeText(context, "资料上传成功", Toast.LENGTH_SHORT).show();
                            person_Set.finish();
                            finish();
                        }else  if (baseModel.getData() == 50) {
                            Toast.makeText(context, "资料上传成功", Toast.LENGTH_SHORT).show();
                            person_Set.finish();
                            finish();
//                        if (saveFile.getShareData("isFristSetData", context).equals("false")) {
                            Intent intent = new Intent(Person_Data.this, setDataActivity.class);
                            startActivity(intent);
//                        }
//                        saveFile.saveShareData("isFristSetData", "true", context);
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


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (RESULT_OK == resultCode) {
//            userModel = data.getParcelableExtra("mymodel");
//            updataMyData(userModel);
//        }
//    }


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

        Luban.compress(file, this.getFilesDir())
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
                        upload_PhotoData(type, Person_Data.this, saveFile.BaseUrl + saveFile.uploadPhoto_Url, file);
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


}
