package com.moying.energyring.waylenBaseView;

/**
 * Created by waylen on 2017/5/22.
 */

import android.support.v4.app.Fragment;

/**
 * 应用程序的基类Fragment，实现懒加载
 * 解决Fragment 预加载问题
 */
public abstract class lazyLoadFragment extends Fragment {
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

}