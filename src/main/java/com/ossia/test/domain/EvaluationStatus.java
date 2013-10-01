package com.ossia.test.domain;

public enum EvaluationStatus {
	ASSIGNED(1),
	IN_PROGRESS(2),
	DONE(3),
	ALREADY_ASSIGNED(4);
	
	private int code;
	
	private EvaluationStatus(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
}
