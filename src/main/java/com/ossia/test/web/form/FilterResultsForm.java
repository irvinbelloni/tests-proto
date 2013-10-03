package com.ossia.test.web.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class FilterResultsForm {

	private String testName;
	
	private String testType;
	
	private String candidateName;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date passingDateFrom;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date passingDateTo;

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public Date getPassingDateFrom() {
		return passingDateFrom;
	}

	public void setPassingDateFrom(Date passingDateFrom) {
		this.passingDateFrom = passingDateFrom;
	}

	public Date getPassingDateTo() {
		return passingDateTo;
	}

	public void setPassingDateTo(Date passingDateTo) {
		this.passingDateTo = passingDateTo;
	}
}
