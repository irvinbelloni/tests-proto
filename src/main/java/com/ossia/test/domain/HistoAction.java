package com.ossia.test.domain;

public enum HistoAction {
	
	ACTIVATE("ACTIV"),
	ADD("ADD"),
	ADD_QUESTION("ADDQU"),
	ADD_DUPLICATION("ADDDU"),
	ASSIGNATION_TEST("ASSTE"),
	ARCHIVE_TEST("ARCTE"),
	DELETE("DELET"),
	DEACTIVATE("DEACT"),
	DELETE_QUESTION("DELQU"),
	DESASSIGNATION_TEST("DESTE"),
	DUPLICATE_TEST("DUPTE"),
	EDIT("EDIT"),
	EDIT_QUESTION("EDITQ"),
	VALIDATE_TEST("VALTE");	
	
	private String code;
	
	private HistoAction(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
}