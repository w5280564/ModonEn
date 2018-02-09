package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moying.energyring.Model.UserInfo_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.waylenBaseView.MyActivityManager;

public class person_Data_Edit extends Activity {


    UserInfo_Model userModel;
    private TextView edit_title;
    private ImageView edit_clear_Img;
    private EditText edit_input;
    private String changetitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__data__edit);

        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);

        Intent intent = getIntent();
        if (intent.getParcelableExtra("mymodel") != null) {
            userModel = intent.getParcelableExtra("mymodel");
            changetitle = intent.getStringExtra("changetitle");
        }
//        Log.e("ttttttttttttttt", userModel.toString());

        initTitle();
        initView();
        initData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#909090"));
        cententtxt.setText(changetitle);
        Button right_Btn = (Button) title_Include.findViewById(R.id.right_Btn);
        right_Btn.setVisibility(View.VISIBLE);
        right_Btn.setTextColor(Color.parseColor("#999999"));
        right_Btn.setText("确定");
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
        right_Btn.setOnClickListener(new right_Btn());
    }

    private void initView() {
        edit_title = (TextView) findViewById(R.id.edit_title);
        edit_clear_Img = (ImageView) findViewById(R.id.edit_clear_Img);
        edit_input = (EditText) findViewById(R.id.edit_input);

        StaticData.ViewScale(edit_clear_Img, 28, 28);
        edit_clear_Img.setOnClickListener(new edit_clear_Img());
        edit_input.addTextChangedListener(new edit_input());
    }

    private void initData() {
        edit_title.setText(changetitle);

        String contentTxt = "";
        if (changetitle.equals("昵称")) {
            contentTxt = userModel.getData().getNickName();
        } else if (changetitle.equals("姓名")) {
            contentTxt = userModel.getData().getUserName();
        } else if (changetitle.equals("邮箱")) {
            contentTxt = userModel.getData().getEmail();
        } else if (changetitle.equals("简介")) {
            contentTxt = userModel.getData().getBrief();
        }
        if (StaticData.isSpace(contentTxt)){
            contentTxt = "";
        }

        edit_input.setText(contentTxt);
        edit_input.setSelection(contentTxt.length());//将光标移至文字末尾
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("mymodel", userModel);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public class right_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (changetitle.equals("昵称")) {
                userModel.getData().setNickName(edit_input.getText().toString().trim());
            } else if (changetitle.equals("姓名")) {
                userModel.getData().setUserName(edit_input.getText().toString().trim());
            }else if (changetitle.equals("邮箱")) {
                String emailStr = edit_input.getText().toString().trim();
                if (!StaticData.isEmail(emailStr)){
                    Toast.makeText(person_Data_Edit.this,"邮箱格式不正确",Toast.LENGTH_SHORT).show();
                    return;
                }
                userModel.getData().setEmail(emailStr);
            }else if (changetitle.equals("简介")) {
                userModel.getData().setBrief(edit_input.getText().toString().trim());
            }
            Intent intent = new Intent();
            intent.putExtra("mymodel", userModel);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public class edit_clear_Img implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!StaticData.isSpace(edit_input.getText().toString())) {
                edit_input.setText("");
            }
        }
    }

    private class edit_input implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            edit_clear_Img.setVisibility(charSequence.length() == 0 ? View.INVISIBLE : View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

}
