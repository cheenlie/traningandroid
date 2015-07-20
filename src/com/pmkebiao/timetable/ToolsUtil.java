package com.pmkebiao.timetable;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

public class ToolsUtil {
	 /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * 
     * dp=dip,不同手机显示效果不同，不依赖像素，
     * px不同设备显示效果一样
     * sp: scaled pixels(放大像素). 主要用于字体显示best for textsize
     */
	public static int dipTopx(Context context, float pxValue) {

		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue * scale + 0.5f);
	}

	
	/**
	 * 获得yyyy-M-d格式的时间string
	 */
	public static String getCurrentTimeStringHX(){
		String currentDate=null;
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d");
		currentDate = simpleDateFormat.format(date);
		return currentDate;
		
	}
	
	
	/**
	 * 获得yyyy.MM.dd格式的时间string
	 */
	public static String getCurrentTimeStringD(){
		String currentDate=null;
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		currentDate = simpleDateFormat.format(date);
		return currentDate;
		
	}
	
}
