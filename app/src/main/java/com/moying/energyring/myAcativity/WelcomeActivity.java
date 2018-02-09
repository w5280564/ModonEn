package com.moying.energyring.myAcativity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.animated.base.AbstractAnimatedDrawable;
import com.facebook.imagepipeline.image.ImageInfo;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.GestureListener;
import com.moying.energyring.waylenBaseView.MyAnimationDrawable;
import com.moying.energyring.waylenBaseView.ViewPager;

import java.util.ArrayList;


/**
 * 引导页
 * Created by Admin on 2015/7/31.
 */
public class WelcomeActivity extends Activity implements ViewPager.OnPageChangeListener {
    private ViewPager mPager;
    // ViewPager-item视图集合的保存
    private ArrayList<LinearLayout> views;
    // ViewPager-item临时视图
    private LinearLayout view1;
    // 当前item
    private int currentItem = 1;
    private SimpleDraweeView wel_onetop;
    SimpleDraweeView webg;
    int index = 0;
    Button startBtn;
    private ImageView annim_img;

//    private  final int[] weimg_threeanimlist = {
//            R.drawable.we_three1,R.drawable.we_three2,R.drawable.we_three3,R.drawable.we_three4,R.drawable.we_three5, R.drawable.we_three6,R.drawable.we_three7,R.drawable.we_three8,R.drawable.we_three9,R.drawable.we_three10,
//            R.drawable.we_three11,R.drawable.we_three12,R.drawable.we_three13,R.drawable.we_three14,R.drawable.we_three15, R.drawable.we_three16,R.drawable.we_three17,R.drawable.we_three18,R.drawable.we_three19,R.drawable.we_three20,
//            R.drawable.we_three21,R.drawable.we_three22,R.drawable.we_three23,R.drawable.we_three24,R.drawable.we_three25, R.drawable.we_three26,R.drawable.we_three27,R.drawable.we_three28,R.drawable.we_three29,R.drawable.we_three30,
//            R.drawable.we_three31,R.drawable.we_three32,R.drawable.we_three33,R.drawable.we_three34,R.drawable.we_three35, R.drawable.we_three36,R.drawable.we_three37,R.drawable.we_three38,R.drawable.we_three39,R.drawable.we_three40,
//            R.drawable.we_three41,R.drawable.we_three42,R.drawable.we_three43,R.drawable.we_three44,R.drawable.we_three45, R.drawable.we_three46,R.drawable.we_three47,R.drawable.we_three48,R.drawable.we_three49,R.drawable.we_three50,
//            R.drawable.we_three51,R.drawable.we_three52,R.drawable.we_three53,R.drawable.we_three54,R.drawable.we_three55, R.drawable.we_three56,R.drawable.we_three57,R.drawable.we_three58,R.drawable.we_three59,R.drawable.we_three60,
//            R.drawable.we_three61,R.drawable.we_three62,R.drawable.we_three63,R.drawable.we_three64,R.drawable.we_three65, R.drawable.we_three66,R.drawable.we_three67,R.drawable.we_three68,R.drawable.we_three69,R.drawable.we_three70,
//            R.drawable.we_three71,R.drawable.we_three72,R.drawable.we_three73,R.drawable.we_three74,R.drawable.we_three75, R.drawable.we_three76 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomeactivity);
//        initViewPager();
//        initDot();
        webg = (SimpleDraweeView) findViewById(R.id.webg);
        annim_img = (ImageView) findViewById(R.id.annim_img);
        annim_img.setScaleType(ImageView.ScaleType.FIT_XY);

//        Uri imgurl = Uri.parse("res:///" + iconArr[0]);
//        addPla(webg, iconArrpla[0]);
//        addGif(imgurl, webg);
        //setLongClickable是必须的
//        webg.setLongClickable(true);
//        webg.setOnTouchListener(new MyGestureListener(this));

        annim_img.setLongClickable(true);//LongClickable 必要条件
        annim_img.setOnTouchListener(new MyGestureListener(this));

