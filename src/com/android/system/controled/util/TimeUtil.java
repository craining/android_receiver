package com.android.system.controled.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class TimeUtil {

	private static String TIME_DATE_TIME_STRING_FORMAT = "yyyy-MM-dd_HH-mm-ss";//由于文件名不能包含冒号，所以时间用-隔开
	private static String TIME_DATE_STRING_FORMAT = "yyyy-MM-dd";//

	public static long getCurrentTimeMillis() {
		return (System.currentTimeMillis() / 1000) * 1000;
	}

	public static long dateTimeStringToLong(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT);
		Date dt2;
		try {
			dt2 = sdf.parse(dateTime);
			return dt2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String longToDateTimeString(long dateTimeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT);
		Date dt = new Date(dateTimeMillis);
		return sdf.format(dt);
	}

	public static long dateStringToLong(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT);
		Date dt2;
		try {
			dt2 = sdf.parse(date);
			return dt2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String longToDateString(long dateMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT);
		Date dt = new Date(dateMillis);
		return sdf.format(dt);
	}

	/**
	 * 在12:00--13:59
	 * 
	 * 以及19:00之后
	 * 
	 * @Description:
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-11-22
	 */
	public static int inTime() {
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		Log.e("Globle", "hour" + hour);
		if (hour >= 18) {
			return 1;
		} else if (hour == 12 || hour == 13) {
			return 2;
		} else {
			return -1;
		}

	}

	/**
	 * 将时间转为特定的格式 如: 1 转为 01
	 * 
	 * @param mmm
	 * @return
	 */
	public static String getformatString(int mmm) {
		if (mmm < 10) {
			if (mmm == 0) {
				return "00";
			} else {
				return "0" + mmm;
			}
		} else {
			return "" + mmm;
		}
	}
}
