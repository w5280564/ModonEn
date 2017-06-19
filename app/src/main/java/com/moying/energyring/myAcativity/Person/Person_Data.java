package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.UserInfo_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.waylenBaseView.wheel.pictime.ChangeBirthDialog_old;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Person_Data extends Activity {

    private TextView Nick_Txt, name_Txt, sex_Txt, bir_Txt, email_Txt, intr_Txt;

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
            EditUserInfo_Data(Person_Data.this,saveFile.BaseUrl+saveFile.EditInfo_Url);
        }
    }

    private void initView() {
        LinearLayout persondata_Lin = (LinearLayout) findViewById(R.id.persondata_Lin);

        RelativeLayout nick_Lin = (RelativeLayout) findViewById(R.id.nick_Lin);
        RelativeLayout name_Rel = (RelativeLayout) findViewById(R.id.name_Rel);
        RelativeLayout email_Rel = (RelativeLayout) findViewById(R.id.email_Rel);
        RelativeLayout intr_Rel = (RelativeLayout) findViewById(R.id.intr_Rel);
        RelativeLayout bir_Rel = (RelativeLayout) findViewById(R.id.bir_Rel);
        RelativeLayout sex_Rel = (RelativeLayout) findViewById(R.id.sex_Rel);
        Nick_Txt = (TextView) findViewById(R.id.Nick_Txt);
        name_Txt = (TextView) findViewById(R.id.name_Txt);
        sex_Txt = (TextView) findViewById(R.id.sex_Txt);
        bir_Txt = (TextView) findViewById(R.id.bir_Txt);
        email_Txt = (TextView) findViewById(R.id.email_Txt);
        intr_Txt = (TextView) findViewById(R.id.intr_Txt);
        ImageView nick_Img = (ImageView) findViewById(R.id.nick_Img);
        ImageView name_Img = (ImageView) findViewById(R.id.name_Img);
        ImageView sex_Img = (ImageView) findViewById(R.id.sex_Img);
        ImageView bir_Img = (ImageView) findViewById(R.id.bir_Img);
        ImageView phone_Img = (ImageView) findViewById(R.id.phone_Img);
        ImageView email_Img = (ImageView) findViewById(R.id.email_Img);
        ImageView intr_Img = (ImageView) findViewById(R.id.intr_Img);

//        StaticData.ViewScale(persondata_Lin,680,847);
        StaticData.ViewScale(persondata_Lin, 680, 726);
        StaticData.ViewScale(nick_Img, 16, 30);
        StaticData.ViewScale(name_Img, 16, 30);
        StaticData.ViewScale(sex_Img, 16, 30);
        StaticData.ViewScale(bir_Img, 16, 30);
        StaticData.ViewScale(phone_Img, 16, 30);
        StaticData.ViewScale(email_Img, 16, 30);
        StaticData.ViewScale(intr_Img, 16, 30);
        nick_Lin.setOnClickListener(new nick_Lin());
        name_Rel.setOnClickListener(new name_Rel());
        email_Rel.setOnClickListener(new email_Rel());
        intr_Rel.setOnClickListener(new intr_Rel());
        sex_Rel.setOnClickListener(new sex_Rel());
        bir_Rel.setOnClickListener(new bir_Rel());
    }

    public static int NICKRESULT = 401;

    private class nick_Lin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Data.this, person_Data_Edit.class);
            intent.putExtra("mymodel", userModel);
            intent.putExtra("changetitle", "昵称");
            startActivityForResult(intent, NICKRESULT);
        }
    }

    private class name_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Data.this, person_Data_Edit.class);
            intent.putExtra("mymodel", userModel);
            intent.putExtra("changetitle", "姓名");
            startActivityForResult(intent, NICKRESULT);
        }
    }

    private class email_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Data.this, person_Data_Edit.class);
            intent.putExtra("mymodel", userModel);
            intent.putExtra("changetitle", "邮箱");
            startActivityForResult(intent, NICKRESULT);
        }
    }

    private class intr_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Person_Data.this, person_Data_Edit.class);
            intent.putExtra("mymodel", userModel);
            intent.putExtra("changetitle", "简介");
            startActivityForResult(intent, NICKRESULT);
        }
    }

    private class sex_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showSex(Person_Data.this, sex_Txt);
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


    private void showSex(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_sex, null);
        final PopupWindow showSexPopup = new BasePopupWindow(mContext);
        showSexPopup.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        showSexPopup.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);
        showSexPopup.setTouchable(true);
        showSexPopup.setContentView(contentView);
        showSexPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
        final Button topbtn = (Button) contentView.findViewById(R.id.topbtn);
        final Button belowbtn = (Button) contentView.findViewById(R.id.belowbtn);
        Button cancelbtn = (Button) contentView.findViewById(R.id.cancelbtn);
        StaticData.ViewScale(topbtn, 750, 102);
        StaticData.ViewScale(belowbtn, 750, 102);
        StaticData.ViewScale(cancelbtn, 750, 88);
        topbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex_Txt.setText(topbtn.getText().toString());
                userModel.getData().setGender(1);
                showSexPopup.dismiss();
            }
        });
        belowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex_Txt.setText(belowbtn.getText().toString());
                userModel.getData().setGender(2);
                showSexPopup.dismiss();
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    SexId = "w";
                showSexPopup.dismiss();
            }
        });

        showSexPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                showSexPopup.dismiss();
            }
        });
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
        Nick_Txt.setText(oneData.getNickName());
        name_Txt.setText(oneData.getUserName());
        int sexId = oneData.getGender();
        if (sexId == 1) {
            sex_Txt.setText("男");
        } else if (sexId == 2) {
            sex_Txt.setText("女");
        } else {
            sex_Txt.setText("未确定");
        }
        bir_Txt.setText(StaticData.Datatypetwo(oneData.getBirthday()));
        email_Txt.setText(oneData.getEmail());
        intr_Txt.setText(oneData.getBrief());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            userModel = data.getParcelableExtra("mymodel");
            updataMyData(userModel);
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
                    Base_Model baseModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(context,"资料上传成功",Toast.LENGTH_SHORT).show();
                        finish();
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


}