        MyAnimationDrawable.animateRawManuallyFromXML(animlist_Arr[0], annim_img,
                new Runnable() {
                    @Override
                    public void run() {
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                    }
                });


        startBtn = (Button) findViewById(R.id.startBtn);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        StaticData.layoutParamsScale(params, 417, 97);
        int padd = (int) (Float.parseFloat(saveFile.getShareData("scale", WelcomeActivity.this)) * 152);
        params.setMargins(0, 0, 0, padd);
        startBtn.setLayoutParams(params);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    /**
     * 继承GestureListener，重写left和right方法
     */
    private class MyGestureListener extends GestureListener {
        public MyGestureListener(Context context) {
            super(context);
        }

        @Override
        public boolean left() {
//            Log.e("test", "向左滑");
            if (index < animlist_Arr.length - 1) {
                index += 1;
//                addPla(webg, iconArrpla[index]);
//                Uri imgurl = Uri.parse("res:///" + iconArr[index]);
//                addGif(imgurl, webg);
                MyAnimationDrawable.animateRawManuallyFromXML(animlist_Arr[index], annim_img,
                        new Runnable() {
                            @Override
                            public void run() {
                            }
                        }, new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                if (index == animlist_Arr.length - 1) {
                    startBtn.setVisibility(View.VISIBLE);
                }
            }
            return super.left();
        }

        @Override
        public boolean right() {
//            Log.e("test", "向右滑");
            if (index > 0) {
                index -= 1;
//                addPla(webg, iconArrpla[index]);
//                Uri imgurl = Uri.parse("res:///" + iconArr[index]);
//                addGif(imgurl, webg);
                MyAnimationDrawable.animateRawManuallyFromXML(animlist_Arr[index], annim_img,
                        new Runnable() {
                            @Override
                            public void run() {
                            }
                        }, new Runnable() {
                            @Override
                            public void run() {
                            }
                        });

                startBtn.setVisibility(View.GONE);
            }


            return super.right();
        }
    }

    //
    private void addPla(SimpleDraweeView myDraw, int iconId) {
        //获取GenericDraweeHierarchy对象
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                //设置占位图及它的缩放方式
                .setPlaceholderImage(ContextCompat.getDrawable(this, iconId), ScalingUtils.ScaleType.FOCUS_CROP)
                //构建
                .build();

        //设置GenericDraweeHierarchy
        myDraw.setHierarchy(hierarchy);
    }

//    private int[] iconArrpla = {R.drawable.we_onepla, R.drawable.we_onepla, R.drawable.we_two_pla, R.drawable.we_three_pla};
//    private int[] animlist_Arr = {R.drawable.animlist_we_one, R.drawable.animlist_we_two, R.drawable.animlist_we_three, R.drawable.animlist_we_four};
    private int[] animlist_Arr = {R.drawable.animlist_we_one, R.drawable.animlist_we_two, R.drawable.animlist_we_three, R.drawable.animlist_we_four};
    private int[] iconArr = {R.drawable.we_one, R.drawable.we_two, R.drawable.we_three};

