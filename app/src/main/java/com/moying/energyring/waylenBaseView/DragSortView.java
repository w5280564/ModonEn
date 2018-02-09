package com.moying.energyring.waylenBaseView;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.sunfusheng.marqueeview.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.xutils.common.util.DensityUtil.getScreenWidth;

/**
 * Created by waylen on 2017/11/24.
 */

public class DragSortView extends GridLayout {
    private static final String TAG = "DragSortView";
    private static final int COLUMN_COUNT = 5;
    private int itemSize;
    private View dragView;
    private List<Rect> viewRects;
    private int margin;

    public DragSortView(Context context) {
        this(context, null);
    }

    public DragSortView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragSortView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //设置一行多少个条目
        setColumnCount(COLUMN_COUNT);
        //child入场退场动画
        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(150);
        setLayoutTransition(transition);
        //计算item 高度和宽度
        int width = getScreenWidth();//屏幕高度
        width -= Utils.dip2px(getContext(), 20 + ((COLUMN_COUNT - 1) * 3));//y轴item布局总数
        itemSize = (int) (width * 1.0f / COLUMN_COUNT + 0.5f); //item宽度,高度都是他
        //初始化数据
        viewRects = new ArrayList<>();
        margin = Utils.dip2px(getContext(), 3);
    }

    public void setData(List<String> mDatas, List<String> allArr) {
//        removeAllViews();
//        int i = 0;
        for (String mData : mDatas) {
            int position = getChildCount() - 1;//下标
            addView(position, mData, allArr);
//            i++
        }
        //实现拖拽
        setOnDragListener(new DragListener());
    }

    private void addView(final int pos, String mData, final List<String> allArr) {
        //创建item
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View viewLayout = inflater.inflate(R.layout.item_dragsortview, this, false);
//        CommonViewHolder holder = new CommonViewHolder(view);
        //设置文字
//        holder.getTextView(R.id.item_tv).setText(mData.getTag());
        //加载图片
//        Glide.with(getContext())
//                .load(QNUtils.formatUrl(mData.getIcon(), 1, 400, 400, false))
//                .placeholder(R.mipmap.area_img1)
//                .into(holder.getIv(R.id.item_iv));
        //长按
//        holder.getContentView().setOnLongClickListener(v -> {
//            dragView = v;
//            v.startDrag(null, new DragShadowBuilder(v), null, 0);
//            return true;
//        });

        ImageView dragcha_Img = (ImageView) viewLayout.findViewById(R.id.dragcha_Img);
        SimpleDraweeView drag_sim = (SimpleDraweeView) viewLayout.findViewById(R.id.drag_sim);
        StaticData.ViewScale(dragcha_Img, 36, 36);
        StaticData.ViewScale(drag_sim, 138, 138);
        Uri imgUri;
        if (mData.substring(0, 4).equals("http")) {
            imgUri = Uri.parse(mData);
        } else {
            imgUri = Uri.parse("file:// /" + mData);
        }
        drag_sim.setImageURI(imgUri);
//        view.findViewById(R.id.drag_sim).setText(mData.getTag());
        //长按
        viewLayout.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dragView = view;
                view.startDrag(null, new DragShadowBuilder(view), null, 0);
                return true;
            }
        });
//        holder.getContentView().setOnLongClickListener(v -> {
//            dragView = v;
//            v.startDrag(null, new DragShadowBuilder(v), null, 0);
//            return true;
//        });
        dragcha_Img.setTag(pos);
        dragcha_Img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                chaClickLitener.chaClick(viewLayout, pos);
            }
        });

        addView(viewLayout, pos, getParams(pos));
//        addView(view, pos);
    }

    private class DragListener implements OnDragListener {
        /**
         * 该值存放的是上一次移动到的位置
         */
        int lastIndex = -1;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    initRects();
                    lastIndex = indexOfChild(dragView);
                    if (dragView != null)
                        dragView.setEnabled(false);
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    lastIndex = -1;
                    if (dragView != null) {
                        dragView.setEnabled(true);
                        dragView = null;
                    }
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    int moveX = (int) event.getX();
                    int moveY = (int) event.getY();
//                    L.i(TAG, "location X:" + moveX + " Y:" + moveY);
                    int findIndex = findTouchChildIndex(moveX, moveY);
//                    Log.e("ttttttttt", Pk_HuiZong.photoPaths + "");
//                    Log.e("kkkkkkkkk", Pk_HuiZong.photoID + "");
//                        Collections.swap(Pk_HuiZong.photoPaths, lastIndex, findIndex);
//                        Collections.swap(Pk_HuiZong.photoID, lastIndex, findIndex);
                    if (findIndex != -1 && findIndex != lastIndex && dragView != null) {
                        chaClickLitener.sortClick(lastIndex, findIndex);
                        removeView(dragView);
                        addView(dragView, findIndex);
                        lastIndex = findIndex;
                    }
                    return true;
            }
            return false;
        }
    }

    /**
     * 获取当前所有child的坐标区域
     */
    private void initRects() {
        viewRects.clear();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect object = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
            viewRects.add(object);
        }
    }

    /**
     * 找到当前坐标所在的pos点，成功返回pos，失败返回-1*
     */
    private int findTouchChildIndex(int moveX, int moveY) {
        for (int i = 0; i < viewRects.size(); i++) {
            boolean isFind = viewRects.get(i).contains(moveX, moveY);
            if (isFind) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取布局参数
     */
    private GridLayout.LayoutParams getParams(int pos) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.height = itemSize;
        params.width = itemSize;
        params.leftMargin = margin;
        if (pos < 4) {
            params.bottomMargin = margin;
        }
        return params;
    }

    private chaClick chaClickLitener;

    public void setChaClickLitener(chaClick listener) {
        chaClickLitener = listener;
    }


    public interface chaClick {
        void chaClick(View view, int postion);

        void sortClick(int startPos, int endPos);
    }


}