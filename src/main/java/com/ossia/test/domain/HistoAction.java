package com.ossia.test.domain;

public enum HistoAction {

	ACTIVATE("ACTIV"),
	ADD("ADD"),
	DELETE("DELET"),
	DEACTIVATE("DEACT"),
	EDIT("EDIT");

	private String code;

	private HistoAction(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}
}