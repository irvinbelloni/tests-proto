package com.ossia.test.domain;

public enum HistoAction {
	
	ACTIVATE("ACTIV"),
	ADD("ADD"),
	ADD_QUESTION("ADDQU"),
	ASSIGNATION_TEST("ASSTE"),
	DELETE("DELET"),
	DEACTIVATE("DEACT"),
	DELETE_QUESTION("DELQU"),
	DESASSIGNATION_TEST("DESTE"),
	EDIT("EDIT");	
	
	private String code;
	
	private HistoAction(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
}
