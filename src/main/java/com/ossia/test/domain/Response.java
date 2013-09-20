package com.ossia.test.domain;//

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "T_RESPONSES")
public class Response implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@ManyToOne @JoinColumn(name = "evaluation_id") @NotNull
	private Evaluation evaluation;
	
	@OneToOne @JoinColumn(name = "question_id")  @NotNull
	private Question question;
    
	@OneToMany
    @JoinTable(
            name="t_responses_t_propositions_responses",
            joinColumns = @JoinColumn( name="response_id"),
            inverseJoinColumns = @JoinColumn( name="proposition_id")
    )
    private Set<PropositionReponse> reponsesChoisies ; 

    public Response() {
    	this.id = 0 ; 
	}

	public Response(Question question) {
		super();
		this.question = question;
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

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public Set<PropositionReponse> getReponsesChoisies() {
		if (reponsesChoisies == null) {
			reponsesChoisies = new HashSet<PropositionReponse>();
		}
		return reponsesChoisies;
	}

	public void setReponsesChoisies(Set<PropositionReponse> reponsesChoisies) {
		this.reponsesChoisies = reponsesChoisies;
	}    
}