package com.pmkebiao.timetable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import android.R.integer;
import android.util.Log;

public class MyTimeUtil {

	private static final int WEEKDAYS = 7;
	
	/**
	 * 
	 * @param dayOfWeek
	 * @param daysOfMonth
	 * @param state  传人一个状态表示这个函数是经过处理后的getWeeksOfMonth函数
	 * @return
	 */

	public static int getWeeksOfMonth(int dayOfWeek,int daysOfMonth,boolean state) {
		int weeksOfMonth=0;
		// getCalendar(year, month);
		int preMonthRelax = 0;
		if (dayOfWeek != 7) {
			preMonthRelax = dayOfWeek;
		}
		if ((daysOfMonth + preMonthRelax) % 7 == 0) {
			weeksOfMonth = (daysOfMonth + preMonthRelax) / 7;
		} else {
			weeksOfMonth = (daysOfMonth + preMonthRelax) / 7 + 1;
		}
		return weeksOfMonth;
	}
	
	/**
	 * 判断某年某月所有的星期数
	 * 
	 * @param year
	 * @param month
	 */
	public static int getWeeksOfMonth(int year, int month) {
		// 先判断某月的第一天为星期几
		SpecialCalendar specialCalendar=new SpecialCalendar();
		int weeksOfMonth=0;
		int preMonthRelax = 0;
		int dayFirst = getWhichDayOfWeek(year, month);
		int days = specialCalendar.getDaysOfMonth(specialCalendar.isLeapYear(year), month);
		if (dayFirst != 7) {
			preMonthRelax = dayFirst;
		}
		if ((days + preMonthRelax) % 7 == 0) {
			weeksOfMonth = (days + preMonthRelax) / 7;
		} else {
			weeksOfMonth = (days + preMonthRelax) / 7 + 1;
		}
		return weeksOfMonth;

	}
	
