package com.moying.energyring.myAcativity.Person;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.DataCleanManager;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Pk.Training.constant.DownLoadConstant;
import com.moying.energyring.myAcativity.Pk.Training.utils.FileUtil;
import com.moying.energyring.waylenBaseView.BasePopupWindow;

import java.io.File;

public class Person_Set_Cache extends AppCompatActivity {

    private TextView imgcache_Txt, traincache_Txt;
    RelativeLayout img_Rel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_cache);

        initTitle();
        initView();
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
        cententtxt.setText("缓存管理");
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

        RelativeLayout train_Rel = (RelativeLayout) findViewById(R.id.train_Rel);
        img_Rel = (RelativeLayout) findViewById(R.id.img_Rel);
        View train_arrow = findViewById(R.id.train_arrow);
        View img_arrow = findViewById(R.id.img_arrow);
        traincache_Txt = (TextView) findViewById(R.id.traincache_Txt);
        imgcache_Txt = (TextView) findViewById(R.id.imgcache_Txt);


        StaticData.ViewScale(train_Rel, 0, 160);
        StaticData.ViewScale(img_Rel, 0, 160);
        StaticData.ViewScale(train_arrow, 48, 56);
        StaticData.ViewScale(img_arrow, 48, 56);

        train_Rel.setOnClickListener(new train_Rel());
        img_Rel.setOnClickListener(new img_Rel());
    }

    private void initData() {

        try {

            String trainSize = FileUtil.getFormatSize(FileUtil.getFolderSize(new File(DownLoadConstant.PATH)));
            traincache_Txt.setText(trainSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            imgcache_Txt.setText(DataCleanManager.getTotalCacheSize(Person_Set_Cache.this));
            if (StaticData.getNumbers(DataCleanManager.getTotalCacheSize(Person_Set_Cache.this)).equals("0")) {
                img_Rel.setEnabled(false);
            } else {
                img_Rel.setEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class train_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showDetele(Person_Set_Cache.this, img_Rel, "清除训练音频后，已下载的音频需要重新下载", 1);
        }
    }

    private class img_Rel implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            showDetele(Person_Set_Cache.this, img_Rel, "本操作将清除图片头像等缓存", 2);

        }
    }

    private void showDetele(final Context mContext, View view, String contentStr, final int Type) {
        View contentView = View.inflate(mContext, R.layout.popup_daypk_delete, null);
        final BasePopupWindow popupWindow = new BasePopupWindow(mContext);
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
        popupWindow.showAsDropDown(view, 0, 0);
        RelativeLayout my_Rel = (RelativeLayout) contentView.findViewById(R.id.my_Rel);
        TextView hint_Txt = (TextView) contentView.findViewById(R.id.hint_Txt);
        TextView content_Txt = (TextView) contentView.findViewById(R.id.content_Txt);
        TextView sure_btn = (TextView) contentView.findViewById(R.id.sure_btn);
        TextView cancel_btn = (TextView) contentView.findViewById(R.id.cancel_btn);
        LinearLayout cha_Lin = (LinearLayout) contentView.findViewById(R.id.cha_Lin);
        StaticData.ViewScale(my_Rel, 610, 450);
        StaticData.ViewScale(hint_Txt, 0, 120);
        StaticData.ViewScale(cha_Lin, 0, 120);

        content_Txt.setText(contentStr);
        sure_btn.setText("取消");
        cancel_btn.setText("清除");

        sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Type == 1) {
                    FileUtil.deleteAllFiles(new File(DownLoadConstant.PATH));

                }else if (Type == 2){
                    try {
                        DataCleanManager.clearAllCache(Person_Set_Cache.this);
//                        imgcache_Txt.setText(DataCleanManager.getTotalCacheSize(Person_Set_Cache.this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                initData();
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }


}
