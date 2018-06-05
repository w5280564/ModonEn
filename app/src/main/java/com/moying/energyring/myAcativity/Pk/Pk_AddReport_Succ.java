package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.moying.energyring.Model.JiFenAndBadge_Model;
import com.moying.energyring.R;
import com.moying.energyring.myAcativity.Person.Person_BadgeHas;
import com.moying.energyring.myAcativity.Person.Person_Commendation;

public class Pk_AddReport_Succ extends Activity {
    private JiFenAndBadge_Model jiFenmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_has_new);

        Intent intent = getIntent();
        if (intent.getParcelableExtra("jiFenmodel") != null) {
            jiFenmodel = intent.getParcelableExtra("jiFenmodel");
            if (jiFenmodel.getData().getIntegral() == 0) {
                if (!jiFenmodel.getData().get_Badge().isEmpty()) {
                    Intent intentSuccess = new Intent(Pk_AddReport_Succ.this, Person_BadgeHas.class);
                    intentSuccess.putExtra("jiFenmodel", jiFenmodel);
//                    startActivity(intentSuccess);
                    startActivityForResult(intentSuccess, 1003);
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
                } else if (!jiFenmodel.getData().get_Praise().isEmpty()) {
                    Intent intentSuccess = new Intent(Pk_AddReport_Succ.this, Person_Commendation.class);
                    intentSuccess.putExtra("jiFenmodel", jiFenmodel);
                    startActivity(intentSuccess);
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
                }else {
                    Intent intentSucc = new Intent();
                    intentSucc.putExtra("guideId", "1");
                    setResult(RESULT_OK, intentSucc);
                    finish();
                }
            } else {
                Intent intentJiFen = new Intent(Pk_AddReport_Succ.this, JiFenActivity.class);
                intentJiFen.putExtra("media", "daypk");
                intentJiFen.putExtra("jifen", jiFenmodel.getData().getIntegral());
                intentJiFen.putExtra("RewardIntegral", jiFenmodel.getData().getRewardIntegral()+"");
                intentJiFen.putExtra("DailyTask",jiFenmodel.getData().getDailyTask());
                startActivityForResult(intentJiFen, 1002);

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1002) {
            if (!jiFenmodel.getData().get_Badge().isEmpty()) {
                Intent intent = new Intent(Pk_AddReport_Succ.this, Person_BadgeHas.class);
                intent.putExtra("jiFenmodel", jiFenmodel);
                startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
            } else {
                Intent intentSucc = new Intent();
                intentSucc.putExtra("guideId", "1");
                setResult(RESULT_OK, intentSucc);
                finish();
            }
        } else if (resultCode == 1003) {
            if (!jiFenmodel.getData().get_Praise().isEmpty()) {
                Intent intentSuccess = new Intent(Pk_AddReport_Succ.this, Person_Commendation.class);
                intentSuccess.putExtra("jiFenmodel", jiFenmodel);
                startActivity(intentSuccess);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomin);
            }
            Intent intentSucc = new Intent();
            intentSucc.putExtra("guideId", "1");
            setResult(RESULT_OK, intentSucc);
            finish();
        }
    }


}
