package com.ossia.test.domain;

public enum TestStatus {
	ASSIGNED(1),
	IN_PROGRESS(2),
	DONE(3);
	
	private int code;
	
	private TestStatus(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
}
