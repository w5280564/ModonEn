package com.moying.energyring.myAcativity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.basePopup.popupFour;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BaseActivity;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.waylenBaseView.MyArcMenu;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.moying.energyring.network.saveFile.saveShareData;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final int[] ITEM_DRAWABLES = {R.drawable.arcmenu_add, R.drawable.arcmenu_pk, R.drawable.arcmenu_growing,
            R.drawable.arcmenu_goal};
    //    TextView popup_Txt;
    RadioGroup tab_group;
    private RadioButton tab_energy, tab_pk, tab_find, tab_person;
    public List<Fragment> fragments;
    @ViewInject(R.id.popup_Txt)
    TextView popup_Txt;

//   public static Activity mainActivitySta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中
//        setContentView(R.layout.activity_main);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        Float scale = (float) dm.widthPixels / 750;
        Float heghtSclae = (float) dm.heightPixels / 1334;
        saveShareData("scale", scale + "", this);
        saveShareData("heghtSclae", heghtSclae + "", this);

        x.view().inject(this);
        initView();

        MyArcMenu mArcMenu = (MyArcMenu) findViewById(R.id.id_arcmenu);
        initArcMenu(mArcMenu, ITEM_DRAWABLES);

        popup_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {

            }
        });
    }


    private void initArcMenu(final MyArcMenu menu, int[] itemDrawables) {
        menu.setmMargin(10);
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(itemDrawables[i]);
            item.setTag(i);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 100);
            StaticData.layoutParamsScale(layoutParams, 80, 80);
            item.setLayoutParams(layoutParams);

            final int position = i;
            menu.addView(item);
        }
        menu.setOnMenuItemClickListener(new MyArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Toast.makeText(MainActivity.this, view.getTag() + "; position :" + pos, Toast.LENGTH_SHORT).show();
            }
        });
        menu.setStatusChange(new MyArcMenu.StatusChange() {
            @Override
            public void arcMenuStatus(MyArcMenu.Status mStatus) {
                menu.setBackgroundColor(mStatus == MyArcMenu.Status.OPEN ? Color.parseColor("#80000000") : Color.TRANSPARENT);
            }
        });
    }


    private void initView() {
        fragments = new ArrayList<>();
        fragments.add(new Fragment1_Energy());
        fragments.add(new Fragment2_Pk());
        fragments.add(new Fragment3_Find());
        fragments.add(new Fragment1_Energy());

        tab_group = (RadioGroup) findViewById(R.id.tab_group);
        StaticData.ViewScale(tab_group, 0, 98);
        tab_energy = (RadioButton) findViewById(R.id.tab_energy);
        tab_pk = (RadioButton) findViewById(R.id.tab_pk);
        RadioButton tab_add = (RadioButton) findViewById(R.id.tab_add);
        tab_find = (RadioButton) findViewById(R.id.tab_find);
        tab_person = (RadioButton) findViewById(R.id.tab_person);
        StaticData.ViewScale(tab_energy, 150, 98);
        StaticData.ViewScale(tab_pk, 150, 98);
        StaticData.ViewScale(tab_add, 150, 98);
        StaticData.ViewScale(tab_find, 150, 98);
        StaticData.ViewScale(tab_person, 150, 98);

        tab_group.setOnCheckedChangeListener(new tab_group());
        tab_energy.setChecked(true);
    }

    public class tab_group implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

            switch (i) {
                case R.id.tab_energy:
                    addFragmentStack(fragments, R.id.main_content_layout, 0);//加载不同的fragment
                    break;
                case R.id.tab_pk:
//                    if (saveFile.getShareData("islogin", MainActivity.this).equals("false")) {
//                        Intent intent = new Intent(MainActivity.this, LoginRegister.class);
//                        startActivity(intent);
//                        tab_energy.setChecked(true);
//                    } else {
                        addFragmentStack(fragments, R.id.main_content_layout, 1);
//                    }
                    break;
                case R.id.tab_find:
                    if (saveFile.getShareData("islogin", MainActivity.this).equals("false")) {
                        Intent intent = new Intent(MainActivity.this, LoginRegister.class);
                        startActivity(intent);
                        tab_energy.setChecked(true);
                    } else {
                        addFragmentStack(fragments, R.id.main_content_layout, 2);
                    }
                    break;
                case R.id.tab_person:
                    if (saveFile.getShareData("islogin", MainActivity.this).equals("false")) {
                        Intent intent = new Intent(MainActivity.this, LoginRegister.class);
                        startActivity(intent);
                        tab_energy.setChecked(true);
                    } else {
                        addFragmentStack(fragments, R.id.main_content_layout, 3);
                    }
                    break;
            }
        }
    }

    /**
     * 回转到上一次的fragment
     */
    public void backFragment(int pos) {
        ((RadioButton) tab_group.getChildAt(pos)).setChecked(true);
        addFragmentStack(fragments, R.id.main_content_layout, pos);//加载不同的fragment
    }


//    @Event(type = View.OnClickListener.class, value = R.id.popup_Txt)
//    public void onClick(View v) {
//        showPopup();
//    }


    public void showPopup() {
//         popupOne popup = new popupOne(MainActivity.this, popup_Txt,"内容");
//        final popupTwo popup = new popupTwo(MainActivity.this, popup_Txt,"标题","内容");
//        popupThree popup = new popupThree(MainActivity.this, popup_Txt,"标题","内容");
        popupFour popup = new popupFour(MainActivity.this, popup_Txt, "标题", "内容");
//        TextView content_Txt = (TextView) popup.getContentView().findViewById(R.id.content_Txt);
//        content_Txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                popup.dismiss();
//            }
//        });
    }

}
