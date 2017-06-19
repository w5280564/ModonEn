package com.moying.energyring.pinyin;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SortAdapter extends BaseAdapter implements  AbsListView.OnScrollListener, HeaderListView.PinnedHeaderAdapter {
    public List<SortModel> list = null;
    private Context mContext;
    private int selectindex = -1;
    public SortAdapter(Context mContext, List<SortModel> list,Map<Integer, Boolean> mcheckflag) {
        this.mContext = mContext;
        this.list = list;
        this.mcheckflag = mcheckflag;
//        init();
    }
    //初始化Check状态
    public  Map<Integer, Boolean> mcheckflag = new HashMap<>();
//    public static Map<Integer, Boolean> mcheckflag = new HashMap<>();
//    void init() {
//        for (int i = 0; i < list.size(); i++) {
//            mcheckflag.put(i, false);
//        }
//    }
    public int getCount() {
        if (null != list) {
            return list.size();
        }
        return 0;
    }
    public Object getItem(int position) {
        if (null != list && position < getCount()) {
            return list.get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }
    public ViewHolder viewHolder;
    public View getView(final int position, View view, ViewGroup arg2) {
        view = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.sort_item, null);
            viewHolder.userrel = (RelativeLayout) view.findViewById(R.id.userrel);
            viewHolder.tagtxt = (TextView) view.findViewById(R.id.tagtxt);
            viewHolder.username = (TextView) view.findViewById(R.id.username);
            viewHolder.head_simple = (SimpleDraweeView) view.findViewById(R.id.head_simple);
            viewHolder.choice_check = (CheckBox) view.findViewById(R.id.choice_check);

            StaticData.ViewScale(viewHolder.username,0,100);
            StaticData.ViewScale(viewHolder.choice_check,40,40);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (needTitle(position)) {
            // 显示标题并设置内容
            viewHolder.tagtxt.setText(list.get(position).getSortLetters());
            viewHolder.tagtxt.setVisibility(View.VISIBLE);
        } else {
            // 内容项隐藏标题
            viewHolder.tagtxt.setVisibility(View.GONE);
        }

        viewHolder.choice_check.setChecked(mcheckflag.get(position));//选择状态

        viewHolder.username.setText(list.get(position).getName());
        if (list.get(position).getImgUrl() != null) {
            Uri imgUri = Uri.parse(list.get(position).getImgUrl());
            viewHolder.head_simple.setImageURI(imgUri);
        }
        return view;
    }
     public  class ViewHolder {
        public TextView tagtxt;
        public TextView username;
        public RelativeLayout userrel;
        public SimpleDraweeView usericon;
         public SimpleDraweeView head_simple;
         public CheckBox choice_check;
     }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < list.size(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view instanceof HeaderListView) {
            ((HeaderListView) view).controlPinnedHeader(firstVisibleItem);
        }
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
    @Override
    public int getPinnedHeaderState(int position) {
        if (getCount() == 0 || position < 0) {
            return HeaderListView.PinnedHeaderAdapter.PINNED_HEADER_GONE;
        }
        if (isMove(position) == true) {
            return HeaderListView.PinnedHeaderAdapter.PINNED_HEADER_PUSHED_UP;
        }

        return HeaderListView.PinnedHeaderAdapter.PINNED_HEADER_VISIBLE;
    }


    @Override
    public void configurePinnedHeader(View headerView, int position, int alpaha) {
        String  headerValue  =  list.get(position).getSortLetters();
        if (!TextUtils.isEmpty(headerValue)) {
            TextView headerTextView = (TextView) headerView.findViewById(R.id.header);
            headerTextView.setText(headerValue);
        }
    }

    /**
     * 判断是否需要显示标题
     *
     * @param position
     * @return
     */
    private boolean needTitle(int position) {
        // 第一个肯定是分类
        if (position == 0) {
            return true;
        }
        // 异常处理
        if (position < 0) {
            return false;
        }
        // 当前  // 上一个
        SortModel currentEntity = (SortModel) getItem(position);
        SortModel previousEntity = (SortModel) getItem(position - 1);
        if (null == currentEntity || null == previousEntity) {
            return false;
        }
        String currentTitle = currentEntity.getSortLetters();
        String previousTitle = previousEntity.getSortLetters();
        if (null == previousTitle || null == currentTitle) {
            return false;
        }
        // 当前item分类名和上一个item分类名不同，则表示两item属于不同分类
        if (currentTitle.equals(previousTitle)) {
            return false;
        }
        return true;
    }

    private boolean isMove(int position) {
        // 获取当前与下一项
        SortModel currentEntity = (SortModel) getItem(position);
        SortModel nextEntity = (SortModel) getItem(position + 1);
        if (null == currentEntity || null == nextEntity) {
            return false;
        }
        // 获取两项header内容
        String currentTitle = currentEntity.getSortLetters();
        String nextTitle = nextEntity.getSortLetters();
        if (null == currentTitle || null == nextTitle) {
            return false;
        }
        // 当前不等于下一项header，当前项需要移动了
        if (!currentTitle.equals(nextTitle)) {
            return true;
        }
        return false;
    }

}