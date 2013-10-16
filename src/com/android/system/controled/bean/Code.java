package com.android.system.controled.bean;


public class Code {

	public static final int RESULT_OK = 1;
	public static final int RESULT_FAILED = 0;
	public static final int RESULT_CODE_REPEAT = -1;

	public static final int REDO_NEED = 1;
	public static final int REDO_NOT = 0;

	/**
	 * 近三天为视为最近的
	 */
	public static final long CODE_RECENTLY_TIME = 259200000;//86400000*N
	
	private int id;
	private long date;
	private String code;
	private String mark;
	private int result;
	private int failedTimes;
	private int redoNeed;

	public int getRedoNeed() {
		return redoNeed;
	}

	public void setRedoNeed(int redoNeed) {
		this.redoNeed = redoNeed;
	}

	public int getFailedTimes() {
		return failedTimes;
	}

	public void setFailedTimes(int failedTimes) {
		this.failedTimes = failedTimes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public boolean equals(Object o) {
		Code code = (Code) o;
		if (getCode() == null && code.getCode() == null) {
			return true;
		} else if (code != null && code.getCode() != null) {
			return getCode().equals(code.getCode());
		}

		return false;
	}

	

}
