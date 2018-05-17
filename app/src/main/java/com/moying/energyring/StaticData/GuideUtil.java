package com.moying.energyring.StaticData;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.moying.energyring.R;

/**
 * Created by waylen on 2018/5/10.
 */

public class GuideUtil {
    private Context context;
    private ImageView imgView;
    private ViewGroup content;
    private static GuideUtil instance = null;
    /**
     * 是否第一次进入该程序
     **/
    private boolean isFirst = true;
    private int i = 0;
    int img[] = new int[]{R.drawable.guide_one, R.drawable.guide_two};
    int detailImageArray[] = new int[]{R.drawable.guide_one, R.drawable.guide_two, R.drawable.guide_train};
    private RemoveListener removeListener;

    private GuideUtil() {
    }


    public static GuideUtil getInstance() {
        synchronized (GuideUtil.class) {
            if (null == instance) {
                instance = new GuideUtil();
            }
        }
        return instance;
    }


    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    // 设置LayoutParams参数
                    final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                    params.type = WindowManager.LayoutParams.TYPE_PHONE;
                    // 设置显示格式
                    params.format = PixelFormat.RGBA_8888;
                    // 设置对齐方式
                    params.gravity = Gravity.LEFT | Gravity.BOTTOM;
                    // 设置宽高
                    params.width =
                            params.width = ScreenUtils.getScreenWidth(context);
                    params.height = ScreenUtils.getScreenHeight(context);
                    // 设置动画
//                    params.windowAnimations = R.style.view_anim;
                    // 添加到当前的界面上
                    content.addView(imgView, params);
                    break;
            }
        }

        ;
    };


    public void initGuide(Activity context, int index, final int flag) {
//        if (!isFirst) {
//            return;
//        }
        this.context = context;
        content = (ViewGroup) context.findViewById(android.R.id.content);
        // 动态初始化图层
        imgView = new ImageView(context);
        imgView.setLayoutParams(new WindowManager.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT));
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);

        int RourcesId = 0;
        if (flag == 0) {//首页
            RourcesId = img[index];
        } else if (flag == 1) {
            RourcesId = detailImageArray[index];
        }
        imgView.setImageResource(RourcesId);
        if (index == 2){
            imgView.setBackgroundColor(Color.parseColor("#2b2a2a"));
        }
        handler.sendEmptyMessage(1);

        i = index;
        // 点击图层之后，将图层移除
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                int[] stepImageArray = new int[]{};
//                if (flag == 0) {//首页
//                    stepImageArray = img;
//                } else if (flag == 1) {
//                    stepImageArray = detailImageArray;
//                }
//                i++;
//                if (i < stepImageArray.length) {
//                    imgView.setImageResource(stepImageArray[i]);
//                    if (i == stepImageArray.length -1){
//                        imgView.setBackgroundColor(Color.parseColor("#2b2a2a"));
//                    }
//                    removeListener.RemoveListener(i,false);
//                } else if (i == stepImageArray.length) {
//                    i = 0;
//                    content.removeView(imgView);
//                    removeListener.RemoveListener(i,true);
//                }

                content.removeView(imgView);
                removeListener.RemoveListener(i,false);

            }
        });
    }

    public void setRemoveListener(RemoveListener listener) {
        this.removeListener = listener;
    }

    public interface RemoveListener {
        void RemoveListener(int index,boolean islast);
    }


}