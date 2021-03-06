package com.ossia.test.web.form;

public class QuestionForm {

	private int evaluationId;
	
	private int questionId;
	
	private int questionIndex;
	
	private int[] propositions;
	
	private boolean warnedCandidate = false;
	
	private int nextQuestionIndex;
	
	public QuestionForm() {
		
	}
	
	public QuestionForm(int evaluationId, int questionId, int questionIndex) {
		this.evaluationId = evaluationId;
		this.questionId = questionId;
		this.questionIndex = questionIndex;
	}
	
	public int getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(int evaluationId) {
		this.evaluationId = evaluationId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int[] getPropositions() {
		return propositions;
	}

	public void setPropositions(int[] propositions) {
		this.propositions = propositions;
	}

	public int getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}

	public boolean isWarnedCandidate() {
		return warnedCandidate;
	}

	public void setWarnedCandidate(boolean warnedCandidate) {
		this.warnedCandidate = warnedCandidate;
	}

	public int getNextQuestionIndex() {
		return nextQuestionIndex;
	}

	public void setNextQuestionIndex(int nextQuestionIndex) {
		this.nextQuestionIndex = nextQuestionIndex;
	}
}