	/**
	 * 判断某年某月的第一天为星期几
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getWhichDayOfWeek(int year, int month) {
		SpecialCalendar specialCalendar=new SpecialCalendar();
		
		return specialCalendar.getWeekdayOfMonth(year, month);
	}

	private static String[] WEEK = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
			"星期六" };

	/**
	 * 日期变量转成对应的星期字符串
	 * 
	 * @param 日期
	 * @return 日期是星期几
	 */
	public static String DateToWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayIndex < 1 || dayIndex > WEEKDAYS) {
			return null;
		}
		return WEEK[dayIndex - 1];
	}

	/**
	 * 把int型星期数转换成String形式
	 * 
	 * @param i
	 *            int型星期数(1对应星期一，7对应星期日)
	 * @return 星期几
	 */
	public static String weekInt2String(int i) {
		return WEEK[i];
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 *            后日期 格式为"yyyy.MM.dd"
	 * @param date2
	 *            前日期 格式为"yyyy.MM.dd"
	 * @return
	 */
	public static long getDays(String date1, String date2) {

		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;

		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy.MM.dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 两个时间之间的周数
	 * 
	 * @param date1
	 *            后日期 格式为"yyyy.MM.dd"
	 * @param date2
	 *            前日期 格式为"yyyy.MM.dd"
	 * @return
	 */
	public static int getWeeks(String date1, String date2) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Date startDate;
		Date endDate = null;
		try {
			startDate = format.parse(date2);
			endDate = format.parse(date1);
			start.setTime(startDate);
			end.setTime(endDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int saturday = 0;

		while (start.compareTo(end) <= 0) {
			int w = start.get(Calendar.DAY_OF_WEEK);
			if (w == Calendar.SATURDAY)
				saturday++;
			// 循环，每次天数加1
			start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
		}
		// 如果后日期为星期天，则减一
		if (DateToWeek(endDate).equals("星期六")) {
			saturday = saturday - 1;
		}

		return saturday;

	}

	/**
	 * 获得指定日期与2014.09.22之间的周数
	 * 
	 * @param date
	 *            格式为"yyyy.MM.dd"
	 * @return
	 */
	public static int getWeeks(String date) {
		return getWeeks(date, "2014.09.22");
	}

	/**
	 * 根据相对2014.09.22的周数与星期数，获得日期
	 * 
	 * @param week
	 *            周数（相对于2014.09.22）
	 * @param dayofweek
	 *            星期几（星期一-1，星期日-7）
	 * @return 日期值（格式为"yyyy.MM.dd"）
	 */
	public static String getDateOfWeekAndDay(int week, int dayofweek) {
		int append = dayofweek - 1;
		String result = "0000.00.00";
		result = getDateWeekAdd("2014.09.22", week);
		result = getDateStr(result, append);
		return result;
	}

	/**
	 * 获取指定周（与2014.09.22之间的周数）第一天（星期日）-最后一天（星期六）
	 * 
	 * @param week
	 *            与2014.09.22之间的周数
	 * 
	 * @return 返回的第一天~最后一天String（格式为 第一天日期~最后一天日期）
	 */
	public static String getTheWeekStr(int week) {
		String dayStr = getDateWeekAdd("2014.09.22", week);
		String result = getWeekFirstAndLastDay(dayStr);
		return result;
	}

	/**
	 * 获取指定周（与2014.09.22之间的周数）第一天（星期日）~最后一天（星期六）
	 * 
	 * @param week
	 *            与2014.09.22之间的周数
	 * 
	 * @return 返回的第一天-最后一天String（格式为 第一天日期~最后一天日期）
	 */
	public static String getTheWeekStrMonthAndDay(int week) {
		String dayStr = getDateWeekAdd("2014.09.22", week);
		String result = getWeekFirstAndLastDayMonthAndDay(dayStr);
		return result;
	}

	/**
	 * 获取指定周（与2014.09.22之间的周数） 前 后指定周 的日期列表
	 * 
	 * @param beforeNumber
	 *            当前周前周数
	 * @param behindNumber
	 *            当前周后周数
	 * @return 获得一个<Map<String, Object>>列表，共有beforeNumber+behindNumber+1个元素
	 *         每个Map中键“used”对应一周中跟当前日期对应的星期的日期值，String格式（yyyy.MM.dd）
	 *         键“show”对应一周的第一天和最后一天String格式（yyyy.MM.dd~yyyy.MM.dd）。
	 */
	public static List<Map<String, Object>> getweekItemsbyWeekNumber(int week,
			int beforeNumber, int behindNumber) {
		String dayStr = getDateWeekAdd("2014.09.22", week);
		Log.e("timeUtil", dayStr);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result = getWeekItems(dayStr, beforeNumber, behindNumber);
		return result;
	}

	/**
	 * 获取指定日后 后 dayAddNum 天的 日期
	 * 
	 * @param day
	 *            日期，格式为String："yyyy.MM.dd";
	 * @param dayAddNum
	 *            增加天数 格式为int;
	 * @return
	 */
	public static String getDateStr(String day, int dayAddNum) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		Date nowDate = null;
		try {
			nowDate = df.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.DAY_OF_YEAR, dayAddNum);// 周加weekAddNum
		Date tdt = calendar.getTime();// 取得加减过后的Date
		// 依照设定格式取得字符串
		String time = df.format(tdt);
		return time;
	}

	/**
	 * 获取指定周后 weekAddNum 天的 日期
	 * 
	 * @param day
	 *            日期，格式为String："yyyy.MM.dd";
	 * @param weekAddNum
	 *            增加周数 格式为int;
	 * @return
	 */

	public static String getDateWeekAdd(String day, int weekAddNum) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		Date nowDate = null;
		try {
			nowDate = df.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.WEEK_OF_YEAR, weekAddNum);// 周加weekAddNum
		Date tdt = calendar.getTime();// 取得加减过后的Date
		// 依照设定格式取得字符串
		String time = df.format(tdt);
		return time;
	}

	/**
	 * 获取指定日期所在周第一天（星期日）+最后一天（星期六）
	 * 
	 * @param day
	 *            指定的日期（格式为yyyy.MM.dd）
	 * @return 返回的第一天加最后一天String（格式为 第一天日期-最后一天日期）
	 */
	public static String getWeekFirstAndLastDay(String day) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		Date nowDate = null;
		String result = "";
		try {
			nowDate = df.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(nowDate);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		Date first = calendar.getTime();
		result = result + df.format(first);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		first = calendar.getTime();
		result = result + "-" + df.format(first);
		return result;
	}

	/**
	 * 获取指定日期所在周第一天（星期日）+最后一天（星期六）
	 * 
	 * @param day
	 *            指定的日期（格式为yyyy.MM.dd）
	 * @return 返回的第一天加最后一天String（格式为 第一天日期(M月d日)-最后一天日期(M月d日)）
	 */
	public static String getWeekFirstAndLastDayMonthAndDay(String day) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat cf = new SimpleDateFormat("M月d日");
		Date nowDate = null;
		String result = "";
		try {
			nowDate = df.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(nowDate);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		Date first = calendar.getTime();
		result = result + cf.format(first);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		first = calendar.getTime();
		result = result + "-" + cf.format(first);
		return result;
	}

	/**
	 * 获得当前日期 前 后指定周 的日期列表
	 * 
	 * @param beforeNumber
	 *            当前日期前周数
	 * @param behindNumber
	 *            当前日期后周数
	 * @return 获得一个<Map<String, Object>>列表，共有beforeNumber+behindNumber+1个元素
	 *         每个Map中键“used”对应一周中跟当前日期对应的星期的日期值，String格式（yyyy.MM.dd）
	 *         键“show”对应一周的第一天和最后一天String格式（yyyy.MM.dd~yyyy.MM.dd）。
	 */
	public static List<Map<String, Object>> getWeekItems(String dateStr,
			int beforeNumber, int behindNumber) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd"); Date
		 * nowDate = new Date(System.currentTimeMillis());// 获取当前时间
		 * 
		 * String dateStr = df.format(nowDate);
		 */

		for (int i = -beforeNumber; i <= behindNumber; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			String dateCode = getDateStr(dateStr, i * 7);
			String weekCode = getWeekFirstAndLastDay(dateCode);
			item.put("used", dateCode);
			item.put("show", weekCode);
			result.add(item);
		}
		return result;

	}

	/**
	 * 获得指定日期是一年中第几周
	 * 
	 * @param day
	 *            日期 格式（yyyy.MM.dd）
	 * @return 第几周
	 */
	public static int getWeekOfYearOneDay(String day) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		Date date;
		try {
			date = format.parse(day);
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(date);
			return calendar.get(Calendar.WEEK_OF_YEAR);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

}
