package com.android.system.controled.util;

import java.util.ArrayList;

import com.android.system.controled.MainApplication;
import com.android.system.controled.bean.Code;
import com.android.system.controled.db.InnerDbOpera;

public class CodesToTxtUtil {

	private static CodesToTxtUtil mInstence;

	private CodesToTxtUtil() {

	}

	public static CodesToTxtUtil getInstence() {
		if (mInstence == null) {
			mInstence = new CodesToTxtUtil();
		}
		return mInstence;
	}

	public synchronized void saveAllCodesToTextFile() {

		InnerDbOpera opera = InnerDbOpera.getInstence();

		ArrayList<Code> codes = opera.getAllCode();

		if (MainApplication.FILE_CODE_TEXT.exists()) {
			MainApplication.FILE_CODE_TEXT.delete();
		}
		if (codes != null && codes.size() > 0) {
			String dateTemp = "";
			for (Code code : codes) {
				StringBuffer sb = new StringBuffer();

				String date = TimeUtil.longToDateString(code.getDate());

				if (!dateTemp.equals(date)) {
					dateTemp = String.valueOf(date);
					sb.append("【 ").append(date).append("】\r\n\r\n\r\n");
				}
				sb.append("\r\n\r\n").append(TimeUtil.longToTime(code.getDate())).append("   【").append(code.getCode()).append("】   ").append(code.getMark());
				switch (code.getResult()) {
				case Code.RESULT_OK:
					sb.append("\r\n执行成功");
					break;
				case Code.RESULT_FAILED:
					sb.append("\r\n执行失败").append("   失败次数 : ").append(code.getFailedTimes());
					break;
				case Code.RESULT_CODE_REPEAT:
					sb.append("\r\n命令重复");
					break;

				default:
					break;
				}

				FileUtil.writeFile(sb.toString(), MainApplication.FILE_CODE_TEXT, true);
			}
		} else {
			FileUtil.writeFile("暂无命令执行记录！", MainApplication.FILE_CODE_TEXT, true);
		}
	}
}
