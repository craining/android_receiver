package com.android.system.controled.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

import com.android.system.controled.Debug;
import com.android.system.controled.bean.Code;

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
	 * ����һ������
	 * 
	 * @param style
	 * @param down
	 * @param up
	 * @return
	 */
	public long insertCode(Code code) {

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
				initialValues.put(Tables.TableCodes.CODES_TABLE_COLUMN_ID, code.getId());
				initialValues.put(Tables.TableCodes.CODES_TABLE_COLUMN_RESULT, code.getResult());
				initialValues.put(Tables.TableCodes.CODES_TABLE_COLUMN_FAILED_TIMES, code.getFailedTime());
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
	 * ɾ�������������
	 * 
	 * @param db
	 */
	public void deleteTable() {
		delete(Tables.TableCodes.TABLE_NAME_CODES, null, null);
	}

	/**
	 * �����������
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
			cur = query(Tables.TableCodes.TABLE_NAME_CODES, null, null, null, null, null);
			if (cur != null && cur.getCount() > 0) {
				Code code = null;
				cur.moveToFirst();
				do {
					code = new Code();
					code.setId(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_ID)));
					code.setCode(cur.getString(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_CODE)));
					code.setDate(cur.getLong(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_DATE_LONG)));
					code.setFailedTime(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_FAILED_TIMES)));
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
	 * ���ʧ�ܵ�code
	 * @Description:
	 * @return
	 * @see: 
	 * @since: 
	 * @author: zhuanggy
	 * @date:2013-10-15
	 */
	public ArrayList<Code> getCodeFailed() {
		ArrayList<Code> codes = new ArrayList<Code>();
		Cursor cur = null;
		try {
			cur = query(Tables.TableCodes.TABLE_NAME_CODES, null, Tables.TableCodes.CODES_TABLE_COLUMN_RESULT + "=?", new String[] { Code.RESULT_FAILED + "" }, null, null);
			if (cur != null && cur.getCount() > 0) {
				Code code = null;
				cur.moveToFirst();
				do {
					code = new Code();
					code.setId(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_ID)));
					code.setCode(cur.getString(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_CODE)));
					code.setDate(cur.getLong(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_DATE_LONG)));
					code.setRedoNeed(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_NEED_REDO)));
					code.setFailedTime(cur.getInt(cur.getColumnIndex(Tables.TableCodes.CODES_TABLE_COLUMN_FAILED_TIMES)));
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
	 * ���½��
	 * @Description:
	 * @param code
	 * @see: 
	 * @since: 
	 * @author: zhuanggy
	 * @date:2013-10-15
	 */
	public void updateCodeResult(Code code) {
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(Tables.TableCodes.TABLE_NAME_CODES).append(" set ").append(Tables.TableCodes.CODES_TABLE_COLUMN_RESULT).append("=? where ").append(Tables.TableCodes.CODES_TABLE_COLUMN_ID).append("=?);");
		getWritableDatabase().execSQL(sql.toString(), new Object[] { code.getResult(), code.getId() });
	}
	
	/**
	 * ʧ������1
	 * @Description:
	 * @param code
	 * @see: 
	 * @since: 
	 * @author: zhuanggy
	 * @date:2013-10-15
	 */
	public void updateCodeFailedTime(Code code) {

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append(Tables.TableCodes.TABLE_NAME_CODES).append(" set ").append(Tables.TableCodes.CODES_TABLE_COLUMN_FAILED_TIMES).append("CONVERT (CHAR,(CONVERT(").append(Tables.TableCodes.CODES_TABLE_COLUMN_FAILED_TIMES).append(",INT)+1)) where ").append(Tables.TableCodes.CODES_TABLE_COLUMN_ID).append("=?");
		getWritableDatabase().execSQL(sql.toString(), new Object[] { code.getId() });
	}
	

}
