package com.android.system.controled.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.system.controled.Debug;

public class TableAllsms {

	private static final String TAG = "TableAllsms";

	private static SQLiteDatabase mSQLiteDatabase = null;

	public TableAllsms(Context context) {
		mSQLiteDatabase = DatabaseUtil.getMDB();
	}

	public void close() {
		if (mSQLiteDatabase != null) {
			mSQLiteDatabase.close();
		}
	}

	/**
	 * 插入一条数据
	 * 
	 * @param style
	 * @param down
	 * @param up
	 * @return
	 */
	public long insertData(int thread_id, long datelong, String dateString, String address, int personId, String personName, int type, String subject, String body, int read, int status) {

		long result = -1;
		Cursor cur = null;
		try {
			cur = mSQLiteDatabase.query(DatabaseUtil.TABLE_NAME_ALLSMS, null, DatabaseUtil.ALLSMS_TABLE_COLUMN_DATE_LONG + "=?", new String[] { datelong + "" }, null, null, null);
			if (cur != null && cur.getCount() > 0) {
				Debug.e(TAG, "already exists this data!");
			} else {
				ContentValues initialValues = new ContentValues();
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_THREAD_ID, thread_id);
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_DATE_LONG, datelong);
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_DATE_STR, dateString);
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_ADDRESS, address);
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_PERSON_ID, personId);
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_PERSON_NAME, personName);
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_TYPE, type);
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_SUBJECT, subject);
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_BODY, body);
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_READ, read);
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_STATUS, status);
				result = mSQLiteDatabase.insert(DatabaseUtil.TABLE_NAME_ALLSMS, DatabaseUtil.ALLSMS_TABLE_COLUMN_ID, initialValues);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 读取表格一列或几列数据，为空时为全部数据
	 * 
	 * @Description:
	 * @param colums
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-10-26
	 */
	public static Cursor getData(String[] colums) {

		if (colums == null || colums.length == 0) {
			return mSQLiteDatabase.query(DatabaseUtil.DB_NAME, DatabaseUtil.ALLSMS_ALLCOLUMNS, null, null, null, null, null);
		} else {
			return mSQLiteDatabase.query(DatabaseUtil.DB_NAME, colums, null, null, null, null, null);
		}

	}

	/**
	 * 删除整个表的数据
	 * 
	 * @param db
	 */
	public void deleteTable() {
		mSQLiteDatabase.execSQL(DatabaseUtil.CLEAR_ALLSMS);
	}

}
