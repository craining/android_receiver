package com.android.system.controled.util;

public class StringUtil {

	/**
	 * 电话号码中除去多余的字符
	 * 
	 * @Description:
	 * @param number
	 * @return
	 */
	public static String getRidofSpecialOfTel(String number) {
		String result = number;
		if (result != null) {
			result = result.replaceAll("-", "");
			result = result.replaceAll(" ", "");
			result = result.replaceAll("\\+86", "");
			if (result.startsWith("12520")) {
				// 若是飞信短信
				result = result.replace("12520", "");
			}
		}

		return result;
	}

	/**
	 * 是否为空
	 * 
	 * @Description:
	 * @param str
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-18
	 */
	public static boolean isNull(String str) {
		if (str == null || str.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 过滤文件名中不合法字符
	 * 
	 * /:\*?"<>|
	 * 
	 * 注意java转义字符和正则
	 * 
	 * @Description:
	 * @param name
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-19
	 */
	public static String getRidofSpecialOfFileName(String name) {
		String result = name;
		if (result != null) {
			result = result.replaceAll("\\:", "");
			result = result.replaceAll("\\/", "");
			result = result.replaceAll("\\\\", "");
			result = result.replaceAll("\\*", "");
			result = result.replaceAll("\\?", "");
			result = result.replaceAll("<", "");
			result = result.replaceAll(">", "");
			result = result.replaceAll("\\|", "");
			result = result.replaceAll("\"", "");
			if (result.startsWith("12520")) {
				// 若是飞信短信
				result = result.replace("12520", "");
			}
		}
		return result;
	}
}
