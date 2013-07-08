package com.android.system.controled.bean;

/**
 * class name：SmsInfo<BR>
 * class description：获取短信各种信息的类<BR>
 * PS： <BR>
 * Date:2012-3-19<BR>
 * 
 * @version 1.00
 */
public class SmsInfo {

	private int thread_id;
	private long date;
	private String address;
	private int person;
	private int type;
	private String subject;
	private String body;
	private int read;
	private int status;
	
	
	
	public int getThread_id() {
		return thread_id;
	}
	
	public void setThread_id(int thread_id) {
		this.thread_id = thread_id;
	}
	
	public long getDate() {
		return date;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getPerson() {
		return person;
	}
	
	public void setPerson(int person) {
		this.person = person;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public int getRead() {
		return read;
	}
	
	public void setRead(int read) {
		this.read = read;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

}