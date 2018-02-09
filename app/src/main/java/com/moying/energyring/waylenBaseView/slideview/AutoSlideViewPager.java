package com.moying.energyring.waylenBaseView.slideview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 *
 * @author MaoJiqing
 *
 */
public class AutoSlideViewPager extends ViewPager {
	private Handler handler; // 检测currentItem
	public static final int SLIDE_MSG = 0x0000;// 用户滑动
	public static final int AUTO_SLIDE_MSG = 0x0001;// 自动滑动
	public static final int SLIDE_DELAY = 1000; // handler延时
	public static final int AUTO_SLIDE_DELAY = 3000; // handler延时
	private static boolean touchStopAutoSlide = false;// 手势滑动时停止自动滑动
	private int adapterCount;// 图片数量
	private boolean isClick = false;// 判断点击事件
	private SlideDurationScroller scroller = null; // 调整滚动完成时间
	private static long SLIDE_DURATION = 3; // 滚动动画完成时间
	private OnPageSlideSelected onPageSlideSelected;
	private static int duration;// 自动滑动延时 默认3秒

	public interface OnPageSlideSelected {
		public void onSlideSelected(int position);

		public void onClickSelected(int position);
	}

	public AutoSlideViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
		setViewPagerScroller();
	}

	public AutoSlideViewPager(Context paramContext,
							  AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		// TODO Auto-generated constructor stub
		init();
		setViewPagerScroller();
	}

	private void setViewPagerScroller() {
		try {
			Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
			scrollerField.setAccessible(true);
			Field interpolatorField = ViewPager.class
					.getDeclaredField("sInterpolator");
			interpolatorField.setAccessible(true);
			scroller = new SlideDurationScroller(getContext(),
					(Interpolator) interpolatorField.get(null));
			scroller.setSlideDurationFactor(SLIDE_DURATION);
			scrollerField.set(this, scroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 默认3秒 设置自动滚动延时时间
	 *
	 * @param duration
	 */
	@SuppressWarnings("static-access")
	public void setAutoSlideDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public void setAdapter(PagerAdapter arg0) {
		// TODO Auto-generated method stub
		super.setAdapter(arg0);
		PagerAdapter adapter = getAdapter();
		adapterCount = adapter == null ? 0 : adapter.getCount();
		if (adapterCount > 1) {
			setCurrentItem(1, false);
		}
		startAutoSlide(duration);
	}

	public void setOnPageSlideSelected(OnPageSlideSelected onPageSlideSelected) {
		this.onPageSlideSelected = onPageSlideSelected;
	}

	private void init() {
		duration = AUTO_SLIDE_DELAY;
		handler = new MyHandler(this);// 初始化handler
		this.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (onPageSlideSelected != null) {
					onPageSlideSelected.onSlideSelected(arg0);
				}
			}

		});
	}

	private float firstX = 0;// 按下时x坐标
	private float slideX = 0;// 滑动时x坐标

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		slideX = ev.getX();
		final int currentItem = getCurrentItem();
		int action = MotionEventCompat.getActionMasked(ev);
		if (action == MotionEvent.ACTION_DOWN) {
			firstX = slideX;
			touchStopAutoSlide = true;
			isClick = true;
		} else if (action == MotionEvent.ACTION_UP) {
			startSlide((SLIDE_DURATION / 2) * SLIDE_DELAY);
			touchStopAutoSlide = false;
			startAutoSlide(duration);
			if (isClick) {
				if (onPageSlideSelected != null) {
					int position;
					if (currentItem == 0) {
						position = adapterCount - 2;
					} else if (currentItem == adapterCount - 1) {
						position = 1;
					} else {
						position = currentItem;
					}
					onPageSlideSelected.onClickSelected(position);
				}
			}
		}
		if (Math.abs(firstX - slideX) >= 5) {
			isClick = false;
		}
		if (adapterCount > 1) {
			if ((currentItem == 0 && firstX < slideX)
					|| (currentItem == adapterCount - 1 && firstX > slideX)) {
				startSlideNow();
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 用户滑动首尾处理
	 */
	private void slideChange() {
		int pageIndex = getCurrentItem();
		int arg0 = getCurrentItem();
//		int viewIndex = arg0;
		if (arg0 == 0) {
			pageIndex = adapterCount; // 2'>2
		} else if (arg0 == adapterCount + 1) {
			pageIndex = 1; // 0'>0
		}
		if (arg0 != pageIndex) {
			arg0 = pageIndex;
		} else {
//			arg0 = arg0;
		}
		setCurrentItem(arg0, false);
//		int currentItem = getCurrentItem();
//		if (currentItem == 0) {
////			setCurrentItem(adapterCount - 2, false);
//			setCurrentItem(adapterCount, false);
//		} else if (currentItem == adapterCount) {
////			setCurrentItem(1, false);
//			setCurrentItem(0, false);
//		}
	}

	/**
	 * 自动滑动首尾处理
	 */
	private void autoSlideChange() {
		int currentItem = getCurrentItem();
		if (currentItem == 0) {
			setCurrentItem(adapterCount - 2, false);
		} else if (currentItem == adapterCount - 1) {
			setCurrentItem(1, false);
		} else {
			int item = currentItem++ >= adapterCount - 2 ? 1 : currentItem;
			setCurrentItem(item, true);
		}
	}

	/**
	 * 开启手势滑动处理
	 *
	 * @param delay
	 */
	private void startSlide(long delay) {
		sendSlideMessage(delay);
	}

	/**
	 * 开启自动滑动处理
	 *
	 * @param delay
	 */
	private void startAutoSlide(long delay) {
//		sendAutoSlideMessage(delay);
	}

	/**
	 * 无延时马上开启用户滑动处理
	 */
	private void startSlideNow() {
		handler.removeMessages(SLIDE_MSG);
		handler.sendEmptyMessage(SLIDE_MSG);
	}

	private void sendSlideMessage(long delayTimeInMills) {
		handler.removeMessages(SLIDE_MSG);
		handler.sendEmptyMessageDelayed(SLIDE_MSG, delayTimeInMills);
	}

	private void sendAutoSlideMessage(long delayTimeInMills) {
		handler.removeMessages(AUTO_SLIDE_MSG);
		handler.sendEmptyMessageDelayed(AUTO_SLIDE_MSG, delayTimeInMills);
	}

	private static class MyHandler extends Handler {

		private final WeakReference<AutoSlideViewPager> autoSlideViewPager;

		public MyHandler(AutoSlideViewPager autoSlideViewPager) {
			this.autoSlideViewPager = new WeakReference<AutoSlideViewPager>(
					autoSlideViewPager);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			AutoSlideViewPager pager = this.autoSlideViewPager.get();
			switch (msg.what) {
				case SLIDE_MSG:
					if (pager != null) {
						pager.slideChange();
						pager.scroller.setSlideDurationFactor(SLIDE_DURATION);
					}
					break;
				case AUTO_SLIDE_MSG:
					if (pager != null) {
						if (!touchStopAutoSlide) {
							pager.autoSlideChange();
							pager.startAutoSlide(duration);
							pager.scroller.setSlideDurationFactor(SLIDE_DURATION);
						}
					}
					break;
				default:
					break;
			}
		}
	}
}
