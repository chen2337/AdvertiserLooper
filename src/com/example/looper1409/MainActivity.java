package com.example.looper1409;

import java.util.ArrayList;
import java.util.List;

import com.example.looper1409.adapter.LooperAdapter;
import com.example.looper1409.constant.ImageUri;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 图片无限轮播
 * 解决了划到一半时停止，还能自动播放的问题
 * 解决了只有2张图片时，崩溃的问题
 * 解决了手动翻页时，自动播放的问题
 * Copyright: 版权所有 (c) 2002 - 2003
 *
 * @author 陈振国
 * @version 1.0.0
 * @create 2016年7月8日
 */
public class MainActivity extends Activity {

	private ViewPager mViewPager;
	private RadioGroup mRadioGroup;
	private List<ImageView> mImageViewList;
	private ImageLoader mImageLoader;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 翻页
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
			// 调用下次
			sendMessageDelay();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mImageLoader = ImageLoader.getInstance();
		// 查找view
		initViews();
		// 自动播放
		sendMessageDelay();
	}

	private void sendMessageDelay() {
		mHandler.sendEmptyMessageDelayed(0, 2000);
	}

	private void initViews() {
		mViewPager = (ViewPager) findViewById(R.id.vp);
		mRadioGroup = (RadioGroup) findViewById(R.id.rg);

		//初始化图片
		initImageViews();
		mViewPager.setAdapter(new LooperAdapter(mImageViewList));

		//初始化小圆点
		initDots();

		mViewPager.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
					sendMessageDelay();
				}
				return false;
			}
		});

		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int index) {
				// 分页指示器跟着动
				mRadioGroup.check(index % ImageUri.images.length);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void initDots() {

		int wrap = RadioGroup.LayoutParams.WRAP_CONTENT;
		RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(wrap, wrap);
		params.leftMargin = 5;

		for (int i = 0; i < ImageUri.images.length; i++) {
			// 创建一个单选按钮
			RadioButton rb = new RadioButton(this);
			rb.setId(i);
			// 设置按钮图片
			rb.setButtonDrawable(R.drawable.x_dot_selector);
			// 添加到组里
			if (i == 0) {
				mRadioGroup.addView(rb);
			} else {
				mRadioGroup.addView(rb, params);
			}
		}
		// 默认选中第一个
		mRadioGroup.check(0);
	}

	private void initImageViews() {
		mImageViewList = new ArrayList<ImageView>();
		int len = ImageUri.images.length;
		if (len <= 2) {
			len = len * 2;
		}
		for (int i = 0; i < len; i++) {
			ImageView iv = new ImageView(this);
			// 添加touch事件
			iv.setOnTouchListener(new ImageTouchEvent());
			iv.setScaleType(ScaleType.FIT_XY);
			// 加载图片
			mImageLoader.displayImage(ImageUri.images[i % ImageUri.images.length], iv);
			// 添加到list中
			mImageViewList.add(iv);
		}
	}

	class ImageTouchEvent implements View.OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 按下时,停止播放
				mHandler.removeCallbacksAndMessages(null);
				break;
			case MotionEvent.ACTION_UP:
				// 抬起时,继续播放
				sendMessageDelay();
				break;
			}
			return true;
		}
	}
}
