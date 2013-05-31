package com.alipay.safe.util;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

@SuppressLint("Wakelock")
public class RecorderUtil {

	private static RecorderUtil mRecorderUtil;
	private MediaRecorder mRecorder;
	private boolean isRecording;

	private static PowerManager pm;
	private static WakeLock wakeLock;

	private Handler mHandler;
	private static final int MSG_HANDLER_STOP = 0x555;
	
	private RecorderUtil() {
	}

	public static RecorderUtil getInstence(Context context) {

		if (mRecorderUtil == null) {
			mRecorderUtil = new RecorderUtil();
		}
		if (wakeLock == null) {
			pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
		}
		return mRecorderUtil;
	}

	public boolean startRecorder(File saveFile, int timeMinute) {
		
		if(isRecording){
			Log.e("", "正在录音，务须再录！");
			return false;
		}
		
		Log.e("", "startRecorder=" + saveFile.getAbsolutePath());
		boolean result = false;
		try {
			if (mRecorder == null) {
				mRecorder = new MediaRecorder();
			}
			mRecorder.reset();
			mRecorder.setOnInfoListener(new OnInfoListener() {

				@Override
				public void onInfo(MediaRecorder mr, int what, int extra) {
					switch (what) {
					case MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN:
						Log.e("", "MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN");
						break;
					case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED:
						Log.e("", "MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED");
						break;
					case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED:
						Log.e("", "MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED");
						break;
					default:
						break;
					}

				}
			});
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			if (timeMinute > 0) {
				mRecorder.setMaxDuration(timeMinute * 60 * 1000);
				mHandler = new Mainhandler();
				mHandler.sendEmptyMessageDelayed(MSG_HANDLER_STOP, timeMinute * 60 * 1000);
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
			if (saveFile.exists()) {
				saveFile.delete();
			}
			e.printStackTrace();
		}

		return result;
	}

	public void stopRecorder() {
		Log.e("", "stopRecorder");
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
		}

	}
	
	private class Mainhandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_HANDLER_STOP:
				stopRecorder();
				break;

			default:
				break;
			}
		}
		
	}
}
