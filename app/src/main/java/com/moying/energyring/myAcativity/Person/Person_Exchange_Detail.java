package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.Person_Exchange_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;

public class Person_Exchange_Detail extends Activity {

    private Person_Exchange_Model.DataBean exchange_model;
    private TextView shou_Txt, phone_Txt, address_Txt, Name_Txt, Time_Txt, fen_Txt, ExpressCompany_Txt, OrderCode_Txt,fa_Txt;
    private SimpleDraweeView my_simple;
    private Button copy_Btn;
    private View express_Rel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_exchange_detail);


        initTitle();
        initView();
        initData();
    }


    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFristWhite));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(ContextCompat.getColor(this,R.color.colorFristBlack));
        cententtxt.setText("订单详情");
//        right_Btn.setBackgroundResource(R.drawable.personnew_idea_icon);
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
        View shou_Rel = findViewById(R.id.shou_Rel);
        View phone_Rel = findViewById(R.id.phone_Rel);
        View address_Rel = findViewById(R.id.address_Rel);
        shou_Txt = (TextView) findViewById(R.id.shou_Txt);
        phone_Txt = (TextView) findViewById(R.id.phone_Txt);
        address_Txt = (TextView) findViewById(R.id.address_Txt);
        my_simple = (SimpleDraweeView) findViewById(R.id.my_simple);
        Name_Txt = (TextView) findViewById(R.id.Name_Txt);
        Time_Txt = (TextView) findViewById(R.id.Time_Txt);
        fen_Txt = (TextView) findViewById(R.id.fen_Txt);
        ExpressCompany_Txt = (TextView) findViewById(R.id.ExpressCompany_Txt);
        OrderCode_Txt = (TextView) findViewById(R.id.OrderCode_Txt);
        copy_Btn = (Button) findViewById(R.id.copy_Btn);
        express_Rel = findViewById(R.id.express_Rel);
        fa_Txt = (TextView)findViewById(R.id.fa_Txt);

        View my_Lin = findViewById(R.id.my_Lin);

        StaticData.ViewScale(shou_Rel, 0, 127);
        StaticData.ViewScale(phone_Rel, 0, 127);
        StaticData.ViewScale(address_Rel, 0, 174);
        StaticData.ViewScale(my_Lin, 0, 200);
        StaticData.ViewScale(my_simple, 160, 160);
        StaticData.ViewScale(copy_Btn, 120, 60);
        copy_Btn.setOnClickListener(new copy_Btn());
    }

    private void initData() {
        Intent intent = getIntent();
        exchange_model = intent.getParcelableExtra("baseModel");

        shou_Txt.setText(exchange_model.getNickName());
        phone_Txt.setText(exchange_model.getReceivePhone());
        address_Txt.setText(exchange_model.getReceiveAddress());
        if (exchange_model.getFilePath() != null) {
            Uri contentUri = Uri.parse(exchange_model.getFilePath());
            my_simple.setImageURI(contentUri);
        }
        Name_Txt.setText(exchange_model.getProductName());
        Time_Txt.setText(exchange_model.getCreateTime());
        fen_Txt.setText(exchange_model.getIntegral() + "积分");


        if (exchange_model.getExpressState() == 1) {
            express_Rel.setVisibility(View.GONE);
            fa_Txt.setVisibility(View.VISIBLE);
        }else {
            express_Rel.setVisibility(View.VISIBLE);
            fa_Txt.setVisibility(View.GONE);
            ExpressCompany_Txt.setText(exchange_model.getExpressCompany());
            OrderCode_Txt.setText(exchange_model.getOrderCode());
        }


//        else if (exchange_model.getExpressState() == 2) {
//            express_Rel.setVisibility(View.VISIBLE);
//            fa_Txt.setVisibility(View.GONE);
//            ExpressCompany_Txt.setText(exchange_model.getExpressCompany());
//            OrderCode_Txt.setText(exchange_model.getOrderCode());
//        } else if (exchange_model.getExpressState() == 3) {
//            express_Rel.setVisibility(View.VISIBLE);
//            fa_Txt.setVisibility(View.GONE);
//        }


    }


    private class copy_Btn extends NoDoubleClickListener {
        @Override
        protected void onNoDoubleClick(View v) {
            StaticData.copy(exchange_model.getOrderCode(), Person_Exchange_Detail.this);
            Toast.makeText(Person_Exchange_Detail.this, "已复制", Toast.LENGTH_SHORT).show();
        }
    }

}
