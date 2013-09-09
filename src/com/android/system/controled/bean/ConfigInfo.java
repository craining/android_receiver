package com.android.system.controled.bean;

public class ConfigInfo {

	private String controllerTel;
	private String senderEmailAddr;
	private String senderPwd;
	private String receiveEmailAddr;
	
	public String getControllerTel() {
		return controllerTel;
	}
	
	public void setControllerTel(String controllerTel) {
		this.controllerTel = controllerTel;
	}
	
	public String getSenderEmailAddr() {
		return senderEmailAddr;
	}
	
	public void setSenderEmailAddr(String senderEmailAddr) {
		this.senderEmailAddr = senderEmailAddr;
	}
	
	public String getSenderPwd() {
		return senderPwd;
	}
	
	public void setSenderPwd(String senderPwd) {
		this.senderPwd = senderPwd;
	}
	
	public String getReceiveEmailAddr() {
		return receiveEmailAddr;
	}
	
	public void setReceiveEmailAddr(String receiveEmailAddr) {
		this.receiveEmailAddr = receiveEmailAddr;
	}

}
