package com.android.system.controled.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

import com.android.system.controled.Debug;
import com.android.system.controled.bean.Code;
import com.android.system.controled.util.TimeUtil;

public class InnerDbOpera extends InnerSqliteHelper {

	private static final String TAG = "TableAllCodes";

	private static InnerDbOpera mInstence;

	private InnerDbOpera() {
		super();
	}

	public static InnerDbOpera getInstence() {

		if (mInstence == null) {
			mInstence = new InnerDbOpera();
		}
		return mInstence;
	}

	/**
	 * 插入一条数据
	 * 
	 * @param style
	 * @param down
	 * @param up
	 * @return
	 */
	public long insertNewCode(Code code) {

		long result = -1;
		Cursor cur = null;
		try {
			cur = query(Tables.TableCodes.TABLE_NAME_CODES, null, Tables.TableCodes.CODES_TABLE_COLUMN_DATE_LONG + "=?", new String[] { code.getDate() + "" }, null, null);
			if (cur != null && cur.getCount() > 0) {
				Debug.e(TAG, "already exists this data!");
			} else {
				ContentValues initialValues = new ContentValues();
				initialValues.put(Tables.TableCodes.CODES_TABLE_COLUMN_CODE, code.getCode());
				initialValues.put(Tables.TableCodes.CODES_TABLE_COLUMN_DATE_LONG, code.getDate());
				initialValues.put(Tables.TableCodes.CODES_TABLE_COLUMN_RESULT, code.getResult());
				initialValues.put(Tables.TableCodes.CODES_TABLE_COLUMN_FAILED_TIMES, code.getFailedTimes());
				initialValues.put(Tables.TableCodes.CODES_TABLE_COLUMN_NEED_REDO, code.getRedoNeed());
				initialValues.put(Tables.TableCodes.CODES_TABLE_COLUMN_MARK, code.getMark());
				result = insert(Tables.TableCodes.TABLE_NAME_CODES, initialValues);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
		}

		return result;
	}

	/**
	 * 删除整个表的数据
	 * 
	 * @param db
	 */
	public void deleteTable() {
		delete(Tables.TableCodes.TABLE_NAME_CODES, null, null);
	}

	/**
	 * 获得所有命令
	 * 
	 * @Description:
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-10-12
	 */
	public ArrayList<Code> getAllCode() {
		ArrayList<Code> codes = new ArrayList<Code>();
		Cursor cur = null;
		try {
			cur = query(Tables.TableCodes.TABLE_NAME_CODES, null, null, null, Tables.TableCodes.CODES_TABLE_COLUMN_DATE_LONG + " desc", null);
			if (cur != null && cur.getCount() > 0) {
				Code code = null;
				cur.moveToFirst();
				do {
					code = new Code();
					code.setId(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_ID)));
					code.setCode(cur.getString(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_CODE)));
					code.setDate(cur.getLong(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_DATE_LONG)));
					code.setFailedTimes(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_FAILED_TIMES)));
					code.setRedoNeed(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_NEED_REDO)));
					code.setMark(cur.getString(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_MARK)));
					code.setResult(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_RESULT)));
					codes.add(code);
				} while (cur.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
		}

		return codes;
	}

	/**
	 * 获得失败的code
	 * 
	 * @Description:
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-10-15
	 */
	public ArrayList<Code> getCodeFailedNotRepeatedNoNeedRedoRecently() {

		long timeMillsRecentlyStart = TimeUtil.getCurrentTimeMillisInner() - Code.CODE_RECENTLY_TIME;// 三天前的那一刻

		ArrayList<Code> codes = new ArrayList<Code>();
		Cursor cur = null;
		try {
			cur = query(Tables.TableCodes.TABLE_NAME_CODES, null, Tables.TableCodes.CODES_TABLE_COLUMN_RESULT + "=?  and " + Tables.TableCodes.CODES_TABLE_COLUMN_NEED_REDO + "=?" + " and " + Tables.TableCodes.CODES_TABLE_COLUMN_DATE_LONG + ">" + timeMillsRecentlyStart, new String[] { Code.RESULT_FAILED + "", Code.REDO_NEED + "" }, Tables.TableCodes.CODES_TABLE_COLUMN_DATE_LONG + " desc", null);
			if (cur != null && cur.getCount() > 0) {
				Code code = null;
				cur.moveToFirst();
				do {
					code = new Code();
					code.setId(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_ID)));
					code.setCode(cur.getString(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_CODE)));
					code.setDate(cur.getLong(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_DATE_LONG)));
					code.setRedoNeed(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_NEED_REDO)));
					code.setFailedTimes(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_FAILED_TIMES)));
					code.setMark(cur.getString(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_MARK)));
					code.setResult(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_RESULT)));
					codes.add(code);
				} while (cur.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
		}

		return codes;
	}

	/**
	 * 更新结果
	 * 
	 * @Description:
	 * @param code
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-10-15
	 */
	public void updateCodeResult(Code code) {
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(Tables.TableCodes.TABLE_NAME_CODES).append(" set ").append(Tables.TableCodes.CODES_TABLE_COLUMN_RESULT).append("=? where ").append(Tables.TableCodes.CODES_TABLE_COLUMN_DATE_LONG).append("=?");
		getWritableDatabase().execSQL(sql.toString(), new Object[] { code.getResult(), code.getDate() });
	}

	/**
	 * 失败数加1
	 * 
	 * @Description:
	 * @param code
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-10-15
	 */
	public void updateCodeFailedTimes(long codeDateTime) {

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append(Tables.TableCodes.TABLE_NAME_CODES).append(" set ").append(Tables.TableCodes.CODES_TABLE_COLUMN_FAILED_TIMES).append(" = ").append(Tables.TableCodes.CODES_TABLE_COLUMN_FAILED_TIMES).append("+1 where ").append(Tables.TableCodes.CODES_TABLE_COLUMN_DATE_LONG).append("=?");
		getWritableDatabase().execSQL(sql.toString(), new Object[] { codeDateTime });
	}

}
