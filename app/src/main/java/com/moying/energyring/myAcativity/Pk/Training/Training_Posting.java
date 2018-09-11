package com.moying.energyring.myAcativity.Pk.Training;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moying.energyring.Model.JiFenAndBadge_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainActivity;
import com.moying.energyring.myAcativity.PostingActivity;

public class Training_Posting extends Activity {

    private TextView train_successTxt, train_NameTxt, train_timeTxt, train_zuTxt,train_CountTxt;
    private Button post_Btn;
    private JiFenAndBadge_Model jiFenmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.MyDialog);
        setContentView(R.layout.activity_training__posting);
        initView();
        initData();
    }


    private void initView() {

        View back_Btn = findViewById(R.id.back_Btn);
        View train_success = findViewById(R.id.train_success);
        train_successTxt = (TextView) findViewById(R.id.train_successTxt);
        View train_nameRel = findViewById(R.id.train_nameRel);
        train_NameTxt = (TextView) findViewById(R.id.train_NameTxt);
        train_CountTxt = (TextView) findViewById(R.id.train_CountTxt);
        View train_timeRel = findViewById(R.id.train_timeRel);
        train_timeTxt = (TextView) findViewById(R.id.train_timeTxt);
        View train_zuRel = findViewById(R.id.train_zuRel);
        train_zuTxt = (TextView) findViewById(R.id.train_zuTxt);
        post_Btn = (Button) findViewById(R.id.post_Btn);

        StaticData.ViewScale(back_Btn, 64, 64);
        StaticData.ViewScale(train_success, 350, 238);
        StaticData.ViewScale(train_nameRel, 0, 120);
        StaticData.ViewScale(train_timeRel, 0, 120);
        StaticData.ViewScale(train_zuRel, 0, 120);
        StaticData.ViewScale(post_Btn, 0, 120);

        Intent intent = getIntent();
        jiFenmodel = intent.getParcelableExtra("jiFenmodel");
        String projectName = intent.getStringExtra("projectName");
        String projectCount = intent.getStringExtra("projectCount");
        String time = intent.getStringExtra("time");
        String GroupNo = intent.getStringExtra("GroupNo");

        int TrainFre = jiFenmodel.getData().getTrainFre();
        train_successTxt.setText("恭喜完成第" + TrainFre + "次训练");

        train_NameTxt.setText(projectName);
        train_CountTxt.setText(projectCount);
        train_timeTxt.setText(time);
        train_zuTxt.setText(GroupNo + "组");

        back_Btn.setOnClickListener(new back_Btn());
        post_Btn.setOnClickListener(new post_Btn());
    }

    private void initData() {

    }

    private class back_Btn implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent i = new Intent(Training_Posting.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

//            finish();
        }
    }

        private class post_Btn implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Training_Posting.this, PostingActivity.class);
            intent.putExtra("ProjectID",jiFenmodel.getData().getProjectID()+"");
            startActivity(intent);
//            finish();
        }
    }


}
