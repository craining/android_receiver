package com.alipay.safe.util;

import java.io.File;
import java.io.IOException;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableAllsms {

	private static final String TAG = "TableAllsms";

	private static SQLiteDatabase mSQLiteDatabase = null;

	public TableAllsms() {
		getMDB();
	}

	private void getMDB() {
		if (mSQLiteDatabase == null || !mSQLiteDatabase.isOpen()) {
			Log.v(TAG, "will create database file");

			File parentDir = new File(DatabaseUtil.DB_PATH).getParentFile();
			Log.v(TAG, parentDir.toString());
			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
			if (!new File(DatabaseUtil.DB_PATH).exists()) {
				try {
					new File(DatabaseUtil.DB_PATH).createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(new File(DatabaseUtil.DB_PATH), null);
				mSQLiteDatabase.execSQL(DatabaseUtil.CREATETABLE_ALLSMS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
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
	public long insertData(int thread_id, long datelong, String dateString, String address, String person, int type, String subject, String body, int read, int status) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_THREAD_ID, thread_id);
		initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_DATE_LONG, datelong);
		initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_DATE_STR, dateString);
		initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_ADDRESS, address);
		initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_PERSON, person);
		initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_TYPE, type);
		initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_SUBJECT, subject);
		initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_BODY, body);
		initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_READ, read);
		initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_STATUS, status);

		return mSQLiteDatabase.insert(DatabaseUtil.TABLE_NAME_ALLSMS, DatabaseUtil.ALLSMS_TABLE_COLUMN_ID, initialValues);
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
	 * 删除整个表格
	 * 
	 * @param db
	 */
	public void deleteTable() {
		mSQLiteDatabase.execSQL(DatabaseUtil.DROPTABLE_ALLSMS);
	}

}
