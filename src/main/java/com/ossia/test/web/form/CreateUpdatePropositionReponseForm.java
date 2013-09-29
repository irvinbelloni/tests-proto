package com.ossia.test.web.form;

import org.hibernate.validator.constraints.NotEmpty;

import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Question;

public class CreateUpdatePropositionReponseForm {
	
	private Integer id ; 
	
	private Integer questionId ; 
	
	@NotEmpty
	private String valeur ; 
	
	private Boolean propositionCorrecte ;
	
	public CreateUpdatePropositionReponseForm() {
		super();
		this.id = 0 ;
	}
	
	public CreateUpdatePropositionReponseForm(Question question) {
		super();
		this.id = 0 ;
		this.questionId = question.getId() ;
		propositionCorrecte = false;
	}
	
	public PropositionReponse updatePropositionReponseModel (PropositionReponse pr) {
		
		pr.setValeur (this.valeur) ;
		pr.setPropositionCorrecte (this.propositionCorrecte) ;
		return pr ; 
	}
	
	public PropositionReponse convertToNewPropositionReponseDomain (Question question) {
		PropositionReponse pr = new PropositionReponse() ; 
		pr.setQuestion (question) ;
		pr.setValeur (this.valeur) ;
		pr.setPropositionCorrecte (this.propositionCorrecte);
		return pr ; 
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public Boolean getPropositionCorrecte() {
		return propositionCorrecte;
	}

	public void setPropositionCorrecte(Boolean propositionCorrecte) {
		this.propositionCorrecte = propositionCorrecte;
	}
}
