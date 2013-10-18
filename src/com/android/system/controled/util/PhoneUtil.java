package com.android.system.controled.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Base64;

public class PhoneUtil {

	/**
	 * ����ֻ���Ϣ
	 * 
	 * @Description:
	 * @param con
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public static String getHandsetId(Context con) {

		TelephonyManager tm = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
		StringBuilder sb = new StringBuilder();

		int version = -1;
		String manufacturer = "null";
		String model = "null";
		String device = "null";

		try {
			Class<android.os.Build.VERSION> build_version_class = android.os.Build.VERSION.class;
			// ȡ�� android �汾
			java.lang.reflect.Field field;
			field = build_version_class.getField("SDK_INT");
			version = (Integer) field.get(new android.os.Build.VERSION());
			sb.append("\r\nSDK_INT=" + version);

			Class<android.os.Build> build_class = android.os.Build.class;
			// ȡ������
			java.lang.reflect.Field manu_field = build_class.getField("MANUFACTURER");
			manufacturer = (String) manu_field.get(new android.os.Build());
			sb.append("\r\nManufacturer=" + manufacturer);
			// ȡ����̖
			java.lang.reflect.Field field2 = build_class.getField("MODEL");
			model = (String) field2.get(new android.os.Build());
			sb.append("\r\nMODEL=" + model);
			// ģ�M̖�a
			java.lang.reflect.Field device_field = build_class.getField("DEVICE");
			device = (String) device_field.get(new android.os.Build());
			sb.append("\r\nDEVICE=" + device);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		sb.append("\r\nIMEI= " + tm.getDeviceId());
		sb.append("\r\nLine1Number= " + tm.getLine1Number());
		sb.append("\r\nSimCountryIso= " + tm.getSimCountryIso());
		sb.append("\r\nSimOperatorName= " + tm.getSimOperatorName());
		sb.append("\r\nIMSI= " + tm.getSubscriberId());

		return Base64.encodeToString(sb.toString().getBytes(), Base64.DEFAULT);
	}
}
