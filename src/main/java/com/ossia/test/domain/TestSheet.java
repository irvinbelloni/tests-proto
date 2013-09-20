package com.ossia.test.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
@Table(name = "T_SHEETS")
public class TestSheet implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotEmpty
	private String intitule;

	@NotNull @NumberFormat(style = Style.NUMBER)
    @Min(1)
    @Max(300)
	private int duree;

	@NotEmpty
	private String type;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY , mappedBy = "test")
	private Set<Evaluation> evaluations;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "test")
	private List<Question> questions;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY , mappedBy = "test")
	@OrderBy(clause = "timestamp DESC")
	private List<TestHisto> historique;

	public TestSheet() {
		this.id = 0 ; 
	}
	
	public int getQuestionSize () {
		if (questions != null) {
			return questions.size() ; 
		}
		else {
			return 0 ; 
		}
	}

	public Integer getId() {
		return id;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<TestHisto> getHistorique() {
		if (historique == null) {
			historique = new ArrayList<TestHisto>();
		}
		return historique;
	}

	public void setHistorique(List<TestHisto> historique) {
		this.historique = historique;
	}
}
