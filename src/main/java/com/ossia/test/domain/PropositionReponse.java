package com.ossia.test.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table (name = "T_PROPOSITIONS_RESPONSES")
public class PropositionReponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ; 
	
	@ManyToOne @JoinColumn(name = "question_id")
    @NotNull
	private Question question ; 
	
	@NotEmpty
	@Length(max=100000)
	private String valeur ; 
	
	@NotNull
    private boolean propositionCorrecte ;

	public PropositionReponse() {
		this.id = 0 ; 
	}
	
	public PropositionReponse(Question question) {
		this.id = 0 ;
		this.question = question ; 
	}

	public PropositionReponse(Question question, String valeur,
			Boolean propositionCorrecte) {
		super();
		this.question = question;
		this.valeur = valeur;
		this.propositionCorrecte = propositionCorrecte;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public void setPropositionCorrecte(boolean propositionCorrecte) {
		this.propositionCorrecte = propositionCorrecte;
	}

	public boolean isPropositionCorrecte() {
		return propositionCorrecte;
	} 

}