    //滑动页面
    private void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.viewpager);
//        mPager.setOffscreenPageLimit(-1);
        mPager.removeAllViews();
        if (views != null) {
            views.clear();
        }
        views = new ArrayList<LinearLayout>();
        LayoutInflater mInflater = LayoutInflater.from(this);
//        int length = iconArr.length;

//        for (int i = 0; i < length; i++) {
//            view1 = (LinearLayout) mInflater.inflate(R.layout.welcomeactivity_style, null);
//            wel_onetop = (SimpleDraweeView) view1.findViewById(R.id.wel_onetop);
//            Button wel_login = (Button) view1.findViewById(R.id.wel_login);
//            LinearLayout wellin = (LinearLayout) view1.findViewById(R.id.wellin);
//
//            StaticData.ViewScale(wel_onetop, 750, 1334);
//            Uri imgurl = Uri.parse("res:///" + iconArr[i]);
////            wel_onetop.setImageURI(imgurl);
////            addGif(this, wel_onetop, imgurl);
//            addGif(imgurl, wel_onetop);
//
////            wel_login.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Intent i = new Intent(WelcomeActivity.this,LoginActivity.class);
//////                    i.putExtra("regtype", "2");
////                    startActivity(i);
////                    WelcomeActivity.this.finish();
////                }
////            });
//
//
//            views.add(view1);
//        }
//        mPager.setAdapter(new myPagerAdapter());
//        mPager.setCurrentItem(0);
//        mPager.setOnPageChangeListener(this);
////        mPager.setPageTransformer(true, new DepthPageTransformer());

    }


    //滑动页面适配器
    class myPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        @Override//点击事件
        public Object instantiateItem(View container, final int position) {
            ((ViewPager) container).addView(views.get(position));
//            if (views.get(position) != null) {
//
//            }
//            container.setTag(position);
            return views.get(position);
        }
    }

    @Override//滑动结束
    public void onPageSelected(int arg0) {
//        Uri imgurl = Uri.parse("res:///" + iconArr[arg0]);
//        addGif(this, wel_onetop, imgurl);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void addGif(final Context context, final SimpleDraweeView draweeView, Uri uri) {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, final Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (animatable != null) {
                    int duration = 0;//获取动图时间
//                    try {
//                        Field field = AbstractAnimatedDrawable.class.getDeclaredField("mTotalLoops");
//                        field.setAccessible(true);
//                        field.set(animatable, 1);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    animatable.start();
                    if (animatable instanceof AbstractAnimatedDrawable) { // 只有fresco 0.13.0+才有getDuration()的方法
                        duration = ((AbstractAnimatedDrawable) animatable).getDuration();
                    }
                    if (duration > 0) {
                        draweeView.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.DONUT)
                            @Override
                            public void run() {
                                if (animatable.isRunning()) {
                                    animatable.stop();
//                                    pk_check_simple.setImageResource(R.drawable.pk_check_after);
//                                    Uri afterUri = Uri.parse("res:///" + R.drawable.pk_check_after);
//                                    pk_check_simple.setImageURI(afterUri);
                                }
                            }
                        }, duration);
                    }
                }
            }
        };

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setControllerListener(controllerListener)
                .setAutoPlayAnimations(true)//是否自动播放
                .build();
        draweeView.setController(controller);
    }

    public void addGif(Uri uri, SimpleDraweeView my_DraweeView) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        my_DraweeView.setController(controller);
    }

//    public class DepthPageTransformer implements ViewPager.PageTransformer{
//        private static final float MIN_SCALE = 0.75f;
//        @Override
//        public void transformPage(View page, float position) {
//            int pageWidth = page.getWidth();
//            if (position < -1) { // [-Infinity,-1)
//                // 页面远离左侧页面
//                page.setAlpha(0);
//            } else if (position <= 0) { // [-1,0]
//                // 页面在由中间页滑动到左侧页面 或者 由左侧页面滑动到中间页
//                page.setAlpha(1);
//                page.setTranslationX(0);
//                page.setScaleX(1);
//                page.setScaleY(1);
//            } else if (position <= 1) { // (0,1]
//                //页面在由中间页滑动到右侧页面 或者 由右侧页面滑动到中间页
//                // 淡入淡出效果
//                page.setAlpha(1 - position);
//                // 反方向移动
//                page.setTranslationX(pageWidth * -position);
//                // 0.75-1比例之间缩放
//                float scaleFactor = MIN_SCALE  + (1 - MIN_SCALE) * (1 - Math.abs(position));
//                page.setScaleX(scaleFactor);
//                page.setScaleY(scaleFactor);
//            } else { // (1,+Infinity]
//                // 页面远离右侧页面
//                page.setAlpha(0);
//            }
//
//
//        }
//    }

}
