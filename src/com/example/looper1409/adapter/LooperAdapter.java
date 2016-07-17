package com.example.looper1409.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 适配器
 * Copyright: 版权所有 (c) 2002 - 2003
 *
 * @author 陈振国
 * @version 1.0.0
 * @create 2016年7月8日
 */
public class LooperAdapter extends PagerAdapter {

	private List<ImageView> mList;
	
	public LooperAdapter(List<ImageView> mList) {
		super();
		this.mList = mList;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		//注意这里
		ImageView iv = mList.get(position % mList.size());
		//先判断是否已经添加过
		if (iv.getParent() != null) {
			container.removeView(iv);
		}
		container.addView(iv);
		return iv;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);;
	}
	
	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
