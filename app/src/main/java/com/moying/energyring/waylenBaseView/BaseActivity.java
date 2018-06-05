package com.moying.energyring.waylenBaseView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.jaeger.library.StatusBarUtil;

import java.util.List;

/**
 * Created by waylen on 2017/5/2.
 */

public class BaseActivity extends FragmentActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStatusBar();
    }


    //解决重叠，方法1@Override
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //如果用以下这种做法则不保存状态，再次进来的话会显示默认tab
//        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    /**
     * activity中搭载了多个fragment，需要显示某个fragment时使用
     *
     * @param fragmentList fragment的集合
     * @param add2LayoutId 此fragment要添加到的布局，布局的ID
     * @param position     此fragment在集合中的下标
     */
    public void addFragmentStack(List<Fragment> fragmentList, int add2LayoutId, int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = fragmentList.get(position);
        if (!fragment.isAdded()) {
            ft.add(add2LayoutId, fragment);
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == position) {
                ft.show(fragmentList.get(i));
            } else {
                ft.hide(fragmentList.get(i));
            }
        }
        ft.commit();
    }


    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this,null);
    }


}
