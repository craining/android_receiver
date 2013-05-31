package com.alipay.safe.util;


public class SmsUtil {

	
	/**
	 * 所有的短信
	 */
	public static final String SMS_URI_ALL = "content://sms/";
	/**
	 * 收件箱短信
	 */
	public static final String SMS_URI_INBOX = "content://sms/inbox";
	/**
	 * 发件箱短信
	 */
	public static final String SMS_URI_SEND = "content://sms/sent";
	/**
	 * 草稿箱短信
	 */
	public static final String SMS_URI_DRAFT = "content://sms/draft";

	
	public static final String[] SMS_COLUMNS = new String[] { "thread_id", "date", "address", "person", "type", "subject", "body", "read", "status"};
	
}
