package com.android.system.controled.bean;

public class Command implements Comparable<Command> {

	public Code code;
	public Runnable runnable;

	@Override
	public int compareTo(Command another) {
		if (another.code.getDate() > code.getDate()) {
			return 1;
		} else if (another.code.getDate() == code.getDate()) {
			return 0;
		}
		return -1;
	}

	@Override
	public boolean equals(Object o) {

		return code.equals(((Command) o).code);
	}

}
