package com.ossia.test.web.form;

import javax.validation.constraints.NotNull;

public class AssignTestForm {

	@NotNull
	private int testId;
	
	@NotNull
	private int candidateId;

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}
}
