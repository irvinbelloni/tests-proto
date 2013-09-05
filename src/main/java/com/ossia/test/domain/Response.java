package com.ossia.test.domain;//

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "T_RESPONSES")
public class Response implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Question question;
    
    @ManyToOne
    @JoinColumn(name = "evaluation_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Evaluation evaluation;
    
    @OneToOne 
    @JoinColumn(name = "repChoisie", referencedColumnName = "id", nullable = false)
    private PropositionReponse reponseChoisie ; 

    public Response() {
		super();
	}

	public Response(Question question, PropositionReponse reponseChoisie) {
		super();
		this.question = question;
		this.reponseChoisie = reponseChoisie;
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
}
