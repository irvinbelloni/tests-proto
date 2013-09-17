package com.ossia.test.web.form;

import javax.validation.constraints.NotNull;

import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;

public class CreateUpdateQuestionForm {
	
	private Integer id;

	private String intitule;
	private String niveau;
	private String contenu;
	
	@NotNull
	private Integer testId ;
	
	public CreateUpdateQuestionForm() {
		super() ; 
		this.id = 0 ; 
	}
	
	public CreateUpdateQuestionForm(TestSheet test) {
		super() ; 
		this.id = 0 ;
		this.testId = test.getId() ; 
	}
	
	public Question updateQuestion(Question qu) {
		qu.setContenu(this.contenu) ; 
		qu.setIntitule(this.intitule) ; 
		qu.setNiveau(this.niveau) ; 
		
		return qu ; 
	} 

	public Question convertToNewTestDomain (TestSheet test) {
		Question qu = new Question() ; 
		qu.setContenu(this.contenu) ; 
		qu.setIntitule(this.intitule) ; 
		qu.setNiveau(this.niveau) ; 
		qu.setTest(test) ; 
		
		return qu ; 
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

}
