package com.android.system.controled.util;

public class StringUtil {

	/**
	 * �绰�����г�ȥ������ַ�
	 * 
	 * @Description:
	 * @param number
	 * @return
	 */
	public static String getRidofSpeciall(String number) {
		String result = number;
		if (result != null) {
			result = result.replaceAll("-", "");
			result = result.replaceAll(" ", "");
			result = result.replaceAll("\\+86", "");
			if (result.startsWith("12520")) {
				// ���Ƿ��Ŷ���
				result = result.replace("12520", "");
			}
		}

		return result;
	}

	/**
	 * �Ƿ�Ϊ��
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
}
