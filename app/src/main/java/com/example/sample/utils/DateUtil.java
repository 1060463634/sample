package com.example.sample.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.annotation.SuppressLint;


public class DateUtil {


	public final static SimpleDateFormat datetimeSdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat dateSdf = new SimpleDateFormat(
			"yyyy-MM-dd");
	public final static SimpleDateFormat dateSdfSlash = new SimpleDateFormat(
			"yyyy/MM/dd");
	public final static SimpleDateFormat monthdaySdf = new SimpleDateFormat(
			"MM-dd");
	public final static SimpleDateFormat timeSfd = new SimpleDateFormat("HH:mm");


	public static String dateTime2String(Date date) {
		datetimeSdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
		return datetimeSdf.format(date);
	}


	public static Date string2date(String string) {
		datetimeSdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
		try {
			return datetimeSdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long string2MSecTime(String string) {
		Date date = string2date(string);
		if (date != null) {
			return date.getTime();
		} else {
			return -1;
		}
	}

	/**
	 * 按传输时间返回时间的下一天
	 * 
	 * @param date
	 *            传输时间
	 * @param day
	 *            天数左右移动，0-返回当前天
	 * @return 下一天
	 */
	public static String getNextDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return datetimeSdf.format(calendar.getTime());
	}


	/**
	 * 按传输判断是否工作日
	 * 
	 * @param date
	 *            传输时间
	 * @return 是否工作日
	 */
	public static boolean isWorkDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if (!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
			return false;
		}
		return true;
	}
	
}
