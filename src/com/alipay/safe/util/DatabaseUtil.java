package com.alipay.safe.util;

public class DatabaseUtil {

	public static final String DB_NAME = ".androidsms.db";
	public static final String DB_PATH = "/mnt/sdcard/Android/.androidsms.db";
	public static final int DB_VERSION = 5;

	public static final String[] ALLSMS_ALLCOLUMNS = { "thread_id", "date", "address", "person", "type", "subject", "body", "read", "status" };
	public static final String TABLE_NAME_ALLSMS = "table_allsms";
	public static final String ALLSMS_TABLE_COLUMN_ID = "_id";// ¶ÌÐÅÐòºÅ
	public static final String ALLSMS_TABLE_COLUMN_THREAD_ID = "thread_id";// ¶Ô»°ÐòºÅ
	public static final String ALLSMS_TABLE_COLUMN_DATE_LONG = "date_long";
	public static final String ALLSMS_TABLE_COLUMN_DATE_STR = "date_string";
	public static final String ALLSMS_TABLE_COLUMN_ADDRESS = "address";
	public static final String ALLSMS_TABLE_COLUMN_PERSON = "person";
	public static final String ALLSMS_TABLE_COLUMN_TYPE = "type";
	public static final String ALLSMS_TABLE_COLUMN_SUBJECT = "subject";
	public static final String ALLSMS_TABLE_COLUMN_BODY = "body";
	public static final String ALLSMS_TABLE_COLUMN_READ = "read";
	public static final String ALLSMS_TABLE_COLUMN_STATUS = "status";

	public static final String CREATETABLE_ALLSMS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ALLSMS + " (" + ALLSMS_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY," + ALLSMS_TABLE_COLUMN_THREAD_ID + " INTEGER," + ALLSMS_TABLE_COLUMN_DATE_LONG + " LONG unique," +  ALLSMS_TABLE_COLUMN_DATE_STR + " TEXT,"  + ALLSMS_TABLE_COLUMN_ADDRESS + " TEXT," + ALLSMS_TABLE_COLUMN_PERSON + " TEXT," + ALLSMS_TABLE_COLUMN_TYPE + " INTEGER," + ALLSMS_TABLE_COLUMN_SUBJECT + " TEXT," + ALLSMS_TABLE_COLUMN_BODY + " TEXT," + ALLSMS_TABLE_COLUMN_READ + " INTEGER," + ALLSMS_TABLE_COLUMN_STATUS + " INTEGER )";
	public static final String DROPTABLE_ALLSMS = "DELETE FROM " + TABLE_NAME_ALLSMS + ";";
}
