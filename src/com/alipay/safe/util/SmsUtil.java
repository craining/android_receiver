package com.alipay.safe.util;


public class SmsUtil {

	
	/**
	 * ���еĶ���
	 */
	public static final String SMS_URI_ALL = "content://sms/";
	/**
	 * �ռ������
	 */
	public static final String SMS_URI_INBOX = "content://sms/inbox";
	/**
	 * ���������
	 */
	public static final String SMS_URI_SEND = "content://sms/sent";
	/**
	 * �ݸ������
	 */
	public static final String SMS_URI_DRAFT = "content://sms/draft";

	
	public static final String[] SMS_COLUMNS = new String[] { "thread_id", "date", "address", "person", "type", "subject", "body", "read", "status"};
	
}
