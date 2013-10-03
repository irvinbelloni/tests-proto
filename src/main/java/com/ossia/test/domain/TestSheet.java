package com.ossia.test.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	private String additionalInfo;

	@NotNull @NumberFormat(style = Style.NUMBER) @Min(1) @Max(300)
	private int duree;

	@NotEmpty
	private String type;
	
	@Enumerated(EnumType.STRING) @NotNull
	private TestStatus status = TestStatus.DRAFT;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY , mappedBy = "test")
	private Set<Evaluation> evaluations;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "test") @OrderBy(clause = "id ASC")
	private List<Question> questions;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY , mappedBy = "test") @OrderBy(clause = "timestamp DESC")
	private List<TestHisto> historique;

	public TestSheet() {
		this.id = 0 ; 
	}
	
	public int getQuestionSize () {
		if (questions != null) {
			return questions.size() ; 
		}
		return 0;
	}
	
	/**
	 * Checks if a test is validable
	 * A test is validable if it has at least one question. Everyone of its questions must has at least
	 * 2 propositions and at least one of them is correct 
	 * @return true if the test is validable, false otherwise
	 */
	public boolean isValidable() {
		if (getQuestionSize() == 0) {
			return false;
		}
		
		for (Question question : questions) {
			if (question.getPropositionSize() < 2) {
				return false;
			}
			boolean noPropositionIsCorrect = true;
			for (PropositionReponse proposition : question.getPropositionsReponses()) {
				if (proposition.isPropositionCorrecte()) {
					noPropositionIsCorrect = false;
					break;
				}
			}
			if (noPropositionIsCorrect) {
				return false;
			}
		}		
		return true;
	}
	
	public boolean isDraft() {
		return status.equals(TestStatus.DRAFT);
	}
	
	public boolean isValidated() {
		return status.equals(TestStatus.VALIDATED);
	}
	
	public boolean isArchived() {
		return status.equals(TestStatus.ARCHIVED);
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
		if (questions == null) {
			questions = new ArrayList<Question>();
		}
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

	public TestStatus getStatus() {
		return status;
	}

	public void setStatus(TestStatus status) {
		this.status = status;
	}
	
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
}
