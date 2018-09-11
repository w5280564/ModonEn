package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Community_Status_Model;
import com.moying.energyring.Model.ShareContent;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Energy.myShareActivity;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class PK_Calner_Set extends Activity {

    private String ProjectID;
    private TextView colockset_Txt, set_Txt,invite_friend_Txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pk_calner__set);

        ProjectID = getIntent().getStringExtra("ProjectID");
        initTitle();
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this, R.color.colorFristWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this, R.color.colorFristBlack));
        cententtxt.setText("习惯设置");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private void initView() {

        View colock_Rel = findViewById(R.id.colock_Rel);
        colockset_Txt = (TextView) findViewById(R.id.colockset_Txt);
        View set_Rel = findViewById(R.id.set_Rel);
        View colock_arrow = findViewById(R.id.colock_arrow);
        View set_arrow = findViewById(R.id.set_arrow);
        set_Txt = (TextView) findViewById(R.id.set_Txt);
        invite_friend_Txt = (TextView) findViewById(R.id.invite_friend_Txt);

        StaticData.ViewScale(colock_Rel, 0, 160);
        StaticData.ViewScale(set_Rel, 0, 160);
        StaticData.ViewScale(colock_arrow, 48, 56);
        StaticData.ViewScale(set_arrow, 48, 56);
        StaticData.ViewScale(invite_friend_Txt, 450, 80);
        colock_Rel.setOnClickListener(new colock_Rel());
        set_Rel.setOnClickListener(new set_Rel());
        invite_friend_Txt.setOnClickListener(new invite_friend_Txt());
    }

    private class colock_Rel extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(PK_Calner_Set.this, Pk_AddReport_Colock.class);
            intent.putExtra("ProjectID", ProjectID + "");
            startActivity(intent);
        }
    }

    private class set_Rel extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            Intent intent = new Intent(PK_Calner_Set.this, PK_Calner_SetPrivacy.class);
            intent.putExtra("ProjectID", ProjectID + "");
            intent.putExtra("Privacy", baseModel.getData().getPrivacy() );
            startActivity(intent);
        }
    }

    private class invite_friend_Txt extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            String shareTitle = "我正在使用能量圈，快来和我一起培养新习惯吧~";
            String shareConStr = "能量圈--为了打造更好的自己" ;
            String shareUrl = saveFile.BaseUrl + "Share/Invite?invitecode=" + saveFile.getShareData("InviteCode", PK_Calner_Set.this);
            ShareContent shareContent = new ShareContent(shareUrl, shareTitle, shareConStr,shareUrl);

            Intent intent = new Intent(PK_Calner_Set.this, myShareActivity.class);
            intent.putExtra("shareContent", shareContent);
            startActivity(intent);
        }
    }


    private void initData() {
        Community_StatusData(PK_Calner_Set.this, saveFile.BaseUrl + saveFile.Community_Status_Url + "?ProjectID=" + ProjectID);
    }


    Community_Status_Model baseModel;
    public void Community_StatusData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                     baseModel = new Gson().fromJson(resultString, Community_Status_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Community_Status_Model.DataBean oneData = baseModel.getData();

                        if (oneData.getClockID() == 0) {
                            colockset_Txt.setText("未设置");
                        } else if (oneData.getClockID() > 0) {
                            colockset_Txt.setText("已设置");
                        }

                        if (oneData.getPrivacy() == 1) {
                            set_Txt.setText("所有人可见");
                        } else if (oneData.getPrivacy() == 2) {
                            set_Txt.setText("仅我的好友可见");
                        } else if (oneData.getPrivacy() == 3) {
                            set_Txt.setText("自己可见");
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

}
