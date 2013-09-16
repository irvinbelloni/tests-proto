package com.ossia.test.web.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Question;

public class PropositionReponseModel {
	
	private Integer id ; 
	
	private Integer questionId ; 
	
	@NotEmpty
	private String valeur ; 
	
	@NotNull
    private Boolean propositionCorrecte ;
	
	public PropositionReponseModel() {
		super();
		this.id = 0 ;
	}
	
	public PropositionReponseModel(Question question) {
		super();
		this.id = 0 ;
		this.questionId = question.getId() ; 
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
