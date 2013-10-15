package com.android.system.controled.db;

import android.database.sqlite.SQLiteDatabase;

import com.android.system.controled.MainApplication;
import com.android.system.controled.util.SQLiteHelper;

public class InnerSqliteHelper extends SQLiteHelper {

	private static InnerSqliteHelper mHelper;

	protected static final String DATABASENAME = "receiver.db";
	protected static final int VERSION = 1;

	protected InnerSqliteHelper() {
		super(MainApplication.getInstence(), DATABASENAME, null, VERSION);
	}

	public static InnerSqliteHelper getInstance() {

		if (mHelper == null) {
			mHelper = new InnerSqliteHelper();
		}

		return mHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Tables.TableCodes.CREATETABLE_CODES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(Tables.TableCodes.DELETE_TABLE_ALLCODES);
		onCreate(db);
	}
}
