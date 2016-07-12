package com.example.looper1409.app;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

/**
 * 自定义Application中,初始化ImageLoader
 * Copyright: 版权所有 (c) 2002 - 2003
 *
 * @author 陈振国
 * @version 1.0.0
 * @create 2016年7月8日
 */
public class BwApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		//使用默认的全局配置
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
	}
}
