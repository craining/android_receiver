package com.android.system.controled.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.system.controled.Debug;
import com.android.system.controled.bean.SmsInfo;
import com.android.system.controled.util.TimeUtil;

public class SdcardDbOpera extends SdcardSqliteHelper {

	private static final String TAG = "TableAllsms";

	private static SdcardDbOpera mInstence;

	private SdcardDbOpera() {
		super();
	}

	public static SdcardDbOpera getInstence() {
		if (mInstence == null) {
			mInstence = new SdcardDbOpera();
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
	// public long insertData(int thread_id, long datelong, String dateString, String address, int personId,
	// String personName, int type, String subject, String body, int read, int status) {
	public long insertData(SmsInfo sms, String persionName) {

		long result = -1;
		Cursor cur = null;
		try {
			cur = query(Tables.TableSms.TABLE_NAME_ALLSMS, null, Tables.TableSms.ALLSMS_TABLE_COLUMN_DATE_LONG + "=?", new String[] { sms.getDate() + "" }, null, null);
			if (cur != null && cur.getCount() > 0) {
				Debug.e(TAG, "already exists this data!");
			} else {
				ContentValues initialValues = new ContentValues();
				initialValues.put(Tables.TableSms.ALLSMS_TABLE_COLUMN_THREAD_ID, sms.getThread_id());
				initialValues.put(Tables.TableSms.ALLSMS_TABLE_COLUMN_DATE_LONG, sms.getDate());
				initialValues.put(Tables.TableSms.ALLSMS_TABLE_COLUMN_DATE_STR, TimeUtil.longToDateTimeString(sms.getDate()));
				initialValues.put(Tables.TableSms.ALLSMS_TABLE_COLUMN_ADDRESS, sms.getAddress());
				initialValues.put(Tables.TableSms.ALLSMS_TABLE_COLUMN_PERSON_ID, sms.getPerson());
				initialValues.put(Tables.TableSms.ALLSMS_TABLE_COLUMN_PERSON_NAME, persionName);
				initialValues.put(Tables.TableSms.ALLSMS_TABLE_COLUMN_TYPE, sms.getType());
				initialValues.put(Tables.TableSms.ALLSMS_TABLE_COLUMN_SUBJECT, sms.getSubject());
				initialValues.put(Tables.TableSms.ALLSMS_TABLE_COLUMN_BODY, sms.getBody());
				initialValues.put(Tables.TableSms.ALLSMS_TABLE_COLUMN_READ, sms.getRead());
				initialValues.put(Tables.TableSms.ALLSMS_TABLE_COLUMN_STATUS, sms.getStatus());
				result = insert(Tables.TableSms.TABLE_NAME_ALLSMS, initialValues);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

 

	/**
	 * 删除整个表的数据
	 * 
	 * @param db
	 */
	public void deleteTable() {
		 delete(Tables.TableSms.TABLE_NAME_ALLSMS, null, null);
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
			cur =  query(Tables.TableSms.TABLE_NAME_ALLSMS, null, null, null, null, null);
			if (cur != null && cur.getCount() > 0) {
				SmsInfo sms = null;
				cur.moveToFirst();
				do {
					sms = new SmsInfo();
					sms.setType(cur.getInt(cur.getColumnIndex(Tables.TableSms.ALLSMS_TABLE_COLUMN_TYPE)));
					sms.setAddress(cur.getString(cur.getColumnIndex(Tables.TableSms.ALLSMS_TABLE_COLUMN_ADDRESS)));
					sms.setName(cur.getString(cur.getColumnIndex(Tables.TableSms.ALLSMS_TABLE_COLUMN_PERSON_NAME)));
					sms.setBody(cur.getString(cur.getColumnIndex(Tables.TableSms.ALLSMS_TABLE_COLUMN_BODY)));
					sms.setDate(cur.getLong(cur.getColumnIndex(Tables.TableSms.ALLSMS_TABLE_COLUMN_DATE_LONG)));
					smss.add(sms);
				} while (cur.moveToNext());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return smss;
	}
}
